package com.github.fantasticke.taskqueueschedule.rabbitmq.normal;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maokeluo
 * @desc
 * @create 17-11-30
 */
@Configuration
public class NormalRabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue("task");
    }
}
