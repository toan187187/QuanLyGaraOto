package com.example.doan123.Model;

public class KhachHang {
    private int id;
    private String tenKhachHang;
    private String soDienThoai;
    private String loaiKhach;

    public KhachHang() {}

    public KhachHang(int id, String tenKhachHang, String soDienThoai, String loaiKhach) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.loaiKhach = loaiKhach;
    }

    public KhachHang(String tenKhachHang, String soDienThoai, String loaiKhach) {
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.loaiKhach = loaiKhach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getLoaiKhach() {
        return loaiKhach;
    }

    public void setLoaiKhach(String loaiKhach) {
        this.loaiKhach = loaiKhach;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "id=" + id +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", loaiKhach='" + loaiKhach + '\'' +
                '}';
    }
}
