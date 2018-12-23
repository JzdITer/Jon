package com.jzd.android.jon.app.module.imgpreview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.core.module.jmap.toJMap
import com.jzd.android.jon.utils.JToast
import com.jzd.android.jon.widget.OnPreviewItemClickListener
import com.jzd.android.jon.widget.OnSimplePreviewItemClickListener
import kotlinx.android.synthetic.main.activity_img_preview.*

class ImgPreviewActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_preview)

        val list = getData()
        mIPLinear.setData(list)

        mIPGrid.setData(list)
        mIPGrid.setOnPreviewItemClickListener(object : OnSimplePreviewItemClickListener()
        {
            override fun onImgClick(position: Int, obj: Any)
            {
                JToast.show("img")
            }

            override fun onAddClick()
            {
                JToast.show("添加")
            }

            override fun onDeleteClick(position: Int): Boolean
            {
                AlertDialog.Builder(mContext).setPositiveButton("是否删除") { _, _ ->
                    JToast.show("是")
                    mIPGrid.delete(position)
                }.setNegativeButton("取消", { _, _ ->

                }).show()
                return true
            }
        })
    }

    private fun getData(): List<String>
    {
        val list = arrayListOf<String>()
        val map = "https://goss.veer.com/creative/vcg/veer/800water/veer-167534806.jpg"
        for(i in 1..10)
        {
            list.add(map)
        }

        return list
    }
}
