package com.jzd.android.jon.app.base

import android.app.Application
import com.jzd.android.jon.app.common.util.MyImageLoader
import com.jzd.android.jon.core.Jon
import com.lzy.okgo.OkGo

class MyApp : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        Jon.init(this).initImageLoader(MyImageLoader)
        OkGo.getInstance().init(this)
    }
}