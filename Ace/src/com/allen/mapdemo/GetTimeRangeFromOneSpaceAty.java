package com.allen.mapdemo;

import com.allen.arguments.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetTimeRangeFromOneSpaceAty extends Activity {
	private EditText firstTimeEditor=null,lastTimeEditor=null;
	private EditText firstLatitudeEditor,secondLatitudeEditor,firstLongitudeEditor,secondLongitudeEditor;
	private Button sureBtn=null;
	String  firstTime,lastTime,firstLatitude,secondLatitude,firstLongitude,secondLongitude;
         @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.get_time_range_from_one_spacexml);
        	initViews();
        }
         
		private void initViews() {
			// TODO Auto-generated method stub
			firstTimeEditor=(EditText) findViewById(R.id.get_one_square_first_time_editor);
			lastTimeEditor=(EditText) findViewById(R.id.get_one_square_last_time_editor);
			
			firstLatitudeEditor=(EditText) findViewById(R.id.get_one_square_first_latitude_editor);
			secondLatitudeEditor=(EditText) findViewById(R.id.get_one_square_second_latitude_editor);
			
			firstLongitudeEditor=(EditText) findViewById(R.id.get_one_square_first_longitude_editor);
			secondLongitudeEditor=(EditText) findViewById(R.id.get_one_square_second_longitude_editor);
			
			sureBtn=(Button) findViewById(R.id.get_one_square_sureBtn);
			sureBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					getInputContent();
					if(checkInputContent())
					{
						Intent intent=getIntent();
						Bundle bundle=new Bundle();
						bundle.putString("firstTime", firstTime);
						bundle.putString("lastTime", lastTime);
						bundle.putString("firstLatitude", firstLatitude);
						bundle.putString("secondLatitude", secondLatitude);
						bundle.putString("firstLongitude", firstLongitude);
						bundle.putString("secondLongitude", secondLongitude);
						bundle.putString(Arguments.FINISH,Arguments.FINISH);
						intent.putExtras(bundle);
						setResult(Arguments.GET_TIME_AND_RANGE_FROM_ONE_SPACE, intent);
						GetTimeRangeFromOneSpaceAty.this.finish();
					}
					else Toast.makeText(GetTimeRangeFromOneSpaceAty.this, "ÊäÈëÓÐ´í............", Toast.LENGTH_LONG).show();
				}
			});
		}
		
		private void getInputContent()
		{
			firstTime=firstTimeEditor.getText().toString();
			lastTime=lastTimeEditor.getText().toString();
			firstLatitude=firstLatitudeEditor.getText().toString();
			secondLatitude=secondLatitudeEditor.getText().toString();
			firstLongitude=firstLongitudeEditor.getText().toString();
			secondLongitude=secondLongitudeEditor.getText().toString();
		}
		
		private boolean checkInputContent()
		{
			double firstLa,secondLa,firstLo,secondLo;
			firstLa=Double.parseDouble(firstLatitude);
			secondLa=Double.parseDouble(secondLatitude);
			firstLo=Double.parseDouble(firstLongitude);
			secondLo=Double.parseDouble(secondLongitude);
			if(firstLa>41||firstLa<38)
				return false;
			if(secondLa>41||secondLa<38)
				return false;
			if(firstLo>118||firstLo<115)
				return false;
			if(secondLo>118||secondLo<115)
				return false;
			return true;
			
		}
}
