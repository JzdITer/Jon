package com.jzd.android.jon.core.module.jmap

/**
 * 将Any转换成JMap
 */
fun Any.toJMap(): JMapImpl<*, *>
{
    return this as? JMapImpl<*, *> ?: JMap("", this)
}