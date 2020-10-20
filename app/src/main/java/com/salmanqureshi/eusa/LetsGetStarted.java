package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LetsGetStarted extends AppCompatActivity {
    Button letsstartbutton;
    ConstraintLayout fullscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_get_started);
        letsstartbutton=findViewById(R.id.letsstartbutton);
        fullscreen=findViewById(R.id.fullscreen);
        letsstartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LetsGetStarted.this,PhoneNumberVerification.class);
                startActivity(intent);
            }
        });
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LetsGetStarted.this,PhoneNumberVerification.class);
                startActivity(intent);
            }
        });
    }
}