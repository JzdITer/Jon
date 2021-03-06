package com.jzd.android.jon.app.module.popupwindow

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.core.module.jmap.JMap
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.utils.JToast
import com.jzd.android.jon.widget.popupwindow.JBasePopupWindow
import com.jzd.android.jon.widget.popupwindow.JListPopupWindow
import kotlinx.android.synthetic.main.activity_popup_window.*

class PopupWindowActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_window)

        setOnClick(mBtnShow, mBtnShowList, mBtnShowTop, mBtnShowBottom, mBtnShowRight, mBtnShowLeft)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnShow ->
            {
                val view = TextView(mContext)
                view.text = "zjjds"
                val popupView = JBasePopupWindow(this, view)
                popupView.show()
            }
            R.id.mBtnShowList ->
            {
                val data = arrayListOf<JMapImpl>()
                data.add(JMap("1", "C"))
                data.add(JMap("2", "C++"))
                data.add(JMap("3", "C#"))
                data.add(JMap("4", "JAVA"))
                data.add(JMap("5", "Python"))
                JListPopupWindow(this, data,
                        JListPopupWindow.OnItemClickListener { _, obj ->
                            JToast.show(obj.value().toString())
                        }).setListGravity(Gravity.CENTER).setIm(true).show()
            }
            R.id.mBtnShowTop ->
            {
                val view = layoutInflater.inflate(R.layout.activity_jform_item, null, false)
                JBasePopupWindow(this, view).setAnim(JBasePopupWindow.Type.TOP).show()
            }
            R.id.mBtnShowBottom ->
            {
                val view = layoutInflater.inflate(R.layout.activity_jform_item, null, false)
                JBasePopupWindow(this, view).setAnim(JBasePopupWindow.Type.BOTTOM).show()
            }
            R.id.mBtnShowLeft ->
            {
                val view = layoutInflater.inflate(R.layout.activity_jform_item, null, false)
                JBasePopupWindow(this, view).setAnim(JBasePopupWindow.Type.LEFT).show(Gravity.START, 0, 0)
            }
            R.id.mBtnShowRight ->
            {
                val view = layoutInflater.inflate(R.layout.activity_jform_item, null, false)
                JBasePopupWindow(this, view).setAnim(JBasePopupWindow.Type.RIGHT).show()
            }
        }
    }
}
