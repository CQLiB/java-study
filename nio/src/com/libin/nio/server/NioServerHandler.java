package com.libin.nio.server;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 基础实现
 */
@SuppressWarnings("all")
public class NioServerHandler implements Runnable {

    private SelectionKey selectionKey;

    public NioServerHandler(SelectionKey selectionKey){
        this.selectionKey = selectionKey;
    }


    @Override
    public void run() {
        try {
            if (selectionKey.isReadable()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                socketChannel.read(buffer);
                buffer.flip();
                System.out.println("收到客户端");

                ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());

                socketChannel.write(outBuffer);
                //关闭
                selectionKey.cancel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
