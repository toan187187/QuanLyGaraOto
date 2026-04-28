CREATE DATABASE QuanLyGaraOto
GO

USE QuanLyGaraOto
GO

CREATE TABLE TAI_KHOAN
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) NOT NULL UNIQUE,
    mat_khau VARCHAR(100) NOT NULL,
    ho_ten NVARCHAR(100),
    vai_tro NVARCHAR(50)
);
GO

CREATE TABLE KHACH_HANG
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_khach_hang NVARCHAR(100) NOT NULL,
    so_dien_thoai VARCHAR(15) NOT NULL,
    loai_khach NVARCHAR(50) DEFAULT N'Thuong' 
);  
GO

CREATE TABLE NHA_CUNG_CAP
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_nha_cung_cap NVARCHAR(100) NOT NULL,
    so_dien_thoai VARCHAR(15),
    dia_chi NVARCHAR(100)
);
GO

CREATE TABLE XE
(
    bien_so VARCHAR(20) PRIMARY KEY, 
    hang_xe NVARCHAR(50),
    so_vin VARCHAR(50),
    khach_hang_id INT NOT NULL,
    FOREIGN KEY (khach_hang_id) REFERENCES KHACH_HANG(id)
);
GO

CREATE TABLE PHU_TUNG
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_phu_tung NVARCHAR(150) NOT NULL,
    gia_ban DECIMAL(15,2) NOT NULL,
    so_luong_ton INT DEFAULT 0 CHECK (so_luong_ton >= 0)
);
GO

CREATE TABLE DICH_VU
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_dich_vu NVARCHAR(150) NOT NULL,
    gia_cong DECIMAL(15,2) NOT NULL
);
GO

CREATE TABLE PHIEU_NHAP
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ngay_nhap DATETIME DEFAULT GETDATE(),
    tong_tien_nhap DECIMAL(15,2) DEFAULT 0,
    nha_cung_cap_id INT,
    nguoi_tao_id INT,
    FOREIGN KEY (nha_cung_cap_id) REFERENCES NHA_CUNG_CAP(id),
    FOREIGN KEY (nguoi_tao_id) REFERENCES TAI_KHOAN(id)
);
GO

CREATE TABLE CHI_TIET_NHAP
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    phieu_nhap_id INT NOT NULL,
    phu_tung_id INT NOT NULL,
    so_luong_nhap INT NOT NULL CHECK (so_luong_nhap > 0),
    gia_von DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (phieu_nhap_id) REFERENCES PHIEU_NHAP(id),
    FOREIGN KEY (phu_tung_id) REFERENCES PHU_TUNG(id)
);
GO

CREATE TABLE PHIEU_SUA_CHUA 
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    ngay_vao DATETIME DEFAULT GETDATE(),
    trang_thai NVARCHAR(50) DEFAULT N'TIEP_NHAN', 
    tong_tien DECIMAL(15, 2) DEFAULT 0,
    bien_so VARCHAR(20) NOT NULL,
    co_van_dich_vu_id INT, 
    FOREIGN KEY (bien_so) REFERENCES XE(bien_so),
    FOREIGN KEY (co_van_dich_vu_id) REFERENCES TAI_KHOAN(id)
);
GO

CREATE TABLE CHI_TIET_SUA_CHUA 
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    phieu_sua_chua_id INT NOT NULL,
    phu_tung_id INT NULL,
    dich_vu_id INT NULL,
    so_luong INT DEFAULT 1 CHECK (so_luong > 0),
    don_gia DECIMAL(15, 2) NOT NULL,
    thanh_tien DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (phieu_sua_chua_id) REFERENCES PHIEU_SUA_CHUA(id),
    FOREIGN KEY (phu_tung_id) REFERENCES PHU_TUNG(id),
    FOREIGN KEY (dich_vu_id) REFERENCES DICH_VU(id)
);
GO

INSERT INTO TAI_KHOAN (ten_dang_nhap, mat_khau, ho_ten, vai_tro) VALUES 
('admin', '123', N'Quản Trị Viên', 'ADMIN'),
('covan', '123', N'Nguyễn Văn Anh', 'THO'),
('letan', '123', N'Trần Thị Tình', 'LE_TAN');

INSERT INTO DICH_VU (ten_dich_vu, gia_cong) VALUES 
(N'Rửa xe bọt tuyết', 50000),
(N'Thay nhớt xe máy', 30000),
(N'Thay nhớt ô tô', 100000),
(N'Cân bằng động lốp', 150000),
(N'Vệ sinh khoang máy', 300000);

INSERT INTO NHA_CUNG_CAP (ten_nha_cung_cap, so_dien_thoai, dia_chi) VALUES 
(N'Michelin Việt Nam', '0283999999', N'TP.HCM'), 
(N'Phụ Tùng Bosch', '0901234567', N'Hà Nội'), 
(N'Dầu Nhớt Castrol', '0988888888', N'Đà Nẵng');   
GO

INSERT INTO PHU_TUNG (ten_phu_tung, gia_ban, so_luong_ton, nha_cung_cap_id) VALUES 
(N'Nhớt Castrol Power 1', 120000, 10, 3),
(N'Lốp Michelin City Grip', 850000, 5, 1),
(N'Bugi NGK Iridium', 250000, 20, 2), 
(N'Gạt mưa Bosch', 150000, 15, 2);
GO

ALTER TABLE PHU_TUNG ADD nha_cung_cap_id INT;

ALTER TABLE PHU_TUNG 
ADD CONSTRAINT FK_PhuTung_NhaCungCap 
FOREIGN KEY (nha_cung_cap_id) REFERENCES NHA_CUNG_CAP(id);

UPDATE phieu_sua_chua
SET trang_thai = N'CHỜ THANH TOÁN'
WHERE trang_thai LIKE '%CHO_THANH_TOAN%';

UPDATE phieu_sua_chua
SET trang_thai = N'ĐÃ THANH TOÁN'
WHERE trang_thai LIKE '%DA_THANH_TOAN%';

UPDATE TAI_KHOAN SET mat_khau = 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3' 
WHERE ten_dang_nhap IN ('admin', 'covan', 'letan');

select * from KHACH_HANG
select * from XE
select * from NHA_CUNG_CAP
select * from PHU_TUNG
select * from DICH_VU


