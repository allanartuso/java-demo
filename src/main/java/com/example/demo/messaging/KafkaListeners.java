package com.example.demo.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener( topics = "myTopic", groupId = "groupId")
    void listener(String data){
    System.out.println("MyTopic received: " + data);
    }
}
