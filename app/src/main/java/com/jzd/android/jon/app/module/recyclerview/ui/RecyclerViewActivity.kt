package com.jzd.android.jon.app.module.recyclerview.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
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
import com.jzd.android.jon.utils.clearItemDecoration
import com.jzd.android.jon.utils.setItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val data = arrayListOf<JMapImpl>()
        for (i in 0..20)
        {
            data.add(i.toString().toJMap())
        }

        val mAdapter = RecyclerViewAdapter(R.layout.rv_item_common_txt, data)
        mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(mRvData.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.drawable_recycler_view_divider_vertical)!!)
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
        when (item?.itemId)
        {
            R.id.id_menu_linear_layout_h ->
            {
                mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                mRvData.setItemDecoration(DividerItemDecoration(mRvData.context, DividerItemDecoration.HORIZONTAL))
            }
            R.id.id_menu_linear_layout_v ->
            {
                mRvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                mRvData.setItemDecoration(DividerItemDecoration(mRvData.context, DividerItemDecoration.VERTICAL))
            }
            R.id.id_menu_grid ->
            {
                mRvData.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                mRvData.clearItemDecoration()
                mRvData.addItemDecoration(DividerItemDecoration(mRvData.context, DividerItemDecoration.VERTICAL))
                mRvData.addItemDecoration(DividerItemDecoration(mRvData.context, DividerItemDecoration.HORIZONTAL))
            }
        }

        return super.onContextItemSelected(item)
    }
}
