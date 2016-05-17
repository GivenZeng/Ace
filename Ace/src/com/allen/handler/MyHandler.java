package com.allen.handler;


import java.util.ArrayList;
import java.util.List;

import com.allen.arguments.Arguments;
import com.allen.mapdemo.TrafficStreamActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyHandler extends Handler {
	private Context context;
	public MyHandler(Context con) {
		// TODO Auto-generated constructor stub
		context=con;
	}
         public void handleMessage(android.os.Message msg) 
         {
        	 if(msg.what==Arguments.show_dialog_by_handler)
        	 {
        		 Log.i("test from solink", "start show dialog in myhandler");
        		 int carnum=msg.arg1;
        		 AlertDialog ad=new AlertDialog.Builder(context).create();
					ad.setTitle("查询结果");
					ad.setMessage("一共查得  "+carnum+" 辆车");
					ad.setButton(DialogInterface.BUTTON_POSITIVE, "确定",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ad.show();
        	 }
        	 if(msg.what==Arguments.SHOW_TRAFFIC_STREAM_BY_HANDLER)
        	 {
        		 Bundle bundle=msg.getData();
        		 ArrayList<CharSequence> timeAndCarnum=bundle.getCharSequenceArrayList(Arguments.timeAndCarnum);
        		 Log.i("trafficstream", " show charsequenct "+timeAndCarnum.toString());
//        		 List<String> list=new ArrayList<String>();
//        		 int length=timeAndCarnum.size();
//        		 for(int i=0;i<length;i++)
//        		 {
//        			 list.add(timeAndCarnum.get(i++).toString());
//        		 }
        		 Log.i("handler ", "finish get message in handler");
        		 Intent intent=new Intent(context,TrafficStreamActivity.class);
        		 intent.putExtras(bundle);
        		 context.startActivity(intent);
        	 }
         }
}
