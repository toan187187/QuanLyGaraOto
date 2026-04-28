package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.Xe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class XeDAO {
//    public static XeDAO getInstance(){
//        return new XeDAO();
//    }

    public int insert(Xe xe){
        int check = 0;
        try(Connection con = KetNoi.getConnection()){
            String sql = "INSERT INTO xe (bien_so, hang_xe, so_vin, khach_hang_id)"+
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, xe.getBienSo());
            pr.setString(2,xe.getHangXe());
            pr.setString(3,xe.getSoVin());
            pr.setInt(4,xe.getKhachHangid());
            check = pr.executeUpdate();
            System.out.println(sql);
            System.out.println("Có "+check+" dòng thay đổi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ArrayList<Xe> selectKhachHang(int idkhachhang){
        ArrayList<Xe> check = new ArrayList<Xe>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM xe WHERE khach_hang_id = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, idkhachhang);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Xe xe = new Xe(
                        rs.getString("bien_so"),
                        rs.getString("hang_xe"),
                        rs.getString("so_vin"),
                        rs.getInt("khach_hang_id")
                        );
                check.add(xe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public Xe selectByBienSo(String bienso){
        Xe xe = null;
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT * FROM xe WHERE bien_so = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, bienso);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                 xe = new Xe(
                        rs.getString("bien_so"),
                        rs.getString("hang_xe"),
                        rs.getString("so_vin"),
                        rs.getInt("khach_hang_id")
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return xe;
    }

}
