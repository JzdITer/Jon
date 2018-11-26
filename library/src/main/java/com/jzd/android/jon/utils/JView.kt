package com.jzd.android.jon.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.jzd.android.jon.widget.JFormItemView

/**
 * View相关工具类
 * @author Jzd
 * @since 1.0
 */

object JView
{
    fun gone(vararg views: View)
    {
        views.forEach { view -> view.gone() }
    }

    fun visible(vararg views: View)
    {
        views.forEach { view -> view.visible() }
    }

    fun invisible(vararg views: View)
    {
        views.forEach { view -> view.invisible() }
    }
}

fun View.gone()
{
    this.visibility = View.GONE
}

fun View.visible()
{
    this.visibility = View.VISIBLE
}

fun View.invisible()
{
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean
{
    return visibility == View.VISIBLE
}

fun View.isGone(): Boolean
{
    return visibility == View.GONE
}

fun View.isInvisible(): Boolean
{
    return visibility == View.INVISIBLE
}

/**
 * 添加TextWatcher简单回调
 */
fun TextView.watch(listener: TextWatcher)
{
    this.addTextChangedListener(listener)
}

interface SimpleWatcherListener : TextWatcher
{
    override fun afterTextChanged(s: Editable?)
    {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    {
    }
}

/**
 * 为JFormItemView添加回调
 */
fun JFormItemView.watch(listener: TextWatcher)
{
    this.getContentView().addTextChangedListener(listener)
}
