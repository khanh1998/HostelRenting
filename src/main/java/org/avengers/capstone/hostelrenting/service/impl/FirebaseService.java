package org.avengers.capstone.hostelrenting.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.*;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequestDTO;
import org.avengers.capstone.hostelrenting.dto.notification.SubscriptionRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
//            log.error("Create FirebaseApp Error", e);
            e.printStackTrace();
        }
    }

    public void subscribeToTopic(SubscriptionRequestDTO subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
//            log.error("Firebase subscribe to topic fail", e);
            e.printStackTrace();
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDTO subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
//            log.error("Firebase unsubscribe from topic fail", e);
            e.printStackTrace();
        }
    }

    public String sendPnsToDevice(NotificationRequestDTO notificationRequestDto) {
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, String> data = objMapper.convertValue(notificationRequestDto.getData(), Map.class);

        Message message = Message.builder()
                .setToken(notificationRequestDto.getDestination())
                .setNotification(new Notification(notificationRequestDto.getContent().getTitle(), notificationRequestDto.getContent().getBody()))
                .putAllData(data)
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
//            log.error("Fail to send firebase notification", e);
            e.printStackTrace();
        }

        return response;
    }

    public String sendPnsToTopic(NotificationRequestDTO notificationRequestDto) {
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getDestination())
                .setNotification(new Notification(notificationRequestDto.getContent().getTitle(), notificationRequestDto.getContent().getBody()))
                .putData("content", notificationRequestDto.getData().toString())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
//            log.error("Fail to send firebase notification", e);
            e.printStackTrace();
        }

        return response;
    }

    public String generateJwtToken(UserDetails userDetails) throws FirebaseAuthException {
        String uid = userDetails.getUsername();
        Map<String, Object> additionalClaims = new HashMap<>();
//        additionalClaims.put("vendor", true);

        String token = FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);
        return token;
    }


}