package org.baade.rat.core.worker.context;

import java.util.Map;

public interface IRequest {

    Map<String, Object> getAttributes();

    <T> T getAttribute(String key);

    IContext getContext();

    RPCFunction getRPCFunction();
}
