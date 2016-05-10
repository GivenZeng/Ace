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
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;

import android.R.string;
import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.util.Log;
import android.view.ViewGroupOverlay;
import android.widget.Toast;

public class TrafficPathUtil {
	public static OverlayOptions ooPolyline = null;
	public static Polyline mPolyline = null;
	public static BaiduMap map;

	public static void drawPath(BaiduMap map, String[] carMessageAndTime) {
		TrafficPathUtil.map = map;
		drawPathThroughSocket(carMessageAndTime);

	}

	// private static List<LatLng> request(String[] carMessageAndTime)
	// {
	// //向服务端传送查询类型、车号、起始时间和结束时间
	// List<LatLng> latlngs =null;
	// latlngs=getPathThroughSocket(carMessageAndTime);
	// return latlngs;
	// }
	//

	private static void drawPathThroughSocket(String[] carMessageAndTime) {
		// TODO Auto-generated method stub
		final List<LatLng> latlngs = new ArrayList<LatLng>();
		// 开启新现线程来通讯
		final String carNum = carMessageAndTime[0];
		final String carTimeStart = carMessageAndTime[1];
		final String carTimeSecond = carMessageAndTime[2];
		new Thread() {
			public void run() {
				InetAddress address = null;
				try {
					address = InetAddress.getByName("192.168.191.1");
				}
				catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 获取服务器ip

				try {
					Socket socket = new Socket(address, 8080);
					socket.setSoTimeout(3000);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					// 下面没错
					writer.println(Arguments.queryType_road);
					writer.println(carNum);
					writer.println(carTimeStart);
					writer.println(carTimeSecond);
					writer.println("end"); // 传查询参数给服务端
					Log.i("test", "finish translate argument to the server");
					// 获取信息
					while (true) {
						String latitude = reader.readLine();
						if (latitude.equals("end")) // 当传入end的时候，跳出循环
						{
							System.out.println("query over");
							break;
						}
						latlngs.add(new LatLng(Double.parseDouble(latitude), Double.parseDouble(reader.readLine())));
					}
					Log.i("test", "finish get argument from server");
					// 将gps坐标转为百度坐标
					CoordinateConverter converter = new CoordinateConverter();
					converter.from(CoordType.GPS);
					for (int i = 0; i < latlngs.size(); i++) {
						converter.coord(latlngs.get(i));
						latlngs.set(i, converter.convert());
					}
					Log.i("test", "finish convert gps to baidu latlng");
					// 开始画
					List<Integer> colors = new ArrayList<Integer>();
					int length = latlngs.size();
					// 构建分段颜色索引数组
					for (int i = 1; i <= length - 1; i++) {
						colors.add(Integer.valueOf(Color.GREEN)); // 绿色
					}
					Log.i("test", "finish add color");
					ooPolyline = new PolylineOptions().width(10).colorsValues(colors).points(latlngs);
					// 添加在地图中
					mPolyline = (Polyline) map.addOverlay(ooPolyline);
					Log.i("test", "finish drawing");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	public static void cancelPath(MapView mapView) {
		// BaiduMap map=mapView.getMap();
		mPolyline.remove();
		mPolyline = null;

	}

}
