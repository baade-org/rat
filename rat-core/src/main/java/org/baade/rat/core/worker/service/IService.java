package org.baade.rat.core.worker.service;

import org.baade.rat.core.worker.IWorker;

public interface IService {

    void heartbeat();

    IWorker getWorker();
}
