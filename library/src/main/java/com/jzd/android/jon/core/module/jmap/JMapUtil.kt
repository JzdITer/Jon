package com.jzd.android.jon.core.module.jmap

import java.util.*

/**
 * 将Any转换成JMap
 */
fun Any.toJMap(): JMapImpl
{
    return this as? JMapImpl ?: JMap("", this)
}

fun List<JMapImpl>.values(): ArrayList<Any>
{
    val values = arrayListOf<Any>()
    forEach {
        values.add(it.value())
    }
    return values
}

fun List<JMapImpl>.keys(): ArrayList<Any>
{
    val keys = arrayListOf<Any>()
    forEach {
        keys.add(it.key())
    }
    return keys
}

fun List<JMapImpl>.asStringList(): ArrayList<String>
{
    val values = arrayListOf<String>()
    forEach {
        values.add(it.value().toString())
    }
    return values
}


fun List<JMapImpl>.asStringArray(): Array<String>
{
    val values: Array<String> = emptyArray()
    forEach {
        values.plus(it.value().toString())
    }
    return values
}