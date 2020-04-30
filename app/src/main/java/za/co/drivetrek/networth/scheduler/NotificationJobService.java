package za.co.drivetrek.networth.scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.IntentFilter;
import android.os.PersistableBundle;

import za.co.drivetrek.networth.notifications.utils.Notification;
import za.co.drivetrek.networth.utils.Constants;

import static za.co.drivetrek.networth.utils.Constants.ACTION_UPDATE_NOTIFICATION;

public class NotificationJobService extends JobService {

    private Notification mNotification;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mNotification = new Notification(this);
        mNotification.createNotificationChannel();
        registerReceiver(mNotification.getReceiver(), new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        PersistableBundle extras = jobParameters.getExtras();
        String contentTitle = extras.getString(Constants.CONTENT_TITLE_EXTRA);
        String contentText = extras.getString(Constants.CONTENT_TEXT_EXTRA);
        mNotification.sendNotification(NotificationJobService.class, contentTitle, contentText);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
