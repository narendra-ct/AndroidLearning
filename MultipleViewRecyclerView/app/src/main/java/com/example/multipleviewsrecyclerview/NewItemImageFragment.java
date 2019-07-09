package com.example.multipleviewsrecyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

public class NewItemImageFragment extends Fragment {

    private static final String TAG = "NewItemImageFragment";

    //widgets
    private ImageView mImageView;
    private TextView mSubTitle;

    private Button deleteButton;

    private Integer mIndex;

    //vars
    // private
    String mImage;
    ButtonActions mActions;


    public static NewItemImageFragment getInstance(String image, Integer index) {
        NewItemImageFragment fragment = new NewItemImageFragment();

        if (image != null) {
            fragment.mIndex = index;
            fragment.mImage = image;


//            Bundle bundle = new Bundle();
//            bundle.putString("image",image);
//            //bundle.putParcelable("hat", hat);
//            fragment.setArguments(bundle);

        }
        return fragment;
    }

    public void setButtonActions(ButtonActions action) {
        mActions = action;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mImage = getArguments().getParcelable("image");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newitem_viewpager_image,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageView = view.findViewById(R.id.imageView5);
        // mSubTitle = view.findViewById(R.id.title);
        deleteButton = view.findViewById(R.id.button5);

        //show image
        File imgFile = new  File(mImage);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mImageView.setImageBitmap(myBitmap);
        }

        //Add actions button
        //centerCamButton.setOnClickListener((View.OnClickListener) this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // center camera button action
                mActions.didTapDeleteButton(mIndex);
            }
        });

    }

    private void updateData() {

        // imagesmImageView.setBackground();
    }

    public interface ButtonActions {
        public void didTapDeleteButton(Integer index);
    }
}
