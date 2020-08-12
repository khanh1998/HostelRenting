package org.avengers.capstone.hostelrenting.firebase;

import org.avengers.capstone.hostelrenting.dto.NotificationRequestDTO;
import org.avengers.capstone.hostelrenting.dto.SubscriptionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class PushNotificationController {
    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        firebaseService.subscribeToTopic(subscriptionRequestDTO);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDTO subscriptionRequestDTO) {
        firebaseService.unsubscribeFromTopic(subscriptionRequestDTO);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return firebaseService.sendPnsToDevice(notificationRequestDTO);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return firebaseService.sendPnsToTopic(notificationRequestDTO);
    }
}
