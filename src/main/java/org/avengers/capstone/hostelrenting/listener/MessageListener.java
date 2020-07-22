package org.avengers.capstone.hostelrenting.listener;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = Constant.Kafka.TOPIC,
            groupId = Constant.Kafka.GROUP_ID
    )
    public void listen(MessageDTO message) {
        System.out.println("sending via kafka listener..");
        template.convertAndSend("/topic/group", message);
    }
}
