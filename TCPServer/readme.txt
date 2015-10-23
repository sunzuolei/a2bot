本工程项目是服务器端的webservice

src目录下是源代码
bin目录下是编译好的文件

运行是需配置相应的lib包以及构建路径

\com\example\and目录下
TCPServer.java是服务器socket链接服务接收端代码，建立起连接。
ServerThread.java是服务器端接受到上位机发来的指令判断发送给下位机的代码。
