package com.chichkanov.tinkoff_fintech.activities;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.chichkanov.tinkoff_fintech.utils.PrefManager;
import com.chichkanov.tinkoff_fintech.R;
import com.chichkanov.tinkoff_fintech.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.animated_vector);
                    imageView.setImageDrawable(d);
                    assert d != null;
                    d.start();
                }
                Runnable afterExe = new Runnable() {
                    @Override
                    public void run() {
                        if (PrefManager.getInstance().getLogin() != null) startMainScreen();
                        else startLoginScreen();
                    }
                };
                handler.postDelayed(afterExe, 1000);
            }
        });
    }

    private void startLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }

    private void startMainScreen() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("login", PrefManager.getInstance().getLogin());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }
}

