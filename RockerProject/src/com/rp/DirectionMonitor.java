package com.rp;

public class DirectionMonitor implements Runnable {

	//
	MySurfaceView dmsf;
	//
	MyConnect mmc;
	//
	boolean lastDir=true;
	
	DirectionMonitor(MySurfaceView msf, MyConnect mc) {
		dmsf = msf;
		mmc = mc;
		
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			switch(dmsf.judgeDirection()) {
			//½ô¼±É²³µ
			case 0:mmc.sendDirection(String.valueOf(0));break;
			//ÉÏ
			case 1:mmc.sendDirection(String.valueOf(1));break;
			//ÏÂ
			case 2:mmc.sendDirection(String.valueOf(2));break;
			//×ó
			case 3:mmc.sendDirection(String.valueOf(3));break;
			//ÓÒ
			case 4:mmc.sendDirection(String.valueOf(4));break;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
