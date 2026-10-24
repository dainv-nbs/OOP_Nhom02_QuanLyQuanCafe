package ngoaile;

// loi khi du lieu nhap vao khong hop le (gia am, sdt sai...)
public class DuLieuSaiException extends RuntimeException {

    public DuLieuSaiException(String thongBao) {
        super(thongBao);
    }
}
