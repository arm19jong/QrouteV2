package com.teamsmokeweed.qroute.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jongzazaal on 10/11/2559.
 */

public class GenUID {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public String genMID(){
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("mobileid");

        String MID = mFirebaseDatabase.push().getKey();

        return MID;

    }

    public String genUID(String ref){
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference(ref);

        String UID = mFirebaseDatabase.push().getKey();

        return UID;
    }
}
