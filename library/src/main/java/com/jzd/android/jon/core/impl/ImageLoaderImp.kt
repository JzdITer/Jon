package com.jzd.android.jon.core.impl

import android.content.Context
import android.widget.ImageView

interface ImageLoaderImp
{
    fun display(context: Context, path: Any, imageView: ImageView)

    fun stop(context: Context)

    fun clearMemory(context: Context)
}