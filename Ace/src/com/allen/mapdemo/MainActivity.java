package com.allen.mapdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen.Util.DrawRectangleUtil;
import com.allen.Util.GetCarNumFromOneSpaceUtil;
import com.allen.Util.GetTrafficStreamFromTwoSpaceUtil;
import com.allen.Util.TrafficPathUtil;
import com.allen.arguments.Arguments;
import com.allen.handler.MyHandler;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	MapView mMapView = null;
	BaiduMap map = null;
	boolean isShowingCarPath = false;
   public MyHandler myHandler=new MyHandler(MainActivity.this);
	String[] arguments = new String[10];
	//public List<String> configuras=new ArrayList<String>();
	public Map<String, String> hashmap=new HashMap<String, String>();
   public boolean hasCheckCarnumFromOneSpace=false;
   public boolean hasCheckCarnumBetweenTwoSpace=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// ��ȡ��ͼ�ؼ�����
		initViews();
	}

	public void initViews() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		map = mMapView.getMap();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.carPath:
			if (isShowingCarPath == false) // ��δ��ʾ�켣
			{
				drawPath(); // ���켣
				item.setTitle("ȡ���켣��ѯ");
				isShowingCarPath = true;
			}

			else {
				cancelCarPath();
				item.setTitle("�켣��ѯ");
				isShowingCarPath = false;
			}
			break;
			////////////////////////////////////////////////////////////////////////////////////
		case R.id.carNum:
			if(hasCheckCarnumFromOneSpace)
			{
				item.setTitle("������ѯ");
				hasCheckCarnumFromOneSpace=false;
				DrawRectangleUtil.cancelRectangle();
			}
			else{
				getTimeAndLocationRangeInOneSpace();
				item.setTitle("��ѯ���");
				hasCheckCarnumFromOneSpace=true;
			}
			break;
			
		case R.id.trafficStreamOneSpace:
			break;
		case R.id.trafficStreamTwoSpace:
			if(hasCheckCarnumBetweenTwoSpace)
			{
				item.setTitle("������ѯ��������");
				hasCheckCarnumBetweenTwoSpace=false;
				DrawRectangleUtil.cancelRectangle();
			}
			else
			{
				hasCheckCarnumBetweenTwoSpace=true;
				getTimeAndLocationRangeInTwoSpace();
				item.setTitle("��ѯ���");
			}
			break;

		case R.id.normallMode:
			map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		case R.id.satelliteMode:
			map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.trafficMode: // ��ͨģʽ
			if (map.isTrafficEnabled() == false) {
				map.setTrafficEnabled(true);
				item.setTitle("ȡ����ͨģʽ");
			} else {
				map.setTrafficEnabled(false);
				item.setTitle("��ͨģʽ");
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	

	private void getTimeAndLocationRangeInOneSpace() {
		// TODO Auto-generated method stub
		Intent intent =new Intent(MainActivity.this,GetTimeRangeFromOneSpaceAty.class);
		startActivityForResult(intent, Arguments.GET_TIME_AND_RANGE_FROM_ONE_SPACE);
	}
	private void getTimeAndLocationRangeInTwoSpace() {
		// TODO Auto-generated method stub
		Intent intent =new Intent(MainActivity.this,GetTimeRangeFromTwoSpaceAty.class);
		startActivityForResult(intent, Arguments.GET_TIME_RANGE_FROM_TWO_SPACE);
	}
	public void drawPath() // �ڵ�ͼ�ϻ��켣�������û����복�ź�ʱ�䣬Ȼ����ͻ��˷��������ȡ���ݣ�����
	{
		getCarNumAndTime();
	}

	// �������Ĺ켣���
	public void cancelCarPath() {
		isShowingCarPath = false;
		if (mMapView == null)
			mMapView = (MapView) findViewById(R.id.bmapView);
		TrafficPathUtil.cancelPath(mMapView);
	}

	// ��ȡ�����ĳ��ź�ʱ�䣬
	public void getCarNumAndTime() {
		Intent intent = new Intent(MainActivity.this, GetCarMessageActivity.class);
		startActivityForResult(intent, Arguments.GET_CARNUM_AND_TIME);
	}

    public MyHandler getMyHandler()
    {
    	return myHandler;
    }



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Arguments.GET_CARNUM_AND_TIME) {
			Bundle bundle = data.getExtras();
			arguments[0] = bundle.getString("carNum");// ����
			arguments[1] = bundle.getString("carTimeFirst");// ʱ��
			arguments[2] = bundle.getString("carTimeSecond");
			if (mMapView == null)
				mMapView = (MapView) findViewById(R.id.bmapView);
			if (map == null)
				map = mMapView.getMap();
			Toast.makeText(MainActivity.this, arguments[0] + arguments[1] + arguments[2],
					Toast.LENGTH_LONG).show();
			TrafficPathUtil.drawPath(map, arguments);
			isShowingCarPath = true;
		}
		if(resultCode==Arguments.GET_TIME_AND_RANGE_FROM_ONE_SPACE)
		{
			int i=0;
			Bundle bundle = data.getExtras();
			arguments[i++]=bundle.getString("firstTime");//0
			arguments[i++]=bundle.getString("lastTime");//1
			arguments[i++]=bundle.getString("firstLatitude");//2
			arguments[i++]=bundle.getString("secondLatitude");//3
			arguments[i++]=bundle.getString("firstLongitude");//4
			arguments[i++]=bundle.getString("secondLongitude");//5
			String[] latlngs=new String[]{arguments[2],arguments[4],arguments[3],arguments[5]};
			if(DrawRectangleUtil.drawRectangle(map, latlngs))
			{
				hasCheckCarnumFromOneSpace=true;
			}
//			MenuItem item = (MenuItem) findViewById(R.id.carNum);//ʹ�û����
//			item.setTitle("��ѯ���");
			GetCarNumFromOneSpaceUtil.getCarNumBySocket(MainActivity.this, arguments);
		}
		if(resultCode==Arguments.GET_TIME_RANGE_FROM_TWO_SPACE)
		{
			int i=0;
			Bundle bundle=data.getExtras();
			hashmap.put(Arguments.firstTime, bundle.getString(Arguments.firstTime));
			hashmap.put(Arguments.secondTime, bundle.getString(Arguments.secondTime));
			
			hashmap.put(Arguments.firstPlaceFirstLatitude,bundle.getString(Arguments.firstPlaceFirstLatitude));
			hashmap.put(Arguments.firstPlaceSceondLatitude,bundle.getString(Arguments.firstPlaceSceondLatitude));
			hashmap.put(Arguments.firstPlaceFirstLongitude, bundle.getString(Arguments.firstPlaceFirstLongitude));
			hashmap.put(Arguments.firstPlaceSecondLongitude,bundle.getString(Arguments.firstPlaceSecondLongitude));
			
			hashmap.put(Arguments.secondPlaceFirstLatitude, bundle.getString(Arguments.secondPlaceFirstLatitude));
			hashmap.put(Arguments.secondPlaceSceondLatitude, bundle.getString(Arguments.secondPlaceSceondLatitude));
			hashmap.put(Arguments.secondPlaceFirstLongitude,bundle.getString(Arguments.secondPlaceFirstLongitude) );
			hashmap.put(Arguments.secondPlaceSecondLongitude,bundle.getString(Arguments.secondPlaceSecondLongitude));
			Log.i("hashmap","hash"+hashmap.toString());
			GetTrafficStreamFromTwoSpaceUtil.getTrafficStreamFromTwoSpace(MainActivity.this,hashmap);
		}
	}
}
