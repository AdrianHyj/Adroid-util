package com.universal.alert;

import android.content.Context;
import android.widget.Toast;

public class AlertToast {
	
	public static void showAlertText(String alertText, Context context) {
		Toast.makeText(context, alertText, Toast.LENGTH_LONG).show();
	}
	
}
