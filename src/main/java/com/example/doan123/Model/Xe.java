package com.example.doan123.Model;

public class Xe {
    private String bienSo;
    private String hangXe;
    private String soVin;
    private int khachHangid;
    public Xe(){}
    public Xe(String bienSo, String hangXe, String soVin, int khachHangid) {
        this.bienSo = bienSo;
        this.hangXe = hangXe;
        this.soVin = soVin;
        this.khachHangid =khachHangid;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getHangXe() {
        return hangXe;
    }

    public void setHangXe(String hangXe) {
        this.hangXe = hangXe;
    }

    public String getSoVin() {
        return soVin;
    }

    public void setSoVin(String soVin) {
        this.soVin = soVin;
    }

    public int getKhachHangid(){
        return khachHangid;
    }

    public void setKhachHang(int khachHangid){
        this.khachHangid = khachHangid;
    }

    @Override
    public String toString() {
        return "Xe{" +
                "bienSo='" + bienSo + '\'' +
                ", hangXe='" + hangXe + '\'' +
                ", soVin='" + soVin + '\'' +
                ", khachHangid=" + khachHangid +
                '}';
    }
}