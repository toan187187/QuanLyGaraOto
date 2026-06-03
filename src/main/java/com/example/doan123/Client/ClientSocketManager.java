package com.example.doan123.Client;

import com.example.doan123.Network.Request;
import com.example.doan123.Network.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketManager {
    private static  final  String IP = "127.0.0.1";
    private static  final  int PORT = 9000;

    public static Response sendRequest(Request request) {
        try (Socket socket = new Socket(IP, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Gửi yêu cầu
            out.writeObject(request);
            out.flush();

            // Chờ và đọc kết quả Server trả về
            return (Response) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, "Lỗi kết nối đến Server! Vui lòng bật Server.", null);
        }
    }
}
