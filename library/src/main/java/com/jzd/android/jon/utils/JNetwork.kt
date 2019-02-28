package com.jzd.android.jon.utils

import android.content.Context
import android.net.ConnectivityManager

object JNetwork
{
    fun isConnected(context: Context): Boolean
    {
        val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = service.activeNetworkInfo
        return info != null && info.isAvailable
    }

    fun getConnectedType(context: Context): Int
    {
        val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = service.activeNetworkInfo
        return info?.type ?: -1
    }

}