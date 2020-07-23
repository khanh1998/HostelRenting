package org.avengers.capstone.hostelrenting.firebase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  NotificationParameter {
    SOUND("default"),
    COLOR("#FFFF00");

    @Getter
    private String value;
}
