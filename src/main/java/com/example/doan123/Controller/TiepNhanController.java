package com.example.doan123.Controller;

import com.example.doan123.Dao.KhachHangDAO;
import com.example.doan123.Dao.PhieuSuaChuaDAO;
import com.example.doan123.Dao.XeDAO;
import com.example.doan123.Model.KhachHang;
import com.example.doan123.Model.PhieuSuaChua;
import com.example.doan123.Model.Xe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TiepNhanController implements Initializable {
    // Khai báo ô nhập liệu
    @FXML private TextField txtSDT;
    @FXML private TextField txtTenChuXe;
    @FXML private TextField txtBienSo;
    @FXML private TextField txtHieuXe;
    @FXML private Button btnThem;
    @FXML private Button btnLamMoi;
    @FXML private Button btnXoa;

    // Khai báo bảng
    @FXML private TableView<PhieuSuaChua> tableTiepNhan;
    @FXML private TableColumn<PhieuSuaChua, String> colBienSo;
    @FXML private TableColumn<PhieuSuaChua, String> colTrangThai;
    @FXML private TableColumn<PhieuSuaChua, String> colNgayVao;
    @FXML private TextField txtTimKiem;
    @FXML private Button btnLichSu;

    // khai báo DAO
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private XeDAO xeDAO = new XeDAO();
    private PhieuSuaChuaDAO phieuSuaChuaDAO = new PhieuSuaChuaDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("(Kết nối thành công)");
        colBienSo.setCellValueFactory(new PropertyValueFactory<>("bienSo"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colNgayVao.setCellValueFactory(new PropertyValueFactory<>("ngayVao"));

        loadTableData();
        btnThem.setOnAction(e-> handleThemXe());
        btnLamMoi.setOnAction(e -> clearForm());
        btnXoa.setOnAction(e -> handleXoa());
        btnLichSu.setOnAction(e -> handleXemLichSu());
    }

    private void handleThemXe(){
//        System.out.println("nscciainncodcacnoadfvofvndfonodfndfondfovndfv");
        String ten = txtTenChuXe.getText().trim();
        String sdt = txtSDT.getText().trim();
        String bienso = txtBienSo.getText().trim();
        String hieuxe = txtHieuXe.getText().trim();

        if(ten.isEmpty() || sdt.isEmpty() || bienso.isEmpty() || hieuxe.isEmpty()){
            showAlert("Lỗi","Vui lòng nhập Tên, SĐT, Biển Số, Hiệu Xe");
            return;
        }
        try{
            // Xử lý khách hàng
            int idkhachhang = 0;
            KhachHang khachCu = khachHangDAO.selectBySDT(sdt);
            if(khachCu != null){
                idkhachhang = khachCu.getId();
//                System.out.println("Khách Quen: "+ khachCu.getTenKhachHang()+"-ID: "+   idkhachhang);
            } else {
                KhachHang khachmoi = new KhachHang(0, ten, sdt, "");
                idkhachhang = khachHangDAO.insert(khachmoi);
            }
            if(idkhachhang <= 0){
                showAlert("Lỗi", "Không thể lưu thông tin khách hàng");
                return;
            }
            // Xử lý xe
            com.example.doan123.Model.Xe xeKiemtra = xeDAO.selectByBienSo(bienso);
            if(xeKiemtra == null){
                Xe xemoi = new Xe(bienso, hieuxe,"Chưa có VIN", idkhachhang);
                xeDAO.insert(xemoi);
            } else {
                System.out.println("Xe cũ quay lại: " +bienso);
            }

            // Tạo phiếu nhận
            PhieuSuaChua phieu = new PhieuSuaChua();
            phieu.setBienSo(bienso);
            phieu.setTrangThai("TIẾP NHẬN");
            phieu.setTongTien(0);
            phieu.setCoVanDichVuId(1);
            phieu.setNgayVao(LocalDateTime.now());

            int ketqua = phieuSuaChuaDAO.insert(phieu);
            if (ketqua > 0) {
                showAlert("Thành công", "Đã tiến nhận xe" + bienso);
                loadTableData();
                clearForm();
            } else {
                showAlert("Thất Bại", "Lỗi khi tạo phiếu tiếp nhận");
            }

        } catch (Exception e){
            e.printStackTrace();
            showAlert("Lỗi Hệ Thống", "Chi Tiết"+e.getMessage());
        }
    }

    private void handleXoa(){
        PhieuSuaChua select = tableTiepNhan.getSelectionModel().getSelectedItem();
        if(select == null){
            showAlert("Chưa chọn", "Vui lòng chọn phiếu tiếp nhận cần xóa trong bảng!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa phiếu tiếp nhận của xe: " + select.getBienSo()+ "?\n\nLưu ý: Chỉ xóa được phiếu chưa có chi tiết sửa chữa.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            int ketqua = phieuSuaChuaDAO.delete(select.getId());
            if(ketqua >0){
                showAlert("Thành công", "Đã xóa phiếu tiếp nhận xe " + select.getBienSo());
                loadTableData();
                clearForm();
            }
        }
    }

    private void  loadTableData(){
        ObservableList<PhieuSuaChua> list = FXCollections.observableArrayList(phieuSuaChuaDAO.selectAll());
        tableTiepNhan.setItems(list);
        // Để hiển thị lên bảng (tableView) chúng t ko dùng ArrayList mà dùng ObservableList
        // Nếu dữ liệu bên trong thay đổi thì ArrayList sẽ làm cho giao diện không biết mà cập nhật theo
        FilteredList<PhieuSuaChua> filteredData = new FilteredList<>(list, p -> true);
        if (txtTimKiem != null) {
            txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(phieu -> {
                    if (newValue == null || newValue.isEmpty()) return true;

                    String searchKeyword = newValue.toLowerCase();
                    // Tìm theo biển số
                    if (phieu.getBienSo().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }
                    return false;
                });
            });
        }
        SortedList<PhieuSuaChua> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableTiepNhan.comparatorProperty());

        tableTiepNhan.setItems(sortedData);
    }

    private void handleXemLichSu() {
        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            showAlert("Thiếu thông tin", "Vui lòng nhập Số điện thoại khách hàng để tra cứu!");
            return;
        }

        ArrayList<PhieuSuaChua> lichSu = phieuSuaChuaDAO.selectHistoryBySDT(sdt);

        if (lichSu.isEmpty()) {
            showAlert("Thông báo", "Khách hàng này chưa có lịch sử sửa chữa (hoặc chưa có phiếu ĐÃ THANH TOÁN).");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lịch sử sửa chữa của SĐT: ").append(sdt).append("\n");
            sb.append("------------------------------------------\n");
            for (PhieuSuaChua p : lichSu) {
                sb.append(String.format("📅 Ngày: %s | 🚗 Xe: %s | 💰 Tổng: %,.0f VNĐ\n",
                        p.getNgayVao().toLocalDate().toString(),
                        p.getBienSo(),
                        p.getTongTien()));
            }
            showAlert("Lịch sử khách hàng", sb.toString());
        }
    }

    private void clearForm(){
        txtTenChuXe.clear();
        txtSDT.clear();
        txtBienSo.clear();
        txtHieuXe.clear();
    }

    private  void showAlert(String title , String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait(); // hiện và chờ bấm oki
    }

}

