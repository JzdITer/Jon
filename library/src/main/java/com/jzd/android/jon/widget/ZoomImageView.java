package com.jzd.android.jon.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 可缩放的图片控件
 * Created by jzd on 2017/1/24.
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener
{
    private boolean mOnce = false;
    private float mMinScale; // 最小缩放尺寸，也是默认的缩放尺寸
    private float mMaxScale; // 最大缩放尺寸
    private float mDoubelScale; // 双击放大的缩放尺寸
    private Matrix mMatrix = new Matrix();
    private ScaleGestureDetector mScaleGestureDetector; // 缩放手势监听

    private int mLastPointCount = 0;// 上次手势指数
    private float mLastX, mLastY;  // 记录上次的中心点位置
    private int mScaledTouchSlop; // 可以触发移动操作的边界

    private GestureDetector mGestureDetector;
    private boolean mScaling = false;// 正在双击放大，防止冲突
    // 滑动方向
    private Orientation mOrientation = Orientation.NO;
    // 未完成-------在ViewPager中使用 边界问题未处理
    private boolean mOnceSlide = false;// 一次完整滑动的标志
    private boolean mLaseIntercept;

    private enum Orientation
    {
        NO,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public ZoomImageView(Context context)
    {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.setScaleType(ScaleType.MATRIX);
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        // 双击手势
        this.mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener()
        {
            @Override
            public boolean onDoubleTap(MotionEvent e)
            {

                float x = e.getX();
                float y = e.getY();
                float scale = 1.0f;
                // 未达到最大缩放值的时候双击为放大
                if (getScale() < mDoubelScale)
                {
                    scale = mDoubelScale;
                }
                else if (getScale() >= mDoubelScale && getScale() < mMaxScale)
                {
                    scale = mMaxScale;
                }
                else if (getScale() >= mMaxScale)
                {
                    scale = mMinScale;
                }
//                mMatrix.postScale(scale, scale, x, y);
//                checkBoarderAndCenter();
//                setImageMatrix(mMatrix);

                if (!mScaling)
                {
                    new AutoScaleRunnable(x, y, scale).run();
                }
                return true;
            }
        });
        this.setOnTouchListener(this);

        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 自动缩放
     */
    private class AutoScaleRunnable implements Runnable
    {
        //缩放中心点
        private float x;
        private float y;
        //缩放目标值
        private float mTargetScale = 1;
        //单次缩放大小
        private float mScale = 1;
        private static final float BIGGER = 1.03f;
        private static final float SMALLER = 0.97f;

        public AutoScaleRunnable(float x, float y, float targetScale)
        {
            this.x = x;
            this.y = y;
            this.mTargetScale = targetScale;
            // 放大
            if (getScale() < mTargetScale)
            {
                this.mScale = BIGGER;
            }
            // 缩小
            else if (getScale() > mTargetScale)
            {
                this.mScale = SMALLER;
            }
        }

        @Override
        public void run()
        {
            mScaling = true;
            mMatrix.postScale(mScale, mScale, x, y);
            checkBoarderAndCenter();
            setImageMatrix(mMatrix);

            float scale = getScale();
            // 未达到需要缩放的目标
            if ((mScale > 1 && scale < mTargetScale) || (mScale < 1 && scale > mTargetScale))
            {
                postDelayed(this, 10);
            }
            // 已经达到或者超过需要缩放的目标，消费掉超过的部分
            else if (scale != mTargetScale)
            {
                mMatrix.postScale(mTargetScale / scale, mTargetScale / scale, x, y);
                checkBoarderAndCenter();
                setImageMatrix(mMatrix);
                mScaling = false;
            }
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= 16)
        {
            this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        else
        {
            this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    /**
     * 控件加载图片的时候，计算缩放大小，并且显示出来
     */
    @Override
    public void onGlobalLayout()
    {
        if (!mOnce)
        {
            int width = getWidth();
            int height = getHeight();
            Drawable drawable = getDrawable();
            float scale = 1.0f;

            if (drawable != null)
            {
                int imgWidth = drawable.getIntrinsicWidth();
                int imgHeight = drawable.getIntrinsicHeight();
                // 图片大小比控件大
                if (imgWidth >= width && imgHeight >= height)
                {
                    // img缩放大小 = imgWidth * scale
                    // scale = width / imgWidth
                    scale = Math.min(width * 1.0f / imgWidth, height * 1.0f / imgHeight);
                }
                // 图片大小比控件小
                if (imgWidth <= width && imgHeight <= height)
                {
                    // img缩放大小 = imgWidth * scale
                    // scale = width / imgWidth
                    scale = Math.min(width * 1.0f / imgWidth, height * 1.0f / imgHeight);
                }
                if (imgWidth >= width && imgHeight <= height)
                {
                    scale = width * 1.0f / imgWidth;
                }
                if (imgHeight >= height && imgWidth <= width)
                {
                    scale = height * 1.0f / imgHeight;
                }

                mMinScale = scale;
                mMaxScale = scale * 4;
                mDoubelScale = scale * 2;

                mMatrix.postTranslate(width * 1.0f / 2 - imgWidth * 1.0f / 2, height * 1.0f / 2 - imgHeight * 1.0f / 2);
                mMatrix.postScale(mMinScale, mMinScale, width * 1.0f / 2, height * 1.0f / 2);
                setImageMatrix(mMatrix);
            }
            mOnce = true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (mGestureDetector.onTouchEvent(event))
        {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);
        if (getDrawable() != null)
        {
            int pointerCount = event.getPointerCount();
            float x = 0, y = 0;
            for (int i = 0; i < pointerCount; i++)
            {
                x += event.getX(i);
                y += event.getY(i);
            }
            x /= pointerCount;
            y /= pointerCount;
            // 防止在多指操作切换的时候，图片出现跳跃
            if (mLastPointCount != pointerCount)
            {
                mLastX = x;
                mLastY = y;
            }
            mLastPointCount = pointerCount;

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mOrientation = Orientation.NO;
                    mOnceSlide = false;
                    if (getParent() != null)
                    {
                        getParent().requestDisallowInterceptTouchEvent(disallowParentInterceptTouchEvent());
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = x - mLastX;
                    float dy = y - mLastY;

                    if (isMove(dx, dy))
                    {
                        checkOrientation(dx, dy);
                        if (getParent() != null)
                        {
                            getParent().requestDisallowInterceptTouchEvent(disallowParentInterceptTouchEvent());
                        }
                        RectF matrixRectF = getMatrixRectF();
                        // 比控件小的时候不能移动
                        if (matrixRectF.width() <= this.getWidth())
                        {
                            dx = 0;
                        }
                        if (matrixRectF.height() <= this.getHeight())
                        {
                            dy = 0;
                        }
                        mMatrix.postTranslate(dx, dy);
                        checkBoarderAndCenter();
                        this.setImageMatrix(mMatrix);
                    }

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mLastPointCount = 0;
                    mOnceSlide = true;
                    break;
            }
            mLastX = x;
            mLastY = y;
        }

        return true;
    }

    /**
     * 判断移动的方向
     *
     * @param x 在x轴上移动的矢量,带方向
     * @param y 在y轴上移动的矢量,带方向
     */
    private void checkOrientation(float x, float y)
    {
        if (mOnceSlide)
        {
            return;
        }
        // 在x轴上移动的距离长
        if (Math.abs(x) >= Math.abs(y))
        {
            if (x > 0)
            {
                mOrientation = Orientation.RIGHT;
            }
            else

            {
                mOrientation = Orientation.LEFT;
            }
        }
        else
        {
            if (y > 0)
            {
                mOrientation = Orientation.DOWN;
            }
            else
            {
                mOrientation = Orientation.UP;
            }
        }
    }

    /**
     * 是否不允许父控件拦截触摸事件
     *
     * @return
     */
    private boolean disallowParentInterceptTouchEvent()
    {
//        if(!mOnceSlide)
//        {
//            return mLaseIntercept;
//        }
        RectF matrixRectF = getMatrixRectF();
//        boolean a = mOrientation == Orientation.LEFT && matrixRectF.right > getWidth();
//        boolean b = mOrientation == Orientation.RIGHT && matrixRectF.left < 0.1;
//        boolean c = mOrientation == Orientation.UP && matrixRectF.bottom > getHeight();
//        boolean d = mOrientation == Orientation.DOWN && matrixRectF.top < 0.1;
//        mLaseIntercept = a || b || c || d;
//        return mLaseIntercept;
        return matrixRectF.width() > getWidth() || matrixRectF.height() > getHeight();
    }

    /**
     * 是否足以出发Move事件
     *
     * @param tx x轴上的偏移
     * @param ty y轴上的偏移
     * @return
     */
    private boolean isMove(float tx, float ty)
    {
        return Math.sqrt(tx * tx + ty * ty) > mScaledTouchSlop;
    }

    /**
     * 得到图片的缩放比例
     *
     * @return Matrix.MSCALE_X
     */
    private float getScale()
    {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {
        // 手势缩放大小
        float scaleFactor = detector.getScaleFactor();
        mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        checkBoarderAndCenter();
        setImageMatrix(mMatrix);
        return true;
    }

    /**
     * 检测边界和中心点
     */
    private void checkBoarderAndCenter()
    {
        float translateX = 0f;
        float translateY = 0f;
        int width = getWidth();
        int height = getHeight();
        RectF rect = getMatrixRectF();
        // 边界的偏移
        // 图片比控件大的时候边界才会比控件大，否则有空白是正常的
        if (rect.width() > width)
        {
            if (rect.left > 0)
            {
                translateX = -rect.left;
            }
            if (rect.right < width)
            {
                translateX = width - rect.right;
            }
        }
        if (rect.height() > height)
        {
            if (rect.top > 0)
            {
                translateY = -rect.top;
            }
            if (rect.bottom < height)
            {
                translateY = height - rect.bottom;
            }
        }
        // 中心点的设置
        // 如果图片的宽或者高小于控件的宽和高，需要居中显示
        if (rect.width() <= width)
        {
            translateX = width * 1.0f / 2 - rect.left - rect.width() * 1.0f / 2;
        }
        if (rect.height() <= height)
        {
            translateY = height * 1.0f / 2 - rect.top - rect.height() * 1.0f / 2;
        }
        mMatrix.postTranslate(translateX, translateY);
    }

    /**
     * 得到图片的位置
     *
     * @return 图片位置的矩阵
     */
    private RectF getMatrixRectF()
    {
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null)
        {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mMatrix.mapRect(rectF);
        }
        return rectF;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        return getDrawable() != null;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector)
    {
        // 当前scale = 原来scale * scaleFactor
        // 如果当前scale比mMaxScale大，缩放比例为 mMaxScale/scale
        float scale = getScale();
        float postScale = 1;
        if (scale > mMaxScale || scale < mMinScale)
        {
            if (scale > mMaxScale)
            {
                postScale = mMaxScale / scale;
            }
            else if (scale < mMinScale)
            {
                postScale = mMinScale / scale;
            }
            mMatrix.postScale(postScale, postScale, getWidth() / 2, getHeight() / 2);
            checkBoarderAndCenter();
            setImageMatrix(mMatrix);
        }
    }
}
