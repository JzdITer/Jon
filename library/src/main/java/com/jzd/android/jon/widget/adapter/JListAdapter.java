package com.jzd.android.jon.widget.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class JListAdapter<T> extends BaseAdapter
{

    private List<T> mData = new ArrayList<T>();

    public void setData(List<T> data)
    {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    public void addData(List<T> data)
    {
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void clear()
    {
        this.mData.clear();
        this.notifyDataSetChanged();
    }

    public List<T> getData()
    {
        return this.mData;
    }


    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getConvertView(position, convertView, parent);
    }

    public abstract View getConvertView(int position, View convertView, ViewGroup parent);

}
