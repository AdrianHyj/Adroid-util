package com.universal.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import android.os.Environment;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.universal.mainApplication.MainApplication;
import com.universal.net.CustomAsyncHttpClient;

public class DownloadUtil {
	
	/**
	 * 下载文件
	 * @author Weiquan
	 * @param strFileName 文件名字
	 * @param downloadURL 下载地址
	 * @param listener 下载进度
	 * @return 下载结果
	 */
	public static void downloadFileWithStatusbarProgress(String strFileName, String downloadURL, final DownloadProgressListener listener) {
		String filePostfix = strFileName.substring(strFileName.lastIndexOf(".") + 1);
		String fileName = strFileName.substring(0, strFileName.lastIndexOf("."));
		final File downloadFile = new File(MainApplication.getInstance().getFilePath() + fileName + "_" + downloadURL.substring(downloadURL.lastIndexOf("=") + 1) + filePostfix);
		
		if (downloadFile.exists()) {
			listener.finish(null, downloadFile);
			return ;
		}
		
		if (!(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))) {
			listener.finish("下载出错", null);
			return ;
		}
		
		try {
			downloadFile.createNewFile();
			final FileOutputStream outputStream = new FileOutputStream(downloadFile);
			CustomAsyncHttpClient.getInstance().get(downloadURL, null, new AsyncHttpResponseHandler() {

				@Override
				public void sendResponseMessage(HttpResponse response) throws IOException {
//					super.sendResponseMessage(response);
					InputStream fileInputStream = response.getEntity().getContent();
					byte[] buffer = new byte[1024];
					int read = -1;
					float count = 0;
					int precent = 0;
					int beforePrecent = 0;
					long length = response.getEntity().getContentLength();
					while ((read = fileInputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, read);
						count += read;
						precent = (int) (count * 100 / length);
						if (precent - beforePrecent > 1) {
							beforePrecent = precent;
							listener.updateProgress(precent);
						}
					}
					outputStream.flush();
					outputStream.close();
					fileInputStream.close();
					listener.finish(null, downloadFile);
				}

			});
		} catch (IOException e) {
			downloadFile.delete();
			listener.finish("下载出错", null);
		}
	}

	public interface DownloadProgressListener {
		public void updateProgress(int progress);
		public void finish(String errorMessage, File downloadFile);
	}
}
