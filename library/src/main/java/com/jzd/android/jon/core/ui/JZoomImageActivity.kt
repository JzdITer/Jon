package com.jzd.android.jon.core.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.jzd.android.jon.R
import com.jzd.android.jon.core.Jon
import com.jzd.android.jon.widget.adapter.JPagerAdapter
import com.jzd.android.jon.widget.zoomimageview.JZoomImageView
import kotlinx.android.synthetic.main.activity_jzoom_image.*

class JZoomImageActivity : JBaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jzoom_image)

        mJvp.adapter = MyAdapter<String>()

    }


    inner class MyAdapter<T> : JPagerAdapter<T>()
    {
        private val views = arrayListOf<View>()
        private val res = arrayListOf(R.drawable.jon_ic_img_preview_add, R.drawable.jon_ic_img_preview_delete)
        override fun instantiateItem(container: ViewGroup, position: Int): Any
        {
            val view = JZoomImageView(this@JZoomImageActivity)
            if(Jon.imageLoader != null)
            {
                Jon.imageLoader!!.display(this@JZoomImageActivity, res[position], view)
            }
            container.addView(view)
            views.add(view)
            return view
        }

        override fun getCount(): Int
        {
            return res.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
        {
            container.removeView(`object` as View)
        }

    }
}
