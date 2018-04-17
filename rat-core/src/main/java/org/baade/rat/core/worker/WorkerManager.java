package org.baade.rat.core.worker;

import org.baade.rat.core.worker.context.CallbackFunction;
import org.baade.rat.core.worker.context.RPC;
import org.baade.rat.core.worker.context.RPCFunction;
import org.baade.rat.core.worker.service.IService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkerManager {

    private static WorkerManager ourInstance = new WorkerManager();

    public static WorkerManager getInstance() {
        return ourInstance;
    }

    private Map<String, Worker> workers;

    private WorkerManager() {
        workers = Collections.synchronizedMap(new HashMap<>());
    }

    public void put(Worker worker){
        workers.put(worker.getId(), worker);
    }

    public Worker get(String workerId){
        return workers.get(workerId);
    }


    public RPC rpc(RPCFunction rpcFunction, Object... objects){

        return new RPC();

    }

    public RPC rpc(String methodName, Class<? extends IService> serviceClazz, Object... objects){

        return new RPC();

    }
}
