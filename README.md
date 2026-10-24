# OOP_Nhom02_QuanLyQuanCafe

Đồ án môn Lập trình hướng đối tượng của Nhóm 2, đề tài số 12 về quản lý quán cà phê.
Giảng viên hướng dẫn là ThS. Nguyễn Dũng.
Mã nguồn lưu tại https://github.com/dainv-nbs/OOP_Nhom02_QuanLyQuanCafe.

## Thành viên

| Họ tên | MSSV | Phần phụ trách |
|---|---|---|
| Nguyễn Văn Đại | NBS2503ITA0040 | `Mon`, `TinhTien`, `Main`, sơ đồ UML, README và báo cáo |
| Trương Thị Tú Vinh | NBS2503ITA0066 | `DoAn`, `DoUong`, `Combo`, `MonFactory` |
| Hoàng Đặng Diệu Linh | NBS2503ITA0042 | `Ban`, `KhachHang`, `ChiTietHoaDon`, `HoaDon` |
| Phạm Lê Xuân Thái | NBS2503ITA0035 | `GiamGia`, `KhongGiam`, `GiamThanhVien`, `QuanCafe` |
| Nguyễn Ngọc Quang | NBS2503ITA0050 | `DuLieuSaiException`, `KhongTimThayException`, `NhapLieu`, `DocGhiFile` và dữ liệu mẫu |

## Môi trường

Máy cần cài JDK 17 trở lên. Chương trình chỉ dùng thư viện chuẩn của Java.

## Biên dịch và chạy

Mở terminal tại thư mục gốc của project (thư mục chứa `src/` và `data/`) rồi chạy các lệnh sau.

```bash
javac -encoding UTF-8 -d out src/*.java src/model/*.java src/dichvu/*.java src/ngoaile/*.java
java -cp out Main
```

Khi khởi động, chương trình đọc dữ liệu từ các file `data/*.csv`. Sau mỗi thao tác trong các menu quản lý, chương trình ghi lại dữ liệu nên tắt máy không mất dữ liệu.

## Chức năng

1. Quản lý món gồm xem thực đơn, thêm, sửa, xóa, tìm theo tên và tìm theo khoảng giá.
2. Quản lý bàn gồm xem tất cả, xem bàn trống, thêm, sửa số chỗ, xóa và tìm bàn.
3. Quản lý khách hàng thành viên gồm xem, thêm, sửa, xóa, tìm theo tên và tìm theo mã.
4. Quản lý hóa đơn gồm tạo hóa đơn cho bàn, gọi món, bớt món, thanh toán, hủy, tìm và xem danh sách hóa đơn. Khách thành viên được giảm 10% khi thanh toán.
5. Thống kê gồm top 5 món bán chạy và doanh thu 12 tháng của một năm.

## Cấu trúc thư mục

```
src/
├── Main.java, NhapLieu.java     menu console và nhập liệu an toàn
├── model/                       Mon, DoAn, DoUong, Combo, MonFactory, Ban, KhachHang,
│                                HoaDon, ChiTietHoaDon, TinhTien, GiamGia, KhongGiam, GiamThanhVien
├── dichvu/                      QuanCafe (Singleton), DocGhiFile (đọc ghi CSV)
└── ngoaile/                     DuLieuSaiException, KhongTimThayException
data/                            mon.csv, ban.csv, khachhang.csv, hoadon.csv, chitiet.csv
uml/class-diagram.png            sơ đồ lớp
tai-lieu/                        BAO-CAO.pdf, SLIDE-BAO-VE.pptx, PHAN-CONG.xlsx
```
