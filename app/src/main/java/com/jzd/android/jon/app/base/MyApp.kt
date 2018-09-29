package com.jzd.android.jon.app.base

import android.app.Application
import com.jzd.android.jon.core.Jon

class MyApp : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        Jon.init(this)
    }
}