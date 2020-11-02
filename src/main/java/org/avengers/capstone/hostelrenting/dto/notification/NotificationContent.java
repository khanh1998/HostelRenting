package org.avengers.capstone.hostelrenting.dto.notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NotificationContent {
    private String id;
    private String title;
    private String body;
    private String clickAction;
    private String icon;
    private String action;
}