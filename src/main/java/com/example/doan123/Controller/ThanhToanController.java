package com.example.doan123.Controller;

import com.example.doan123.Dao.PhieuSuaChuaDAO;
import com.example.doan123.Model.PhieuSuaChua;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ThanhToanController implements Initializable {
    @FXML private TableView<PhieuSuaChua> tableXeCho;
    @FXML private TableColumn<PhieuSuaChua, String> colBienSo;
    @FXML private TableColumn<PhieuSuaChua, Double> colTongTien;

    @FXML private Label lblBienSo;
    @FXML private Label lblTongTien;
    @FXML private TextField txtTienKhachDua;
    @FXML private Label lblTienThua;

    @FXML private Button btnXacNhan;
    @FXML private TextField txtTimKiem;

    private PhieuSuaChuaDAO phieuSuaChuaDAO = new PhieuSuaChuaDAO();
    private PhieuSuaChua xeDangChon = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBienSo.setCellValueFactory(new PropertyValueFactory<>("bienSo"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

        tableXeCho.setOnMouseClicked(e->{
            xeDangChon = tableXeCho.getSelectionModel().getSelectedItem();
            if(xeDangChon != null){
                hienthiThongTin();
            }
        });

        txtTienKhachDua.textProperty().addListener((observable, oldValue, newValue) ->{
            tinhTienThua();
        });

        btnXacNhan.setOnAction(e -> xulyThanhToan());
        loadDanhSachChoThanhToan();
    }

    private void loadDanhSachChoThanhToan(){
//        System.out.println("--- BẮT ĐẦU TẢI DANH SÁCH THANH TOÁN ---");

        // 1. Gọi DAO lấy danh sách
        ArrayList<PhieuSuaChua> listDB = phieuSuaChuaDAO.selectByTrangThai("CHỜ THANH TOÁN");

//        System.out.println("Tìm thấy " + listDB.size() + " xe trong Database.");
        ObservableList<PhieuSuaChua> list = FXCollections.observableArrayList(phieuSuaChuaDAO.selectByTrangThai("CHỜ THANH TOÁN"));
        tableXeCho.setItems(list);

        FilteredList<PhieuSuaChua> filteredList  = new FilteredList<>(list, p -> true);
        if(txtTimKiem != null){
            txtTimKiem.textProperty().addListener((observable, oldValue, newValue) ->{
                filteredList.setPredicate(phieuSuaChua -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if(phieuSuaChua.getBienSo().toLowerCase().contains(searchKeyWord)){
                        return true;
                    }
                    return false;
                });
            });
        }
        SortedList<PhieuSuaChua> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableXeCho.comparatorProperty());
        tableXeCho.setItems(sortedList);
    }

    private void hienthiThongTin(){
        lblBienSo.setText(xeDangChon.getBienSo());
        lblTongTien.setText(String.format("%,.0f VNĐ", xeDangChon.getTongTien()));
        txtTienKhachDua.setText("");
        lblTienThua.setText("0 VNĐ");
        txtTienKhachDua.requestFocus();
    }

    private void tinhTienThua(){
        if(xeDangChon == null) return;
        try{
            String nhap = txtTienKhachDua.getText().replace(",","");
            if(nhap.isEmpty()){
                nhap = "0";
            }
            double tienKhach = Double.parseDouble(nhap);
            double tongTien = xeDangChon.getTongTien();
            double tienThua = tienKhach - tongTien;

            lblTienThua.setText(String.format("%,.0f VNĐ", tienThua));

            if(tienThua < 0){
                lblTienThua.setStyle("-fx-text-fill: red;");
            } else{
                lblTienThua.setStyle("-fx-text-fill: #27ae60;");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void xulyThanhToan(){
        if(xeDangChon == null){
            showAlert("Chưa chọn xe", "Vui lòng chọn xe cần thanh toán!");
            return;
        }

        try{
            double tienKhach = Double.parseDouble(txtTienKhachDua.getText().isEmpty() ? "0" : txtTienKhachDua.getText());
            if(tienKhach < xeDangChon.getTongTien()){
                showAlert("Thiếu tiền", "Khách chưa đưa đủ tiền");
                return;
            }
        } catch (Exception e){
            showAlert("Lỗi", "Vui lòng nhập số tiền khách đưa hợp lệ");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText("Xác nhận đã thu đủ tiền và trả xe "+ xeDangChon.getBienSo()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            phieuSuaChuaDAO.updateTrangThaivaTongTien(xeDangChon.getId(), "ĐÃ THANH TOÁN", xeDangChon.getTongTien());
            Alert printAlert = new Alert(Alert.AlertType.CONFIRMATION);
            printAlert.setTitle("In hóa đơn");
            printAlert.setHeaderText(null);
            printAlert.setContentText("Thanh toán thành công! Bạn có muốn in hóa đơn PDF không?");

            Optional<ButtonType> printResult = printAlert.showAndWait();
            if (printResult.isPresent() && printResult.get() == ButtonType.OK) {
                // Khởi tạo DAO chi tiết để lấy danh sách phụ tùng/dịch vụ của xe này
                com.example.doan123.Dao.ChiTietSuaChuaDAO ctDAO = new com.example.doan123.Dao.ChiTietSuaChuaDAO();
                java.util.List<com.example.doan123.Model.ChiTietSuaChua> listChiTiet = ctDAO.selectByPhieuSuaChuaId(xeDangChon.getId());

                // Gọi class PdfUtil để tạo file
                com.example.doan123.Util.PdfUtil.xuatHoaDon(xeDangChon, listChiTiet);
                showAlert("Thành công", "Đã xuất hóa đơn ra thư mục dự án!");
            }
            showAlert("Thành công", "Giao dịch hoàn tất! Cảm ơn quý khách.");
            loadDanhSachChoThanhToan();
            lblBienSo.setText("...");
            txtTienKhachDua.clear();
            lblTienThua.setText("0 VNĐ");
            xeDangChon = null;
        }
    }

    private void showAlert(String title, String content){
        Alert alert =  new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
