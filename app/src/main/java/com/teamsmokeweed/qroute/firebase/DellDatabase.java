package com.teamsmokeweed.qroute.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jongzazaal on 24/11/2559.
 */

public class DellDatabase {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public void del(String mid, String mobileID){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("mobileid"+"/"+mid);

        mFirebaseDatabase.child(mobileID).removeValue();

    }
}
