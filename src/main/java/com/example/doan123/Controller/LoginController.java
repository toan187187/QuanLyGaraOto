package com.example.doan123.Controller;

import com.example.doan123.Dao.TaiKhoanDAO;
import com.example.doan123.Controller.MainController;

import com.example.doan123.Model.TaiKhoan;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class   LoginController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    @FXML private TextField txtTenDangNhapDK;
    @FXML private PasswordField txtMatKhauDK;
    @FXML private PasswordField txtNhapLaiMatKhauDK;
    @FXML private TextField txtHoTenDK;
    @FXML private ComboBox<String> cbVaiTroDK;


    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private int solansai =0;


    public void handleLogin(ActionEvent e){
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.isEmpty() || password.isEmpty()){
            showAlert("Lỗi", "Vui lòng đăng nhập lại");
            return;
        }

        // Gói dữ liệu
        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", username);
        loginData.put("password", password);
        com.example.doan123.Network.Request request= new com.example.doan123.Network.Request("LOGIN", loginData);

        btnLogin.setDisable(true);
        btnLogin.setText("Đang kết nối...");

        // Tạo luồng ngầm
        Task<com.example.doan123.Network.Response> loginTask = new Task<>(){
            @Override
            protected com.example.doan123.Network.Response call() throws Exception{
                return com.example.doan123.Client.ClientSocketManager.sendRequest(request);
            }
        };
        loginTask.setOnSucceeded(event ->{
            com.example.doan123.Network.Response response = com.example.doan123.Client.ClientSocketManager.sendRequest(request);
            btnLogin.setDisable(false);
            btnLogin.setText("ĐĂNG NHẬP");
            if(response.isSuccess()){
                solansai =0;
                TaiKhoan tkDangNhap = (TaiKhoan) response.getData();
                openMainWindow(tkDangNhap);

                ((Node) (e.getSource())).getScene().getWindow().hide();
            } else {
                solansai++;
                if (solansai >= 3) {
                    showAlert("Khóa bảo mật", "Bạn đã nhập sai 3 lần liên tiếp!\nHệ thống sẽ khóa đăng nhập trong 30 giây");
                    btnLogin.setDisable(true); // khóa nút

                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(30));    // tự động mở khóa
                    pauseTransition.setOnFinished(ev -> {
                        btnLogin.setDisable(false);  // cho mở lại
                        solansai = 0;
                    });
                    pauseTransition.play();
                } else {
                    showAlert("Thông Báo", response.getMessage() + "\n(Bạn còn " + (3 - solansai) + " lần thử)");
                }
            }
        });

        // xử lý lỗi (rớt mạng, server)
        loginTask.setOnFailed(event ->{
            btnLogin.setDisable(false);
            btnLogin.setText("ĐĂNG NHẬP");
            showAlert("Lỗi", "Kết nối đến Server thất bại");
        });

        new Thread(loginTask).start();
    }

    private void chuyencanh(ActionEvent e, String fxmlfile, String title){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doan123/" +fxmlfile));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle(title);
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void handleDangKy(ActionEvent e){
        String user = txtTenDangNhapDK.getText().trim();
        String pass = txtMatKhauDK.getText().trim();
        String repass = txtNhapLaiMatKhauDK.getText().trim();
        String hoten = txtHoTenDK.getText().trim();
        String vaitro = cbVaiTroDK.getValue();

        if(user.isEmpty() || pass.isEmpty() || repass.isEmpty() || hoten.isEmpty() || vaitro.isEmpty()){
            showAlert("Thiếu thông tin", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if(!pass.equals(repass)){
            showAlert("Sai mật khẩu", "Mật khẩu nhập lại không khớp!");
            return;
        }
        TaiKhoan tkmoi = new TaiKhoan(user, pass, hoten, vaitro);
        int ketqua = taiKhoanDAO.insert(tkmoi);
        if(ketqua > 0){
            showAlert("Thành công", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.");
            chuyencanh(e, "Login-View.fxml", "ĐĂNG NHẬP HỆ THỐNG");
        } else{
            showAlert("Lỗi", "Tên đăng nhập đã tồn tại hoặc lỗi hệ thống.");
        }
    }

    public void handleChuyenFormDangKy(ActionEvent e){
        chuyencanh(e, "Dang-Ky.fxml", "ĐĂNG KÝ TÀI KHOẢN");
    }

    public void handleQuayLaiLogin(ActionEvent e){
        chuyencanh(e, "Login-View.fxml", "ĐĂNG NHẬP HỆ THỐNG");
    }



    public void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void openMainWindow(TaiKhoan tkDangNhap){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doan123/Main.fxml"));  // chuyển sang giao diện chính
            Parent parent = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.settaikhoan(tkDangNhap);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("QUẢN LÝ GARA Ô TÔ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Thông Báo", "Lỗi: Không tìm thấy màn hình chính");
        }
    }

    public void closeLoginWindow(){
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
}
