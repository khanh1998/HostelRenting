package org.avengers.capstone.hostelrenting.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Long vendorId;
    private Long renterId;
    private String destination;
    private Map<String, String> data;
}
