package com.jzd.android.jon.utils

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context

/**
 * 应用运行时信息
 * @author Jzd
 * @since 1.0
 */
class JApp
{
    companion object
    {
        @Suppress("DEPRECATION")
        /**
         * 得到栈顶的Activity
         * 通过 getTopActivity() == componentName 来判断
         */
        fun getTopActivity(context: Context): ComponentName?
        {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningTasks = manager.getRunningTasks(1)
            if (runningTasks != null && runningTasks.isNotEmpty())
            {
                return runningTasks[0].topActivity
            }
            return null
        }

        @Suppress("DEPRECATION")
        /**
         * 判断是否在运行
         */
        fun isRunning(context: Context):Boolean
        {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            // 貌似只能获取本App的信息
            val runningTasks = manager.getRunningTasks(10)
            if(runningTasks != null)
            {
                for (task in runningTasks)
                {
                    if(task.topActivity.packageName == context.packageName || task.baseActivity.packageName == context.packageName)
                    {
                        return true
                    }
                }
            }
            return false
        }
    }
}