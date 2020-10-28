package org.avengers.capstone.hostelrenting.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class NotificationContent {
    private String title;
    private String body;
    private String clickAction;
    private String icon;
}