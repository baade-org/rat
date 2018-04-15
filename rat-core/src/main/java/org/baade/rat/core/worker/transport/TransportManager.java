package org.baade.rat.core.worker.transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransportManager {

    private static TransportManager ourInstance = new TransportManager();

    public static TransportManager getInstance() {
        return ourInstance;
    }

    private Map<String, Transport> transports;

    private TransportManager() {
        transports = Collections.synchronizedMap(new HashMap<>());
    }

    public void put(Transport transport){
        transports.put(transport.getId(), transport);
    }

    public Transport get(String workerId){
        return transports.get(workerId);
    }
}
