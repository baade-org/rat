package org.baade.rat.core.worker.service;

import org.baade.rat.core.worker.IWorker;

public abstract class AbstractService implements IService{

    private IWorker worker;

    @Override
    public void heartbeat() {
        
    }

    @Override
    public IWorker getWorker() {
        return worker;
    }
}
