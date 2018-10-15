package com.jzd.android.jon.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView

/**
 * View相关工具类
 * @author Jzd
 * @since 1.0
 */

class JView
{
    companion object
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