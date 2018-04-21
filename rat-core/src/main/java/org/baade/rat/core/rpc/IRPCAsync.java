package org.baade.rat.core.rpc;

import org.baade.rat.core.context.IRequest;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.service.IService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IRPCAsync extends IRPC {

    void launch() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull, InterruptedException, ExecutionException, TimeoutException;

    RPCAsync setRPCClass(Class<? extends IService> serviceClazz);

    <T extends IRPC> T listener(CallbackFunction callbackFunction);

    CallbackFunction getListener();

    String getCallbackWorkerId();

    void setResponse(IResponse response);

    IResponse getResponse();

    boolean isCallback();

    void setCallback(boolean isCallback);

}
