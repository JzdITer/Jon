package com.jzd.android.jon.core.module.permission

/**
 * 请求权限接口
 * @author Jzd
 * @since 1.0
 */
interface PermissionListener
{
    fun onResult(granted: Boolean)
}