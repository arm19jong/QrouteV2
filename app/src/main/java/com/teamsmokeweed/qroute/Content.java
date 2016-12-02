package com.teamsmokeweed.qroute;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jongzazaal on 2/11/2559.
 */

public class Content extends AppCompatActivity {
    ImageView imageView;
    TextView textView, des, web, titles, timeOut, startStop;
    View decorView;
    String[] sQr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.content);



        setTitle("Test");
        //setTitleColor(R.color.colorPrimary);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(1,0,0)));
        fab.setImageDrawable(ContextCompat.getDrawable(Content.this, R.drawable.ic_directions_48px));
        fab.setImageResource(R.drawable.ic_directions_48px);
        fab.setSize(fab.SIZE_AUTO);


//        textView = (TextView) findViewById(R.id.titles);
//        textView.setText(R.string.large_text);

        des = (TextView) findViewById(R.id.des);
        web = (TextView) findViewById(R.id.web);
        titles = (TextView) findViewById(R.id.titles);
        startStop = (TextView) findViewById(R.id.startStop);



        imageView = (ImageView) findViewById(R.id.image_id);

        //Picasso.with(ScrollingActivity.this).load("https://c1.staticflickr.com/9/8452/7936998300_6ab78565ff_b.jpg").into(imageView);
        //final String url = "https://c1.staticflickr.com/9/8452/7936998300_6ab78565ff_b.jpg"; //place your url here

//        Intent i = getIntent();
//        final String url = i.getStringExtra("web");


        Intent i = getIntent();
        sQr = i.getStringArrayExtra("sQr");

        des.setText(sQr[5]);
        web.setText(sQr[6]);
        titles.setText(sQr[2]);
        try {
            startStop.setText(startStop(sQr[8]+" "+sQr[9], sQr[10]+" "+sQr[11]));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            String ss = checkTime(sQr[10]+ " "+ sQr[11], getCurrentTimeStamp());
            final Dialog dialog = new Dialog(Content.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_show_date);
            dialog.setCancelable(true);
            timeOut = (TextView) dialog.findViewById(R.id.timeOut);

            timeOut.setText(ss);

            dialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Float.valueOf(sQr[0]), Float.valueOf(sQr[1]), "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(Content.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if(titles.getText().length()>30){
            titles.setTextSize(12);
        }


        picassoLoader(this, imageView, sQr[7]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = Content.this.getLayoutInflater();
                View imageDialog = inflater.inflate(R.layout.dialog_image, null);
                Dialog dialog = new Dialog(Content.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(imageDialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imgImage = (ImageView) imageDialog.findViewById(R.id.img);
                // Set your image
                picassoLoader(Content.this, imgImage, sQr[7]);
                dialog.show();
            }
        });





    }
    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    public String startStop(String start, String stop) throws ParseException {
        Calendar startTime = Calendar.getInstance();
        Calendar stopTime = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        startTime.setTime(sdf.parse(start));// all done
        stopTime.setTime(sdf.parse(stop));
        //Date Dcurrent = currentTime.getTime();
        //Date DendTime = endTime.getTime();


        SimpleDateFormat myFormat = new SimpleDateFormat("EEEE dd MMMM yyyy, HH:mm");
        String s = myFormat.format(startTime.getTime());
        return myFormat.format(startTime.getTime())+"-\n"+myFormat.format(stopTime.getTime());
    }
    static String  checkTime(String sEndTime, String sCurrentTime) throws ParseException {
        Calendar currentTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        endTime.setTime(sdf.parse(sEndTime));// all done
        currentTime.setTime(sdf.parse(sCurrentTime));
        Date Dcurrent = currentTime.getTime();
        Date DendTime = endTime.getTime();

//        SimpleDateFormat year = new SimpleDateFormat("yyyy");
//        SimpleDateFormat month = new SimpleDateFormat("MM");
//        SimpleDateFormat day = new SimpleDateFormat("dd");
//        SimpleDateFormat house = new SimpleDateFormat("HH");
//        SimpleDateFormat minute = new SimpleDateFormat("mm");
//
//        String formattedDate = year.format(currentTime.getTime());
////        Date dataNow = new Date();
////        Date data = new Date();
////        data.setDate(8);
////        data.setMonth(11);
////        data.setYear(2016);
////        data.setHours(22);
////        data.setMinutes(44);
//
//
//        String timeOutYear = (
//                Integer.valueOf(year.format(endTime.getTime()))-
//                Integer.valueOf(year.format(currentTime.getTime()))
//        )+"";
//        String timeOutMonth = (
//                Integer.valueOf(month.format(endTime.getTime()))-
//                        Integer.valueOf(month.format(currentTime.getTime()))
//        )+"";
//        String timeOutDay = (
//                Integer.valueOf(day.format(endTime.getTime()))-
//                        Integer.valueOf(day.format(currentTime.getTime()))
//        )+"";
//
//        String timeOutHouse = (
//                Integer.valueOf(house.format(endTime.getTime()))-
//                        Integer.valueOf(house.format(currentTime.getTime()))
//        )+"";
//        String timeOutMinute = (
//                Integer.valueOf(minute.format(endTime.getTime()))-
//                        Integer.valueOf(minute.format(currentTime.getTime()))
//        )+"";

//        TimeOutAbs t = new TimeOutSimple();
//        t = new SHouse(t);
//        t.setTimeOut(Integer.valueOf(timeOutHouse));
        if(DendTime.getTime()>Dcurrent.getTime()){
//            Toast.makeText(this, "TimeOut: "+timeOutDay+" Day "+timeOutMonth+" month "+timeOutYear+" year",Toast.LENGTH_LONG).show();
//
//            Toast.makeText(this, t.getTimeOut(),Toast.LENGTH_LONG).show();
//            return timeOutYear+" "+timeOutMonth+" "+timeOutDay+" "+timeOutHouse+" "+timeOutMinute;
            if ((DendTime.getTime() - Dcurrent.getTime()) / (24 * 60 * 60 * 1000L)>0){
                return  "EndTime: "+(DendTime.getTime() - Dcurrent.getTime()) / (24 * 60 * 60 * 1000L)+" Day";
            }
            else if ((DendTime.getTime() - Dcurrent.getTime()) / (60 * 60 * 1000)>0){
                return  "EndTime: "+(DendTime.getTime() - Dcurrent.getTime()) / (60 * 60 * 1000)+" Hr";
            }
            else {
                return  "EndTime: "+(DendTime.getTime() - Dcurrent.getTime()) / (60 * 1000)+" Min";
            }
        }
        else {
//            Toast.makeText(this, "TimeOut", Toast.LENGTH_LONG).show();
            return "TimeOut";
        }
    }
    public void picassoLoader(Context context, ImageView imageView, String url){
        Log.d("PICASSO", "loading image");
        Picasso.with(context)
                .load(url)
                //.resize(30,30)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
