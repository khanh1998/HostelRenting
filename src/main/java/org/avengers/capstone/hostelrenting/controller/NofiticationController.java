package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.dto.notification.SubscriptionRequestDTO;
import org.avengers.capstone.hostelrenting.service.impl.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class NofiticationController {
    private FirebaseService firebaseService;

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

//    @PostMapping("/subscribe")
//    public void subscribeToTopic(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
//        firebaseService.subscribeToTopic(subscriptionRequestDTO);
//    }
//
//    @PostMapping("/unsubscribe")
//    public void unsubscribeFromTopic(SubscriptionRequestDTO subscriptionRequestDTO) {
//        firebaseService.unsubscribeFromTopic(subscriptionRequestDTO);
//    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequest notificationRequest) {
        return firebaseService.sendPnsToDevice(notificationRequest);
    }

//    @PostMapping("/topic")
//    public String sendPnsToTopic(@RequestBody NotificationRequest notificationRequest) {
//        return firebaseService.sendPnsToTopic(notificationRequest);
//    }
}
