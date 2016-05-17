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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen.arguments.Arguments;
import com.allen.mapdemo.MainActivity;
import com.allen.mapdemo.TrafficStreamActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class GetTrafficStreamFromTwoSpaceUtil {
      public static ArrayList<CharSequence> list=null;
      public static  List<CharSequence> getTrafficStreamFromTwoSpace(final Context context,final Map<String, String> hashmap)
      {
    	  list=new ArrayList<CharSequence>();
    	  new Thread()
    	  {
    		  @Override
    		public void run() {
    			// TODO Auto-generated method stub
    			  InetAddress address = null;
    			  try {
					address = InetAddress.getByName("192.168.191.1");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
				try {
					  Socket socket = new Socket(address,8080);
					socket.setSoTimeout(3000);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					writer.println(Arguments.queryType_trafficStream_between_two);
					writer.println(hashmap.get(Arguments.firstTime));
					writer.println(hashmap.get(Arguments.secondTime));
					writer.println(hashmap.get(Arguments.firstPlaceFirstLatitude));
					writer.println(hashmap.get(Arguments.firstPlaceSceondLatitude));
					writer.println(hashmap.get(Arguments.firstPlaceFirstLongitude));
					writer.println(hashmap.get(Arguments.firstPlaceSecondLongitude));
					
					writer.println(hashmap.get(Arguments.secondPlaceFirstLatitude));
					writer.println(hashmap.get(Arguments.secondPlaceSceondLatitude));
					writer.println(hashmap.get(Arguments.secondPlaceFirstLongitude));
					writer.println(hashmap.get(Arguments.secondPlaceSecondLongitude));
					writer.println("end");
					while(true)
					{
						String time=reader.readLine();//Èç7£¬8£¬9
						if(time.equals("end"))
							break;
						
						else 
						{
							String num=reader.readLine();
							list.add(time);
							list.add(num);
							//map.put(time,num);
							Log.i("trafficstream","trafficstream "+time+"h  "+num);
						}
					}
					Message message=Message.obtain();
					Bundle bundle=new Bundle();
					bundle.putCharSequenceArrayList(Arguments.timeAndCarnum, list);
					message.what=Arguments.SHOW_TRAFFIC_STREAM_BY_HANDLER;
					message.setData(bundle);
					((MainActivity)context).getMyHandler().sendMessage(message);
					Log.i("traffic stream ", "trafficstream"+"finish send message to handler");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    		}
    	  }.start();
    	  return getList();
      }
      
      public static List<CharSequence> getList()
      {
    	  return list;
      }
}
