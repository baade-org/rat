package org.baade.rat.core.rpc;

import org.baade.rat.core.context.DefaultRequest;
import org.baade.rat.core.context.IRequest;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.service.IService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class RPCSync extends AbstractRPC implements IRPCSync {

    @Override
    public IResponse launch() throws RPCServiceClassIsNull, RPCCallbackFunctionIsNull,
            RPCMethodNameIsNull, InterruptedException, ExecutionException, TimeoutException {
        checkSelf();
//        Future<IResponse> futureTask = WorkerManager.getInstance().get("humanWorker").submit(this);
//        return futureTask.get(getTimeout(), TimeUnit.MILLISECONDS);
//
        return null;
    }

    @Override
    public FutureTask<IResponse> getFutureTask() {
        return null;
    }

    @Override
    public boolean isSync() {
        return true;
    }


    public static RPCSync newBuilder() {
        return new RPCSync();
    }

    @Override
    public RPCSync setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    @Override
    public RPCSync setRPCMethodName(String rpcMethodName) {
        this.rpcMethodName = rpcMethodName;
        return this;
    }

    @Override
    public RPCSync setRPCClass(Class<? extends IService> serviceClazz) {
        this.serviceClazz = serviceClazz;
        return this;
    }

    @Override
    public RPCSync setRPCMethodRequestParameters(Object... requestParameters) {
        this.request = DefaultRequest.build(requestParameters);
        return this;
    }

    @Override
    public RPCSync setRPCMethodRequest(IRequest request) {
        this.request = request;
        return this;
    }

    @Override
    public String toString() {
        return "RPCSync{" +
                "timeout=" + timeout +
                ", rpcMethodName='" + rpcMethodName + '\'' +
                ", serviceClazz=" + serviceClazz +
                ", request=" + request +
                '}';
    }
}
