package com.jzd.android.jon.widget.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class JDividerItemDecoration(val mContext: Context, private val mOrientation: Int) : RecyclerView.ItemDecoration()
{
    private var mDivider: Drawable? = null
    private var mDividerSize = 0

    companion object
    {
        // <item name="android:listDivider">@drawable/divider</item>
        val ATTRS = intArrayOf(android.R.attr.listDivider)
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        const val VERTICAL = LinearLayoutManager.VERTICAL
    }

    init
    {
        if(mDivider == null)
        {
            val typedArray = mContext.obtainStyledAttributes(ATTRS)
            mDivider = typedArray.getDrawable(0)
            typedArray.recycle()
        }
    }

    fun setDivider(divider: Drawable?): JDividerItemDecoration
    {
        this.mDivider = divider
        return this
    }

    fun setDivider(color: Int, size: Int): JDividerItemDecoration
    {
        this.mDivider = ColorDrawable(color)
        mDividerSize = size
        return this
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        super.onDraw(c, parent, state)
        if(mOrientation == HORIZONTAL)
        {
            drawHorizontalDivider(c, parent)
        } else if(mOrientation == VERTICAL)
        {
            drawVerticalDivider(c, parent)
        }

    }

    private fun drawVerticalDivider(canvas: Canvas, parent: RecyclerView)
    {
        if(mDivider == null)
        {
            return
        }
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        var index = 0
        while(index < parent.childCount)
        {
            val child = parent.getChildAt(index)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = if(mDividerSize != 0)
            {
                top + mDividerSize
            } else
            {
                top + mDivider!!.intrinsicHeight
            }
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
            index++
        }
    }

    private fun drawHorizontalDivider(canvas: Canvas, parent: RecyclerView)
    {
        if(mDivider == null)
        {
            return
        }
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        var index = 0
        while(index < parent.childCount)
        {
            val child = parent.getChildAt(index)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = if(mDividerSize != 0)
            {
                left + mDividerSize
            } else
            {
                left + mDivider!!.intrinsicWidth
            }
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
            index++
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
        if(mDivider == null)
        {
            return
        }
        if(mOrientation == HORIZONTAL)
        {
            if(mDividerSize != 0)
            {
                outRect.set(0, 0, mDividerSize, 0)
            } else
            {
                outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
            }
        } else if(mOrientation == VERTICAL)
        {
            if(mDividerSize != 0)
            {
                outRect.set(0, 0, 0, mDividerSize)
            } else
            {
                outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
            }
        }
    }


}