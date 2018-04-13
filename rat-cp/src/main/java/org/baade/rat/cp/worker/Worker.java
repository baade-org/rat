package org.baade.rat.cp.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Worker {

    private LinkedBlockingQueue<Callback> tasks;
    private long id;

    public Worker() {
        tasks = new LinkedBlockingQueue<>();
        init();
    }

    public long getId() {
        return id;
    }

    private void init() {
        WorkerThreadFactory workerThreadFactory = new WorkerThreadFactory();
        this.id = workerThreadFactory.getId();
        Executors.newSingleThreadScheduledExecutor(workerThreadFactory).scheduleAtFixedRate(() -> {
            try {
                Callback callback = tasks.take();
                Consumer<Object> consumer = callback.getConsumer();
                if (consumer != null) {
                    consumer.accept(callback.getObject());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void put(Callback callback) {
        this.tasks.add(callback);
    }


}
