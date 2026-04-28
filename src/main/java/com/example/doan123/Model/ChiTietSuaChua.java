package com.example.doan123.Model;

public class ChiTietSuaChua {
    private int id;
    private int phieuSuaChuaId;

    private int phuTungId;
    private int dichVuId;

    private int soLuong;
    private double donGia;
    private double thanhTien;

    private String tenHangMuc;

    public ChiTietSuaChua() {}

    public ChiTietSuaChua(int id, int phieuSuaChuaId, int phuTungId, int dichVuId, int soLuong, double donGia, double thanhTien) {
        this.id = id;
        this.phieuSuaChuaId = phieuSuaChuaId;
        this.phuTungId = phuTungId;
        this.dichVuId = dichVuId;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public ChiTietSuaChua(int phieuSuaChuaId, int phuTungId, int dichVuId, int soLuong, double donGia, double thanhTien) {
        this.phieuSuaChuaId = phieuSuaChuaId;
        this.phuTungId = phuTungId;
        this.dichVuId = dichVuId;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhieuSuaChuaId() {
        return phieuSuaChuaId;
    }

    public void setPhieuSuaChuaId(int phieuSuaChuaId) {
        this.phieuSuaChuaId = phieuSuaChuaId;
    }

    public int getPhuTungId() {
        return phuTungId;
    }

    public void setPhuTungId(int phuTungId) {
        this.phuTungId = phuTungId;
    }

    public int getDichVuId() {
        return dichVuId;
    }

    public void setDichVuId(int dichVuId) {
        this.dichVuId = dichVuId;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }
}
