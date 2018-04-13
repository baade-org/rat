package org.baade.rat.cp.worker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkerManager {

    private static WorkerManager ourInstance = new WorkerManager();

    public static WorkerManager getInstance() {
        return ourInstance;
    }

    private Map<Long, Worker> workers;

    private WorkerManager() {
        workers = Collections.synchronizedMap(new HashMap<>());
    }

    public void put(Worker worker){
        workers.put(worker.getId(), worker);
    }

    public Worker get(long workerId){
        return workers.get(workerId);
    }

}
