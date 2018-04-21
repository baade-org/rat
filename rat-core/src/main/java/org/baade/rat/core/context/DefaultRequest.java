package org.baade.rat.core.context;

import org.baade.rat.core.cycle.AbstractLifeCycle;

import java.util.HashMap;
import java.util.Map;

public class DefaultRequest extends AbstractLifeCycle implements IRequest {

    private IParameter parameter;

    DefaultRequest(IParameter parameter) {
        super();
        this.parameter = parameter;
    }


    public static IRequest build(Object... objects){
        return new DefaultRequest(DefaultParameter.build(objects));
    }

    @Override
    public <P> P get(String key) {
        return (P)this.parameter.get(key);
    }

    @Override
    public void put(String key, Object obj) {
        this.parameter.put(key, obj);
    }

    @Override
    public IParameter getParameter() {
        return this.parameter;
    }

    @Override
    public String toString() {
        return "DefaultRequest{" +
                "parameter=" + parameter +
                ", lifeCreateTime=" + lifeCreateTime +
                '}';
    }
}
