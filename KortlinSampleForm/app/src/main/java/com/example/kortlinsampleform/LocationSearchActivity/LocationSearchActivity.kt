package com.example.kortlinsampleform.LocationSearchActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.kortlinsampleform.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.IOException
import java.util.ArrayList


class LocationSearchActivity : AppCompatActivity(), SearchResultsAdapter.ONNClickListener {

    companion object {
        var TAG = "LocationSearchActivity"
    }

    //widgets
    private var locationResultsRecyclerView: RecyclerView? = null
    private var searchEditText: EditText? = null

    //vars
    private lateinit var mAdapter: SearchResultsAdapter
    private var mPredictionLocNames: MutableList<String> = mutableListOf()

//    // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
//    val token = AutocompleteSessionToken.newInstance()
    // Create a new Places client instance.
    var placesClient: PlacesClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        // Initialize Places.
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }
        // Create a new Places client instance.
        placesClient = Places.createClient(this)

        //load widgets
        locationResultsRecyclerView = findViewById(R.id.locationResultsRecyclerView) as RecyclerView
        searchEditText = findViewById(R.id.searchLocEditText) as EditText

        searchEditText!!.doAfterTextChanged {
            Log.d("LocationSearchActivity", it.toString())
            getPlaces(it.toString())
        }

        //
        initRecyclerView()
    }

    fun initRecyclerView() {
        mAdapter = SearchResultsAdapter(listOf<String>(),this)
        locationResultsRecyclerView!!.adapter = mAdapter
    }

    private fun getPlaces(searchString: String){
        Log.d(TAG, "getPlaces: Called")
        if (searchString.isEmpty() || searchString.length < 3) {
            mPredictionLocNames = mutableListOf()
            updateUI()
            return
        }

        // Create a RectangularBounds object.
        // 68.1766451354, 7.96553477623, 97.4025614766, 35.4940095078
        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596)
        )

//        // Create a RectangularBounds object.
//        val bounds = RectangularBounds.newInstance(
//            LatLng(-85.0, 180.0),
//            LatLng(85.0, -180.0)
//        )

        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        val token = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            //.setLocationBias(bounds)
            //.setTypeFilter(TypeFilter.GEOCODE)
            .setSessionToken(token)
            .setQuery(searchString)
            .build()

        placesClient!!.findAutocompletePredictions(request).addOnSuccessListener { response ->
            mPredictionLocNames = response.autocompletePredictions.map { prediction -> prediction.getFullText(null).toString() } as MutableList<String>
            updateUI()

        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
                Toast.makeText(this, "Failed to get locations", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun updateUI(){
        runOnUiThread {
            mAdapter.updatedSearchResults(mPredictionLocNames)
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(position: Int) {
        Log.d(TAG,"onClick $position")

        geoLcoate(mPredictionLocNames.get(position))

    }

    private fun geoLcoate(searchString: String) {

        Log.d(TAG, "geoLcoate: Called")

        val geocoder = Geocoder(this)
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
        } catch (e: IOException) {
            Log.d(TAG, "geoLcoate: IOException: " + e.localizedMessage)
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
        }
        if (list.isNotEmpty()) {
            val address = list.first()
            Log.d(TAG, "geoLcoate: LATITUDE : ${address.latitude}")
            Log.d(TAG, "geoLcoate: LONGITUDE : ${address.longitude}")
            var locality = address.locality
            if (locality == null || locality.isEmpty()) {
                locality = address.subLocality
            }
            sendLocationDetailsBackToActivity(address.latitude,address.longitude,locality,address.countryName)
        }else{
            Toast.makeText(this, "Location Not Found", Toast.LENGTH_LONG).show()
        }
    }

    fun sendLocationDetailsBackToActivity(lat: Double,lng: Double,city:String,country: String) {

        val returnIntent = Intent()
        returnIntent.putExtra("latitude",lat)
        returnIntent.putExtra("longitude",lng)
        returnIntent.putExtra("city",city)
        returnIntent.putExtra("country",country)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
