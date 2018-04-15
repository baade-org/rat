package org.baade.rat.cp.worker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

public class WorkerThreadFactory implements ThreadFactory {

    private LinkedBlockingQueue<Object> tasks;
    private WorkerThread workerThread;

    public WorkerThreadFactory() {
        tasks = new LinkedBlockingQueue<>();

    }

    public long getId(){
        return workerThread.getId();
    }

    @Override
    public Thread newThread(Runnable r) {
        workerThread = new WorkerThread(r);
        return workerThread;
    }
}
