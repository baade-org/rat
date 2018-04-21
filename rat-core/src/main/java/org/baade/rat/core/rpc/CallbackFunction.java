package org.baade.rat.core.rpc;

import org.baade.rat.core.context.IResponse;

public interface CallbackFunction {

    void apply(IResponse response);
}
