package com.jzd.android.jon.utils

import com.jzd.android.jon.core.Jon

/**
 * 版本工具类
 *
 * Uri uri = Uri.parse(versionAPI.updateUrl);
 * Intent intent = new Intent();
 * intent.setAction(Intent.ACTION_VIEW);
 * intent.setData(uri);
 * context.startActivity(intent);
 * @author jzd
 * @since  v1.0
 */
object JVersion
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