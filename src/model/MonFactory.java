package model;

import ngoaile.DuLieuSaiException;

// factory: tao dung loai mon theo ten loai
public class MonFactory {

    public static Mon taoMon(String loai, String ma, String ten, double gia, String thongTinThem) {
        switch (loai.trim().toLowerCase()) {
            case "doan":
                return new DoAn(ma, ten, gia, thongTinThem);
            case "douong":
                return new DoUong(ma, ten, gia, thongTinThem);
            case "combo":
                return new Combo(ma, ten, gia, thongTinThem);
            default:
                throw new DuLieuSaiException("Khong co loai mon: " + loai);
        }
    }
}
