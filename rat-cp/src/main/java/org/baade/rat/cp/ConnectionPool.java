package org.baade.rat.cp;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * 连接池
 * Created by zz@baade.org on 2017/9/27.
 */
public class ConnectionPool {

    private DatabaseInfo databaseInfo;
    private Connection connection;

    private ScheduledExecutorService selectService;
    private LinkedBlockingQueue<String> selectSqls;

    public ConnectionPool(DatabaseInfo databaseInfo) throws Exception{
        this.databaseInfo = databaseInfo;
        selectSqls = new LinkedBlockingQueue<>();
        initConnection();
        initSelectService();
    }

    private void initSelectService() {
        selectService = Executors.newScheduledThreadPool(5, new ConnectionThreadFactory(databaseInfo));
//        selectService = Executors.newScheduledThreadPool(5);
        selectService.scheduleAtFixedRate(() -> {
            try {
                String sql = selectSqls.take();
                Connection connection = ((ConnectionThread)Thread.currentThread()).getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("dddddddddddddddddddddddddddsss: " + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, 100, TimeUnit.MILLISECONDS);
    }



    private void initConnection() throws Exception{
        Class.forName(databaseInfo.getDriverClass());
        List<DatabaseInfo.DBProperty> dbProperties = databaseInfo.getDbProperties();
        Properties properties = new Properties();
        for (DatabaseInfo.DBProperty dbProperty : dbProperties) {
            properties.put(dbProperty.getKey(), dbProperty.getValue());
        }

        connection = DriverManager.getConnection(databaseInfo.getUrl(), properties);

    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet testSelect(){
        Callable<ResultSet> callable = () -> {
            Connection connection = ((ConnectionThread)Thread.currentThread()).getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("show databases");
            System.out.println("callablecallable: " + Thread.currentThread().getName() +
                    "   connection:" + connection);
            return preparedStatement.executeQuery();
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
        return null;
    }
}
