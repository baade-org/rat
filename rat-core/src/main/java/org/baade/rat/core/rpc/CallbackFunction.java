package org.baade.rat.core.rpc;

import org.baade.rat.core.worker.context.IResponse;

public interface CallbackFunction {

    void apply(IResponse response);
}
