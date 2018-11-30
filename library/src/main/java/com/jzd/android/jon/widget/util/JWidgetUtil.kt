package com.jzd.android.jon.widget.util

import android.content.Context
import android.widget.TextView

/**
 * Widget 下通用工具类
 * @author Jzd
 * @since 1.0
 */
class JWidgetUtil(context: Context)
{
    private var mTextView: TextView = TextView(context)

    /**
     * 默认字体大小
     */
    fun defTextSize(): Float
    {
        return mTextView.textSize
    }

    /**
     * 默认字体颜色
     */
    fun defTextColor(): Int
    {
        return mTextView.currentTextColor
    }

    /**
     * 默认hint字体颜色
     */
    fun defTextColorHint(): Int
    {
        return mTextView.currentHintTextColor
    }
}

