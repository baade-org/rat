package org.baade.rat.core.worker;

import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.cycle.ILifeCycle;
import org.baade.rat.core.rpc.IRPCAsync;
import org.baade.rat.core.rpc.IRPCSync;
import org.baade.rat.core.service.IService;

import java.util.Collection;
import java.util.concurrent.*;

public interface IWorker extends ILifeCycle {

    String getId();

    void addService(IService service);

    void addServices(Collection<IService> services);

    IService getServiceById(String serviceId);

    IService getServiceByClass(Class<? extends IService> serviceClass);

    Collection<IService> getAllServices();

    <T> Future<T> submit(Callable<T> callable);

    Future<IResponse> submit(IRPCSync rpcSync);

    void submit(Runnable runnable);

    void submit(IRPCAsync rpcAsync) throws InterruptedException, ExecutionException, TimeoutException;

}
