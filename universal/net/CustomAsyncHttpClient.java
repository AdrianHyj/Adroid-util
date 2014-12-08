package com.universal.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.universal.commen.Define;


public class CustomAsyncHttpClient {

	private static CustomAsyncHttpClient mInstance;
	private AsyncHttpClient httpClient;

	CustomAsyncHttpClient() {
		httpClient = new AsyncHttpClient();
		httpClient.setTimeout(Define.REQUEST_TIMEOUT);
	}

	public static CustomAsyncHttpClient getInstance() {
		if (mInstance == null) {
			synchronized (CustomAsyncHttpClient.class) {
				if (mInstance == null) {
					mInstance = new CustomAsyncHttpClient();
				}
			}
		}
		return mInstance;
	}

	/**
	 * GET Request
	 * 
	 * @param url
	 *            绝对地址
	 * @param params
	 *            参数（空则null）
	 * @param responseHandler
	 *            返回参数的handler
	 * @author yuelaiye
	 */
	public void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(url, params, responseHandler);
	}

	/**
	 * POST Request
	 * 
	 * @param url
	 *            绝对地址
	 * @param params
	 *            参数
	 * @param responseHandler
	 *            返回参数的Handler
	 * @author yuelaiye
	 */
	public void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.post(url, params, responseHandler);
	}
}
