# a2bot
The patrol robot with Arduino and Android, implemented based on MPIG robot Carmela
## 目录
1.RockerProject 是android客户端程序<br>
2.TCPServer 是摇杆控制服务器端程序<br>
3.ZoomerWithKeys 是类库提供给RockerProject调用<br>
## 调试说明
1.首先先把3个工程项目下载导入到eclipse。（需要androidsdk环境支持）<br>
2.RockerProject调试：<br>
  下载com.antlersoft.android.db_0.1.6.jar，是运行程序的sqlite依赖包<br>
  下载安装sqlitegen_eclipse_site_0.1.18.jar是eclipse使用sqlite的插件需要安装才能build工程<br>
3.TCPServer调试（导入即可）<br>
4.ZoomerWithKeys调试（导入即可）<br>
## 工程详细说明
###1.RockerProject
  此工程是上位机控制的android端app。 <br>
  其中介绍几个主要目录：<br>
    bin目录下是编译好的文件和apk<br>
    src目录下是源代码<br>
    res目录下是界面素材<br>
    androidManifest.xml工程主配置文件<br>
  src下的目录结构<br>
    android/androidVNC   主要界面显示传输接受代码都在该目录下<br>
    com/antlersoft/android/bc  暂时还在优化的小功能代码<br>
    com/antlersoft/android/drawing 显示图像的配置<br>
    com/antlersoft/util  一些共用的工具，其中控制遥感界面也在此目录下<br>
    com/rp 遥感操作控制的链接端口代码以及方向判断代码<br>
  功能主要介绍：<br>
    (在src目录下)<br>
    主入口是android/androidVNC/androidVNC.java，<br>
    其中函数都可以用<br>
    从主入口开启VncCanvasActivity.java的activity。<br>
    在这个activity中，从MySurfaceView创建了一个对象mySurfaceView，<br>
    开始绘制界面最顶层的遥感canvas并且链接控制端口的服务，<br>
    然后从VncVanvas.java创建一个对象vncCanvas，<br>
    调用initializeVncCanvas这个函数进行初始化<br>
    在这个初始化函数中，<br>
    创建线程用这个connectAndAuthenticate方法链接服务器视频显示的端口服务，<br>
    链接成功后在调用doProtocolInitialisation方法用来显示桌面初始化<br>
    初始化桌面后，调用processNormalProtocol这个方法，是显示桌面的主要代码（其中手势操作的功能做在上面但是木有具体用）<br>
###2.TCPServer（其中视频传输我们采用vnc server软件，此处没有附带源码，需另外下载）
  本工程项目是服务器端的webservice<br>
  运行时需配置相应的lib包以及构建路径<br>
    \com\example\and目录下<br>
    TCPServer.java是服务器socket链接服务接收端代码，建立起连接。<br>
    ServerThread.java是服务器端接受到上位机发来的指令判断发送给下位机的代码。<br>
  功能主要介绍：  <br>
    当服务器接收到连接请求时会开辟一个相应的线程，<br>
    线程中按照传出过来的流对应利用javaRobot模拟键盘按键，达到控制机器人的效果。<br>
###3.ZoomerWithKeys
  本工程项目是提供给Rockerproject依赖库的作用<br>
  功能主要介绍：<br>
    本次项目中未使用到，主要功能是手机端对服务器的一些控制，对后期开发的起到一定作用。<br>
