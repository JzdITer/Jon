package com.jzd.android.jon.app.module.tag.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tag.*

class TagActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        setOnClick(mTagEnglish, mTagMath, mTagProgramming)

    }

    override fun onClick(v: View?)
    {
        super.onClick(v)
        mTagEnglish.isSelected = false
        mTagMath.isSelected = false
        mTagProgramming.isSelected = false
        when(v?.id)
        {
            R.id.mTagEnglish ->
                mTagEnglish.isSelected = true
            R.id.mTagMath ->
                mTagMath.isSelected = true
            R.id.mTagProgramming ->
                mTagProgramming.isSelected = true
        }
    }
}
