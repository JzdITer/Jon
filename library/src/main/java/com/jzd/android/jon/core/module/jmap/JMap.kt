package com.jzd.android.jon.core.module.jmap


/**
 * JMapInter子类,可以不用实现obj()方法,在只使用Key-Value功能的情况下继承该类
 *
 * @author jzd
 * @since  v1.0
 */

class JMap<out K, out V>(val key: K, val value: V) : JMapImpl<K, V>
{

    override fun key(): K
    {
        return key
    }

    override fun value(): V
    {
        return value
    }

    override fun obj(): Any?
    {
        return this
    }
}