package org.baade.rat.core.exception;

import org.baade.rat.core.rpc.IRPC;

public class RPCCallbackFunctionIsNull extends Exception {

    public RPCCallbackFunctionIsNull(IRPC rpc) {
        super(rpc.toString());
    }
}
