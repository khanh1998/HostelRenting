package org.avengers.capstone.hostelrenting.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.dto.notification.SubscriptionRequestDTO;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VendorService vendorService;
    private RenterService renterService;

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

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

    public String sendPnsToDevice(NotificationRequest notificationRequest) {
        if (notificationRequest.getRenterId() != null) {
            Renter exRenter = renterService.findById(notificationRequest.getRenterId());
            notificationRequest.setDestination(exRenter.getFirebaseToken());
        } else if (notificationRequest.getVendorId() != null) {
            Vendor exVendor = vendorService.findById(notificationRequest.getVendorId());
            notificationRequest.setDestination(exVendor.getFirebaseToken());
        }


        String response = null;
        try {
            Message message = Message.builder()
                    .setNotification(null)
                    .setToken(notificationRequest.getDestination())
                    .putAllData(notificationRequest.getData())
                    .build();

            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String sendPnsToTopic(NotificationRequest notificationRequest) {
        String response = null;
        try {
            ObjectMapper objMapper = new ObjectMapper();
            Object data = notificationRequest.getData();
            String json = objMapper.writeValueAsString(data);

            Message message = Message.builder()
                    .setTopic(notificationRequest.getDestination())
//                    .setNotification(new Notification(notificationRequest.getContent().getTitle(), notificationRequest.getContent().getBody()))
                    .putData("json", json)
                    .build();
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String generateJwtToken(UserDetails userDetails) throws FirebaseAuthException {
        String uid = userDetails.getUsername();
        Map<String, Object> additionalClaims = new HashMap<>();
//        additionalClaims.put("vendor", true);

        return FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);
    }


}