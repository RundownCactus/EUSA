package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //NAVIGATION DRAWER VARIABLES START
    DrawerLayout drawerLayout;
    Integer REQUEST_CODE=1;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView serviceprovidersRV;
    ImageView mainmenu;
    TextView text;
    ImageView profileImage;
    List<ServiceProvider> contacts;
    MyRvAdapter adapter;
    Bitmap image;
    MaterialButton searchserviceprovideronmapinput;
    FirebaseDatabase rootnode;
    DatabaseReference myref,jobref,joncancelref;
    private FirebaseAuth mAuth;
    DatabaseReference myref1;
    List<ServiceProvider> serviceProviderList;
    List<ServiceProvider> newserviceProviderList;
    DatabaseReference key;
    //NAVIGATION DRAWER VARIABLES START



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        mainmenu=findViewById(R.id.mainmenu);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //NAVIGATION DRAWER CODE START
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.username);

        rootnode = FirebaseDatabase.getInstance();
        myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid());

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text.setText(snapshot.child("fname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileImage=(ImageView) header.findViewById(R.id.circleImageView);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this,EditProfile.class);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homepage.this,EditProfile.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //NAVIGATION DRAWER CODE END
    }
    //NAVIGATION DRAWER FUNCTIONS START
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_history:
                Intent intent=new Intent(Homepage.this,History.class);
                startActivity(intent);
                break;
            case R.id.nav_payment:
                Intent intent1=new Intent(Homepage.this,Payment.class);
                startActivity(intent1);
                break;
            case R.id.nav_settings:
                Intent intent2=new Intent(Homepage.this,Settings.class);
                startActivity(intent2);
                break;
            case R.id.nav_notifications:
                Intent intent3=new Intent(Homepage.this,Notifications.class);
                startActivity(intent3);
                break;
            case R.id.nav_contactus:
                Intent intent4=new Intent(Homepage.this,ContactUs.class);
                startActivity(intent4);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }
    //NAVIGATION DRAWER FUNCTIONS END


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}