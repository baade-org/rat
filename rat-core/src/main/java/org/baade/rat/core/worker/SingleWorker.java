package org.baade.rat.core.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class SingleWorker extends AbstractWorker implements IWorker {
    private static final Logger log = LoggerFactory.getLogger(SingleWorker.class);


    public SingleWorker() {
        super();
    }

    public SingleWorker(String id) {
        this();
        this.id = id;
        this.executorService = Executors.newSingleThreadScheduledExecutor(new WorkerThreadFactory(this));
    }

}
