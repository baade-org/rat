package org.baade.rat.core.worker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerManager {

    private static WorkerManager ourInstance = new WorkerManager();

    public static WorkerManager getInstance() {
        return ourInstance;
    }

    private Map<String, IWorker> workers;

    private WorkerManager() {
        workers = new ConcurrentHashMap<>();
    }

    public void put(IWorker worker){
        workers.put(worker.getId(), worker);
    }

    public IWorker get(String workerId){
        return workers.get(workerId);
    }


}
