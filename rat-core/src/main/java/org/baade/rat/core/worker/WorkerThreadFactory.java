package org.baade.rat.core.worker;

import java.util.concurrent.ThreadFactory;

public class WorkerThreadFactory implements ThreadFactory {

    private IWorker worker;

    public WorkerThreadFactory(IWorker worker) {
        this.worker = worker;
    }

    @Override
    public Thread newThread(Runnable r) {
        WorkerThread thread = new WorkerThread(r, this.worker);
//        thread.setDaemon(true);
        return thread;
    }
}
