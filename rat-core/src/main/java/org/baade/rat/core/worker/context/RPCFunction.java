package org.baade.rat.core.worker.context;

public interface RPCFunction {

    void accept(IRequest request, IResponse response);
}
