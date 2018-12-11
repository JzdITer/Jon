package com.jzd.android.jon.widget.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class JFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm)
{

    private var mFragments = arrayListOf<Fragment>()

    fun setData(fragments: ArrayList<Fragment>)
    {
        mFragments = fragments
    }

    fun getData(): ArrayList<Fragment>
    {
        return mFragments
    }

    override fun getItem(position: Int): Fragment
    {
        return mFragments[position]
    }

    override fun getCount(): Int
    {
        return mFragments.size
    }

}