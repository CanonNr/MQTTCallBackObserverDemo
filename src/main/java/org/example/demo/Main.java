package org.example.demo;

import org.example.demo.entity.MQTTCallBackObserverService;
import org.example.demo.entity.Result;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class Main {

    private static final Result result = new Result();
    // 模拟一个cmdId
    private static final String cmdId = UUID.randomUUID().toString().replace("-", "");

    private static final MQTTCallBackObserverService service = new MQTTCallBackObserverService(result);

    public static void main(String[] args) {
        Thread callback = new Thread(() -> {
            try {
                Thread.sleep(3000);
                Result.Body body = new Result.Body(cmdId, 200, "车辆启动成功");
                System.out.println("模拟MQTT消费者收到消息：" + body);
                // 此处模拟获取到MQTT回调消息，将结果存入结果缓存区
                service.put(body.cmdId, body);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 注册MQTT回调服务
        service.register(cmdId);
        // 模拟发送消息
        System.out.println("模拟发送消息成功");
        // 模拟返回消息结果
        callback.start();
        // get结果;如果读不到数据线程会被阻塞，读到数据后自动唤醒
        Result.Body body = service.get(cmdId);
        System.out.println("get获取MQTT回调："+body.toString());
    }
}
