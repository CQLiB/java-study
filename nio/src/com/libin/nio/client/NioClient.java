package com.libin.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
  * @author:李斌
  *
  * @description:NIO 简单客户端实现
  *
  * @create:2019-00-28 06:00
  *
  * @version
  */
@SuppressWarnings("all")
public class NioClient {
    private static final String host = "";

    private static final int port = 80;

    private Selector selector;

    public static void main(String[] args) {
        for (int i=0; i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NioClient client = new NioClient();
                    try {
                        client.connect(host,port);
                        client.listen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public void connect(String host,int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(host,port));
    }

    public void listen(){
        while (true){
            try {
                int events = selector.select();
                if (events>0){
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()){
                        SelectionKey selectionKey = selectionKeys.next();
                        selectionKeys.remove();
                        if (selectionKey.isConnectable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            if (socketChannel.isConnectionPending()){
                                socketChannel.finishConnect();
                            }

                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            socketChannel.write(ByteBuffer.wrap("".getBytes()));
                        }else if (selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.read(buffer);
                            buffer.flip();
                            System.out.println("收到服务器端返回的数据:"+new String(buffer.array()));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
