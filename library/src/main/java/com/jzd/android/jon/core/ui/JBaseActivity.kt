package com.jzd.android.jon.core.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.jzd.android.jon.core.impl.IContextDelegate
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.REQUEST_CODE_PERMISSION
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_BOOLEAN
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_BUNDLE
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_DOUBLE
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_FLOAT
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_INT
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_LONG
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_PARCELABLE
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_PARCELABLE_LIST
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_SERIALIZABLE
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_STRING
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_CODE_STRING_LIST
import com.jzd.android.jon.core.impl.IContextDelegate.Companion.START_FOR_RESULT_CODE
import com.jzd.android.jon.core.impl.OnDoubleBackPressListener
import com.jzd.android.jon.core.module.permission.JPermission
import com.jzd.android.jon.core.module.permission.PermissionListener
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper
import qiu.niorgai.StatusBarCompat
import java.io.Serializable

/**
 * Activity父类 封装系统Api
 * 侧滑返回需要重写findViewById,这里没有重写，可能会有问题
 * @author Jzd
 * @since 1.0
 */
open class JBaseActivity : RxAppCompatActivity(), View.OnClickListener, IContextDelegate, SwipeBackActivityBase
{
    protected lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mContext = this
        if(isSplash())
        {
            if(intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0)
            {
                finish()
            } else
            {
                //取消标题栏
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                //取消状态栏
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                        .FLAG_FULLSCREEN)
            }
        }
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
    }

    // ------------------------------------- 侧滑返回  --------------------------------------------------------------
    private lateinit var mHelper: SwipeBackActivityHelper
    override fun getSwipeBackLayout(): SwipeBackLayout
    {
        return mHelper.swipeBackLayout
    }

    override fun scrollToFinishActivity()
    {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }

    override fun setSwipeBackEnable(enable: Boolean)
    {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun onPostCreate(savedInstanceState: Bundle?)
    {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }
    // ------------------------------------- 侧滑返回  --------------------------------------------------------------
    /**
     *  防止重复启动
     */
    protected open fun isSplash(): Boolean
    {
        return false
    }

    override fun setContentView(layoutResID: Int)
    {
        super.setContentView(layoutResID)
        initActivity()
    }

    override fun setContentView(view: View?)
    {
        super.setContentView(view)
        initActivity()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?)
    {
        super.setContentView(view, params)
        initActivity()
    }

    /**
     * 初始化Activity
     */
    private fun initActivity()
    {
        // 沉浸式
        if(isIm())
        {
            StatusBarCompat.translucentStatusBar(this, true)
        }
    }

    /**
     * 是否是沉浸式
     */
    open fun isIm(): Boolean
    {
        return true
    }

    // 各种启动Intent
    fun start(activity: Class<out Activity>)
    {
        val intent = Intent(mContext, activity)
        start(intent)
    }

    interface StartResultListener
    {
        fun onResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onResultOk(requestCode: Int, data: Intent?)
        fun onResultCancel(requestCode: Int, data: Intent?)
    }

    interface SimpleStartResultListener : StartResultListener
    {
        override fun onResult(requestCode: Int, resultCode: Int, data: Intent?)
        {
        }

        override fun onResultOk(requestCode: Int, data: Intent?)
        {
        }

        override fun onResultCancel(requestCode: Int, data: Intent?)
        {
        }
    }

    private var mStartResultListener: StartResultListener? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(mStartResultListener != null)
        {

            when(resultCode)
            {
                Activity.RESULT_OK -> mStartResultListener!!.onResultOk(requestCode, data)
                Activity.RESULT_CANCELED -> mStartResultListener!!.onResultCancel(requestCode, data)
                else -> mStartResultListener!!.onResult(requestCode, resultCode, data)
            }
        }
    }

    fun startResult(activity: Class<out Activity>, startResultListener: StartResultListener)
    {
        val intent = Intent(mContext, activity)
        mStartResultListener = startResultListener
        startActivityForResult(intent, START_FOR_RESULT_CODE)
    }

    fun startResult(intent: Intent, startResultListener: StartResultListener)
    {
        mStartResultListener = startResultListener
        startActivityForResult(intent, START_FOR_RESULT_CODE)
    }

    fun startTop(intent: Intent)
    {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        start(intent)
    }

    fun startTop(activity: Class<out Activity>)
    {
        val intent = Intent(mContext, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        start(intent)
    }

    fun startWithString(activity: Class<out Activity>, str: String?)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_STRING, str)
        start(intent)
    }

    fun getStartString(): String?
    {
        return intent.getStringExtra(START_CODE_STRING)
    }

    fun startWithStringList(activity: Class<out Activity>, list: ArrayList<String>?)
    {
        val intent = Intent(mContext, activity)
        intent.putStringArrayListExtra(START_CODE_STRING_LIST, list)
        start(intent)
    }

    fun getStartStringList(): ArrayList<String>?
    {
        return intent.getStringArrayListExtra(START_CODE_STRING_LIST)
    }

    fun startWithBundle(activity: Class<out Activity>, bundle: Bundle)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_BUNDLE, bundle)
        start(intent)
    }

    fun getStartBundle(): Bundle?
    {
        return intent.getBundleExtra(START_CODE_BUNDLE)
    }

    fun startWithSerializable(activity: Class<out Activity>, s: Serializable)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_INT, s)
        start(intent)
    }

    fun getStartSerializable(): Serializable?
    {
        return intent.getSerializableExtra(START_CODE_SERIALIZABLE)
    }

    fun startWithInt(activity: Class<out Activity>, i: Int)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_INT, i)
        start(intent)
    }

    fun getStartInt(): Int
    {
        return intent.getIntExtra(START_CODE_INT, 0)
    }

    fun startWithFloat(activity: Class<out Activity>, f: Float)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_FLOAT, f)
        start(intent)
    }

    fun getStartFloat(): Float
    {
        return intent.getFloatExtra(START_CODE_FLOAT, 0f)
    }

    fun startWithDouble(activity: Class<out Activity>, d: Double)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_DOUBLE, d)
        start(intent)
    }

    fun getStartDouble(): Double
    {
        return intent.getDoubleExtra(START_CODE_DOUBLE, 0.0)
    }

    fun startWithBoolean(activity: Class<out Activity>, b: Boolean)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_BOOLEAN, b)
        start(intent)
    }

    fun getStartBoolean(def:Boolean): Boolean
    {
        return intent.getBooleanExtra(START_CODE_BOOLEAN, def)
    }

    fun startWithLong(activity: Class<out Activity>, l: Long)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_LONG, l)
        start(intent)
    }

    fun getStartLong(): Long
    {
        return intent.getLongExtra(START_CODE_LONG, 0)
    }

    fun startWithParcelable(activity: Class<out Activity>, p: Parcelable)
    {
        val intent = Intent(mContext, activity)
        intent.putExtra(START_CODE_PARCELABLE, p)
        start(intent)
    }

    fun getStartParcelable(): Parcelable?
    {
        return intent.getParcelableExtra(START_CODE_PARCELABLE)
    }

    fun startWithParcelableList(activity: Class<out Activity>, list: ArrayList<out Parcelable>)
    {
        val intent = Intent(mContext, activity)
        intent.putParcelableArrayListExtra(START_CODE_PARCELABLE_LIST, list)
        start(intent)
    }

    fun getStartParcelableList(): ArrayList<out Parcelable>?
    {
        return intent.getParcelableArrayListExtra<Parcelable>(START_CODE_PARCELABLE_LIST)
    }

    fun start(intent: Intent)
    {
        startActivity(intent)
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
        if(mIsDoubleBack)
        {
            if(mOnDoubleBackPressListener != null)
            {
                val b = mOnDoubleBackPressListener!!.onPress()
                if(!b)
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
        if(System.currentTimeMillis() - mBackPressTimer >= 2000)
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

        if(granted)
        {
            listener.onResult(granted)
        } else
        {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION)
        }
    }

    private lateinit var mPermissionListener: PermissionListener

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if(requestCode == REQUEST_CODE_PERMISSION)
        {
            for(i in permissions.indices)
            {
                // 部分权限未授权
                if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    mPermissionListener.onResult(false)
                    return
                }
            }
            // 都已授权
            mPermissionListener.onResult(true)
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