package com.example.and;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class ServerThread extends Thread {
	private Socket mSocket;
	private BufferedReader mBufferedReader;
	
	//退出命令
	private static final String exit="EXIT";
	private Set<Socket> allSockets;
	
	private Robot robot;

	public ServerThread(Socket socket) throws AWTException {
		robot = new Robot();
		this.mSocket=socket;
		try{
			//从socket中回去缓冲字符输入流，编码为UTF-8
			mBufferedReader=new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"UTF-8"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void pressKey(Robot robot, int keyvalue){
		robot.keyPress(keyvalue);
		//robot.delay(200);
		//robot.keyRelease(keyvalue);
	}
	
	public static void ReleaseKey(Robot robot){
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		
		//robot.delay(200);
		//robot.keyRelease(keyvalue);
	}
	
	public static void pressKeyWithShift(Robot robot, int keyvalue){
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}
	
	
	
	public void run(){
		try{
			while(true){
				//按行读取
				String str=mBufferedReader.readLine();
				if(exit.equals(str)){
					allSockets.remove(mSocket);
					mSocket.close();
					//让默认离开的消息发到所有和服务器相连的客户端
					sendMessageToAllClient("离开聊天室！");
					break;
				
				}System.out.println(str);
				//接收控制信息并转化为键盘响应
				if(str.contentEquals("1")){
					pressKey(this.robot, KeyEvent.VK_UP);
					
				}else if(str.contentEquals("3")){
					pressKey(this.robot,KeyEvent.VK_LEFT);
				}else if(str.contentEquals("4")){
					pressKey(this.robot,KeyEvent.VK_RIGHT);
				}else if(str.contentEquals("2")){
					pressKey(this.robot,KeyEvent.VK_DOWN);
				}else if(str.contentEquals("0")){
					ReleaseKey(this.robot);
				}
				
				//将获取的消息发到所有的和服务器端相连的客户端
				sendMessageToAllClient(str);	
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//奖消息发到所有和服务器端相连的客户端
	//@msg  消息字符串
	private void sendMessageToAllClient(String msg) {
		if(msg!=null && !"".equals(msg.trim())){
			//System.out.println(msg);
			String date = getDateByFormat(new Date(),"yyyy-MM-dd");
			System.out.println("获取到消息："+mSocket.getInetAddress()+"\t"+msg+"\t["+date+"]");
			/*for(Socket socket:allSockets){
				try{
					PrintWriter pw= new PrintWriter(socket.getOutputStream());
					pw.println(mSocket.getInetAddress()+"\t["+date+"\t");
					pw.println(msg);
					pw.flush();
				}catch(IOException e){
					e.printStackTrace();
				}
			}*/
		}
	}
	
	//
	public String getDateByFormat(Date strDate,String format){
		String curTime="";
		SimpleDateFormat ss=new SimpleDateFormat(format);
		curTime=ss.format(strDate);
		return curTime;
	}
	
	public static void main(String[] args){
		new TCPServer().init();
	}

}

