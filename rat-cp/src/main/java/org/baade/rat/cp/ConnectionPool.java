package org.baade.rat.cp;

import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.Table;
import org.baade.rat.cp.table.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 连接池
 * Created by zz@baade.org on 2017/9/27.
 */
public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    private DatabaseInfo databaseInfo;
    private ExecutorService runSQLService;
    private ScheduledExecutorService readSQLQueueService;
    private LinkedBlockingQueue<RSql> sqlQueue;
    private ICrud crud;

    public ConnectionPool(DatabaseInfo databaseInfo, ICrud curd){
        this.databaseInfo = databaseInfo;
        this.sqlQueue = new LinkedBlockingQueue<>();
        this.crud = curd;
        initRunService();
        initReadSQLQueueService();
    }

    private void initRunService() {
        runSQLService = Executors.newFixedThreadPool(databaseInfo.getPoolSize(), new ConnectionThreadFactory(databaseInfo));
    }
    private void initReadSQLQueueService() {
        readSQLQueueService = Executors.newSingleThreadScheduledExecutor(new ReadRSqlThreadFactory());
        readSQLQueueService.scheduleAtFixedRate(() ->{
            String sql = null;
            try {
                RSql rsql = sqlQueue.take();
                sql = rsql.getSql();
                run(rsql);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("Read SQL Queue Service Error, sql={}", sql, e);
            }

        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public Map<String, Table> getAllTables() throws ExecutionException, InterruptedException, SQLException {
        List<ResultSet> resultSetList = selectListAndWaiting(() -> {
            Connection connection = ((ConnectionThread) Thread.currentThread()).getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, databaseInfo.getSchema(), "%", new String[]{"TABLE"});

            List<ResultSet> list = new ArrayList<>();
            while (tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
                list.add(colRet);
            }
            return list;
        });



        Map<String, Table> tables = new HashMap<>();

        for (ResultSet resultSet : resultSetList) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                int type = resultSet.getInt("DATA_TYPE");

                int size = resultSet.getInt("COLUMN_SIZE") + 1;
                String defaultValue = resultSet.getString("COLUMN_DEF");
                defaultValue = defaultValue == null ? "null" : defaultValue;
                String nullable = resultSet.getString("IS_NULLABLE");

                TableColumn column = new TableColumn(columnName, type, size, nullable, false, defaultValue);

                Table table = tables.get(tableName);
                if (table == null) {
                    table = new Table(tableName);
                    table.setSchema(databaseInfo.getSchema());
                    tables.put(tableName, table);
                }
                table.put(column);
                column.setTable(table);

            }
        }
        log.debug("RAT found [{}] tables From DB [{}].", tables.size(), tables);
        return tables;
    }

    public ResultSet selectAndWaiting(String sql) throws ExecutionException, InterruptedException {
        return selectAndWaiting(() -> {
            Connection connection = ((ConnectionThread) Thread.currentThread()).getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println("callablecallable: " + Thread.currentThread() +
                    "   connection:" + connection);
            return preparedStatement.executeQuery();
        });

    }

    public List<ResultSet> selectListAndWaiting(Callable<List<ResultSet>> callable) throws ExecutionException, InterruptedException {
        Future<List<ResultSet>> future = runSQLService.submit(callable);
        List<ResultSet> resultSets = future.get();
        return resultSets;

    }

    public ResultSet selectAndWaiting(Callable<ResultSet> callable) throws ExecutionException, InterruptedException {
        Future<ResultSet> future = runSQLService.submit(callable);
        ResultSet resultSet = future.get();
        return resultSet;

    }

    public ResultSet testSelect() throws ExecutionException, InterruptedException {
        return selectAndWaiting("show databases;");
    }



    public void update(String sql) {
        update(sql, false, false, null);
    }

    public void updateSync(String sql, Consumer<Object> returnMethod) throws ExecutionException, InterruptedException {
        RSql rsql = new RSql(sql, true, false, databaseInfo.isShowSql(), returnMethod);
        Object o = runSQLService.submit(this.crud.update(rsql)).get();
        if (returnMethod != null){
            returnMethod.accept(o);
        }
    }

    public void update(String sql, Consumer<Object> returnMethod) {
        update(sql, false, false, returnMethod);
    }

    public void update(String sql, boolean isSync, boolean isSelect, Consumer<Object> returnMethod) {
        RSql rsql = new RSql(sql, isSync, isSelect, databaseInfo.isShowSql(), returnMethod);
        System.out.println("updateupdateupdatedssss:" + Thread.currentThread());
        sqlQueue.add(rsql);
    }

    public void run(RSql rsql) {
        runSQLService.submit(this.crud.update(rsql));
    }


    public Runnable createUpdateNoReturn(String sql){
        return () -> {
            try {
                Connection connection = ((ConnectionThread) Thread.currentThread()).getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                if (databaseInfo.isShowSql()){
                    log.info("Run SQL: [{}]", sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Create Update No Return Error [{}]", sql, e);
            }
        };

    }



    @Override
    public String toString() {
        return "ConnectionPool{" +
                "databaseInfo=" + databaseInfo +
                '}';
    }
}
