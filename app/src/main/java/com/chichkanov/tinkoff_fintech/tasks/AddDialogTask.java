package com.chichkanov.tinkoff_fintech.tasks;


import android.os.AsyncTask;

import com.chichkanov.tinkoff_fintech.models.DialogsItem;
import com.chichkanov.tinkoff_fintech.presenters.AddDialogPresenter;

public class AddDialogTask extends AsyncTask<String[], Void, Boolean> {

    private AddDialogPresenter addDialogPresenter;
    private DialogsItem item;

    public AddDialogTask(AddDialogPresenter addDialogPresenter, DialogsItem item) {
        this.addDialogPresenter = addDialogPresenter;
        this.item = item;
    }

    @Override
    protected Boolean doInBackground(String[]... params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        addDialogPresenter.setAddDialogResult(success, item);
    }
}
