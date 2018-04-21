package org.baade.rat.core.context;

import org.baade.rat.core.cycle.AbstractLifeCycle;

import java.util.HashMap;
import java.util.Map;

public class DefaultParameter extends AbstractLifeCycle implements IParameter {

    private Map<String, Object> params;

    public DefaultParameter() {
        super();
        params = new HashMap<>();
    }

    public static IParameter build(Object... objects){
        if (objects == null || objects.length == 0){
            return null;
        }
        IParameter parameter = new DefaultParameter();
        for (int i = 0; i < objects.length; i += 2) {
            String key = String.valueOf(objects[i]);
            Object obj = objects[i + 1];
            parameter.put(key, obj);
        }
        return parameter;

    }

    @Override
    public <P> P get(String key) {
        return (P)params.get(key);
    }

    @Override
    public void put(String key, Object obj) {
        params.put(key, obj);
    }

    @Override
    public String toString() {
        return "DefaultParameter{" +
                "params=" + params +
                ", lifeCreateTime=" + lifeCreateTime +
                '}';
    }
}
