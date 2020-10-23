package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_list_view);
        imageViewBackArrowSPListView=findViewById(R.id.imageViewBackArrowSPListView);
        serviceProviderList=new ArrayList<>();
        mainmenu=findViewById(R.id.mainmenu);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);
        serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Plumber","E11/4 Islamabad"));
        serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Cleaner","E11/4 Islamabad"));
        serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Electrician","E11/4 Islamabad"));
        serviceProviderList.add(new ServiceProvider(image,"Akash","Ali","03101515786","Carpenter","E11/4 Islamabad"));
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
}