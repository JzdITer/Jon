package com.jzd.android.jon.utils

import android.content.Context

object JBarUtil
{
    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int
    {
        var result = 0
        val resourceId = context.resources
                .getIdentifier("status_bar_height", "dimen", "android")
        if(resourceId > 0)
        {
            result = context.resources
                    .getDimensionPixelSize(resourceId)
        }
        return result
    }
}