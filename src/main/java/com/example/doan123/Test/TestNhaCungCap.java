package com.example.doan123.Test;

import com.example.doan123.Dao.NhaCungCapDAO;
import com.example.doan123.Model.NhaCungCap;
import java.util.ArrayList;

public class TestNhaCungCap {
    public static void main(String[] args) {
        NhaCungCapDAO dao = new NhaCungCapDAO();

        System.out.println("--------- TEST 1: INSERT (THÊM MỚI) ---------");
        // ID để số 0 vì trong database nó tự tăng (IDENTITY)
        NhaCungCap nccMoi = new NhaCungCap(0, "Công ty Test Java", "0909000111", "123 Đường Code, TP.HCM");
        int ketQuaInsert = dao.insert(nccMoi);
        if(ketQuaInsert > 0){
            System.out.println("✅ Thêm thành công!");
        } else {
            System.out.println("❌ Thêm thất bại!");
        }

        System.out.println("\n--------- TEST 2: SELECT ALL (XEM DANH SÁCH) ---------");
        ArrayList<NhaCungCap> list = dao.selectAll();
        int idVuaThem = 0; // Biến này để lưu ID thằng cuối cùng để test sửa/xóa

        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
        } else {
            for (NhaCungCap ncc : list) {
                System.out.println("ID: " + ncc.getId() +
                        " | Tên: " + ncc.getTenNhaCungCap() +
                        " | SĐT: " + ncc.getSoDienThoai());
                idVuaThem = ncc.getId(); // Lấy ID của thằng cuối cùng để test tiếp
            }
        }

        // Nếu có dữ liệu thì mới test tiếp Sửa và Xóa
        if (idVuaThem > 0) {
            System.out.println("\n--------- TEST 3: UPDATE (SỬA THẰNG CUỐI CÙNG: ID " + idVuaThem + ") ---------");
            // Tạo đối tượng mới có ID của thằng vừa thêm, nhưng đổi tên và địa chỉ
            NhaCungCap nccCanSua = new NhaCungCap(idVuaThem, "Công ty Đã Sửa Tên", "0123456789", "Địa chỉ mới");

            int ketQuaUpdate = dao.update(nccCanSua);
            if(ketQuaUpdate > 0) {
                System.out.println("✅ Sửa thành công! Kiểm tra lại:");
                // In ra xem thử đã đổi tên chưa
                NhaCungCap kiemTra = dao.selectById(idVuaThem);
                if(kiemTra != null) {
                    System.out.println("   -> Sau khi sửa: " + kiemTra.getTenNhaCungCap() + " - " + kiemTra.getDiaChi());
                }
            }

            System.out.println("\n--------- TEST 4: DELETE (XÓA THẰNG CUỐI CÙNG: ID " + idVuaThem + ") ---------");
            // Để xóa, chỉ cần tạo đối tượng có đúng ID cần xóa
            NhaCungCap nccCanXoa = new NhaCungCap();
            nccCanXoa.setId(idVuaThem);

            int ketQuaDelete = dao.delete(nccCanXoa);
            if(ketQuaDelete > 0) {
                System.out.println("✅ Xóa thành công ID " + idVuaThem);
            } else {
                System.out.println("❌ Xóa thất bại!");
            }

            System.out.println("\n--------- KIỂM TRA LẠI DANH SÁCH CUỐI CÙNG ---------");
            dao.selectAll().forEach(ncc -> System.out.println("ID: " + ncc.getId() + " - " + ncc.getTenNhaCungCap()));
        }
    }
}