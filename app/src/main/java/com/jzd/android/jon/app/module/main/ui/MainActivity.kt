package com.jzd.android.jon.app.module.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.app.adapter.ModuleAdapter
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.app.module.japp.ui.JAppActivity
import com.jzd.android.jon.app.module.loader.ui.JLoaderActivity
import com.jzd.android.jon.app.module.notification.ui.NotificationWatchActivity
import com.jzd.android.jon.app.module.permission.ui.PermissionActivity
import com.jzd.android.jon.app.module.recyclerview.ui.RecyclerViewActivity
import com.jzd.android.jon.app.module.rx.ui.RxMainActivity
import com.jzd.android.jon.core.impl.OnDoubleBackPressListener
import com.jzd.jutils.app.common.bean.ModuleBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity()
{

    private val mLayoutManager = GridLayoutManager(this, 2)
    private val mAdapter = ModuleAdapter(R.layout.rv_item_main_module, null)
    private var mDoubleBackCount = 5

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDoubleBack("双击退出应用", object : OnDoubleBackPressListener
        {
            override fun onPress(): Boolean
            {
                return if (mDoubleBackCount-- > 0)
                {
                    Toast.makeText(mContext, "拦截${mDoubleBackCount}次", Toast.LENGTH_SHORT).show()
                    true
                } else
                {
                    false
                }
            }
        })
        mRvTest.layoutManager = mLayoutManager
        mRvTest.adapter = mAdapter

        initData()

        mAdapter.setOnItemClickListener({ baseQuickAdapter: BaseQuickAdapter<Any, BaseViewHolder>, _: View, i: Int ->
            val moduleBean = baseQuickAdapter.getItem(i) as ModuleBean
            if (moduleBean.target != null)
            {
                startActivity(Intent(this, moduleBean.target))
            }
        })

    }

    private fun initData()
    {
        val data = arrayListOf<ModuleBean>()
        data.add(ModuleBean("权限管理", PermissionActivity::class.java))
        data.add(ModuleBean("RxJava", RxMainActivity::class.java))
        data.add(ModuleBean("RecyclerView", RecyclerViewActivity::class.java))
        data.add(ModuleBean("JLoader", JLoaderActivity::class.java))
        data.add(ModuleBean("Notification", NotificationWatchActivity::class.java))
        data.add(ModuleBean("JApp", JAppActivity::class.java))
        mAdapter.setNewData(data)
    }
}
