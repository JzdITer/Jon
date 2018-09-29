package com.jzd.android.jon.app.module.permission.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.core.module.permission.PermissionListener
import com.jzd.android.jon.utils.JApp
import com.jzd.android.jon.utils.JLog
import kotlinx.android.synthetic.main.activity_permission.*

/**
 * 权限测试类
 */
class PermissionActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        setOnClick(mBtnCamera, mBtnCamera2)
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.mBtnCamera ->
            {
                val checkSelfPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                //
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED)
                {
                    JLog.d("PERMISSION_DENIED")

                    val shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)
                    JLog.d("shouldShowRequestPermissionRationale=$shouldShowRequestPermissionRationale")
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 11)
                    //                    if (shouldShowRequestPermissionRationale)
                    //                    {
                    //                        JLog.d("shouldShowRequestPermissionRationale")
                    //                    } else
                    //                    {
                    //                        JLog.d("shouldShowRequestPermissionRationale:FALSE")
                    //
                    //                        ActivityCompat.requestPermissions(this, Arrays.copyOf(arrayOf(android.Manifest.permission.CAMERA), 1), 11)
                    //                    }
                } else
                {
                    JLog.d("PERMISSION_GRANTED")
                    JLog.d("GRANTED")
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 22)
                }
            }
            R.id.mBtnCamera2 ->
            {
                requestPermission(object : PermissionListener
                {
                    override fun onResult(granted: Boolean)
                    {
                        if (granted)
                        {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, 22)
                        } else
                        {
                            JLog.d(granted.toString())
                            AlertDialog.Builder(this@PermissionActivity).setMessage("请打开摄像头权限").setPositiveButton("去设置",
                                    { _: DialogInterface, _: Int ->
                                        JApp.goSetting()
                                    }).show()
                        }
                    }
                }, android.Manifest.permission.CAMERA)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        JLog.d("requestCode=$requestCode,permission=${permissions.asList()},grantResult=${grantResults.asList()}")
    }

    override fun shouldShowRequestPermissionRationale(permission: String?): Boolean
    {
        JLog.d("permission=$permission")
        return super.shouldShowRequestPermissionRationale(permission)
    }
}
