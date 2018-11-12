package com.jzd.android.jon.core.module.permission

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.jzd.android.jon.core.Jon

/**
 * 权限管理
 *
 * 危险权限：
 * 权限组                    权限名
 * ---------------------------------------
 * CALENDAR（日历）          READ_CALENDAR
 *                          WRITE_CALENDAR
 * ---------------------------------------
 * CAMERA（相机）            CAMERA
 * ---------------------------------------
 * CONTACTS（联系人）        READ_CONTACTS
 *                          WRITE_CONTACTS
 *                          GET_ACCOUNTS
 * ---------------------------------------
 * LOCATION（位置）          ACCESS_FINE_LOCATION
 *                          ACCESS_COARSE_LOCATION
 * ---------------------------------------
 * MICROPHONE（麦克风）      RECORD_AUDIO
 * ---------------------------------------
 * PHONE（手机）             READ_PHONE_STATE
 *                          CALL_PHONE
 *                          ERAD_CALL_LOG
 *                          WRITE_CALL_LOG
 *                          ADD_VOICEMAIL
 *                          USE_SIP
 *                          PROCESS_OUTGOING_CALLS
 * -----------------------------------------
 * SENSORS（传感器）         BODY_SENSORS
 * -----------------------------------------
 * SMS（短信）               SEND_SMS
 *                          RECEIVE_SMS
 *                          READ_SMS
 *                          RECEIVE_WAP_PUSH
 *                          RECEIVE_MMS
 * -----------------------------------------
 * STORAGE（存储卡）         READ_EXTERNAL_STORAGE
 *                          WRITE_EXTERNAL_STORAGE
 *
 *
 * @author Jzd
 * @since 1.0
 */
object JPermission
{
    /**
     * 判断是否缺少权限
     */
    fun lackPermission(permissions: Array<out String>): Boolean
    {
        for(i in permissions.indices)
        {
            if(ActivityCompat.checkSelfPermission(Jon.mContext, permissions[i]) == PackageManager.PERMISSION_DENIED)
            {
                return true
            }
        }
        return false
    }
}