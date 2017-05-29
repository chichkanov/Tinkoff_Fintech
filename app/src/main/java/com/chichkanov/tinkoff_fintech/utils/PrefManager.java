package com.chichkanov.tinkoff_fintech.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_FILE = "user_info";
    private static final String PREF_LOGIN = "user_login";

    private static PrefManager instance;

    private final Context context;

    private PrefManager(Context context) {
        this.context = context;
    }

    public static void newInstance(Context context) {
        instance = new PrefManager(context);
    }

    public static PrefManager getInstance() {
        return instance;
    }

    public String getLogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREF_LOGIN, null);
    }

    public void saveLogin(String login) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PREF_LOGIN, login);
        edit.apply();
    }
}
