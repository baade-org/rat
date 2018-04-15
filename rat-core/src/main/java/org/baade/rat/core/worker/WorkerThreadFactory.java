package org.baade.rat.core.worker;

import java.util.concurrent.ThreadFactory;

public class WorkerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = newThread(r);
        thread.setDaemon(true);
        return thread;
    }
}
