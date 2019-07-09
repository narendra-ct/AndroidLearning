package com.example.multipleviewsrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewItemImageViewHolder extends RecyclerView.ViewHolder implements NewItemImageFragment.ButtonActions {

    private static final String TAG = "NewItemImageViewHolder";

    ViewPager viewPager;
    Context mContext;
    Button cornerCameraButton;
    Button centerCameraButton;
    TextView infoTextView;

    AddimagesAction imageActionListener;

    ArrayList<String> mImages = new ArrayList<String>();

    public NewItemImageViewHolder(@NonNull View itemView, Context context, ArrayList<String> images ,AddimagesAction listener) {
        super(itemView);
        this.mContext = context;
        viewPager = itemView.findViewById(R.id.viewpager);
        cornerCameraButton = itemView.findViewById(R.id.button4);
        centerCameraButton = itemView.findViewById(R.id.button6);
        infoTextView = itemView.findViewById(R.id.textView6);

        //set listener
        imageActionListener = listener;

        TabLayout tabLayout = (TabLayout) itemView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);

        centerCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add image action
                //corner button action
                if (imageActionListener != null) {
                    imageActionListener.didTapAddImage();
                }

                loadImageController();
            }
        });

        //camera button action
        cornerCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //corner button action
                if (imageActionListener != null) {
                    imageActionListener.didTapAddImage();
                }
                loadImageController();
            }
        });
    }

    void setUpdatedImages(ArrayList<String> images) {
        if (images != null) {
            mImages = images;
        }else {
            mImages = new ArrayList<>();
        }
        setupViewPager();
    }


    private void setupViewPager(){

        viewPager.setSaveFromParentEnabled(false);

        //updateUI components based on images size
        if (mImages.size() == 0) {
            centerCameraButton.setVisibility(View.VISIBLE);
            infoTextView.setVisibility(View.VISIBLE);
            cornerCameraButton.setVisibility(View.INVISIBLE);
        }else if (mImages.size() == 6) {
            centerCameraButton.setVisibility(View.INVISIBLE);
            infoTextView.setVisibility(View.INVISIBLE);
            cornerCameraButton.setVisibility(View.INVISIBLE);
        }else {
            centerCameraButton.setVisibility(View.INVISIBLE);
            cornerCameraButton.setVisibility(View.VISIBLE);
            infoTextView.setVisibility(View.INVISIBLE);
        }


        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String image: mImages) {

            Integer index = mImages.indexOf(image);
            NewItemImageFragment fragment = NewItemImageFragment.getInstance(image,index);
            fragment.setButtonActions(this);
            fragments.add(fragment);
        }
        NewItemImageAdapter adapter = new NewItemImageAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(),mImages,fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void didTapDeleteButton(Integer index) {
        Log.d(TAG, "didTapButton: "+ index);
        // if required can be used
        imageActionListener.didTapDeleteImage(index);
    }

    // Load Images

    private void loadImageController() {

        Options options = Options.init()
                .setRequestCode(100)                                                 //Request code for activity results
                .setCount(6)                                                         //Number of images to restict selection count
                .setFrontfacing(false)                                                //Front Facing camera on start
                .setImageQuality(ImageQuality.HIGH)                                  //Image Quality
                .setPreSelectedUrls(new ArrayList<>())                                     //Pre selected Image Urls
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)           //Orientaion
                .setPath("/pix/images");                                             //Custom Path For Image Storage
        Pix.start((MainActivity) mContext, options);
    }





















    public interface AddimagesAction {
        public void didTapAddImage();
        public void didTapDeleteImage(Integer index);
    }

}

