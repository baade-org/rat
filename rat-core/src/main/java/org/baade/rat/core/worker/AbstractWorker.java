package org.baade.rat.core.worker;

import org.baade.rat.core.Constants;
import org.baade.rat.core.cycle.AbstractLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractWorker extends AbstractLifeCycle implements IWorker {

    private static final Logger log = LoggerFactory.getLogger(AbstractWorker.class);


    protected String id;

//    private LinkedBlockingQueue<IContext> queue;

    protected ExecutorService executorService;

//    protected Map<String, IService> services;

    public AbstractWorker() {
        super();
        this.id = UUID.randomUUID().toString();
//        this.queue = new LinkedBlockingQueue<>();
//        this.services = new ConcurrentHashMap<>();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public String getId() {
        return id;
    }

//    @Override
//    public void bindService(IService service) {
//        this.services.put(service.getId(), service);
//        service.bindWorker(this);
//    }
//
//    @Override
//    public void bindServices(Collection<IService> services) {
//        services.stream().forEach(s -> bindService(s));
//    }
//
//    @Override
//    public IService getServiceById(String serviceId) {
//        return this.services.get(serviceId);
//    }
//
//    @Override
//    public IService getServiceByClass(Class<? extends IService> serviceClass) {
//        Optional<IService> serviceOptional = this.services.values().stream().filter(s -> s.getClass() == serviceClass).findAny();
//        if (serviceOptional.isPresent()){
//            return serviceOptional.get();
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<IService> getAllServices() {
//        return this.services.values();
//    }
//
//
//
//    @Override
//    public Future<IResponse> submit(IRPCSync rpcSync) {
//        Callable<IResponse> callable = () -> {
//            IService service = getService(rpcSync);
//            if (service == null){
//                return null;
//            }
//            return invoke(rpcSync, service);
//        };
//        return submit(callable);
//    }
//
//    private IService getService(IRPC rpc){
//        Class<? extends IService> rpcServiceClass = rpc.getRPCClass();
//        IService service = this.getServiceByClass(rpcServiceClass);
//        return service;
//    }
//
//    private IResponse invoke(IRPC rpc, IService service) throws NoSuchMethodException,
//            InvocationTargetException, IllegalAccessException {
//        String rpcMethodName = rpc.getRPCMethodName();
//        IRequest rpcRequest = rpc.getRequest();
//        Method method = null;
//        Object invokeResult = null;
//        if (rpcRequest != null){
//            method = service.getClass().getDeclaredMethod(rpcMethodName, IRequest.class);
//            invokeResult = method.invoke(service, rpcRequest);
//        } else {
//            method = service.getClass().getDeclaredMethod(rpcMethodName);
//            invokeResult = method.invoke(service);
//        }
//
//        if (invokeResult instanceof IResponse){
//            return (IResponse)invokeResult;
//        }
//
//        return null;
//
//    }
//
//
//
//    @Override
//    public void submit(IRPCAsync rpcAsync) throws InterruptedException, ExecutionException, TimeoutException {
//        Runnable runnable = () -> {
//            boolean isCallback = rpcAsync.isCallback();
//            if (isCallback){
//                rpcAsync.getListener().apply(rpcAsync.getResponse());
//                return;
//            }
//
//            IService service = getService(rpcAsync);
//            if (service == null){
//                return;
//            }
//
//            try {
//                IResponse response = invoke(rpcAsync, service);
//                CallbackFunction listener = rpcAsync.getListener();
//                if (listener != null){
//                    rpcAsync.setCallback(true);
//                    rpcAsync.setResponse(response);
//                    String callbackWorkerId = rpcAsync.getCallbackWorkerId();
//                    WorkerManager.getInstance().get(callbackWorkerId).submit(rpcAsync);
//                }
//
//
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        };
//        submit(runnable);
//    }
//
//    @Override
//    public void submit(Runnable runnable) {
//        this.executorService.submit(runnable);
//    }
//
//    @Override
//    public <T> Future<T> submit(Callable<T> callable) {
//        return this.executorService.submit(callable);
//    }




    private <R> R submitSync(Callable<R> callable, long timeout){
        try {
            Future<R> future = executorService.submit(callable);
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("", e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("", e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            log.error("", e);
        }
        return null;
    }

    private void executeAsync(Runnable runnable, long timeout){
        executorService.execute(runnable);
    }



    @Override
    public <T> void async(Consumer<T> consumer, T t) {
        async(consumer, t, Constants.RPC_DEFAULT_TIMEOUT);
    }

    @Override
    public <T, R> void async(Function<T, R> function, T t, IWorker callbackWorker, Consumer<R> callbackConsumer) {
        async(function, t, Constants.RPC_DEFAULT_TIMEOUT, callbackWorker, callbackConsumer);
    }

    @Override
    public <T> void async(Consumer<T> consumer, T t, long timeout) {
        executeAsync(() -> consumer.accept(t), timeout);

    }

    @Override
    public <T, R> void async(Function<T, R> function, T t, long timeout, IWorker callbackWorker, Consumer<R> callbackConsumer) {
        executeAsync(() -> {
            R result = function.apply(t);
            callbackWorker.async(callbackConsumer, result, timeout);
        }, timeout);
    }


    @Override
    public <T> void sync(Consumer<T> consumer, T t) {
        sync(consumer, t, Constants.RPC_DEFAULT_TIMEOUT);
    }

    @Override
    public <T, R> R sync(Function<T, R> function, T t) {
        return sync(function, t, Constants.RPC_DEFAULT_TIMEOUT);
    }

    @Override
    public <T> void sync(Consumer<T> consumer, T t, long timeout) {
        Callable<Object> callable = () -> {
            consumer.accept(t);
            return null;
        };

        submitSync(callable, timeout);
    }

    @Override
    public <T, R> R sync(Function<T, R> function, T t, long timeout) {
        Callable<R> callable = () -> function.apply(t);
        return submitSync(callable, timeout);
    }
}
