package com.allen.mapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class TrafficStreamActivity extends Activity {
	private TrafficStreamGraph trafficStreamGraph=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	setContentView(R.layout.traffic_stream);
    	initViews();
    }
    
    private void initViews() {
		// TODO Auto-generated method stub
    	trafficStreamGraph=(TrafficStreamGraph) findViewById(R.id.trafficStreamGraph);
//    	float[] points={0,50,150,300,500,400,500,450,100};
//        trafficStreamGraph.setPoints(points);
	}
    
    private void manipulate()
    {
    	Intent intent=this.getIntent();
    	String[] messages=null;
    	
    }
    //获取数据后要调用
    public void setPoints(float[]points)
    {
    	trafficStreamGraph.setPoints(points);
    }
    public void request(String[] messgage)
    {
    	//使用socket
    	float[] carNums=null;
        trafficStreamGraph.setPoints(carNums);
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	getMenuInflater().inflate(R.menu.traffic_stream_menu, menu);
		return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
        int id=item.getItemId();
        if(id==R.id.trafficStreamBackItem)
        {
        	TrafficStreamActivity.this.finish();
        }
    	return super.onOptionsItemSelected(item);
    	
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
}
