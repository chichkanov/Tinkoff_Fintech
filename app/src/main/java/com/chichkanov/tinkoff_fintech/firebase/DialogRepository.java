package com.chichkanov.tinkoff_fintech.firebase;

import com.chichkanov.tinkoff_fintech.dialogs.DialogsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;


public class DialogRepository {

    private final static DialogRepository instance = new DialogRepository();
    private final DatabaseReference db;

    public DialogRepository() {
        this.db = FirebaseDatabase.getInstance().getReference("dialogs");
    }

    public static synchronized DialogRepository getInstance() {
        return instance;
    }

    public void addDialog(final DialogsItem dialogItem, final OnTransactionComplete<Void> onTransactionComplete) {
        UserInfo userInfo = FirebaseAuth.getInstance().getCurrentUser();
        db.child(userInfo.getUid()).push().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.setValue(dialogItem);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean commited, DataSnapshot dataSnapshot) {
                if (commited) {
                    onTransactionComplete.onCommit(null);
                } else {
                    onTransactionComplete.onAbort(databaseError.toException());
                }
            }
        });
    }

    public void getDialogs(final DialogItemValueListener eventListener) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.child(currentUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DialogsItem value = dataSnapshot.getValue(DialogsItem.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //TODO implementation
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //TODO implementation
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //TODO implementation
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO implementation
            }
        });
        db.child(currentUser.getUid()).addListenerForSingleValueEvent(new DefaultValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DialogsItem> items = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items.add(snapshot.getValue(DialogsItem.class));
                }
                eventListener.onValue(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
