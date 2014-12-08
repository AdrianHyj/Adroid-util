package com.universal.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.universal.alert.ExceptionToastHandler;
import com.universal.mainApplication.MainApplication;
import com.universal.net.NetUtil.Response;
/**
 * 这是接口工具。有需要则俺下面例子重写
 */
//import com.lbaobao.alert.ExceptionToastHandler;
//import com.lbaobao.define.Define;
//import com.lbaobao.define.Params;
//import com.lbaobao.lba.app.LBApp;
//import com.lbaobao.model.LBError;
//import com.lbaobao.net.ApiCallerInternal.ApiRequestCallback;
//import com.lbaobao.net.NetUtil.Response;
//import com.lbaobao.saveappdata.APPDataManager;

public class ApiAdapter {

	private static ApiAdapter mInstance;
	private ApiCallerInternal internal;

	ApiAdapter() {
		internal = new ApiCallerInternal();
	}

	// ApiCaller singleton
	public static ApiAdapter getInstance() {
		if (mInstance == null) {
			synchronized (ApiAdapter.class) {
				if (mInstance == null) {
					mInstance = new ApiAdapter();
				}
			}
		}
		return mInstance;
	}

	// api call back
	public interface LoginCallBack {
		void requestFinish(String token, Error error);
	}
	

	public interface GetDetailViewLastSecCallBack {
		void requestFinish(String time, Error error);
	}
	
	public interface GetXmlCallBack {
		void requestFinish(String xmlString, Error error);
	}
	
	public interface CheckXmlUpdateCallBack {
		void requestFinish(String resultString, Error error);
	}
	
