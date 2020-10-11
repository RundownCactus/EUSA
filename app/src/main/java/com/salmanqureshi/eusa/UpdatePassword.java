package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UpdatePassword extends AppCompatActivity {
    Button updatepasswordbutton;
    ImageView imageViewBackArrowUpdatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        updatepasswordbutton=findViewById(R.id.updatepasswordbutton);
        updatepasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewBackArrowUpdatePassword=findViewById(R.id.imageViewBackArrowUpdatePassword);
        imageViewBackArrowUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}