package com.universal.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.universal.commen.Define;


/**
 * 数字签名加密
 */
public class EncryptUtil {

	// md5加密
	public static String md5Encrypt(HashMap<String, String> params) {
		String encryptText = encryptText(params);
		return encrypt(encryptText, "md5");
	}

	// sha1加密
	public static String shaEncrypt(HashMap<String, String> params) {
		String encryptText = encryptText(params);
		return encrypt(encryptText, "sha-1");
	}

	private static String encryptText(HashMap<String, String> params) {
		List<Map.Entry<String, String>> paramList = new ArrayList<Map.Entry<String, String>>(params.entrySet());

		// 排序
		Collections.sort(paramList, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareToIgnoreCase(o2.getKey());
			}
		});

		StringBuilder strBuilder = new StringBuilder();

		for (int i = 0; i < paramList.size(); i++) {
			Entry<String, String> entry = paramList.get(i);
			String value = entry.getValue();
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			strBuilder.append(String.format("%1$s=%2$s", entry.getKey(), value));
			if (i != paramList.size() - 1) {
				strBuilder.append("&");
			}
		}

		return strBuilder.toString() + Define.SECTET_CODE;
	}

	/**
	 * md5或者sha1加密
	 * 
	 * @param encryptText
	 *            要加密的内容
	 * @param encryptType
	 *            加密算法名称：md5或者sha1，不区分大小写
	 */
	public static String encrypt(String encryptText, String encryptType) {
		if (encryptText == null || "".equals(encryptText.trim())) {
		}
		if (encryptType == null || "".equals(encryptType.trim())) {
			encryptType = "sha-1";
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
			messageDigest.reset();
			messageDigest.update(encryptText.getBytes("UTF8"));
			byte s[] = messageDigest.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
