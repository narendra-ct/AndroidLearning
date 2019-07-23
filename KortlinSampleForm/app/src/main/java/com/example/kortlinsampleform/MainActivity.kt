package com.example.kortlinsampleform

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.kortlinsampleform.Modal.NonCuratedItem
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import com.google.android.material.tabs.TabLayout
import java.math.BigDecimal
import java.util.ArrayList


class MainActivity : AppCompatActivity(), OnClickListener, NewItemImageFragment.ButtonActions {


    val TAG = "MainActivity"

    lateinit var nonCuratedItem: NonCuratedItem

    lateinit var viewPager: ViewPager
    lateinit var cornerCameraButton: Button
    lateinit var centerCameraButton: Button
    lateinit var infoTextView: TextView
    lateinit var tabLayout: TabLayout

    private var mImages: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize UI
        initializeUI()
        inititalizeViewpager()
        setupViewPager()

        //Initialize noncurated item class
        nonCuratedItem = NonCuratedItem()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.action_name) {
            navigateToNext()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun navigateToNext() {
        val intent = Intent(this, PickupLocationActivity::class.java)
        intent.putExtra("NewItem",nonCuratedItem)
        startActivity(intent)
    }


    private fun initializeUI(){
        print("initializeUI")
        Log.d("MainActivity", "initializeUI")

        val test1View = findViewById<View>(R.id.item_name)
        val test1TextView = test1View.findViewById<EditText>(R.id.editText)

        test1TextView.afterTextChanged { string ->
            nonCuratedItem.itemName = test1TextView.text.toString()
            Log.d("MainActivity", "TextChanged:: ${test1TextView.text}")
        }

        val test2View = findViewById<View>(R.id.item_link)
        val test2TextView = test2View.findViewById<EditText>(R.id.editText)

        test2TextView.afterTextChanged { string ->
            nonCuratedItem.link = test2TextView.text.toString()
            Log.d("MainActivity", "test2TextView:: ${test2TextView.text}")
        }

        val test3View = findViewById<View>(R.id.item_price)
        val test3TextView = test3View.findViewById<EditText>(R.id.editText4)

        test3TextView.afterTextChanged { string ->
            if (!test3TextView.text.toString().isBlank()) {
                nonCuratedItem.price = BigDecimal(test3TextView.text.toString())
            }
            Log.d("MainActivity", "test3TextView:: ${test3TextView.text}")
        }

        val test4View = findViewById<View>(R.id.item_description)
        val test4TextView = test4View.findViewById<EditText>(R.id.editText2)

        test4TextView.afterTextChanged { string ->
            nonCuratedItem.itemDescription = test4TextView.text.toString()
            Log.d("MainActivity", "test4TextView:: ${test4TextView.text}")
        }


        val testSizeView = findViewById<View>(R.id.item_size)
        val testButton1 = testSizeView.findViewById<Button>(R.id.button2)
        val testButton2 = testSizeView.findViewById<Button>(R.id.button3)
        val testButton3 = testSizeView.findViewById<Button>(R.id.button)

        testButton1.setOnClickListener(this)
        testButton2.setOnClickListener(this)
        testButton3.setOnClickListener(this)
    }


    private fun inititalizeViewpager() {

        val includeView     = findViewById<View>(R.id.item_image)

        viewPager           = includeView.findViewById<ViewPager>(R.id.viewpager)
        cornerCameraButton  = includeView.findViewById<Button>(R.id.button4)
        centerCameraButton  = includeView.findViewById<Button>(R.id.button6)
        infoTextView        = includeView.findViewById<TextView>(R.id.textView6)
        tabLayout           = includeView.findViewById<TabLayout>(R.id.tablayout)
        tabLayout.setupWithViewPager(viewPager!!,true)

        centerCameraButton.setOnClickListener { loadImagePicker() }
        cornerCameraButton.setOnClickListener { loadImagePicker() }

    }

    private fun setUpdatedImages(images: MutableList<String>?) {
        if (images != null) {
            mImages = images
        } else {
            mImages = mutableListOf()
        }
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager.isSaveFromParentEnabled = false
        //updateUI components based on images size
        if (mImages.size == 0) {
            centerCameraButton.visibility = View.VISIBLE
            infoTextView.visibility = View.VISIBLE
            cornerCameraButton.visibility = View.INVISIBLE
        } else if (mImages.size == 6) {
            centerCameraButton.visibility = View.INVISIBLE
            infoTextView.visibility = View.INVISIBLE
            cornerCameraButton.visibility = View.INVISIBLE
        } else {
            centerCameraButton.visibility = View.INVISIBLE
            cornerCameraButton.visibility = View.VISIBLE
            infoTextView.visibility = View.INVISIBLE
        }

        val fragments = ArrayList<Fragment>()
        for (image in mImages) {

            val index = mImages.indexOf(image)
            val fragment = NewItemImageFragment.getInstance(image, index,this)
            fragments.add(fragment)
        }
        val adapter = NewItemImageAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
    }

    override fun didTapDeleteButton(index: Int?) {

        mImages.removeAt(index!!)
        setUpdatedImages(mImages)

    }


    fun loadImagePicker() {
        val options = Options.init()
            .setRequestCode(100)                                                 //Request code for activity results
            .setCount(6)                                                         //Number of images to restict selection count
            .setFrontfacing(false)                                                //Front Facing camera on start
            .setImageQuality(ImageQuality.HIGH)                                  //Image Quality
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)           //Orientaion
            .setPath("/pix/images")                                              //Custom Path For Image Storage

        Pix.start(this, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val returnValue = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            Log.d(TAG, "returnValue " + returnValue!!)

            // update in UI
            setUpdatedImages(returnValue)

//            newItem = adapter.getUpdatedItem()
//            newItem.setImages(returnValue)
//            adapter.setUpdatedNewItem(newItem)
//            adapter.notifyDataSetChanged()

        }
    }
    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.button2 -> {
                Log.d(TAG,"Clicked on First Button")
            }

            R.id.button3 -> {
                Log.d(TAG,"Clicked on Second Button")
            }

            R.id.button -> {
                Log.d(TAG,"Clicked on Third Button")
            }

            else -> {
                Log.d(TAG,"BUTTON CLICK :: ELSE ::")
            }
        }

    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}
