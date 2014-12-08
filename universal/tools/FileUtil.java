package com.universal.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.util.EncodingUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FileUtil {

	private static final int IO_BUFFER_SIZE = 1024;

	/**
	 * 判断文件是否可读可写
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileCanReadAndWrite(String filePath) {
		if (null != filePath && filePath.length() > 0) {
			File f = new File(filePath);
			if (null != f && f.exists()) {
				return f.canRead() && f.canWrite();
			}
		}
		return false;
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径
	 * @param newPath
	 *            String 复制后路径
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[IO_BUFFER_SIZE];

				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
//			Logger.getInstance().error(e);
			return false;
		}
		return true;
	}

	/**
	 * 写入文件
	 * 
	 * @param strFileName
	 *            文件名
	 * @param ins
	 *            流
	 */
	public static void writeToFile(String strFileName, InputStream ins) {
		try {
			File file = new File(strFileName);

			FileOutputStream fouts = new FileOutputStream(file);
			int len;
			int maxSize = 1024 * 1024;
			byte buf[] = new byte[maxSize];
			while ((len = ins.read(buf, 0, maxSize)) != -1) {
				fouts.write(buf, 0, len);
				fouts.flush();
			}

			fouts.close();
		} catch (IOException e) {
//			Logger.getInstance().error(e);
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param strFileName
	 *            文件名
	 * @param bytes
	 *            bytes
	 */
	public static boolean writeToFile(String strFileName, byte[] bytes) {
		try {
			File file = new File(strFileName);

			FileOutputStream fouts = new FileOutputStream(file);
			fouts.write(bytes, 0, bytes.length);
			fouts.flush();
			fouts.close();
			return true;
		} catch (IOException e) {
//			Logger.getInstance().error(e);
		}
		return false;
	}

	/**
	 * Prints some data to a file using a BufferedWriter
	 */
	public static boolean writeToFile(String filename, String data) {
		BufferedWriter bufferedWriter = null;
		try {
			// Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(filename));
			// Start writing to the output stream
			bufferedWriter.write(data);
			return true;
		} catch (FileNotFoundException e) {
//			Logger.getInstance().error(e);
		} catch (IOException e) {
//			Logger.getInstance().error(e);
		} finally {
			// Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException e) {
//				Logger.getInstance().error(e);
			}
		}
		return false;
	}

	public static String Read(String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
//			Logger.getInstance().error(e);
		}
		return res;
	}

	public static void Write(String fileName, String message) {

		try {
			FileOutputStream outSTr = null;
			try {
				outSTr = new FileOutputStream(new File(fileName));
			} catch (FileNotFoundException e) {
//				Logger.getInstance().error(e);
			}
			BufferedOutputStream Buff = new BufferedOutputStream(outSTr);
			byte[] bs = message.getBytes();
			Buff.write(bs);
			Buff.flush();
			Buff.close();
		} catch (MalformedURLException e) {
//			Logger.getInstance().error(e);
		} catch (IOException e) {
//			Logger.getInstance().error(e);
		}
	}

	/**
	 * 删除文件 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 路径
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {// 如果是文件，则删除文件
			file.delete();
			return;
		}
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				deleteFile(files[i].getAbsolutePath());// 先删除文件夹里面的文件
			}
			files[i].delete();
		}
		file.delete();
	}

	/**
	 * 文件大小
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (!file.exists()) {
			return size;
		}
		if (!file.isDirectory()) {
			size = file.length();
		} else {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFileSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		}
		return size;
	}

	/**
	 * 
	 * @return 文件的大小，带单位(MB、KB等)
	 */
	public static String getFileLength(String filePath) {
		try {
			File file = new File(filePath);
			return fileLengthFormat(getFileSize(file));
		} catch (Exception e) {
//			Logger.getInstance().error(e);
			return "";
		}
	}

	/**
	 * 
	 * @return 文件的大小，带单位(MB、KB等)
	 */
	public static String fileLengthFormat(long length) {
		String lenStr = "";
		DecimalFormat formater = new DecimalFormat("#0.##");
		if (length > 0 && length < 1024) {
			lenStr = formater.format(length) + " Byte";
		} else if (length < 1024 * 1024) {
			lenStr = formater.format(length / 1024.0f) + " KB";
		} else if (length < 1024 * 1024 * 1024) {
			lenStr = formater.format(length / (1024 * 1024.0f)) + " MB";
		} else {
			lenStr = formater.format(length / (1024 * 1024 * 1024.0f)) + " GB";
		}
		return lenStr;
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 */
	public static String pathExtension(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point, length);
		}
	}

	/**
	 * 调用系统打开文件
	 * 
	 * @param file
	 * @param context
	 */
	public static boolean openFile(File file, Context context) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		// 跳转
		try {
			((Activity) context).startActivity(intent);
		} catch (ActivityNotFoundException e) {
//			Logger.getInstance().error(e);
			return false;
			// GYToast.makeText(context, "您还未安装打开文件的相关软件",
			// Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	@SuppressLint("DefaultLocale")
	public static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			java.io.File myDelFile = new java.io.File(filePathAndName);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	private static final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".doc", "application/msword" }, { ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" }, { ".xls", "application/vnd.ms-excel" },
			{ ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }, { ".pdf", "application/pdf" }, { ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" }, { ".txt", "text/plain" }, { ".wps", "application/vnd.ms-works" }, { "", "*/*" } };

	/**
	 * 解压文件
	 * 
	 * @param zipFile
	 * @param targetDir
	 * @return
	 */
	public static boolean Unzip(String zipFile, String targetDir) {
		try {
			int BUFFER = 4096; // 这里缓冲区我们使用4KB，
			String strEntry; // 保存每个zip的条目名称

			BufferedOutputStream dest = null; // 缓冲输出流
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry; // 每个zip条目的实例

			while ((entry = zis.getNextEntry()) != null) {

				int count;
				byte data[] = new byte[BUFFER];
				strEntry = entry.getName();

				File entryFile = new File(targetDir + strEntry);
				File entryDir = new File(entryFile.getParent());
				if (!entryDir.exists()) {
					entryDir.mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(entryFile);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();

			}
			zis.close();
			return true;
		} catch (IOException e) {
//			Logger.getInstance().error(e);
			return true;
		}
	}

	public void listMemoFolder() {

	}

}
