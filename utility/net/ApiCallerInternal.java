package com.universal.net;

/**
 * 这是接口工具。有需要则俺下面例子重写
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.universal.commen.Define;

public class ApiCallerInternal {

	private CustomAsyncHttpClient httpClient;
	
	public ApiCallerInternal() {
		httpClient = CustomAsyncHttpClient.getInstance();
	}
	
//	private interface HttpRequestCallback {
//		void requestFinish(String content, Error error);
//	}
//	
//	public interface ApiRequestCallback {
//		void requestFinish(JSONObject response, Error error);
//	}
//	
//	/**
//	 *  发出get请求
//	 * @param uri
//	 * @param parameters
//	 * @param callback
//	 */
//	public void getRequest(String uri, HashMap<String, String> parameters, ApiRequestCallback callback) {
//		String url = buildRequestUrl(uri);
//		HashMap<String, String> requestParameters = new HashMap<String, String>();
//		requestParameters.putAll(parameters);
////		signRequestParameters(requestParameters);
//		RequestParams params = buildRequestParams(requestParameters);
//		
//		httpClient.get(url, params, buildResponseHandler(callback));
//	}
//	
//	/**
//	 * 发出post请求
//	 * @param uri
//	 * @param parameters
//	 * @param callback
//	 */
//	public void postRequest(String uri, HashMap<String, String> parameters, ApiRequestCallback callback) {
//		String url = buildRequestUrl(uri);
//		HashMap<String, String> requestParameters = new HashMap<String, String>();
//		requestParameters.putAll(parameters);
////		signRequestParameters(requestParameters);
//		RequestParams params = buildRequestParams(requestParameters);
//		
//		httpClient.post(url, params, buildResponseHandler(callback));
//	}
//	
//	/**
//	 * 发出post请求
//	 * @param uri
//	 * @param parameters
//	 * @param callback
//	 */
//	public void postRequest(String uri, HashMap<String, String> parameters, HashMap<String, File> fileParameters, ApiRequestCallback callback) {
//		String url = buildRequestUrl(uri);
//		HashMap<String, String> requestParameters = new HashMap<String, String>();
//		requestParameters.putAll(parameters);
////		signRequestParameters(requestParameters);
//		RequestParams params = buildRequestParams(requestParameters);
//		
//		Iterator<String> it = fileParameters.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			try {
//				params.put(key, fileParameters.get(key));
//			} catch (FileNotFoundException e) {
////				ConsoleLogger.getConsoleLogger().error(e);
//			}
//		}
//		httpClient.post(url, params, buildResponseHandler(callback));
//	}
//	
//	/**
//	 * 组装api url
//	 * @param uri
//	 * @return
//	 */
//	private String buildRequestUrl(String uri) {
//		if(uri.startsWith("http://", 0)){
////			ConsoleLogger.getConsoleLogger().v(uri);
//			return uri;
//		}
////		ConsoleLogger.getConsoleLogger().v(Define.REQUEST_BASE_URL + uri);
//		return Define.REQUEST_BASE_URL + uri;
//	}
//	
////	/**
////	 * 参数数字签名
////	 */
////	private void signRequestParameters(HashMap<String, String> requestParameters) {
////		String signature = EncryptUtil.shaEncrypt(requestParameters);
////		requestParameters.put(Params.SIGNATURE, signature);
////		ConsoleLogger.getConsoleLogger().v("signature:" + signature);
////	}
//	
//	/**
//	 * 封装成http请求参数
//	 * @param requestParameters
//	 * @return
//	 */
//	private RequestParams buildRequestParams(HashMap<String, String> requestParameters) {
//		RequestParams requestParams = new RequestParams();
//		Iterator<String> it = requestParameters.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			requestParams.put(key, requestParameters.get(key));
//		}
//		return requestParams;
//	}
//	
//	/**
//	 * 创建AsyncHttp请求callback
//	 * @param callback 回调ApiCaller
//	 * @return
//	 */
//	private AsyncHttpResponseHandler buildResponseHandler(final ApiRequestCallback callback) {
//		return buildResponseHandler(buildHttpRequestCallback(callback));
//	}
//	
//	/**
//	 * 创建AsyncHttp请求callback
//	 * @param callback  回调给HttpRequestCallback，处理json
//	 * @return
//	 */
//	private AsyncHttpResponseHandler buildResponseHandler(final HttpRequestCallback callback) {
//		return new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(String content) {
////				ConsoleLogger.getConsoleLogger().v( content);
//				callback.requestFinish(content, null);
//			}
//
//			@Override
//			public void onFailure(Throwable error, String content) {
////				ConsoleLogger.getConsoleLogger().e(content);
////				GYError gyError = new GYError(500, "服务器未响应，请稍候再试");
//				Error gyError = new Error();
//				callback.requestFinish(null, gyError);
//			}
//		};
//	}
//	
//	/**
//	 * 创建HttpRequestCallback请求callback
//	 * @param callback 回调ApiCaller
//	 * @return
//	 */
//	private HttpRequestCallback buildHttpRequestCallback(final ApiRequestCallback callback) {
//		return new HttpRequestCallback() {
//			@Override
//			public void requestFinish(String content, Error error) {
//
//				if (error == null) {
//					JSONObject jsonObject = null;
//					try {
//						jsonObject = new JSONObject(content);
////						int status = Integer.parseInt(jsonObject.getString("status"));
////						int status = jsonObject.getInt("error");
//
//						if (!jsonObject.has("code")) {
//							callback.requestFinish(jsonObject, null);
//						}
//						else {
//							//errorMessag 要根据errorCode table 决定，暂时不处理
////							final String errorMessage = jsonObject.getString("errorMessage");
////							if(status == 400){
////								errorMessage = "服务器未响应，请稍候再试";
////							}
//							int code = jsonObject.getInt("code");
//							if (code == 0) {
//								callback.requestFinish(jsonObject, null);
//							}
//							else {
//								String message = "";
//								if (jsonObject.has("message")) {
//									message = jsonObject.getString("message");
//								}
//								LBError lbError = new LBError(code, message);
//								callback.requestFinish(jsonObject, lbError);
//							}
//						}
//						
//					} catch (Exception e) {
////						ConsoleLogger.getConsoleLogger().error(e);
////						GYError gyError = new GYError(400, "服务器未响应，请稍候再试");
//						Error gyError = new Error();
//						callback.requestFinish(null, gyError);
//					}
//				} else {
////					ConsoleLogger.getConsoleLogger().e(error.getMessage());
////					GYError gyError = new GYError(400, "服务器未响应，请稍候再试");
//					Error gyError = new Error();
//					callback.requestFinish(null, gyError);
//				}
//			}
//		};
//	}
}
