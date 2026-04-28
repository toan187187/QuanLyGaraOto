package com.example.doan123.Model;

public class PhuTung {
    private int id;
    private String tenPhuTung;
    private double giaBan;
    private int soLuongTon;
    private int nha_cung_cap_id;

    public PhuTung() {}

    public PhuTung(String tenPhuTung, double giaBan, int soLuongTon, int nha_cung_cap_id) {
        this.tenPhuTung = tenPhuTung;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.nha_cung_cap_id = nha_cung_cap_id;
    }

    public PhuTung(int id, String tenPhuTung, double giaBan, int soLuongTon, int nha_cung_cap_id) {
        this.id = id;
        this.tenPhuTung = tenPhuTung;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.nha_cung_cap_id = nha_cung_cap_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPhuTung() {
        return tenPhuTung;
    }

    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getNha_cung_cap_id() {
        return nha_cung_cap_id;
    }

    public void setNha_cung_cap_id(int nha_cung_cap_id) {
        this.nha_cung_cap_id = nha_cung_cap_id;
    }

    @Override
    public String toString() {
        return this.tenPhuTung;
    }
}