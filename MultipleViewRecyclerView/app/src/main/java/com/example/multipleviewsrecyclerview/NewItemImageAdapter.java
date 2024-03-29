package com.example.multipleviewsrecyclerview;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class NewItemImageAdapter extends FragmentPagerAdapter {


    ArrayList<String> images = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    public NewItemImageAdapter(FragmentManager fm, ArrayList<String> images, ArrayList<Fragment> fragments) {
        super(fm);

        this.images = images;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);

//        NewItemImageFragment fragment = new NewItemImageFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("position", String.valueOf(position));
//        fragment.setArguments(bundle);
//        return fragment;
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
