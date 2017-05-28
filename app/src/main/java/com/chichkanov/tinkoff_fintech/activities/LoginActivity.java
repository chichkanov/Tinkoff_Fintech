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
import com.chichkanov.tinkoff_fintech.fragments.ErrorDialogFragment;
import com.chichkanov.tinkoff_fintech.fragments.LoadingDialogFragment;
import com.chichkanov.tinkoff_fintech.presenters.LoginPresenter;
import com.chichkanov.tinkoff_fintech.views.LoginView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1;

    private EditText login;
    private EditText password;
    private Button button;

    private GoogleSignInOptions signInOptions;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleApiClient client;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PrefManager.getInstance().getLogin() != null) goToNavigationScreen(PrefManager.getInstance().getLogin());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (Button) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.length() > 0 && password.length() > 0) {
                    new LoadingDialogFragment().show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
                    presenter.onLoginButtonClick(login.getText().toString(), password.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(), "Введите правильные данные!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(client);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuth(acct);
        }
    }

    private void firebaseAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        AuthResult result = task.getResult();
                        FirebaseUser user = result.getUser();
                        if (user != null) {
                            PrefManager.getInstance().saveLogin(user.getDisplayName());
                            goToNavigationScreen(user.getDisplayName());
                        } else {
                            Toast.makeText(LoginActivity.this, "Unauthorized", Toast.LENGTH_SHORT).show();
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
    public void goToNavigationScreen(String name) {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showUnSuccessAuth() {
        new ErrorDialogFragment().show(getSupportFragmentManager(), null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // empty
    }
}
