package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_list_view);
        searchserviceprovider=findViewById(R.id.searchserviceprovider);
        searchserviceprovider.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
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

            }
        });
        imageViewBackArrowSPListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    private List<ServiceProvider> collectData(Map<String, Object> value) {

        for (Map.Entry<String, Object> entry : value.entrySet()){
            Map singleUser = (Map) entry.getValue();
            serviceProviderList.add(new ServiceProvider(image,(String) singleUser.get("fname"),(String) singleUser.get("lname"),(String) singleUser.get("phone"),(String) singleUser.get("type"),(String) singleUser.get("address")));
            adapter.notifyDataSetChanged();
        }
        return(serviceProviderList);
    }
}