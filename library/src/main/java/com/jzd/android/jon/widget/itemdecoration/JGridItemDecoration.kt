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
            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin + mDivider!!.intrinsicWidth
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
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
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
            i++
        }
    }

    private fun isLastColum(parent: RecyclerView, pos: Int, spanCount: Int,
                            childCount: Int): Boolean
    {
        val layoutManager = parent.layoutManager
        if(layoutManager is GridLayoutManager)
        {
            if((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
            {
                return true
            }
        } else if(layoutManager is StaggeredGridLayoutManager)
        {
            val orientation = layoutManager.orientation
            if(orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
                {
                    return true
                }
            } else
            {
                if(pos >= (childCount - childCount % spanCount)) // 如果是最后一列，则不需要绘制右边
                    return true
            }
        }
        return false
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int,
                          childCount: Int): Boolean
    {
        val layoutManager = parent.layoutManager
        if(layoutManager is GridLayoutManager)
        {
            if(pos >= childCount - childCount % spanCount) // 如果是最后一行，则不需要绘制底部
                return true
        } else if(layoutManager is StaggeredGridLayoutManager)
        {
            val orientation = layoutManager.orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if(orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                // 如果是最后一行，则不需要绘制底部
                if(pos >= childCount - childCount % spanCount)
                    return true
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if((pos + 1) % spanCount == 0)
                {
                    return true
                }
            }
        }
        return false
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        val itemPosition = parent.getChildAdapterPosition(view)
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter.itemCount
        if(isLastRaw(parent, itemPosition, spanCount, childCount)) // 如果是最后一行，则不需要绘制底部
        {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        } else if(isLastColum(parent, itemPosition, spanCount, childCount)) // 如果是最后一列，则不需要绘制右边
        {
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else
        {
            outRect.set(0, 0, mDivider!!.intrinsicWidth,
                    mDivider!!.intrinsicHeight)
        }
    }

}