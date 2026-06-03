package com.example.doan123.Server;

import com.example.doan123.Dao.TaiKhoanDAO;
import com.example.doan123.Model.TaiKhoan;
import com.example.doan123.Network.Request;
import com.example.doan123.Network.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }
    @Override
    public void run(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ){
            while (true){
                Request request = (Request) inputStream.readObject();
                System.out.println("Nhận yêu cầu "+ request.getAction());

                Response response  = null;

                switch (request.getAction()){
                    case "LOGIN":
                        Map<String, String> loginData = (Map<String, String>) request.getData();
                        TaiKhoan  tk = taiKhoanDAO.login(loginData.get("username"), loginData.get("password"));
                        if(tk != null){
                            response = new Response(true, "Đăng nhập thành công", tk);
                        } else {
                            response = new Response(false, "Sai tài khoản hoặc mật khẩu", null);
                        }
                        break;
                    default:
                        response = new Response(false, "Không tìm thấy Action", null);
                }
                //Gửi response về lại cho Client
                outputStream.writeObject(response);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
