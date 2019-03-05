package com.jzd.android.jon.widget.zoomimageview

import android.content.Context

class ZoomImageBuilder<T> constructor(val context: Context)
{
    private var mData = arrayListOf<T>()
    private var mCurIndex = 0
    /**
     * 单击关闭
     */
    private var mClickDismiss = true

    /**
     * 设置数据
     */
    fun setData(data: List<T>)
    {
        this.mData.clear()
        this.mData.addAll(data)
    }

    /**
     * 设置当前数据下标,需要先setData，否则无效
     */
    fun setIndex(index: Int): ZoomImageBuilder<T>
    {
        mCurIndex = if(index < 0 || index >= mData.size)
        {
            0
        } else
        {
            index
        }
        return this
    }

    /**
     * 设置是否单击关闭
     */
    fun setClickDismiss(clickDismiss: Boolean): ZoomImageBuilder<T>
    {
        mClickDismiss = clickDismiss
        return this
    }

    /**
     * 启动预览
     */
    fun start()
    {
        if(mData[0] is String)
        {

        }
    }
}