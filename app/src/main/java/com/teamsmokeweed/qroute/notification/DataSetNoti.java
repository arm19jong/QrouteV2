package com.teamsmokeweed.qroute.notification;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by jongzazaal on 3/12/2559.
 */

public class DataSetNoti {
    private int day, month, year, hour, min, position;
    private String[] sqr;
    private static DataSetNoti instance;


    private DataSetNoti() {
    }

    public static DataSetNoti getInstance(){
        if (instance == null){
            instance = new DataSetNoti();
        }
        return instance;
    }

    public void SetDateTime(int day, int month, int year, int hour, int min, int position, String[] sqr) {
        this.day = day-1;
//        this.hour = hour;
        this.hour = 12;
//        this.min = min;
        this.min = 0;
        //0->JANUARY
        this.month = month-1;
        this.position = position;
        this.year = year;
        this.sqr = sqr;
    }

    public long getTimeInMillis(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min, 0);

        //System.out.println(day+"/"+month+"/"+year);
//        Log.i("aaaaaaaaaaaaa", day+"/"+month+"/"+year+"//"+calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    public int getPosition(){
        return this.position;
    }

    public String[] getSqr() {
        return sqr;
    }
}
