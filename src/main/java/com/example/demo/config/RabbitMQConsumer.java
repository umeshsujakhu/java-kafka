package com.example.demo.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQMessageConfig.QUEUE)
    public void consumeMessageFromQueue(Object data) {
        System.out.println("Message received from queue : " + data);
    }
}
