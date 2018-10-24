package com.jzd.android.jon.app.module.tree.ui

import android.os.Bundle
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.app.module.tree.bean.TreeBean
import kotlinx.android.synthetic.main.activity_tree.*

class TreeActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)

        val data = getData()

        val adapter = TreeAdapter(this,mLvData,0)
        adapter.setDatas(data)
        mLvData.adapter = adapter
    }

    private fun getData(): List<TreeBean>
    {
        val list = arrayListOf<TreeBean>()
        val deptBean = TreeBean()
        deptBean.id = "0"
        deptBean.parentId=""
        deptBean.name="学校"
        val deptBean1 = TreeBean()
        deptBean1.id = "1"
        deptBean1.parentId="0"
        deptBean1.name="一年级"
        val deptBean2 = TreeBean()
        deptBean2.id = "2"
        deptBean2.parentId="0"
        deptBean2.name="二年级"
        val deptBean3 = TreeBean()
        deptBean3.id = "3"
        deptBean3.parentId="0"
        deptBean3.name="三年级"
        val deptBean4 = TreeBean()
        deptBean4.id = "4"
        deptBean4.parentId="0"
        deptBean4.name="四年级"
        val deptBean5 = TreeBean()
        deptBean5.id = "5"
        deptBean5.parentId="0"
        deptBean5.name="五年级"
        list.add(deptBean)
        list.add(deptBean1)
        list.add(deptBean2)
        list.add(deptBean3)
        list.add(deptBean4)
        list.add(deptBean5)

        val person1 = TreeBean()
        person1.id = "100"
        person1.parentId = "1"
        person1.name = "张三"
        list.add(person1)
        return list
    }
}
