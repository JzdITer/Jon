package com.jzd.android.jon.widget.viewpager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 自定义的ViewPager</br>
 * 1.mScrollable:可滚动属性</br>
 * 2.可解决ViewPager嵌套问题</br>
 *
 * @author Jzd
 */
public class JViewPager extends ViewPager
{
    private Context mContext;
    private boolean mScrollable = true;
    private PointF mDownPoint = new PointF();

    public JViewPager(Context context)
    {
        this(context, null);
    }

    public JViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
    }

    public boolean ismScrollable()
    {
        return mScrollable;
    }

    public void setScrollable(boolean scrollable)
    {
        this.mScrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return mScrollable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (!mScrollable)
        {
            return false;
        }
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                // 记录按下时候的坐标
                mDownPoint.x = ev.getX();
                mDownPoint.y = ev.getY();
                if (this.getChildCount() > 1)
                { // 有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getChildCount() > 1)
                { // 有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为一个点
                if (PointF.length(ev.getX() - mDownPoint.x, ev.getY() - mDownPoint.y) < ViewConfiguration.get(mContext).getScaledTouchSlop())
                {
                    performClick();
                    return true;
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

}
