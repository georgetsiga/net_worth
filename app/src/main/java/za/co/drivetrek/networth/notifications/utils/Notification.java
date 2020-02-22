package za.co.drivetrek.networth.notifications.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import za.co.drivetrek.networth.notifications.builder.NotificationBuilder;
import za.co.drivetrek.networth.utils.Constants;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notification {

    private NotificationManager mNotificationManager;
    private NotificationReceiver mReceiver;
    private Context mContext;

    public Notification(Context context){
        mNotificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        mReceiver = new NotificationReceiver();
        mContext = context;
    }

    public NotificationReceiver getReceiver(){
        return mReceiver;
    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(Constants.PRIMARY_CHANNEL_ID, Constants.PRIMARY_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(Constants.NOTIFICATION_DESCRIPTION);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void cancelNotification() {

    }

    private void updateNotification() {

    }

    public void sendNotification(Class target, String contentTitle, String contentText) {
        Intent updateIntent = new Intent(Constants.ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent
                .getBroadcast(mContext, Constants.NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder =
                NotificationBuilder.getNotificationBuilder(mContext, target, contentTitle, contentText);
        //builder.addAction(android.R.drawable.stat_sys_upload_done, "Reply", updatePendingIntent);
        mNotificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
    }

    public class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (Constants.UPDATE_NOTIFICATION.equals(intentAction)) {
                updateNotification();
            } else {
                cancelNotification();
            }
        }
    }
}
