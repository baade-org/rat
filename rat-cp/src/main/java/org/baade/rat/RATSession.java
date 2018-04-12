package org.baade.rat;

import org.baade.rat.cp.ConnectionPool;
import org.baade.rat.cp.DatabaseInfo;
import org.baade.rat.cp.table.Column;
import org.baade.rat.cp.table.Table;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class RATSession {

    private ConnectionPool pool;

    private LinkedBlockingQueue<String> selectSqls;
    private LinkedBlockingQueue<String> updateSqls;

    private ScheduledExecutorService selectService;

    private ScheduledExecutorService updateService;


    public RATSession(DatabaseInfo databaseInfo) throws Exception{
        this.pool = new ConnectionPool(databaseInfo);
        selectSqls = new LinkedBlockingQueue<>();
        updateSqls = new LinkedBlockingQueue<>();
        init();
    }

    public void init(){
        selectService = Executors.newScheduledThreadPool(5);
        selectService.scheduleAtFixedRate(() -> {
            try {
                System.out.println("ddddddddddddddddddddddddddd: " + Thread.currentThread().getName());
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
            try {
                String sql = updateSqls.take();
                Connection connection = pool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                boolean execute = preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
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
                System.out.println(tableName);
                Table table = new Table(tableName);

                ResultSet colRet = metaData.getColumns(null,"%", tableName,"%");
                while(colRet.next()) {

                    String columnName = colRet.getString("COLUMN_NAME");
                    int type = colRet.getInt("DATA_TYPE");

                    int size = colRet.getInt("COLUMN_SIZE");
//                    int digits = colRet.getInt("DECIMAL_DIGITS");
                    int nullable = colRet.getInt("NULLABLE");

                    Column column = new Column(columnName, type, size, nullable);
                    table.put(column);

                }
                tables.put(tableName, table);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet addSelectSql(String sql, boolean isAsync){
        if (isAsync){
            selectSqls.add(sql);
        } else {
            Callable<ResultSet> callable = new Callable<ResultSet>() {
                @Override
                public ResultSet call() throws Exception {
                    Connection connection = pool.getConnection();
                    DatabaseMetaData metaData = connection.getMetaData();
                    metaData.getTables(null, "%", "%", new String[]{"TABLE"});
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    System.out.println("callablecallable: " + Thread.currentThread().getName());
                    return preparedStatement.executeQuery();
                }
            };
            Future<ResultSet> future = selectService.submit(callable);
            try {
                ResultSet resultSet = future.get();
                return resultSet;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addSelectSql(String sql){
        addSelectSql(sql, true);
    }

    public void addUpdateSql(String sql){
        updateSqls.add(sql);
    }
}
