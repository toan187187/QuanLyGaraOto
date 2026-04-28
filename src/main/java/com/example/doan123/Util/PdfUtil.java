package com.example.doan123.Util;

import com.example.doan123.Model.ChiTietSuaChua;
import com.example.doan123.Model.PhieuSuaChua;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfUtil {
    public static void xuatHoaDon(PhieuSuaChua phieu, List<ChiTietSuaChua> dsChiTiet) {
        Document document = new Document();
        try {
            String fileName = "HoaDon_" + phieu.getBienSo().replace("-", "") + "_" + phieu.getId() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // 1. TÌM VÀ CẤU HÌNH FONT CHỮ AN TOÀN
            Font fontTitle;
            Font fontBold;
            Font fontNormal;

            File font1 = new File("C:\\Windows\\Fonts\\arial.ttf");
            File font2 = new File("C:\\Windows\\Fonts\\Arial.ttf");
            String fontPath = null;

            if (font1.exists()) fontPath = font1.getAbsolutePath();
            else if (font2.exists()) fontPath = font2.getAbsolutePath();

            if (fontPath != null) {
                BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontTitle = new Font(bf, 18, Font.BOLD);
                fontBold = new Font(bf, 12, Font.BOLD);
                fontNormal = new Font(bf, 12, Font.NORMAL);
            } else {
                // Cứu cánh: Nếu không tìm thấy font Arial trong máy, dùng font mặc định
                fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                fontBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                fontNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            }

            // 2. VẼ NỘI DUNG HÓA ĐƠN
            Paragraph title = new Paragraph("HÓA ĐƠN SỬA CHỮA Ô TÔ", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Gara: Gara Toàn Trương (VKU)", fontBold));
            document.add(new Paragraph("Biển số xe: " + phieu.getBienSo(), fontNormal));

            if (phieu.getNgayVao() != null) {
                document.add(new Paragraph("Ngày vào: " + phieu.getNgayVao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
            }

            document.add(new Paragraph("Trạng thái: " + phieu.getTrangThai(), fontNormal));
            document.add(new Paragraph("----------------------------------------------------------------------------------"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{3f, 1f, 2f, 2f});

            addCellToTable(table, "Tên Hạng Mục", fontBold);
            addCellToTable(table, "SL", fontBold);
            addCellToTable(table, "Đơn Giá", fontBold);
            addCellToTable(table, "Thành Tiền", fontBold);

            if (dsChiTiet != null) {
                for (ChiTietSuaChua ct : dsChiTiet) {
                    String ten = (ct.getTenHangMuc() != null) ? ct.getTenHangMuc() : "Không xác định";
                    addCellToTable(table, ten, fontNormal);
                    addCellToTable(table, String.valueOf(ct.getSoLuong()), fontNormal);
                    addCellToTable(table, String.format("%,.0f", ct.getDonGia()), fontNormal);
                    addCellToTable(table, String.format("%,.0f", ct.getThanhTien()), fontNormal);
                }
            }

            document.add(table);

            Paragraph tongTien = new Paragraph("\nTỔNG CỘNG: " + String.format("%,.0f VNĐ", phieu.getTongTien()), fontTitle);
            tongTien.setAlignment(Element.ALIGN_RIGHT);
            document.add(tongTien);

            document.add(new Paragraph("\nCảm ơn quý khách đã tin tưởng dịch vụ!", fontNormal));

        } catch (Exception e) {
            System.out.println("❌ LỖI TRONG QUÁ TRÌNH TẠO PDF: ");
            e.printStackTrace();
        } finally {
            // 3. BƯỚC QUAN TRỌNG NHẤT: Bắt buộc phải đóng file dù code chạy mượt hay bị lỗi
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    private static void addCellToTable(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}