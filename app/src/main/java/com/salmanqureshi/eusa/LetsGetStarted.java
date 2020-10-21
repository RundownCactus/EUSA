package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LetsGetStarted extends AppCompatActivity {
    Button loginbutton1,signupbutton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_get_started);
        loginbutton1=findViewById(R.id.loginbutton1);
        signupbutton1=findViewById(R.id.signupbutton1);
        loginbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LetsGetStarted.this,PhoneNumberVerification.class);
                startActivity(intent);
            }
        });
        signupbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}