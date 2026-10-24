package model;

// khach vang lai, khong giam
public class KhongGiam implements GiamGia {

    @Override
    public double tinhGiam(double tongTien) {
        return 0;
    }

    @Override
    public String tenChuongTrinh() {
        return "Khong giam";
    }
}
