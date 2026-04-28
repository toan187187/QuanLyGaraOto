package com.example.doan123.Controller;

import com.example.doan123.Dao.PhieuSuaChuaDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import javax.xml.transform.dom.DOMLocator;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class BaoCaoController implements Initializable {
    @FXML private ComboBox<Integer> cbNam;
    @FXML private ComboBox<Integer> cbThang;
    @FXML private RadioButton rbTheoThang;
    @FXML private RadioButton rbTheoNgay;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label lblTongDoanhThu;
    @FXML private Label lblSoPhieu;

    private PhieuSuaChuaDAO dao = new PhieuSuaChuaDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int namHientai = LocalDate.now().getYear();
        for(int i =namHientai; i>=2020; i-- ){
            cbNam.getItems().add(i);
        }
        cbNam.setValue(namHientai);
        for(int i =1; i<=12; i++ ){
            cbThang.getItems().add(i);
        }

        cbThang.setValue(LocalDate.now().getMonthValue());
        rbTheoThang.setSelected(true);
        cbThang.setVisible(false);
        cbThang.setManaged(false);

        rbTheoThang.setOnAction(e -> {
            cbThang.setVisible(false);
            cbThang.setManaged(false);
        });
        rbTheoNgay.setOnAction(e -> {
            cbThang.setVisible(true);
            cbThang.setManaged(true);
        });
        loadBieuDo();
    }
    @FXML
    public void loadBieuDo(){
        barChart.getData().clear();
        if(rbTheoThang.isSelected()){
            hienthitheothang();
        }
        else{
            hienThiTheoNgay();
        }
    }

    private void hienthitheothang(){
        int nam = cbNam.getValue();
        Map<Integer, Double> data = dao.getDoanhThuTheoThang(nam);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu năm " + nam);

        double tongDoanhthu = 0;
        int soPhieucotien = 0;
        for(Map.Entry<Integer, Double> entry: data.entrySet()){
            String nhan = "T" + entry.getKey();
            double giatri = entry.getValue();
            series.getData().add(new XYChart.Data<>(nhan, giatri));
            tongDoanhthu += giatri;
            if(giatri > 0) soPhieucotien++;
        }
        barChart.getData().add(series);
        xAxis.setLabel("Tháng");
        yAxis.setLabel("Doanh thu (VNĐ)");
        barChart.setTitle("Doanh thu theo tháng - năm " +nam);
        lblTongDoanhThu.setText("Tổng: "+ formatTien(tongDoanhthu));
        lblSoPhieu.setText("Số tháng có doanh thu: " +soPhieucotien +"/12");
    }

    private void hienThiTheoNgay() {
        int nam = cbNam.getValue();
        int thang = cbThang.getValue();
        Map<Integer, Double> data = dao.getDoanhThuTheoNgay(nam, thang);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu tháng " + thang + "/" + nam);

        double tongDoanhThu = 0;
        int soNgayCoDT = 0;

        for (Map.Entry<Integer, Double> entry : data.entrySet()) {
            String nhan = "N" + entry.getKey();
            double giatri = entry.getValue();
            series.getData().add(new XYChart.Data<>(nhan, giatri));
            tongDoanhThu += giatri;
            if (giatri > 0) soNgayCoDT++;
        }

        barChart.getData().add(series);
        xAxis.setLabel("Ngày");
        yAxis.setLabel("Doanh thu (VNĐ)");
        barChart.setTitle("Doanh thu tháng " + thang + "/" + nam);

        lblTongDoanhThu.setText("Tổng: " + formatTien(tongDoanhThu));
        lblSoPhieu.setText("Số ngày có doanh thu: " + soNgayCoDT);
    }

    private String formatTien(double so){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        return nf.format(so) + " VNĐ";
    }
}
