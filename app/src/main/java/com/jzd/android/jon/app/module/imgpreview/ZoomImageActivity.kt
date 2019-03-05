package com.jzd.android.jon.app.module.imgpreview

import android.os.Bundle
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.app.common.util.MyImageLoader
import com.jzd.android.jon.core.ui.JZoomImageActivity
import kotlinx.android.synthetic.main.activity_zoom_image.*

class ZoomImageActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_image)

        val url = getStartString()
        if(url != null)
        {
            MyImageLoader.display(mContext,url,mZiv)
        }

        mZiv.setOnClickListener {
            start(JZoomImageActivity::class.java)
        }
    }
}
