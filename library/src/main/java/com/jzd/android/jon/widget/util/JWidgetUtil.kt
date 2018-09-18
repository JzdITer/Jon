package com.jzd.android.jon.widget.util

import android.content.Context
import android.support.v7.widget.RecyclerView
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

/**
 * 清除RecyclerView的ItemDecoration
 */
fun RecyclerView.clearItemDecoration()
{
    while (this.itemDecorationCount > 0)
    {
        this.removeItemDecorationAt(0)
    }
}

/**
 * 重新设置RecyclerView的ItemDecoration
 */
fun RecyclerView.setItemDecoration(itemDecoration: RecyclerView.ItemDecoration)
{
    if (this.itemDecorationCount > 0)
    {
        clearItemDecoration()
    }
    addItemDecoration(itemDecoration)
}


