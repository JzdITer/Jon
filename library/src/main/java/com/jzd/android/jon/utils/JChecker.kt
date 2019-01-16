package com.jzd.android.jon.utils

import android.view.View
import android.widget.TextView
import com.jzd.android.jon.widget.JFormItemView

/**
 * 检查
 * @author Jzd
 * @since 1.0
 */
object JChecker
{
    /**
     * 非空检查
     */
    fun checkEmpty(vararg views: View): Boolean
    {
        views.forEach {
            if(it is TextView)
            {
                if(it.text.toString().isEmpty())
                {
                    JToast.show(it.hint)
                    return false
                }
            } else if(it is JFormItemView)
            {
                if(it.getContent().isEmpty())
                {
                    JToast.show(it.getContentView().hint)
                    return false
                }
            }
        }
        return true
    }

    /**
     * 非空检查
     */
    fun checkEmpty(showHint: Boolean = false, vararg views: View): Boolean
    {
        views.forEach {
            if(it is TextView)
            {
                if(it.text.toString().isEmpty())
                {
                    if(showHint)
                    {
                        JToast.show(it.hint)
                    }
                    return false
                }
            } else if(it is JFormItemView)
            {
                if(it.getContent().isEmpty())
                {
                    if(showHint)
                    {
                        JToast.show(it.getContentView().hint)
                    }
                    return false
                }
            }
        }
        return true
    }

    /**
     * tag检查
     */
    fun checkTag(vararg views: View): Boolean
    {
        views.forEach {
            if(it.tag == null)
            {
                if(it is TextView)
                {
                    JToast.show(it.hint)
                } else if(it is JFormItemView)
                {
                    JToast.show(it.getContentView().hint)
                }
                return false
            }
        }
        return true
    }

    /**
     * tag检查
     */
    fun checkTag(showHint: Boolean = false, vararg views: View): Boolean
    {
        views.forEach {
            if(it.tag == null)
            {
                if(showHint)
                {
                    if(it is TextView)
                    {
                        JToast.show(it.hint)
                    } else if(it is JFormItemView)
                    {
                        JToast.show(it.getContentView().hint)
                    }
                }
                return false
            }
        }
        return true
    }

    /**
     * data检查
     * JFormItemView检查data,TextView检查text
     */
    fun checkData(vararg views: View): Boolean
    {
        views.forEach {
            val map = it.getData()
            if(map == null)
            {
                if(it is TextView)
                {
                    JToast.show(it.hint)
                } else if(it is JFormItemView)
                {
                    JToast.show(it.getContentView().hint)
                }
                return false
            }
        }
        return true
    }

    /**
     * data检查
     * JFormItemView检查data,TextView检查text
     */
    fun checkData(showHint: Boolean = false, vararg views: View): Boolean
    {
        views.forEach {
            val map = it.getData()
            if(map == null)
            {
                if(showHint)
                {
                    if(it is TextView)
                    {
                        JToast.show(it.hint)
                    } else if(it is JFormItemView)
                    {
                        JToast.show(it.getContentView().hint)
                    }
                }
                return false
            }
        }
        return true
    }


    /**
     * 是否包含中文字符
     */
    fun isContainChinese(char: Char?): Boolean
    {
        if(char == null)
        {
            return false
        }
        val ub = Character.UnicodeBlock.of(char)
        return (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
    }

    /**
     * 是否是手机号
     */
    fun isPhone(phone: String?): Boolean
    {
        return !phone.isNullOrEmpty() && phone!!.matches("^1[34578][0-9]\\d{8}$".toRegex())
    }
}