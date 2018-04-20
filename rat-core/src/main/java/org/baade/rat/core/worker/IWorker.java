package org.baade.rat.core.worker;

import org.baade.rat.core.cycle.ILifeCycle;
import org.baade.rat.core.rpc.IRPC;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.context.IResponse;

import java.util.Collection;
import java.util.concurrent.FutureTask;

public interface IWorker extends ILifeCycle {

    String getId();

    void addService(IService service);

    void addServices(Collection<IService> services);

    IService getServiceById(String serviceId);

    IService getServiceByClass(Class<? extends IService> serviceClass);

    Collection<IService> getAllServices();


    FutureTask<IResponse> runRPC(IRPC rpc);
}
