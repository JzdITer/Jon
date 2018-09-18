package com.jzd.android.jon.utils

import android.content.Context

/**
 * 版本工具类
 *
 * @author jzd
 * @since  v1.0
 */
class JVersion {
    companion object {

        /**
         * 获取版本号
         */
        fun getVersionCode(context: Context): Int {
            return try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            }
            catch (ex: Exception) {
                0
            }
        }

        /**
         * 获取版本名称
         */
        fun getVersionName(context: Context): String {
            return try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            }
            catch (e: Exception) {
                ""
            }
        }
    }
}