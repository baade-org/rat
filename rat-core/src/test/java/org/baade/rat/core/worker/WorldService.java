package org.baade.rat.core.worker;

import org.baade.rat.core.context.IRequest;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.rpc.RPCAsync;
import org.baade.rat.core.rpc.RPCSync;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.anno.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service(bind = WorldWorker.class)
public class WorldService implements IService {


    @Override
    public String getId() {
        return "WorldService";
    }

    @Override
    public IWorker getWorker() {
        return null;
    }

    @Override
    public long getLifeCreateTime() {
        return 0;
    }

    @Override
    public long getLifeAliveTime() {
        return 0;
    }

    public void testRPCSyncFlag() throws RPCMethodNameIsNull, InterruptedException, RPCServiceClassIsNull, ExecutionException, TimeoutException, RPCCallbackFunctionIsNull {

        IResponse response = RPCSync.newBuilder() //
                .setRPCMethodName("flag") //
                .setRPCClass(HumanService.class) //
                .setRPCMethodRequestParameters("aaa", "aaaValue", "bbb", "bbbValue") //
                .launch();

        System.out.println("RPCSync result: " + response + " : Thread:" + Thread.currentThread());
    }

    public void testRPCAsyncFlag() throws RPCMethodNameIsNull, RPCCallbackFunctionIsNull, RPCServiceClassIsNull, InterruptedException, ExecutionException, TimeoutException {

        RPCAsync.newBuilder()
                .setRPCMethodName("flag") //
                .setRPCClass(HumanService.class) //
                .setRPCMethodRequestParameters("aaa", "aaaValue", "bbb", "bbbValue") //
                .listener(this::asyncCallback) //
                .launch();

//        this.getWorker().submit(rpcAsync);
        System.out.println("RPCAsync aaa: 111111111111"+ " : Thread:" + Thread.currentThread());


    }

    public void asyncCallback(IResponse response){
        System.out.println("RPCAsync asyncCallback result: " + response + " : Thread:" + Thread.currentThread());
    }
}
