package com.chichkanov.tinkoff_fintech.dialogs;

import com.chichkanov.tinkoff_fintech.dialogs.DialogsView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class DialogsPresenter extends MvpBasePresenter<DialogsView> {

    @Override
    public void attachView(DialogsView view) {
        super.attachView(view);
    }

    public void onAddDialogButtonClick() {
        if (isViewAttached()) {
            getView().goToAddDialogScreen();
        }
    }

}
