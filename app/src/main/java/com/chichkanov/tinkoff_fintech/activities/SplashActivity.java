package com.chichkanov.tinkoff_fintech.activities;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.chichkanov.tinkoff_fintech.PrefManager;
import com.chichkanov.tinkoff_fintech.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Drawable drawable = imageView.getDrawable();
        final Handler handler = new Handler();

        handler.post(new Runnable(){
            public void run() {
                ((Animatable) drawable).start();

                Runnable afterExe = new Runnable() {
                    @Override
                    public void run() {
                        if (PrefManager.getInstance().getLogin() != null) startMainScreen();
                        else startLoginScreen();
                    }
                };

                handler.postDelayed(afterExe, 2000);
            }
        });
    }

    private void startLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startMainScreen() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", PrefManager.getInstance().getLogin());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

