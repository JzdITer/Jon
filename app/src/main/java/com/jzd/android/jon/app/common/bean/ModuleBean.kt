package com.jzd.jutils.app.common.bean

import android.app.Activity
import android.support.annotation.DrawableRes
import com.jzd.android.jon.app.R

/**
 * Created by Jzd on 2018/7/10.
 */
data class ModuleBean(val title: String = "", val target: Class<out Activity>? = null, @DrawableRes val icon: Int = R.mipmap.ic_launcher)