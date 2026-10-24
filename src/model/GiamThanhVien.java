package model;

// khach thanh vien giam 10%
public class GiamThanhVien implements GiamGia {

    public static final double TI_LE_GIAM = 0.1;

    @Override
    public double tinhGiam(double tongTien) {
        return tongTien * TI_LE_GIAM;
    }

    @Override
    public String tenChuongTrinh() {
        return "Thanh vien giam 10%";
    }
}
