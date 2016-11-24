package com.teamsmokeweed.qroute.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jongzazaal on 10/11/2559.
 */

public class SaveToMobileID {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public SaveToMobileID(String cid, String MID){

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("mobileid/"+MID);

        //FirebaseUser userc = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = mFirebaseDatabase.push().getKey();



        //mFirebaseDatabase = mFirebaseInstance.getReference("mobileid"+"/"+MID);

        //Log.d("sssssssssssssssssssss", MID+"///"+"ooooooooooooooooooooooooooooooooooooooooooo");
        //mFirebaseDatabase.child(id).setValue(dateQr);
        MIDClass midClass = new MIDClass(cid);

        mFirebaseDatabase.child(new GenUID().genUID("mobileid/"+MID)).setValue(midClass);

    }
}
