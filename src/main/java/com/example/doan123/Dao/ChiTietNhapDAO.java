package com.example.doan123.Dao;
import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.ChiTietNhap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ChiTietNhapDAO {
    public int insert(ChiTietNhap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "INSERT INTO chi_tiet_nhap (phieu_nhap_id, phu_tung_id, so_luong_nhap, gia_von) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, t.getPhieuNhapId());
            pr.setInt(2, t.getPhuTungId());
            pr.setInt(3, t.getSoLuongNhap());
            pr.setDouble(4, t.getGiaVon());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua + " dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(ChiTietNhap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE chi_tiet_nhap SET phieu_nhap_id=?, phu_tung_id=?, so_luong_nhap=?, gia_von=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, t.getPhieuNhapId());
            pr.setInt(2, t.getPhuTungId());
            pr.setInt(3, t.getSoLuongNhap());
            pr.setDouble(4, t.getGiaVon());
            pr.setInt(5, t.getId());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua + " dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(int id) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "DELETE FROM chi_tiet_nhap WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ketQua = pr.executeUpdate();
            System.out.println("Đã xóa dòng ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ChiTietNhap> selectByPhieuNhapId(int phieuNhapId) {
        ArrayList<ChiTietNhap> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM chi_tiet_nhap WHERE phieu_nhap_id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, phieuNhapId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                ChiTietNhap ctn = new ChiTietNhap(
                        rs.getInt("id"),
                        rs.getInt("phieu_nhap_id"),
                        rs.getInt("phu_tung_id"),
                        rs.getInt("so_luong_nhap"),
                        rs.getDouble("gia_von")
                );
                list.add(ctn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
