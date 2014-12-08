package com.universal.tools;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

	/**
	 * 时间戳转格式化日期
	 * 
	 * @param timestamp
	 *            单位毫秒
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String timestampToStr(long timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(timestamp * 1000);
		return sdf.format(date);
	}

	/**
	 * 格式化日期转时间戳
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return
	 */
	public static long strToTimestamp(String date) {
		return strToTimestamp(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期转时间戳
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static long strToTimestamp(String date, String format) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(format).parse(date).getTime() / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static String timeDistanceString(long unixtime) {
		final long MILLISEC = 1000;
		final long MILLISEC_MIN = 60 * MILLISEC;
		final long MILLISEC_HOUR = 60 * MILLISEC_MIN;
		final long MILLISEC_DAY = 24 * MILLISEC_HOUR;
		final long MILLISEC_MONTH = 30 * MILLISEC_DAY;
		final long MILLISEC_YEAR = 12 * MILLISEC_MONTH;
		long currentTime = System.currentTimeMillis();
		long timeDifference = Math.abs(currentTime - unixtime * 1000);

		/*
		 * 今年之前
		 */
		if (timeDifference / MILLISEC_YEAR > 1) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = dateFormat.format(new Date(unixtime * 1000));
			return dateString;
		}

		/*
		 * 今年
		 */
		if (timeDifference / MILLISEC_DAY > 1 && timeDifference / MILLISEC_YEAR <= 1) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
			String dateString = dateFormat.format(new Date(unixtime * 1000));
			return dateString;
		}

		/*
		 * 昨天
		 */
		if (timeDifference / MILLISEC_DAY == 1) {
			return "昨天";
		}

		/*
		 * 1天内
		 */
		if (timeDifference / MILLISEC_HOUR >= 1 && timeDifference / MILLISEC_DAY < 1) {
			return String.format(Locale.US, "%d小时前", timeDifference / MILLISEC_HOUR);
		}

		/*
		 * 1小时内
		 */
		if (timeDifference / MILLISEC_MIN >= 1 && timeDifference / MILLISEC_HOUR < 1) {
			return String.format(Locale.US, "%d分钟前", timeDifference / MILLISEC_MIN);
		}

		return "刚刚";

	}

	/**
	 * 把时间转换为秒
	 * 
	 * @param dateString
	 * @return
	 */
	public static String dateToSecond(String dateString) {
		String count = dateString.trim().substring(0, dateString.length() - 1);
		String unit = dateString.trim().substring(dateString.length() - 1, dateString.length());
		if (unit.equals("周")) {
			if (count.equals("一")) {
				return String.valueOf(7 * 24 * 60 * 60);
			}

			if (count.equals("二")) {
				return String.valueOf(2 * 7 * 24 * 60 * 60);
			}
		}

		if (unit.equals("日")) {
			return String.valueOf(Integer.parseInt(count) * 24 * 60 * 60);
		}

		return null;
	}

	public static String formatDay(int day) {
		if (day == 7) {
			return "一周";
		} else if (day == 14) {
			return "两周";
		} else {
			return day + "天";
		}
	}

	public static String formatLongToTimeStr(Long l) {
		String str = "";
		int hour = 0;
		int minute = 0;
		int second = 0;
		second = l.intValue() / 1000;
		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}

		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}

		String strtime = "";
		if (hour != 0) {
			strtime = hour + ":" + minute + ":" + second;
		} else {
			strtime = minute + ":" + (second>=10?second:"0"+second);
		}
		return strtime;
	}
}
