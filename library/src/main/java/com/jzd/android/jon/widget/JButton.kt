package com.jzd.android.jon.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.Button
import com.jzd.android.jon.R

@Suppress("DEPRECATION")
/**
 * 自定义按钮
 * 1.实现动态圆角+背景颜色
 * @author Jzd
 */
class JButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : Button(context, attrs, defStyleAttr)
{
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, Resources.getSystem().getIdentifier("buttonStyle", "attr", "android"))
    constructor(context: Context) : this(context, null)

    init
    {

        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.JButton)

        val backGround = attrsArray.getColor(R.styleable.JButton_j_button_background_color, Color.GRAY)
        val cornerRadius = attrsArray.getDimensionPixelSize(R.styleable.JButton_j_button_corner_radius, 0)
        val drawable = GradientDrawable()
        drawable.cornerRadius = cornerRadius.toFloat()
        drawable.setColor(backGround)
        if(Build.VERSION.SDK_INT >= 16)
        {
            background = drawable
        } else
        {
            setBackgroundDrawable(drawable)
        }
        attrsArray.recycle()
    }
}