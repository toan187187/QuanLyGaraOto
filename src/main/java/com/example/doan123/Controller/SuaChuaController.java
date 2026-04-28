package com.example.doan123.Controller;

import com.example.doan123.Dao.ChiTietSuaChuaDAO;
import com.example.doan123.Dao.DichVuDAO;
import com.example.doan123.Dao.PhieuSuaChuaDAO;
import com.example.doan123.Dao.PhuTungDAO;
import com.example.doan123.Model.ChiTietSuaChua;
import com.example.doan123.Model.DichVu;
import com.example.doan123.Model.PhieuSuaChua;
import com.example.doan123.Model.PhuTung;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SuaChuaController implements Initializable {
    @FXML private TableView<PhieuSuaChua> tableXeCho;
    @FXML private TableColumn<PhieuSuaChua, String> colBienSoCho;
    @FXML private ComboBox<PhuTung> cbPhuTung;
    @FXML private TextField txtSoLuongPT;
    @FXML private Button btnThemPT;

    @FXML private ComboBox<DichVu> cbDichVu;
    @FXML private Button btnThemDV;

    @FXML private Label lblTongTien;
    @FXML private Button btnHoanThanh;

    @FXML private Label lblBienSoDangChon;
    @FXML private TableView<ChiTietSuaChua> tableChiTiet;
    @FXML private TableColumn<ChiTietSuaChua, String> colTenHang;
    @FXML private TableColumn<ChiTietSuaChua, Integer> colSoLuong;
    @FXML private  TableColumn<ChiTietSuaChua, Double> colDonGia;
    @FXML private  TableColumn<ChiTietSuaChua, Double> colThanhTien;

    private PhieuSuaChuaDAO phieuDao = new PhieuSuaChuaDAO();
    private PhuTungDAO phuTungDAO = new PhuTungDAO();
    private DichVuDAO dichVuDAO = new DichVuDAO();
    private ChiTietSuaChuaDAO  chiTietSuaChuaDAO = new ChiTietSuaChuaDAO();

    private PhieuSuaChua phieuSuaChua = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // new PropertyValueFactory: với mỗi object trong tableview phải sử dụng câu lệnh này để lấy dữ liệu hiển thị. Gọi getBienso();
        colBienSoCho.setCellValueFactory(new PropertyValueFactory<>("bienSo"));
        colTenHang.setCellValueFactory(new PropertyValueFactory<>("tenHangMuc"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

        // tải dữ liệu lên màn hình
    loadDanhSachXeCho();
    loadComboBox();

        tableXeCho.setOnMouseClicked(e->{
        PhieuSuaChua suaChua = tableXeCho.getSelectionModel().getSelectedItem();
        if(suaChua != null){
            chonXeDeSua(suaChua);
        }
    });
        btnThemDV.setOnAction(e-> xulyThemDichVu());
        btnThemPT.setOnAction(e-> xulyThemPhuTung());
        btnHoanThanh.setOnAction(e-> xulyHoanThanh());
}

    private void chonXeDeSua(PhieuSuaChua phieu){
        this.phieuSuaChua = phieu;
        lblBienSoDangChon.setText("Đang sửa xe: "+phieu.getBienSo());
        loadBangChiTiet();

    }

    private void xulyThemPhuTung(){
        if (phieuSuaChua == null){
            showAlert("Chưa chọn xe!", "Vui lòng bấm chọn xe bên trái");
            return;
        }

        PhuTung pt = cbPhuTung.getValue();
        if(pt == null){
            showAlert("Thiếu thông tin", "Bạn chưa chọn phụ tùng nào cả");
            return;
        }
        try{
            int soLuong = Integer.parseInt(txtSoLuongPT.getText().trim());
            if (soLuong <= 0) {
                showAlert("Lỗi", "Số lượng phải lớn hơn 0!");
                return;
            }
            if (soLuong > pt.getSoLuongTon()) {
                showAlert("Lỗi tồn kho", "Trong kho chỉ còn " + pt.getSoLuongTon() + " cái, không đủ để xuất!");
                return;
            }
            // Gọi dao lưu vào bảng chi tiết sửa chữa
            ChiTietSuaChua ct = new ChiTietSuaChua();
            ct.setPhieuSuaChuaId(phieuSuaChua.getId());
            ct.setPhuTungId(pt.getId());
            ct.setSoLuong(soLuong);
            ct.setDonGia(pt.getGiaBan());
            ct.setThanhTien(pt.getGiaBan() * soLuong);
            ct.setTenHangMuc(pt.getTenPhuTung());

            int ketqua = chiTietSuaChuaDAO.insert(ct);
            if(ketqua > 0){
                int soluongmoi = pt.getSoLuongTon() - soLuong;
                pt.setSoLuongTon(soluongmoi);

                phuTungDAO.update(pt);
                loadBangChiTiet();
                loadComboBox();
                txtSoLuongPT.setText("1");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xulyThemDichVu(){
//        System.out.println("THÊM PHỤ TÙNG");
        if(phieuSuaChua == null){
            showAlert("Chưa chọn xe", "Vui lòng chọn xe trước");
            return;
        }

        DichVu dv = cbDichVu.getValue();
        if (dv != null) {
            ChiTietSuaChua ct = new ChiTietSuaChua();
            ct.setPhieuSuaChuaId(phieuSuaChua.getId());
            ct.setDichVuId(dv.getId());
            ct.setSoLuong(1);
            ct.setDonGia(dv.getGiaCong());
            ct.setThanhTien(ct.getDonGia() * 1);
            ct.setTenHangMuc(dv.getTenDichVu());

            chiTietSuaChuaDAO.insert(ct);
            loadBangChiTiet();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------
    private void xulyHoanThanh(){
        if(phieuSuaChua == null){
            showAlert("Chưa chọn xe", "Vui lòng chọn xe cần hoàn thành!");
            return;
        }

        double tongtien = 0;
        for(ChiTietSuaChua i: tableChiTiet.getItems()){
            tongtien += i.getThanhTien();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận hoàn thành");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn hoàn thành việc sửa chữa xe " +phieuSuaChua.getBienSo()+"?\nXe sẽ được chuyển cho bộ phân Thanh Toán.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            int ketqua = phieuDao.updateTrangThaivaTongTien(phieuSuaChua.getId(), "CHỜ THANH TOÁN", tongtien);
            if (ketqua > 0) {
                showAlert("Thành công", "Đã chuyển hồ sơ xe" + phieuSuaChua.getBienSo() + " sang Thanh Toán!");
                loadDanhSachXeCho();
                tableChiTiet.getItems().clear();
                phieuSuaChua = null;
                lblTongTien.setText("0 VNĐ");
            } else {
                showAlert("Lỗi", "Không thể cập nhật trạng thái. Vui lòng kiểm tra lại!");
            }
        }
    }
    // -----------------------------------------------------------------------------------------------------------------------


    private void loadDanhSachXeCho(){
        ObservableList<PhieuSuaChua> list = FXCollections.observableArrayList(phieuDao.selectByTrangThai("TIẾP NHẬN"));
        tableXeCho.setItems(list);
    }

    private void loadComboBox(){
        cbPhuTung.setItems(FXCollections.observableArrayList(phuTungDAO.selectAll()));
        cbDichVu.setItems(FXCollections.observableArrayList(dichVuDAO.selectAll()));
    }

    private void loadBangChiTiet(){
        if(phieuSuaChua != null) {
            ObservableList<ChiTietSuaChua> list = FXCollections.observableArrayList(chiTietSuaChuaDAO.selectByPhieuSuaChuaId(phieuSuaChua.getId()));
            tableChiTiet.setItems(list);

            // tính tồng tiền
            double tong = 0;
            for (ChiTietSuaChua c : list) {
                tong += c.getThanhTien();
            }
            lblTongTien.setText(String.format("%,.0f VNĐ", tong));  // format: đếm số tiền đẹp

        }
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
