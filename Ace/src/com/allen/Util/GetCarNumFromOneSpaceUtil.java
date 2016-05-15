package com.allen.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.allen.arguments.Arguments;
import com.allen.mapdemo.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class GetCarNumFromOneSpaceUtil {
     public static void getCarNumBySocket(final Context context,String[] arguments)
     {
    	    final String firstTime=arguments[0];
    	    final String lastTime=arguments[1];
    		final String firstPointLatitude=arguments[2];
    		final String secondPointLatitude=arguments[3];
    		final String firstPointLongitude=arguments[4];
    		final String secondPointLongitude=arguments[5];
    		
    	    final List<String> list=new ArrayList<String>();
    	 new Thread()
    	 {
    		 public void run() 
    		 {
    			 InetAddress address = null;
         		try 
         		{
         			address = InetAddress.getByName("192.168.191.1");
         		}
         		catch (UnknownHostException e)
         		{
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		}//获取服务器ip
         		
         		try {
					Socket socket=new Socket(address,8080);
					socket.setSoTimeout(3000);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					writer.println(Arguments.queryType_carnum_at_one_space);
					writer.println(firstTime);
					writer.println(lastTime);
					writer.println(firstPointLatitude);
					writer.println(secondPointLatitude);
					writer.println(firstPointLongitude);
					writer.println(secondPointLongitude);
					writer.println("end");
					Log.i("test from solink", "start reading");
					while(true)
					{
						String message=reader.readLine();
						Log.i("test from solink", message);
						if(message.equals("end"))
							break;
						list.add(message);
					}
					
//					Toast.makeText(context, "共有 "+list.get(0)+" 辆车", 2000).show();
					if(list.size()>0)
					{
						Log.i("test from solink", "start handler");
						//Handler handler=new Handler();
						Message msg=Message.obtain();
						msg.what=Arguments.show_dialog_by_handler;
						msg.arg1=Integer.parseInt(list.get(0));
					//	con.sendMessage(msg);
						((MainActivity)context).getMyHandler().sendMessage(msg);
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    	 }.start();
    	 
    	 
     }
}
