package com.example.doan123.Model;


import java.time.LocalDateTime;

public class PhieuNhap {
    private int id;
    private LocalDateTime ngayNhap;
    private double tongTienNhap;
    private int nhaCungCapId;
    private int nguoiTaoId;

    public PhieuNhap() {}

    public PhieuNhap(int id, LocalDateTime ngayNhap, double tongTienNhap, int nhaCungCapId, int nguoiTaoId) {
        this.id = id;
        this.ngayNhap = ngayNhap;
        this.tongTienNhap = tongTienNhap;
        this.nhaCungCapId = nhaCungCapId;
        this.nguoiTaoId = nguoiTaoId;
    }

    public PhieuNhap(LocalDateTime ngayNhap, double tongTienNhap, int nhaCungCapId, int nguoiTaoId) {
        this.ngayNhap = ngayNhap;
        this.tongTienNhap = tongTienNhap;
        this.nhaCungCapId = nhaCungCapId;
        this.nguoiTaoId = nguoiTaoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public double getTongTienNhap() {
        return tongTienNhap;
    }

    public void setTongTienNhap(double tongTienNhap) {
        this.tongTienNhap = tongTienNhap;
    }

    public int getNhaCungCapId() {
        return nhaCungCapId;
    }

    public void setNhaCungCapId(int nhaCungCapId) {
        this.nhaCungCapId = nhaCungCapId;
    }

    public int getNguoiTaoId() {
        return nguoiTaoId;
    }

    public void setNguoiTaoId(int nguoiTaoId) {
        this.nguoiTaoId = nguoiTaoId;
    }
}
