package com.example.doan123.Test;

import com.example.doan123.Dao.KhachHangDAO;
import com.example.doan123.Model.KhachHang;

public class TestKhachHang {
    public static void main(String[] args) {
        KhachHang khachHang = new KhachHang("toan","012345678","Thuong");
        KhachHangDAO dao = new KhachHangDAO();
        dao.insert(khachHang);

//        KhachHang khachHang = new KhachHang("toan","0855022558","Thuong");
//        KhachHang khachHang1 = new KhachHang("toan","0123456789","Thuong");
//        KhachHangDAO dao = new KhachHangDAO();
//        dao.deleteBySDT("0123456789");

//        KhachHangDAO dao = new KhachHangDAO();
//        ArrayList<KhachHang> list = dao.selectAll();
//        for(KhachHang k: list){
//            System.out.println(k.toString());
//        }
//
//        System.out.println("----------------------------------------------");
//        KhachHangDAO dao1 = new KhachHangDAO();
//        KhachHang kh;
//        kh = dao1.selecyBySDT("0855022558");
//        System.out.println(kh);
    }
}
