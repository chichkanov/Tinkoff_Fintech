package com.chichkanov.tinkoff_fintech.utils;

import android.app.Application;

import com.chichkanov.tinkoff_fintech.utils.PrefManager;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.newInstance(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
