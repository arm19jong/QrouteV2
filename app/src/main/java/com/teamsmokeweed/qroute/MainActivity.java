package com.teamsmokeweed.qroute;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamsmokeweed.qroute.readqr.ConnectivityReceiver;
import com.teamsmokeweed.qroute.readqr.MyApplication;
import com.teamsmokeweed.qroute.readqr.ReadActivity;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAdd;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.teamsmokeweed.qroute", MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        checkConnection();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new StaggeredGridLayoutManager(3,1);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList<String> linkPic = new ArrayList<String>();
        linkPic.add("https://www.picmonkey.com/_/static/images/index/picmonkey_twitter_02.24fd38f81e59.jpg");
        linkPic.add("https://firebasestorage.googleapis.com/v0/b/qroutedb.appspot.com/o/images%2Fmountains.jpg?alt=media&token=673caf69-beee-4f64-a33b-5e521130a1e5");
        linkPic.add("http://i.ndtvimg.com/i/2016-06/wolf_650x400_61465560359.jpg");
        linkPic.add("https://c1.staticflickr.com/9/8452/7936998300_6ab78565ff_b.jpg");
        linkPic.add("http://www.picdesi.com/upload/comment/friend/best-friends-quote-pic.jpg");
        linkPic.add("http://nation.com.pk/digital_images/large/2016-06-15/pic-of-the-day-1465995986-1968.jpg");
        linkPic.add("http://www.destination-languedoc.co.uk/docs/1972-9-pic-saint-loup-herault-le-languedoc.jpg");
        linkPic.add("http://ste.india.com/sites/default/files/2015/12/31/446562-g.jpg");

        mAdapter = new MyAdapter(getApplicationContext(), linkPic);
        ((MyAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("จิ้มๆๆ");
                Intent i = new Intent(MainActivity.this, ReadActivity.class);
                startActivityForResult(i, 1);
            }
        });

//        fabAdd.setBac(0x009688);

        //mRecyclerView.addOnItemTouchListener(
//        try {
//            checkTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    Toast m_currentToast;
    void showToast(String text)
    {
        if(m_currentToast == null)
        {
            m_currentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        }

        m_currentToast.setText(text);
        m_currentToast.setDuration(Toast.LENGTH_LONG);
        m_currentToast.show();
    }

    void checkTime() throws ParseException {
        Calendar c = Calendar.getInstance();
        Calendar cc = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        cc.setTime(sdf.parse("07-11-2016 23:19"));// all done
        Date Dc = c.getTime();
        Date Dcc = cc.getTime();

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = df.format(c.getTime());
//        Date dataNow = new Date();
//        Date data = new Date();
//        data.setDate(8);
//        data.setMonth(11);
//        data.setYear(2016);
//        data.setHours(22);
//        data.setMinutes(44);



        if(Dcc.getTime()>Dc.getTime()){
            Toast.makeText(this, "true"+ c.getTime(),Toast.LENGTH_SHORT).show();
        }
        else {Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();}
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("mobileid");
            String mobileid = mFirebaseDatabase.push().getKey();


            prefs.edit().putBoolean("firstrun", false).commit();
            prefs.edit().putString("mobileid", mobileid).commit();
        }
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
