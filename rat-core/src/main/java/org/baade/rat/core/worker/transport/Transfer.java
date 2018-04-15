package org.baade.rat.core.worker.transport;

import java.util.Map;
import java.util.function.Consumer;

public class Transfer {

    private String workerId;

    private Consumer<Object> method;

    private Map<String, Object> param;

    public Consumer<Object> getMethod() {
        return method;
    }

    public void setMethod(Consumer<Object> method) {
        this.method = method;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
