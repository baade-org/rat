package org.baade.rat.core.rpc;

import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.worker.service.IService;

public interface IRPCAsync extends IRPC {

    void launch() throws RPCCallbackFunctionIsNull, RPCServiceClassIsNull, RPCMethodNameIsNull;

    RPCAsync setRPCClass(Class<? extends IService> serviceClazz);

    <T extends IRPC> T listener(CallbackFunction callbackFunction);

    CallbackFunction getListener();

}
