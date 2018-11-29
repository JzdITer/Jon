package com.jzd.android.jon.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 封装了bitmap常用的一些方法
 * 
 * @author Jzd
 *
 */
public class BitmapUtils
{
	/**
	 * 压缩图片，放入ImageView中，用于下载后直接显示
	 * 
	 * @param path
	 *            路径
	 * @return
	 */
	public static Bitmap decodeSampledBitmap(String path, ImageView imageView)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 只是获取图片的宽和高 不加载到内存中，不设置会OOM
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 压缩比例
		int width = 0;
		int height = 0;
		ImageSize imageSize = getImageSize(imageView);
		width = imageSize.width;
		height = imageSize.height;

		options.inSampleSize = caculateInSampleSize(options, width, height);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 压缩图片,用于对本地图片进行操作
	 * 
	 * @param resourcePath
	 * @param width
	 * @param height
	 * @param size
	 * @param saveDir
	 * @param saveName
	 * @return
	 */
	public static String decodeSampledBitmap(String resourcePath, int width, int height, int size, String saveDir,
			String saveName)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(resourcePath, options);
		options.inSampleSize = caculateInSampleSize(options, width, height);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		Bitmap tempBitmap = BitmapFactory.decodeFile(resourcePath, options);
		int quality = caculateQualityScale(tempBitmap, size);
		boolean save = saveImage(saveDir, saveName, tempBitmap, quality);
		return save ? saveDir + File.separator + saveName : null;
	}

	/**
	 * 保存文件
	 * @param dir 保存路径
	 * @param name 文件名称
	 * @param bitmap 图片
	 * @param quality 压缩质量
	 * @return
	 */
	public static boolean saveImage(String dir, String name, Bitmap bitmap, int quality)
	{
		File file = new File(dir);
		if (!file.exists() && !file.mkdirs())
		{
			return false;
		} else
		{
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(file.getPath() + File.separator + name);
				bitmap.compress(CompressFormat.JPEG, quality, fos);
				return true;
			} catch (IOException ioException)
			{
				ioException.printStackTrace();
				JLog.e(ioException.toString());
				return false;
			} finally
			{
				if (fos != null)
				{
					try
					{
						fos.flush();
						fos.close();
					} catch (IOException ioException)
					{
						ioException.printStackTrace();
						JLog.e(ioException.toString());
					}
				}
			}
		}
	}

	private static int caculateQualityScale(Bitmap bitmap, int size)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		int options = 100;

		while (baos.toByteArray().length > size)
		{
			options -= 10;
			if (options <= 0)
			{
				options = 10;
				break;
			}

			baos.reset();
			bitmap.compress(CompressFormat.JPEG, options, baos);
		}

		return options;
	}

	private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;
		if (width > reqWidth || height > reqHeight)
		{
			inSampleSize = (int) Math.round(Math.max(width * 1.0 / reqWidth, height / reqHeight));
		}
		return inSampleSize;
	}

	/**
	 * 得到image的宽高，以便压缩
	 * 
	 * @param imageView
	 *            存放image的imageView
	 * @return ImageSize,包含ImageSize.width,ImageSize.height
	 */
	public static ImageSize getImageSize(ImageView imageView)
	{
		ImageSize imageSize = new ImageSize();
		LayoutParams lp = imageView.getLayoutParams();
		// 如果imageview的实际宽度<0 说明还没有填充或其他情况，我们使用布局中的宽度
		// 布局中如果是固定宽度，我们可以取到，如果是wrap_content或者match_parent还是<0
		int width = imageView.getWidth() > 0 ? imageView.getWidth() : lp.width;
		int height = imageView.getHeight() > 0 ? imageView.getHeight() : lp.height;
		// 我们取布局中设置的最大宽度
		width = width <= 0 ? getImageViewFieldSize(imageView, "mMaxWidth") : width;
		height = height <= 0 ? getImageViewFieldSize(imageView, "mMaxHeight") : height;
		// 如果我们都取不到，则使用屏幕的宽和高
		if (width <= 0 || height <= 0)
		{
			DisplayMetrics metrics = imageView.getContext().getResources().getDisplayMetrics();
			width = width <= 0 ? metrics.widthPixels : width;
			height = height <= 0 ? metrics.heightPixels : height;
		}
		imageSize.width = width;
		imageSize.height = height;
		return imageSize;
	}

	private static int getImageViewFieldSize(Object object, String fieldName)
	{
		int value = 0;
		try
		{
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int maxValue = field.getInt(object);
			if (maxValue > 0 && maxValue < Integer.MAX_VALUE)
			{
				value = maxValue;
			}
		} catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 图片裁剪意图
	 * 
	 * @param uri
	 * @param outputX
	 * @param outputY
	 * @return
	 */
	public Intent initZoomIntent(Uri uri, int outputX, int outputY)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("output", uri);
		intent.putExtra("outputFormat", "JPEG");
		return intent;
	}

	/**
	 * Bitmap大小封装类
	 * 
	 * @author jzd
	 *
	 */
	public static class ImageSize
	{
		public int width;
		public int height;
	}
}
