package de.lukas.americacountdown.Core;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import de.lukas.americacountdown.Activities.MainActivity;
import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.Calculator;

/**
 * Created by Tim on 08.05.2016.
 */
public class MyNotificationBuilder {
    public static Notification build(Context context) {
        Intent mIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Reise nach Amerika");
        builder.setContentText("noch " + Calculator.getLeftDays() + " Tage");
        builder.setSmallIcon(R.drawable.airplane_takeoff);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon_x72));
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);

        return builder.build();
    }
}
