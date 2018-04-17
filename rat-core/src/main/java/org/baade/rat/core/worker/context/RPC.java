package org.baade.rat.core.worker.context;

import org.baade.rat.core.worker.service.IService;

import java.util.Map;

public class RPC {

    private String rpcMethodName;
    private Class<? extends IService> serviceClazz;
    private Map<String, Object> attributes;

    public RPC(String rpcMethodName, Class<? extends IService> serviceClazz) {
        this(rpcMethodName, serviceClazz, null);
    }

//    public RPC(String rpcMethodName, Class<? extends IService> serviceClazz, Object... attributes) {
//
//    }

    public RPC(String rpcMethodName, Class<? extends IService> serviceClazz, Map<String, Object> attributes) {
        this.rpcMethodName = rpcMethodName;
        this.serviceClazz = serviceClazz;
        this.attributes = attributes;
    }

    public void listener(CallbackFunction callbackFunction, Object... contexts){

    }

    public String getRpcMethodName() {
        return rpcMethodName;
    }

    public void setRpcMethodName(String rpcMethodName) {
        this.rpcMethodName = rpcMethodName;
    }

    public Class<? extends IService> getServiceClazz() {
        return serviceClazz;
    }

    public void setServiceClazz(Class<? extends IService> serviceClazz) {
        this.serviceClazz = serviceClazz;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "RPC{" +
                "rpcMethodName='" + rpcMethodName + '\'' +
                ", serviceClazz=" + serviceClazz +
                ", attributes=" + attributes +
                '}';
    }
}
