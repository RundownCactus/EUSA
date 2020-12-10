package com.salmanqureshi.eusa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ServiceProvidersListView extends AppCompatActivity {
    RecyclerView serviceprovidersRV;
    ImageView mainmenu;
    TextView text;
    ImageView profileImage;
    List<ServiceProvider> serviceProviderList;
    EditText search;
    MyRvAdapter adapter;
    Bitmap image;
    ImageView imageViewBackArrowSPListView;
    TextInputLayout searchserviceprovider;
    private DatabaseReference myref;
    private int REQUEST_CODE=1;
    String filter;

    Chip pricelowtohigh,pricehightolow,ratinglowtohigh,ratinghightolow;
    String selectedChipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_list_view);

        pricelowtohigh=findViewById(R.id.pricelowtohigh);
        pricehightolow=findViewById(R.id.pricehightolow);
        ratinglowtohigh=findViewById(R.id.ratinglowtohigh);
        ratinghightolow=findViewById(R.id.ratinghightolow);

        CompoundButton.OnCheckedChangeListener checkedChangeListener= new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    selectedChipData=compoundButton.getText().toString();
                    //Toast.makeText(ServiceProvidersListView.this,selectedChipData,Toast.LENGTH_SHORT).show();
                    Chipfilter(selectedChipData);

                }
            }
        };
        pricelowtohigh.setOnCheckedChangeListener(checkedChangeListener);
        pricehightolow.setOnCheckedChangeListener(checkedChangeListener);
        ratinglowtohigh.setOnCheckedChangeListener(checkedChangeListener);
        ratinghightolow.setOnCheckedChangeListener(checkedChangeListener);

        searchserviceprovider=findViewById(R.id.searchserviceprovider);
        searchserviceprovider.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                //Intent intent= new Intent(ServiceProvidersListView.this, ApplyFilters.class);
                //startActivityForResult(intent,REQUEST_CODE);
            }
        });
        imageViewBackArrowSPListView=findViewById(R.id.imageViewBackArrowSPListView);
        serviceProviderList=new ArrayList<>();
        mainmenu=findViewById(R.id.mainmenu);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);
        search = findViewById(R.id.srch);
        String type = getIntent().getStringExtra("type");
        myref = FirebaseDatabase.getInstance().getReference("Users").child("ServiceProviders");
        if(type.matches("All")){
            Query getTypeSP = myref.orderByChild("type");
            getTypeSP.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    serviceProviderList = collectData((Map<String,Object>) snapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            Query getTypeSP = myref.orderByChild("type").equalTo(type);
            getTypeSP.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    serviceProviderList = collectData((Map<String,Object>) snapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        serviceprovidersRV=findViewById(R.id.serviceprovidersRV);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        serviceprovidersRV.setLayoutManager(lm);
        adapter=new MyRvAdapter(serviceProviderList,this);
        serviceprovidersRV.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog( ServiceProvidersListView.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView= LayoutInflater.from(ServiceProvidersListView.this).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomsheet_cl));
                TextView myname=bottomSheetView.findViewById(R.id.myname);
                TextView myrating=bottomSheetView.findViewById(R.id.myrating);
                ImageView myimage=bottomSheetView.findViewById(R.id.myimage);
                ImageView worktypeicon=bottomSheetView.findViewById(R.id.worktypeicon);
                TextView worktypetext=bottomSheetView.findViewById(R.id.worktypetext);
                myimage.setImageBitmap(serviceProviderList.get(position).getImage());
                myname.setText(serviceProviderList.get(position).getFname()+" "+serviceProviderList.get(position).getLname());
                myrating.setText(serviceProviderList.get(position).getRating());
                worktypetext.setText(serviceProviderList.get(position).getWorktype());
                if(serviceProviderList.get(position).getWorktype().equals("Plumber"))
                {
                    worktypeicon.setImageResource(R.drawable.plumbericon);
                }
                else if(serviceProviderList.get(position).getWorktype().equals("Carpenter"))
                {
                    worktypeicon.setImageResource(R.drawable.carpentericon);
                }
                else if(serviceProviderList.get(position).getWorktype().equals("Cleaner"))
                {
                    worktypeicon.setImageResource(R.drawable.cleanericon);
                }
                else if(serviceProviderList.get(position).getWorktype().equals("Car Mechanic"))
                {
                    worktypeicon.setImageResource(R.drawable.mechanicicon);
                }
                else
                {
                    worktypeicon.setImageResource(R.drawable.electricianicon);
                }

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });
        imageViewBackArrowSPListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            Log.d("ResultOk", "ResultOk Called");
            if (resultCode == RESULT_OK )
            {
                Log.d("ResultOk", "ResultOk Called--1");
                filter=data.getStringExtra("filter");
                Log.d("ResultOk", filter);
            }
        }
    }


    private void filter(String text) {
        ArrayList<ServiceProvider> filteredList = new ArrayList<>();
        for (ServiceProvider item : serviceProviderList ){
            if(item.getFname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
                Log.d("WHAT", item.getFname());
            }
        }
        adapter.filterlist(filteredList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Chipfilter(String text) {
        if(text.matches("Price Low to High")){
            serviceProviderList.sort(Comparator.comparing(ServiceProvider::getPrice));
            adapter.filterList((ArrayList<ServiceProvider>) serviceProviderList);
        }else if(text.matches("Price High to low")){
            serviceProviderList.sort(Comparator.comparing(ServiceProvider::getPrice).reversed());
            adapter.filterList((ArrayList<ServiceProvider>) serviceProviderList);
        }else if(text.matches("Rating Low to High")){
            serviceProviderList.sort(Comparator.comparing(ServiceProvider::getRating));
            adapter.filterList((ArrayList<ServiceProvider>) serviceProviderList);
        }else if(text.matches("Rating High to low")){
            serviceProviderList.sort(Comparator.comparing(ServiceProvider::getRating).reversed());
            adapter.filterList((ArrayList<ServiceProvider>) serviceProviderList);
        }
    }
    private List<ServiceProvider> collectData(Map<String, Object> value) {

        for (Map.Entry<String, Object> entry : value.entrySet()){
            Map singleUser = (Map) entry.getValue();
            serviceProviderList.add(new ServiceProvider(singleUser.get("uid").toString(),image,(String) singleUser.get("fname"),(String) singleUser.get("lname"),(String) singleUser.get("phone"),(String) singleUser.get("type"),(String) singleUser.get("address"), (String) singleUser.get("rating"),(String) singleUser.get("pricerat"),(String) singleUser.get("loc")));
            adapter.notifyDataSetChanged();
        }
        return(serviceProviderList);
    }
}