package com.chichkanov.tinkoff_fintech.presenters;

import com.chichkanov.tinkoff_fintech.tasks.LoginTask;
import com.chichkanov.tinkoff_fintech.views.LoginView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


public class LoginPresenter extends MvpBasePresenter<LoginView> {

    Boolean authResult;

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        if (authResult != null) {
            onAuthResult(authResult);
            authResult = null;
        }
    }

    public void setAuthorizationResult(Boolean authResult) {
        if (isViewAttached()) {
            onAuthResult(authResult);
        } else {
            this.authResult = authResult;
        }
    }

    public void onLoginButtonClick(String login, String password) {
        LoginTask loginTask = new LoginTask(this);
        loginTask.execute(new String[]{login, password});
    }

    private void onAuthResult(Boolean authResult) {
        if (authResult) {
            getView().goToNavigationScreen("ds");
        } else {
            getView().showUnSuccessAuth();
        }
    }
}
