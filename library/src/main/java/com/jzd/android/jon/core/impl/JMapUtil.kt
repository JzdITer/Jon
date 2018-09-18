package com.jzd.android.jon.core.impl

import com.jzd.android.jon.core.bean.JMap

/**
 * 将Any转换成JMap
 */
fun Any.toJMap(): JMapImpl
{
    return this as? JMapImpl ?: JMap("", this)
}