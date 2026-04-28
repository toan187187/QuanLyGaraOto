package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.NhaCungCap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhaCungCapDAO {
    public int insert(NhaCungCap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            // ID tự tăng nên không cần insert ID
            String sql = "INSERT INTO nha_cung_cap (ten_nha_cung_cap, so_dien_thoai, dia_chi) VALUES (?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, t.getTenNhaCungCap());
            pr.setString(2, t.getSoDienThoai());
            pr.setString(3, t.getDiaChi());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(NhaCungCap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE nha_cung_cap SET ten_nha_cung_cap=?, so_dien_thoai=?, dia_chi=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, t.getTenNhaCungCap());
            pr.setString(2, t.getSoDienThoai());
            pr.setString(3, t.getDiaChi());
            pr.setInt(4, t.getId());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(NhaCungCap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "DELETE FROM nha_cung_cap WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, t.getId());

            ketQua = pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<NhaCungCap> selectAll() {
        ArrayList<NhaCungCap> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM nha_cung_cap";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                        rs.getInt("id"),
                        rs.getString("ten_nha_cung_cap"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("dia_chi")
                );
                list.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhaCungCap selectById(int id) {
        NhaCungCap ncc = null;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM nha_cung_cap WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                ncc = new NhaCungCap(
                        rs.getInt("id"),
                        rs.getString("ten_nha_cung_cap"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("dia_chi")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ncc;
    }
}
