package com.teamsmokeweed.qroute.firebase;

import android.content.SharedPreferences;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jongzazaal on 9/11/2559.
 */

public class ReadFirebase {
//    private DatabaseReference mFirebaseDatabase;
//    private FirebaseDatabase mFirebaseInstance;
//    SharedPreferences prefs = null;
//
//    public void readDb(final String centerid){
//        mFirebaseInstance = FirebaseDatabase.getInstance();
//
//        // get reference to 'users' node
//        mFirebaseDatabase = mFirebaseInstance.getReference("centerid"+"/"+ centerid);
//
//        //String mobileid = prefs.getString("mobileid", "a");
//
//        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
//                    try{
//
//
////                        ValuesF valuesF = dataSnapshot.getValue(ValuesF.class);
//
//                        CenteridValue centeridValue = dataSnapshot.getValue(CenteridValue.class);
////
////
////                        mDataSet.add(valuesF);
////                        mRecyclerView.scrollToPosition(mDataSet.size() - 1);
////                        mAdapter.notifyItemInserted(mDataSet.size() - 1);
//                    } catch (Exception ex) {
//                        //Log.e(TAG, ex.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

}
