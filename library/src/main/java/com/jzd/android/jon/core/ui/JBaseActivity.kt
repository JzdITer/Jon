package com.jzd.android.jon.core.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Activity父类 封装系统Api
 * @author Jzd
 * @since 1.0
 */
open class JBaseActivity : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int)
    {
        super.setContentView(layoutResID)
    }


    /**
     * 默认实现View.OnClickListener接口，根据需要重写
     */
    override fun onClick(v: View?)
    {

    }

    /**
     * 设置OnClick回调
     */
    fun setOnClick(vararg view: View)
    {
        view.forEach {
            it.setOnClickListener(this)
        }
    }

}