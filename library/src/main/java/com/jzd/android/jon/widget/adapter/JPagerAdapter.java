package com.jzd.android.jon.widget.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class JPagerAdapter<T> extends PagerAdapter
{
    private List<T> mData = new ArrayList<>();

    public void setDatas(List<T> data)
    {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<T> data)
    {
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void clear()
    {
        this.mData.clear();
        this.notifyDataSetChanged();
    }

    public List<T> getDatas()
    {
        return this.mData;
    }

    @Override
    public int getCount()
    {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

}
