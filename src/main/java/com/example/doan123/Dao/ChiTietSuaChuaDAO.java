package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.ChiTietSuaChua;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class ChiTietSuaChuaDAO {

    public int insert(ChiTietSuaChua t){
        int check = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "INSERT INTO chi_tiet_sua_chua (phieu_sua_chua_id, phu_tung_id, dich_vu_id, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);

            pr.setInt(1,t.getPhieuSuaChuaId());
            if(t.getPhuTungId() > 0) {
                pr.setInt(2, t.getPhuTungId());
            } else {
                pr.setNull(2, Types.INTEGER);
            }

            if (t.getDichVuId() > 0){
                pr.setInt(3,t.getDichVuId());
            } else {
                pr.setNull(3, Types.INTEGER);
            }
            pr.setInt(4, t.getSoLuong());
            pr.setDouble(5, t.getDonGia());
            pr.setDouble(6, t.getThanhTien());

            check = pr.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }

    public int update(ChiTietSuaChua t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE chi_tiet_sua_chua SET so_luong=?, don_gia=?, thanh_tien=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);

            pr.setInt(1, t.getSoLuong());
            pr.setDouble(2, t.getDonGia());
            pr.setDouble(3, t.getThanhTien());
            pr.setInt(4, t.getId());

            ketQua = pr.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return ketQua;
    }

    public int delete(int id) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "DELETE FROM chi_tiet_sua_chua WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ketQua = pr.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return ketQua;
    }

    public ArrayList<ChiTietSuaChua> selectByPhieuSuaChuaId(int phieuId) {
        ArrayList<ChiTietSuaChua> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT ct.*, pt.ten_phu_tung, dv.ten_dich_vu "+
                    " FROM chi_tiet_sua_chua AS ct"+
                    " LEFT JOIN PHU_TUNG AS pt ON ct.phu_tung_id = pt.id"+
                    " LEFT JOIN DICH_VU AS dv ON ct.dich_vu_id = dv.id"+
                    " WHERE ct.phieu_sua_chua_id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, phieuId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                ChiTietSuaChua chiTietSuaChua = new ChiTietSuaChua(
                        rs.getInt("id"),
                        rs.getInt("phieu_sua_chua_id"),
                        rs.getInt("phu_tung_id"),
                        rs.getInt("dich_vu_id"),
                        rs.getInt("so_luong"),
                        rs.getDouble("don_gia"),
                        rs.getDouble("thanh_tien")
                );
                String tenPT = rs.getString("ten_phu_tung");
                String tenDV = rs.getString("ten_dich_vu");

                if(tenPT!= null){
                    chiTietSuaChua.setTenHangMuc(tenPT);
                } else if (tenDV!=null){
                    chiTietSuaChua.setTenHangMuc(tenDV);
                } else {
                    chiTietSuaChua.setTenHangMuc("Không xác định");
                }
                list.add(chiTietSuaChua);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
