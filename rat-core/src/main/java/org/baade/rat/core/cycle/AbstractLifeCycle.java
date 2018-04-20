package org.baade.rat.core.cycle;

public abstract class AbstractLifeCycle implements ILifeCycle {

    private long lifeCreateTime;

    public AbstractLifeCycle() {
        this.lifeCreateTime = System.currentTimeMillis();
    }

    @Override
    public long getLifeCreateTime() {
        return this.lifeCreateTime;
    }

    @Override
    public long getLifeAliveTime() {
        return System.currentTimeMillis() - this.lifeCreateTime;
    }
}
