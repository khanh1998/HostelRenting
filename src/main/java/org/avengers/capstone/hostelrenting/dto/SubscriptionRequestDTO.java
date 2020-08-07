package org.avengers.capstone.hostelrenting.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDTO {
    String topicName;
    List<String> tokens;
}
