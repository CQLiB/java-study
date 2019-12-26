package com.libin.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 服务器端 监听 接收数据  处理业务逻辑 以及数据转换的简单过程
 */
@SuppressWarnings("all")
public class Server {
    private static final int port = 8080;

    Socket socket;

    public void init(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                socket = serverSocket.accept();
                new HandlerThread(socket);
            }
        }catch (Exception e){

        }
    }

    private class HandlerThread implements Runnable{
        private Socket socket;
        public HandlerThread(Socket socket){
            this.socket = socket;
            new Thread(this).start();
        }

        @Override
        public void run(){
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientInputStr = bufferedReader.readLine();


                //处理客户端发送来的数据 或者请求
                System.out.println("处理业务逻辑");

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

                printWriter.println("返回客户端处理结果");

                printWriter.close();

                bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (socket != null){
                    try {
                        socket.close();
                    }catch (Exception e){
                        socket = null;

                    }
                }
            }
        }

    }
}
