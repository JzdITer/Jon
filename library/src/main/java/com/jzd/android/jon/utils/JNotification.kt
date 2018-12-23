package com.jzd.android.jon.utils

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import android.util.SparseArray
import com.jzd.android.jon.core.Jon

/**
 * Notification工具类
 *
 * 创建notification:
 *      NotificationCompat.Builder(context) //创建，8.0以后必须指定channelId, 使用setChannelId(channelId)或者Builder(context,channelId)
 *      .setAutoCancel(mCbAutoCancel.isChecked) // 点击后自动取消
 *      .setOngoing(mCbOnGoing.isChecked) // 是否是一个持续notification,持久的不能被用户取消
 *      .setColorized(mCbColorized.isChecked) // 是否着色,各手机表现不一,配合setColor(Color.GREEN)使用
 *      .setOnlyAlertOnce(mCbOnlyAlertOnce.isChecked) // 是否只在第一次提醒时产生default效果
 *      .setDefaults(NotificationCompat.DEFAULT_ALL) // 默认效果
 *      .setContentText(mEdtContentText.text.toString()) // 内容
 *      .setTicker(mEdtTicker.text.toString()) // 第一次弹出时，出现在状态栏的文字
 *      .setContentInfo(mEdtContentInfo.text.toString()) // 右边文字，各手机表现不一
 *      .setContentTitle(mEdtTitle.text.toString()) // 标题
 *      .setSmallIcon(R.mipmap.ic_launcher) // 小图标
 *      .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_plane)) // 大图标，各手机表现不一，华为8.0会出现在右侧
 *      .setNumber(mEdtNumber.text.toString().toInt()) // 设置消息数量，在8.0后 长按时间会弹出消息列表，出现消息数量，表现不一
 *
 * notification设置：
 *      notification = builder.build()
 *      notification.flags = NotificationCompat.FLAG_SHOW_LIGHTS // 使用呼吸灯
 *
 *      bundle.putString("data", "我是一个Bundle.负责从NotificationMainActivity传值到NotificationInfoActivity")
 *      val intent = Intent(this, NotificationInfoActivity::class.java)
 *      intent.putExtra("bundle", bundle)
 *      pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
 *      notification.contentIntent = pendingIntent // 点击事件
 *
 * 鉴于兼容性问题，简化版本:
 *      @Suppress("DEPRECATION")
 *      private fun initNotification(): Notification
 *      {
 *          val builder: NotificationCompat.Builder
 *          builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
 *          {
 *              val channel = NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT)
 *              channel.enableLights(true) // 桌面icon上角标
 *              // channel.lightColor = Color.RED
 *              channel.setShowBadge(true) // 长按弹出详情
 *              JNotification.getManager().createNotificationChannel(channel)
 *              NotificationCompat.Builder(this, channel.id)
 *          }else
 *          {
 *              NotificationCompat.Builder(this)
 *          }
 *          val notification = builder.setAutoCancel(true).setOnlyAlertOnce(true)
 *              .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE).setContentText("text")
 *              .setContentTitle("title").setSmallIcon(R.mipmap.ic_launcher).build()
 *          notification.flags = notification.flags or NotificationCompat.FLAG_SHOW_LIGHTS // 使用呼吸灯
 *          val pendingIntent = PendingIntent.getActivity(this, 1, Intent(), PendingIntent.FLAG_UPDATE_CURRENT)
 *          notification.contentIntent = pendingIntent // 点击事件
 *          return notification
 *      }
 *
 *
 * 有些时候setAutoCancel(true)不起作用，可能是因为设置了Notification.flags重置了flags
 * @author Jzd
 * @since 1.0
 */
object JNotification
{
    private const val TAG = "JNotification"
    private val mNotifications = SparseArray<Notification>()
    private var mCurId = 0
    private var mNotificationManager: NotificationManager = Jon.mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val mNotificationManagerCompat = NotificationManagerCompat.from(Jon.mContext)

    fun areNotificationsEnabled(): Boolean
    {
        return mNotificationManagerCompat.areNotificationsEnabled()
    }

    fun getManager(): NotificationManager
    {
        return mNotificationManager
    }


    /**
     * 此方法只要使用id来更新notification,内部id并不会自增
     */
    fun show(id: Int, notification: Notification)
    {
        if(mNotifications.get(id) != null)
        {
            mNotificationManager.notify(TAG, id, notification)
        } else
        {
            mNotifications.put(id, notification)
            mNotificationManager.notify(TAG, id, notification)
        }
    }


    /**
     * 使用该方法弹出notification，并且保证自增序列的准确
     */
    fun show(notification: Notification): Int
    {
        this.show(mCurId, notification)
        return mCurId++
    }

    /**
     * 根据id获取notification
     */
    fun get(id: Int): Notification?
    {
        return mNotifications.get(id)
    }

    /**
     * 取消notification
     */
    fun cancel(id: Int)
    {
        mNotificationManager.cancel(TAG, id)
        mNotifications.remove(id)
    }

    /**
     * 取消全部notification
     */
    fun cancel()
    {
        mNotificationManager.cancelAll()
        mNotifications.clear()
    }

}