	public interface CheckAPPUpdateCallBack {
		void requestFinish(Boolean showUpdate, String downloadUrl,  Error error);
	}
//	
//	/**
//	 * 加入认证token参数
//	 * @param requestParameters
//	 */
//	private void authorizeRequestParameters(HashMap<String, String> requestParameters) {
//		String token = APPDataManager.getInstance().getAppStringData(Define.DEVICE_TOKEN, "");
//		requestParameters.put(Params.DEVICE_TOKEN, token);
//	}
//
//	/**
//	 * 登陆绑定
//	 */
//	public void login(String deviceToken, String vipCard, final LoginCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		parameters.put(Params.VIP_CARD, vipCard);
//		parameters.put("device_token", deviceToken); 
//		
//		internal.postRequest(Define.LOGIN_URL, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error ==null) {
//						if (response.has("data")) {
//							JSONObject resposeObject;
//							try {
//								resposeObject = response.getJSONObject("data");
//								if (resposeObject.has("token")) {
//									callback.requestFinish(resposeObject.getString("token"), null);
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//					}
//					callback.requestFinish("", error);
//				}
//		});
//	}
//	
//	/**
//	 * 获取内页返回秒数 
//	 */
//	public void getDetialViewLastSec(final GetDetailViewLastSecCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		
//		internal.getRequest(Define.GET_DETAIL_LAST_TIME, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							try {
//								JSONObject resposeObject;
//								resposeObject = response.getJSONObject("data");
//								if (resposeObject.has("result") && resposeObject.has("time")) {
//									if (resposeObject.getString("result").equals("true")) {
//										callback.requestFinish(resposeObject.getString("time"), null);
//									}
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish("", error);
//					}
//				}
//		});
//	}
//	
//	/**
//	 * 向服务器提交机器的运行数据 
//	 */
//	public void sendDataToServer(String ip, String token, String mac) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
////		authorizeRequestParameters(parameters);
//		parameters.put("device_token", token);
//		parameters.put("ip", ip);
//		parameters.put("mac", mac);
//		
//		internal.postRequest(Define.SEND_DATA_TO_SERVER, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {}
//		});
//	}
//	
//	/**
//	 * 首页xml
//	 */
//	public void getIndexXml(final GetXmlCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		
//		internal.getRequest(Define.INDEX_XML_URL, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							try {
//								String xmlString = response.getString("data");
//								callback.requestFinish(xmlString, null);
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish("", error);
//					}
//				}
//		});
//	}
//	
//	/**
//	 * 锁屏xml
//	 */
//	public void getScreenXml(final GetXmlCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		
//		internal.getRequest(Define.SCREEN_XML_URL, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							try {
//								String xmlString = response.getString("data");
//								callback.requestFinish(xmlString, null);
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish("", error);
//					}
//				}
//		});
//	}
//	
//	/**
//	 * 检查首页是否更新
//	 */
//	public void checkIndexXmlUpdate(String version, final CheckXmlUpdateCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		parameters.put(Params.VERSION, version);
//		
//		internal.getRequest(Define.CHECK_INDEX_XML, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							JSONObject resposeObject;
//							try {
//								resposeObject = response.getJSONObject("data");
//								if (resposeObject.has("result")) {
//									callback.requestFinish(resposeObject.getString("result"), null);
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish("", error);
//					}
//				}
//		});
//	}
//	
//	/**
//	 * 检查App更新
//	 */
//	public void checkAPPUpdate(String appVersion, final CheckAPPUpdateCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		parameters.put(Params.VERSION, appVersion);
//		
//		internal.getRequest(Define.CHECK_APP_UPDATE, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							JSONObject resposeObject;
//							try {
//								resposeObject = response.getJSONObject("data");
//								if (resposeObject.has("result") && resposeObject.has("url") && resposeObject.getString("result").equals("true")) {
//									callback.requestFinish(true, resposeObject.getString("url"), null);
//								}
//								else {
//									callback.requestFinish(false, "", null);
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//								callback.requestFinish(false, "", null);
//							}
//						}
//						else {
//							callback.requestFinish(false, "", null);
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish(false, "", null);
//					}
//				}
//		});
//	}
//	
//	/**
//	 * 检查锁屏是否更新
//	 */
//	public void checkScreenXmlUpdate(String version, final CheckXmlUpdateCallBack callback) {
//		
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		authorizeRequestParameters(parameters);
//		parameters.put(Params.VERSION, version);
//		
//		internal.getRequest(Define.CHECK_SCREEN_XML, parameters,new ApiRequestCallback() {
//
//			@Override
//			public void requestFinish(JSONObject response, Error error) {
//					if (error == null) {
//						if (response.has("data")) {
//							JSONObject resposeObject;
//							try {
//								resposeObject = response.getJSONObject("data");
//								if (resposeObject.has("result")) {
//									callback.requestFinish(resposeObject.getString("result"), null);
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					else {
//						ExceptionToastHandler.getInstance().showErrorText(error.getMessage());
//						callback.requestFinish("", error);
//					}
//				}
//		});
//	}
//
	/**
	 * url 图片下载路径
	 */
	public static void getPicture(String url, String imageMD5,
			ApiAdapterCallBack callback) {
		if (url == null || url.equals("") || url.matches("")) {
			return;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		File localFile = new File(MainApplication.getInstance().imageLocalPath(url));
		if (localFile.exists()) {
			if (callback != null) {
				map.put("imageUrl", url);
				map.put("MD5", imageMD5);
				map.put("imageFile", localFile);
				callback.doSuccess(map);
			}
			return;
		}

		try {
			Response res = NetUtil.get(url);
			InputStream in = res.getInputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			FileOutputStream out = new FileOutputStream(localFile);
			while ((count = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, count);
			}
			in.close();
			out.close();
			res.getClientConnectionManager().shutdown();
			if (callback != null) {
				map.put("imageUrl", url);
				map.put("MD5", imageMD5);
				map.put("imageFile", localFile);
				callback.doSuccess(map);
			}
		} catch (Exception e) {
			Log.i("22", e.toString());
			ExceptionToastHandler.getInstance().showFailObject(e);
			// FITLog.error(ExceptionUtil.getCrashInfo(e));
			if (callback != null) {
				map.put("imageUrl", url);
				map.put("MD5", imageMD5);
				map.put("imageException", e);
				callback.doFail(map);
			}
		}
	}

	/**
	 * url 视频下载路径
	 */
	public static void getVideo(String url, String videoMD5,
			ApiAdapterCallBack callback) {
		if (url == null || url.equals("") || url.matches("")) {
			return;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();

		File localFile = new File(MainApplication.getInstance().videoLocalPath(url));
		if (localFile.exists()) {
			if (callback != null) {
				map.put("videoUrl", url);
				map.put("MD5", videoMD5);
				map.put("videoFile", localFile);
				callback.doSuccess(map);
			}
			return;
		}

		try {
			Response res = NetUtil.get(url);
			InputStream in = res.getInputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			FileOutputStream out = new FileOutputStream(localFile);
			while ((count = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, count);
			}
			in.close();
			out.close();
			res.getClientConnectionManager().shutdown();
			if (callback != null) {
				map.put("videoUrl", url);
				map.put("videoFile", localFile);
				map.put("MD5", videoMD5);
				callback.doSuccess(map);
			}
		} catch (Exception e) {
			ExceptionToastHandler.getInstance().showFailObject(e);
			if (callback != null) {
				map.put("videoUrl", url);
				map.put("MD5", videoMD5);
				map.put("videoException", e);
				callback.doFail(map);
			}
		}
	}

	/**
	 * url 要下载文件的Url savePath 下载好的文件存储的路径 callback 成功或者失败的回掉
	 */
	public static void getFile(String url, String savePath,
			ApiAdapterCallBack callback) {
		if (url == null || url.equals("") || url.matches("")) {
			return;
		}

		File localFile = new File(savePath
				+ url.substring(url.lastIndexOf("/") + 1));

		try {
			Response res = NetUtil.get(url);
			InputStream in = res.getInputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			FileOutputStream out = new FileOutputStream(localFile);
			while ((count = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, count);
			}
			in.close();
			out.close();
			res.getClientConnectionManager().shutdown();
			if (callback != null) {
				callback.doSuccess(localFile);
			}
		} catch (Exception e) {
			ExceptionToastHandler.getInstance().showFailObject(e);
			if (callback != null) {
				ExceptionToastHandler.getInstance().showFailObject(e);
				callback.doFail("getFileFail");
			}
		}
	}
//	
//	public interface GetFileWithProgressCallBack {
//		void requestFinish(long total,long download, File finishFile,LBError error);
//	}
//	
//	public static void getFileWithProgress(String url, String savePath,
//			GetFileWithProgressCallBack callback) {
//		if (url == null || url.equals("") || url.matches("")) {
//			return;
//		}
//
//		File localFile = new File(savePath
//				+ url.substring(url.lastIndexOf("/") + 1));
//
//		try {
//			Response res = NetUtil.get(url);
//			InputStream in = res.getInputStream();
//			byte[] buffer = new byte[1024];
//			int count = 0;
//			FileOutputStream out = new FileOutputStream(localFile);
//			long down = 0;
//			long total = res.getDataLength();
//			while ((count = in.read(buffer, 0, 1024)) != -1) {
//				out.write(buffer, 0, count);
//				down += count;
//				if (callback != null) {
//					callback.requestFinish(total, down, null, null);
//				}
//			}
//			in.close();
//			out.close();
//			res.getClientConnectionManager().shutdown();
//			if (total == down) {
//				if (callback != null) {
//					callback.requestFinish(total, down, localFile, null);
//				}
//			}
//		} catch (Exception e) {
//			ExceptionToastHandler.getInstance().showFailObject(e);
//			if (callback != null) {
//				ExceptionToastHandler.getInstance().showFailObject(e);
//				if (callback != null) {
//					callback.requestFinish((long)100, (long)0, null, new LBError(10000, "downloadPageFail"));
//				}
//			}
//		}
//	}

}
