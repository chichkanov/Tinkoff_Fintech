package com.chichkanov.tinkoff_fintech.login;

import android.os.AsyncTask;

import com.chichkanov.tinkoff_fintech.utils.PrefManager;

public class LoginTask extends AsyncTask<String[], Void, Boolean> {

    private LoginPresenter loginPresenter;

    public LoginTask(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
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
        loginPresenter.setAuthorizationResult(success);
    }
}
