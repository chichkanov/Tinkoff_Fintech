package com.chichkanov.tinkoff_fintech.presenters;

import com.chichkanov.tinkoff_fintech.models.DialogsItem;
import com.chichkanov.tinkoff_fintech.tasks.AddDialogTask;
import com.chichkanov.tinkoff_fintech.views.AddDialogView;
import com.chichkanov.tinkoff_fintech.views.LoginView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddDialogPresenter extends MvpBasePresenter<AddDialogView> {

    Boolean result;

    @Override
    public void attachView(AddDialogView view) {
        super.attachView(view);
        if (result != null) {
            onAddDialogResult(result);
            result = null;
        }
    }

    public void setAddDialogResult(Boolean result, DialogsItem item) {
        if (isViewAttached()) {
            onAddDialogAdd(item);
            onAddDialogResult(result);
        } else {
            this.result = result;
        }
    }

    private void onAddDialogAdd(DialogsItem item) {
        getView().addDialog(item);
    }

    private void onAddDialogResult(Boolean result) {
        getView().goBack();
    }

    public void onAddDialogButtonClick(String userName) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        AddDialogTask dialogTask = new AddDialogTask(this, new DialogsItem(userName, "", dateFormat.format(new Date())));
        dialogTask.execute(new String[]{userName});
    }
}
