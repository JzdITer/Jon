package com.jzd.android.jon.app.common.bean

import com.jzd.android.jon.core.impl.JMapImpl


data class MapBean(val key: String, val value: String) : JMapImpl
{
    override fun key(): Any
    {
        return key
    }

    override fun value(): Any
    {
        return value
    }

    override fun obj(): Any?
    {
        return this
    }
}