package com.jzd.android.jon.widget.popupwindow;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jzd.android.jon.R;
import com.jzd.android.jon.core.ui.JBaseActivity;
import com.jzd.android.jon.utils.JBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的PopupWindow:
 * 宽度为屏幕宽度*0.5，弹出时变为模态,外部点击可关闭
 * show为弹出样式，showAsDropDown为下拉
 */
// TODO 取消软键盘
@SuppressWarnings("unused") public class JBasePopupWindow extends PopupWindow
{

    private JBaseActivity mActivity;

    private List<PopupWindow.OnDismissListener> mOnDismissListeners = new ArrayList<>();


    public JBasePopupWindow(@NonNull JBaseActivity activity, View view)
    {
        super(activity);

        mActivity = activity;

        setView(view);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setClippingEnabled(false);
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //setAnimationStyle(R.style.style_anim_slide_from_top);

        mOnDismissListeners.add(() -> lightAlpha(1));
        setOnDismissListener(() ->
        {
            for(PopupWindow.OnDismissListener onDismissListener : mOnDismissListeners)
            {
                onDismissListener.onDismiss();
            }
        });
    }

    /**
     * 设置自定义界面，注意区分setContentView
     * setContentView会直接重置界面
     * setView是将view加入到跟布局中
     */
    @SuppressLint("InflateParams")
    public JBasePopupWindow setView(View view)
    {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(mActivity)
                .inflate(R.layout.layout_j_base_popup_window, null, false);
        if(view != null)
        {
            rootView.removeAllViews();
            rootView.addView(view);
        }

        setContentView(rootView);
        return this;
    }

    /**
     * 设置沉浸式(当高度全屏时使用)
     * <p>
     * 1.使用setContentView()方法设置界面时，需要用户自己实现沉浸式
     * 2.使用JBasePopupWindow构造方法设置界面时，该方法可以实现沉浸式
     * 3.使用setView(View)时，在setView之后调用该方法才会生效
     */
    public JBasePopupWindow setIm(boolean isIm)
    {
        if(isIm)
        {
            if(getContentView() != null)
            {
                LinearLayout parent = getContentView().findViewById(R.id.mLayoutRoot);
                if(parent != null && parent.getChildCount() > 0)
                {
                    View view = parent.getChildAt(0);

                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.topMargin = JBarUtil.INSTANCE.getStatusBarHeight(mActivity);
                    view.setLayoutParams(layoutParams);
                }
            }
        }
        return this;
    }

    /**
     * 添加dismiss监听
     */
    public JBasePopupWindow addOnDismissListener(PopupWindow.OnDismissListener listener)
    {
        if(listener != null)
        {
            mOnDismissListeners.add(listener);
        }
        return this;
    }


    /**
     * 弹出
     */
    public void show()
    {
        //        View currentFocus = mActivity.getCurrentFocus();
        //        if(currentFocus != null) {
        //            KeyBoardUtil.closeKeybord(mActivity, currentFocus);
        //        }
        lightAlpha(0.79f);
        showAtLocation(mActivity.getWindow()
                .getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 弹出
     */
    public void show(int gravity, int x, int y)
    {
        lightAlpha(0.79f);
        showAtLocation(mActivity.getWindow()
                .getDecorView(), gravity, x, y);
    }

    /**
     * 弹出
     */
    public void show(View view, int gravity, int x, int y)
    {
        //        View currentFocus = mActivity.getCurrentFocus();
        //        if(currentFocus != null) {
        //            KeyBoardUtil.closeKeybord(mActivity, currentFocus);
        //        }
        lightAlpha(0.79f);
        showAtLocation(view, gravity, x, y);
    }


    /**
     * 改变屏幕亮度
     */
    private void lightAlpha(float toAlpha)
    {
        //        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        //        lp.alpha = alpha;
        //        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //        mActivity.getWindow().setAttributes(lp);
        final Window window = mActivity.getWindow();
        float formAlpha = window.getAttributes().alpha;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(formAlpha, toAlpha);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation ->
        {
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = (Float) animation.getAnimatedValue();
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(params);
        });
        valueAnimator.start();
    }

    /**
     * 进入动画
     * 1：top
     * 2：bottom
     * 3: left
     * 4: right
     * <p>
     * 自定义动画使用setAnimationStyle
     */
    public JBasePopupWindow setAnim(int type)
    {
        switch(type)
        {
            case 1:
                setAnimationStyle(R.style.style_anim_slide_from_top);
                break;
            case 2:
                setAnimationStyle(R.style.style_anim_slide_from_bottom);
                break;
            case 3:
                setAnimationStyle(R.style.style_anim_slide_from_left);
                break;
            case 4:
                setAnimationStyle(R.style.style_anim_slide_from_right);
                break;
            default:
                break;
        }
        return this;
    }
}
