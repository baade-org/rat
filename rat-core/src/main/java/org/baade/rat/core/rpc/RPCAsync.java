package org.baade.rat.core.rpc;

import org.baade.rat.core.context.DefaultRequest;
import org.baade.rat.core.context.IRequest;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.WorkerManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class RPCAsync extends AbstractRPC implements IRPCAsync {

    private boolean isCallback = false;
    private CallbackFunction callbackFunction;
    private String callbackWorkerId;
    private IResponse response;

    @Override
    protected boolean checkSelf() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull {
        super.checkSelf();
        // 检查回调方法是否配置
//        if (this.callbackFunction == null){
//            throw new RPCCallbackFunctionIsNull(this);
//        }
        return true;
    }

    @Override
    public void launch() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull, InterruptedException, ExecutionException, TimeoutException {
        checkSelf();

        WorkerManager.getInstance().get("humanWorker").submit(this);

    }

    @Override
    public CallbackFunction getListener() {
        return this.callbackFunction;
    }

    @Override
    public RPCAsync listener(CallbackFunction callbackFunction) {
        this.callbackFunction = callbackFunction;
        this.callbackWorkerId = Thread.currentThread().getName();

        return this;
    }

    @Override
    public boolean isSync() {
        return false;
    }


    public static RPCAsync newBuilder() {
        return new RPCAsync();
    }

    @Override
    public RPCAsync setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    @Override
    public RPCAsync setRPCMethodName(String rpcMethodName) {
        this.rpcMethodName = rpcMethodName;
        return this;
    }

    @Override
    public RPCAsync setRPCClass(Class<? extends IService> serviceClazz) {
        this.serviceClazz = serviceClazz;
        return this;
    }

    @Override
    public RPCAsync setRPCMethodRequestParameters(Object... requestParameters) {
        this.request = DefaultRequest.build(requestParameters);
        return this;
    }

    @Override
    public RPCAsync setRPCMethodRequest(IRequest request) {
        this.request = request;
        return this;
    }

    @Override
    public String getCallbackWorkerId() {
        return this.callbackWorkerId;
    }

    @Override
    public boolean isCallback() {
        return this.isCallback;
    }

    @Override
    public void setCallback(boolean isCallback) {
        this.isCallback = isCallback;
    }

    @Override
    public void setResponse(IResponse response) {
        this.response = response;
    }

    @Override
    public IResponse getResponse() {
        return this.response;
    }

    @Override
    public String toString() {
        return "RPCAsync{" +
                "callbackFunction=" + callbackFunction +
                ", timeout=" + timeout +
                ", rpcMethodName='" + rpcMethodName + '\'' +
                ", serviceClazz=" + serviceClazz +
                ", request=" + request +
                '}';
    }
}
