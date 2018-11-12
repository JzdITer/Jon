package com.jzd.android.jon.utils

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import com.jzd.android.jon.core.Jon

/**
 * 应用运行时信息
 * @author Jzd
 * @since 1.0
 */
object JApp
{

    /**
     * 得到栈顶的Activity
     * 通过 getTopActivity() == componentName 来判断
     */
    @Suppress("DEPRECATION")
    fun getTopActivity(): ComponentName?
    {
        val manager = Jon.mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = manager.getRunningTasks(1)
        if(runningTasks != null && runningTasks.isNotEmpty())
        {
            return runningTasks[0].topActivity
        }
        return null
    }


    /**
     * 判断是否在运行
     */
    @Suppress("DEPRECATION")
    fun isRunning(): Boolean
    {
        val manager = Jon.mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 貌似只能获取本App的信息
        val runningTasks = manager.getRunningTasks(10)
        if(runningTasks != null)
        {
            for(task in runningTasks)
            {
                if(task.topActivity.packageName == Jon.mContext.packageName || task.baseActivity.packageName == Jon.mContext.packageName)
                {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 跳转设置界面
     */
    fun goSetting()
    {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.fromParts("package", Jon.mContext.packageName, null)
        startActivity(Jon.mContext, intent, null)
    }
}