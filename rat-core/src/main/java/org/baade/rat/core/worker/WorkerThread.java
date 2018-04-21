package org.baade.rat.core.worker;

public class WorkerThread extends Thread {

    private static final ThreadLocal<IWorker> threadLocal = new ThreadLocal();

    public WorkerThread(Runnable target, IWorker worker) {
        super(target, worker.getId());

        threadLocal.set(worker);
    }


}
