package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UpdatePhoneNumber extends AppCompatActivity {
    Button updateuserphonenobutton;
    ImageView imageViewBackArrowUpdatePhoneNumber;
    TextInputEditText getupdateuserphoneno;
    Intent result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);
        updateuserphonenobutton=findViewById(R.id.updateuserphonenobutton);
        updateuserphonenobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=new Intent();
                String phone=getupdateuserphoneno.getText().toString();
                result.putExtra("phone",phone);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        getupdateuserphoneno=findViewById(R.id.getupdateuserphoneno);
        imageViewBackArrowUpdatePhoneNumber=findViewById(R.id.imageViewBackArrowUpdatePhoneNumber);
        imageViewBackArrowUpdatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}