package org.baade.rat.core.worker;

import org.baade.rat.core.worker.context.IResponse;
import org.baade.rat.core.worker.service.AbstractService;

public class WorldService extends AbstractService {

    public void testFlag(){
        WorkerManager.getInstance() //
                .rpc("flag", HumanService.class) //
                .listener(this::callback);

    }

    public void callback(IResponse response){

    }
}
