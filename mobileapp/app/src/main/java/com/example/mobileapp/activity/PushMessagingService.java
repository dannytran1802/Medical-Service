package com.example.mobileapp.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mobileapp.R;
import com.example.mobileapp.dto.MessageDTO;
import com.example.mobileapp.util.ContantUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

public class PushMessagingService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();
        String CHANNEL_ID = "MESSAGE";

        String stKey = "";
        String stType = "";
        String stTitle = "";

        StringTokenizer st = new StringTokenizer(title, "||");
        while (st.hasMoreTokens()) {
            stKey = st.nextToken();
            stType = st.nextToken();
            stTitle = st.nextToken();
        }

        if (!ContantUtil.checkMessage(stKey)) {
            if (stType.contains("BOOKING")) {
                Intent intent = new Intent("MyMessage");
                intent.putExtra("title", stTitle);
                intent.putExtra("body", body);
                broadcaster.sendBroadcast(intent);
            }

            if (stType.contains("BOOKING")) {
                stTitle = "Medical Service";
            }

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Message Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notiBuilder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(stTitle)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
            NotificationManagerCompat.from(this).notify(1, notiBuilder.build());



            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

            // save
            MessageDTO msg = new MessageDTO();
            msg.setId(stKey);
            msg.setTitle(stTitle);
            msg.setContent(body);
            msg.setTime(formatter.format(date));
            msg.setType(stType);
            ContantUtil.addMessage(msg);
        }

        super.onMessageReceived(message);
    }

}
