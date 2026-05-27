package com.example.doan123;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doan123/Login-View.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            stage.setTitle("ĐĂNG NHẬP HỆ THỐNG GARA");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}



//loi login dang nhap (ai cx co the co admin)
// sai ve vits khi them 2 cai
