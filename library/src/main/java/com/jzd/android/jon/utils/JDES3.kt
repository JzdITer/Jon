package com.jzd.android.jon.utils

class JDES3
{

    lateinit var mKey: ByteArray
    var mKeyIv: ByteArray? = null
    lateinit var mChartName: String

    fun getInstance(key: ByteArray, keyIv: ByteArray, chartName: String)
    {
        mKey = key
        mChartName = chartName
    }
}

