package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Payment extends AppCompatActivity {
    ImageView imageViewBackArrowPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        imageViewBackArrowPayment=findViewById(R.id.imageViewBackArrowPayment);
        imageViewBackArrowPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}