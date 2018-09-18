package com.jzd.android.jon.app.module.permission.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_permission.*

/**
 * 权限测试类
 */
class PermissionActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        mBtnCamera.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.mBtnCamera ->
            {

            }
        }
    }
}
