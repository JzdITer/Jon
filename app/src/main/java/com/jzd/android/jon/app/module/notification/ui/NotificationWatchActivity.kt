package com.jzd.android.jon.app.module.notification.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JNotification
import com.jzd.android.jon.utils.JToast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_notification_watch.*
import java.util.concurrent.TimeUnit

class NotificationWatchActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_watch)

        setOnClick(mBtnShow, mBtnCancel, mBtnDownload, mBtnMain, mBtnEnable)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnEnable ->
            {
                val enable = JNotification.getManager().areNotificationsEnabled()
                JToast.show("通知权限：$enable")
            }
            R.id.mBtnShow ->
            {
                val initNotification = initNotification()
                JNotification.show(initNotification)
            }
            R.id.mBtnDownload ->
            {
                val initNotification = initDownloadNotification(0)
                val show = JNotification.show(initNotification)
                Observable.interval(1, 1, TimeUnit.SECONDS).subscribe(object : Observer<Long>
                {
                    override fun onComplete()
                    {
                        JNotification.cancel(show)
                    }

                    override fun onSubscribe(d: Disposable)
                    {

                    }

                    override fun onNext(t: Long)
                    {
                        if(t >= 10)
                        {
                            this.onComplete()
                        } else
                        {
                            val initDownloadNotification = initDownloadNotification(t.toInt().times(10))
                            JNotification.show(show, initDownloadNotification)
                        }
                    }

                    override fun onError(e: Throwable)
                    {
                    }
                })
            }
            R.id.mBtnCancel ->
            {
                JNotification.cancel()
            }
            R.id.mBtnMain ->
            {
                val intent = Intent(this, NotificationMainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @Suppress("DEPRECATION") private fun initDownloadNotification(progress: Int): Notification
    {
        val builder: NotificationCompat.Builder
        builder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true) // 桌面icon上角标
            // channel.lightColor = Color.RED
            channel.setShowBadge(true) // 长按弹出详情
            NotificationCompat.Builder(this, channel.id)
        } else
        {
            NotificationCompat.Builder(this)
        }
        return builder.setAutoCancel(true).setOnlyAlertOnce(true).setDefaults(NotificationCompat.DEFAULT_ALL).setContentText("当前进度:$progress%")
                .setProgress(100, progress, false).setOngoing(true).setContentTitle("下载").setSmallIcon(R.mipmap.ic_launcher).build()
    }

    @Suppress("DEPRECATION") private fun initNotification(): Notification
    {
        val builder: NotificationCompat.Builder
        builder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true) // 桌面icon上角标
            // channel.lightColor = Color.RED
            channel.setShowBadge(true) // 长按弹出详情
            NotificationCompat.Builder(this, channel.id)
        } else
        {
            NotificationCompat.Builder(this)
        }
        val intent = Intent(this, NotificationInfoActivity::class.java)
        val bundle = Bundle()
        bundle.putString("data", "一个标准的show")
        intent.putExtra("bundle", bundle)
        val notification =
                builder.setAutoCancel(true).setOnlyAlertOnce(true).setDefaults(NotificationCompat.DEFAULT_ALL).setContentText("text").setContentTitle("title")
                        .setSmallIcon(R.mipmap.ic_launcher).build()
        notification.flags = notification.flags or NotificationCompat.FLAG_SHOW_LIGHTS // 使用呼吸灯
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notification.contentIntent = pendingIntent // 点击事件
        return notification
    }
}
