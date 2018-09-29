package com.jzd.android.jon.core.module.permission

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.jzd.android.jon.core.Jon

class JPermission
{
    companion object
    {
        /**
         * 判断是否缺少权限
         */
        fun lackPermission(permissions: Array<out String>): Boolean
        {
            for (i in permissions.indices)
            {
                if (ActivityCompat.checkSelfPermission(Jon.mContext, permissions[i]) == PackageManager.PERMISSION_DENIED)
                {
                    return true
                }
            }
            return false
        }
    }
}