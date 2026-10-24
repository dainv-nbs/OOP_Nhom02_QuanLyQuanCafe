package model;

import ngoaile.DuLieuSaiException;

// khach thanh vien, co tich diem
public class KhachHang {

    private String maKH;
    private String tenKH;
    private String sdt;
    private int diemTichLuy;

    public KhachHang(String maKH, String tenKH, String sdt, int diemTichLuy) {
        setMaKH(maKH);
        setTenKH(tenKH);
        setSdt(sdt);
        setDiemTichLuy(diemTichLuy);
    }

    // khach moi 0 diem
    public KhachHang(String maKH, String tenKH, String sdt) {
        this(maKH, tenKH, sdt, 0);
    }

    // 10k = 1 diem
    public void congDiem(double tien) {
        diemTichLuy += (int) (tien / 10000);
    }

    public String toCsv() {
        return maKH + "," + tenKH + "," + sdt + "," + diemTichLuy;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        if (maKH == null || maKH.isBlank() || maKH.contains(",")) {
            throw new DuLieuSaiException("Ma khach hang khong hop le");
        }
        this.maKH = maKH.trim().toUpperCase();
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        if (tenKH == null || tenKH.isBlank() || tenKH.contains(",")) {
            throw new DuLieuSaiException("Ten khach khong duoc rong hoac co dau phay");
        }
        this.tenKH = tenKH.trim();
    }

    public String getSdt() {
        return sdt;
    }

    // sdt 10 so, bat dau bang 0
    public void setSdt(String sdt) {
        if (sdt == null || !sdt.trim().matches("0\\d{9}")) {
            throw new DuLieuSaiException("SDT phai 10 so va bat dau bang 0");
        }
        this.sdt = sdt.trim();
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        if (diemTichLuy < 0) {
            throw new DuLieuSaiException("Diem khong duoc am");
        }
        this.diemTichLuy = diemTichLuy;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-11s %d diem", maKH, tenKH, sdt, diemTichLuy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KhachHang)) {
            return false;
        }
        return maKH.equals(((KhachHang) o).maKH);
    }

    @Override
    public int hashCode() {
        return maKH.hashCode();
    }
}
