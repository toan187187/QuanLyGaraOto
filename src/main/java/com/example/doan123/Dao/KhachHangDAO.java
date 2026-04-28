package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class KhachHangDAO {

    public int insert(KhachHang khachHang) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "INSERT INTO khach_hang (ten_khach_hang, so_dien_thoai, loai_khach)"+
                    " VAlUES (?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, khachHang.getTenKhachHang());
            pr.setString(2, khachHang.getSoDienThoai());
            pr.setString(3, khachHang.getLoaiKhach());
            int layid = pr.executeUpdate();
            if(layid > 0){
                ResultSet rs = pr.getGeneratedKeys();
                if (rs.next()){
                    check = rs.getInt(1);
                }
            }
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }

    public int update(KhachHang khachHang) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "UPDATE khach_hang"+
                    " SET"+
                    " ten_khach_hang = ?"+
                    ", so_dien_thoai = ?"+
                    ", loai_khach = ?"+
                    " WHERE so_dien_thoai = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, khachHang.getTenKhachHang());
            pr.setString(2, khachHang.getSoDienThoai());
            pr.setString(3, khachHang.getLoaiKhach());
            pr.setString(4, khachHang.getSoDienThoai());
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public int deleteBySDT(String sdt) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "DELETE FROM khach_hang"+
                    " WHERE so_dien_thoai = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, sdt);
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ArrayList<KhachHang> selectAll() {
        ArrayList<KhachHang> check = new ArrayList<KhachHang>();
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM khach_hang";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String tenKhachHang = rs.getString("ten_khach_hang");
                String soDienThoai = rs.getString("so_dien_thoai");
                String loaiKhach = rs.getString("loai_khach");

                KhachHang khachHang = new KhachHang(id, tenKhachHang, soDienThoai, loaiKhach);
                check.add(khachHang);
            }
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public KhachHang selectBySDT(String sdt){
        KhachHang ketQua = null;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM khach_hang WHERE so_dien_thoai = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, sdt);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                ketQua = new KhachHang(
                        rs.getInt("id"),
                        rs.getString("ten_khach_hang"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("loai_khach")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
