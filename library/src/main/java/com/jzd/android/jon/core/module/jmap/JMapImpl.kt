package com.jzd.android.jon.core.module.jmap

/**
 * 键值对,实现该接口,可快速转换Key-Value形式
 *
 * @author jzd
 * @since  v1.0
 */

interface JMapImpl
{
    /**
     * 返回Key
     */
    fun key(): Any

    /**
     * 返回Value
     */
    fun value(): Any

    /**
     * 返回本身
     */
    fun obj(): Any?
}