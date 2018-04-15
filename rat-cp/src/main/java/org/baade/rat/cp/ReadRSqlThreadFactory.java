package org.baade.rat.cp;

import java.util.concurrent.ThreadFactory;

/**
 * 读线程
 */
public class ReadRSqlThreadFactory implements ThreadFactory {



    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }

}
