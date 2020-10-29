package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateFirstName extends AppCompatActivity {
    Button updateuserfirstnamebutton;
    ImageView imageViewBackArrowUpdateFirstName;
    TextInputEditText getupdateuserfirstname;
    Intent result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_first_name);
        updateuserfirstnamebutton=findViewById(R.id.updateuserfirstnamebutton);
        updateuserfirstnamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=new Intent();
                String firstname=getupdateuserfirstname.getText().toString();
                result.putExtra("firstname",firstname);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        getupdateuserfirstname=findViewById(R.id.getupdateuserfirstname);
        imageViewBackArrowUpdateFirstName=findViewById(R.id.imageViewBackArrowUpdateFirstName);
        imageViewBackArrowUpdateFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}