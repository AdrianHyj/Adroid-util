package com.universal.tools;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtil {
	public static String getCurrentVersion(Context context) {
		if (context != null) {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
				return "V " + packageInfo.versionName;
			} catch (NameNotFoundException e) {
				return "获取版本号失败";
			}
		} else {
			return "获取版本号失败";
		}
	}
	
	public static int getCurrentVersionCode(Context context) {
		if (context != null) {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
				return packageInfo.versionCode;
			} catch (NameNotFoundException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}
}
