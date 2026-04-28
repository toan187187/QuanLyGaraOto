package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.PhieuNhap;

import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDAO {

//    public static PhieuNhapDAO getInstance() {
//        return new PhieuNhapDAO();
//    }

    public int insert(PhieuNhap t) {
        int id = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "INSERT INTO phieu_nhap (ngay_nhap, tong_tien_nhap, nha_cung_cap_id, nguoi_tao_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pr.setTimestamp(1, Timestamp.valueOf(t.getNgayNhap()));
            pr.setDouble(2, t.getTongTienNhap());
            pr.setInt(3, t.getNhaCungCapId());
            pr.setInt(4, t.getNguoiTaoId());

            // thực thi lệnh Insert
            int layid = pr.executeUpdate();
            if(layid > 0){
                ResultSet rs = pr.getGeneratedKeys();  // lấy danh sách key vừa tạo
                if(rs.next()){
                    id = rs.getInt(1); // lấy cột đầu tiên(id)
                }
            }
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public int update(PhieuNhap t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE phieu_nhap SET ngay_nhap=?, tong_tien_nhap=?, nha_cung_cap_id=?, nguoi_tao_id=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);

            pr.setTimestamp(1, Timestamp.valueOf(t.getNgayNhap()));
            pr.setDouble(2, t.getTongTienNhap());
            pr.setInt(3, t.getNhaCungCapId());
            pr.setInt(4, t.getNguoiTaoId());
            pr.setInt(5, t.getId());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(int id) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "DELETE FROM phieu_nhap WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);

            ketQua = pr.executeUpdate();
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<PhieuNhap> selectAll() {
        ArrayList<PhieuNhap> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM phieu_nhap";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setId(rs.getInt("id"));
                pn.setNgayNhap(rs.getTimestamp("ngay_nhap").toLocalDateTime());
                pn.setTongTienNhap(rs.getDouble("tong_tien_nhap"));
                pn.setNhaCungCapId(rs.getInt("nha_cung_cap_id"));
                pn.setNguoiTaoId(rs.getInt("nguoi_tao_id"));

                list.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhieuNhap selectById(int id) {
        PhieuNhap pn = null;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM phieu_nhap WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                pn = new PhieuNhap();
                pn.setId(rs.getInt("id"));
                pn.setNgayNhap(rs.getTimestamp("ngay_nhap").toLocalDateTime());
                pn.setTongTienNhap(rs.getDouble("tong_tien_nhap"));
                pn.setNhaCungCapId(rs.getInt("nha_cung_cap_id"));
                pn.setNguoiTaoId(rs.getInt("nguoi_tao_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pn;
    }
}