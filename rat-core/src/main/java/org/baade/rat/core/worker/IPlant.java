package org.baade.rat.core.worker;

public interface IPlant {

    void startup();

    void add(IWorker worker);

    IWorker getWorker(String id);

    int getWorkerSize();

}
