package com.chichkanov.tinkoff_fintech;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    private EditText login;
    private EditText password;
    private Button button;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PrefManager.getInstance().getLogin() != null) startNextScreen();

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
                    new LoginTask(loginFragment).execute(new String[]{login.getText().toString()});
                } else {
                    Toast.makeText(getApplicationContext(), "Введите правильные данные!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (savedInstanceState != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            loginFragment = (LoginFragment) fragmentManager.findFragmentByTag(LoginFragment.TAG);
            if (loginFragment != null) {

            } else {
                loginFragment = new LoginFragment();
                fragmentManager.beginTransaction().add(loginFragment, LoginFragment.TAG).commit();
            }
        } else {
            loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(loginFragment, LoginFragment.TAG).commit();
        }

    }

    private void startNextScreen() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", PrefManager.getInstance().getLogin());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResult(Boolean success) {
        ((LoadingDialogFragment) getSupportFragmentManager().findFragmentByTag(LoadingDialogFragment.TAG)).dismissAllowingStateLoss();
        if (success) {
            startNextScreen();
        } else {
            new LoginActivity.ErrorDialogFragment().show(getSupportFragmentManager(), null);
        }
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
