package org.baade.rat.cp.worker;

import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThread extends Thread{

    private LinkedBlockingQueue<Object> tasks;

    public WorkerThread(Runnable r) {
        super(r);
        WorkerManager.getInstance().put(this);
        tasks = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        super.run();
    }
}
