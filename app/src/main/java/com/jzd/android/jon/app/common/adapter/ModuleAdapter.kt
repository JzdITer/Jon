package com.jzd.android.jon.app.app.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzd.android.jon.app.R
import com.jzd.jutils.app.common.bean.ModuleBean

/**
 * Created by Jzd on 2018/7/10.
 */
class ModuleAdapter(layoutResId: Int, data: MutableList<ModuleBean>?) : BaseQuickAdapter<ModuleBean, BaseViewHolder>(layoutResId, data)
{
    override fun convert(helper: BaseViewHolder?, item: ModuleBean?)
    {
        if (helper != null && item != null)
        {
            val textView = helper.getView<TextView>(R.id.mTvModule)
            textView?.text = item.title
            textView?.setCompoundDrawablesWithIntrinsicBounds(0, item.icon, 0, 0)
        }
    }

}
