package org.baade.rat.core.exception;

import org.baade.rat.core.rpc.IRPC;

public class RPCServiceClassIsNull extends Exception {

    public RPCServiceClassIsNull(IRPC rpc) {
        super(rpc.toString());
    }
}
