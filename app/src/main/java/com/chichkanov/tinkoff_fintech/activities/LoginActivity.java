package com.chichkanov.tinkoff_fintech.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chichkanov.tinkoff_fintech.PrefManager;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.presenters.LoginPresenter;
import com.chichkanov.tinkoff_fintech.views.LoginView;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    private EditText login;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PrefManager.getInstance().getLogin() != null) goToNavigationScreen();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (Button) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.length() > 0 && password.length() > 0) {
                    new LoginActivity.LoadingDialogFragment().show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
                    presenter.onLoginButtonClick(login.getText().toString(), password.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(), "Введите правильные данные!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void goToNavigationScreen() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", PrefManager.getInstance().getLogin());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showUnSuccessAuth() {
        new LoginActivity.ErrorDialogFragment().show(getSupportFragmentManager(), null);
    }

    public static class ErrorDialogFragment extends DialogFragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_fragment_error, null);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.setTitle("Ошибка авторизации!");
            return dialog;
        }
    }

    public static class LoadingDialogFragment extends DialogFragment {

        public static final String TAG = "LoadingDialogFragment";

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_fragment_loading, null);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            setCancelable(false);
            return dialog;
        }
    }
}
