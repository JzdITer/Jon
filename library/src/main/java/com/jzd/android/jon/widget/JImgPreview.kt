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
import com.jzd.android.jon.utils.clearItemDecoration
import com.jzd.android.jon.utils.gone
import com.jzd.android.jon.utils.visible
import io.reactivex.internal.operators.maybe.MaybeConcatArrayDelayError

/**
 * 因为xml设置LayoutManager是反射注入，在init方法时还不能获取layoutManager
 */
class JImgPreview(context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle)
{

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var mMaxCount = -1
    private lateinit var mAdapter: ImgAdapter
    private var mAddable = false // 是否可以添加
    private var mDelete = false // 是否可以删除

    init
    {
        if(attrs != null)
        {
            val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.JImgPreview)
            // 最大数量 -1:不限制数量
            mMaxCount = attributeSet.getInt(R.styleable.JImgPreview_j_img_preview_max_count, -1)

            val width = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_item_width, 100)
            val height = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_item_height, 100)

            val padding = attributeSet.getDimensionPixelSize(R.styleable.JImgPreview_j_img_preview_padding, 0) / 2
            setPadding(padding, padding, padding, padding)

            mAddable = attributeSet.getBoolean(R.styleable.JImgPreview_j_img_preview_add, false)
            mDelete = attributeSet.getBoolean(R.styleable.JImgPreview_j_img_preview_delete, false)


            mAdapter = ImgAdapter(context, width, height, mAddable, mDelete, mMaxCount)
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

    fun setData(data: List<Any>): JImgPreview
    {
        if(Jon.imageLoader == null)
        {
            throw NullPointerException("ImageLoader不能为空，请使用Jon.init()初始化")
        }
        mAdapter.setData(data)
        return this
    }

    fun setOnPreviewItemClickListener(onItemClickListener: OnPreviewItemClickListener)
    {
        mAdapter.mOnItemClickListener = onItemClickListener
    }

    /**
     * 主动删除
     */
    fun delete(position: Int)
    {
        mAdapter.delete(position)
    }

}

private class ImgAdapter(val context: Context, val width: Int, val height: Int, val addable: Boolean, val delete: Boolean, val maxCount: Int) :
        RecyclerView.Adapter<MyViewHolder>()
{
    val mData = arrayListOf<Any>()
    val mAddBtn = R.drawable.jon_ic_img_preview_add
    var mOnItemClickListener: OnPreviewItemClickListener? = null

    private fun checkData(checkData: List<Any>): List<Any>
    {
        val result = arrayListOf<Any>()
        val data = arrayListOf<Any>()
        data.addAll(checkData - mAddBtn)

        // 如果有限制数量
        if(maxCount > 0)
        {
            if(data.size > maxCount)
            {
                result.addAll(data.subList(0, maxCount))
            } else
            {
                result.addAll(data)
            }
        } else
        {
            result.addAll(data)
        }
        // +
        if(needAddBtn())
        {
            result.add(mAddBtn)
        }
        return result
    }

    fun setData(data: List<Any>)
    {
        mData.clear()
        mData.addAll(checkData(data))
        notifyDataSetChanged()
    }

    fun addData(data: Any)
    {
        addData(arrayListOf(data))
    }

    fun addData(data: List<Any>)
    {
        val oldData = arrayListOf<Any>()
        oldData.addAll(mData)
        oldData.addAll(data)
        val result = checkData(oldData)
        mData.clear()
        mData.addAll(result)
        notifyDataSetChanged()
    }

    private fun needAddBtn(): Boolean
    {
        if(addable)
        {
            // 限制了数量
            if(maxCount > 0)
            {
                // 达到最大数据
                if(mData.size >= maxCount)
                {
                    return false
                }
            }
            return true
        } else
        {
            return false
        }
    }

    /**
     * 返回图片数据，不包含图片按钮
     */
    fun getData(): ArrayList<Any>
    {
        val data = arrayListOf<Any>()
        if(mData.contains(mAddBtn))
        {
            data.addAll(mData)
            data.remove(mAddBtn)
        }
        return data
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
        holder.mIvDelete.visibility = View.GONE
        if(mData[position] != mAddBtn)
        {
            Jon.imageLoader?.display(context, mData[position], holder.mIvImg)
            holder.mIvImg.setOnClickListener({
                if(mOnItemClickListener != null)
                {
                    mOnItemClickListener!!.onImgClick(position, mData[position])
                }
            })
            holder.mIvDelete.setOnClickListener({

                if(mOnItemClickListener != null)
                {
                    val call = !mOnItemClickListener!!.onDeleteClick(position)
                    if(call)
                    {
                        delete(position)
                    }
                } else
                {
                    delete(position)
                }
            })
            if(delete)
            {
                holder.mIvDelete.visible()
            } else
            {
                holder.mIvDelete.gone()
            }
        } else
        {
            holder.mIvImg.setImageResource(mAddBtn)
            holder.mIvImg.setOnClickListener({
                if(mOnItemClickListener != null)
                {
                    mOnItemClickListener!!.onAddClick()
                }
            })

            holder.mIvDelete.setOnClickListener(null)
            holder.mIvDelete.gone()
        }
    }

    fun delete(position: Int)
    {
        // 完成删除操作  再回调
        mData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, itemCount)
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

abstract class OnSimplePreviewItemClickListener : OnPreviewItemClickListener
{
    override fun onDeleteClick(position: Int): Boolean
    {
        return false
    }
}

interface OnPreviewItemClickListener
{
    /**
     * 图片点击事件
     */
    fun onImgClick(position: Int, obj: Any)

    /**
     * 删除事件
     */
    fun onDeleteClick(position: Int): Boolean

    /**
     * 添加事件
     */
    fun onAddClick()
}