package org.baade.rat.core.exception;

import org.baade.rat.core.rpc.IRPC;

public class RPCMethodNameIsNull extends Exception {

    public RPCMethodNameIsNull(IRPC rpc) {
        super(rpc.toString());
    }
}
