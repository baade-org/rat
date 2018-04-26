package org.baade.rat.core.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ServiceManager {

    private static Map<Class<? extends IService>, IService> services = new ConcurrentHashMap<>();

    private ServiceManager() {
    }

    public static void put(IService service){
        services.put(service.getClass(), service);
    }

    public static <T extends IService > T get(Class<T> serviceClazz){
        return (T)services.get(serviceClazz);
    }
}
