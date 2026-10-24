package ngoaile;

// loi khi tim ma khong co trong danh sach
public class KhongTimThayException extends Exception {

    public KhongTimThayException(String thongBao) {
        super(thongBao);
    }
}
