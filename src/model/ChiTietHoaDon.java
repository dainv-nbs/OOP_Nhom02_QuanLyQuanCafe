package model;

import ngoaile.DuLieuSaiException;

// 1 dong trong hoa don, chi HoaDon moi tao duoc
public class ChiTietHoaDon implements TinhTien {

    private Mon mon;
    private int soLuong;
    private double donGia;

    ChiTietHoaDon(Mon mon, int soLuong, double donGia) {
        if (mon == null) {
            throw new DuLieuSaiException("Mon khong duoc rong");
        }
        this.mon = mon;
        setSoLuong(soLuong);
        this.donGia = donGia;
    }

    // lay gia hien tai cua mon
    ChiTietHoaDon(Mon mon, int soLuong) {
        this(mon, soLuong, mon.tinhTien());
    }

    @Override
    public double tinhTien() {
        return donGia * soLuong;
    }

    public String toCsv() {
        return mon.getMaMon() + "," + soLuong + "," + donGia;
    }

    public Mon getMon() {
        return mon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // chi HoaDon duoc sua so luong
    void setSoLuong(int soLuong) {
        if (soLuong <= 0 || soLuong > 100) {
            throw new DuLieuSaiException("So luong tu 1 den 100");
        }
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    @Override
    public String toString() {
        return String.format("  %-22s x%-3d %,10.0f = %s", mon.getTenMon(), soLuong, donGia, dinhDangTien());
    }
}
