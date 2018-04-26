package org.baade.rat.core.worker;

public interface IMaster {

    void startup();

    void add(IWorker worker);

    IWorker getWorker(String id);

    int getWorkerSize();

}
