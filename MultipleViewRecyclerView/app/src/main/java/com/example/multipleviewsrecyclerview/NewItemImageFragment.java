package com.example.multipleviewsrecyclerview;

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

public class NewItemImageFragment extends Fragment {

    private static final String TAG = "NewItemImageFragment";

    //widgets
    private ImageView mImageView;
    private TextView mSubTitle;

    private Button centerCamButton;
    private Button deleteButton;
    private Button cornerCamButton;

    //vars
    // private
    String mImage;

    public static NewItemImageFragment getInstance(String image) {
        NewItemImageFragment fragment = new NewItemImageFragment();

        if (image != null) {
            Bundle bundle = new Bundle();
            bundle.putString("image",image);
            //bundle.putParcelable("hat", hat);
            fragment.setArguments(bundle);
        }
        return fragment;
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
        centerCamButton = view.findViewById(R.id.button4);
        deleteButton = view.findViewById(R.id.button5);
        cornerCamButton = view.findViewById(R.id.button6);

        // setdata
        updateData();

    }

    private void updateData() {

        // imagesmImageView.setBackground();
    }
}
