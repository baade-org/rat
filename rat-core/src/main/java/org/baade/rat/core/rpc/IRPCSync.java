package org.baade.rat.core.rpc;

import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.worker.context.IResponse;
import org.baade.rat.core.worker.service.IService;

public interface IRPCSync extends IRPC {


    IResponse launch() throws RPCServiceClassIsNull, RPCCallbackFunctionIsNull, RPCMethodNameIsNull;

    RPCSync setRPCClass(Class<? extends IService> serviceClazz);

}
