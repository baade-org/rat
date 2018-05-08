package org.baade.rat.core.rpc;

public class RPC {

    private boolean sync;

    private String targetWorkerId;


    private String callbackWorkerId;


    public static RPC newBuilder(){
        return new RPC();
    }

    public RPC build(){
        return this;
    }


    public RPC setSync(boolean isSync){
        this.sync = isSync;
        return this;
    }

    public boolean isSync() {
        return sync;
    }

    public String getTargetWorkerId() {
        return targetWorkerId;
    }

    public RPC setTargetWorkerId(String targetWorkerId) {
        this.targetWorkerId = targetWorkerId;
        return this;
    }

    public String getCallbackWorkerId() {
        return callbackWorkerId;
    }

    public RPC setCallbackWorkerId(String callbackWorkerId) {
        this.callbackWorkerId = callbackWorkerId;
        return this;
    }


}
