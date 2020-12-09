package org.avengers.capstone.hostelrenting.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private UUID vendorId;
    private UUID renterId;
    private String destination;
    private Map<String, String> data;
}
