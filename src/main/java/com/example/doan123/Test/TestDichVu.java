package com.example.doan123.Test;

import com.example.doan123.Dao.DichVuDAO;
import com.example.doan123.Model.DichVu;
import java.util.ArrayList;

public class TestDichVu {
    public static void main(String[] args) {
        DichVuDAO dao = new DichVuDAO();

//        System.out.println("--- TEST 1: INSERT ---");
//        DichVu dvMoi = new DichVu(0, "Rửa xe siêu sạch", 50000);
//        dao.insert(dvMoi);

        System.out.println("\n--- TEST 2: SELECT ALL ---");
        ArrayList<DichVu> list = dao.selectAll();
        int lastId = 0;
        for (DichVu dv : list) {
            System.out.println(dv.getId() + " - " + dv.getTenDichVu() + " - Giá: " + dv.getGiaCong());
            lastId = dv.getId();
        }

        if (lastId > 0) {
            System.out.println("\n--- TEST 3: UPDATE (ID " + lastId + ") ---");
            DichVu dvSua = new DichVu(lastId, "Rửa xe VIP Pro", 80000);
            dao.update(dvSua);

            // Kiểm tra lại
            DichVu check = dao.selectById(lastId);
            System.out.println("Sau khi sửa: " + check.getTenDichVu());

            System.out.println("\n--- TEST 4: DELETE (ID " + lastId + ") ---");
//             dao.delete(lastId); // Bỏ comment dòng này nếu muốn xóa thật
        }
    }
}