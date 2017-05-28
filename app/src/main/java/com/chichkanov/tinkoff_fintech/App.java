package com.chichkanov.tinkoff_fintech;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.newInstance(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
