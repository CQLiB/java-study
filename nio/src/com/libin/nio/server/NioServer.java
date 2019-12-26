package com.libin.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器端 NIO实现
 *
 * 模拟服务器端处理多个连接请求
 */
@SuppressWarnings("all")
public class NioServer {

    /**
     * 服务器端监听的端口（即客户端连接服务器的端口号）
     */
    private int port;

    /**
     *
     */
    private Selector selector;


    /**
     * 初始化服务器端 多线程
     */
    private ExecutorService service = Executors.newFixedThreadPool(5);

    /**
     * 构造函数  初始化端口号
     * @param port
     */
    public NioServer(int port){
        this.port = port;
    }

    /**
     * 初始化
     */
    public void init(){
        ServerSocketChannel serverSocketChannel =null;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            /**
             * 关闭非阻塞
             */
            serverSocketChannel.configureBlocking(false);
            /**
             * bind端口号
             */
            serverSocketChannel.bind(new InetSocketAddress(port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NioServer Started------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param key
     */
    public void accept(SelectionKey key){
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

            SocketChannel socketChannel = serverSocketChannel.accept();

            socketChannel.configureBlocking(false);

            socketChannel.register(selector,SelectionKey.OP_READ);

            System.out.println("accept client:"+socketChannel.socket().getInetAddress().getHostName());

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @throws IOException
     */
    public void start() throws IOException {
        this.init();
        while (true){
            int events = selector.select();

            if (events>0){
                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();

                while (selectionKeys.hasNext()){
                    SelectionKey selectionKey = selectionKeys.next();

                    selectionKeys.remove();

                    if (selectionKey.isAcceptable()){
                        accept(selectionKey);
                    }else {
                        service.submit(new NioServerHandler(selectionKey));
                    }
                }
            }
        }
    }



}
