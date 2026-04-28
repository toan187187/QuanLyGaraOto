package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.DichVu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DichVuDAO {
    public int insert(DichVu t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            // ID tự tăng nên không cần insert ID
            String sql = "INSERT INTO dich_vu (ten_dich_vu, gia_cong) VALUES (?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, t.getTenDichVu());
            pr.setDouble(2, t.getGiaCong());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua + " dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(DichVu t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE dich_vu SET ten_dich_vu=?, gia_cong=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, t.getTenDichVu());
            pr.setDouble(2, t.getGiaCong());
            pr.setInt(3, t.getId());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua + " dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int delete(int t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "DELETE FROM dich_vu WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, t);

            ketQua = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có " + ketQua + " dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<DichVu> selectAll() {
        ArrayList<DichVu> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM dich_vu";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getInt("id"),
                        rs.getString("ten_dich_vu"),
                        rs.getDouble("gia_cong")
                );
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public DichVu selectById(int id) {
        DichVu dv = null;
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM dich_vu WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                dv = new DichVu(
                        rs.getInt("id"),
                        rs.getString("ten_dich_vu"),
                        rs.getDouble("gia_cong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dv;
    }
}
