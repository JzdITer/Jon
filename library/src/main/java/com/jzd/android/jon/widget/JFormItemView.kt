package com.jzd.android.jon.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.jzd.android.jon.R
import com.jzd.android.jon.core.module.jmap.JMapImpl
import com.jzd.android.jon.utils.JMetrics
import com.jzd.android.jon.widget.util.JWidgetUtil

/**
 * 表单输入控件，包含左侧TextView,内容TextView,右侧TextView和分割线
 *
 * @author jzd
 * @since  v1.0
 */
@SuppressWarnings("unused")
class JFormItemView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr)
{

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    lateinit var mLayoutRoot: LinearLayout
    lateinit var mLayoutContent: LinearLayout
    lateinit var mTvItemLeft: TextView
    lateinit var mTvItemContext: EditText
    lateinit var mTvItemRight: TextView
    lateinit var mDivider: View

    private var mJMapImpl: JMapImpl? = null

    init
    {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_j_form_item_view, this, true)
        if(attrs != null)
        {
            var defPadding = 0
            var defIconPadding = 0
            if(!isInEditMode)
            {
                defPadding = JMetrics.dp2px(8F)
                defIconPadding = JMetrics.dp2px(3F)
            }


            val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.JFormItemView)
            // 获取系统默认字体
            val defTextSize = JWidgetUtil(context).defTextSize()
            val defTextColor = JWidgetUtil(context).defTextColor()
            val defHintTextColor = JWidgetUtil(context).defTextColorHint()

