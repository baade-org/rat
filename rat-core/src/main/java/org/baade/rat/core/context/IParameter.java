package org.baade.rat.core.context;

import org.baade.rat.core.cycle.ILifeCycle;

public interface IParameter extends ILifeCycle {

    <P> P get(String key);

    void put(String key, Object obj);

}
