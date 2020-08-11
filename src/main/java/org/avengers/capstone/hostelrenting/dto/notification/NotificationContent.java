package org.avengers.capstone.hostelrenting.dto.notification;

import lombok.Getter;

@Getter
public class NotificationContent {
    private String title;
    private String body;
    private String clickAction;
    private String icon;
}