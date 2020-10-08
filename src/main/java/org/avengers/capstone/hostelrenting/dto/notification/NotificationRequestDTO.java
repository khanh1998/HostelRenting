package org.avengers.capstone.hostelrenting.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.messaging.Notification;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NotificationRequestDTO implements Serializable {
    private String destination;
    @JsonProperty("notification")
    private NotificationContent content;
    private Object data;
}
