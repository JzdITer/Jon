package com.jzd.android.jon.core.ui

import android.content.Intent
import android.os.Bundle

/**
 * 启动页面
 * @author Jzd
 * @since 1.0
 */
class JSplashActivity : JBaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // 防止重复启动
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0)
        {
            finish()
        }
    }
}
