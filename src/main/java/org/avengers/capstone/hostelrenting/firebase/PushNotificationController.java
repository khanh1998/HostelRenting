package org.avengers.capstone.hostelrenting.firebase;

import org.avengers.capstone.hostelrenting.dto.NotificationRequestDTO;
import org.avengers.capstone.hostelrenting.dto.SubscriptionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class PushNotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        notificationService.subscribeToTopic(subscriptionRequestDTO);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDTO subscriptionRequestDTO) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDTO);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return notificationService.sendPnsToDevice(notificationRequestDTO);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        return notificationService.sendPnsToTopic(notificationRequestDTO);
    }
}
