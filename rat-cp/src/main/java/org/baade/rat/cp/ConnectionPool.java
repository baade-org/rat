package org.baade.rat.cp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

/**
 * 连接池
 * Created by zz@baade.org on 2017/9/27.
 */
public class ConnectionPool {

    private DatabaseInfo databaseInfo;
    private Connection connection;

    public ConnectionPool(DatabaseInfo databaseInfo) throws Exception{
        this.databaseInfo = databaseInfo;
        initConnection();
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
}
