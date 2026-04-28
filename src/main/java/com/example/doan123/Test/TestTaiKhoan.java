package com.example.doan123.Test;

import com.example.doan123.Dao.TaiKhoanDAO;
import com.example.doan123.Model.TaiKhoan;

public class TestTaiKhoan {
    public static void main(String[] args) {
//        TaiKhoan taiKhoan = new TaiKhoan("toan", "1234", "Truong Van Toan", "Chủ");
//        TaiKhoanDAO.getInstance().insert(taiKhoan);
//        TaiKhoan taiKhoan1 = new TaiKhoan(123, "toan", "TVT187187@", "Truong Van Toan", "Chủ");
//        TaiKhoanDAO.getInstance().update(taiKhoan1);
//        TaiKhoan taiKhoan1 = new TaiKhoan(5,"toan", "1234   ", "Truong Van Toan", "Chủ");
//        TaiKhoanDAO.getInstance().delete(taiKhoan1);
//        ArrayList<TaiKhoan> list =TaiKhoanDAO.getInstance().selectAll();
//        for(TaiKhoan t: list){
//            System.out.println(t.toString());
//        }
//        System.out.println("------------------------------------");
//        TaiKhoan taiKhoan = new TaiKhoan();
//        taiKhoan.setId(3);
//        TaiKhoan taiKhoan1 = TaiKhoanDAO.getInstance().selectById(taiKhoan);
//        System.out.println(taiKhoan1);
//        System.out.println("---------------------------------------");
//        ArrayList<TaiKhoan> list1 = TaiKhoanDAO.getInstance().selectByCondition("ADMIN");
//        for(TaiKhoan t: list1){
//            System.out.println(t.toString());
//        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap("admin");
        taiKhoan.setMatKhau("123");
//        TaiKhoan ketqua  = TaiKhoanDAO.getInstance().login(taiKhoan.getHoTen(), taiKhoan.getMatKhau());
//        if(ketqua != null){
//            System.out.println("Đăng nhập thành công");
//        } else{
//            System.out.println("Đăng nhập thất bại");
//        }

        // không chạy được cái nà
    }
}
