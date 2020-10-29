package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateEmailAddress extends AppCompatActivity {
    Button updateemailaddressbutton;
    ImageView imageViewBackArrowUpdateEmailAddress;
    TextInputEditText getupdateemailaddress;
    Intent result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email_address);
        updateemailaddressbutton=findViewById(R.id.updateemailaddressbutton);
        updateemailaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=new Intent();
                String email=getupdateemailaddress.getText().toString();
                result.putExtra("email",email);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        getupdateemailaddress=findViewById(R.id.getupdateemailaddress);
        imageViewBackArrowUpdateEmailAddress=findViewById(R.id.imageViewBackArrowUpdateEmailAddress);
        imageViewBackArrowUpdateEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}