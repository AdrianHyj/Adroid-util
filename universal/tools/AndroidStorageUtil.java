package com.universal.tools;

import java.io.File;

import com.universal.commen.Define;

import android.os.Environment;

public class AndroidStorageUtil {
	
	/**
	 * 检测SD卡
	 * @return 是否有SD卡
	 */
	public static boolean isSDCardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
	
	/**
	 * 初始化存储路径
	 */
	public static String initInternalStoragePath() {
		String internalStoragePath ="";
		if (isSDCardMounted()) {
			return null;
		}
		for (String path : Define.INTERNAL_STORAGE_PATHS) {
			if (FileUtil.isFileCanReadAndWrite(path)) {
				internalStoragePath = path;
				return internalStoragePath;
			} else {
				File f = new File(path);
				if (f.isDirectory()) {
					for (File file : f.listFiles()) {
						if (file != null
								&& file.isDirectory()
								&& !file.isHidden()
								&& FileUtil.isFileCanReadAndWrite(file
										.getPath())) {
							internalStoragePath = file.getPath();
							if (!internalStoragePath.endsWith(File.separator)) {
								internalStoragePath += File.separator;
							}
							return internalStoragePath;
						}
					}
				}
				return null;
			}
		}
		return null;
	}
	
}
