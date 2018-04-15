package org.baade.rat.core.worker;

import org.baade.rat.core.worker.transport.Transfer;
import org.baade.rat.core.worker.transport.Transport;
import org.baade.rat.core.worker.transport.TransportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Worker extends AbstractWorker {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);


    private String id;

    private LinkedBlockingQueue<Transport> queue;

    private ScheduledExecutorService executor;

    private Map<String, IService> services;

    public Worker(){
        this.id = UUID.randomUUID().toString();
        this.queue = new LinkedBlockingQueue<>();
        this.services = new HashMap<>();

        init();
    }



    public void init(){
        executor = Executors.newSingleThreadScheduledExecutor(new WorkerThreadFactory());
        executor.scheduleAtFixedRate(() -> {
            try {
                Transport transport = queue.poll();
                if (transport != null){
                    // TODO: 2018/4/14 execute transport
                    transportHandler(transport);

                }
                heartbeat();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Worker run ERROR, worker's id =[{}].", getId(), e);

            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }


    private void transportHandler(Transport transport){
//        Transport transport1 = TransportManager.getInstance().get(transport.getId());
        Transfer receiver = transport.getReceiver();
        String receiverWorkerId = receiver.getWorkerId();

        // TODO: 2018/4/15 调用本线程的方法
        if (this.getId().equals(receiverWorkerId)){

            
            return;
        }

        // TODO: 2018/4/15 调用其他线程的方法
        if (!transport.isCallback()){
            Worker worker = WorkerManager.getInstance().get(receiverWorkerId);
            return;
        }

        // TODO: 2018/4/15 其他线程执行完成后，回调到本线程
        Transfer sender = transport.getSender();
        String senderWorkerId = sender.getWorkerId();
        if (this.getId().equals(senderWorkerId)){


            return;
        }

        // TODO: 2018/4/15 投递错误，找不到对应的执行线程
    }
}
