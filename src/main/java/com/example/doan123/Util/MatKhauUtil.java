package com.example.doan123.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class MatKhauUtil {
    public static String hash(String matKhau){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(matKhau.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte b: bytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return matKhau;
        }
    }

    private static  final String SECRET_KEY = "ToanTruongVKU026";
    // Hàm Mã hóa
    public static String encrypt(String data){
        if(data ==null  || data.isEmpty()) return data;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return  Base64.getEncoder().encodeToString(encryptedData);

        }catch (Exception e) {
            System.out.println("❌ Lỗi mã hóa AES: " + e.getMessage());
        }
        return null;
    }
    // Hàm Mã hóa
    public static String decrypt(String encryptedData){
        if(encryptedData == null || encryptedData.isEmpty()) return encryptedData;
        try{
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e){
            return encryptedData;
        }
    }


//    public static void main(String[] args) {
//        // 1. Giả sử đây là số điện thoại khách hàng nhập trên giao diện
//        String sdtBanDau = "0905123456";
//        System.out.println("1. Số điện thoại gốc: " + sdtBanDau);
//
//        // 2. Test chức năng mã hóa (Khóa lại)
//        String sdtMaHoa = encrypt(sdtBanDau);
//        System.out.println("2. Chuỗi sau khi mã hóa (Sẽ lưu xuống SQL): " + sdtMaHoa);
//
//        // 3. Test chức năng giải mã (Mở khóa)
//        String sdtGiaiMa = decrypt(sdtMaHoa);
//        System.out.println("3. Chuỗi sau khi giải mã (Sẽ hiện lên bảng): " + sdtGiaiMa);
//
//        // 4. Kiểm tra chéo xem có khớp nhau không
//        if (sdtBanDau.equals(sdtGiaiMa)) {
//            System.out.println("✅ KẾT LUẬN: MÃ HÓA VÀ GIẢI MÃ HOẠT ĐỘNG HOÀN HẢO!");
//        } else {
//            System.out.println("❌ CÓ LỖI: Dữ liệu giải mã không khớp với ban đầu!");
//        }
//    }
}

