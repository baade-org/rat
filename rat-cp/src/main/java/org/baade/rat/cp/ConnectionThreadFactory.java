package org.baade.rat.cp;

import java.sql.Connection;
import java.util.concurrent.ThreadFactory;

/**
 * 连接线程
 */
public class ConnectionThreadFactory implements ThreadFactory {


    ThreadLocal<Connection> threadLocal;
    private DatabaseInfo databaseInfo;

    public ConnectionThreadFactory(DatabaseInfo databaseInfo) {
        this.threadLocal = new ThreadLocal<>();
        this.databaseInfo = databaseInfo;
    }

    @Override
    public Thread newThread(Runnable r) {
        ConnectionThread connectionThread = new ConnectionThread(r, this.databaseInfo);
        connectionThread.setDaemon(true);
        return connectionThread;
    }

}
