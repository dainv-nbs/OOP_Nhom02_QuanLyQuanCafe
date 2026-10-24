package model;

import ngoaile.DuLieuSaiException;

// lop cha cua moi mon trong menu
public abstract class Mon implements TinhTien {

    private String maMon;
    private String tenMon;
    private double gia;

    public Mon(String maMon, String tenMon, double gia) {
        setMaMon(maMon);
        setTenMon(tenMon);
        setGia(gia);
    }

    // khong nhap gia thi lay 10k
    public Mon(String maMon, String tenMon) {
        this(maMon, tenMon, 10000);
    }

    // moi loai mon tu tinh gia
    @Override
    public abstract double tinhTien();

    public abstract String loaiMon();

    // thong tin rieng: don vi / size / mo ta
    public abstract String thongTinThem();

    public String toCsv() {
        return loaiMon() + "," + maMon + "," + tenMon + "," + gia + "," + thongTinThem();
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        if (maMon == null || maMon.isBlank() || maMon.contains(",")) {
            throw new DuLieuSaiException("Ma mon khong hop le");
        }
        this.maMon = maMon.trim().toUpperCase();
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        if (tenMon == null || tenMon.isBlank() || tenMon.contains(",")) {
            throw new DuLieuSaiException("Ten mon khong duoc rong hoac co dau phay");
        }
        this.tenMon = tenMon.trim();
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        if (!(gia > 0) || Double.isInfinite(gia)) {
            throw new DuLieuSaiException("Gia phai lon hon 0");
        }
        this.gia = gia;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-7s %-22s %-14s %s", maMon, loaiMon(), tenMon, dinhDangTien(), thongTinThem());
    }

    // 2 mon bang nhau neu trung ma
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mon)) {
            return false;
        }
        return maMon.equals(((Mon) o).maMon);
    }

    @Override
    public int hashCode() {
        return maMon.hashCode();
    }
}
