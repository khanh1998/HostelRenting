package org.avengers.capstone.hostelrenting.dto.notification;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDTO {
    String topicName;
    List<String> tokens;
}
