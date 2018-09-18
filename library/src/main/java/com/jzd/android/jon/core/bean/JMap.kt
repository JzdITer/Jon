package com.jzd.android.jon.core.bean

import com.jzd.android.jon.core.impl.JMapImpl


/**
 * JMapInter子类,可以不用实现obj()方法,在只使用Key-Value功能的情况下继承该类
 *
 * @author jzd
 * @since  v1.0
 */
class JMap(val key: String, val value: Any) : JMapImpl
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