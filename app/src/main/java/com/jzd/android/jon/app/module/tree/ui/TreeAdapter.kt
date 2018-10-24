package com.jzd.android.jon.app.module.tree.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.module.tree.bean.TreeBean
import com.jzd.android.jon.widget.tree.TreeListViewAdapter

class TreeAdapter(var context: Context?, mTree: ListView?, defaultExpandLevel: Int) : TreeListViewAdapter<TreeBean>(context, mTree, defaultExpandLevel)
{
    @SuppressLint("InflateParams") override fun getConvertView(`object`: Any?, position: Int, isExpand: Boolean, convertView: View?, parent: ViewGroup): View
    {
        var conView = convertView
        val viewHolder :TreeViewHolder?
        if(convertView == null)
        {
            conView = LayoutInflater.from(context!!).inflate(R.layout.lv_item_tree, null, false)
            viewHolder = TreeViewHolder()
            viewHolder.mTvName = conView.findViewById(R.id.mTvName)
            conView.tag = viewHolder
        }else
        {
            viewHolder = conView!!.tag as TreeViewHolder
        }

        val item = `object` as TreeBean

        viewHolder.mTvName.text = item.name
        return conView!!
    }

    class TreeViewHolder
    {
        lateinit var mTvName: TextView
    }

}