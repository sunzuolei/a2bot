package com.rp;


import java.io.PrintWriter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	//连接服务器IP地址及端口号
    EditText ipaddr0 = null;
    EditText ipaddr1 = null;
    EditText ipaddr2 = null;
    EditText ipaddr3 = null;
    EditText mport = null;
    int port = 0;
    //登陆界面信息栏
    TextView msgText = null;
    //
    boolean flag = false;
    //连接用字符串ip地址
    String ipconfig = "0.0.0.0";
    
    //
    MyConnect connect;
    //
    DirectionMonitor dirmon;
    //
    MySurfaceView msf;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//跳转到连接页面
		setContentView(R.layout.main1);
		//控件通过id对应xml
		final Button sendBtn = (Button)findViewById(R.id.sendBtn);
		ipaddr0 = (EditText)findViewById(R.id.ipaddr0);
		ipaddr1 = (EditText)findViewById(R.id.ipaddr1);
		ipaddr2 = (EditText)findViewById(R.id.ipaddr2);
		ipaddr3 = (EditText)findViewById(R.id.ipaddr3);
		mport = (EditText)findViewById(R.id.port);
		msgText = (TextView)findViewById(R.id.msgText);
		
		//发送消息
    	sendBtn.setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			try{
    				//取得编辑框中输入的内容
    				int ip0,ip1,ip2,ip3;
    				String msg = null;
    				ip0 = Integer.valueOf(ipaddr0.getText().toString());
    				ip1 = Integer.valueOf(ipaddr1.getText().toString());
    				ip2 = Integer.valueOf(ipaddr2.getText().toString());
    				ip3 = Integer.valueOf(ipaddr3.getText().toString());
    				port = Integer.valueOf(mport.getText().toString());
    				if(ip0 >= 0 && ip0 < 256 &&
    				   ip1 >= 0 && ip1 < 256 &&
    				   ip2 >= 0 && ip2 < 256 &&
    				   ip3 >= 0 && ip3 < 256) {
    					msg = String.valueOf(ip0) + "." + String.valueOf(ip1) + "." + String.valueOf(ip2) + "." +String.valueOf(ip3);
    					ipconfig = msg;
    					msgText.setText("连接"+msg);
    					connect = new MyConnect(ipconfig, port);
    					connect.run();
    					//if(connect.ifConnected()) {
    						msgText.setText("连接成功！");
    						//connect.sendTest();
    						Log.v(TAG, "Go to my surfece view!");
    						gotoLayout2();
    						Log.v(TAG, "Start to run Direction Monitor！");
    						dirmon = new DirectionMonitor(msf, connect);
    						Thread th = new Thread(dirmon);
    						th.start();
    					//}
    					
    				} else {
    					Log.v(TAG, "Ip address is out of range");
    					msgText.setText("ip超出范围");
    				}
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	});
    	
    	
	}
	
	private void gotoLayout2(){
		//跳转到主界面
		setContentView(msf = new MySurfaceView(this));
	}
	

}