package za.co.drivetrek.networth.workmanager;

import android.content.Context;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import za.co.drivetrek.networth.notifications.utils.Notification;
import za.co.drivetrek.networth.scheduler.NotificationJobService;
import za.co.drivetrek.networth.utils.Constants;

import static za.co.drivetrek.networth.utils.Constants.ACTION_UPDATE_NOTIFICATION;

public class NotificationWorker extends Worker {
    private static final String TAG = NotificationWorker.class.getSimpleName();
    private Notification mNotification;
    private WorkerParameters mWorkerParameters;
    private Context mContext;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.mWorkerParameters = workerParams;
    }

    @NonNull
    @Override
    public Result doWork() {
        try{
            mNotification = new Notification(this.mContext);
            mNotification.createNotificationChannel();
            this.mContext.registerReceiver(mNotification.getReceiver(),
                    new IntentFilter(ACTION_UPDATE_NOTIFICATION));

            Data extras = mWorkerParameters.getInputData();
            String contentTitle = extras.getString(Constants.CONTENT_TITLE_EXTRA);
            String contentText = extras.getString(Constants.CONTENT_TEXT_EXTRA);
            mNotification.sendNotification(NotificationJobService.class, contentTitle, contentText);
            return Result.success();
        } catch (Throwable throwable){
            Log.e(TAG, "Error sending notification", throwable);
            return Result.failure();
        }
    }
}
