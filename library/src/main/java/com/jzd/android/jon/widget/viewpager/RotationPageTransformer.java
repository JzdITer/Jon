package com.jzd.android.jon.widget.viewpager;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * 每一页旋转方法进场</br>
 * 当 -1 < position <= 0 的时候是前一页 但0<= position < 1的时候是后一页
 * 
 * @author Jzd
 *
 */
public class RotationPageTransformer implements PageTransformer
{
	private static final float MIN_SCALE = 0.5f;

	@Override
	public void transformPage(View page, float position)
	{
		if (position < -1)
		{
			page.setAlpha(0);
		} else if (position <= 0)
		{
			// position 0 ~ -1
			// 第一页缩放比例 1 ~ min_sacle
			
			float scaleX = 1 - MIN_SCALE * Math.abs(position);
			page.setScaleX(scaleX);
			page.setScaleY(scaleX);
			page.setRotation(360f * (1 - Math.abs(position)));
		} else if (position <= 1)
		{
			// 第二页缩放比例 min_sacle ~ 1
			float scaleX = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
			page.setScaleX(scaleX);
			page.setScaleY(scaleX);
			page.setRotation(360f * (1 - position));
		} else
		{
			page.setAlpha(0);
		}
	}

}
