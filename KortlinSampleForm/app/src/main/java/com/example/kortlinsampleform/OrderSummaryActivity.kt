package com.example.kortlinsampleform

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import android.R.attr.button
import android.R.color
import android.R.attr.strokeWidth
import android.R.attr.radius
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import androidx.core.content.ContextCompat
import io.fotoapparat.selector.back
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.libraries.places.internal.dp
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth







class OrderSummaryActivity : AppCompatActivity(), OnMapReadyCallback {

    //widgets
    private var mGmapView: MapView? = null
    private var parentLinearLayout: LinearLayout? = null


    private var mMap: GoogleMap? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        parentLinearLayout = findViewById(R.id.formLinearLayout)

        // Initialise Map
        initialiseMap(savedInstanceState)

        // Initialise AddionalInfo Header
        initialiseAddionalInfoHeader()

        //Initialise Other Views
        initialiseViews()
    }

    private fun initialiseViews() {
        val specialInstView     = findViewById<View>(R.id.item_special_instructions)
        specialInstView.findViewById<TextView>(R.id.textView2).text = "Special Instructions"
        specialInstView.findViewById<TextView>(R.id.editText2).hint = "Leave a note"

        //setup postorder button
        val button = findViewById<Button>(R.id.postOrder)
        val color = ContextCompat.getColor(this,R.color.colorShoppper)
        setCornerRadius(button,color,20.0f)
    }

    private fun initialiseAddionalInfoHeader() {

        val additionalInfoView     = findViewById<View>(R.id.item_additional_info)

        var purchaseLocView = findViewById<View>(R.id.item_purchase_location)
        var purchaseStoreView = findViewById<View>(R.id.item_purchase_store)
        var pickupMethodView = findViewById<View>(R.id.item_preferred_method)
        var questionsView = findViewById<View>(R.id.item_specific_questions)

        var isClicked: Boolean = false
        additionalInfoView.setOnClickListener {
            isClicked = !isClicked
            if (isClicked) {
                purchaseLocView.isVisible = true
                purchaseStoreView.isVisible = true
                pickupMethodView.isVisible = true
                questionsView.isVisible = true

                //update data
                purchaseStoreView.findViewById<TextView>(R.id.textView).text = "Item Purchase Store"
                purchaseStoreView.findViewById<TextView>(R.id.editText).text = "Anywhere"

            }else{
                purchaseLocView.isVisible = false
                purchaseStoreView.isVisible = false
                pickupMethodView.isVisible = false
                questionsView.isVisible = false
            }
        }

        //for questions
        questionsAction(questionsView)
    }

    private fun questionsAction(questionsView: View) {
        questionsView.setOnClickListener {
            //questionsView action
            // Add the new row before the add field button.
            var rowView = LayoutInflater.from(this).inflate(R.layout.item_two_line_filed_arrow,null)
            rowView.findViewById<TextView>(R.id.textview_title).text = "Question Tilte" + parentLinearLayout!!.childCount
            rowView.findViewById<TextView>(R.id.textView_title_value).text = "Answer - Question" + parentLinearLayout!!.childCount


            val factor = this.getResources().getDisplayMetrics().density.toInt()
            rowView.setLayoutParams(ViewGroup.LayoutParams(parentLinearLayout!!.width, 75 * factor))

            parentLinearLayout!!.addView(rowView,parentLinearLayout!!.childCount - 1)
        }
    }

    private fun initialiseMap(savedInstanceState: Bundle?) {
        val mapView     = findViewById<View>(R.id.item_pickup_location)
        mGmapView       = mapView.findViewById<MapView>(R.id.mapView)
        mGmapView!!.onCreate(savedInstanceState)
        mGmapView!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap!!.getUiSettings().setMyLocationButtonEnabled(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mGmapView!!.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mGmapView!!.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mGmapView!!.onLowMemory()
    }

    override fun onResume() {
        super.onResume()
        mGmapView!!.onResume()
    }

    fun setCornerRadius(view: View, backgroundColor: Int, radius: Float) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(backgroundColor)
        gradientDrawable.cornerRadius = radius
        view.stateListAnimator = null
        //gradientDrawable.setStroke(strokeWidth, color)
        view.setBackground(gradientDrawable)
    }
}
