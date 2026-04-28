package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.TaiKhoan;
import com.example.doan123.Util.MatKhauUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TaiKhoanDAO {
//    public static TaiKhoanDAO getInstance(){
//        return new TaiKhoanDAO();
//    }

    public TaiKhoan login(String username, String password){
        TaiKhoan tk = null;
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap = ? AND mat_khau = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, username);
            pr.setString(2, MatKhauUtil.hash(password));
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                tk = new TaiKhoan();
                tk.setTenDangNhap(rs.getString("ten_dang_nhap"));
                tk.setMatKhau(rs.getString("mat_khau"));
                tk.setVaiTro(rs.getString("vai_tro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

        public int insert(TaiKhoan taiKhoan) {
            int check = 0;
            try(Connection con = KetNoi.getConnection()){
                String sql = "INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, ho_ten, vai_tro)"+
                        " VALUES (?, ?, ?, ?)";
                PreparedStatement pr = con.prepareStatement(sql);
//                pr.setInt(1, taiKhoan.getId());
                pr.setString(1, taiKhoan.getTenDangNhap());
                pr.setString(2, MatKhauUtil.hash(taiKhoan.getMatKhau()));
                pr.setString(3, taiKhoan.getHoTen());
                pr.setString(4,taiKhoan.getVaiTro());
                check = pr.executeUpdate();
                System.out.println(sql);
                System.out.println("Có "+check+" dòng thay đổi");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return check;
    }

    public int update(TaiKhoan taiKhoan) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "UPDATE tai_khoan"+
                    " SET"+
                    " ten_dang_nhap = ?"+
                    ", mat_khau = ?"+
                    ", ho_ten = ?"+
                    ", vai_tro = ?"+
                    " WHERE id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, taiKhoan.getTenDangNhap());
            pr.setString(2, taiKhoan.getMatKhau());
            pr.setString(3, taiKhoan.getHoTen());
            pr.setString(4,taiKhoan.getVaiTro());
            pr.setInt(5, taiKhoan.getId());
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public int delete(TaiKhoan taiKhoan) {
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "DELETE FROM tai_khoan"+
                    " WHERE id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, taiKhoan.getId());
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ArrayList<TaiKhoan> selectAll() {
        ArrayList<TaiKhoan> check = new ArrayList<TaiKhoan>();
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM tai_khoan";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String tenDangNhap = rs.getString("ten_dang_nhap");
                String matKhau = rs.getString("mat_khau");
                String hoTen = rs.getString("ho_ten");
                String vaiTro = rs.getString("vai_tro");

                TaiKhoan taiKhoan = new TaiKhoan(id, tenDangNhap, matKhau, hoTen, vaiTro);
                check.add(taiKhoan);

            }
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public TaiKhoan selectById(TaiKhoan taiKhoan) {
        TaiKhoan check = null;
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM tai_khoan WHERE id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1,taiKhoan.getId());
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String tenDangNhap = rs.getString("ten_dang_nhap");
                String matKhau = rs.getString("mat_khau");
                String hoTen = rs.getString("ho_ten");
                String vaiTro = rs.getString("vai_tro");

                check = new TaiKhoan(id, tenDangNhap, matKhau, hoTen, vaiTro);

            }
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}
