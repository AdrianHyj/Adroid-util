package com.universal.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class ImageUtil {

	/**
	 * 生成实心圆
	 * 
	 * @param color
	 *            颜色
	 * @param size
	 *            直径
	 * @return
	 */
	public static Drawable createOvalSolidDrawable(int color, int size) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(color);
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setSize(size, size);
		return drawable;
	}

	/**
	 * 生成圆环
	 * 
	 * @param strokeColor
	 *            圆环颜色
	 * @param strokeWidth
	 *            环的宽度
	 * @param size
	 *            直径
	 */
	public static Drawable createRingDrawable(int strokeColor, int strokeWidth,
			int size) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.TRANSPARENT);
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setStroke(strokeWidth, strokeColor);
		if (size > 0) {
			drawable.setSize(size, size);
		}
		return drawable;
	}

	/**
	 * 生成圆角
	 * 
	 * @param color
	 *            背景颜色
	 * @param size
	 *            直径
	 */
	public static Drawable createRectRoundDrawable(int color, int size,
			int radius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(color);
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setCornerRadius(radius);
		return drawable;
	}

	/**
	 * 图片圆角
	 * 
	 * @param file
	 *            图片路径
	 * @param ratio
	 *            截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
	 * @return
	 */
	public static Bitmap toRoundCorner(String file, float ratio) {
		Bitmap bitmap = null;
		if (file != null) {
			bitmap = BitmapFactory.decodeFile(file);
		}
		return toRoundCorner(bitmap, ratio);
	}

	/**
	 * 画圆角图片
	 * 
	 * @param bitmap
	 * @param ratio
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
				bitmap.getHeight() / ratio, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
//		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

		return output;
	}

	/**
	 * 指定宽高加载bitmap
	 * 
	 * @param c
	 * @param fileName
	 * @param width
	 * @return
	 */
	public static Bitmap loadBitmap(Context c, String fileName, int width,
			int height) {
		int count = 0;
		ParcelFileDescriptor pfd;
		try {
			pfd = c.getContentResolver().openFileDescriptor(
					Uri.parse("file://" + fileName), "r");
		} catch (IOException ex) {
			return null;
		}
		java.io.FileDescriptor fd = pfd.getFileDescriptor();
		BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inTempStorage = new byte[10 * 1024];
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		// 我们的目标是在400pixel的画面上显示。
		// 所以需要调用computeSampleSize得到图片缩放的比例
		options.inSampleSize = computeSampleSize(options, width, height);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		try {
			Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null,
					options);
			if (count < 4) {
				count++;
			} else {
				count = 0;
				System.gc();
			}
			return sourceBitmap;
		} catch (OutOfMemoryError e) {

			return loadBitmap(c, fileName, (int) (width * 0.5),
					(int) (height * 0.5));
		}
	}

	/**
	 * 用computeSampleSize得到图片缩放的比例
	 * 
	 * @param options
	 * @param target
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int width, int height) {
		int w = options.outWidth;
		int h = options.outHeight;
		float candidateW = w / width;
		float candidateH = h / height;
		float candidate = Math.max(candidateW, candidateH);
		candidate = (float) (candidate + 0.5);

		if (candidate < 1.0)
			return 1;

		return (int) candidate;
	}

	/**
	 * 获取相机照相图片
	 * 
	 * @param context
	 * @param imgpath
	 * @param adjustOritation
	 * @return
	 */
	public static Bitmap loadBitmapRotate(Context context, String imgpath,
			boolean adjustOritation, int width, int height) {
		if (!adjustOritation) {
			return loadBitmap(context, imgpath, width, height);
		} else {

			Bitmap bitmap = loadBitmap(context, imgpath, width, height);
			int rotate = 0;
			ExifInterface exif = null;
			try {
				exif = new ExifInterface(imgpath);
			} catch (IOException e) {
				e.printStackTrace();
				exif = null;
			}
			if (exif != null) {
				// 读取图片中相机方向信息
				int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				// 计算旋转角度
				switch (ori) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				default:
					rotate = 0;
					break;
				}
			}
			if (rotate != 0) {
				// 旋转图片
				Matrix matrix = new Matrix();
				matrix.postRotate(rotate);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
				bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
			}
			return bitmap;
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @param targetKB
	 * @return
	 */
	public static int compressImage(Bitmap image, int targetKB) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		while (baos.toByteArray().length / 1024 > targetKB) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;
		}
		return options;
	}

	/**
	 * 保存图片
	 * 
	 * @param bitName
	 * @param bitmap
	 * @return
	 */
	public static boolean saveBitmap(String bitName, Bitmap bitmap) {
		try {
			File file = new File(bitName);
			try {
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
