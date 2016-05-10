package com.allen.mapdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class TrafficStreamGraph extends View {
	private float x=20;  //������λ��
	private float y=100;
	private float[] carNums=null;   //������Ŀ

	//�������췽��ȱһ����
	public TrafficStreamGraph(Context context) {
		super(context);
		x=20;
    	y=100;
		// TODO Auto-generated constructor stub
	}
	
    public  TrafficStreamGraph(Context context, AttributeSet attrs)//Ĭ�ϵĲ����ļ����õ������������Ĺ��췽��
	    {
	    	super( context, attrs );
	    	x=20;
	    	y=100;
	    	this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	    }

	    	 

	public TrafficStreamGraph(Context context, AttributeSet attrs, int defStyle)
	  {
	   super( context, attrs, defStyle );
	   x=20;
    	y=100;
	   this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	  }
     
	
	//���ó�����
    public void setPoints(float[]points)
    {
    	this.carNums=points;
    	this.invalidate();
    }
    //�ı�λ��
    public void setPositon(float x,float y)
    {
    	this.x=x;
    	this.y=y;
    	this.invalidate();   //���ı�����ʱ�ػ�
    }
    
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
    	Paint paint=new Paint();
    	//����������ͼƬ
      	Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(),R.drawable.zhoutime);
        if (bitmap==null)  return;
//    	canvas.drawBitmap(bitmap,x, y,paint);
    
    	if(bitmap.isRecycled())
    	{
    		bitmap.recycle();
    	}
    	//����δ��ȡ����ʱ��������
    	if(carNums==null) return;
    	//����
    	else
    	{
    		int width=bitmap.getWidth();      //������Ŀ��͸�
        	int heigth=bitmap.getHeight();
        	
        	int gapOfX=(width-105)/8;    //ÿ��ĳ������ְ˸���
        	
        	paint.setColor(Color.BLACK);   //������ɫ
        	paint.setDither(true);       //ͼ�񶼶�
        	paint.setStrokeWidth((float) 3.0);    //�ʴ���С
        	paint.setAntiAlias(true);  //�����
        	paint.setStyle(Style.STROKE);         //ȱ�پͻ�������??????
        	
        	Path path=new Path();
        	
            int length=carNums.length; //��ȡ���ȣ�����������8
           // if(length>0)  path.moveTo((float)20, 630-carNums[0]);    //0ʱ
            if(length>0)  path.moveTo((float)33,1002-carNums[0]);    //0ʱ
            for(int i=1;i<length;i++)
            {
            	path.lineTo((float) (gapOfX*i+33), 1002-carNums[i]);  //i*3ʱ
            }
            canvas.drawPath(path, paint);
    	}
    	
	}
}