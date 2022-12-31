package com.example.restapi.service;

import com.example.restapi.model.dto.NotificationRequestDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotificationService {

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    public String sendPnsToDevice(NotificationRequestDTO notificationRequestDto) {
        try {
            Message message = Message.builder()
                    .setToken(notificationRequestDto.getTarget())
                    .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
                    .putData("content", notificationRequestDto.getTitle())
                    .putData("body", notificationRequestDto.getBody())
                    .build();

            String response = null;
            try {
                response = FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                log.error("Fail to send firebase notification", e);
            }

            return response;
        } catch (Exception ex) {
            return null;
        }
    }

    public String sendPnsToTopic(NotificationRequestDTO notificationRequestDto) {
        try {
            Message message = Message.builder()
                    .setTopic(notificationRequestDto.getTarget())
                    .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
                    .putData("content", notificationRequestDto.getTitle())
                    .putData("body", notificationRequestDto.getBody())
                    .build();

            String response = null;
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                log.error("Fail to send firebase notification", e);
            }

            return response;
        } catch (Exception ex) {
            return null;
        }
    }

    public BatchResponse sendMultiToDevice(NotificationRequestDTO notificationRequestDto) {
        try {
            List<String> registrationTokens = notificationRequestDto.getTargetMultiDevice();

            MulticastMessage message = MulticastMessage .builder()
                    .addAllTokens(registrationTokens)
                    .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
                    .putData("content", notificationRequestDto.getTitle())
                    .putData("body", notificationRequestDto.getBody())
                    .build();

            BatchResponse batchResponse = null;
            try {
                batchResponse = FirebaseMessaging.getInstance().sendMulticast(message);
            } catch (FirebaseMessagingException e) {
                log.info("Firebase error {}", e.getMessage());
            }
            if (batchResponse.getFailureCount() > 0) {
                List<SendResponse> responses = batchResponse.getResponses();
                List<String> failedTokens = new ArrayList<>();
                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        failedTokens.add(registrationTokens.get(i));
                    }
                }
                log.info("List of tokens that caused failures: " + failedTokens);
            }
            return batchResponse;
        } catch (Exception ex) {
            return null;
        }
    }

}
