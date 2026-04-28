package com.example.doan123.Dao;

import com.example.doan123.JDBC.KetNoi;
import com.example.doan123.Model.PhieuSuaChua;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PhieuSuaChuaDAO {
    public int insert(PhieuSuaChua t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            // Không insert ID vì ID tự tăng
            String sql = "INSERT INTO phieu_sua_chua (ngay_vao, trang_thai, tong_tien, bien_so, co_van_dich_vu_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pr = con.prepareStatement(sql);

            if (t.getNgayVao() != null) {
                pr.setTimestamp(1, Timestamp.valueOf(t.getNgayVao()));
            } else {
                pr.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            }

            pr.setString(2, t.getTrangThai());
            pr.setDouble(3, t.getTongTien());
            pr.setString(4, t.getBienSo());
            pr.setInt(5, t.getCoVanDichVuId());

            ketQua = pr.executeUpdate();
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(PhieuSuaChua t) {
        int ketQua = 0;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "UPDATE phieu_sua_chua SET ngay_vao=?, trang_thai=?, tong_tien=?, bien_so=?, co_van_dich_vu_id=? WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);

            pr.setTimestamp(1, Timestamp.valueOf(t.getNgayVao()));
            pr.setString(2, t.getTrangThai());
            pr.setDouble(3, t.getTongTien());
            pr.setString(4, t.getBienSo());
            pr.setInt(5, t.getCoVanDichVuId());
            pr.setInt(6, t.getId());

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
            String sqlcon = "DELETE FROM chi_tiet_sua_chua WHERE phieu_sua_chua_id = ?";
            PreparedStatement prcon = con.prepareStatement(sqlcon);
            prcon.setInt(1, id);
            prcon.executeUpdate();

            String sql = "DELETE FROM phieu_sua_chua WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);

            ketQua = pr.executeUpdate();
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể xóa phiếu này vì đã có chi tiết sửa chữa bên trong!");
        }
        return ketQua;
    }

    public ArrayList<PhieuSuaChua> selectAll() {
        ArrayList<PhieuSuaChua> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            // Sắp xếp ngày vào giảm dần (xe mới vào lên đầu)
            String sql = "SELECT * FROM phieu_sua_chua ORDER BY ngay_vao DESC";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                PhieuSuaChua psc = new PhieuSuaChua();
                psc.setId(rs.getInt("id"));

                // Chuyển từ SQL Timestamp về Java LocalDateTime
                Timestamp ts = rs.getTimestamp("ngay_vao");
                if (ts != null) {
                    psc.setNgayVao(ts.toLocalDateTime());
                }

                psc.setTrangThai(rs.getString("trang_thai"));
                psc.setTongTien(rs.getDouble("tong_tien"));
                psc.setBienSo(rs.getString("bien_so"));
                psc.setCoVanDichVuId(rs.getInt("co_van_dich_vu_id"));

                list.add(psc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhieuSuaChua selectById(int id) {
        PhieuSuaChua psc = null;
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM phieu_sua_chua WHERE id=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                psc = new PhieuSuaChua();
                psc.setId(rs.getInt("id"));

                Timestamp ts = rs.getTimestamp("ngay_vao");
                if (ts != null) {
                    psc.setNgayVao(ts.toLocalDateTime());
                }

                psc.setTrangThai(rs.getString("trang_thai"));
                psc.setTongTien(rs.getDouble("tong_tien"));
                psc.setBienSo(rs.getString("bien_so"));
                psc.setCoVanDichVuId(rs.getInt("co_van_dich_vu_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return psc;
    }

    public ArrayList<PhieuSuaChua> selectByTrangThai(String trangThai) {
        ArrayList<PhieuSuaChua> list = new ArrayList<>();
        try(Connection con = KetNoi.getConnection()) {
            String sql = "SELECT * FROM phieu_sua_chua WHERE trang_thai LIKE ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, "%"+ trangThai+ "%");
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                PhieuSuaChua p = new PhieuSuaChua();
                p.setId(rs.getInt("id"));
                p.setBienSo(rs.getString("bien_so"));
                p.setTrangThai(rs.getString("trang_thai"));
                p.setTongTien(rs.getDouble("tong_tien"));
                // Chuyển đổi ngày giờ từ SQL sang Java
                if (rs.getTimestamp("ngay_vao") != null) {
                    p.setNgayVao(rs.getTimestamp("ngay_vao").toLocalDateTime());
                }
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//------------------------------------------------------------------------------------------------------------
    public int updateTrangThaivaTongTien(int idPhieu, String Trangthaimoi, double TongTien){
        int ketqua =0;
        try(Connection con = KetNoi.getConnection()){
            System.out.println(" đsdsd"+idPhieu);
            System.out.println("dsdsđs"+Trangthaimoi);

            if(idPhieu == 0){
                System.out.println("lốiajnscncncnss");
                return 0;
            }

            String sql = "UPDATE phieu_sua_chua SET trang_thai = ?, tong_tien =? WHERE id =?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1,Trangthaimoi);
            pr.setDouble(2, TongTien);
            pr.setInt(3,idPhieu);

            ketqua = pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketqua;
    }

    // Doanh thu theo thang trong nam
    public Map<Integer, Double> getDoanhThuTheoThang(int nam){
        Map<Integer, Double> ketQua = new LinkedHashMap<>();
        for(int i =1; i <= 12; i++){
            ketQua.put(i, 0.0);
        }
        try(Connection con = KetNoi.getConnection()){
            String sql = "SELECT MONTH(ngay_vao) as thang, SUM(tong_tien) as doanh_thu " +
                    "FROM PHIEU_SUA_CHUA "+
                    "WHERE trang_thai = N'ĐÃ THANH TOÁN' AND YEAR(ngay_vao) = ? "+
                    "GROUP BY MONTH(ngay_vao) ORDER BY thang";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, nam);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                ketQua.put(rs.getInt("thang"), rs.getDouble("doanh_thu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    // Doanh thu theo ngay trong thang
    public Map<Integer, Double> getDoanhThuTheoNgay(int nam, int thang){
        Map<Integer, Double>  ketQua = new LinkedHashMap<>();
        try(Connection con  = KetNoi.getConnection()){
            String sql = "SELECT DAY(ngay_vao) as ngay, SUM(tong_tien) as doanh_thu "+
                    "FROM PHIEU_SUA_CHUA "+
                    "WHERE trang_thai = N'ĐÃ THANH TOÁN' "+
                    "  AND YEAR(ngay_vao) = ? AND MONTH(ngay_vao) = ? "+
                    "GROUP BY DAY(ngay_vao) ORDER BY ngay";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, nam);
            pr.setInt(2, thang);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                ketQua.put(rs.getInt("ngay"), rs.getDouble("doanh_thu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<PhieuSuaChua> selectHistoryBySDT(String sdt) {
        ArrayList<PhieuSuaChua> list = new ArrayList<>();
        try (Connection con = KetNoi.getConnection()) {
            // Câu lệnh SQL nối 3 bảng để lọc theo SĐT khách hàng
            String sql = "SELECT p.* FROM phieu_sua_chua p " +
                    "JOIN xe x ON p.bien_so = x.bien_so " +
                    "JOIN khach_hang k ON x.khach_hang_id = k.id " +
                    "WHERE k.so_dien_thoai = ? AND p.trang_thai = N'ĐÃ THANH TOÁN' " +
                    "ORDER BY p.ngay_vao DESC";

            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, sdt);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                PhieuSuaChua p = new PhieuSuaChua();
                p.setId(rs.getInt("id"));
                p.setBienSo(rs.getString("bien_so"));
                p.setTrangThai(rs.getString("trang_thai"));
                p.setTongTien(rs.getDouble("tong_tien"));
                if (rs.getTimestamp("ngay_vao") != null) {
                    p.setNgayVao(rs.getTimestamp("ngay_vao").toLocalDateTime());
                }
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

