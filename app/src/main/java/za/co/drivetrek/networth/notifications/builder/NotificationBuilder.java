package za.co.drivetrek.networth.notifications.builder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import za.co.drivetrek.networth.notifications.utils.Notification.NotificationReceiver;
import za.co.drivetrek.networth.utils.Constants;

public class NotificationBuilder {

    public static NotificationCompat.Builder getNotificationBuilder(Context context, Class target, String contentTitle,
            String contentText) {
        Intent notificationIntent = new Intent(context, target);
        PendingIntent notificationPendingIntent = PendingIntent
                .getActivity(context, Constants.NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent deletedIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent deletedPendingIntent = PendingIntent
                .getBroadcast(context, Constants.NOTIFICATION_ID, deletedIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, Constants.PRIMARY_CHANNEL_ID).setContentTitle(contentTitle)
                        .setContentText(contentText).setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setContentIntent(notificationPendingIntent).setAutoCancel(true)
                        .setDeleteIntent(deletedPendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true);

        return builder;
    }
}
