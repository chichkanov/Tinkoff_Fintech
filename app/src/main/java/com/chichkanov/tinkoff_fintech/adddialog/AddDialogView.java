package com.chichkanov.tinkoff_fintech.adddialog;

import com.chichkanov.tinkoff_fintech.dialogs.DialogsItem;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface AddDialogView extends MvpView {
    void addDialog(DialogsItem item);

    void goBack();

    void showLoading();
}
