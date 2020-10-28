package org.avengers.capstone.hostelrenting.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
public class NotificationRequest {
    private String destination;
    @JsonProperty("notification")
    private NotificationContent content;
    private Map<String, String> data;
}
