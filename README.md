# 🚗 Quản Lý Gara Ô Tô

Ứng dụng desktop quản lý gara ô tô được xây dựng bằng **Java JavaFX** và **SQL Server**, hỗ trợ toàn bộ quy trình từ tiếp nhận xe → sửa chữa → thanh toán → báo cáo doanh thu.

---

## 📋 Mục lục

- [Tính năng](#-tính-năng)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án)
- [Phân quyền người dùng](#-phân-quyền-người-dùng)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Sơ đồ cơ sở dữ liệu](#-sơ-đồ-cơ-sở-dữ-liệu)

---

## ✨ Tính năng

### 🔐 Đăng nhập & Phân quyền
- Đăng nhập bằng tài khoản, mật khẩu mã hóa SHA-256
- Hỗ trợ đăng ký tài khoản mới
- Phân quyền 3 vai trò: Admin, Lễ tân, Thợ sửa chữa

### 📥 Tiếp nhận xe
- Nhập thông tin khách hàng (tự động nhận diện khách quen qua SĐT)
- Đăng ký xe mới hoặc ghi nhận xe cũ quay lại
- Tạo phiếu tiếp nhận, tìm kiếm theo biển số
- Xem lịch sử sửa chữa theo số điện thoại

### 🔧 Sửa chữa
- Xem danh sách xe đang chờ sửa
- Thêm phụ tùng vào phiếu (tự động trừ tồn kho)
- Thêm dịch vụ sửa chữa
- Tính tổng tiền và chuyển trạng thái sang "Chờ thanh toán"

### 💰 Thanh toán
- Hiển thị danh sách xe chờ thanh toán
- Tính tiền thừa trả khách
- Xác nhận thanh toán và xuất **hóa đơn PDF**
- Tìm kiếm theo biển số

### 📦 Quản lý kho hàng
- Thêm / Sửa / Xóa phụ tùng
- Tìm kiếm phụ tùng theo tên
- **Cảnh báo tự động** khi phụ tùng tồn kho < 5 cái

### 📊 Báo cáo doanh thu
- Biểu đồ cột doanh thu theo **tháng** trong năm
- Biểu đồ cột doanh thu theo **ngày** trong tháng
- Lọc theo năm / tháng tùy chọn

---

## 🛠 Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java 25 |
| Giao diện | JavaFX 21 + FXML |
| Cơ sở dữ liệu | Microsoft SQL Server |
| Kết nối DB | JDBC (mssql-jdbc 12.6.1) |
| Xuất PDF | iTextPDF 5.5.13 |
| Build tool | Maven |
| Bảo mật | SHA-256 (mật khẩu) |

---

## 💻 Yêu cầu hệ thống

- Java JDK **21 trở lên**
- Microsoft SQL Server (bản Express miễn phí cũng được)
- Maven 3.x
- IDE khuyến nghị: IntelliJ IDEA

---

## 🚀 Cài đặt & Chạy dự án

### Bước 1: Clone dự án
```bash
git clone https://github.com/toan187187/QuanLyGaraOto.git
cd QuanLyGaraOto
```

### Bước 2: Tạo Database
Mở **SQL Server Management Studio**, chạy file:
```
ĐỒ ÁN CUỐI KỲ.sql
```

### Bước 3: Cấu hình kết nối Database
Mở file `src/main/java/com/example/doan123/JDBC/KetNoi.java` và chỉnh lại thông tin:
```java
private static final String URL =
    "jdbc:sqlserver://127.0.0.1:1433;"
    + "databaseName=QuanLyGaraOto;"
    + "encrypt=true;"
    + "trustServerCertificate=true;";

private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### Bước 4: Chạy ứng dụng
```bash
mvn javafx:run
```
Hoặc chạy trực tiếp class `Launcher.java` trong IntelliJ IDEA.

### Tài khoản mặc định

| Tài khoản | Mật khẩu | Vai trò |
|---|---|---|
| `admin` | `123` | Quản trị viên |
| `letan` | `123` | Lễ tân |
| `covan` | `123` | Thợ sửa chữa |

---

## 👥 Phân quyền người dùng

| Chức năng | Admin | Lễ tân | Thợ |
|---|:---:|:---:|:---:|
| Tiếp nhận xe | ✅ | ✅ | ❌ |
| Sửa chữa | ✅ | ❌ | ✅ |
| Kho hàng | ✅ | ❌ | ✅ |
| Thanh toán | ✅ | ✅ | ❌ |
| Báo cáo | ✅ | ❌ | ❌ |

---

## 📁 Cấu trúc dự án

```
DOAN123/
├── src/main/java/com/example/doan123/
│   ├── Controller/         # Xử lý sự kiện giao diện
│   │   ├── LoginController.java
│   │   ├── MainController.java
│   │   ├── TiepNhanController.java
│   │   ├── SuaChuaController.java
│   │   ├── ThanhToanController.java
│   │   ├── KhoHangController.java
│   │   └── BaoCaoController.java
│   ├── Dao/                # Truy vấn cơ sở dữ liệu
│   ├── Model/              # Các đối tượng dữ liệu
│   ├── JDBC/               # Kết nối SQL Server
│   └── Util/               # Tiện ích (PDF, hash mật khẩu)
├── src/main/resources/     # Giao diện FXML + CSS
├── ĐỒ ÁN CUỐI KỲ.sql      # Script tạo database
└── pom.xml
```

---

## 🗄 Sơ đồ cơ sở dữ liệu

```
TAI_KHOAN ──────────────────────────────────────┐
                                                 │
KHACH_HANG ──── XE ──── PHIEU_SUA_CHUA ─────────┤
                              │                  │
                         CHI_TIET_SUA_CHUA       │
                           /        \            │
                      PHU_TUNG    DICH_VU        │
                          │                      │
NHA_CUNG_CAP ─── PHIEU_NHAP ─── CHI_TIET_NHAP ──┘
```

---

## 👨‍💻 Tác giả

Dự án đồ án cuối kỳ — **Gara Toàn Trương (VKU)**
