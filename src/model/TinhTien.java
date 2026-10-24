package model;

// cai gi tinh duoc tien thi implement
public interface TinhTien {

    double tinhTien();

    // in tien cho dep, vd 25,000 VND
    default String dinhDangTien() {
        return String.format("%,.0f VND", tinhTien());
    }
}