            // 左侧TextView
            mTvItemLeft = view.findViewById(R.id.mTvItemLeft)
            val leftIcon = attributeSet.getResourceId(R.styleable.JFormItemView_left_icon, 0)
            mTvItemLeft.setCompoundDrawablesWithIntrinsicBounds(leftIcon, 0, 0, 0)
            val leftTextSize = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_text_size, defTextSize.toInt())
            mTvItemLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize.toFloat())
            val leftTextColor = attributeSet.getColor(R.styleable.JFormItemView_left_text_color, defTextColor)
            mTvItemLeft.setTextColor(leftTextColor)
            val leftIconPadding = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_icon_padding, defIconPadding)
            mTvItemLeft.compoundDrawablePadding = leftIconPadding
            val leftPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_padding_start, 0)
            val leftPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_padding_top, 0)
            val leftPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_padding_end, 0)
            val leftPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_left_padding_bottom, 0)
            mTvItemLeft.setPadding(leftPaddingStart, leftPaddingTop, leftPaddingEnd, leftPaddingBottom)
            val leftText = attributeSet.getString(R.styleable.JFormItemView_left_text)
            mTvItemLeft.text = leftText
            val leftVisibility = attributeSet.getInt(R.styleable.JFormItemView_left_visibility, 1)
            when(leftVisibility)
            {
                1 -> mTvItemLeft.visibility = View.VISIBLE
                2 -> mTvItemLeft.visibility = View.GONE
                3 -> mTvItemLeft.visibility = View.INVISIBLE
            }

            // 中间TextView
            mTvItemContext = view.findViewById(R.id.mTvItemContext)
            val editable = attributeSet.getBoolean(R.styleable.JFormItemView_editable, false)
            mTvItemContext.isFocusable = editable
            mTvItemContext.isFocusableInTouchMode = editable
            val contentTextSize = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_content_text_size, defTextSize.toInt())
            mTvItemContext.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize.toFloat())
            val contentTextColor = attributeSet.getColor(R.styleable.JFormItemView_content_text_color, defTextColor)
            mTvItemContext.setTextColor(contentTextColor)
            val contentPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_content_padding_start, defPadding)
            val contentPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_content_padding_top, 0)
            val contentPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_content_padding_end, defPadding)
            val contentPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_content_padding_bottom, 0)
            mTvItemContext.setPadding(contentPaddingStart, contentPaddingTop, contentPaddingEnd, contentPaddingBottom)
            val contentText = attributeSet.getString(R.styleable.JFormItemView_content_text)
            mTvItemContext.setText(contentText)
            val contentHint = attributeSet.getString(R.styleable.JFormItemView_content_hint)
            mTvItemContext.hint = contentHint
            val contentTextColorHint = attributeSet.getColor(R.styleable.JFormItemView_content_text_color_hint, defHintTextColor)
            mTvItemContext.setHintTextColor(contentTextColorHint)

            // 右侧TextView
            mTvItemRight = view.findViewById(R.id.mTvItemRight)
            val rightIcon = attributeSet.getResourceId(R.styleable.JFormItemView_right_icon, 0)
            mTvItemRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightIcon, 0)
            val rightPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_padding_start, 0)
            val rightPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_padding_top, 0)
            val rightPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_padding_end, 0)
            val rightPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_padding_bottom, 0)
            mTvItemRight.setPadding(rightPaddingStart, rightPaddingTop, rightPaddingEnd, rightPaddingBottom)
            val rightVisibility = attributeSet.getInt(R.styleable.JFormItemView_right_visibility, 1)
            when(rightVisibility)
            {
                1 -> mTvItemRight.visibility = View.VISIBLE
                2 -> mTvItemRight.visibility = View.GONE
                3 -> mTvItemRight.visibility = View.INVISIBLE
            }
            val rightTextSize = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_text_size, defTextSize.toInt())
            mTvItemRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize.toFloat())
            val rightTextColor = attributeSet.getColor(R.styleable.JFormItemView_right_text_color, defTextColor)
            mTvItemRight.setTextColor(rightTextColor)
            val rightIconPadding = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_right_icon_padding, defIconPadding)
            mTvItemRight.compoundDrawablePadding = rightIconPadding
            val rightText = attributeSet.getString(R.styleable.JFormItemView_right_text)
            mTvItemRight.text = rightText

            // 分割线
            mDivider = view.findViewById(R.id.mDivider)
            val dividerColor = attributeSet.getColor(R.styleable.JFormItemView_divider_color, Color.BLACK)
            mDivider.setBackgroundColor(dividerColor)
            val dividerPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_divider_padding_start, 0)
            val dividerPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_divider_padding_top, defIconPadding)
            val dividerPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_divider_padding_end, 0)
            val dividerPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_divider_padding_bottom, 0)
            val layoutParams = mDivider.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(dividerPaddingStart, dividerPaddingTop, dividerPaddingEnd, dividerPaddingBottom)
            val dividerHeight = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_divider_height, 1)
            mDivider.layoutParams.height = dividerHeight
            val dividerVisibility = attributeSet.getInt(R.styleable.JFormItemView_divider_visibility, 1)
            when(dividerVisibility)
            {
                1 -> mDivider.visibility = View.VISIBLE
                2 -> mDivider.visibility = View.GONE
                3 -> mDivider.visibility = View.INVISIBLE
            }


            // 整体
            mLayoutRoot = view.findViewById(R.id.mLayoutRoot)
            val itemPadding = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_item_padding, defPadding)
            var itemPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_item_padding_start, 0)
            var itemPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_item_padding_top, 0)
            var itemPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_item_padding_end, 0)
            var itemPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.JFormItemView_item_padding_bottom, 0)
            if(itemPadding != 0)
            {
                itemPaddingStart = itemPadding
                itemPaddingTop = itemPadding
                itemPaddingEnd = itemPadding
                itemPaddingBottom = itemPadding
            }
            mLayoutRoot.setPadding(itemPaddingStart, itemPaddingTop, itemPaddingEnd, itemPaddingBottom)

            // 回收资源
            attributeSet.recycle()
        }
    }

    /**
     * 为控件绑定数据
     */
    fun setData(map: JMapImpl?)
    {
        this.mJMapImpl = map
        mTvItemContext.setText(map?.value().toString())
    }

    /**
     * 获取该控件上的数据
     */
    fun getData(): JMapImpl?
    {
        return this.mJMapImpl
    }

}
