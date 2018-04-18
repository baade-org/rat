package org.baade.rat.core.rpc;

import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.worker.context.IRequest;
import org.baade.rat.core.worker.service.IService;

public class RPCAsync extends AbstractRPC implements IRPCAsync {

    private CallbackFunction callbackFunction;

    @Override
    protected boolean checkSelf() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull {
        super.checkSelf();
        // 检查回调方法是否配置
        if (this.callbackFunction == null){
            throw new RPCCallbackFunctionIsNull(this);
        }
        return true;
    }

    @Override
    public void launch() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull {
        checkSelf();

    }

    @Override
    public CallbackFunction getListener() {
        return this.callbackFunction;
    }

    @Override
    public RPCAsync listener(CallbackFunction callbackFunction) {
        this.callbackFunction = callbackFunction;
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
        return this;
    }

    @Override
    public RPCAsync setRPCMethodRequest(IRequest request) {
        this.request = request;
        return this;
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
