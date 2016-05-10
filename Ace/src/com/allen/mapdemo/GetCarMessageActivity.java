package com.allen.mapdemo;

import com.allen.arguments.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetCarMessageActivity extends Activity {
	 private EditText carNumEditor,carTimeFirstEditor,carTimeSecondEditor;
	 private Button sureBtn;
	 private Intent intent;
	 String carNum=null;
	 String carTimeFrist=null;
	 String carTimeSecond=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.car_message);
    	initViews();
    }
    
    private void initViews() {
		//���ڻ�ȡ�û��������Ϣ�Լ��Ƴ�
		carNumEditor=(EditText) findViewById(R.id.carNumEditor);
		carTimeFirstEditor=(EditText) findViewById(R.id.carTimeFirstEidtor);
		carTimeSecondEditor=(EditText) findViewById(R.id.carTimeSecondEidtor);
		sureBtn=(Button) findViewById(R.id.okBtn);
		sureBtn.setOnClickListener(new OnClickListener() {
			//�д�
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				carNum=carNumEditor.getText().toString();
				 carTimeFrist=carTimeFirstEditor.getText().toString();
			     carTimeSecond=carTimeSecondEditor.getText().toString();
			   //  Toast.makeText(GetCarMessageActivity.this,"��������ȷ����Ϣ", Toast.LENGTH_LONG).show();
				if(!checkMessage())
				{
					Toast.makeText(GetCarMessageActivity.this,"��������ȷ����Ϣ", Toast.LENGTH_LONG).show();
				}
				else
				{
					intent = GetCarMessageActivity.this.getIntent();
					Bundle bundle = new Bundle();
					bundle.putString("carNum", carNum);
					bundle.putString("carTimeFirst", carTimeFrist);
					bundle.putString("carTimeSecond", carTimeSecond);
					intent.putExtras(bundle);
					GetCarMessageActivity.this.setResult(Arguments.GET_CARNUM_AND_TIME, intent);
					GetCarMessageActivity.this.finish();
				}
			}
		});
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
    //�ж���Ϣ�Ƿ�Ϊ��
    private boolean checkMessage()
    {
//    	int num=Integer.parseInt(carNum);    //�����֪��ʲôԭ���д�
//    	if(num<1||num>11000)
//		return false;
    	if(carNum.equals(""))
    		return false;

    	if(carTimeFrist.equals("")||carTimeFrist==null)
    		return false;
    	if(carTimeSecond.equals("")||carTimeSecond==null)
    		return false;
    	return true;
    }
 
}

