package com.jzd.android.jon.app.base

import android.os.Bundle
import android.os.CountDownTimer
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.module.main.ui.MainActivity

class SplashActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun isSplash(): Boolean
    {
        return true
    }

    override fun onResume()
    {
        super.onResume()
        object : CountDownTimer(2000, 500)
        {
            override fun onFinish()
            {
                cancel()
                start(MainActivity::class.java)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

            override fun onTick(millisUntilFinished: Long)
            {
                //JLog.d("启动时间=$millisUntilFinished")
            }
        }.start()
    }
}
