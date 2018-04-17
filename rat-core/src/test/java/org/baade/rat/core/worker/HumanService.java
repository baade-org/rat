package org.baade.rat.core.worker;

import org.baade.rat.core.worker.anno.Service;
import org.baade.rat.core.worker.context.IRequest;
import org.baade.rat.core.worker.context.IResponse;
import org.baade.rat.core.worker.service.AbstractService;

@Service(bind = HumanWorker.class)
public class HumanService extends AbstractService {


    @Override
    public void heartbeat() {

    }

    public void flag(IRequest request, IResponse response){

    }


}
