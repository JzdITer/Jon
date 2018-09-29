package com.jzd.android.jon.utils

import com.jzd.android.jon.core.Jon

/**
 * 版本工具类
 *
 * @author jzd
 * @since  v1.0
 */
class JVersion
{
    companion object
    {

        /**
         * 获取版本号
         */
        fun getVersionCode(): Int
        {
            return try
            {
                Jon.mContext.packageManager.getPackageInfo(Jon.mContext.packageName, 0).versionCode
            } catch (ex: Exception)
            {
                0
            }
        }

        /**
         * 获取版本名称
         */
        fun getVersionName(): String
        {
            return try
            {
                Jon.mContext.packageManager.getPackageInfo(Jon.mContext.packageName, 0).versionName
            } catch (e: Exception)
            {
                ""
            }
        }
    }
}