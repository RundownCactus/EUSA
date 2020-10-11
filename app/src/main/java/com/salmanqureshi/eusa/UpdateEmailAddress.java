package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UpdateEmailAddress extends AppCompatActivity {
    Button updateemailaddressbutton;
    ImageView imageViewBackArrowUpdateEmailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email_address);
        updateemailaddressbutton=findViewById(R.id.updateemailaddressbutton);
        updateemailaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewBackArrowUpdateEmailAddress=findViewById(R.id.imageViewBackArrowUpdateEmailAddress);
        imageViewBackArrowUpdateEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}