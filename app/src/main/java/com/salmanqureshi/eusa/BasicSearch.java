package com.salmanqureshi.eusa;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicSearch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
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
    DatabaseReference myref;
    private FirebaseAuth mAuth;
    DatabaseReference myref1;
    List<ServiceProvider> serviceProviderList;
    List<ServiceProvider> newserviceProviderList;
    //NAVIGATION DRAWER VARIABLES START

    //GOOGLE MAPS VARIABLES START
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    LatLng latLng;
    //GOOGLE MAPS VARIABLES END

    //Filter Alert Dialog VARIABLES START
    String filter_dist="2";
    String filter_rat="2";
    //Filter Alert Dialog VARIABLES END

    //SWIPE CARD VIEW VARIABLES START
    ViewPager viewPager;
    Adapter adapter1;
    List<Models> models;
    Integer[] colors=null;
    ArgbEvaluator argbEvaluator=new ArgbEvaluator();
    //SWIPE CARD VIEW VARIABLES END

    //FIREBASE VARIABLES START
    //FIREBASE VARIABLES END
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_search);
        Log.d("basicsearchCalled", "onCreate Called");



        contacts=new ArrayList<>();
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        mainmenu=findViewById(R.id.mainmenu);
        image= BitmapFactory.decodeResource(getResources(),R.drawable.profile1);
        Log.d("BC", mAuth.getInstance().getCurrentUser().getUid());
        searchserviceprovideronmapinput=findViewById(R.id.searchserviceprovideronmapinput);
        searchserviceprovideronmapinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicSearch.this,ServiceProvidersListView.class);
                intent.putExtra("type","All");
                startActivity(intent);
            }
        });

        //contacts.add(new Contact(image,"Akash","03101515786","i170019@nu.edu.pk","Islamabad"));

        //MAP FRAGMENT
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        rlp.setMargins(0, 166, 44, 0);

        //MAP FRAGMENT

        //SWIPE CARD VIEW START
        models=new ArrayList<>();
        models.add(new Models(R.drawable.carpentericon,"Carpenter"));
        models.add(new Models(R.drawable.electricianicon,"Electrician"));
        models.add(new Models(R.drawable.plumbericon,"Plumber"));
        models.add(new Models(R.drawable.cleanericon,"Cleaner"));
        models.add(new Models(R.drawable.mechanicicon,"Car Mechanic"));

        adapter1=new Adapter(models,this);

        viewPager=findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter1);
        viewPager.setPadding(130,0,130,0);
        Integer[] color_temp={
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };
        colors=color_temp;

        serviceProviderList=new ArrayList<>();
        newserviceProviderList=new ArrayList<>();
        myref = FirebaseDatabase.getInstance().getReference("Users").child("ServiceProviders");
        Query getTypeSP = myref.orderByChild("type");
        getTypeSP.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceProviderList = collectData((Map<String, Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //SWIPE CARD VIEW END
    }
    private List<ServiceProvider> collectData(Map<String, Object> value) {
        for (Map.Entry<String, Object> entry : value.entrySet()){
            Map singleUser = (Map) entry.getValue();
            serviceProviderList.add(new ServiceProvider(image,(String) singleUser.get("fname"),(String) singleUser.get("lname"),(String) singleUser.get("phone"),(String) singleUser.get("type"),(String) singleUser.get("address"), (String) singleUser.get("rating"),(String) singleUser.get("pricerat"),(String) singleUser.get("loc")));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMap.clear();
                if(latLng!=null){
                    //Toast.makeText(BasicSearch.this,Integer.toString(serviceProviderList.size()),Toast.LENGTH_SHORT).show();
                    for (ServiceProvider sp :serviceProviderList) {
                        if(models.get(position).getTitle().equals(sp.getWorktype())) {

                            String addr =  sp.getLoc().toString();
                            String [] loc = addr.split(",",2);
                            Double lat = Double.parseDouble(loc[0]);
                            Double lon = Double.parseDouble(loc[1]);
                            LatLng myLocation = new LatLng(lat,lon);
                            float[] results = new float[1];
                            Location.distanceBetween(latLng.latitude, latLng.longitude,
                                    lat, lon,
                                    results);
                            //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();

                            int myDist = 0;
                            try {
                                myDist = Integer.parseInt(filter_dist);
                            } catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }

                            float myRat = 0;
                            try {
                                myRat = Float.parseFloat(filter_rat);
                            } catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }

                            float mySpRat = 0;
                            try {
                                mySpRat = Float.parseFloat(sp.getRating());
                            } catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }

                            if(results[0]<(myDist*1000) && (mySpRat>=myRat)) {
                                switch (sp.getWorktype()) {
                                    case "Car Mechanic":
                                        //mMap.clear();
                                        //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_mechanicmapicon)));
                                        break;
                                    case "Carpenter":
                                        //mMap.clear();
                                        //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_carpentermapicon)));
                                        break;
                                    case "Plumber":
                                        //mMap.clear();
                                        //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_plumbermapicon)));
                                        break;
                                    case "Cleaner":
                                        //mMap.clear();
                                        //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_cleanermapicon)));
                                        break;
                                    case "Electrician":
                                        //mMap.clear();
                                        //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_electricianmapicon)));
                                        break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*

            // LatLng Pakistan = null;
            // Pakistan=getLocationFromAddress(this,"House 609, Main Double Road, E11/4, Islamabad");
            // googleMap.addMarker(new MarkerOptions()
            //       .position(Pakistan)
            //     .title("Marker in Pakistan")
            //   .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_carpentermapicon)));
        }*/
        mMap.setOnMarkerClickListener(marker -> {
            for (ServiceProvider sp :serviceProviderList) {
                if(marker.getSnippet().equals(sp.getPhone()))
                {
                    //Toast.makeText(BasicSearch.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();// display toast

                    final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog( BasicSearch.this,R.style.BottomSheetDialogTheme);
                    View bottomSheetView= LayoutInflater.from(BasicSearch.this).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomsheet_cl));
                    TextView myname=bottomSheetView.findViewById(R.id.myname);
                    TextView myrating=bottomSheetView.findViewById(R.id.myrating);
                    ImageView myimage=bottomSheetView.findViewById(R.id.myimage);
                    ImageView worktypeicon=bottomSheetView.findViewById(R.id.worktypeicon);
                    TextView worktypetext=bottomSheetView.findViewById(R.id.worktypetext);
                    myimage.setImageBitmap(sp.getImage());
                    myname.setText(sp.getFname()+" "+sp.getLname());
                    myrating.setText(sp.getRating());
                    worktypetext.setText(sp.getWorktype());
                    switch (sp.getWorktype()) {
                        case "Plumber":
                            worktypeicon.setImageResource(R.drawable.plumbericon);
                            break;
                        case "Carpenter":
                            worktypeicon.setImageResource(R.drawable.carpentericon);
                            break;
                        case "Cleaner":
                            worktypeicon.setImageResource(R.drawable.cleanericon);
                            break;
                        case "Car Mechanic":
                            worktypeicon.setImageResource(R.drawable.mechanicicon);
                            break;
                        default:
                            worktypeicon.setImageResource(R.drawable.electricianicon);
                            break;
                    }

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();

                }

            }

            return true;
        });
        return(serviceProviderList);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        serviceprovidersRV=findViewById(R.id.serviceprovidersRV);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        serviceprovidersRV.setLayoutManager(lm);
        adapter=new MyRvAdapter(contacts,this);
        serviceprovidersRV.setAdapter(adapter);

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
                Intent intent=new Intent(BasicSearch.this,EditProfile.class);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BasicSearch.this,EditProfile.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        adapter.setOnItemClickListener(new MyRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        //NAVIGATION DRAWER CODE END

    }

    //NAVIGATION DRAWER FUNCTIONS START
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_history:
                Intent intent=new Intent(BasicSearch.this,History.class);
                startActivity(intent);
                break;
            case R.id.nav_payment:
                Intent intent1=new Intent(BasicSearch.this,Payment.class);
                startActivity(intent1);
                break;
            case R.id.nav_settings:
                Intent intent2=new Intent(BasicSearch.this,Settings.class);
                startActivity(intent2);
                break;
            case R.id.nav_notifications:
                Intent intent3=new Intent(BasicSearch.this,Notifications.class);
                startActivity(intent3);
                break;
            case R.id.nav_contactus:
                Intent intent4=new Intent(BasicSearch.this,ContactUs.class);
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
            super.onBackPressed();
        }
    }
    //NAVIGATION DRAWER FUNCTIONS END

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //MAP FUNCTIONS
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getPermission();


        // LatLng Pakistan = null;
       // Pakistan=getLocationFromAddress(this,"House 609, Main Double Road, E11/4, Islamabad");
       // googleMap.addMarker(new MarkerOptions()
         //       .position(Pakistan)
           //     .title("Marker in Pakistan")
             //   .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_carpentermapicon)));
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                // Add a marker in Sydney and move the camera
                //CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                //LatLng myLocation = new LatLng(33.699989, 73.001916);
                //mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng());
                //mMap.animateCamera(zoom);
            }



            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mLocationListener);
    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable=ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private  void getPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            mMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},600);
        }

    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==600)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

            }
        }
    }

    public void show_filter_dialog(View view) {
        final AlertDialog.Builder filter=new AlertDialog.Builder(BasicSearch.this);
        View filterView=getLayoutInflater().inflate(R.layout.filters_dialog_box,null);
        final MaterialButton cancel=(MaterialButton)filterView.findViewById(R.id.filter_cancel);
        final MaterialButton apply=(MaterialButton)filterView.findViewById(R.id.filter_apply);

        String[] dist = new String[] {"1", "2","3", "4","5"};

        ArrayAdapter newAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        dist);

        final AutoCompleteTextView distAutoCompleteTextView =(AutoCompleteTextView)filterView. findViewById(R.id.getdistancedropdown);
        distAutoCompleteTextView.setText(filter_dist);
        distAutoCompleteTextView.setAdapter(newAdapter);




        String[] rat = new String[] {"1", "2","3", "4","5"};

        ArrayAdapter newAdapter1 =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        rat);

        final AutoCompleteTextView ratAutoCompleteTextView =(AutoCompleteTextView)filterView. findViewById(R.id.getratingdropdown);
        ratAutoCompleteTextView.setText(filter_rat);
        ratAutoCompleteTextView.setAdapter(newAdapter1);


        filter.setView(filterView);
        final AlertDialog alertDialog=filter.create();
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_dist=distAutoCompleteTextView.getText().toString();
                filter_rat=ratAutoCompleteTextView.getText().toString();
                Log.d("me",filter_dist);
                Log.d("me",filter_rat);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    //MAP FUNCTIONS
}