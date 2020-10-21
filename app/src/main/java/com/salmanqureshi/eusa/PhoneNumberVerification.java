package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneNumberVerification extends AppCompatActivity {
    MaterialButton sendverificationcode;
    TextInputEditText phno;
    ImageView phonenobackbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        sendverificationcode=findViewById(R.id.sendverificationcode);
        phno = findViewById(R.id.verifyphonenumber);
        phonenobackbutton=findViewById(R.id.phonenobackbutton);
        phonenobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sendverificationcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = phno.getText().toString();
                Log.d("WAH","+92"+ph);
                Intent intent=new Intent(PhoneNumberVerification.this,MainActivity.class);
                intent.putExtra("phno",ph);
                startActivity(intent);
            }
        });
    }
}