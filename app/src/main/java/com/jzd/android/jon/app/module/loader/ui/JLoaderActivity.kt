package com.jzd.android.jon.app.module.loader.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.widget.JLoadDialog
import kotlinx.android.synthetic.main.activity_jloader.*

class JLoaderActivity : BaseActivity()
{
    val dialog = JLoadDialog(true)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jloader)

        setOnClick(mBtnShow)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnShow ->
            {

                dialog.show(supportFragmentManager, "aa")

                Handler().postDelayed({ dialog.dismiss() }, 5000)
            }
        }
    }


}
