package com.jzd.android.jon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 特性： 1.可展开
 * 
 * @author Jzd
 *
 */
public class JListView extends ListView
{
	/**
	 * 可“展开”
	 */
	private boolean mExpandable = false;

	public JListView(Context context)
	{
		super(context);
	}

	public JListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public JListView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public boolean isExpandable()
	{
		return mExpandable;
	}

	/**
	 * 设置在嵌套ScrollView的时候能不能“展开”
	 */
	public void setExpandable(boolean expandable)
	{
		this.mExpandable = expandable;
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// 嵌套ScrollView的时候 需要“展开”
		if (mExpandable)
		{
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
