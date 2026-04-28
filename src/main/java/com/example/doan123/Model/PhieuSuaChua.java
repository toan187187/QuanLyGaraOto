package com.example.doan123.Model;

import java.time.LocalDateTime;

public class PhieuSuaChua {
    private int id;
    private LocalDateTime ngayVao;
    private String trangThai;
    private double tongTien;
    private String bienSo;
    private int coVanDichVuId;

    public PhieuSuaChua() {}

    public PhieuSuaChua(int id, LocalDateTime ngayVao, String trangThai, double tongTien, String bienSo, int coVanDichVuId) {
        this.id = id;
        this.ngayVao = ngayVao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.bienSo = bienSo;
        this.coVanDichVuId = coVanDichVuId;
    }

    public PhieuSuaChua(LocalDateTime ngayVao, String trangThai, double tongTien, String bienSo, int coVanDichVuId) {
        this.ngayVao = ngayVao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.bienSo = bienSo;
        this.coVanDichVuId = coVanDichVuId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getNgayVao() {
        return ngayVao;
    }

    public void setNgayVao(LocalDateTime ngayVao) {
        this.ngayVao = ngayVao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public int getCoVanDichVuId() {
        return coVanDichVuId;
    }

    public void setCoVanDichVuId(int coVanDichVuId) {
        this.coVanDichVuId = coVanDichVuId;
    }
}