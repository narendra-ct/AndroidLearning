package com.example.kortlinsampleform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView





class MainActivity : AppCompatActivity(), OnClickListener {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize UI
        initializeUI()

    }

    private fun initializeUI(){
        print("initializeUI")
        Log.d("MainActivity", "initializeUI")

        val test1View = findViewById<View>(R.id.item_name)
        val test1TextView = test1View.findViewById<EditText>(R.id.editText)

        test1TextView.afterTextChanged {
            Log.d("MainActivity", "TextChanged:: ${test1TextView.text}")
        }

        val test2View = findViewById<View>(R.id.item_link)
        val test2TextView = test2View.findViewById<EditText>(R.id.editText)

        test2TextView.afterTextChanged {
            Log.d("MainActivity", "test2TextView:: ${test2TextView.text}")
        }

        val test3View = findViewById<View>(R.id.item_price)
        val test3TextView = test3View.findViewById<EditText>(R.id.editText4)

        test3TextView.afterTextChanged {
            Log.d("MainActivity", "test3TextView:: ${test3TextView.text}")
        }

        val test4View = findViewById<View>(R.id.item_description)
        val test4TextView = test4View.findViewById<EditText>(R.id.editText2)

        test4TextView.afterTextChanged {
            Log.d("MainActivity", "test4TextView:: ${test4TextView.text}")
        }


        val testSizeView = findViewById<View>(R.id.item_size)
        val testButton1 = testSizeView.findViewById<Button>(R.id.button2)
        val testButton2 = testSizeView.findViewById<Button>(R.id.button3)
        val testButton3 = testSizeView.findViewById<Button>(R.id.button)

        testButton1.setOnClickListener(this)
        testButton2.setOnClickListener(this)
        testButton3.setOnClickListener(this)




//        test1TextView.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//                print("TextChanged:: ${s.toString()}")
//                Log.d("MainActivity", "TextChanged:: ${s.toString()}")
//                print(s.toString())
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                Log.d("MainActivity", "TextChanged:: ${s.toString()}")
//            }
//        })
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
