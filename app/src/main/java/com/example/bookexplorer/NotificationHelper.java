package com.example.bookexplorer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {


    private static final String CHANNEL_ID = "book_channel";


    public static void showNotification(
            Context context,
            String title,
            String message) {


        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Book Notifications",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );


            manager.createNotificationChannel(channel);

        }



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )

                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info
                        )

                        .setContentTitle(title)

                        .setContentText(message)

                        .setAutoCancel(true);



        manager.notify(
                1,
                builder.build()
        );

    }

}