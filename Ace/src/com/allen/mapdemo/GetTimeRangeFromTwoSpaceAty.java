package com.allen.mapdemo;

import com.allen.arguments.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GetTimeRangeFromTwoSpaceAty extends Activity {
	private String[] time = new String[2];
	private String[] firstPlace = new String[4];
	private String[] secondPlace = new String[4];
    private Button sureBtn=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_time_range_from_two_space);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
           sureBtn=(Button) findViewById(R.id.get_time_range_from_two_space_sure_btn);
           sureBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time[0]=((EditText)findViewById(R.id.get_time_range_from_two_space_firsttime)).getText().toString();
				time[1]=((EditText)findViewById(R.id.get_time_range_from_two_space_lasttime)).getText().toString();
				firstPlace[0]=((EditText)findViewById(R.id.get_time_range_from_two_space_first_place_firstlatitude)).getText().toString();
				firstPlace[1]=((EditText)findViewById(R.id.get_time_range_from_two_space_first_place_secondlatitude)).getText().toString();
				firstPlace[2]=((EditText)findViewById(R.id.get_time_range_from_two_space_first_place_firstlongitude)).getText().toString();
				firstPlace[3]=((EditText)findViewById(R.id.get_time_range_from_two_space_first_place_secondlongitude)).getText().toString();
				
				secondPlace[0]=((EditText)findViewById(R.id.get_time_range_from_two_space_second_place_firstlatitude)).getText().toString();
				secondPlace[1]=((EditText)findViewById(R.id.get_time_range_from_two_space_second_place_secondlatitude)).getText().toString();
				secondPlace[2]=((EditText)findViewById(R.id.get_time_range_from_two_space_second_place_firstlongitude)).getText().toString();
				secondPlace[3]=((EditText)findViewById(R.id.get_time_range_from_two_space_second_place_secondlongitude)).getText().toString();
				Intent intent=GetTimeRangeFromTwoSpaceAty.this.getIntent();
				Bundle bundle=new Bundle();
				bundle.putString(Arguments.firstTime,time[0]);
				bundle.putString(Arguments.secondTime,time[1]);
				
				bundle.putString(Arguments.firstPlaceFirstLatitude,firstPlace[0] );
				bundle.putString(Arguments.firstPlaceSceondLatitude, firstPlace[1]);
				bundle.putString(Arguments.firstPlaceFirstLongitude, firstPlace[2]);
				bundle.putString(Arguments.firstPlaceSecondLongitude, firstPlace[3]);
				
				bundle.putString(Arguments.secondPlaceFirstLatitude, secondPlace[0]);
				bundle.putString(Arguments.secondPlaceSceondLatitude, secondPlace[1]);
				bundle.putString(Arguments.secondPlaceFirstLongitude, secondPlace[2]);
				bundle.putString(Arguments.secondPlaceSecondLongitude, secondPlace[3]);
				//bundle.putString(Arguments.FINISH,Arguments.FINISH);
				intent.putExtras(bundle);
				setResult(Arguments.GET_TIME_RANGE_FROM_TWO_SPACE, intent);
				GetTimeRangeFromTwoSpaceAty.this.finish();
				
			}
		});
	}
}
