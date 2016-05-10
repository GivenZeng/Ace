package com.allen.Util;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;

import android.graphics.Color;
import android.util.Log;

public class DrawRectangleUtil {
	   public static OverlayOptions ooPolyline = null;
	   public static Polyline mPolyline = null;
	   public static BaiduMap map;
       public static boolean drawRectangle(BaiduMap baidumap,LatLng a,LatLng b )
       {   
    	   map=baidumap;
    	   List<Integer> colors = new ArrayList<Integer>();
    	   colors.add(Integer.valueOf(Color.GREEN));
    	   colors.add(Integer.valueOf(Color.GREEN));
    	   colors.add(Integer.valueOf(Color.GREEN));
    	   colors.add(Integer.valueOf(Color.GREEN));
    	   double a_lat,a_long,b_lat,b_long,temp;
    	   a_lat=a.latitude;
    	   a_long=a.longitude;
    	   b_lat=b.latitude;
    	   b_long=b.longitude;
    	   if(a_lat>b_lat)
    	   {
    		   temp=a_lat;
    		   a_lat=b_lat;
    		   b_lat=temp;
    	   }
    	   if(a_long>b_long)
    	   {
    		   temp=a_long;
    		   a_long=b_long;
    		   b_long=temp;
    	   }
    	   List<LatLng> latlngs = new ArrayList<LatLng>();
    	   latlngs.add(new LatLng(a_lat, a_long));
    	   latlngs.add(new LatLng(b_lat,a_long));
    	   latlngs.add(new LatLng(b_lat, b_long));
    	   latlngs.add(new LatLng(a_lat, b_long));
    	   latlngs.add(new LatLng(a_lat, a_long));
    	   ooPolyline = new PolylineOptions().width(10).colorsValues(colors).points(latlngs);
    	   mPolyline = (Polyline) map.addOverlay(ooPolyline);
			Log.i("test", "finish drawing rectangle");
			return true;
       }
       
       
       public static boolean drawRectangle(BaiduMap map,String[] gpsPoints)
       {
    	   double a=Double.parseDouble(gpsPoints[0]);
    	   double b=Double.parseDouble(gpsPoints[1]);
    	   double c=Double.parseDouble(gpsPoints[2]);
    	   double d=Double.parseDouble(gpsPoints[3]);
    	   CoordinateConverter converter = new CoordinateConverter();
			converter.from(CoordType.GPS);
			LatLng e,f;
			e=new LatLng(a, b);
			f=new LatLng(c, d);
			converter.coord(e);
			e=converter.convert();
			converter.coord(f);
			f=converter.convert();
    	   return drawRectangle(map,e,f );
       }
       
       public static void cancelRectangle()
       {
    		mPolyline.remove();
    		mPolyline = null;
       }
}
