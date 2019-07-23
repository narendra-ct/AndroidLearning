package com.example.kortlinsampleform

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kortlinsampleform.Modal.NonCuratedItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class PickupLocationActivity : AppCompatActivity(),OnMapReadyCallback, SeekBar.OnSeekBarChangeListener, GoogleMap.OnMyLocationButtonClickListener {


    val TAG = "PickupLocationActivity"

    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234

    private val LAT_LNG_BOUNDS = LatLngBounds(LatLng(-40.0, -160.0), LatLng(71.0, 136.0))
    private val DEFAULT_ZOOM = 9f


    //vars
    private var mLocationPermissiongranted = false
    private lateinit var mMap: GoogleMap
    // The entry point to the Fused Location Provider.
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var selectedLatLng: LatLng? = null

    private var mRadius = 1609.34f
    private val mOneMileIntoMeter = 1609.34f

    //Widgests
    private var seekBar: SeekBar? = null
    private var pickupLocationTextView: TextView? = null
    private var milesTextView: TextView? = null

    //data variabel
    lateinit var nonCuratedItem: NonCuratedItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_location)

        loadWidgets()
        init()
        getLocationPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_name) {
            navigateToNext()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    internal fun navigateToNext() {
        val intent = Intent(this, PickupLocationActivity::class.java)
        intent.putExtra("NewItem",nonCuratedItem)
        startActivity(intent)
    }


    private fun loadWidgets() {
        // load widgets
        seekBar = findViewById(R.id.seekBar) as SeekBar
        pickupLocationTextView = findViewById(R.id.locationTextView) as TextView
        milesTextView = findViewById(R.id.milesTextView) as TextView
    }

    private fun init() {
        Log.d(TAG,"init called")

        seekBar?.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        Log.d(TAG,"onProgressChanged:: $p1")
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        Log.d(TAG,"onStartTrackingTouch:: ${p0!!.progress}")
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        Log.d(TAG,"onStopTrackingTouch:: ${p0!!.progress}")
        if (selectedLatLng != null) {
            drawCircle(selectedLatLng!!, seekBar!!.getProgress())
        }
    }

    private fun initMap() {
        Log.d(TAG, "initMap: initialise map")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!

        if (mLocationPermissiongranted) {

            getDeviceLocation()

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap?.setMyLocationEnabled(true)
            mMap?.getUiSettings()?.isMyLocationButtonEnabled = true
        }else{
            getLocationPermissions()
        }

        mMap.setOnMyLocationButtonClickListener(this)
    }

    override fun onMyLocationButtonClick(): Boolean {
        getDeviceLocation()
        return false
    }

    private fun getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: ")
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissiongranted = true
                initMap()
            } else {
                Log.d(TAG, "getLocationPermissions: requestPermissions")
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            Log.d(TAG, "getLocationPermissions: requestPermissions")
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "onRequestPermissionsResult: $requestCode")
        mLocationPermissiongranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    val length = grantResults.size
                    for (i in 0 until length) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissiongranted = false
                            return
                        }
                    }
                    mLocationPermissiongranted = true
                    //initialise our map
                    initMap()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the current device location")

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mFusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    Log.d(TAG, "onComplete: found location:: $location" )
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    moveCamera(currentLatLng,DEFAULT_ZOOM,"My Location")
                }else{
                    Log.d(TAG, "onComplete: unable get current locaiton")
                    Toast.makeText(this, "Unable to get current location", Toast.LENGTH_LONG)
                }
            }
        }

    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(
            TAG,
            "moveCamera: Moving the camera to lat: " + latLng.latitude + "lng: " + latLng.longitude + "zoom: " + zoom
        )
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

        if (title != "My Location") {
            val options = MarkerOptions()
            options.position(latLng)
            options.title(title)
            mMap?.addMarker(options)
        }

        pickupLocationTextView?.setText(title)
        milesTextView?.setText("20 miles")
        selectedLatLng = latLng
        drawCircle(latLng, 20)

        hideSoftKeyborad()
    }

    private fun drawCircle(point: LatLng, radius: Int) {

        mRadius = radius * mOneMileIntoMeter

        mMap.clear()
        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()
        // Specifying the center of the circle
        circleOptions.center(point)
        // Radius of the circle
        circleOptions.radius(mRadius.toDouble())
        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)
        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)
        // Border width of the circle
        circleOptions.strokeWidth(1f)
        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions)


        updateMapZoomLevel(point, mRadius)
    }


    private fun updateMapZoomLevel(location: LatLng, radius: Float) {

        if (radius > 5000 && radius < 10000) {
            if (mMap?.getCameraPosition()?.zoom != 11f) {
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 11.0f))
            }

        } else if (radius > 10000 && radius < 25000) {
            if (mMap?.getCameraPosition()?.zoom?.toDouble() != 9.3) {
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 9.3f))
            }
        } else if (radius > 40000 && radius < 65000) {
            if (mMap?.getCameraPosition()?.zoom?.toDouble() != 8.5) {
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 8.5f))
            }
        } else if (radius > 65000 && radius < 90000) {
            if (mMap?.getCameraPosition()?.zoom != 8f) {
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 8f))
            }
        } else if (radius > 65000) {
            if (mMap?.getCameraPosition()?.zoom != 7f) {
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7f))
            }
        }
    }

    private fun hideSoftKeyborad() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }



}
