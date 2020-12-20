package com.salmanqureshi.eusa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
        Animation topAnim, bottomAnim;
        TextView logoName;
        ImageView logoIcon;
        private static int SPLASH_SCREEN=2000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

            logoIcon = findViewById(R.id.logoicon);
            logoName = findViewById(R.id.logoname);

            logoName.setAnimation(bottomAnim);
            logoIcon.setAnimation(topAnim);


            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    if(sessionManager.checkLogin()){
                        Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(SplashScreen.this,LetsGetStarted.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },SPLASH_SCREEN);
        }
    }
