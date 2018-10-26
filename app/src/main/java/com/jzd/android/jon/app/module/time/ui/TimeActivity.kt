package com.jzd.android.jon.app.module.time.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JTime
import kotlinx.android.synthetic.main.activity_time.*
import java.util.*

class TimeActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        setOnClick(mBtnChangeLong, mBtnChangeString)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnChangeLong ->
            {
                mEdtTimeLong.setText(JTime.parseDate(mEdtTimeString.text.toString()).time.toString())
            }
            R.id.mBtnChangeString ->
            {
                val timeL = mEdtTimeLong.text.toString().toLongOrNull()
                if(timeL != null)
                {
                    mEdtTimeString.setText(JTime.format(Date(timeL)))
                }
            }
        }
    }
}
