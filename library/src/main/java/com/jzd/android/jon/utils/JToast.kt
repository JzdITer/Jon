package com.jzd.android.jon.utils

import android.content.Context
import android.widget.Toast
import com.jzd.android.jon.core.Jon

/**
 * Toast工具类
 * @author Jzd
 * @since 1.0
 */
object JToast
{
    private var toast: Toast? = null

    fun show(message: CharSequence?)
    {
        show(Jon.mContext, message)
    }

    fun show(e: Throwable?)
    {
        show(Jon.mContext, e?.message)
    }

    fun show(context: Context, message: CharSequence?)
    {
        if(toast == null)
        {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast!!.show()
        } else
        {
            toast!!.setText(message)
            toast!!.show()
        }
    }
}