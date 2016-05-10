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
	private float x=20;  //坐标轴位置
	private float y=100;
	private float[] carNums=null;   //车流数目

	//三个构造方法缺一不可
	public TrafficStreamGraph(Context context) {
		super(context);
		x=20;
    	y=100;
		// TODO Auto-generated constructor stub
	}
	
    public  TrafficStreamGraph(Context context, AttributeSet attrs)//默认的布局文件调用的是两个参数的构造方法
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
     
	
	//设置车流量
    public void setPoints(float[]points)
    {
    	this.carNums=points;
    	this.invalidate();
    }
    //改变位置
    public void setPositon(float x,float y)
    {
    	this.x=x;
    	this.y=y;
    	this.invalidate();   //当改变数据时重画
    }
    
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
    	Paint paint=new Paint();
    	//设置坐标轴图片
      	Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(),R.drawable.zhoutime);
        if (bitmap==null)  return;
//    	canvas.drawBitmap(bitmap,x, y,paint);
    
    	if(bitmap.isRecycled())
    	{
    		bitmap.recycle();
    	}
    	//当尚未获取数据时，不画线
    	if(carNums==null) return;
    	//画线
    	else
    	{
    		int width=bitmap.getWidth();      //坐标轴的宽和高
        	int heigth=bitmap.getHeight();
        	
        	int gapOfX=(width-105)/8;    //每天的车流量分八个点
        	
        	paint.setColor(Color.BLACK);   //画笔颜色
        	paint.setDither(true);       //图像都懂
        	paint.setStrokeWidth((float) 3.0);    //笔触大小
        	paint.setAntiAlias(true);  //抗锯齿
        	paint.setStyle(Style.STROKE);         //缺少就画不出来??????
        	
        	Path path=new Path();
        	
            int length=carNums.length; //获取长度，如无意外是8
           // if(length>0)  path.moveTo((float)20, 630-carNums[0]);    //0时
            if(length>0)  path.moveTo((float)33,1002-carNums[0]);    //0时
            for(int i=1;i<length;i++)
            {
            	path.lineTo((float) (gapOfX*i+33), 1002-carNums[i]);  //i*3时
            }
            canvas.drawPath(path, paint);
    	}
    	
	}
}
