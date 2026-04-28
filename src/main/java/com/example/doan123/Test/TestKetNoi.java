package com.example.doan123.Test;


import com.example.doan123.JDBC.KetNoi;


import java.sql.Connection;

public class TestKetNoi {
    public static void main(String[] args) {
        Connection con = KetNoi.getConnection();
            try{
                System.out.println("Database hiện tại: "+ con.getCatalog());
            } catch (Exception e) {
                e.printStackTrace();
            }
        KetNoi.closeConnection(con);
    }
}
