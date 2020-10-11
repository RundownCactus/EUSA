package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UpdatePhoneNumber extends AppCompatActivity {
    Button updateuserphonenobutton;
    ImageView imageViewBackArrowUpdatePhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);
        updateuserphonenobutton=findViewById(R.id.updateuserphonenobutton);
        updateuserphonenobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewBackArrowUpdatePhoneNumber=findViewById(R.id.imageViewBackArrowUpdatePhoneNumber);
        imageViewBackArrowUpdatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}