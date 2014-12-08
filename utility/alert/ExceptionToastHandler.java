package com.universal.alert;


import com.universal.mainApplication.MainApplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ExceptionToastHandler {
	private static ExceptionToastHandler instance;

	private ExceptionToastHandler() {
		super();
		instance = this;
	}
	
	public static ExceptionToastHandler getInstance() {
		return instance;
	}
	
	public static void init() {
		if (instance == null) {
			instance = new ExceptionToastHandler();
		}
	}
	
	@SuppressLint("HandlerLeak") 
	private Handler exceptionHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			AlertToast.showAlertText(msg.getData().getString("ErrorText"), MainApplication.getInstance());
		}
		
	};
	
	public void showFailObject(Object object){
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putString("ErrorText", "api fail");
		bundle.putInt("ErrorType", 0);
		msg.setData(bundle);
		exceptionHandler.sendMessage(msg);
	}
	
	public void showErrorText(String string){
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putString("ErrorText", string);
		bundle.putInt("ErrorType", 1);
		msg.setData(bundle);
		exceptionHandler.sendMessage(msg);
	}
	
}
