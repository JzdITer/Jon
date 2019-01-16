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

    // 画竖向分割线
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
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
            i++
        }
    }

    // 画横向分割线
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
        val position = parent.getChildAdapterPosition(view)
        val spanCount = getSpanCount(parent)
        val isLastLaw = isLastLaw(position, spanCount, parent)
        val isLastColumn = isLastColumn(position, spanCount, parent)


        if(isLastColumn && isLastLaw)
        {
            outRect.set(0,0,0,0)
        } else if(isLastLaw(position, spanCount, parent))
        {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        } else if(isLastColumn)
        {
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else
        {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
        }
       // outRect.set(0, 0, mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
    }

    private fun isLastLaw(position: Int, spanCount: Int, parent: RecyclerView): Boolean
    {
        val orientation = getOrientation(parent)
        if(orientation == RecyclerView.HORIZONTAL)
        {
            return (position + 1) % spanCount == 0
        } else if(orientation == RecyclerView.VERTICAL)
        {
            return position in (parent.childCount - (parent.childCount % spanCount) until parent.childCount)
        }
        return false
    }

    private fun isLastColumn(position: Int, spanCount: Int, parent: RecyclerView): Boolean
    {
        val orientation = getOrientation(parent)
        if(orientation == RecyclerView.HORIZONTAL)
        {
            return position in (parent.childCount - (parent.childCount % spanCount) until parent.childCount)
        } else if(orientation == RecyclerView.VERTICAL)
        {
            return (position + 1) % spanCount == 0
        }
        return false
    }

    private fun getOrientation(parent: RecyclerView): Int
    {
        val layoutManager = parent.layoutManager
        if(layoutManager is GridLayoutManager)
        {
            return layoutManager.orientation
        } else if(layoutManager is StaggeredGridLayoutManager)
        {
            return layoutManager.orientation
        }
        return -1
    }

}