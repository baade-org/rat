package org.baade.rat.core.service;

import org.baade.rat.core.cycle.AbstractLifeCycle;
import org.baade.rat.core.worker.IWorker;

import java.util.UUID;

public class AbstractService extends AbstractLifeCycle implements IService {

    private String id;
    private IWorker worker;

    public AbstractService(IWorker worker) {
        this.id = UUID.randomUUID().toString();
        this.worker = worker;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public IWorker getWorker() {
        return this.worker;
    }

    @Override
    public void bindWorker(IWorker worker) {
        this.worker = worker;
    }
}
