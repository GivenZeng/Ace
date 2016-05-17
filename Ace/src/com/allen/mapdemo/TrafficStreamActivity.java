package com.allen.mapdemo;

import java.util.ArrayList;
import java.util.List;

import com.allen.arguments.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    	Log.i("trafficstream", " start init in trafficstreamactivity");
    	trafficStreamGraph=(TrafficStreamGraph) findViewById(R.id.trafficStreamGraph);
    	Bundle bundle=getIntent().getExtras();
    	ArrayList<CharSequence> timeAndCarnum=bundle.getCharSequenceArrayList(Arguments.timeAndCarnum);
    	List<String> list=new ArrayList<String>();
    	int length=timeAndCarnum.size();
    	Log.i("trafficstream", " start change charsequence to string in trafficstreamactivity");
    	for(int i=0;i<length;i++)
    	{
    		list.add(timeAndCarnum.get(i).toString());
    	}
    	Log.i("trafficstream","trafficstream "+"finish change charsequence to string");
    	trafficStreamGraph.setPoints(list);
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
   
}
