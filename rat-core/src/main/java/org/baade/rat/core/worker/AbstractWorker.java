package org.baade.rat.core.worker;

import org.baade.rat.core.cycle.AbstractLifeCycle;
import org.baade.rat.core.rpc.IRPC;
import org.baade.rat.core.rpc.RPCSync;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.context.IContext;
import org.baade.rat.core.worker.context.IRequest;
import org.baade.rat.core.worker.context.IResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

public abstract class AbstractWorker extends AbstractLifeCycle implements IWorker {

    private static final Logger log = LoggerFactory.getLogger(AbstractWorker.class);

    private String id;

    private LinkedBlockingQueue<IContext> queue;

    private ScheduledExecutorService executor;

    protected Map<String, IService> services;

    public AbstractWorker() {
        super();
        this.id = UUID.randomUUID().toString();
        this.queue = new LinkedBlockingQueue<>();
        this.services = new ConcurrentHashMap<>();

        init();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addService(IService service) {
        this.services.put(service.getId(), service);
    }

    @Override
    public void addServices(Collection<IService> services) {
        services.stream().forEach(s -> addService(s));
    }

    @Override
    public IService getServiceById(String serviceId) {
        return this.services.get(serviceId);
    }

    @Override
    public IService getServiceByClass(Class<? extends IService> serviceClass) {
        Optional<IService> serviceOptional = this.services.values().stream().filter(s -> s.getClass() == serviceClass).findAny();
        if (serviceOptional.isPresent()){
            return serviceOptional.get();
        }
        return null;
    }

    @Override
    public Collection<IService> getAllServices() {
        return this.services.values();
    }

    private void init() {
        executor = Executors.newSingleThreadScheduledExecutor(new WorkerThreadFactory());
        executor.scheduleAtFixedRate(() -> {
            try {
//                Transport transport = queue.poll();
//                if (transport != null){
//                    // TODO: 2018/4/14 execute transport
////                    transportHandler(transport);
//
//                }
//                heartbeat();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Worker run ERROR, worker's id =[{}].", getId(), e);

            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

//    public void heartbeat(){
//        services.values().forEach(s -> s.heartbeat());
//    }


    @Override
    public FutureTask<IResponse> runRPC(IRPC rpc) {
        Callable<IResponse> callable = () -> {
            if (rpc instanceof RPCSync){
                RPCSync rpcSync = (RPCSync)rpc;
                Class<? extends IService> rpcServiceClass = rpcSync.getRPCClass();
                IService service = this.getServiceByClass(rpcServiceClass);

                if (service != null){
                    String rpcMethodName = rpcSync.getRPCMethodName();
                    IRequest rpcRequest = rpcSync.getRPCMethodRequestParameters();
                    Method method = null;
                    Object invokeResult = null;
                    if (rpcRequest != null){
                        method = rpcServiceClass.getDeclaredMethod(rpcMethodName, IRequest.class);
                        invokeResult = method.invoke(service, rpcRequest);
                    } else {
                        method = rpcServiceClass.getDeclaredMethod(rpcMethodName);
                        invokeResult = method.invoke(service);
                    }

                    if (invokeResult instanceof IResponse){
                        return (IResponse)invokeResult;
                    }

                    return null;

                }
            }
            return null;
        };

        FutureTask<IResponse> futureTask = new FutureTask<>(callable);
        executor.submit(futureTask);

        return futureTask;


    }
}
