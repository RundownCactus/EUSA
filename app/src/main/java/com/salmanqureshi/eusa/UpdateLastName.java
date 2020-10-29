package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateLastName extends AppCompatActivity {
    Button updateuserlastnamebutton;
    ImageView imageViewBackArrowUpdateLastName;
    TextInputEditText getupdateuserlastname;
    Intent result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_last_name);
        updateuserlastnamebutton=findViewById(R.id.updateuserlastnamebutton);
        updateuserlastnamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=new Intent();
                String lastname=getupdateuserlastname.getText().toString();
                result.putExtra("lastname",lastname);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        getupdateuserlastname=findViewById(R.id.getupdateuserlastname);
        imageViewBackArrowUpdateLastName=findViewById(R.id.imageViewBackArrowUpdateLastName);
        imageViewBackArrowUpdateLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}