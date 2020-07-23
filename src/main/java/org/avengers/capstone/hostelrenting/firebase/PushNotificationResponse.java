package org.avengers.capstone.hostelrenting.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationResponse {

    private int status;
    private String message;
}
