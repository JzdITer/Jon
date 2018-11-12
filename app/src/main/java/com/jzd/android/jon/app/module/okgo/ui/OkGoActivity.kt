package com.jzd.android.jon.app.module.okgo.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ok_go.*

class OkGoActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_go)

        setOnClick(mBtnSubmit)
    }

    override fun onClick(v: View?)
    {
        when(v!!.id)
        {
            R.id.mBtnSubmit ->
            {

            }
        }
    }
}
