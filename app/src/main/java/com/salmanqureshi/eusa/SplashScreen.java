package com.salmanqureshi.eusa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_SCREEN);
        }
    }
