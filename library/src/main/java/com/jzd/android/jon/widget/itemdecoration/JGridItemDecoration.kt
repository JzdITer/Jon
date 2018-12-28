package com.jzd.android.jon.widget.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class JGridItemDecoration(val mContext: Context) : RecyclerView.ItemDecoration()
{
    companion object
    {
        val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    private var mDivider: Drawable? = null

    init
    {
        if(mDivider == null)
        {
            val typedArray = mContext.obtainStyledAttributes(ATTRS)
            mDivider = typedArray.getDrawable(0)
            typedArray.recycle()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun getSpanCount(parent: RecyclerView): Int
    {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if(layoutManager is GridLayoutManager)
        {
            spanCount = layoutManager.spanCount
        } else if(layoutManager is StaggeredGridLayoutManager)
        {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView)
    {
        if(mDivider == null)
        {
            return
        }
        var i = 0
        while(i < parent.childCount)
        {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            // 最左边一列
            if(i in 0..getSpanCount(parent))
            {
                val left = child.left - params.leftMargin - mDivider!!.intrinsicWidth
                val right = left + mDivider!!.intrinsicWidth
                val top = child.top - params.topMargin
                val bottom = child.bottom + params.bottomMargin
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(c)
            }
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
            i++
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView)
    {
        if(mDivider == null)
        {
            return
        }
        var i = 0
        while(i < parent.childCount)
        {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            // 最上边一行
            if(i % getSpanCount(parent) == 0)
            {
                val left = child.left - params.leftMargin
                val right = child.right + params.rightMargin
                val top = child.top - params.topMargin - mDivider!!.intrinsicHeight
                val bottom = top + mDivider!!.intrinsicHeight
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(c)
            }
            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin
            val top = child.bottom - params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
            i++
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        outRect.set(mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight, mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
    }

}