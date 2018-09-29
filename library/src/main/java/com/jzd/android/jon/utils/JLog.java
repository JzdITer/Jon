package com.jzd.android.jon.utils;

import android.util.Log;

/**
 * Log工具类
 *
 * @author jzd
 * @since v1.0
 */
public class JLog
{
    private static String TAG = "JLog";
    private static boolean sDebug = true;

    /**
     * 是否开启Debug模式,只有开启时,才可以输出日志
     *
     * @param isDebug true:开启Debug模式 false:关闭Debug模式
     */
    public static void setDebug(boolean isDebug)
    {
        JLog.sDebug = isDebug;
    }

    /**
     * 重新设置TAG,此TAG为全局设置，推荐在Application中设置
     *
     * @param tag 需要设置的标志
     */
    public static void setTAG(String tag)
    {
        TAG = tag;
    }

    public static boolean isDebug()
    {
        return sDebug;
    }

    /**
     * 输出ERROR日志
     */
    public static void e(String msg)
    {
        if(sDebug)
        {
            Log.e(TAG, msg);
        }
    }

    public static void e(String msg, String tag)
    {
        if(sDebug)
        {
            Log.e(tag, msg);
        }
    }

    /**
     * 输出ERROR日志
     */
    public static void e(Throwable e)
    {
        if(sDebug)
        {
            Log.e(TAG, e.toString());
        }
    }

    public static void e(Throwable e, String tag)
    {
        if(sDebug)
        {
            Log.e(tag, e.toString());
        }
    }

    /**
     * 输出INFO日志
     */
    public static void i(String msg)
    {
        if(sDebug)
        {
            Log.i(TAG, msg);
        }
    }

    public static void i(String msg, String tag)
    {
        if(sDebug)
        {
            Log.i(tag, msg);
        }
    }

    /**
     * 输出DEBUG日志
     */
    public static void d(String msg)
    {
        if(sDebug)
        {
            Log.d(TAG, msg);
        }
    }

    public static void d(String msg, String tag)
    {
        if(sDebug)
        {
            Log.d(tag, msg);
        }
    }

    /**
     * 输出VERBOSE日志
     */
    public static void v(String msg)
    {
        if(sDebug)
        {
            Log.v(TAG, msg);
        }
    }

    public static void v(String msg, String tag)
    {
        if(sDebug)
        {
            Log.v(tag, msg);
        }
    }

    /**
     * 输出WARN日志
     */
    public static void w(String msg)
    {
        if(sDebug)
        {
            Log.w(TAG, msg);
        }
    }

    public static void w(String msg, String tag)
    {
        if(sDebug)
        {
            Log.w(tag, msg);
        }
    }

}
