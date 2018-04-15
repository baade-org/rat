package org.baade.rat.core.worker;

import org.baade.rat.core.worker.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractWorker implements IWorker{

    private static final Logger log = LoggerFactory.getLogger(AbstractWorker.class);

    private String id;

    private LinkedBlockingQueue<Transport> queue;

    private ScheduledExecutorService executor;

    private Map<String, IService> services;

    public AbstractWorker(){
        this.id = UUID.randomUUID().toString();
        this.queue = new LinkedBlockingQueue<>();
        this.services = new HashMap<>();

        init();
    }

    public String getId() {
        return id;
    }



    private void init(){
        executor = Executors.newSingleThreadScheduledExecutor(new WorkerThreadFactory());
        executor.scheduleAtFixedRate(() -> {
            try {
                Transport transport = queue.poll();
                if (transport != null){
                    // TODO: 2018/4/14 execute transport
//                    transportHandler(transport);

                }
                heartbeat();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Worker run ERROR, worker's id =[{}].", getId(), e);

            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void heartbeat(){
        services.values().forEach(s -> s.heartbeat());
    }
}
