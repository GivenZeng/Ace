package com.allen.mapdemo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

public  class DrawLabelOnMap {
	 public static List<Polyline> list=new ArrayList<Polyline>();
     public static void  addLabel(BaiduMap map,LatLng latlng)
     {
    	 BitmapDescriptor bitmap=BitmapDescriptorFactory.fromResource(R.drawable.map_label);
    	 OverlayOptions overlay=new MarkerOptions().position(latlng).icon(bitmap);
         Polyline polyline=(Polyline) map.addOverlay(overlay);
    	 list.add(polyline);
     }
     
     public static void removeAllLabel()
     {
    	for(int i=0;i<list.size();i++)
    	{
    		list.get(i).remove();
    	}
    	for(int i=0;i<list.size();i++)
    	{
    		list.remove(i);
    	}
     }
}

