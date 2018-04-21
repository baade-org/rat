package org.baade.rat.core.rpc;

import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.service.IService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public interface IRPCSync extends IRPC {


    IResponse launch() throws RPCServiceClassIsNull, RPCCallbackFunctionIsNull, RPCMethodNameIsNull, InterruptedException, ExecutionException, TimeoutException;

    RPCSync setRPCClass(Class<? extends IService> serviceClazz);

    FutureTask<IResponse> getFutureTask();
}
