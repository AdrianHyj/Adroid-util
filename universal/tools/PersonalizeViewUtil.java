/**
 * 用于动态设置view或者其子view的字体颜色、大小以及背景颜色的工具类
 * @author yuelai.ye
 */
package com.universal.tools;


import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalizeViewUtil {

	/**
	 * 改变字体颜色
	 * 
	 * @param v
	 * @param fontSize
	 * @author yuelai.ye
	 */
	public static void setFontColor(View v, int fontColorValue) {
		if (v instanceof TextView) {
			((TextView) v).setTextColor(fontColorValue);
		} else if (v instanceof EditText) {
			((EditText) v).setTextColor(fontColorValue);
		} else if (v instanceof Button) {
			((Button) v).setTextColor(fontColorValue);
		} else if (v instanceof ViewGroup) {
			int vChildCount = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < vChildCount; i++) {
				View v1 = ((ViewGroup) v).getChildAt(i);
				setFontColor(v1, fontColorValue);
			}
		}
	}

	/**
	 * 改变背景颜色
	 * 
	 * @param v
	 * @param fontSize
	 * @author yuelai.ye
	 */
	public static void setBgColor(View v, int bgColorValue) {
		if (v instanceof TextView) {
			((TextView) v).setBackgroundColor(bgColorValue);
		} else if (v instanceof EditText) {
			((EditText) v).setBackgroundColor(bgColorValue);
		} else if (v instanceof Button) {
			((Button) v).setBackgroundColor(bgColorValue);
		} else if (v instanceof ViewGroup) {
			int vChildCount = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < vChildCount; i++) {
				View v1 = ((ViewGroup) v).getChildAt(i);
				setBgColor(v1, bgColorValue);
			}
		}
	}

}
