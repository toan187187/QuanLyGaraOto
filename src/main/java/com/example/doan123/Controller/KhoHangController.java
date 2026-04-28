package com.example.doan123.Controller;

import com.example.doan123.Dao.PhuTungDAO;
import com.example.doan123.Model.PhuTung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class KhoHangController implements Initializable {
    @FXML private TextField txtTenPhuTung;
    @FXML private TextField txtSoLuong;
    @FXML private TextField txtGiaBan;

    @FXML private Button btnThem;
    @FXML private Button btnSua;
    @FXML private Button btnXoa;
    @FXML private Button btnLamMoi;

    @FXML private TableView<PhuTung> tableKho;
    @FXML private TableColumn<PhuTung, String> colTen;
    @FXML private TableColumn<PhuTung, Integer> colSoLuong;
    @FXML private TableColumn<PhuTung, Double> colGia;

    @FXML private TextField txtTimKiem;

    private PhuTungDAO phuTungDAO = new PhuTungDAO();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTen.setCellValueFactory(new PropertyValueFactory<>("tenPhuTung"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        colGia.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

        loadDuLieuLenBang();
        tableKho.setOnMouseClicked(e->{
            PhuTung p = tableKho.getSelectionModel().getSelectedItem();
            if(p != null){
                txtTenPhuTung.setText(p.getTenPhuTung());
                txtSoLuong.setText(String.valueOf(p.getSoLuongTon()));
                txtGiaBan.setText(String.valueOf((long)p.getGiaBan()));
            }
        });
        btnThem.setOnAction(e-> xulyThem());
        btnSua.setOnAction(e-> xulySua());
        btnXoa.setOnAction(e-> xulyXoa());
        btnLamMoi.setOnAction(e-> xulyLamMoi());

        kiemtraTonKho();
    }

    private void loadDuLieuLenBang(){
        ObservableList<PhuTung> list = FXCollections.observableArrayList(phuTungDAO.selectAll());
        tableKho.setItems(list);

        FilteredList<PhuTung> filteredList = new FilteredList<>(list,  p->  true);
        if(txtTimKiem != null){
            txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(phuTung -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(phuTung.getTenPhuTung().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
        }
        SortedList<PhuTung> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableKho.comparatorProperty());
        tableKho.setItems(sortedList);
    }

    private void xulyThem(){
        if(txtTenPhuTung.getText().isEmpty()){
            showAlert("Thiếu thông tin", "Vui lòng nhập tên phụ tùng");
            return;
        }
        try{
            PhuTung p = new PhuTung();
            p.setTenPhuTung(txtTenPhuTung.getText());
            p.setSoLuongTon(Integer.parseInt(txtSoLuong.getText().trim()));
            p.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));

            phuTungDAO.insert(p);

            showAlert("Thành công", "Đã thêm món hàng mới");
            loadDuLieuLenBang();
            xulyLamMoi();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void xulySua(){
        PhuTung selected = tableKho.getSelectionModel().getSelectedItem();
        if(selected == null){
            showAlert("Chưa chọn", "Vui lòng bấm vào món cần sửa trong bảng trước!");
            return;
        }

        try{
            selected.setTenPhuTung(txtTenPhuTung.getText());
            selected.setSoLuongTon(Integer.parseInt(txtSoLuong.getText()));
            selected.setGiaBan(Double.parseDouble(txtGiaBan.getText()));

            phuTungDAO.update(selected);

            showAlert("Thành công", "Đã cập nhật thông tin");
            loadDuLieuLenBang();
            xulyLamMoi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xulyXoa(){
        PhuTung selected = tableKho.getSelectionModel().getSelectedItem();
        if(selected == null){
            showAlert("Chưa chọn", "Vui lòng chọn món cần xóa!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa: " +selected.getTenPhuTung() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            int ketqua = phuTungDAO.delete(selected.getId());
            if(ketqua > 0){
                showAlert("Thành công", "Đã xóa phụ tùng khỏi kho.");
                loadDuLieuLenBang(); // Load lại để mất dòng đó
                xulyLamMoi();
            }
            else{
                showAlert("Không thể xóa!", "Phụ tùng này đã được sử dụng trong Lịch sử sửa chữa.\n\n(SQL không cho phép xóa để bảo toàn dữ liệu lịch sử)");
            }
        }
    }

    private void xulyLamMoi(){
        txtTenPhuTung.setText("");
        txtGiaBan.setText("");
        txtSoLuong.setText("");
        txtTenPhuTung.requestFocus(); // đưa con trỏ chuột về ô đầu tiên
        tableKho.getSelectionModel().clearSelection(); // Bỏ chọn dongf trong bảng
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void kiemtraTonKho(){
        java.util.List<PhuTung> list = phuTungDAO.selectAll();
        StringBuilder canhBao = new StringBuilder();
        for(PhuTung pt: list){
            if(pt.getSoLuongTon() < 5){
                canhBao.append("- ").append(pt.getTenPhuTung()).append(" (Chỉ còn: ").append(pt.getSoLuongTon()).append(" cái)\n");
            }
        }
        if(canhBao.length() > 0){
            showAlert("⚠ CẢNH BÁO TỒN KHO THẤP", "Các phụ tùng sau sắp hết, cần nhập thêm gấp:\n\n" + canhBao.toString());
        }
    }
}
