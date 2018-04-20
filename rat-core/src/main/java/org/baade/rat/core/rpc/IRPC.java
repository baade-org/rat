package org.baade.rat.core.rpc;

import org.baade.rat.core.cycle.ILifeCycle;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.context.IRequest;

public interface IRPC extends ILifeCycle {


    boolean isSync();

    int getTimeout();

    String getRPCMethodName();

    Class<? extends IService> getRPCClass();

    IRequest getRPCMethodRequestParameters();


//    <T extends IRPC> T newBuilder();

    <T extends IRPC> T setTimeout(int timeout);

    <T extends IRPC> T setRPCMethodName(String rpcMethodName);

//    <T extends IRPC> T setRPCClass(Class<? extends IService> serviceClazz);

    <T extends IRPC> T setRPCMethodRequestParameters(Object... requestParameters);

    <T extends IRPC> T setRPCMethodRequest(IRequest request);




}
