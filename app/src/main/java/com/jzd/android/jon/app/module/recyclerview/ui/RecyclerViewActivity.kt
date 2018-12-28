package com.jzd.android.jon.app.module.recyclerview.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.app.module.recyclerview.adapter.RecyclerViewAdapter
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.core.module.jmap.toJMap
import com.jzd.android.jon.utils.setItemDecoration
import com.jzd.android.jon.widget.itemdecoration.JDividerItemDecoration
import com.jzd.android.jon.widget.itemdecoration.JGridItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val data = arrayListOf<JMapImpl>()
        for(i in 0..20)
        {
            data.add(i.toString().toJMap())
        }

        val mAdapter = RecyclerViewAdapter(R.layout.rv_item_common_txt, data)
        mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = JDividerItemDecoration(mContext,
                JDividerItemDecoration.VERTICAL).setDivider(
                Color.GREEN, 30)
        mRvData.addItemDecoration(itemDecoration)
        mRvData.adapter = mAdapter

        registerForContextMenu(mIvMenu)

        mIvMenu.setOnClickListener({ it.showContextMenu() })
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_recyvler_view_activity, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean
    {
        when(item?.itemId)
        {
            R.id.id_menu_linear_layout_h ->
            {
                mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                val itemDecoration = JDividerItemDecoration(mContext,
                        JDividerItemDecoration.HORIZONTAL)
                        .setDivider(Color.RED, 10)
                mRvData.setItemDecoration(itemDecoration)
            }
            R.id.id_menu_linear_layout_v ->
            {
                mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                val itemDecoration = JDividerItemDecoration(mContext,
                        JDividerItemDecoration.VERTICAL)
                        .setDivider(ContextCompat.getDrawable(mContext, R.drawable.drawable_recycler_view_divider_vertical))
                mRvData.setItemDecoration(itemDecoration)
            }
            R.id.id_menu_grid_horizontal ->
            {
                mRvData.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
                val itemDecoration = JGridItemDecoration(mContext)
                mRvData.setItemDecoration(itemDecoration)
            }
            R.id.id_menu_grid_vertical->
            {
                mRvData.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                val itemDecoration = JGridItemDecoration(mContext)
                mRvData.setItemDecoration(itemDecoration)
            }
        }

        return super.onContextItemSelected(item)
    }
}
