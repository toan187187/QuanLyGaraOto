package com.example.doan123.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    private static final int Port = 9000;
    public static void main(String[] args){
        System.out.println("Server đang khởi động");
        ExecutorService pool = Executors.newFixedThreadPool(10); // threadpool tối đa 10 máy kết nối
        try(ServerSocket serverSocket = new ServerSocket(Port)){
            System.out.println("Server đang lắng nghe tại cổng : "+Port);
            while(true){
                // chở KNoi
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server có client mới kết nối : "+ clientSocket.getInetAddress());

                pool.execute(new ClientHandler(clientSocket));  // client này cho 1 thread khác để xử lý kết nối khác vào
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
