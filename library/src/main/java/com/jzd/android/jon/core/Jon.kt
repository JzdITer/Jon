package com.jzd.android.jon.core

import android.annotation.SuppressLint
import android.content.Context
import com.jzd.android.jon.core.impl.ImageLoaderImp

/**
 * @author Jzd
 * @since 1.0
 */
// todo okgo
// @Throws(Throwable::class)
// popupwindow
// todo webview
@SuppressLint("StaticFieldLeak")
object Jon
{
    lateinit var mContext: Context
    var imageLoader: ImageLoaderImp? = null

    fun init(context: Context): Jon
    {
        mContext = context
        return this
    }

    fun initImageLoader(imageLoaderImp: ImageLoaderImp): Jon
    {
        imageLoader = imageLoaderImp
        return this
    }
}