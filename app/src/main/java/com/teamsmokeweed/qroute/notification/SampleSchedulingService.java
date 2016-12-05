package com.teamsmokeweed.qroute.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.teamsmokeweed.qroute.Content;
import com.teamsmokeweed.qroute.MainActivity;
import com.teamsmokeweed.qroute.R;

/**
 * Created by jongzazaal on 3/12/2559.
 */

public class SampleSchedulingService extends IntentService {
    private NotificationManager mNotificationManager;
    public static int NOTIFICATION_ID = 1;
    public static String[] sqr;
    public static String title;
    public static String placeName;
    public static String dayStart;
    @Override
    protected void onHandleIntent(Intent intent) {
//        Intent i = Intent.getIntent()
        //String t = intent.getStringExtra("s");
//        intent.getScheme();
        sendNotification("Hello Notification");

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        SampleAlarmReceiver.completeWakefulIntent(intent);
    }

    public SampleSchedulingService() {
        super("SchedulingService");
    }
    private void sendNotification(String msg){
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Content.class);
        i.putExtra("sQr", sqr);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_app)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(placeName+"\n Even begin tomorrow"))
                .setContentText(placeName+"\n Even begin tomorrow");

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        NOTIFICATION_ID+=1;
    }



}
