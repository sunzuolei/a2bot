package com.rp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class MyConnect implements Runnable{
	
	protected static final String TAG = "MyConnect";
	//客户端连接Socket
    Socket mSocket = null;
    //输入流
    BufferedReader mBufferedReader = null;
    //输出流
    PrintWriter mPrintWriter= null;
    //ipaddress
    String ipconfig = "0.0.0.0";
    //port
    int mport = 15000;
    //连接成功flag
    boolean issuccess = false;
    
    public MyConnect(String ipaddr, int port){
    	this.ipconfig = ipaddr;
    	this.mport = port;
    }
    
    

	public void run() {
		// TODO Auto-generated method stub
    	try {
    		Log.v(TAG,ipconfig + ":"+mport);
    		mSocket = new Socket();
    		mSocket.connect(new InetSocketAddress(ipconfig, mport), 5000);  		
    		//取得输入或者输出流
    		mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"GB2312"));
    		mPrintWriter = new PrintWriter(mSocket.getOutputStream(),true);
    		//记录连接成功
    		Log.i(TAG, "Connect successfully!");
    		issuccess = true;
    		//msgText.setText("连接成功！");
    		//sendBtn.setTag("进入");
    	} catch (SocketTimeoutException aa) {
    		
    		//记录连接失败
    		//msgText.setText("连接失败！");
    		Log.w(TAG, "Connect failed!");
    		issuccess = false;
    	} catch(Exception e){
    		//记录连接失败
    		//msgText.setText("连接失败！");
    		Log.w(TAG, "Connect failed!");
    		issuccess = false;
    		e.printStackTrace(); 
    	}

    	//接收视频代码应该写在下方！！！！！！
    	
	}

	//返回连接结果
	public boolean ifConnected() {
		return issuccess;
	}
	
	
	//传输方向数据
	public void sendDirection(String dir) {
		try{
			//msgText.append("上");
			mPrintWriter.println(dir);
			//mPrintWriter.println();
			mPrintWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//数据传输测试
	public void sendTest() {
		try{
			//msgText.append("上");
			mPrintWriter.println("收到来自手机发来的信息！");
			mPrintWriter.println();
			mPrintWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
}
