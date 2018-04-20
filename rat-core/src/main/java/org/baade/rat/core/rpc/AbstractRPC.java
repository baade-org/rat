package org.baade.rat.core.rpc;

import org.baade.rat.core.Constants;
import org.baade.rat.core.cycle.AbstractLifeCycle;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.utils.StringUtils;
import org.baade.rat.core.worker.context.IRequest;

public abstract class AbstractRPC extends AbstractLifeCycle implements IRPC {

    protected int timeout = Constants.RPC_DEFAULT_TIMEOUT;
    protected String rpcMethodName;
    protected Class<? extends IService> serviceClazz;
    protected IRequest request;


    public AbstractRPC() {
        super();
    }

    protected boolean checkSelf() throws RPCCallbackFunctionIsNull, RPCMethodNameIsNull, RPCServiceClassIsNull {
        if (StringUtils.isBlank(rpcMethodName)){
            throw new RPCMethodNameIsNull(this);
        }
        if (serviceClazz == null){
            throw new RPCServiceClassIsNull(this);
        }
        return true;
    }


    @Override
    public int getTimeout() {
        return this.timeout;
    }

    @Override
    public String getRPCMethodName() {
        return this.rpcMethodName;
    }

    @Override
    public Class<? extends IService> getRPCClass() {
        return this.serviceClazz;
    }

    @Override
    public IRequest getRPCMethodRequestParameters() {
        return this.request;
    }
}
