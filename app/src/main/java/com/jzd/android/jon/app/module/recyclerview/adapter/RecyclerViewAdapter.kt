package com.jzd.android.jon.app.module.recyclerview.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzd.android.jon.app.R
import com.jzd.android.jon.core.module.jmap.JMapImpl

class RecyclerViewAdapter(layoutResId: Int, data: MutableList<JMapImpl<*, *>>?) : BaseQuickAdapter<JMapImpl<*, *>, BaseViewHolder>(layoutResId, data)
{
    override fun convert(helper: BaseViewHolder?, item: JMapImpl<*, *>?)
    {
        helper?.setText(R.id.mTvItem, item?.value().toString())
    }
}
