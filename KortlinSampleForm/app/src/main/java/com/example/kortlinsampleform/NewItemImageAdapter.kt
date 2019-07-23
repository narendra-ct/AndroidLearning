package com.example.kortlinsampleform

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class NewItemImageAdapter(fragmentManager: FragmentManager,fragments: ArrayList<Fragment>): FragmentStatePagerAdapter(fragmentManager) {

    private var mFragments = java.util.ArrayList<Fragment>()

    init {
        this.mFragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

}