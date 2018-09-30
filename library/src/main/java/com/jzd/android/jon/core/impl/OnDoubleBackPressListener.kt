package com.jzd.android.jon.core.impl

/**
 * 双击返回监听
 * @author Jzd
 * @since 1.0
 */
interface OnDoubleBackPressListener
{
    /**
     * 因为双击退出事件由2个返回事件组成，如果界面需要先消费第一个返回事件，返回true，此时不会向下传递，返回false，会向下传递
     * @return true:子类自己消费该事件，比如双击退出时，需要先关闭弹窗,子类消费了该事件中的第一个返回事件
     *          false:不消费该事件
     */
    fun onPress(): Boolean
}