package model;

import ngoaile.DuLieuSaiException;

// combo nhieu mon, duoc giam 10% gia
public class Combo extends Mon {

    public static final double GIAM_COMBO = 0.1;

    private String moTa;

    public Combo(String maMon, String tenMon, double gia, String moTa) {
        super(maMon, tenMon, gia);
        setMoTa(moTa);
    }

    public Combo(String maMon, String tenMon, double gia) {
        this(maMon, tenMon, gia, "Combo tiet kiem");
    }

    @Override
    public double tinhTien() {
        return getGia() * (1 - GIAM_COMBO);
    }

    @Override
    public String loaiMon() {
        return "Combo";
    }

    @Override
    public String thongTinThem() {
        return moTa;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        if (moTa == null || moTa.isBlank() || moTa.contains(",")) {
            throw new DuLieuSaiException("Mo ta khong duoc rong hoac co dau phay");
        }
        this.moTa = moTa.trim();
    }
}
