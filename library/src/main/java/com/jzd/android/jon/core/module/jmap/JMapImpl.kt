package com.jzd.android.jon.core.module.jmap

/**
 * 键值对,实现该接口,可快速转换Key-Value形式
 *
 * @author jzd
 * @since  v1.0
 */

interface JMapImpl<out K,out V>
    : JMapImpl<Any, Any>
{
    /**
     * 返回Key
     */
    fun key(): K

    /**
     * 返回Value
     */
    fun value(): V

    /**
     * 返回本身
     */
    fun obj(): Any?
}