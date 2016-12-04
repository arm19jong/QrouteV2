package com.teamsmokeweed.qroute.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jongzazaal on 3/12/2559.
 */

public class SampleBootReceiver extends BroadcastReceiver {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);
        }
    }
}