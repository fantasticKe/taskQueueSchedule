package com.github.fantasticke.taskqueueschedule.rabbitmq.normal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author maokeluo
 * @desc
 * @create 17-12-1
 */
@Component
public class NormalReceiver {
    @RabbitListener(queues = "task")
    public void process(String task){
        System.out.println("接收到任务:"+task);
    }
}
