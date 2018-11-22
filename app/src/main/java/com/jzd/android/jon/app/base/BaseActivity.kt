package com.jzd.android.jon.app.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.jzd.android.jon.core.ui.JBaseActivity
import com.jzd.android.jon.utils.gone
import com.jzd.android.jon.utils.visible
import kotlinx.android.synthetic.main.layout_top.*


@SuppressLint("Registered")
open class BaseActivity : JBaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE //横屏
    }

    protected fun initToolBar(title: CharSequence?)
    {
        initToolBar(true, title)
    }

    protected fun initToolBar(back: Boolean, title: CharSequence?)
    {
        initToolBar(back, title, null, 0)
    }

    protected fun initToolBar(title: CharSequence?, rightTitle: CharSequence?, rightIcon: Int)
    {
        initToolBar(true, title, rightTitle, rightIcon)
    }


    protected fun initToolBar(back: Boolean, title: CharSequence?, rightTitle: CharSequence?, rightIcon: Int)
    {
        initToolBar(null, 0, back, title, rightTitle, rightIcon, rightTitle != null || rightIcon != 0)
    }

    protected fun initToolBar(leftTitle: CharSequence?, leftIcon: Int, leftVisible: Boolean, title: CharSequence?, rightTitle: CharSequence?,
                              rightIcon: Int, rightVisible: Boolean)
    {
        if(leftVisible)
        {
            mTvLeft.visible()
            mTvLeft.text = leftTitle
            mTvLeft.setCompoundDrawablesWithIntrinsicBounds(leftIcon, 0, 0, 0)
        } else
        {
            mTvLeft.gone()
        }
        mTvTitle.text = title
        if(rightVisible)
        {
            mTvRight.visible()
            mTvRight.text = rightTitle
            mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightIcon, 0)
        } else
        {
            mTvRight.gone()
        }
    }

}