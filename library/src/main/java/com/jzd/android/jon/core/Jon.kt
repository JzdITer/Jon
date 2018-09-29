package com.jzd.android.jon.core

import android.annotation.SuppressLint
import android.content.Context

/**
 * @author Jzd
 * @since 1.0
 */
class Jon
{
    companion object
    {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        fun init(context: Context)
        {
            mContext = context
        }
    }
}