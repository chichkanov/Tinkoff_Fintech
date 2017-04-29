package com.chichkanov.tinkoff_fintech;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.newInstance(this);
    }
}
