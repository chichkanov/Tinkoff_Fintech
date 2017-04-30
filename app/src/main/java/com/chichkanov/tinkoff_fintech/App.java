package com.chichkanov.tinkoff_fintech;

import android.app.Application;

public class App extends Application {

    private static ChatDbHelper chatDbHelper;

    public static ChatDbHelper getDbhelper() {
        return chatDbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.newInstance(this);
        chatDbHelper = new ChatDbHelper(this);
    }
}
