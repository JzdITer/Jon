package com.jzd.android.jon.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jzd.android.jon.R
import com.jzd.android.jon.core.Jon
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.utils.JLog
import com.jzd.android.jon.utils.clearItemDecoration

/**
 * 因为xml设置LayoutManager是反射注入，在init方法时还不能获取layoutManager
 */
class JImgPreview(context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle)
{

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var mMaxCount = -1
    private lateinit var mAdapter: ImgAdapter

    init
    {
        if(attrs != null)
        {
            val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.JImgPreview)
            JLog.e(mMaxCount.toString())
            // 最大数量
            mMaxCount = attributeSet.getInt(R.styleable.JImgPreview_j_img_preview_max_count, -1)

            val width = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_item_width, 100)
            val height = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_item_height, 100)

            val padding = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_padding, 0) / 2

            setPadding(padding, padding, padding, padding)

            mAdapter = ImgAdapter(context, width, height)
            adapter = mAdapter
            attributeSet.recycle()
        }
    }

    override fun setLayoutManager(layout: LayoutManager)
    {
        if(itemDecorationCount <= 0)
        {
            val drawableV = ContextCompat.getDrawable(context, R.drawable.drawable_item_decoration_empty_v)!!
            val drawableH = ContextCompat.getDrawable(context, R.drawable.drawable_item_decoration_empty_h)!!
            if(layout is GridLayoutManager)
            {
                val itemDecoration1 = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecoration1.setDrawable(drawableV)
                val itemDecoration2 = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                itemDecoration2.setDrawable(drawableH)
                clearItemDecoration()
                addItemDecoration(itemDecoration1)
                addItemDecoration(itemDecoration2)
            } else if(layout is LinearLayoutManager)
            {
                val orientation = layout.orientation
                val itemDecoration = DividerItemDecoration(context, orientation)
                if(orientation == DividerItemDecoration.HORIZONTAL)
                {
                    itemDecoration.setDrawable(drawableH)
                } else if(orientation == DividerItemDecoration.VERTICAL)
                {
                    itemDecoration.setDrawable(drawableV)
                }
                clearItemDecoration()
                addItemDecoration(itemDecoration)
            }
        }
        super.setLayoutManager(layout)
    }

    fun setData(data: List<JMapImpl>)
    {
        if(Jon.imageLoader == null)
        {
            throw NullPointerException("ImageLoader不能为空，请使用Jon.init()初始化")
        }
        mAdapter.setData(data)
    }


}

private class ImgAdapter(val context: Context, val width: Int, val height: Int) : RecyclerView.Adapter<MyViewHolder>()
{
    val mData = arrayListOf<JMapImpl>()
    fun setData(data: List<JMapImpl>)
    {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.jon_rv_item_j_img_preview, parent, false)
        return MyViewHolder(view, width, height)
    }

    override fun getItemCount(): Int
    {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        Jon.imageLoader?.display(context, mData[position].value(), holder.mIvImg)
        holder.mIvDelete.setOnClickListener {

        }
    }
}

private class MyViewHolder(itemView: View, width: Int, height: Int) : RecyclerView.ViewHolder(itemView)
{
    var mIvImg: ImageView = itemView.findViewById(R.id.mIvImg)
    var mIvDelete: ImageView = itemView.findViewById(R.id.mIvDelete)

    init
    {
        val layoutParams = mIvImg.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        mIvImg.layoutParams = layoutParams
    }
}