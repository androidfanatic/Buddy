package manish.buddy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;

import manish.buddy.R;
import manish.buddy.main.MainActivity;

public class StayAwakeService extends Service {
    private static StayAwakeService stayAwakeService;
    private Notification notification;
    private NotificationManager notificationManager;
    private WindowManager windowManager;
    private View view;

    public static StayAwakeService getInstance() {
        return stayAwakeService;
    }

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        stayAwakeService = this;
        setupNotif();
        startForeground(R.string.notif_id_stay_awake, notification);
    }

    private void setupNotif() {
        notification = buildNotification("Stay awake enabled.");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public Notification buildNotification(String msg) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(msg);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        return builder.build();
    }

    public void showNotification() {
        notificationManager.notify(R.string.notif_id_stay_awake, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stayAwakeService = null;
        hideNotification();
        if (view != null) {
            windowManager.removeView(view);
        }
    }

    public void hideNotification() {
        notificationManager.cancel(R.string.notif_id_adb_wireless);
    }
}
