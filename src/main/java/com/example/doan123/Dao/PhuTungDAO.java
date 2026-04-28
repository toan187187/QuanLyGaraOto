package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.PhuTung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhuTungDAO {
//    public static PhuTungDAO getInstance(){
//        return new PhuTungDAO();
//    }

    public int insert(PhuTung phuTung) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            // CẬP NHẬT: Thêm nha_cung_cap_id
            String sql = "INSERT INTO phu_tung (ten_phu_tung, gia_ban, so_luong_ton, nha_cung_cap_id)"+
                    " VALUES (?, ?, ?, 1)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, phuTung.getTenPhuTung());
            pr.setDouble(2, phuTung.getGiaBan());
            pr.setInt(3, phuTung.getSoLuongTon());
//            pr.setInt(4, phuTung.getNha_cung_cap_id());

            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public int update(PhuTung phuTung) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){

            String sql = "UPDATE phu_tung"+
                    " SET"+
                    " ten_phu_tung = ?"+
                    ", gia_ban = ?"+
                    ", so_luong_ton = ?"+
                    ", nha_cung_cap_id = ?"+
                    " WHERE id = ?";

            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, phuTung.getTenPhuTung());
            pr.setDouble(2, phuTung.getGiaBan());
            pr.setInt(3, phuTung.getSoLuongTon());
            pr.setInt(4, phuTung.getNha_cung_cap_id());
            pr.setInt(5, phuTung.getId());

            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public int delete(int id) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "DELETE FROM phu_tung"+
                    " WHERE id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ArrayList<PhuTung> selectAll() {
        ArrayList<PhuTung> check = new ArrayList<PhuTung>();
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM phu_tung";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                PhuTung phuTung = new PhuTung(
                        rs.getInt("id"),
                        rs.getString("ten_phu_tung"),
                        rs.getDouble("gia_ban"),
                        rs.getInt("so_luong_ton"),
                        rs.getInt("nha_cung_cap_id")
                );
                check.add(phuTung);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public PhuTung selectById(int id) {
        PhuTung check = null;
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM phu_tung WHERE id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                check = new PhuTung(
                        rs.getInt("id"),
                        rs.getString("ten_phu_tung"),
                        rs.getDouble("gia_ban"),
                        rs.getInt("so_luong_ton"),
                        rs.getInt("nha_cung_cap_id")
                );
            }
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}