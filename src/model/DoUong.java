package model;

import ngoaile.DuLieuSaiException;

// do uong co size S M L
public class DoUong extends Mon {

    public static final double PHU_THU_M = 5000;
    public static final double PHU_THU_L = 10000;

    private String size;

    public DoUong(String maMon, String tenMon, double gia, String size) {
        super(maMon, tenMon, gia);
        setSize(size);
    }

    // mac dinh size M
    public DoUong(String maMon, String tenMon, double gia) {
        this(maMon, tenMon, gia, "M");
    }

    // size lon thi cong them tien
    @Override
    public double tinhTien() {
        if (size.equals("L")) {
            return getGia() + PHU_THU_L;
        }
        if (size.equals("M")) {
            return getGia() + PHU_THU_M;
        }
        return getGia();
    }

    @Override
    public String loaiMon() {
        return "DoUong";
    }

    @Override
    public String thongTinThem() {
        return size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null) {
            throw new DuLieuSaiException("Size chi duoc S, M, L");
        }
        String s = size.trim().toUpperCase();
        if (!s.equals("S") && !s.equals("M") && !s.equals("L")) {
            throw new DuLieuSaiException("Size chi duoc S, M, L");
        }
        this.size = s;
    }
}
