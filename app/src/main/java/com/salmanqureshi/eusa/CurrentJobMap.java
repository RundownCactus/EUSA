package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentJobMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latLng;
    LatLng Location,userLocation;
    LatLng myLocation;
    String spid,uid,key,worktype,loc,spname,spphno,userLatLng,serviceKey;
    DatabaseReference myref,jobref,jobcancelref,services;
    TextView currentspname;
    ImageView currentjobspcall;
    String sprating;
    MaterialButton booking_cancel1;
    LinearLayout service1,service2,service3;
    TextView s1_title,s2_title,s3_title;
    TextView s1_price,s2_price,s3_price;
    TextView s1_description,s2_description,s3_description;
    List<ServiceDetails> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job_map);
        myList=new ArrayList<>();
        service1=findViewById(R.id.service1);
        service2=findViewById(R.id.service2);
        service3=findViewById(R.id.service3);
        s1_title=findViewById(R.id.s1_title);
        s2_title=findViewById(R.id.s2_title);
        s3_title=findViewById(R.id.s3_title);
        s1_price=findViewById(R.id.s1_price);
        s2_price=findViewById(R.id.s2_price);
        s3_price=findViewById(R.id.s3_price);
        s1_description=findViewById(R.id.s1_description);
        s2_description=findViewById(R.id.s2_description);
        s3_description=findViewById(R.id.s3_description);
        currentspname=findViewById(R.id.currentspname);
        currentjobspcall=findViewById(R.id.currentjobspcall);
        booking_cancel1=findViewById(R.id.booking_cancel1);
        spid = getIntent().getStringExtra("spid");
        uid = getIntent().getStringExtra("uid");
        key = getIntent().getStringExtra("key");
        worktype = getIntent().getStringExtra("type");
        loc =  getIntent().getStringExtra("loc");
        spname=getIntent().getStringExtra("spname");
        spphno=getIntent().getStringExtra("spphno");
        userLatLng=getIntent().getStringExtra("userLatLng");
        serviceKey=getIntent().getStringExtra("serviceKey");
        currentspname.setText(spname);
    }

    @Override
    protected void onResume() {
        super.onResume();
        services=FirebaseDatabase.getInstance().getReference("Services").child(serviceKey);
        services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    Log.d("ABCDE",snap.getValue().toString());
                    if(myList.size()==3) {
                        myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString(), snap.child("isSelected").getValue().toString()));
                        service3.setVisibility(View.VISIBLE);
                        s3_title.setText(snap.child("title").getValue().toString());
                        s3_description.setText(snap.child("description").getValue().toString());
                        s3_price.setText(snap.child("price").getValue().toString());
                    }
                    if(myList.size()==2) {
                        myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString(), snap.child("isSelected").getValue().toString()));
                        service2.setVisibility(View.VISIBLE);
                        s2_title.setText(snap.child("title").getValue().toString());
                        s2_description.setText(snap.child("description").getValue().toString());
                        s2_price.setText(snap.child("price").getValue().toString());
                    }
                    if(myList.size()==1) {
                        myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString(), snap.child("isSelected").getValue().toString()));
                        service1.setVisibility(View.VISIBLE);
                        s1_title.setText(snap.child("title").getValue().toString());
                        s1_description.setText(snap.child("description").getValue().toString());
                        s1_price.setText(snap.child("price").getValue().toString());
                    }
                    if(myList.size()==0) {
                        service1.setVisibility(View.GONE);
                        service2.setVisibility(View.GONE);
                        service3.setVisibility(View.GONE);
                        myList.add(new ServiceDetails(snap.child("title").getValue().toString(), snap.child("price").getValue().toString(), snap.child("description").getValue().toString(), snap.child("key").getValue().toString(), snap.child("isSelected").getValue().toString()));
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //if the user cancel the job this lister function is called and user is redirected to BasicSearch.
        booking_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobCancelTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                DatabaseReference jobCancelTime1=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobCancelTime");
                jobCancelTime1.setValue(jobCancelTime);
                jobcancelref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("status");
                jobcancelref.setValue("Cancel by user after accept");
                Intent intent=new Intent(CurrentJobMap.this,BasicSearch.class);
                startActivity(intent);
                finish();
            }
        });
        //call button lister.
        currentjobspcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri u = Uri.parse("tel:" + spphno);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(CurrentJobMap.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
        String [] loc1 = loc.split(",",2);
        Double lat = Double.parseDouble(loc1[0]);
        Double lon = Double.parseDouble(loc1[1]);
        Location = new LatLng(lat,lon);

        String [] loc2 = userLatLng.split(",",2);
        Double lat1 = Double.parseDouble(loc2[0]);
        Double lon2 = Double.parseDouble(loc2[1]);
        userLocation = new LatLng(lat1,lon2);

        Log.d("TAGAR",spid);
        Log.d("TAGAR",uid);
        Log.d("TAGAR",key);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        jobref= FirebaseDatabase.getInstance().getReference().child("Jobs").child(key);
        jobref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAGATAGA",snapshot.getValue().toString());
                //if the job is accepted by sp then user is able to get location updates.
                if(snapshot.child("status").getValue().toString().equals("Accept"))
                {
                    //Log.d("TAGA",snapshot.getKey());
                    String mysplatlng=snapshot.child("spLatlngStart").getValue().toString();
                    if(!mysplatlng.equals(""))
                    {
                        String [] loc = mysplatlng.split(",",2);
                        Double lat = Double.parseDouble(loc[0]);
                        Double lon = Double.parseDouble(loc[1]);
                        myLocation = new LatLng(lat,lon);
                        Log.d("nananana",Double.toString(lat));
                        Log.d("nananana",Double.toString(lon));
                        Log.d("nananana",mysplatlng);
                        addMarker(worktype,myLocation);

                    }
                }
                if(snapshot.child("status").getValue().toString().equals("Cancel by user after accept") && !(snapshot.child("jobCancelTime").getValue().toString().equals("")))
                {
                    Intent intent=new Intent(CurrentJobMap.this,Homepage.class);
                    startActivity(intent);
                    finish();
                }
                if(snapshot.child("status").getValue().toString().equals("Cancel by SP") && !(snapshot.child("jobCancelTime").getValue().toString().equals("")))
                {
                    Intent intent=new Intent(CurrentJobMap.this,Homepage.class);
                    startActivity(intent);
                    finish();
                }//if the job is completed by service provider.
                if(!(snapshot.child("jobUserRating").getValue().toString().equals("")) && !(snapshot.child("jobCompletionTime").getValue().toString().equals("")) && (snapshot.child("status").getValue().toString().equals("Complete")))
                {
                    //Log.d("TAGA",snapshot.getKey());
                    final AlertDialog.Builder job_complete_alert_dialog=new AlertDialog.Builder(CurrentJobMap.this);
                    View jobCompleteView=getLayoutInflater().inflate(R.layout.job_complete_dialog_box,null);
                    final MaterialButton complete=(MaterialButton)jobCompleteView.findViewById(R.id.booking_complete);

                    final ImageView oneStar=(ImageView)jobCompleteView.findViewById(R.id.onestar);
                    final ImageView twoStar=(ImageView)jobCompleteView.findViewById(R.id.twostar);
                    final ImageView threeStar=(ImageView)jobCompleteView.findViewById(R.id.threestar);
                    final ImageView fourStar=(ImageView)jobCompleteView.findViewById(R.id.fourstar);
                    final ImageView fiveStar=(ImageView)jobCompleteView.findViewById(R.id.fivestar);
                    sprating="0";
                    oneStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            sprating="1";
                        }
                    });
                    twoStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            sprating="2";
                        }
                    });
                    threeStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            sprating="3";
                        }
                    });
                    fourStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_grey);
                            sprating="4";
                        }
                    });
                    fiveStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            oneStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            twoStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            threeStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fourStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            fiveStar.setImageResource(R.drawable.ic_baseline_star_yellow);
                            sprating="5";
                        }
                    });

                    job_complete_alert_dialog.setView(jobCompleteView);
                    final AlertDialog alertDialog=job_complete_alert_dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseReference ratref=FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("jobSPRating");
                            ratref.setValue(sprating);
                            Intent intent=new Intent(CurrentJobMap.this,Homepage.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //function to draw marker on map for tracking.
    public void addMarker(String worktype,LatLng myLocation)
    {
        switch (worktype) {
            case "Car Mechanic":
                mMap.clear();
                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("SP Location").icon(bitmapDescriptorFromVector(CurrentJobMap.this, R.drawable.ic_mechanicmapicon)));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 18);
                mMap.animateCamera(cameraUpdate);
                break;
            case "Carpenter":
                mMap.clear();
                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("SP Location").icon(bitmapDescriptorFromVector(CurrentJobMap.this, R.drawable.ic_carpentermapicon)));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
                CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(myLocation, 18);
                mMap.animateCamera(cameraUpdate1);
                break;
            case "Plumber":
                mMap.clear();
                Log.d("nananana","Plumber");
                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("SP Location").icon(bitmapDescriptorFromVector(CurrentJobMap.this, R.drawable.ic_plumbermapicon)));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
                CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(myLocation, 18);
                mMap.animateCamera(cameraUpdate2);
                break;
            case "Cleaner":
                mMap.clear();
                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("SP Location").icon(bitmapDescriptorFromVector(CurrentJobMap.this, R.drawable.ic_cleanermapicon)));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
                CameraUpdate cameraUpdate3 = CameraUpdateFactory.newLatLngZoom(myLocation, 18);
                mMap.animateCamera(cameraUpdate3);
                break;
            case "Electrician":
                mMap.clear();
                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("SP Location").icon(bitmapDescriptorFromVector(CurrentJobMap.this, R.drawable.ic_electricianmapicon)));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_usermappinicon)));
                CameraUpdate cameraUpdate4 = CameraUpdateFactory.newLatLngZoom(myLocation, 18);
                mMap.animateCamera(cameraUpdate4);
                break;
        }
    }
    //map functions
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //getPermission();


        //LatLng myLocation = new LatLng(33.112859, 73.855685);

        // LatLng Pakistan = null;
        // Pakistan=getLocationFromAddress(this,"House 609, Main Double Road, E11/4, Islamabad");
        // googleMap.addMarker(new MarkerOptions()
        //       .position(Pakistan)
        //     .title("Marker in Pakistan")
        //   .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_carpentermapicon)));

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

    }


    //function that convert image resource id to bitmap descriptor
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorResId);
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
            //mMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},600);
        }

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
}
/*jobref= FirebaseDatabase.getInstance().getReference().child("Jobs");
        jobref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAGATAGA",snapshot.getValue().toString());
                if(snapshot.child("spid").getValue().toString().equals(spid) && snapshot.child("uid").getValue().toString().equals(uid))
                {
                    if(snapshot.child("status").getValue().toString().equals("Accept"))
                    {
                        //Log.d("TAGA",snapshot.getKey());
                        String mysplatlng=snapshot.child("spLatlngStart").getValue().toString();
                        if(!mysplatlng.equals(""))
                        {
                            String [] loc = mysplatlng.split(",",2);
                            Double lat = Double.parseDouble(loc[0]);
                            Double lon = Double.parseDouble(loc[1]);
                            myLocation = new LatLng(lat,lon);
                            Log.d("nananana",Double.toString(lat));
                            Log.d("nananana",Double.toString(lon));
                            Log.d("nananana",mysplatlng);
                            addMarker(worktype,myLocation);

                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/