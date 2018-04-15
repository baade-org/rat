package org.baade.rat.cp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ConnectionThread extends Thread {
    ThreadLocal<Connection> threadLocal;
    private DatabaseInfo databaseInfo;

    public ConnectionThread(Runnable r, DatabaseInfo databaseInfo) {
        super(r);
        this.threadLocal = new ThreadLocal<>();
        this.databaseInfo = databaseInfo;
        initThreadLocal();
    }

    public void initThreadLocal() {
        openConnection();
    }


    public Connection openConnection() {
        try {
            Class.forName(databaseInfo.getDriverClass());
            List<DatabaseInfo.DBProperty> dbProperties = databaseInfo.getDbProperties();
            Properties properties = new Properties();
            for (DatabaseInfo.DBProperty dbProperty : dbProperties) {
                properties.put(dbProperty.getKey(), dbProperty.getValue());
            }

            Connection currentConnection = DriverManager.getConnection(databaseInfo.getUrl(), properties);
            threadLocal.set(currentConnection);
            return currentConnection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = openConnection();
        }
        return connection;
    }
}
