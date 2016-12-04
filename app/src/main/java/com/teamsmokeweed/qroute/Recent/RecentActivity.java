package com.teamsmokeweed.qroute.Recent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamsmokeweed.qroute.MainActivity;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.firebase.CenteridValue;
import com.teamsmokeweed.qroute.firebase.GenUID;
import com.teamsmokeweed.qroute.firebase.MIDClass;
import com.teamsmokeweed.qroute.notification.SampleAlarmReceiver;
import com.teamsmokeweed.qroute.readqr.ReadActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jongzazaal on 9/11/2559.
 */

public class RecentActivity extends AppCompatActivity {

//    private long lastBackPressTime = 0;
    private Toast toast;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    private DatabaseReference mFirebaseDatabase2;
    private FirebaseDatabase mFirebaseInstance2;


    private List<CenteridValue> mDataSet;
    private List<String> mMobileID;
    SharedPreferences prefs = null;
    private FloatingActionButton fabAdd;
    View decorView;

    SwipeRefreshLayout swipeRefreshLayout;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        prefs = getSharedPreferences("CheckFirst", MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("จิ้มๆๆ");
                Intent i = new Intent(RecentActivity.this, ReadActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mDataSet= new ArrayList<>();
        mMobileID=new ArrayList<>();
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        catch (Exception e){

        }





        //From Here Starts All The Swipe To
        // Refresh Initialisation And Setter Methods.
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);//Initialising
        //Setting Up OnRefresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //onRefresh method is used to perform all the action
                // when the swipe gesture is performed.
                try {
                    //new RecentCustomAdapter().clearData();
                    initializeRecyclerView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //This are some optional methods for customizing
        // the colors and size of the loader.
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue,       //This method will rotate
                R.color.red,        //colors given to it when
                R.color.yellow,     //loader continues to
                R.color.green);     //refresh.

        //setSize() Method Sets The Size Of Loader
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //Below Method Will set background color of Loader
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);







        //ArrayList<String> ar = viewDatabaseQr.queryFirebase();


//        try {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }
//        catch (Exception e){
//
//        }

        mFirebaseInstance = FirebaseDatabase.getInstance();

        final String[] values = new String[2];
        values[0] = "-KW8hT-b2GREsu4y_bjl";
        values[1] = "-KW8Z2DOEXOVznFW-pcA";

        initializeRecyclerView();



    }
    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
            String MID = new GenUID().genMID();

            prefs.edit().putString("MID", MID).commit();
        }

    }
    public void initializeRecyclerView(){
        mFirebaseDatabase = mFirebaseInstance.getReference("mobileid");
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //prefs.edit().putString("MID", MID).commit();
                //Toast.makeText(RecentActivity.this, dataSnapshot.getKey()+"//"+prefs.getString("MID", ""), Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getKey().equals(prefs.getString("MID", ""))){
//                    Toast.makeText(RecentActivity.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    for (final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        mFirebaseDatabase2 = mFirebaseInstance.getReference("centerid");

                        mFirebaseDatabase2.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot3, String s) {
                                //Chat model = dataSnapshot.getValue(Chat.class);
                                //for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                                MIDClass midClass = dataSnapshot1.getValue(MIDClass.class);
                                //Toast.makeText(RecentActivity.this, dataSnapshot3.getKey()+"///"+midClass.getCid(), Toast.LENGTH_SHORT).show();


                                if ((dataSnapshot3.getKey()).equals(midClass.getCid())) {
                                    CenteridValue centeridValue = dataSnapshot3.getValue(CenteridValue.class);
//                                    Toast.makeText(RecentActivity.this, dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(RecentActivity.this, "" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                                    //mChats.add(model);
                                    mMobileID.add(dataSnapshot1.getKey());
                                    mDataSet.add(centeridValue);
                                    mRecyclerView.scrollToPosition(mDataSet.size() - 1);
                                    mAdapter.notifyItemInserted(mDataSet.size() - 1);
                                } else {
                                    //Toast.makeText(RecentActivity.this, "fail" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                                }
                                //}


                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new StaggeredGridLayoutManager(3,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //myDataset = {"ggg",""};


        //ArrayList<String> email = new ArrayList<String>();

        String m = prefs.getString("MID", "");
        mAdapter = new RecentCustomAdapter(RecentActivity.this, mDataSet, mMobileID, m);
        new RecentCustomAdapter(RecentActivity.this, mDataSet, mMobileID, m).clearData();
        mAdapter = new RecentCustomAdapter(RecentActivity.this, mDataSet, mMobileID, m);

        ((RecentCustomAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addOnItemTouchListener(
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);

        }
        //mRecyclerView=null;
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press AGAIN to EXIT",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
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