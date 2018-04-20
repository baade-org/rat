package org.baade.rat.core.service;

import org.baade.rat.core.cycle.ILifeCycle;
import org.baade.rat.core.worker.IWorker;

/**
 * 线程上的服务类接口
 */
public interface IService extends ILifeCycle {

    String getId();

    IWorker getWorker();
}
