package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class LoginReceiveVerificationCode extends AppCompatActivity {
    MaterialButton verifycodelogin;
    ImageView loginverifybackbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_receive_verification_code);
        loginverifybackbutton=findViewById(R.id.loginverifybackbutton);
        verifycodelogin=findViewById(R.id.verifycodelogin);
        verifycodelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginReceiveVerificationCode.this,BasicSearch.class);
                startActivity(intent);
            }
        });
        loginverifybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}