package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UpdateFirstName extends AppCompatActivity {
    Button updateuserfirstnamebutton;
    ImageView imageViewBackArrowUpdateFirstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_first_name);
        updateuserfirstnamebutton=findViewById(R.id.updateuserfirstnamebutton);
        updateuserfirstnamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewBackArrowUpdateFirstName=findViewById(R.id.imageViewBackArrowUpdateFirstName);
        imageViewBackArrowUpdateFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}