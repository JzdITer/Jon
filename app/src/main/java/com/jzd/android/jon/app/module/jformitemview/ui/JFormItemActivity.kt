package com.jzd.android.jon.app.module.jformitemview.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.utils.JChecker
import com.jzd.android.jon.utils.JToast
import com.jzd.android.jon.utils.setData
import com.jzd.android.jon.widget.JFormItemView
import kotlinx.android.synthetic.main.activity_jform_item.*

class JFormItemActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jform_item)

        setOnClick(mFv1, mFv2,mFv3)

        val jMapImpl: JMapImpl?=null
        mFv1.setData(jMapImpl)

        JChecker.checkEmpty(false,mFv1,mFv2)
    }

    override fun onClick(v: View?)
    {
        JToast.show((v as JFormItemView).getContent())
    }
}
