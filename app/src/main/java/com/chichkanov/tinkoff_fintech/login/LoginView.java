package com.chichkanov.tinkoff_fintech.login;

import com.hannesdorfmann.mosby3.mvp.MvpView;


public interface LoginView extends MvpView {

    void goToNavigationScreen(String name);

    void showUnSuccessAuth();

}
