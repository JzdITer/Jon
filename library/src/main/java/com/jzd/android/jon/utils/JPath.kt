package com.jzd.android.jon.utils

import android.os.Environment
import com.jzd.android.jon.core.Jon
import java.io.File

/**
 *
 * @author Jzd
 */
object JPath
{

    /**
     * 获取根路径
     */
    fun getRootPath(): File
    {
        return if(isExternalExists())
        {
            getExternalPath()
        } else
        {
            getFilesDir()
        }

    }

    /**
     * 得到外部存储的挂载状态
     */
    fun isExternalExists(): Boolean
    {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 得到外部存储的路径
     */
    fun getExternalPath(): File
    {
        return Environment.getExternalStorageDirectory()
    }

    /**
     * 外部缓存
     * @return /storage/emulated/0/Android/data/包名/cache
     */
    fun getExternalCachePath(): File?
    {
        return Jon.mContext.externalCacheDir
    }

    /**
     * 外部存储路径
     * @return /storage/emulated/0/Android/data/包名/files/(例如：Music)
     */
    fun getExternalFilesPath(type: String): File?
    {
        return Jon.mContext.getExternalFilesDir(type)
    }

    /**
     * @return /data/user/0/包名/files
     */
    fun getFilesDir(): File
    {
        return Jon.mContext.filesDir
    }

    /**
     * @param type
     * Environment
     * The type of storage directory to return. Should be one of
     * {#DIRECTORY_MUSIC}, {#DIRECTORY_PODCASTS},
     * {#DIRECTORY_RINGTONES}, {#DIRECTORY_ALARMS},
     * {#DIRECTORY_NOTIFICATIONS}, {#DIRECTORY_PICTURES},
     * {#DIRECTORY_MOVIES}, {#DIRECTORY_DOWNLOADS}, or
     * {#DIRECTORY_DCIM}. May not be null.
     */
    fun getPublicPath(type: String): File
    {
        return Environment.getExternalStoragePublicDirectory(type)
    }

}