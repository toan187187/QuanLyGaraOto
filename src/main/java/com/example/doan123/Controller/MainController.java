package com.example.doan123.Controller;

import com.example.doan123.Model.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private AnchorPane contentArea;

    private TaiKhoan tkhientai;

    @FXML private Label btnMenuTiepNhan;
    @FXML private Label btnMenuSuaChua;
    @FXML private Label btnMenuKhoHang;
    @FXML private Label btnMenuThanhToan;
    @FXML private Label btnMenuBaoCao;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loadform("Tiep-nhan-view.fxml");
    }

    public void onTiepNhan(){
        loadform("Tiep-Nhan-View.fxml");
    }

    public void onSuaChua(){
        loadform("Sua-Chua-View.fxml");
    }

    public void onKhoHang(){
        loadform("Kho-Hang-View.fxml");
    }

    public void onThanhToan(){
        loadform("Thanh-toan.fxml");
    }

    public void onBaoCao(){
        loadform("Bao-Cao-View.fxml");
    }

    public void settaikhoan(TaiKhoan tk){
        this.tkhientai = tk;
        phanQuyen();
    }

    private void phanQuyen() {
        anNut(btnMenuTiepNhan);
        anNut(btnMenuSuaChua);
        anNut(btnMenuKhoHang);
        anNut(btnMenuThanhToan);
        anNut(btnMenuBaoCao);

        String vaiTro = tkhientai.getVaiTro();

        switch (vaiTro) {
            case "ADMIN":
                hienNut(btnMenuTiepNhan);
                hienNut(btnMenuSuaChua);
                hienNut(btnMenuKhoHang);
                hienNut(btnMenuThanhToan);
                hienNut(btnMenuBaoCao);
                loadform("Tiep-Nhan-View.fxml");
                break;

            case "LE_TAN":
                hienNut(btnMenuTiepNhan);
                hienNut(btnMenuThanhToan);
                loadform("Tiep-Nhan-View.fxml");
                break;

            case "THO":
                hienNut(btnMenuSuaChua);
                hienNut(btnMenuKhoHang);
                loadform("Sua-Chua-View.fxml");
                break;

            default:
                hienNut(btnMenuTiepNhan);
                loadform("Tiep-Nhan-View.fxml");
        }
    }

    private void anNut(Label btn){
        btn.setVisible(false);
        btn.setManaged(false);
    }
    private void hienNut(Label btn){
        btn.setVisible(true);
        btn.setManaged(true);
    }
    @FXML
    public void onDangXuat(){
        try {
            if(contentArea != null){
                Stage current = (Stage) contentArea.getScene().getWindow();
                current.close();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doan123/Login-View.fxml"));   // chuyển về lại class login
            Stage stage = new Stage();
            stage.setTitle("ĐĂNG NHẬP HỆ THỐNG GARA");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadform(String fxmlFile){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/doan123/"+ fxmlFile));
            Parent parent = fxmlLoader.load();   // Lấy giao diện từ file fxml
//            contentArea.getChildren().removeAll(parent);
//            Objects controller = fxmlLoader.getController();
//            if(controller instanceof TiepNhanController){
//                ((TiepNhanController) controller).setTaiKhoan
//            }
            contentArea.getChildren().setAll(parent); //  bỏ nó vào cái khung trống đó
            // ép nó vào cái khung và lấp đầy cái khung đó
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setBottomAnchor(parent,0.0);
            AnchorPane.setLeftAnchor(parent,0.0);
            AnchorPane.setRightAnchor(parent,0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}