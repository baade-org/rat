package org.baade.rat.cp.worker;

import java.util.function.Consumer;

public class Callback {


    private Consumer<Object> consumer;
    private Object object;

    public Callback(Consumer<Object> consumer, Object object) {
        this.consumer = consumer;
        this.object = object;
    }

    public Consumer<Object> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Object> consumer) {
        this.consumer = consumer;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
