package com.jzd.android.jon.app.common.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jzd.android.jon.core.impl.ImageLoaderImp

object MyImageLoader : ImageLoaderImp
{
    override fun display(context: Context, path: Any, imageView: ImageView)
    {
        Glide.with(context).load(path).into(imageView)
    }

    override fun onStop(context: Context)
    {
    }

    override fun clearMemory(context: Context)
    {
    }

}