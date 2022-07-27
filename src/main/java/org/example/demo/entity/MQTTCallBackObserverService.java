package org.example.demo.entity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

public class MQTTCallBackObserverService {

    private final Result result;

    private final ConcurrentHashMap<String,Thread> observerMap = new ConcurrentHashMap<>();

    public void register(String uuid){
        observerMap.put(uuid,Thread.currentThread());
    }

    public MQTTCallBackObserverService(Result result){
        this.result = result;
    }

    public void put(String uuid, Result.Body body){
        result.put(uuid,body);
        notifyObservers(uuid);
    }

    private void notifyObservers(String uuid) {
        Thread thread = observerMap.get(uuid);
        LockSupport.unpark(thread);
    }

    public Result.Body get(String uuid){
        // 如果uuid对应的结果不存在，则阻塞等待
        if (!result.containsKey(uuid)) {
            LockSupport.park(Thread.currentThread());
        }
        return result.get(uuid);
    }
}
