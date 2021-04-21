package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class SearchServiceProvider extends AppCompatActivity implements OnMapReadyCallback {
    //NAVIGATION DRAWER VARIABLES START
    DrawerLayout drawerLayout;
    Integer REQUEST_CODE=1;
    NavigationView navigationView;
    List<Models> models;
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
    DatabaseReference myref,jobref,joncancelref,serviceKey;
    List<String> namelist = new ArrayList<String>();

    private FirebaseAuth mAuth;
    DatabaseReference myref1;
    List<ServiceProvider> serviceProviderList;
    List<ServiceProvider> newserviceProviderList;
    DatabaseReference key;
    //NAVIGATION DRAWER VARIABLES START

    //GOOGLE MAPS VARIABLES START
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    LatLng latLng;
    //GOOGLE MAPS VARIABLES END
    //Filter Alert Dialog VARIABLES START
    String filter_dist="5";
    String filter_rat="1";
    //Filter Alert Dialog VARIABLES END
    MaterialCardView backbutton;
    //SKYLINE QUERY Variables
    List<Float> skylineDist;
    List<Float> skylineRat;
    List<String> skylineUid;
    List<Marker> markersList;
    ProgressBar bestRecommendationProgressbar;
    TextView loadingBestRecommendation;
    MaterialCardView progressBar_cardView;

    //BottomSheet variables
    List<ServiceDetails> myList;
    RelativeLayout loadingBackground;
    ProgressBar maps_progressbar;
    TextView loadingText,text1;
    ImageView locationButton;
    ProgressBar simpleProgressBar;

    //Nearby SP variables start
    RecyclerView nearbyspRV;
    List<ServiceProvider> nearbyspList;
    NearbySPAdapter nearbyspAdapter;
    TextView nearbySP;
    //Nearby SP variables send

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service_provider);
        nearbySP=findViewById(R.id.nearbySP);
        nearbySP.setText("Nearby "+getIntent().getStringExtra("type")+"s");
        nearbyspList=new ArrayList<>();

        simpleProgressBar=findViewById(R.id.simpleProgressBar);
        text1=findViewById(R.id.text1);
        loadingText=findViewById(R.id.loadingText);
        //loadingText.setVisibility(View.VISIBLE);
        maps_progressbar=findViewById(R.id.maps_progressbar);
        maps_progressbar.setVisibility(View.VISIBLE);
        loadingBackground=findViewById(R.id.loadingBackground);
        loadingBackground.setVisibility(View.VISIBLE);
        namelist.add("Carpenter");
        namelist.add("Electrician");
        namelist.add("Plumber");
        namelist.add("Cleaner");
        namelist.add("Car Mechanic");
        myList=new ArrayList<>();
        myList.add(new ServiceDetails("ABC","DEF","GHI","-ABC","No"));
        skylineDist= new ArrayList<Float>();
        skylineRat= new ArrayList<Float>();
        skylineUid=new ArrayList<String>();
        markersList=new ArrayList<Marker>();
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationButton = (ImageView) mapFragment.getView().findViewById(Integer.parseInt("2"));
        locationButton.setImageResource(R.drawable.ic_gps_focus);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
        rlp.setMarginEnd(50);
        rlp.setMargins(0, 256, 0, 0);
        rootnode = FirebaseDatabase.getInstance();

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });



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
    //MAP FUNCTIONS
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getPermission();

        mMap.clear();


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
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                //mMap.animateCamera(cameraUpdate);
                mMap.moveCamera(cameraUpdate);
                //loadingText.setVisibility(View.GONE);
                maps_progressbar.setVisibility(View.GONE);
                loadingBackground.setVisibility(View.GONE);
                text1.performClick();
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

    public class GetBestSuggestion extends AsyncTask<SearchServiceProvider.BestSuggestionParams, Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(SearchServiceProvider.BestSuggestionParams... params) {
            int[] i = {0};
            if(!params[0].uid.isEmpty()) {

                String postUrl = "https://skylineq.herokuapp.com/";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("dist",params[0].dist.toString() );
                    jsonObject.put("rat",params[0].rat.toString() );
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("LETS SEEE 1", "onErrorResponse: (");
                }
                ArrayList<String> results = new ArrayList<String>();

                Log.d("LETS SEEE 2", "onErrorResponse: (");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            Log.d("WHAT I AM APPENDING",response.get("result").toString());

                            results.add(response.get("result").toString());
                            results.add(String.valueOf(params[0].pos));
                            i[0] = 1;
                        } catch (JSONException e) {
                            Log.d("LETS SEEE323", "onErrorResponse: (");
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LETS SEEE4", "onErrorResponse: (");
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);

                //if (!Python.isStarted()) {
                //  Python.start(new AndroidPlatform(getApplicationContext()));
                // }

                //Python py = Python.getInstance();
                //PyObject pyObj = py.getModule("test");
                //Pass arguments to func
                //PyObject obj = pyObj.callAttr("wow","arg1","arg2".....);
                //PyObject obj = pyObj.callAttr("wow",params[0].dist.toString(),params[0].rat.toString());
                //Log.d("IF THIS WORKS I AM GOD", obj.toString());
                //Toast.makeText(BasicSearch.this, obj.toString(), Toast.LENGTH_SHORT).show();
                while(i[0] != 1){
                    Log.d("LOGGGASDASD", "doInBackground: ");
                    continue;
                }
                return (results);

            }
            else
            {
                ArrayList<String> results = new ArrayList<String>();
                results.add("none");
                results.add(String.valueOf(params[0].pos));
                return results;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            simpleProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<String> results) {
            super.onPostExecute(results);
            simpleProgressBar.setVisibility(View.GONE);

            int bestSpInt = -1;
            String bestSP = results.get(0);
            try {
                bestSpInt = Integer.decode(bestSP);
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            if(!bestSP.equals("none"))
            {
                for (ServiceProvider sp :serviceProviderList) {
                    if(namelist.get(Integer.decode(results.get(1))).equals(sp.getWorktype())) {
                        String addr =  sp.getLoc().toString();
                        String [] loc = addr.split(",",2);
                        Double lat = Double.parseDouble(loc[0]);
                        Double lon = Double.parseDouble(loc[1]);
                        LatLng myLocation = new LatLng(lat,lon);
                        float[] result = new float[1];
                        Location.distanceBetween(latLng.latitude, latLng.longitude,
                                lat, lon,
                                result);
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

                        if(result[0]<(myDist*1000) && (mySpRat>=myRat)) {
                            switch (sp.getWorktype()) {
                                case "Car Mechanic":
                                    if(sp.getUid().equals(skylineUid.get(bestSpInt)))
                                    {
                                        markersList.get(bestSpInt).remove();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_bestmechanicmapicon)));
                                        nearbyspList.get(bestSpInt).setBest();
                                        nearbyspAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                case "Carpenter":
                                    if(sp.getUid().equals(skylineUid.get(bestSpInt)))
                                    {
                                        markersList.get(bestSpInt).remove();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_bestcarpentermapicon)));
                                        nearbyspList.get(bestSpInt).setBest();
                                        nearbyspAdapter.notifyDataSetChanged();
                                        break;
                                    }

                                case "Plumber":
                                    if(sp.getUid().equals(skylineUid.get(bestSpInt)))
                                    {
                                        markersList.get(bestSpInt).remove();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_bestplumbermapicon)));
                                        nearbyspList.get(bestSpInt).setBest();
                                        nearbyspAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                case "Cleaner":
                                    if(sp.getUid().equals(skylineUid.get(bestSpInt)))
                                    {
                                        markersList.get(bestSpInt).remove();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_bestcleanermapicon)));
                                        nearbyspList.get(bestSpInt).setBest();
                                        nearbyspAdapter.notifyDataSetChanged();
                                        break;
                                    }

                                case "Electrician":
                                    if(sp.getUid().equals(skylineUid.get(bestSpInt)))
                                    {
                                        markersList.get(bestSpInt).remove();
                                        mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_bestelectricianmapicon)));
                                        nearbyspList.get(bestSpInt).setBest();
                                        nearbyspAdapter.notifyDataSetChanged();
                                        break;
                                    }
                            }
                        }
                    }
                }



            }


        }
    }

    private static class BestSuggestionParams {
        List<String> uid;
        List <Float> dist;
        List <Float> rat;
        int pos;
        List<Marker> markers;

        public BestSuggestionParams(List<String> uid, List<Float> dist, List<Float> rat, int pos,List<Marker> markers) {
            this.uid = uid;
            this.dist = dist;
            this.rat = rat;
            this.pos = pos;
            this.markers=markers;
        }
    }

    private List<ServiceProvider> collectData(Map<String, Object> value) {
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            serviceProviderList.add(new ServiceProvider(singleUser.get("uid").toString(), image, (String) singleUser.get("fname"), (String) singleUser.get("lname"), (String) singleUser.get("phone"), (String) singleUser.get("type"), (String) singleUser.get("address"), (String) singleUser.get("rating"), (String) singleUser.get("pricerat"), (String) singleUser.get("loc"),(String) singleUser.get("isAvailable")));
        }
        mMap.clear();
        skylineDist.clear();
        skylineRat.clear();
        skylineUid.clear();
        markersList.clear();
        //Nearby bottomsheet start

        nearbyspRV = findViewById(R.id.nearbyspRV);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        nearbyspRV.setLayoutManager(lm);
        nearbyspAdapter=new NearbySPAdapter(nearbyspList,this);
        nearbyspRV.setAdapter(nearbyspAdapter);
        nearbyspAdapter.setOnItemClickListener(new NearbySPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                String[] latlong =  nearbyspList.get(position).getLoc().split(",");
                double latitude = Double.parseDouble(latlong[0]);
                double longitude = Double.parseDouble(latlong[1]);
                LatLng location = new LatLng(latitude, longitude);


                Log.d("HA", mMap.getCameraPosition().target.toString());

                Log.d("HA2", location.toString());

                if(mMap.getCameraPosition().target.toString().equals(location.toString()) ){
                    onMarkerClick(markersList.get(position));
                    Log.d("HAANJE", "onItemClick: ");
                }
                else{
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                }
            }


        });


        //Nearby bottomsheet end

        if (latLng != null) {
            //Toast.makeText(BasicSearch.this,Integer.toString(serviceProviderList.size()),Toast.LENGTH_SHORT).show();
            for (ServiceProvider sp : serviceProviderList) {
                if (getIntent().getStringExtra("type").toString().equals(sp.getWorktype())) {




                    String addr = sp.getLoc().toString();
                    String[] loc = addr.split(",", 2);
                    Double lat = Double.parseDouble(loc[0]);
                    Double lon = Double.parseDouble(loc[1]);
                    LatLng myLocation = new LatLng(lat, lon);
                    float[] results = new float[1];
                    Location.distanceBetween(latLng.latitude, latLng.longitude,
                            lat, lon,
                            results);
                    //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();

                    int myDist = 0;
                    try {
                        myDist = Integer.parseInt(filter_dist);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                    float myRat = 0;
                    try {
                        myRat = Float.parseFloat(filter_rat);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                    float mySpRat = 0;
                    try {
                        mySpRat = Float.parseFloat(sp.getRating());
                    } catch (NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                    if (results[0] < (myDist * 1000) && (mySpRat >= myRat)) {
                        switch (sp.getWorktype()) {
                            case "Car Mechanic":
                                skylineDist.add(results[0]);
                                skylineRat.add(mySpRat);
                                skylineUid.add(sp.getUid());
                                //mMap.clear();
                                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                nearbyspList.add(new ServiceProvider(sp.getUid(),sp.getImage(), sp.getFname(), sp.getLname(), sp.getPhone(), sp.getWorktype(),sp.getAddress(),sp.getRating(),sp.getPrice(),sp.getLoc(),sp.getIsAvailable()));
                                markersList.add(mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_mechanicmapicon))));
                                break;
                            case "Carpenter":
                                skylineDist.add(results[0]);
                                skylineRat.add(mySpRat);
                                skylineUid.add(sp.getUid());
                                //mMap.clear();
                                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                nearbyspList.add(new ServiceProvider(sp.getUid(),sp.getImage(), sp.getFname(), sp.getLname(), sp.getPhone(), sp.getWorktype(),sp.getAddress(),sp.getRating(),sp.getPrice(),sp.getLoc(), sp.getIsAvailable()));

                                markersList.add(mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_carpentermapicon))));
                                break;
                            case "Plumber":
                                skylineDist.add(results[0]);
                                skylineRat.add(mySpRat);
                                skylineUid.add(sp.getUid());
                                //mMap.clear();
                                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                nearbyspList.add(new ServiceProvider(sp.getUid(),sp.getImage(), sp.getFname(), sp.getLname(), sp.getPhone(), sp.getWorktype(),sp.getAddress(),sp.getRating(),sp.getPrice(),sp.getLoc(), sp.getIsAvailable()));
                                markersList.add(mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_plumbermapicon))));
                                break;
                            case "Cleaner":
                                skylineDist.add(results[0]);
                                skylineRat.add(mySpRat);
                                skylineUid.add(sp.getUid());
                                //mMap.clear();
                                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                nearbyspList.add(new ServiceProvider(sp.getUid(),sp.getImage(), sp.getFname(), sp.getLname(), sp.getPhone(), sp.getWorktype(),sp.getAddress(),sp.getRating(),sp.getPrice(),sp.getLoc(),sp.getIsAvailable()));
                                markersList.add(mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_cleanermapicon))));
                                break;
                            case "Electrician":
                                skylineDist.add(results[0]);
                                skylineRat.add(mySpRat);
                                skylineUid.add(sp.getUid());
                                //mMap.clear();
                                //Toast.makeText(BasicSearch.this, String.valueOf(results[0]), Toast.LENGTH_SHORT).show();
                                nearbyspList.add(new ServiceProvider(sp.getUid(),sp.getImage(), sp.getFname(), sp.getLname(), sp.getPhone(), sp.getWorktype(),sp.getAddress(),sp.getRating(),sp.getPrice(),sp.getLoc(),sp.getIsAvailable()));
                                markersList.add(mMap.addMarker(new MarkerOptions().position(myLocation).title(sp.getFname() + " " + sp.getLname()).snippet(sp.getPhone()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_electricianmapicon))));
                                break;
                        }
                    }
                }
            }
            Log.d("dooo", skylineDist.toString() + "," + skylineRat.toString() + "," + skylineUid);
            SearchServiceProvider.BestSuggestionParams myparams = new BestSuggestionParams(skylineUid, skylineDist, skylineRat, namelist.indexOf(getIntent().getStringExtra("type")), markersList);
            new SearchServiceProvider.GetBestSuggestion().execute(myparams);
        }
        mMap.setOnMarkerClickListener(this::onMarkerClick);
        return(serviceProviderList);
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
    //function to show the filter dialog box.
    public void show_filter_dialog(View view) {
        final AlertDialog.Builder filter=new AlertDialog.Builder(SearchServiceProvider.this);
        View filterView=getLayoutInflater().inflate(R.layout.filters_dialog_box,null);
        final MaterialButton cancel=(MaterialButton)filterView.findViewById(R.id.filter_cancel);
        final MaterialButton apply=(MaterialButton)filterView.findViewById(R.id.filter_apply);

        String[] dist = new String[] {"1", "2","3", "4","5"};

        ArrayAdapter newAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        dist);

        final AutoCompleteTextView distAutoCompleteTextView =(AutoCompleteTextView)filterView.findViewById(R.id.getdistancedropdown);
        distAutoCompleteTextView.setText(filter_dist);
        distAutoCompleteTextView.setAdapter(newAdapter);


        String[] rat = new String[] {"1", "2","3", "4","5"};

        ArrayAdapter newAdapter1 =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu,
                        rat);

        final AutoCompleteTextView ratAutoCompleteTextView =(AutoCompleteTextView)filterView.findViewById(R.id.getratingdropdown);
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
                text1.performClick();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    //function when you click on particular service provider icon on the map and bottom sheet appears to book the service provider.
    private boolean onMarkerClick(Marker marker) {
        myList.clear();
        myList.add(new ServiceDetails("ABC","DEF","GHI","IJK","No"));
        for (ServiceProvider sp : serviceProviderList) {
            if (marker.getSnippet().equals(sp.getPhone())) {
                //Toast.makeText(BasicSearch.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();// display toast

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchServiceProvider.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(SearchServiceProvider.this).inflate(R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomsheet_cl));
                TextView myname = bottomSheetView.findViewById(R.id.myname);
                TextView myrating = bottomSheetView.findViewById(R.id.myrating);
                ImageView myimage = bottomSheetView.findViewById(R.id.myimage);
                ImageView worktypeicon = bottomSheetView.findViewById(R.id.worktypeicon);
                TextView worktypetext = bottomSheetView.findViewById(R.id.worktypetext);
                MaterialButton book_button = bottomSheetView.findViewById(R.id.book_button);
                MaterialButton call_button = bottomSheetView.findViewById(R.id.call_button);
                //myimage.setImageBitmap(sp.getImage());
                myname.setText(sp.getFname() + " " + sp.getLname());
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
                LinearLayout service1=bottomSheetView.findViewById(R.id.service1);
                LinearLayout service2=bottomSheetView.findViewById(R.id.service2);
                LinearLayout service3=bottomSheetView.findViewById(R.id.service3);
                ImageView s1_tickIcon_blue=bottomSheetView.findViewById(R.id.s1_tickIcon_blue);
                ImageView s2_tickIcon_blue=bottomSheetView.findViewById(R.id.s2_tickIcon_blue);
                ImageView s3_tickIcon_blue=bottomSheetView.findViewById(R.id.s3_tickIcon_blue);
                ImageView s1_tickIcon_gray=bottomSheetView.findViewById(R.id.s1_tickIcon_gray);
                ImageView s2_tickIcon_gray=bottomSheetView.findViewById(R.id.s2_tickIcon_gray);
                ImageView s3_tickIcon_gray=bottomSheetView.findViewById(R.id.s3_tickIcon_gray);
                TextView s1_title=bottomSheetView.findViewById(R.id.s1_title);
                TextView s2_title=bottomSheetView.findViewById(R.id.s2_title);
                TextView s3_title=bottomSheetView.findViewById(R.id.s3_title);
                TextView s1_description=bottomSheetView.findViewById(R.id.s1_description);
                TextView s2_description=bottomSheetView.findViewById(R.id.s2_description);
                TextView s3_description=bottomSheetView.findViewById(R.id.s3_description);
                TextView s1_price=bottomSheetView.findViewById(R.id.s1_price);
                TextView s2_price=bottomSheetView.findViewById(R.id.s2_price);
                TextView s3_price=bottomSheetView.findViewById(R.id.s3_price);
                DatabaseReference myref1= FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(sp.getUid()).child("Services");
                service1.setVisibility(View.GONE);
                service2.setVisibility(View.GONE);
                service3.setVisibility(View.GONE);
                myref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            DatabaseReference serviceref=FirebaseDatabase.getInstance().getReference().child("Users").child("ServiceProviders").child(sp.getUid()).child("Services").child(snap.getKey());
                            serviceref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(myList.size()==1)
                                    {
                                        Log.d("TAGA",snapshot.getKey());
                                        Log.d("TAGA",snapshot.child("title").getValue().toString());
                                        Log.d("TAGA","Rs. " + snapshot.child("price").getValue().toString());
                                        Log.d("TAGA",snapshot.child("description").getValue().toString());
                                        myList.add(new ServiceDetails(snapshot.child("title").getValue().toString(), "Rs. " + snapshot.child("price").getValue().toString(),
                                                snapshot.child("description").getValue().toString(), snapshot.getKey().toString(),"No"));
                                        service1.setVisibility(View.VISIBLE);
                                        s1_title.setText(myList.get(1).getTitle());
                                        s1_price.setText(myList.get(1).getPrice());
                                        s1_description.setText(myList.get(1).getDescription());
                                    }
                                    else if(myList.size()==2)
                                    {
                                        Log.d("TAGA",snapshot.getKey());
                                        Log.d("TAGA",snapshot.child("title").getValue().toString());
                                        Log.d("TAGA","Rs. " + snapshot.child("price").getValue().toString());
                                        Log.d("TAGA",snapshot.child("description").getValue().toString());
                                        myList.add(new ServiceDetails(snapshot.child("title").getValue().toString(), "Rs. " + snapshot.child("price").getValue().toString(),
                                                snapshot.child("description").getValue().toString(), snapshot.getKey().toString(),"No"));
                                        service2.setVisibility(View.VISIBLE);
                                        s2_title.setText(myList.get(2).getTitle());
                                        s2_price.setText(myList.get(2).getPrice());
                                        s2_description.setText(myList.get(2).getDescription());
                                    }
                                    else if(myList.size()==3)
                                    {
                                        Log.d("TAGA",snapshot.getKey());
                                        Log.d("TAGA",snapshot.child("title").getValue().toString());
                                        Log.d("TAGA","Rs. " + snapshot.child("price").getValue().toString());
                                        Log.d("TAGA",snapshot.child("description").getValue().toString());
                                        myList.add(new ServiceDetails(snapshot.child("title").getValue().toString(), "Rs. " + snapshot.child("price").getValue().toString(),
                                                snapshot.child("description").getValue().toString(), snapshot.getKey().toString(),"No"));
                                        service3.setVisibility(View.VISIBLE);
                                        s3_title.setText(myList.get(3).getTitle());
                                        s3_price.setText(myList.get(3).getPrice());
                                        s3_description.setText(myList.get(3).getDescription());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                service1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(s1_tickIcon_gray.getVisibility()==View.VISIBLE) {
                            s1_tickIcon_blue.setVisibility(View.VISIBLE);
                            s1_tickIcon_gray.setVisibility(View.GONE);
                            myList.get(1).setIsSelected("Yes");
                        }
                        else
                        {
                            s1_tickIcon_blue.setVisibility(View.GONE);
                            s1_tickIcon_gray.setVisibility(View.VISIBLE);
                            myList.get(1).setIsSelected("No");
                        }
                    }
                });
                service2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(s2_tickIcon_gray.getVisibility()==View.VISIBLE) {
                            s2_tickIcon_blue.setVisibility(View.VISIBLE);
                            s2_tickIcon_gray.setVisibility(View.GONE);
                            myList.get(2).setIsSelected("Yes");
                        }
                        else
                        {
                            s2_tickIcon_blue.setVisibility(View.GONE);
                            s2_tickIcon_gray.setVisibility(View.VISIBLE);
                            myList.get(2).setIsSelected("No");
                        }
                    }
                });
                service3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(s3_tickIcon_gray.getVisibility()==View.VISIBLE) {
                            s3_tickIcon_blue.setVisibility(View.VISIBLE);
                            s3_tickIcon_gray.setVisibility(View.GONE);
                            myList.get(3).setIsSelected("Yes");
                        }
                        else
                        {
                            s3_tickIcon_blue.setVisibility(View.GONE);
                            s3_tickIcon_gray.setVisibility(View.VISIBLE);
                            myList.get(3).setIsSelected("No");
                        }
                    }
                });
                //book button listener to show the alert dialog box
                book_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                        String jobBookTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        String userLatLng="";
                        userLatLng+=latLng.latitude;
                        userLatLng+=",";
                        userLatLng+=latLng.longitude;
                        makeJob(sp.getUid(), Uid,"New",jobBookTime,userLatLng);

                        // Toast.makeText(BasicSearch.this, "Book Pressed", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder booking_dialog = new AlertDialog.Builder(SearchServiceProvider.this);
                        View bookingView = getLayoutInflater().inflate(R.layout.booking_dialog_box, null);
                        final MaterialButton cancel = (MaterialButton) bookingView.findViewById(R.id.booking_cancel);
                        final ProgressBar progressBar = (ProgressBar) bookingView.findViewById(R.id.booking_progress);
                        progressBar.setIndeterminate(true);
                        Log.d("BC", "onClick: ");
                        booking_dialog.setView(bookingView);
                        final AlertDialog alertDialog = booking_dialog.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                alertDialog.dismiss();
                                //Intent intent = new Intent(BasicSearch.this,CurrentJobMap.class);;
                                //startActivity(intent);
                                joncancelref = FirebaseDatabase.getInstance().getReference("Jobs").child(key.getKey()).child("status");
                                joncancelref.setValue("Cancel by user");
                            }
                        });
                        alertDialog.show();
                        jobref=FirebaseDatabase.getInstance().getReference().child("Jobs");
                        jobref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }


                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                //Log.d("TAGA",snapshot.getValue().toString());
                                //if the service provider accepts the job then user is moved to another map screen where user can track the service provider.
                                if(snapshot.child("spid").getValue().toString().equals(sp.getUid()) && snapshot.child("uid").getValue().toString().equals(Uid))
                                {
                                    if(snapshot.child("status").getValue().toString().equals("Accept"))
                                    {
                                        //Log.d("TAGA",snapshot.getKey());
                                        Intent intent = new Intent(SearchServiceProvider.this,CurrentJobMap.class);
                                        intent.putExtra("key",snapshot.getKey());
                                        intent.putExtra("spid",snapshot.child("spid").getValue().toString());
                                        intent.putExtra("uid",snapshot.child("uid").getValue().toString());
                                        intent.putExtra("userLatLng",snapshot.child("userLatLng").getValue().toString());
                                        intent.putExtra("type",sp.getWorktype());
                                        intent.putExtra("loc",sp.getLoc());
                                        intent.putExtra("spphno",sp.getPhone());
                                        intent.putExtra("spname",sp.getFname()+" "+sp.getLname());
                                        intent.putExtra("serviceKey",serviceKey.getKey());
                                        startActivity(intent);

                                    }
                                    if(snapshot.child("status").getValue().toString().equals("Job Rejected by SP"))
                                    {
                                        //Log.d("TAGA",snapshot.getKey());
                                        alertDialog.dismiss();
                                        Toast.makeText(SearchServiceProvider.this,"Job Rejected by Service Provider",Toast.LENGTH_SHORT).show();
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
                        });

                    }

                });
                call_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri u = Uri.parse("tel:" + sp.getPhone());
                        Intent i = new Intent(Intent.ACTION_DIAL, u);
                        try {
                            startActivity(i);
                        } catch (SecurityException s) {
                            Toast.makeText(SearchServiceProvider.this, "An error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        }

        return true;
    }
    //function that will store the job data in the database.
    public void makeJob(String SPID,String UID,String status,String JobBookTime,String userLatLng){
        myref = FirebaseDatabase.getInstance().getReference("Jobs");
        key = myref.push();
        //Log.d("My service",myList.get(1).getKey());
        DatabaseReference orderedService=FirebaseDatabase.getInstance().getReference("Services");
        serviceKey=orderedService.push();

        if(myList.size()!=1)
        {
            if(myList.get(1).getIsSelected().equals("Yes"))
            {
                DatabaseReference ser1=FirebaseDatabase.getInstance().getReference("Services").child(serviceKey.getKey()).child(myList.get(1).getKey());
                ser1.setValue(myList.get(1));
            }
            if(myList.get(2).getIsSelected().equals("Yes"))
            {
                DatabaseReference ser1=FirebaseDatabase.getInstance().getReference("Services").child(serviceKey.getKey()).child(myList.get(2).getKey());
                ser1.setValue(myList.get(2));
            }
            if(myList.get(3).getIsSelected().equals("Yes"))
            {
                DatabaseReference ser1=FirebaseDatabase.getInstance().getReference("Services").child(serviceKey.getKey()).child(myList.get(3).getKey());
                ser1.setValue(myList.get(3));
            }
        }
        myList.get(0).setKey("-ABC");
        DatabaseReference ser2=FirebaseDatabase.getInstance().getReference("Services").child(serviceKey.getKey()).child(myList.get(0).getKey());
        ser2.setValue(myList.get(0));


        myref.child(key.getKey()).setValue(new Job(SPID,UID,status,JobBookTime,userLatLng,"",
                "","","","","",
                "","","","","","","",serviceKey.getKey()));
        myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid());
        myref.child("Jobs").child(key.getKey()).setValue("true");

    }
    //MAP FUNCTIONS
}