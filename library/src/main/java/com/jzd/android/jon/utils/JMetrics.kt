package com.jzd.android.jon.utils

import android.util.DisplayMetrics
import com.jzd.android.jon.core.Jon

/**
 * 度量相关工具类
 *
 * @author jzd
 * @since  v1.0
 */
@SuppressWarnings("unused")
class JMetrics
{
    companion object
    {
        /**
         * 屏幕宽
         */
        fun getWidth(): Int
        {
            return Jon.mContext.resources.displayMetrics.widthPixels
        }

        /**
         * 屏幕高
         */
        fun getHeight(): Int
        {
            return Jon.mContext.resources.displayMetrics.heightPixels
        }

        /**
         * dp转px
         */
        fun dp2px(dp: Float): Int
        {
            return (Jon.mContext.resources.displayMetrics.density * dp).toInt()
        }

        /**
         * px转dp
         */
        fun px2dp(px: Int): Float
        {
            return px / Jon.mContext.resources.displayMetrics.density
        }

        /**
         * sp转px
         */
        fun sp2px(sp: Float): Int
        {
            return (Jon.mContext.resources.displayMetrics.density * sp).toInt()
        }

        /**
         * px转sp
         */
        fun px2sp(px: Int): Float
        {
            return px / Jon.mContext.resources.displayMetrics.density
        }

        /**
         * 得到display metrics
         */
        fun getDisplayMetrics(): DisplayMetrics
        {
            return Jon.mContext.resources.displayMetrics
        }

        /**
         * 得到density
         */
        fun getDensity(): Float
        {
            return Jon.mContext.resources.displayMetrics.density
        }

        /**
         * 得到densityDpi
         *
         * ldpi:120
         * mdpi:160
         * hdpi:240
         * xhdpi:320
         * xxhdpi:480
         */
        fun getDensityDpi(): Int
        {
            return Jon.mContext.resources.displayMetrics.densityDpi
        }


    }
}