package org.avengers.capstone.hostelrenting.dto.notification;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscriptionRequestDTO {
    String topicName;
    List<String> tokens;
}
