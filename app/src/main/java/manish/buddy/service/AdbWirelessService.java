package manish.buddy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import manish.buddy.R;
import manish.buddy.main.MainActivity;

public class AdbWirelessService extends Service{

    private static AdbWirelessService adbWirelessService;
    private Notification notification;
    private NotificationManager notificationManager;

    public static AdbWirelessService getInstance() {
        return adbWirelessService;
    }

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        adbWirelessService = this;
        setupNotif();
        startForeground(R.string.notif_id_adb_wireless, notification);
    }

    private void setupNotif() {
        notification = buildNotification("ADB wireless enabled.");
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
        notificationManager.notify(R.string.notif_id_adb_wireless, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adbWirelessService = null;
        hideNotification();
    }

    public void hideNotification(){
        notificationManager.cancel(R.string.notif_id_adb_wireless);
    }
}
