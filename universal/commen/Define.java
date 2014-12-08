package com.universal.commen;

import android.content.pm.PackageManager.NameNotFoundException;

import com.universal.mainApplication.MainApplication;

public class Define {

	//webView Url
	public static final String HOME_URL = "http://m.lba.cn/";
	public static final String ASSORTMENT_URL = "http://m.lba.cn/categoryList.html";  
	public static final String DISCOVER_URL = "http://m.lba.cn/faxian.html";  
	public static final String PURCHASE_URL = "http://m.lba.cn/cart.html";  
	public static final String MYVIEW_URL = "http://m.lba.cn/user.html";  
	
	//storage
	public static final String[] INTERNAL_STORAGE_PATHS = new String[] { "/mnt/", "/emmc/" };
	public static final String CACHE_PATH = "lbaobaophone/.cache/";
	public static final String PIC_PATH = "lbaobaophone/pic/";
	public static final String VIDEO_PATH = "lbaobaophone/video/";
	public static final String FILE_PATH = "lbaobaophone/files/";
	public static final String TMP_PATH = "lbaobaophone/.tmp/";
	public static final String LOG_PATH = "lbaobaophone/.log/";
	public static final String DOWNLOAD_PATH = "lbaobaophone/download/";
	public static final String PACKAGE_PATH = "lbaobaophone/package/";
	
	//net
	public static final int UNDEFINE = -1;
	public static final String SECTET_CODE = "ad1a8a381d12d411879bd5e9a998e3f1b7354e34"; 
	public static final int REQUEST_TIMEOUT = 20000;
	public static final float DENSITY = MainApplication.getInstance().getResources().getDisplayMetrics().density;
	public static final float SCALESITY = MainApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
	public static final int widthPx = MainApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
	public static final int heightPx = MainApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
	public static final int APP_VERSION = getAPPVer();
	
	public static int getAPPVer() {
		try {
			return MainApplication.getInstance().getPackageManager().getPackageInfo(MainApplication.getInstance().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
