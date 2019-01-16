package com.example.nir30.newsly2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class NotificationSendReceiver extends BroadcastReceiver {
    private int interval;
    private int kind;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ON RECEIVE ", Toast.LENGTH_SHORT).show();


        interval = intent.getIntExtra("intervalNotificationInSec", 1000);
        kind = intent.getIntExtra("kindArticle", 0);
        showNotification(context,intent);
    }



    public  void showNotification(Context context, Intent intent) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String articleKindstr = sp.getString("list_kind", "0");
        int articleKind = Integer.parseInt(articleKindstr) ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        //
        NewsArticle notifItem;
        if(articleKind == 0 ) { // news
         //   notifItem = MainActivity.getMostRecentNewsByType;
        }
        else {
           // notifItem = sportNewsArticles.get(0);
        }
        //
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("test:");
               // .setContentTitle(notifItem.getTitle())
                //.setContentText(notifItem.getContent());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(notificationId, mBuilder.build());

        Toast.makeText(context, "Sms Sent2 ", Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0 , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval*1000 , pendingIntent);
    }
}
