package model;

import ngoaile.DuLieuSaiException;

// banh, mi... ban theo don vi
public class DoAn extends Mon {

    private String donVi;

    public DoAn(String maMon, String tenMon, double gia, String donVi) {
        super(maMon, tenMon, gia);
        setDonVi(donVi);
    }

    // mac dinh ban theo phan
    public DoAn(String maMon, String tenMon, double gia) {
        this(maMon, tenMon, gia, "phan");
    }

    // do an gia goc
    @Override
    public double tinhTien() {
        return getGia();
    }

    @Override
    public String loaiMon() {
        return "DoAn";
    }

    @Override
    public String thongTinThem() {
        return donVi;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        if (donVi == null || donVi.isBlank() || donVi.contains(",")) {
            throw new DuLieuSaiException("Don vi khong hop le");
        }
        this.donVi = donVi.trim();
    }
}
