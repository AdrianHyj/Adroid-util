package com.universal.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.util.EncodingUtils;

import com.universal.mainApplication.MainApplication;

import android.text.TextUtils;


public class StringUtil {
	private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

	
	/**
	 * 比较程序版本号与升级包版本号的大小,如果程序版本号更高,则返回1, 相等则返回0, 小于则返回-1
	 * 
	 */
	public static int compareVersion(String appVer, String updateVer) {
		if (appVer.equals(updateVer))
			return 0;
		String[] appVerNum = appVer.split("\\.");
		String[] updateVerNum = updateVer.split("\\.");
		for (int i = 0; i < Math.min(appVerNum.length, updateVerNum.length); ++i) {
			if (Integer.parseInt(appVerNum[i]) > Integer.parseInt(updateVerNum[i]))
				return 1;
			else if (Integer.parseInt(appVerNum[i]) < Integer.parseInt(updateVerNum[i]))
				return -1;
		}
		return appVerNum.length > updateVerNum.length ? 1 : -1;
	}
	/**
	 * 判断String数组包含某一string
	 * 
	 * @param strs
	 * @param str
	 * @return
	 */
	public static boolean isHave(String[] strs, String str) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断str以string数组某一项开头
	 * 
	 * @param strs
	 * @param str
	 * @return
	 */
	public static boolean isStartWith(String[] strs, String str) {
		if (str == null || strs == null) {
			return false;
		}
		for (int i = 0; i < strs.length; i++) {
			if (str.startsWith(strs[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析一个带 token 分隔符的字符串，这个方法的效率比直接调用String的split()方法快大约1倍。
	 * 
	 * @param tokenedStr
	 * @param token
	 * @return String[]
	 */
	public static String[] splitString(String tokenedStr, String token) {
		String[] ids = null;
		if (tokenedStr != null) {
			StringTokenizer st = new StringTokenizer(tokenedStr, token);
			final int arraySize = st.countTokens();
			if (arraySize > 0) {
				ids = new String[arraySize];
				int counter = 0;
				while (st.hasMoreTokens()) {
					ids[counter++] = st.nextToken();
				}
			}
		}
		return ids;
	}

	/**
	 * 把字符串数组组合成一个以指定分隔符分隔的字符串，并追加到给定的<code>StringBuilder</code>
	 * 
	 * @param strs
	 *            字符串数组
	 * @param seperator
	 *            分隔符
	 * @return
	 */
	public static void mergeString(String[] strs, String seperator, StringBuilder sb) {
		if (strs == null || strs.length == 0)
			return;
		for (int i = 0; i < strs.length; i++) {
			if (i != 0) {
				sb.append(seperator);
			}
			sb.append(strs[i]);
		}
	}

	public static String stringWithFormat(String str, Object... args) {
		str = String.format(str, args);
		return str;
	}

	/**
	 * 把字符串数组组合成一个以指定分隔符分隔的字符串。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param seperator
	 *            分隔符
	 * @return
	 */
	public static String mergeString(String[] strs, String seperator) {
		StringBuilder sb = new StringBuilder();
		mergeString(strs, seperator, sb);
		return sb.toString();
	}

	/**
	 * MessageEncrypt 文件后缀名小写
	 * 
	 * @param strFileName
	 * @return
	 */
	public static String lowerCaseExtension(String strFileName) {
		// 去掉文件后缀
		String strFileNameBody = stringByDeletingPathExtension(strFileName);
		// 得到文件后缀
		String strExt = pathExtension(strFileName).toLowerCase(Locale.US);
		strFileName = strFileNameBody + "." + strExt;
		return strFileName;
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
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 将文件名中的类型部分去掉。不含点
	 * 
	 * @param filename
	 *            文件名
	 * @return 去掉类型部分的结果
	 */
	public static String stringByDeletingPathExtension(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}
	
	public static String FileNamePathExtension(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(index, filename.length());
		} else {
			return "";
		}
	}


	/**
	 * strFolder/filename
	 * 
	 * @param strFolder
	 * @param filename
	 * @return
	 */
	public static String stringByAppendingPathComponent(String strFolder, String filename) {
		return strFolder + File.separator + filename;
	}

	/**
	 * trimExt.ext
	 * 
	 * @param trimExt
	 * @param ext
	 * @return
	 */
	public static String stringByAppendingPathExtension(String trimExt, String ext) {
		return trimExt + "." + ext;
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的名字部分
	 * @since 0.5
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLastIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLastIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到除去文件名部分的路径 实际上就是路径中的最后一个路径分隔符前的部分。
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getNameDelLastPath(String fileName) {
		int point = getPathLastIndex(fileName);
		if (point == -1) {
			return fileName;
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLastIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLastIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	public static final String toString(byte[] ba) {
		return toString(ba, 0, ba.length);
	}

	public static final String toString(byte[] ba, int offset, int length) {
		char[] buf = new char[length * 2];
		for (int i = 0, j = 0, k; i < length;) {
			k = ba[offset + i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeInvalidChar(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;＇,\\[\\].<>/?～！＠＃￥％……＆＊（）——＋｜｛｝【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static class StringComparator implements Comparator<String> {
		@Override
		public int compare(String object1, String object2) {
			return object1.compareTo(object2);
		}
	}

	/**
	 * 生成size位随机数
	 * 
	 * @param size
	 * @return
	 */
	public static String createRandomKey(int size) {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		Random random = new Random(); // 初始化随机数产生器
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	public static void Write(String fileName, String message) {

		try {
			FileOutputStream outSTr = null;
			try {
				outSTr = new FileOutputStream(new File(fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedOutputStream Buff = new BufferedOutputStream(outSTr);
			byte[] bs = message.getBytes();
			Buff.write(bs);
			Buff.flush();
			Buff.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String readFromAsset(String fileName) {
		String result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(MainApplication.getInstance().getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";

			while ((line = bufReader.readLine()) != null)
				result += line;

			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String Read(String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	public static long getFolderSize(java.io.File file) throws Exception {
		long size = 0;
		java.io.File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size / 1024;
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);

			if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {
					file.delete();
				} else {
					if (file.listFiles().length == 0) {
						file.delete();
					}
				}
			}
		}
	}

	public static boolean Unzip(String zipFile, String targetDir) {
		try {
			int BUFFER = 4096; 
			String strEntry; 

			BufferedOutputStream dest = null; 
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry; 

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
			e.printStackTrace();
			return true;
		}

	}
	
	 /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    public static String TruncateUrlPage(String strURL)
    {
    String strAllParam=null;
      String[] arrSplit=null;
     
      strURL=strURL.trim().toLowerCase();
     
      arrSplit=strURL.split("[?]");
      if(strURL.length()>1)
      {
          if(arrSplit.length>1)
          {
                  if(arrSplit[1]!=null)
                  {
                  strAllParam=arrSplit[1];
                  }
          }
      }
     
    return strAllParam;   
    }
    
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
    Map<String, String> mapRequest = new HashMap<String, String>();
   
      String[] arrSplit=null;
     
    String strUrlParam=URL;
    if(strUrlParam==null)
    {
        return mapRequest;
    }
      //每个键值为一组 www.2cto.com
    arrSplit=strUrlParam.split("[&]");
    for(String strSplit:arrSplit)
    {
          String[] arrSplitEqual=null;         
          arrSplitEqual= strSplit.split("[=]");
         
          //解析出键值
          if(arrSplitEqual.length>1)
          {
              //正确解析
              mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
             
          }
          else
          {
              if(arrSplitEqual[0]!="")
              {
              //只有参数没有值，不加入
              mapRequest.put(arrSplitEqual[0], "");       
              }
          }
    }   
    return mapRequest;   
    }

}
