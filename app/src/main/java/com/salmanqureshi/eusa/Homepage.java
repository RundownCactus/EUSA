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
import android.net.LinkAddress;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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


    //Hommepage
    ImageView homepage_profile;
    LinearLayout select_location_ll;
    RelativeLayout location_book_button;
    TextView recentspname,recentservicetime;
    MaterialCardView book_plumber,book_electrician,book_carmechanic,book_carpenter,book_cleaner,book_mechanic,basicsrch,listsrch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        recentspname=findViewById(R.id.recentspname);
        recentservicetime=findViewById(R.id.recentservicetime);
        book_plumber=findViewById(R.id.book_plumber);
        book_electrician=findViewById(R.id.book_electrician);
        book_carmechanic=findViewById(R.id.book_carmechanic);
        book_carpenter=findViewById(R.id.book_carpenter);
        book_cleaner=findViewById(R.id.book_cleaner);
        book_mechanic=findViewById(R.id.book_mechanic);
        select_location_ll=findViewById(R.id.select_location_ll);
        location_book_button=findViewById(R.id.location_book_button);
        homepage_profile=findViewById(R.id.homepage_profile);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        mainmenu=findViewById(R.id.mainmenu);
        basicsrch=findViewById(R.id.basicsearch);
        listsrch=findViewById(R.id.listsearch);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        book_plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                intent.putExtra("type","Plumber");
                startActivity(intent);
            }
        });
        book_electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                intent.putExtra("type","Electrician");
                startActivity(intent);
            }
        });
        book_carmechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                intent.putExtra("type","Car Mechanic");
                startActivity(intent);
            }
        });
        book_carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                intent.putExtra("type","Carpenter");
                startActivity(intent);
            }
        });
        book_cleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                intent.putExtra("type","Cleaner");
                startActivity(intent);
            }
        });
        book_mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(Homepage.this,SearchServiceProvider.class);
                //intent.putExtra("type","mechanic");
                //startActivity(intent);
            }
        });
        location_book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,BasicSearch.class);
                startActivity(intent);
            }
        });
        select_location_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,BasicSearch.class);
                startActivity(intent);
            }
        });
        homepage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,EditProfile.class);
                startActivity(intent);
            }
        });
        listsrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,ServiceProvidersListView.class);
                startActivity(intent);
            }
        });
        basicsrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this,BasicSearch.class);
                startActivity(intent);
            }
        });

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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid()).child("Jobs");
        Query lastQuery = databaseReference.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] arrayString = dataSnapshot.getValue().toString().split("=");
                String str1=arrayString[0];
                String str2=str1.substring(1);

                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Jobs").child(str2);
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try{
                            if(!(snapshot.child("jobCompletionTime").getValue().toString().equals("")) && snapshot.child("status").getValue().toString().equals("Complete"))
                            {
                                String[] str1=snapshot.child("jobCompletionTime").getValue().toString().split(" ");
                                String str2=str1[1];
                                String str3=str2.substring(0,5);
                                recentservicetime.setText(str3);
                            }
                            else if(!(snapshot.child("jobRejectTime").getValue().toString().equals("")) && snapshot.child("status").getValue().toString().equals("Job Rejected by SP"))
                            {
                                String[] str1=snapshot.child("jobRejectTime").getValue().toString().split(" ");
                                String str2=str1[1];
                                String str3=str2.substring(0,5);
                                recentservicetime.setText(str3);
                            }
                            else if(!(snapshot.child("jobCancelTime").getValue().toString().equals("")))
                            {
                                String[] str1=snapshot.child("jobCancelTime").getValue().toString().split(" ");
                                String str2=str1[1];
                                String str3=str2.substring(0,5);
                                recentservicetime.setText(str3);
                            }
                        }
                        catch(Exception e){
                            return;

                        }


                        DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(snapshot.child("spid").getValue().toString());
                        databaseRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("Total List 2", snapshot.getValue().toString() );
                                recentspname.setText(snapshot.child("fname").getValue().toString()+" "+snapshot.child("lname").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
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