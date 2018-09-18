package com.jzd.android.jon.utils

import android.content.Context
import android.util.DisplayMetrics

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
        fun getWidth(context: Context): Int
        {
            return context.resources.displayMetrics.widthPixels
        }

        /**
         * 屏幕高
         */
        fun getHeight(context: Context): Int
        {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * dp转px
         */
        fun dp2px(context: Context, dp: Float): Int
        {
            return (context.resources.displayMetrics.density * dp).toInt()
        }

        /**
         * px转dp
         */
        fun px2dp(context: Context, px: Int): Float
        {
            return px / context.resources.displayMetrics.density
        }

        /**
         * sp转px
         */
        fun sp2px(context: Context, sp: Float): Int
        {
            return (context.resources.displayMetrics.density * sp).toInt()
        }

        /**
         * px转sp
         */
        fun px2sp(context: Context, px: Int): Float
        {
            return px / context.resources.displayMetrics.density
        }

        /**
         * 得到display metrics
         */
        fun getDisplayMetrics(context: Context): DisplayMetrics
        {
            return context.resources.displayMetrics
        }

        /**
         * 得到density
         */
        fun getDensity(context: Context): Float
        {
            return context.resources.displayMetrics.density
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
        fun getDensityDpi(context: Context): Int
        {
            return context.resources.displayMetrics.densityDpi
        }


    }
}