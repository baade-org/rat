package org.baade.rat;

import org.baade.rat.cp.ConnectionPool;
import org.baade.rat.cp.DatabaseInfo;
import org.baade.rat.cp.DatabaseManager;
import org.baade.rat.cp.DriverClassNameDefault;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.ColumnOfTable;
import org.baade.rat.cp.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class RATSession {

    private static final Logger log = LoggerFactory.getLogger(RATSession.class);

    private ConnectionPool pool;
    private DatabaseInfo databaseInfo;
    private ICrud curd;

    private LinkedBlockingQueue<String> selectSqls;
    private LinkedBlockingQueue<String> updateSqls;

    private ScheduledExecutorService selectService;

    private ScheduledExecutorService updateService;


    public RATSession(DatabaseInfo databaseInfo) throws Exception{
        this.databaseInfo = databaseInfo;
        this.pool = new ConnectionPool(databaseInfo);
        selectSqls = new LinkedBlockingQueue<>();
        updateSqls = new LinkedBlockingQueue<>();
        init();
        initCrud();

        log.info("RAT Session init Done.");
    }

    public void initCrud(){
        DriverClassNameDefault type = DriverClassNameDefault.getType(databaseInfo.getDriverClass());
        if (type == null) {
            throw new RuntimeException("RAT not support current driver class=" + databaseInfo.getDriverClass());
        }
        curd = type.newCrud();
    }

    public void init(){
        selectService = Executors.newScheduledThreadPool(5);
        selectService.scheduleAtFixedRate(() -> {
            try {
                String sql = selectSqls.take();
                Connection connection = pool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("dddddddddddddddddddddddddddsss: " + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, 100, TimeUnit.MILLISECONDS);

        updateService = Executors.newSingleThreadScheduledExecutor();
        updateService.scheduleAtFixedRate(() -> {
            String sql = "";
            try {
                sql = updateSqls.take();
                Connection connection = pool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                log.info("RATSession Execute sql [{}].", sql);
                //如果第一个结果是 ResultSet 对象，则返回 true；如果第一个结果是更新计数或者没有结果，则返回 false
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("RATSession Execute sql success=[{}], [{}].", false, sql);
            }

        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public Map<String, Table> getAllTables(){
        try {
            Map<String, Table> tables = new HashMap<>();
            Connection connection = pool.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
            while(tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                Table table = new Table(tableName);

                ResultSet colRet = metaData.getColumns(null,"%", tableName,"%");
                while(colRet.next()) {

                    String columnName = colRet.getString("COLUMN_NAME");
                    int type = colRet.getInt("DATA_TYPE");

                    int size = colRet.getInt("COLUMN_SIZE") + 1;
                    String defaultValue = colRet.getString("COLUMN_DEF");
                    defaultValue = defaultValue == null ? "null" : defaultValue;
//                    int digits = colRet.getInt("DECIMAL_DIGITS");
                    String nullable = colRet.getString("IS_NULLABLE");

                    ColumnOfTable column = new ColumnOfTable(columnName, type, size, nullable, false, defaultValue);
                    table.put(column);
                    column.setTable(table);

                }
                table.setSchema(databaseInfo.getSchema());
                tables.put(tableName, table);
                log.debug("RATSession found table [{}].", table);

            }
            return tables;
        } catch (SQLException e) {
            log.error("", e);
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException(e);
        }

    }

    public ResultSet addSelectSql(String sql, boolean isAsync){
        return pool.testSelect();
//        if (isAsync){
//            selectSqls.add(sql);
//        } else {
//            Callable<ResultSet> callable = () -> {
//                Connection connection = pool.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                System.out.println("callablecallable: " + Thread.currentThread().getName());
//                return preparedStatement.executeQuery();
//            };
//            Future<ResultSet> future = selectService.submit(callable);
//            try {
//                ResultSet resultSet = future.get();
//                return resultSet;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

    public void addSelectSql(String sql){
        addSelectSql(sql, true);
    }

    public void addUpdateSql(String sql){
        updateSqls.add(sql);
    }

    public void save(Object entity) throws Exception{
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            log.error("RATSession Save is not @Entity class=[{}].", entity.getClass());
            return;
        }
        Table table = DatabaseManager.getAllTables().get(entity.getClass());
        if (table == null){
            log.error("RATSession Save not support @Entity class=[{}].", entity.getClass());
            return;
        }
        String insertSql = curd.insert(table, entity);
        addUpdateSql(insertSql);
    }
}
