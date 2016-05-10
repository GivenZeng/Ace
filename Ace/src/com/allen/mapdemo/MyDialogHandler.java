package com.allen.mapdemo;

import com.allen.arguments.Arguments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

public class MyDialogHandler extends Handler {
	private Context context;
	public MyDialogHandler(Context con) {
		// TODO Auto-generated constructor stub
		context=con;
	}
         public void handleMessage(android.os.Message msg) 
         {
        	 Log.i("test from solink", "start show dialog in myhandler");
        	 if(msg.what==Arguments.show_dialog_by_handler)
        	 {
        		 int carnum=msg.arg1;
        		 AlertDialog ad=new AlertDialog.Builder(context).create();
					ad.setTitle("��ѯ���");
					ad.setMessage("һ����� "+carnum+" ����");
					ad.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ad.show();
        	 }
         }
}
