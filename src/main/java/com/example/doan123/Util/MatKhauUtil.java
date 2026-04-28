package com.example.doan123.Util;

import java.security.MessageDigest;

public class MatKhauUtil {
    public static String hash(String matKhau){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(matKhau.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte b: bytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return matKhau;
        }
    }
}
