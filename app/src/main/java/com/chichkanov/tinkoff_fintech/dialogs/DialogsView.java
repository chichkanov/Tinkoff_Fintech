package com.chichkanov.tinkoff_fintech.dialogs;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface DialogsView extends MvpView {
    void openDialog(String userId);

    void showLoading();

    void goToAddDialogScreen();
}
