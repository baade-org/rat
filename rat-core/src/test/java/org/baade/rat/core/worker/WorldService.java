package org.baade.rat.core.worker;

import org.baade.rat.core.rpc.RPCSync;
import org.baade.rat.core.worker.anno.Service;
import org.baade.rat.core.worker.context.IResponse;
import org.baade.rat.core.worker.service.AbstractService;

@Service(bind = WorldWorker.class)
public class WorldService extends AbstractService {

    public void testFlag() throws Exception{
//        WorkerManager.getInstance() //
//                .rpc("flag", HumanService.class) //
//                .listener(this::callback);


        Object launchResult = RPCSync.newBuilder() //
                .setRPCMethodName("flag") //
                .setRPCClass(HumanService.class) //
                .setRPCMethodRequestParameters("aaa", "aaaValue", "bbb", "bbbValue") //
                .launch();

    }

    public void callback(IResponse response){

    }
}
