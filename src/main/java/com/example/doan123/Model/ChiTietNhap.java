package com.example.doan123.Model;

public class ChiTietNhap {
    private int id;
    private int phieuNhapId;
    private int phuTungId;
    private int soLuongNhap;
    private double giaVon;

    public ChiTietNhap() {}

    public ChiTietNhap(int id, int phieuNhapId, int phuTungId, int soLuongNhap, double giaVon) {
        this.id = id;
        this.phieuNhapId = phieuNhapId;
        this.phuTungId = phuTungId;
        this.soLuongNhap = soLuongNhap;
        this.giaVon = giaVon;
    }

    public ChiTietNhap(int phieuNhapId, int phuTungId, int soLuongNhap, double giaVon) {
        this.phieuNhapId = phieuNhapId;
        this.phuTungId = phuTungId;
        this.soLuongNhap = soLuongNhap;
        this.giaVon = giaVon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhieuNhapId() {
        return phieuNhapId;
    }

    public void setPhieuNhapId(int phieuNhapId) {
        this.phieuNhapId = phieuNhapId;
    }

    public int getPhuTungId() {
        return phuTungId;
    }

    public void setPhuTungId(int phuTungId) {
        this.phuTungId = phuTungId;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public double getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(double giaVon) {
        this.giaVon = giaVon;
    }
}
