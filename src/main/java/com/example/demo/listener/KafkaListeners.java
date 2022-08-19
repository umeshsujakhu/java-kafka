package com.example.demo.listener;

import com.example.demo.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class KafkaListeners {

    @KafkaListener(topics = "Kafka_Example", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Listener received String message : " + message);
    }

    @KafkaListener(topics = "Kafka_Example_json", groupId = "group_json", containerFactory = "studentKafkaListenerFactory")
    public void consumeJson(Student student) {
        System.out.println("Listener received JSON message : " + student);
    }
}
