package com.example.kortlinsampleform

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File

class NewItemImageFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    //widgets
    private var mImageView: ImageView? = null
    private var deleteButton: Button? = null

    private var mIndex: Int? = null

    //vars
    // private
    internal var mImage: String? = null
    var mActions: ButtonActions? = null

    companion object {
        fun getInstance(image: String?, index: Int?, listener: ButtonActions): NewItemImageFragment {
            val fragment = NewItemImageFragment(R.layout.fragment_newitem_viewpager_image)

            if (image != null) {
                fragment.mIndex = index
                fragment.mImage = image
                fragment.mActions = listener
            }
            return fragment
        }
    }

//    fun getInstance(image: String?, index: Int?): NewItemImageFragment {
//        val fragment = NewItemImageFragment(R.layout.fragment_newitem_viewpager_image)
//
//        if (image != null) {
//            fragment.mIndex = index
//            fragment.mImage = image
//
//        }
//        return fragment
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_newitem_viewpager_image,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mImageView = view.findViewById<ImageView>(R.id.imageView5)
        deleteButton = view.findViewById<Button>(R.id.button5)

        //show image
        val imgFile = File(mImage)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            mImageView!!.setImageBitmap(myBitmap)
        }

        deleteButton!!.setOnClickListener(View.OnClickListener {
            // center camera button action
            mActions!!.didTapDeleteButton(mIndex)
        })
    }


    interface ButtonActions {
        fun didTapDeleteButton(index: Int?)
    }
}