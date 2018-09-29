package com.jzd.android.jon.app.module.japp.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JApp
import com.jzd.android.jon.utils.JLog
import kotlinx.android.synthetic.main.activity_japp.*

class JAppActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_japp)

        setOnClick(mBtnRunning, mBtnTopActivity)
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.mBtnRunning ->
            {
                JLog.d("running:${JApp.isRunning()}")
            }
            R.id.mBtnTopActivity ->
            {
                JLog.d("topActivity:${JApp.getTopActivity()}")
            }
        }
    }
}
