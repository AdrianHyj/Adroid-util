package com.universal.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.universal.commen.Define;

import android.net.Uri;


public class NetUtil {
	
	public static String post(String strURL, List<NameValuePair> params) throws IOException {
		Uri.Builder uriBuilder = Uri.parse(strURL).buildUpon();
		for (NameValuePair nameValuePair : params) {
			uriBuilder.appendQueryParameter(nameValuePair.getName(), nameValuePair.getValue());
		}
		HttpClient httpclient = getNewHttpClient();
        try {
            HttpPost httppost = new HttpPost(strURL);
            httppost.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 60000);
            httppost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 60000);
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            InputStream is = resEntity == null ? null : resEntity.getContent();
//        	FITLog.logRequestUrl(uriBuilder.build().toString(), resEntity == null ? 0 : resEntity.getContentLength() <= 0 ? is.available()/1024 : resEntity.getContentLength() / 1024);
            if (is != null) {
    			byte[] buffer = new byte[1024];
    			int count = 0;
    			StringBuilder resultStr = new StringBuilder();
    			while ((count = is.read(buffer, 0, 1024)) != -1)
    			{
    				resultStr.append(new String(buffer, 0, count));
    			}
    			is.close();
    			return resultStr.toString();
            }
            return null;
        } catch (UnsupportedEncodingException e) {
//        	FITLog.error(ExceptionUtil.getCrashInfo(e));
		} catch (ClientProtocolException e) {
//		    FITLog.error(ExceptionUtil.getCrashInfo(e));
		} finally {
            try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        return null;
	}
	
	public static Response postS(String strURL, List<NameValuePair> params) throws IOException
	{
		try {
			Uri.Builder uriBuilder = Uri.parse(strURL).buildUpon();
			for (NameValuePair nameValuePair : params) {
				uriBuilder.appendQueryParameter(nameValuePair.getName(), nameValuePair.getValue());
			}
			HttpClient httpclient = getNewHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			httppost.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
			httppost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
			UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			Response myResponse = resEntity == null ? null : new Response(resEntity.getContent(), httpclient.getConnectionManager(),resEntity.getContentLength());
//			FITLog.logRequestUrl(uriBuilder.build().toString(), myResponse == null ? 0 : resEntity.getContentLength() <= 0 ? myResponse.getInputStream().available()/1024 : resEntity.getContentLength() / 1024);
			if (myResponse != null) {
				return myResponse;
			} 
			else {
				httpclient.getConnectionManager().shutdown();
			}
		} catch (UnsupportedEncodingException e) {
//			FITLog.error(ExceptionUtil.getCrashInfo(e));
		} catch (ClientProtocolException e) {
//			FITLog.error(ExceptionUtil.getCrashInfo(e));
		} finally{
			
		}
		return null;
	}
	
	public static Response get(String strURL) throws IOException {
		try {
			HttpClient httpclient = getNewHttpClient();
			HttpGet httpget = new HttpGet(strURL);
			httpget.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
			httpget.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			Response myResponse = resEntity == null ? null : new Response(resEntity.getContent(), httpclient.getConnectionManager(),resEntity.getContentLength());
//			FITLog.logRequestUrl(strURL, myResponse == null ? 0 : resEntity.getContentLength() <= 0 ? myResponse.getInputStream().available()/1024 : resEntity.getContentLength() / 1024);
			if (myResponse != null) {
				return myResponse;
			}
			else {
				httpclient.getConnectionManager().shutdown();
			}
		} catch (UnsupportedEncodingException e) {
//			FITLog.error(ExceptionUtil.getCrashInfo(e));
		} catch (ClientProtocolException e) {
//			FITLog.error(ExceptionUtil.getCrashInfo(e));
		}
		return null;
	}
	
	public static long getContentLength(String strURL) throws IOException {
	    long contentLength = Define.UNDEFINE;
	    try {
	        HttpClient httpclient = getNewHttpClient();
            HttpGet httpget = new HttpGet(strURL);
            httpget.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
            httpget.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity resEntity = response.getEntity();
//            FITLog.logRequestUrl(strURL, resEntity == null ? 0 : resEntity.getContentLength() <= 0 ? resEntity.getContent().available()/1024 : resEntity.getContentLength() / 1024);
            if (resEntity != null) {
                contentLength = resEntity.getContentLength();
            }
            httpclient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException e) {
//            FITLog.error(ExceptionUtil.getCrashInfo(e));
        } catch (ClientProtocolException e) {
//            FITLog.error(ExceptionUtil.getCrashInfo(e));
        }
        return contentLength;
	}
	
	public static String postFile(String strURL, List<String[]> params, String key, File file) throws IOException {
		HttpClient httpclient = getNewHttpClient();
        try {
            HttpPost httppost = new HttpPost(strURL);
            httppost.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 100000);
            httppost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 100000);
            MultipartEntity reqEntity = new MultipartEntity();
            if (file != null && file.exists()) {
                FileBody bin = new FileBody(file);
                reqEntity.addPart(key, bin);
            }
            if (params != null) {
                for (String[] param : params) {
                    reqEntity.addPart(param[0], new StringBody(param[1]));
                }
            }
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            InputStream is = resEntity == null ? null : resEntity.getContent();
//            FITLog.logRequestUrl(strURL, resEntity == null ? 0 : resEntity.getContentLength() <= 0 ? is.available()/1024 : resEntity.getContentLength() / 1024);
            if (is != null) {
    			byte[] buffer = new byte[1024];
    			int count = 0;
    			StringBuilder resultStr = new StringBuilder();
    			while ((count = is.read(buffer, 0, 1024)) != -1)
    			{
    				resultStr.append(new String(buffer, 0, count));
    			}
    			is.close();
    			return resultStr.toString();
            }
            return null;
        } catch (UnsupportedEncodingException e) {
//        	FITLog.error(ExceptionUtil.getCrashInfo(e));
		} catch (ClientProtocolException e) {
//			FITLog.error(ExceptionUtil.getCrashInfo(e));
		} finally {
            try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        return null;
	}
	
	private static HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
	public static class Response {
		private InputStream is;
		private ClientConnectionManager ccm;
		private long dataLength;
		
		public Response(InputStream is, ClientConnectionManager ccm,long dataLength) {
			super();
			this.is = is;
			this.ccm = ccm;
			this.dataLength = dataLength;
		}

		public InputStream getInputStream() {
			return is;
		}

		public ClientConnectionManager getClientConnectionManager() {
			return ccm;
		}

		public long getDataLength() {
			return dataLength;
		}
		
		
	}
}
