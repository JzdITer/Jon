package com.jzd.android.jon.core.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jzd.android.jon.core.impl.IContextDelegate
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.REQUEST_CODE_PERMISSION
import com.jzd.android.jon.core.module.permission.JPermission
import com.jzd.android.jon.core.module.permission.PermissionListener

/**
 * Fragment父类 封装系统级Api
 *
 * val fragment = JBaseFragment.newInstance("1"),目前不这样写没有警告了???
 * @author Jzd
 * @since 1.0
 */
open class JBaseFragment : Fragment(), View.OnClickListener, IContextDelegate
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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
            requestPermissions(permissions, REQUEST_CODE_PERMISSION)
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
    // ----------------------------------------------------------------------------------------


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