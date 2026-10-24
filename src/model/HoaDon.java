package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ngoaile.DuLieuSaiException;
import ngoaile.KhongTimThayException;

// hoa don cua 1 ban, chua nhieu chi tiet
public class HoaDon implements TinhTien {

    private static int dem = 0; // dem de sinh ma HD001, HD002...

    private String maHD;
    private Ban ban;
    private KhachHang khachHang; // null = khach vang lai
    private LocalDate ngayLap;
    private boolean daThanhToan;
    private GiamGia giamGia;
    private List<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

    public HoaDon(String maHD, Ban ban, KhachHang khachHang, LocalDate ngayLap, boolean daThanhToan) {
        if (ban == null) {
            throw new DuLieuSaiException("Hoa don phai co ban");
        }
        this.maHD = maHD;
        this.ban = ban;
        this.khachHang = khachHang;
        this.ngayLap = ngayLap;
        this.daThanhToan = daThanhToan;
        // co the thanh vien thi giam, khong thi thoi
        this.giamGia = (khachHang != null) ? new GiamThanhVien() : new KhongGiam();
    }

    // tao hoa don moi hom nay
    public HoaDon(Ban ban, KhachHang khachHang) {
        this(taoMa(), ban, khachHang, LocalDate.now(), false);
    }

    private static String taoMa() {
        dem++;
        return String.format("HD%03d", dem);
    }

    // doc file xong thi cap nhat dem cho khong trung ma
    public static void capNhatDem(String maHD) {
        int so = Integer.parseInt(maHD.substring(2));
        if (so > dem) {
            dem = so;
        }
    }

    // goi 1 mon
    public void themMon(Mon mon) {
        themMon(mon, 1);
    }

    public void themMon(Mon mon, int soLuong) {
        themMon(mon, soLuong, mon.tinhTien());
    }

    // goi trung mon thi cong don so luong
    public void themMon(Mon mon, int soLuong, double donGia) {
        kiemTraChuaThanhToan();
        kiemTraSoLuong(soLuong);
        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getMon().equals(mon)) {
                ct.setSoLuong(ct.getSoLuong() + soLuong);
                return;
            }
        }
        dsChiTiet.add(new ChiTietHoaDon(mon, soLuong, donGia));
    }

    public void botMon(String maMon, int soLuong) throws KhongTimThayException {
        kiemTraChuaThanhToan();
        kiemTraSoLuong(soLuong);
        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getMon().getMaMon().equalsIgnoreCase(maMon)) {
                if (soLuong >= ct.getSoLuong()) {
                    dsChiTiet.remove(ct);
                } else {
                    ct.setSoLuong(ct.getSoLuong() - soLuong);
                }
                return;
            }
        }
        throw new KhongTimThayException("Hoa don khong co mon " + maMon);
    }

    public double tinhTongGoc() {
        double tong = 0;
        for (ChiTietHoaDon ct : dsChiTiet) {
            tong += ct.tinhTien();
        }
        return tong;
    }

    // tong sau khi tru giam gia
    @Override
    public double tinhTien() {
        double tong = tinhTongGoc();
        return tong - giamGia.tinhGiam(tong);
    }

    public void thanhToan() {
        kiemTraChuaThanhToan();
        if (dsChiTiet.isEmpty()) {
            throw new DuLieuSaiException("Hoa don chua co mon nao");
        }
        daThanhToan = true;
        ban.setCoKhach(false);
        if (khachHang != null) {
            khachHang.congDiem(tinhTien());
        }
    }

    private void kiemTraSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new DuLieuSaiException("So luong phai lon hon 0");
        }
    }

    private void kiemTraChuaThanhToan() {
        if (daThanhToan) {
            throw new DuLieuSaiException("Hoa don " + maHD + " da thanh toan roi");
        }
    }

    public String toCsv() {
        String maKH = (khachHang == null) ? "-" : khachHang.getMaKH();
        return maHD + "," + ban.getSoBan() + "," + maKH + "," + ngayLap + "," + daThanhToan;
    }

    public String getMaHD() {
        return maHD;
    }

    public Ban getBan() {
        return ban;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    // chi dung khi doc file len
    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public GiamGia getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(GiamGia giamGia) {
        if (giamGia == null) {
            throw new DuLieuSaiException("Chua chon cach giam gia");
        }
        this.giamGia = giamGia;
    }

    // tra ve ban sao de ben ngoai khong sua truc tiep
    public List<ChiTietHoaDon> getDsChiTiet() {
        return new ArrayList<>(dsChiTiet);
    }

    @Override
    public String toString() {
        String tenKhach = (khachHang == null) ? "Khach le" : khachHang.getTenKH();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s | Ban %d | %s | %s | %s%n", maHD, ban.getSoBan(), tenKhach, ngayLap,
                daThanhToan ? "Da thanh toan" : "Chua thanh toan"));
        for (ChiTietHoaDon ct : dsChiTiet) {
            sb.append(ct).append("\n");
        }
        sb.append(String.format("  Tong: %,.0f | %s | Phai tra: %s", tinhTongGoc(), giamGia.tenChuongTrinh(), dinhDangTien()));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoaDon)) {
            return false;
        }
        return maHD.equals(((HoaDon) o).maHD);
    }

    @Override
    public int hashCode() {
        return maHD.hashCode();
    }
}
