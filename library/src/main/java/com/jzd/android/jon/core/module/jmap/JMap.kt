package com.jzd.android.jon.core.module.jmap


/**
 * JMapInter子类,可以不用实现obj()方法,在只使用Key-Value功能的情况下继承该类
 *
 * @author jzd
 * @since  v1.0
 */

class JMap(val key: Any, val value: Any) : JMapImpl
{

    constructor(value:Any):this("",value)

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