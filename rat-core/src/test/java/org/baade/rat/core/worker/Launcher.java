package org.baade.rat.core.worker;

import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.exception.RPCCallbackFunctionIsNull;
import org.baade.rat.core.exception.RPCMethodNameIsNull;
import org.baade.rat.core.exception.RPCServiceClassIsNull;
import org.baade.rat.core.rpc.RPCAsync;
import org.baade.rat.core.rpc.RPCSync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Launcher {

    public static void main(String[] args) throws Exception {
//        Tree<ClassModel> classModelTree = ClassScanner.doScan("org.baade");
//        List<ClassModel> classModels = classModelTree.getInstances(classModelTree.getRootNode());

        IWorker worker = new SingleWorker("humanWorker");
        WorkerManager.getInstance().put(worker);
        HumanService humanService = new HumanService();

        worker.addService(humanService);


        IWorker worldWorker = new SingleWorker("worldWorker");
        WorkerManager.getInstance().put(worldWorker);
        WorldService worldService = new WorldService();

        worldWorker.addService(worldService);

//        worldService.testRPCSyncFlag();

        worldWorker.submit(() -> {
            try {
                worldService.testRPCAsyncFlag();
            } catch (RPCMethodNameIsNull rpcMethodNameIsNull) {
                rpcMethodNameIsNull.printStackTrace();
            } catch (RPCCallbackFunctionIsNull rpcCallbackFunctionIsNull) {
                rpcCallbackFunctionIsNull.printStackTrace();
            } catch (RPCServiceClassIsNull rpcServiceClassIsNull) {
                rpcServiceClassIsNull.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }
}
