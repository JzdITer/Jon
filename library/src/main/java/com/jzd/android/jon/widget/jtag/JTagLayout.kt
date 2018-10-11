package com.jzd.android.jon.widget.jtag

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent

class JTagLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : FlexboxLayout(context, attrs, defStyleAttr)
{
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)

    init
    {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP
        justifyContent = JustifyContent.CENTER


        var i = 0
        while(i < childCount)
        {
            val child = getReorderedChildAt(i)
            child.isClickable = true
            if(i == 0)
            {
                if(Build.VERSION.SDK_INT >= 16)
                {
                    //child.background = ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_left)
                } else
                {
                    //child.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_left))
                }
            } else if(i == childCount - 1)
            {
                if(Build.VERSION.SDK_INT >= 16)
                {
                    //child.background = ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_right)
                } else
                {
                    //child.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_right))
                }
            } else
            {
                if(Build.VERSION.SDK_INT >= 16)
                {
                    //child.background = ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_center)
                } else
                {
                    //child.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.drawable_common_radio_button__circle_center))
                }
            }
            i++
        }


    }
}