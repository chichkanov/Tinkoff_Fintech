package com.chichkanov.tinkoff_fintech;

import android.os.AsyncTask;

import com.chichkanov.tinkoff_fintech.fragments.LoginFragment;

import java.lang.ref.WeakReference;

public class LoginTask extends AsyncTask<String[], Void, Boolean> {

    private WeakReference<LoginFragment> loginFragment;

    public LoginTask(LoginFragment loginFragment) {
        this.loginFragment = new WeakReference<>(loginFragment);
    }

    @Override
    protected Boolean doInBackground(String[]... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PrefManager.getInstance().saveLogin(params[0][0]);
        return true;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        LoginFragment loginFragment = this.loginFragment.get();
        loginFragment.setSuccess(success);
    }
}
