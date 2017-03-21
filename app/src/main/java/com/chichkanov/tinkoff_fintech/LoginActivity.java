package com.chichkanov.tinkoff_fintech;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by chichkanov on 20.03.17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.edit_text_login);
        password = (EditText) findViewById(R.id.edit_text_password);
        button = (Button) findViewById(R.id.btn_enter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextScreen();
            }
        });
    }

    private void startNextScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LOGIN", login.getText().toString());
        startActivity(intent);
    }
}
