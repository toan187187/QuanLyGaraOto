package com.example.doan123.Test;

import com.example.doan123.Dao.PhuTungDAO;
import com.example.doan123.Model.PhuTung;

public class TestPhuTung {
    public static void main(String[] args) {
//        PhuTungDAO phuTungDAO = new PhuTungDAO();
//        phuTungDAO.delete(5);
        PhuTungDAO phuTungDAO = new PhuTungDAO();
        PhuTung ph;
        ph = phuTungDAO.selectById(1);
        System.out.println(ph);
//        System.out.println("--------------------------------");
//        ArrayList<PhuTung> dao = phuTungDAO.selectAll();
//        for(PhuTung p: dao){
//            System.out.println(p.toString());
//        }
    }
}
