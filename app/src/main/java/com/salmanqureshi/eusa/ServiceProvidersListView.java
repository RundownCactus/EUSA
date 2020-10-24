package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_list_view);
        imageViewBackArrowSPListView=findViewById(R.id.imageViewBackArrowSPListView);
        serviceProviderList=new ArrayList<>();
        mainmenu=findViewById(R.id.mainmenu);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);

        myref = FirebaseDatabase.getInstance().getReference("Users").child("ServiceProviders");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceProviderList = collectData((Map<String,Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       // serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Plumber","E11/4 Islamabad"));
       // serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Cleaner","E11/4 Islamabad"));
       // serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Electrician","E11/4 Islamabad"));
       // serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Carpenter","E11/4 Islamabad"));
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

    private List<ServiceProvider> collectData(Map<String, Object> value) {
        List<ServiceProvider> data = new ArrayList<>();

        for (Map.Entry<String, Object> entry : value.entrySet()){
            Map singleUser = (Map) entry.getValue();
            Log.d("WOW", (String) singleUser.get("fname"));
            Log.d("WOW", (String) singleUser.get("lname"));
            Log.d("WOW", (String) singleUser.get("phone"));
            Log.d("WOW", (String) singleUser.get("type"));
            Log.d("WOW", (String) singleUser.get("address"));
            serviceProviderList.add(new ServiceProvider(image,(String) singleUser.get("fname"),(String) singleUser.get("lname"),(String) singleUser.get("phone"),(String) singleUser.get("type"),(String) singleUser.get("address")));
            serviceprovidersRV.setAdapter(adapter);
        }
        return(data);
    }
}