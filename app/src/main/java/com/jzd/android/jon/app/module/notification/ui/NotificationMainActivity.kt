package com.jzd.android.jon.app.module.notification.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JLog
import kotlinx.android.synthetic.main.activity_notification_main.*

/**
 * 兼容性很难做，基本就没有展示的一样的地方
 */
class NotificationMainActivity : BaseActivity()
{
    private val bundle = Bundle()
    private lateinit var pendingIntent: PendingIntent
    private lateinit var manager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_main)
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mBtnClear.setOnClickListener({
            if (mEdtCancelId.text.isNotEmpty())
            {
                manager.cancel(mEdtCancelId.text.toString().toInt())
            } else
            {
                manager.cancelAll()
            }
        })

        setOnClick(mBtnNot1, mBtnNot2, mBtnNot3)
    }

    override fun onClick(v: View?)
    {

        val notification = initNotification()
        // 使用呼吸灯
        notification.flags = NotificationCompat.FLAG_SHOW_LIGHTS

        when (v?.id)
        {
            R.id.mBtnNot1 ->
            {
                notification.contentIntent = pendingIntent
            }
            R.id.mBtnNot2 ->
            {

            }
            R.id.mBtnNot3 ->
            {
                notification.contentIntent = pendingIntent
                // 此属性可能会直接跳转
                notification.fullScreenIntent = pendingIntent
            }

        }
        try
        {
            manager.notify(mEdtId.text.toString().toInt(), notification)
        } catch (e: Exception)
        {
            JLog.e(e)
        }

    }

    @Suppress("DEPRECATION")
    private fun initNotification(): Notification
    {
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(mEdtChannelId.text.toString(), "channelName", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true) // 桌面icon上角标
            // channel.lightColor = Color.RED
            channel.setShowBadge(true) // 长按弹出详情
            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, channel.id)
        } else
        {
            builder = NotificationCompat.Builder(this)
        }
        builder.setAutoCancel(mCbAutoCancel.isChecked)
                .setOngoing(mCbOnGoing.isChecked).setColorized(mCbColorized.isChecked).setColor(Color.GREEN).setOnlyAlertOnce(mCbOnlyAlertOnce.isChecked)

        if (mCbDefAll.isChecked)
        {
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        } else if (mCbDefLights.isChecked)
        {
            builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS)
        } else if (mCbDefSound.isChecked)
        {
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        } else if (mCbDefVibrate.isChecked)
        {
            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
        }

        builder.setContentText(mEdtContentText.text.toString()).setTicker(mEdtTicker.text.toString())
                .setContentInfo(mEdtContentInfo.text.toString()).setContentTitle(mEdtTitle.text.toString())
                .setSmallIcon(R.mipmap.ic_launcher)
        if (mCbLargerIcon.isChecked)
        {
            builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_plane))
        }

        if (mEdtNumber.text.isNotEmpty())
        {
            builder.setNumber(mEdtNumber.text.toString().toInt())
        }

        bundle.putString("data", "我是一个Bundle.负责从NotificationMainActivity传值到NotificationInfoActivity")
        val intent = Intent(this, NotificationInfoActivity::class.java)
        intent.putExtra("bundle", bundle)
        pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return builder.build()
    }
}
