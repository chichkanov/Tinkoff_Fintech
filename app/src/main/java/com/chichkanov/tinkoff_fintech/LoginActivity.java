package com.chichkanov.tinkoff_fintech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if(login.length() > 0 && password.length() > 0){
                    startNextScreen();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Введите правильные данные!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startNextScreen() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", login.getText().toString());
        startActivity(intent);
        finish();
    }
}
