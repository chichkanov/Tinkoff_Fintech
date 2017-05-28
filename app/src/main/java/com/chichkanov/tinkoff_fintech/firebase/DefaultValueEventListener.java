package com.chichkanov.tinkoff_fintech.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class DefaultValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //empty
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        //empty
    }
}
