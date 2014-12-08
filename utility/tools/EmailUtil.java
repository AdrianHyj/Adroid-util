package com.universal.tools;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

public class EmailUtil {
	
	/**
	 * 邮件发送
	 * 带附件
	 * @param downloadFile
	 */
	public static void shareWithEmail(Context context, File downloadFile) {
		File file = new File(downloadFile.getAbsolutePath()); // 附件文件地址
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, file.getName());
		intent.putExtra("body", "android123 - email sender"); // 正文
		ArrayList<Parcelable> list = new ArrayList<Parcelable>();
		list.add(Uri.fromFile(file));
		intent.putExtra(Intent.EXTRA_STREAM, list); // 添加附件，附件为file对象
		if (file.getName().endsWith(".gz")) {
			intent.setType("application/x-gzip"); // 如果是gz使用gzip的mime
		} else if (file.getName().endsWith(".txt")) {
			intent.setType("text/plain"); // 纯文本则用text/plain的mime
		} else {
//			intent.setType("application/octet-stream"); // 其他的均使用流当做二进制数据来发送
			intent.setType("message/rfc882");
		}
		((Activity)context).startActivity(intent); // 调用系统的mail客户端进行发送
	}
	
}
