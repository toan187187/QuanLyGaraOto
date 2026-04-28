package com.example.doan123.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;

public class KetNoi {
    private static final String URL =
            "jdbc:sqlserver://127.0.0.1:1433;"
                    + "databaseName=QuanLyGaraOto;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    private static final String USER = "Toan0108";
    private static final String PASSWORD = "Tvt187187@";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("✅ KẾT NỐI SQL SERVER THÀNH CÔNG!");
            return con;
        } catch (Exception e) {
//            System.out.println("❌ KẾT NỐI SQL SERVER THẤT BẠI!");
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
