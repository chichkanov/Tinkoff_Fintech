package com.chichkanov.tinkoff_fintech.views;

import com.hannesdorfmann.mosby3.mvp.MvpView;


public interface LoginView extends MvpView {

    void goToNavigationScreen();
    void showUnSuccessAuth();

}
