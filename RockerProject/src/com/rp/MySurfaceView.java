package com.rp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	//固定摇杆背景圆形的X,Y坐标以及半径
	private int RockerCircleX = 300;
	private int RockerCircleY = 750;
	private int RockerCircleR = 200;
	//摇杆的X,Y坐标以及摇杆的半径
	private float SmallRockerCircleX = 300;
	private float SmallRockerCircleY = 750;
	private float SmallRockerCircleR = 70; 
	private double LimitR = 0;  
	private double tempRad = 0;

	//
	Context mainA = null;
	
	private int dir = 0;
	
	public MySurfaceView(Context context) {
		super(context);
		Log.v("Himi", "MySurfaceView");
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		th = new Thread(this);
		
		flag = true;
		th.start();
	}

	/***
	 * 得到两点之间的弧度
	 */
	public double getRad(float px1, float py1, float px2, float py2) {
		//得到两点X的距离
		float x = px2 - px1;
		//得到两点Y的距离
		float y = py1 - py2;
		//算出斜边长
		float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
		float cosAngle = x / xie;
		//通过反余弦定理获取到其角度的弧度
		float rad = (float) Math.acos(cosAngle);
		//注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE ) {
			LimitR = Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2) + Math.pow((RockerCircleY - (int) event.getY()), 2));
			// 当触屏区域不在活动范围内
			if (LimitR >= RockerCircleR && LimitR <= RockerCircleR + 200) {
				//得到摇杆与触屏点所形成的角度
				tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
				//保证内部小圆运动的长度限制
				getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
			} else if (LimitR <= RockerCircleR + 200)  {//如果小球中心点小于活动区域则随着用户触屏点移动即可
				SmallRockerCircleX = (int) event.getX();
				SmallRockerCircleY = (int) event.getY();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//当释放按键时摇杆要恢复摇杆的位置为初始位置
			SmallRockerCircleX = 300;
			SmallRockerCircleY = 750;
		}
		judgeDirection();
		return true;
	}

	/**
	 * 
	 * @param R
	 *            圆周运动的旋转点
	 * @param centerX
	 *            旋转点X
	 * @param centerY
	 *            旋转点Y
	 * @param rad
	 *            旋转的弧度
	 */
	public void getXY(float centerX, float centerY, float R, double rad) {
		//获取圆周运动的X坐标 
		SmallRockerCircleX = (float) ((R-20) * Math.cos(rad)) + centerX;
		//获取圆周运动的Y坐标
		SmallRockerCircleY = (float) ((R-20) * Math.sin(rad)) + centerY;
	}

	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE);
			//设置透明度
			paint.setColor(0x70000000);
			//绘制摇杆背景
			canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
			paint.setColor(0x70000000);
			//绘制摇杆
			canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, SmallRockerCircleR, paint);
			
			switch(dir){
			case 1:canvas.drawCircle(300, 100, SmallRockerCircleR, paint);break;
			case 2:canvas.drawCircle(300, 200, SmallRockerCircleR, paint);break;
			case 3:canvas.drawCircle(200, 150, SmallRockerCircleR, paint);break;
			case 4:canvas.drawCircle(400, 150, SmallRockerCircleR, paint);break;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {

			}
		}
	}

	public int judgeDirection(){
		
		double Rad = tempRad;
		//负弧度转正弧度变量
		if (tempRad < 0){
			Rad = Rad + 2*Math.PI;
		}
		
		if (SmallRockerCircleX==300 && SmallRockerCircleY==750){
			dir = 0;
		} else if (tempRad > -Math.PI * 0.75 && tempRad <= -Math.PI/4){//上
			dir = 1;
		} else if (tempRad > Math.PI/4 && tempRad <= Math.PI * 0.75){//下
			dir = 2;
		} else if (tempRad > -Math.PI/4 && tempRad < Math.PI * 0.75){//右
			dir = 4;
		} else if (Rad > Math.PI *0.75 && Rad <= Math.PI * 1.25){//左
			dir = 3;
		}else {//回到中心
			
		}
		
		return dir;
			
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			draw();
			try {
				Thread.sleep(50);
			} catch (Exception ex) {
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("Himi", "surfaceChanged");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.v("Himi", "surfaceDestroyed");
	}
}