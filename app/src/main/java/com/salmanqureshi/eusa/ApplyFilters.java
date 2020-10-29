package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ApplyFilters extends AppCompatActivity {
    ImageView imageViewBackArrowFilter;
    MaterialButton applyfiltersbutton;
    Intent result;
    Chip pricelowtohigh,pricehightolow,ratinglowtohigh,ratinghightolow;
    String selectedChipData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_filters);
        pricelowtohigh=findViewById(R.id.pricelowtohigh);
        pricehightolow=findViewById(R.id.pricehightolow);
        ratinglowtohigh=findViewById(R.id.ratinglowtohigh);
        ratinghightolow=findViewById(R.id.ratinghightolow);
        imageViewBackArrowFilter=findViewById(R.id.imageViewBackArrowFilter);
        applyfiltersbutton=findViewById(R.id.applyfiltersbutton);
        applyfiltersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=new Intent();
                result.putExtra("filter",selectedChipData);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        imageViewBackArrowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CompoundButton.OnCheckedChangeListener checkedChangeListener= new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    selectedChipData=compoundButton.getText().toString();
                }
            }
        };
        pricelowtohigh.setOnCheckedChangeListener(checkedChangeListener);
        pricehightolow.setOnCheckedChangeListener(checkedChangeListener);
        ratinglowtohigh.setOnCheckedChangeListener(checkedChangeListener);
        ratinghightolow.setOnCheckedChangeListener(checkedChangeListener);
    }
}