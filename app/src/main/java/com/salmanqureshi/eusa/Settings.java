package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    ImageView imageViewBackArrowSettings, circleImageViewSettings, accountimagesettings;
    TextView signoutsettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageViewBackArrowSettings = findViewById(R.id.imageViewBackArrowSettings);
        imageViewBackArrowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        circleImageViewSettings=findViewById(R.id.circleImageViewSettings);
        circleImageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.this,EditProfile.class);
                startActivity(intent);
            }
        });
        accountimagesettings=findViewById(R.id.accountimagesettings);
        accountimagesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.this,EditProfile.class);
                startActivity(intent);
            }
        });
        signoutsettings=findViewById(R.id.signoutsettings);
        signoutsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}