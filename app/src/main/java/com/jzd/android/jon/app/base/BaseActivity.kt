package com.jzd.android.jon.app.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.jzd.android.jon.core.ui.JBaseActivity


@SuppressLint("Registered")
open class BaseActivity : JBaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
    }
}