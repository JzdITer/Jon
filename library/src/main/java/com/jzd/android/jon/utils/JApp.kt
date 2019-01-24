package com.jzd.android.jon.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat.startActivity
import android.telephony.TelephonyManager
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

    /**
     * 拨打电话
     * 需添加权限 `<uses-permission android:name="android.permission.CALL_PHONE"/>`
     */
    fun call(phoneNumber: String): Intent
    {
        return Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
    }

    /**
     * 发送短信
     * <uses-permission android:name="android.permission.SEND_SMS" />
     */
    fun sendSms(phoneNumber: String?, content: String?): Intent
    {
        val uri = Uri.parse("smsto:" + (phoneNumber ?: ""))
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", content ?: "")
        return intent
    }

    /**
     * 是否为手机
     */
    fun isPhone(context: Context): Boolean
    {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.phoneType != TelephonyManager.PHONE_TYPE_NONE
    }


    /**
     * 序列号
     */
    @SuppressLint("PrivateApi", "HardwareIds")
    fun getSerialNumber(): String?
    {
        var serial: String? = null
        try
        {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            serial = get.invoke(c, "ro.serialno") as String
        } catch(e: Exception)
        {
            e.printStackTrace()
        }

        return serial
    }

    /**
     * 获取设备唯一编码
     */
    fun getDeviceUniqueCode(context: Context): String
    {
        val phoneName = Build.MODEL // 型号
        val factoryName = Build.MANUFACTURER // 品牌
        var serialNumber = getSerialNumber()
        if(serialNumber == null || serialNumber.isEmpty())
        {
            serialNumber = getAndroidId(context)
        }
        return "$factoryName-$phoneName-$serialNumber"
    }

    /**
     * 获取android设备码
     *  * 可能相同或为空
     *  * 恢复出场设置可能会重置
     */
    fun getAndroidId(context: Context): String
    {
        return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * 获取IMEI
     *  * 只有手机才有imei
     *  * 需要 android.Manifest.permission.READ_PHONE_STATE 权限,6.0以后动态申请
     *  * 可能为空
     */
    @SuppressLint("HardwareIds", "MissingPermission")
    fun getIMEI(context: Context): String?
    {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if(isPhone(context))
        {
            if(Build.VERSION.SDK_INT >= 26)
            {
                telephonyManager.imei
            } else
            {
                telephonyManager.deviceId
            }
        } else null
    }

}