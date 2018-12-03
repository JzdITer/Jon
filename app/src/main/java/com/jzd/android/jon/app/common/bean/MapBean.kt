package com.jzd.android.jon.app.common.bean

import com.jzd.android.jon.core.module.jmap.JMapImpl


data class MapBean(val key: String, val value: String) : JMapImpl<String,String>
{
    override fun key(): String
    {
        return key
    }

    override fun value(): String
    {
        return value
    }

    override fun obj(): Any?
    {
        return this
    }
}