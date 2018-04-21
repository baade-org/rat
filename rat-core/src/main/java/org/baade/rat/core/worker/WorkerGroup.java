package org.baade.rat.core.worker;

import org.baade.rat.core.context.IContext;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.rpc.IRPC;
import org.baade.rat.core.rpc.IRPCAsync;
import org.baade.rat.core.rpc.IRPCSync;
import org.baade.rat.core.service.IService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class WorkerGroup implements IWorker{

    private String id;



    private LinkedBlockingQueue<IContext> queue;

    private ScheduledExecutorService readQueueExecutor;
    private ExecutorService runExecutor;

    private Map<String, IService> services;

    public WorkerGroup() {
        this.id = UUID.randomUUID().toString();
        this.queue = new LinkedBlockingQueue<>();
        this.services = new HashMap<>();
        init();
    }

    private void init(){
        initReadQueueExecutor();
//        initRunExecutor();
    }

    private void initReadQueueExecutor(){
//        readQueueExecutor = Executors.newSingleThreadScheduledExecutor();
//        readQueueExecutor.scheduleWithFixedDelay(() -> {
//
////            Transport transport = queue.poll();
////            if (transport != null){
////                // TODO: 2018/4/15 execute transport
////            }
//
//
//        }, 0, 10, TimeUnit.MILLISECONDS);
    }

//    private void initRunExecutor(){
//        runExecutor = Executors.newFixedThreadPool(5, new WorkerThreadFactory());
//    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void addService(IService service) {

    }

    @Override
    public void addServices(Collection<IService> services) {

    }

    @Override
    public IService getServiceById(String serviceId) {
        return null;
    }

    @Override
    public IService getServiceByClass(Class<? extends IService> serviceClass) {
        return null;
    }

    @Override
    public Collection<IService> getAllServices() {
        return null;
    }


    @Override
    public long getLifeCreateTime() {
        return 0;
    }

    @Override
    public long getLifeAliveTime() {
        return 0;
    }


    @Override
    public FutureTask<IResponse> submit(IRPCSync rpcSync) {
        return null;
    }

    @Override
    public void submit(IRPCAsync rpcAsync) {

    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return null;
    }

    @Override
    public void submit(Runnable runnable) {

    }
}
