package com.jzd.android.jon.utils

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.jzd.android.jon.core.module.jmap.JMap
import com.jzd.android.jon.core.module.jmap.JMapImpl
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

/**
 * 删除TextWatcher回调
 */
fun TextView.removeWatch(listener: TextWatcher)
{
    this.removeTextChangedListener(listener)
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

/**
 * 删除JFormItemView回调
 */
fun JFormItemView.removeWatch(listener: TextWatcher)
{
    this.getContentView().removeTextChangedListener(listener)
}

/**
 * 清除RecyclerView的ItemDecoration
 */
fun RecyclerView.clearItemDecoration()
{
    while(this.itemDecorationCount > 0)
    {
        this.removeItemDecorationAt(0)
    }
}

/**
 * 重新设置RecyclerView的ItemDecoration
 */
fun RecyclerView.setItemDecoration(itemDecoration: RecyclerView.ItemDecoration)
{
    if(this.itemDecorationCount > 0)
    {
        clearItemDecoration()
    }
    addItemDecoration(itemDecoration)
}

/**
 * 为View设置data,该方法通过设置tag实现，如果手动调用setTag，该方法会失效
 */
fun View.setData(map: JMapImpl<Any, Any>)
{
    this.tag = map
    if(this is TextView)
    {
        this.text = map.value().toString()
    } else if(this is JFormItemView)
    {
        this.setContent(map.value().toString())
    }
}

/**
 * 为View设置data,该方法通过设置tag实现，如果手动调用setTag，该方法会失效
 */
fun View.setData(msg: String)
{
    val map = JMap("", msg)
    setData(map)
}

/**
 * 获取设置的data
 */
fun View.getData(): JMapImpl<*, *>?
{
    return this.tag as JMapImpl<*, *>?
}

/**
 * 获取key
 */
fun View.getDataKey(): Any?
{
    return getData()?.key()
}

/**
 * 获取value
 */
fun View.getDataValue(): Any?
{
    return getData()?.value()
}
