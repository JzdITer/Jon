package com.jzd.android.jon.core.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jzd.android.jon.core.impl.IContextDelegate
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.REQUEST_CODE_PERMISSION
import com.jzd.android.jon.core.impl.OnDoubleBackPressListener
import com.jzd.android.jon.core.module.permission.JPermission
import com.jzd.android.jon.core.module.permission.PermissionListener

/**
 * Activity父类 封装系统Api
 * @author Jzd
 * @since 1.0
 */
open class JBaseActivity : AppCompatActivity(), View.OnClickListener, IContextDelegate
{

    protected lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        mContext = this
    }

    override fun setContentView(layoutResID: Int)
    {
        super.setContentView(layoutResID)
    }

    // 双击退出----------------------------------------------------------------------------------
    private var mIsDoubleBack = false
    private var mBackPressTimer = 0L
    private var mDoubleBackMessage: CharSequence = ""
    private var mOnDoubleBackPressListener: OnDoubleBackPressListener? = null
    /**
     * 设置双击退出事件
     */
    fun setDoubleBack(message: CharSequence, onDoubleBackPressListener: OnDoubleBackPressListener? = null)
    {
        mOnDoubleBackPressListener = onDoubleBackPressListener
        mIsDoubleBack = true
        mDoubleBackMessage = message
    }

    override fun onBackPressed()
    {
        // 是否可退出
        if (mIsDoubleBack)
        {
            if (mOnDoubleBackPressListener != null)
            {
                val b = mOnDoubleBackPressListener!!.onPress()
                if (!b)
                {
                    doubleBack(mDoubleBackMessage)
                }
            } else
            {
                doubleBack(mDoubleBackMessage)
            }
        } else
        {
            super.onBackPressed()
        }
    }

    /**
     * 双击退出
     */
    private fun doubleBack(message: CharSequence)
    {
        // 判断双击退出事件
        if (System.currentTimeMillis() - mBackPressTimer >= 2000)
        {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            mBackPressTimer = System.currentTimeMillis()
        } else
        {
            // 退出app
            super.onBackPressed()
        }
    }
    // ----------------------------------------------------------------------------------------


    // 权限-------------------------------------------------------------------------------------
    /**
     * 请求权限
     */
    fun requestPermission(listener: PermissionListener, vararg permissions: String)
    {
        mPermissionListener = listener
        val granted = !JPermission.lackPermission(permissions)

        if (granted)
        {
            listener.onResult(granted)
        } else
        {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION)
        }
    }

    private var mPermissionListener: PermissionListener? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if (requestCode == REQUEST_CODE_PERMISSION && mPermissionListener != null)
        {
            for (i in permissions.indices)
            {
                // 部分权限未授权
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    mPermissionListener!!.onResult(false)
                    return
                }
            }
            // 都已授权
            mPermissionListener!!.onResult(true)
        }
    }
    // ------------------------------------------------------------------------------------


    // 点击--------------------------------------------------------------------------------
    /**
     * 默认实现View.OnClickListener接口，根据需要重写
     */
    override fun onClick(v: View?)
    {

    }

    /**
     * 设置OnClick回调
     */
    fun setOnClick(vararg view: View)
    {
        view.forEach {
            it.setOnClickListener(this)
        }
    }
    // ----------------------------------------------------------------------------------------

}