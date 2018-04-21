package org.baade.rat.core.worker;

import org.baade.rat.core.context.DefaultResponse;
import org.baade.rat.core.context.IRequest;
import org.baade.rat.core.context.IResponse;
import org.baade.rat.core.service.IService;
import org.baade.rat.core.worker.anno.Service;

@Service(bind = HumanWorker.class)
public class HumanService implements IService {




    @Override
    public String getId() {
        return "HumanService";
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

//    public IResponse flag(IRequest request){
//        System.out.println("HumanService flag of request"+ " : Thread:" + Thread.currentThread());
//        return DefaultResponse.build("xxx", "xxxV", "yyyy" , "yyyV");
//
//    }

//    public void flag(){
//        System.out.println("HumanService flag of no request"+ " : Thread:" + Thread.currentThread());
////        return null;
//
//    }

//    public IResponse flag(){
//        System.out.println("HumanService flag of no request"+ " : Thread:" + Thread.currentThread());
////        return null;
//        return DefaultResponse.build("xxx2", "xxxV2", "yyyy2" , "yyyV2");
//    }


}
