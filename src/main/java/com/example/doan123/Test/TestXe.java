package com.example.doan123.Test;

import com.example.doan123.Dao.XeDAO;
import com.example.doan123.Model.Xe;

import java.util.ArrayList;

public class TestXe {
    public static void main(String[] args) {
//        XeDAO xeDAO = new XeDAO();
//        Xe xe = new Xe("59A-1235", "Toyota", "1234567789222", 6);
//        xeDAO.insert(xe);
//        for(Xe x : xeDAO.selectKhachHang(5)) {
//            System.out.println(x);
//        }
        System.out.println("👉 Lấy xe của khách id = 5");
        XeDAO xeDAO = new XeDAO();
        ArrayList<Xe> list = xeDAO.selectKhachHang(4);

        System.out.println("Số xe lấy được: " + list.size());
        for (Xe x : list) {
            System.out.println(x.toString());
        }
    }
}
