package com.universal.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

public class BitmapUtils {

	private static int count = 0;

	/**
	 * 200px PreviewBitmap
	 * 
	 * @param fileName
	 * @return
	 */
	public static Bitmap loadPreviewBitmap(String fileName, int width, int height) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			File file = new File(fileName);
			if (!file.exists()) {
				return null;
			}
			if (file.length() > 4 * 1024 * 1024) {
				options.inSampleSize = 4;
			} else if (file.length() > 1024 * 1024) {
				options.inSampleSize = 2;
			}
			Bitmap bm = BitmapFactory.decodeFile(fileName, options);
			if (bm != null) {
				Bitmap resizebm = resizePreviewBitmap(bm, width, height);
				bm.recycle();
				return resizebm;
			}
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	public static Bitmap resizePreviewBitmap(Bitmap bm, int width, int height) {
		try {
			if (bm != null) {
				Bitmap resizebm = Bitmap.createScaledBitmap(bm, width, height, true);
				return resizebm;
			} else {
				return null;
			}
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	public static Bitmap loadBitmap(InputStream is) {
		try {
			if (is != null && is.available() > 0) {
				return BitmapFactory.decodeStream(is);
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		} finally {

		}
	}

	public static Bitmap loadBitmap(Context c, String fileName) {
		ParcelFileDescriptor pfd;
		try {
			pfd = c.getContentResolver().openFileDescriptor(Uri.parse("file://" + fileName), "r");
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
		options.inSampleSize = computeSampleSize(options, 400);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
		if (count < 4) {
			count++;
		} else {
			count = 0;
			System.gc();
		}
		return sourceBitmap;
	}

	public static Bitmap loadBitmap(Context c, String fileName, int width) {
		ParcelFileDescriptor pfd;
		try {
			pfd = c.getContentResolver().openFileDescriptor(Uri.parse("file://" + fileName), "r");
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
		options.inSampleSize = computeSampleSize(options, width);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		try {
			Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
			if (count < 4) {
				count++;
			} else {
				count = 0;
				System.gc();
			}
			return sourceBitmap;
		} catch (OutOfMemoryError e) {

			return loadBitmap(c, fileName, (int) (width * 0.5));
		}

	}

	public static Bitmap loadBitmap(Context c, String fileName, int width, boolean sample) {
		ParcelFileDescriptor pfd;
		try {
			pfd = c.getContentResolver().openFileDescriptor(Uri.parse("file://" + fileName), "r");
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
		if (sample) {
			options.inSampleSize = computeSampleSize(options, width);
		} else {
			File file = new File(fileName);
			if (!file.exists()) {
				return null;
			}
			if (file.length() > 256 * 1024) {
				int presample = computeSampleSize(options, width);
				if (presample > 2 && file.length() / (2 * 2) < 256 * 1024) {
					options.inSampleSize = 2;
				} else if (presample > 3 && file.length() / (3 * 3) < 256 * 1024) {
					options.inSampleSize = 3;
				} else if (presample > 4 && file.length() / (4 * 4) < 256 * 1024) {
					options.inSampleSize = 4;
				} else if (file.length() / (presample * presample) > 256 * 1024) {
					options.inSampleSize = presample + 1;
				} else {
					options.inSampleSize = presample;
				}

			} else {
				options.inSampleSize = 1;
			}
		}

		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
		if (count < 4) {
			count++;
		} else {
			count = 0;
			System.gc();
		}
		return sourceBitmap;
	}

	// 这个函数会对图片的大小进行判断，并得到合适的缩放比例，比如2即1/2,3即1/3
	public static int computeSampleSize(BitmapFactory.Options options, int target) {
		int w = options.outWidth;
		int h = options.outHeight;
		float candidateW = w / target;
		float candidateH = h / target;
		float candidate = Math.max(candidateW, candidateH);
		candidate = (float) (candidate + 0.0);
		// int candidate = candidateW;
		if (candidate <= 1.0)
			return 1;
		// if (candidate > 1) {
		// if ((w > target) && (w / candidate) < target)
		// candidate -= 1;
		// }
		// if (candidate > 1) {
		// if ((h > target) && (h / candidate) < target)
		// candidate -= 1;
		// }
		return (int) candidate;
	}

	/** 获取图像的宽高 **/
	public static int[] getImageWH(String path) {
		int[] wh = { -1, -1 };
		if (path == null) {
			return wh;
		}
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				InputStream is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				wh[0] = options.outWidth;
				wh[1] = options.outHeight;
			} catch (Exception e) {
			}
		}
		return wh;
	}

	/**
	 * 保存bitmap图片
	 * 
	 * @param bitmap
	 * @param bitName
	 * @return 是否保存成功
	 */
	public static boolean saveBitmap(String bitName, Bitmap bitmap) {
		try {
			File temp = File.createTempFile("temp", ".png", new File(StringUtil.getNameDelLastPath(bitName)));
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(temp);
			} catch (FileNotFoundException e) {
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
			if (temp.exists()) {
				File f = new File(bitName);
				if (f.exists()) {
					f.delete();
				}
				FileUtil.moveFile(temp.getAbsolutePath(), bitName);
			}

			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void saveMyBitmap(String bitName, Bitmap mBitmap) {
		File f = new File(bitName);
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, (int) (height / 2.0), width, height);

		drawable.draw(canvas);

		return bitmap;

	}

	/**
	 * 截取webView可视区域的截图
	 * 
	 * @param webView
	 *            前提：WebView要设置webView.setDrawingCacheEnabled(true);
	 * @return
	 */
	public static Bitmap captureWebViewVisibleSize(WebView webView) {
		Bitmap bmp = webView.getDrawingCache();
		return bmp;
	}

	public static Bitmap captureWebView(WebView webView) {
		Picture snapShot = webView.capturePicture();

		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);

		return bmp;
	}

	public static Bitmap shotScreen(View cv) {
		try {
			cv.setDrawingCacheEnabled(true);
			Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			cv.draw(canvas);
			cv.setDrawingCacheEnabled(false);
			return bmp;
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap saveBitmapFromScrollView(ScrollView scrollView, String path) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			// scrollView.getChildAt(i).setBackgroundResource(R.drawable.bg3);
		}
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			if (out != null) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			return null;
		}
		return bitmap;
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int targetwidth) {
		int imageWidth = targetwidth;
		if (bitmap != null && (bitmap.getWidth() < imageWidth || bitmap.getHeight() < imageWidth)) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int min = width;
			if (width > height) {
				min = height;
			}
			float scale = imageWidth / min;

			bitmap = BitmapUtils.zoomBitmap(bitmap, (int) (width * scale), (int) (height * scale));

		}
		return bitmap;
	}

}
