package org.baade.rat.core.context;

import org.baade.rat.core.cycle.AbstractLifeCycle;

public final class DefaultResponse extends AbstractLifeCycle implements IResponse {

    private IParameter parameter;

    private DefaultResponse(IParameter parameter) {
        super();
        this.parameter = parameter;
    }

    public static IResponse build(Object... objects){
        return new DefaultResponse(DefaultParameter.build(objects));
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
    public IRequest reverse() {
        return new DefaultRequest(this.parameter);
    }

    @Override
    public String toString() {
        return "DefaultResponse{" +
                "parameter=" + parameter +
                ", lifeCreateTime=" + lifeCreateTime +
                '}';
    }
}
