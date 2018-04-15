package org.baade.rat.core.worker;

import org.baade.rat.core.worker.transport.Transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class WorkerGroup implements IWorker{

    private String id;



    private LinkedBlockingQueue<Transport> queue;

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
        initRunExecutor();
    }

    private void initReadQueueExecutor(){
        readQueueExecutor = Executors.newSingleThreadScheduledExecutor();
        readQueueExecutor.scheduleWithFixedDelay(() -> {

            Transport transport = queue.poll();
            if (transport != null){
                // TODO: 2018/4/15 execute transport 
            }


        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void initRunExecutor(){
        runExecutor = Executors.newFixedThreadPool(5, new WorkerThreadFactory());
    }


    @Override
    public String getId() {
        return this.id;
    }


}
