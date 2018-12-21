package com.jzd.android.jon.app.module.imgpreview

import android.os.Bundle
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.core.module.jmap.toJMap
import kotlinx.android.synthetic.main.activity_img_preview.*

class ImgPreviewActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_preview)

        val list = getData()
        mIPLinear.setData(list)

        mIPLinear2.setData(list)

        mIPGrid.setData(list)
    }

    private fun getData(): List<JMapImpl>
    {
        val list = arrayListOf<JMapImpl>()
        val map = "https://goss.veer.com/creative/vcg/veer/800water/veer-167534806.jpg".toJMap()
        for(i in 1..10)
        {
            list.add(map)
        }

        return list
    }
}
