package com.jzd.android.jon.core.module.image

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.jzd.android.jon.utils.JLog


/**
 * 图片加框,注意使用Glide加载图片时使用asBitmap(),否则imageView.getDrawable()是GlideBitmapDrawable而不是BitmapDrawable
 */
fun ImageView.drawRect(rect: Rect, paint: Paint? = null)
{
    try
    {
        if (drawable != null)
        {
            val bitmapDrawable = drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
                    .copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)
            var p = paint
            if (p == null)
            {
                p = Paint()
                paint!!.color = Color.RED
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 10f
            }
            canvas.drawRect(rect, p)
            setImageBitmap(bitmap)
        }
    } catch (e: Exception)
    {
        JLog.e(e)
    }
}