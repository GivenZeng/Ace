package com.allen.mapdemo;

import java.util.List;

import com.allen.Util.GetCarNumFromOneSpaceUtil;
import com.allen.Util.TrafficPathUtil;
import com.allen.arguments.Arguments;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	MapView mMapView = null;
	BaiduMap map = null;
	boolean isShowingCarPath = false;
   public MyDialogHandler myHandler=new MyDialogHandler(MainActivity.this);
	String[] arguments = new String[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// 获取地图控件引用
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
			if (isShowingCarPath == false) // 尚未显示轨迹
			{
				drawPath(); // 画轨迹
				item.setTitle("取消轨迹查询");
				isShowingCarPath = true;
			}

			else {
				cancelCarPath();
				item.setTitle("轨迹查询");
				isShowingCarPath = false;
			}
			break;
			////////////////////////////////////////////////////////////////////////////////////
		case R.id.carNum:
			getTimeAndLocationRangeInOneSpace();
			break;
			
		case R.id.trafficStream:
			break;

		case R.id.normallMode:
			map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		case R.id.satelliteMode:
			map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.trafficMode: // 交通模式
			if (map.isTrafficEnabled() == false) {
				map.setTrafficEnabled(true);
				item.setTitle("取消交通模式");
			} else {
				map.setTrafficEnabled(false);
				item.setTitle("交通模式");
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

	//
	public void drawPath() // 在地图上画轨迹：首先用户输入车号和时间，然后向客户端发送请求获取数据，画线
	{
		getCarNumAndTime();
	}

	// 将画出的轨迹清除
	public void cancelCarPath() {
		isShowingCarPath = false;
		if (mMapView == null)
			mMapView = (MapView) findViewById(R.id.bmapView);
		TrafficPathUtil.cancelPath(mMapView);
	}

	// 获取汽车的车号和时间，
	public void getCarNumAndTime() {
		Intent intent = new Intent(MainActivity.this, GetCarMessageActivity.class);
		startActivityForResult(intent, Arguments.GET_CARNUM_AND_TIME);
	}

    public MyDialogHandler getMyHandler()
    {
    	return myHandler;
    }



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Arguments.GET_CARNUM_AND_TIME) {
			Bundle bundle = data.getExtras();
			arguments[0] = bundle.getString("carNum");// 车号
			arguments[1] = bundle.getString("carTimeFirst");// 时间
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
			arguments[i++]=bundle.getString("firstTime");
			arguments[i++]=bundle.getString("lastTime");
			arguments[i++]=bundle.getString("firstLatitude");
			arguments[i++]=bundle.getString("secondLatitude");
			arguments[i++]=bundle.getString("firstLongitude");
			arguments[i++]=bundle.getString("secondLongitude");
			GetCarNumFromOneSpaceUtil.getCarNumBySocket(MainActivity.this, arguments);
		}
	}
}
