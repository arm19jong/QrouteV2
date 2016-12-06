package com.teamsmokeweed.qroute.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.teamsmokeweed.qroute.firebase.CenteridValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jongzazaal on 3/12/2559.
 */

public class SampleAlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public static int hh;
    public static int mm;
    public static long timeMillis;
    public static int position;
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("cccccccccc", intent.getStringArrayExtra("sqr")[9]);
        SampleSchedulingService.sqr = intent.getStringArrayExtra("sqr");
        SampleSchedulingService.title = intent.getStringArrayExtra("sqr")[2];
        SampleSchedulingService.placeName = intent.getStringArrayExtra("sqr")[3];
        SampleSchedulingService.dayStart = intent.getStringArrayExtra("sqr")[8];
        Intent service = new Intent(context, SampleSchedulingService.class);

        startWakefulService(context, service);


    }

    public void setAlarm(Context context){
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SampleAlarmReceiver.class);
        intent.putExtra("s", "sampleEx");
        intent.putExtra("sqr", DataSetNoti.getInstance().getSqr());
        pendingIntent = PendingIntent.getBroadcast(context, position, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        if((calendar.getTimeInMillis()-System.currentTimeMillis())<=0){
            return;
        }

        Log.i("hhhhhhhhhh", "setAlarm: "+calendar.getTime().toString());
//        Log.i("hhhhhhhhhh", "setAlarm: "+calendar.getTime().toString());

//        calendar.set(Calendar.HOUR_OF_DAY, hh);
//        calendar.set(Calendar.MINUTE, mm);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.DATE, 2);
//        SimpleDateFormat myFormat = new SimpleDateFormat("EEEE dd MMMM yyyy, HH:mm");

//        Log.i("ssssss", timeMillis+""+myFormat.format(calendar.getTime()));
//        Toast.makeText(context, "s: "+myFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();


//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
//                5 * 1000, pendingIntent);

//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, (calendar.getTimeInMillis()-System.currentTimeMillis())+ SystemClock.elapsedRealtime(), pendingIntent);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager packageManager = context.getPackageManager();

        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    public void cancelAlarm(Context context){
        if (alarmManager!=null){

            Intent intent = new Intent(context, SampleAlarmReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(context, position, intent, 0);
//            alarmManager.cancel(pendingIntent);
            for (int i = 0; i<=30; i++) {
                pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
//            Toast.makeText(context, "Canceiiiiiiiii", Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(context, "cccccccc", Toast.LENGTH_SHORT).show();

        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager packageManager = context.getPackageManager();

        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}