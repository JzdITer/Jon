package com.jzd.android.jon.app.module.anim.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_anim_main.*

class AnimMainActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_main)

        setOnClick(mBtnExpand,mBtnScale)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnExpand->
            {
                val anim = ValueAnimator.ofInt(0, mTvAnim.width).setDuration(1000)
                anim.addUpdateListener {
                    val layoutParams = mTvAnim.layoutParams
                    layoutParams.width = it.animatedValue as Int
                    mTvAnim.layoutParams = layoutParams
                }
                anim.start()
            }
            R.id.mBtnScale->
            {
                val anim = ScaleAnimation(mTvAnim.x, mTvAnim.width.toFloat(),mTvAnim.y, mTvAnim.height.toFloat())
                anim.duration = 2000
                mTvAnim.startAnimation(anim)
            }
        }
    }
}

