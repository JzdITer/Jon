package com.jzd.android.jon.app.module.network

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JNetwork
import com.jzd.android.jon.utils.JToast
import kotlinx.android.synthetic.main.activity_network.*

class NetworkActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        setOnClick(mBtnStatus, mBtnType)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnStatus ->
            {
                val status = JNetwork.isConnected(mContext)
                JToast.show(if(status)
                {
                    "网络连接"
                } else
                {
                    "无网络连接"
                })
            }
            R.id.mBtnType ->
            {
                val type = JNetwork.getConnectedType(mContext)
                when(type)
                {
                    ConnectivityManager.TYPE_WIFI -> JToast.show("WIFI")
                    ConnectivityManager.TYPE_MOBILE -> JToast.show("Mobile")
                    -1 -> JToast.show("无连接")
                }
            }
        }
    }
}
