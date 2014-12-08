package com.universal.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.universal.mainApplication.MainApplication;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.Toast;

public class SendEmailCommand extends Command {
	String subject;
	String content;
	String[] reciver;
	String[] files;

    public SendEmailCommand(Context context,String subject, String content ,String[] reciver) {
        super(context);
        this.context = context;
        this.subject = subject;
        this.content = content;
        this.reciver = reciver;
    }
    
    /**
	 * @param context
	 * @param subject
	 * @param content
	 * @param reciver
	 * @param files
	 * 
	 * @author yuelaiye
	 */
	public SendEmailCommand(Context context, String subject, String content, String[] reciver, String[] files) {
		super(context);
		this.context = context;
		this.subject = subject;
		this.content = content;
		this.reciver = reciver;
		this.files = files;
	}

    @Override
    public void execute() {
    	//EmailTools.sendEmail(subject, content);
    	if(files == null || files.length <= 0){
    		Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);  
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myIntent.setType("plain/text");  
            myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);  
            myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);  
            myIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);  
            context.startActivity(Intent.createChooser(myIntent, "mail"));  
    	}else{
    		Intent it = new Intent(Intent.ACTION_SEND_MULTIPLE, null);
			it.setType("message/rfc882");
			List<ResolveInfo> resInfo = MainApplication.getInstance().getPackageManager().queryIntentActivities(it, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

			if (!resInfo.isEmpty()) {
				List<Intent> targetedShareIntents = new ArrayList<Intent>();
				for (ResolveInfo info : resInfo) {
					Intent targeted = new Intent(Intent.ACTION_SEND_MULTIPLE);
					ActivityInfo activityInfo = info.activityInfo;

					if (activityInfo.packageName.contains("gm") || activityInfo.packageName.contains("mail") || activityInfo.name.contains("gm") || activityInfo.name.contains("mail")) {
						ArrayList<Parcelable> list = new ArrayList<Parcelable>();
						targeted.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						targeted.putExtra(Intent.EXTRA_EMAIL, reciver);
						targeted.putExtra(Intent.EXTRA_SUBJECT, subject);
						targeted.putExtra(Intent.EXTRA_TEXT, content);
						targeted.setType("message/rfc882");
						targeted.setPackage(activityInfo.packageName);
						if (files != null) {
							for (String file_path : files) {
								File file = new File(file_path);
								if (file.exists()) {
									list.add(Uri.fromFile(file));
								}
							}
						}
						if (list != null && list.size() > 0) {
							targeted.putExtra(Intent.EXTRA_STREAM, list);
						}
						targetedShareIntents.add(targeted);

					}
				}
				if (targetedShareIntents == null || targetedShareIntents.size() < 1) {
					Toast.makeText(context, "您还未安装邮箱相关软件", Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					context.startActivity(Intent.createChooser(targetedShareIntents.get(0), "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(context, "您还未安装邮箱相关软件", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "请先绑定您常用的邮箱", Toast.LENGTH_SHORT).show();
			}
		}
    }
}
