此工程是上位机控制的android端app。 

其中介绍几个主要目录：
bin目录下是编译好的文件和apk
src目录下是源代码
res目录下是界面素材
androidManifest.xml工程主配置文件

src下的目录结构
android/androidVNC   主要界面显示传输接受代码都在该目录下
com/antlersoft/android/bc  暂时还在优化的小功能代码
com/antlersoft/android/drawing 显示图像的配置
com/antlersoft/util  一些共用的工具，其中控制遥感界面也在此目录下
com/rp 遥感操作控制的链接端口代码以及方向判断代码


功能主要介绍：
(在src目录下)
主入口是android/androidVNC/androidVNC.java，
其中函数都可以用
从主入口开启VncCanvasActivity.java的activity。
在这个activity中，从MySurfaceView创建了一个对象mySurfaceView，
开始绘制界面最顶层的遥感canvas并且链接控制端口的服务，
然后从VncVanvas.java创建一个对象vncCanvas，
调用initializeVncCanvas这个函数进行初始化
在这个初始化函数中，
创建线程用这个connectAndAuthenticate方法链接服务器视频显示的端口服务，
链接成功后在调用doProtocolInitialisation方法用来显示桌面初始化
初始化桌面后，
调用processNormalProtocol这个方法，是显示桌面的主要代码（其中手势操作的功能做在上面但是木有具体用到）。









ps:启动工程 要配置好路径，还要下载sqlitegen库方可调试