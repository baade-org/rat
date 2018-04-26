package org.baade.rat.core.worker;

import org.baade.rat.core.cycle.ILifeCycle;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IWorker extends ILifeCycle {

    String getId();

//    void bindService(IService service);
//
//    void bindServices(Collection<IService> services);
//
//    IService getServiceById(String serviceId);
//
//    IService getServiceByClass(Class<? extends IService> serviceClass);
//
//    Collection<IService> getAllServices();
//
//    <T> Future<T> submit(Callable<T> callable);
//
//    Future<IResponse> submit(IRPCSync rpcSync);
//
//    void submit(Runnable runnable);
//
//    void submit(IRPCAsync rpcAsync) throws InterruptedException, ExecutionException, TimeoutException;



    <T> void async(Consumer<T> consumer, T t, long timeout);

    <T> void async(Consumer<T> consumer, T t);


    <T, R> void async(Function<T, R> function, T t, IWorker callbackWorker, Consumer<R> callbackConsumer);

    <T, R> void async(Function<T, R> function, T t, long timeout, IWorker callbackWorker, Consumer<R> callbackConsumer);


    <T> void sync(Consumer<T> consumer, T t);

    <T> void sync(Consumer<T> consumer, T t, long timeout);


    <T, R> R sync(Function<T, R> function, T t);

    <T, R> R sync(Function<T, R> function, T t, long timeout);

}
