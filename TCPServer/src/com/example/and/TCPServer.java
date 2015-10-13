package com.example.and;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
	//监听端口号
	int port=15000;
	ServerSocket mServerSocket;
	//Socket服务器端相连接的Socket
	private Set<Socket> allSockets;
	//线程池用于线程的重用
	private ExecutorService mExecutorService;

	public TCPServer(){
		try{
			//服务器端socket创建
			mServerSocket=new ServerSocket(port);
			//初始化与服务器端相连接的所有Socket
			allSockets=new HashSet<Socket>();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	//启动服务器端的方法
	public void init(){
		try{
			//创建一个线程池
			mExecutorService=Executors.newCachedThreadPool();
			System.out.println("TCP服务器端已启动，监听端口："+port);
			//循环等待客户端连接
			while(true){
				Socket mSocket =mServerSocket.accept();
				//当有一个客户连接
				if(mSocket!=null){
					//获取当前日期
					String date=getDateByFormat(new Date(),"yyyy-MM-dd");
					System.out.println(mSocket.getInetAddress()+"加入"+"\t["+date+"]");
					allSockets.add(mSocket);
					//开启一个客户端线程，如果已经开启会重用
					mExecutorService.execute(new ServerThread(mSocket));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getDateByFormat(Date strDate, String format) {
		String curTime="";
		SimpleDateFormat ss=new SimpleDateFormat(format);
		curTime=ss.format(strDate);
		return curTime;
	}

	public static void main(String[] args){
		new TCPServer().init();
	}
}
