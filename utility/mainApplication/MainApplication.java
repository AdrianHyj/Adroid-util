package com.universal.mainApplication;

import java.io.File;
import java.util.HashMap;


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.universal.alert.ExceptionToastHandler;
import com.universal.commen.Define;
import com.universal.tools.AndroidStorageUtil;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

public class MainApplication extends Application{

	//初始化instance
	private static MainApplication instanceApplication;
	
	public static MainApplication getInstance() {
		return instanceApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instanceApplication = this;
		initialise();
	}
	
	//以下是初始化要做的事

	private String logPath;
	private String picPath;
	private String videoPath;
	private String filePath;
	private String appPackagePath;
	private String cachePath;
	private String tmpPath;
	private String internalStoragePath;
	private String currentDirectory;
	
	
	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getAppPackagePath() {
		return appPackagePath;
	}

	public void setAppPackagePath(String appPackagePath) {
		this.appPackagePath = appPackagePath;
	}

	public String getCachePath() {
		return cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	public String getTmpPath() {
		return tmpPath;
	}

	public void setTmpPath(String tmpPath) {
		this.tmpPath = tmpPath;
	}
	
	private void initialise() {
		
        //为了让此handler在主线程新建；
        ExceptionToastHandler.init();
        
		updatePath();
		initImageLoader();
		
		//分享组件初始化
//        ShareSDK.initSDK(this,"androidv1101");
        
	}
	
	
	
	public void updatePath() {
		internalStoragePath = AndroidStorageUtil.initInternalStoragePath();
		this.currentDirectory = this.getFilesDir().getAbsolutePath()
				.concat(File.separator);
		this.logPath = this.currentDirectory + Define.LOG_PATH;
		this.cachePath = this.currentDirectory + Define.CACHE_PATH;
		this.tmpPath = this.currentDirectory + Define.TMP_PATH;
		this.picPath = this.currentDirectory + Define.PIC_PATH;
		this.videoPath = this.currentDirectory + Define.VIDEO_PATH;
		this.filePath = this.currentDirectory + Define.FILE_PATH;
		this.appPackagePath = this.currentDirectory + Define.PACKAGE_PATH;

		String storagePath = null;
		if (AndroidStorageUtil.isSDCardMounted()) {
			storagePath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
		} else if (null != internalStoragePath) {
			storagePath = internalStoragePath;
		}

		if (null != storagePath) {
			this.logPath = storagePath.concat(File.separator) + Define.LOG_PATH;
			this.cachePath = storagePath.concat(File.separator)
					+ Define.CACHE_PATH;
			this.tmpPath = storagePath.concat(File.separator) + Define.TMP_PATH;
			this.picPath = storagePath.concat(File.separator) + Define.PIC_PATH;
			this.videoPath = storagePath.concat(File.separator) + Define.VIDEO_PATH;
			this.filePath = storagePath.concat(File.separator) + Define.FILE_PATH;
			this.appPackagePath = storagePath.concat(File.separator) + Define.PACKAGE_PATH;
		}

		File logDir = new File(this.logPath);
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		File cacheDir = new File(this.cachePath);
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		File tmpDir = new File(this.tmpPath);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		File picDir = new File(this.picPath);
		if (!picDir.exists()) {
			picDir.mkdirs();
		}
		
		File videoDir = new File(this.videoPath);
		if (!videoDir.exists()) {
			videoDir.mkdirs();
		}
		
		File fileDir = new File(this.filePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		
		File appPackageDir = new File(this.appPackagePath);
		if (!appPackageDir.exists()) {
			appPackageDir.mkdirs();
		}

	}
	
	/**
	 * 初始化imageloader
	 */
	public void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(false).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCache(new UnlimitedDiscCache(new File(MainApplication.getInstance().getPicPath())))
				.diskCacheSize(1024*1024*100) // 100 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(options)
				.writeDebugLogs()
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.build();
		ImageLoader.getInstance().init(config);
	}
	
	/**
	 * 通过网络url获取本地存储路径
	 * @param url
	 * @return 图片本地存储路径
	 */
	public String imageLocalPath(String url){
		return this.picPath + url.substring(url.lastIndexOf("/") + 1);
	}
	/**
	 * 通过网络url获取本地存储路径
	 * @param url
	 * @return 视频本地存储路径
	 */
	public String videoLocalPath(String url){
		return this.videoPath + url.substring(url.lastIndexOf("/") + 1);
	}
	
}
