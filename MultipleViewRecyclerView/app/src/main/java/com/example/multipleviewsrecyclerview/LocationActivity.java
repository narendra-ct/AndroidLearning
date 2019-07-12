package com.example.multipleviewsrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "LocationActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40,-160),new LatLng(71,136));
    private static final float DEFAULT_ZOOM = 9f;


    //vars
    private boolean mLocationPermissiongranted = false;
    private GoogleMap mMap;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng selectedLatLng;

    private float mRadius = 1609.34f;
    private float mOneMileIntoMeter = 1609.34f;

    //Widgests
    private SeekBar seekBar;
    private TextView pickupLocationTextView;
    private TextView milesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // load widgets
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        pickupLocationTextView = (TextView) findViewById(R.id.locationTextView);
        milesTextView= (TextView) findViewById(R.id.milesTextView);


        init();
        getLocationPermissions();

    }

    private void init() {
        Log.d(TAG, "init: Initializing");


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();

                if (selectedLatLng != null) {
                    drawCircle(selectedLatLng,progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch started! : "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch stopped!:: " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initMap() {

        Log.d(TAG, "initMap: initialise map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "MAP is READY", Toast.LENGTH_LONG).show();
        mMap = googleMap;

        if (mLocationPermissiongranted) {
            getDeviceLocation();

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }else {
            getLocationPermissions();
        }
    }

    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: ");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissiongranted = true;
                initMap();
            } else {
                Log.d(TAG, "getLocationPermissions: requestPermissions");
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            Log.d(TAG, "getLocationPermissions: requestPermissions");
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        mLocationPermissiongranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    int length = grantResults.length;
                    for (int i = 0; i < length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissiongranted = false;
                            return;
                        }
                    }
                    mLocationPermissiongranted = true;
                    //initialise our map
                    initMap();
                }
            }

        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the current device location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissiongranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();

                            Log.d(TAG, "onComplete: found location:: "+ currentLocation.toString());

                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), DEFAULT_ZOOM,"My Location");
                        }else {
                            Log.d(TAG, "onComplete: unable get current locaiton");
                            Toast.makeText(LocationActivity.this,"Unable to get current location",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }catch (SecurityException e) {
            Log.d(TAG, "Exception: "+ e.getLocalizedMessage());
        }
    }

    private void geoLcoate() {

        Log.d(TAG, "geoLcoate: ");

        String searchString = "";

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);

        }catch (IOException e) {
            Log.d(TAG, "geoLcoate: IOException: "+e.getLocalizedMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLcoate: Address FOUND : " + address.toString());
            Toast.makeText(this, address.toString(),Toast.LENGTH_LONG).show();

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: Moving the camera to lat: " + latLng.latitude + "lng: "+latLng.longitude + "zoom: "+ zoom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.title(title);
            mMap.addMarker(options);
        }

        pickupLocationTextView.setText(title);
        milesTextView.setText("20 miles");
        selectedLatLng = latLng;
        drawCircle(latLng,20);

        hideSoftKeyborad();
    }

    private void hideSoftKeyborad() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void drawCircle(LatLng point, int radius){

        mRadius = radius * mOneMileIntoMeter;

        mMap.clear();
        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();
        // Specifying the center of the circle
        circleOptions.center(point);
        // Radius of the circle
        circleOptions.radius(mRadius);
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);
        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);
        // Border width of the circle
        circleOptions.strokeWidth(1);
        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);
    }


    private void updateMapZoomLevel(float radius){

        if (radius > 5000 && radius < 10000) {
            if (mMap.getCameraPosition().zoom != 11) {

            }

        }else if (radius > 10000 && radius < 25000) {
            if (mMap.getCameraPosition().zoom != 9.3) {

            }
        }else if (radius > 40000 && radius < 65000) {
            if (mMap.getCameraPosition().zoom != 8.5) {

            }
        }else if (radius > 65000 && radius < 90000) {
            if (mMap.getCameraPosition().zoom != 8) {

            }
        }else if (radius > 65000) {
            if (mMap.getCameraPosition().zoom != 7) {

            }
        }

    }
}
