package com.example.doan123.Model;

public class DichVu {
    private int id;
    private String tenDichVu;
    private double giaCong;

    public DichVu(){}

    public DichVu(int id, String tenDichVu, double giaCong) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.giaCong = giaCong;
    }

    public DichVu(String tenDichVu, double giaCong) {
        this.tenDichVu = tenDichVu;
        this.giaCong = giaCong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public double getGiaCong() {
        return giaCong;
    }

    public void setGiaCong(double giaCong) {
        this.giaCong = giaCong;
    }

    @Override
    public String toString() {
        return this.tenDichVu;
    }
}
