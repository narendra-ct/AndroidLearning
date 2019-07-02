package com.example.multipleviewsrecyclerview;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewItemImageViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "NewItemImageViewHolder";

    ViewPager viewPager;
    Context mContext;

    public NewItemImageViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        viewPager = itemView.findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) itemView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);

        setupViewPager();
    }

    void setupViewPager(){

        ArrayList<String> images = new ArrayList<>();
        images.add("");
        images.add("");
        images.add("");
        images.add("");
        images.add("");
        NewItemImageAdapter adapter = new NewItemImageAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(),images);
        viewPager.setAdapter(adapter);
    }
}
