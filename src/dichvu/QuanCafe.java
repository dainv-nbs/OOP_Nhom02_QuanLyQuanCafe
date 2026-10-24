package dichvu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Ban;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.KhachHang;
import model.Mon;
import ngoaile.DuLieuSaiException;
import ngoaile.KhongTimThayException;

// singleton: ca chuong trinh chi co 1 quan
public class QuanCafe {

    private static QuanCafe instance;

    private final DocGhiFile docGhi = new DocGhiFile();
    private List<Mon> dsMon = new ArrayList<>(); // da hinh: DoAn, DoUong, Combo chung 1 list
    private List<Ban> dsBan = new ArrayList<>();
    private List<KhachHang> dsKhach = new ArrayList<>();
    private List<HoaDon> dsHoaDon = new ArrayList<>();

    private QuanCafe() {
    }

    public static QuanCafe getInstance() {
        if (instance == null) {
            instance = new QuanCafe();
        }
        return instance;
    }

    public void taiDuLieu() throws IOException {
        dsMon = docGhi.docMon();
        dsBan = docGhi.docBan();
        dsKhach = docGhi.docKhach();
        dsHoaDon = docGhi.docHoaDon(dsBan, dsKhach, dsMon);
        // ban co khach = ban dang co hoa don chua thanh toan
        for (Ban b : dsBan) {
            b.setCoKhach(false);
        }
        for (HoaDon hd : dsHoaDon) {
            if (!hd.isDaThanhToan()) {
                hd.getBan().setCoKhach(true);
            }
        }
    }

    public void luuDuLieu() throws IOException {
        List<String> mon = new ArrayList<>();
        for (Mon m : dsMon) {
            mon.add(m.toCsv());
        }
        List<String> ban = new ArrayList<>();
        for (Ban b : dsBan) {
            ban.add(b.toCsv());
        }
        List<String> khach = new ArrayList<>();
        for (KhachHang kh : dsKhach) {
            khach.add(kh.toCsv());
        }
        List<String> hoaDon = new ArrayList<>();
        List<String> chiTiet = new ArrayList<>();
        for (HoaDon hd : dsHoaDon) {
            hoaDon.add(hd.toCsv());
            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                chiTiet.add(hd.getMaHD() + "," + ct.toCsv());
            }
        }
        docGhi.ghiFile(DocGhiFile.FILE_MON, mon);
        docGhi.ghiFile(DocGhiFile.FILE_BAN, ban);
        docGhi.ghiFile(DocGhiFile.FILE_KHACH, khach);
        docGhi.ghiFile(DocGhiFile.FILE_HOA_DON, hoaDon);
        docGhi.ghiFile(DocGhiFile.FILE_CHI_TIET, chiTiet);
    }

    // ========== MON ==========
    public List<Mon> getDsMon() {
        return new ArrayList<>(dsMon);
    }

    public void themMon(Mon mon) {
        if (dsMon.contains(mon)) {
            throw new DuLieuSaiException("Trung ma mon " + mon.getMaMon());
        }
        dsMon.add(mon);
    }

    public Mon timMon(String maMon) throws KhongTimThayException {
        for (Mon m : dsMon) {
            if (m.getMaMon().equalsIgnoreCase(maMon.trim())) {
                return m;
            }
        }
        throw new KhongTimThayException("Khong co mon ma " + maMon);
    }

    // nap chong: tim theo khoang gia ban
    public List<Mon> timMon(double giaTu, double giaDen) {
        List<Mon> kq = new ArrayList<>();
        for (Mon m : dsMon) {
            if (m.tinhTien() >= giaTu && m.tinhTien() <= giaDen) {
                kq.add(m);
            }
        }
        return kq;
    }

    public List<Mon> timMonTheoTen(String tuKhoa) {
        List<Mon> kq = new ArrayList<>();
        for (Mon m : dsMon) {
            if (m.getTenMon().toLowerCase().contains(tuKhoa.toLowerCase().trim())) {
                kq.add(m);
            }
        }
        return kq;
    }

    // mon da nam trong hoa don thi khong cho xoa
    public void xoaMon(String maMon) throws KhongTimThayException {
        Mon m = timMon(maMon);
        for (HoaDon hd : dsHoaDon) {
            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                if (ct.getMon().equals(m)) {
                    throw new DuLieuSaiException("Mon da co trong hoa don " + hd.getMaHD() + ", khong xoa duoc");
                }
            }
        }
        dsMon.remove(m);
    }

    // ========== BAN ==========
    public List<Ban> getDsBan() {
        return new ArrayList<>(dsBan);
    }

    public void themBan(Ban ban) {
        if (dsBan.contains(ban)) {
            throw new DuLieuSaiException("Trung so ban " + ban.getSoBan());
        }
        dsBan.add(ban);
    }

    public Ban timBan(int soBan) throws KhongTimThayException {
        for (Ban b : dsBan) {
            if (b.getSoBan() == soBan) {
                return b;
            }
        }
        throw new KhongTimThayException("Khong co ban so " + soBan);
    }

    public List<Ban> dsBanTrong() {
        List<Ban> kq = new ArrayList<>();
        for (Ban b : dsBan) {
            if (!b.isCoKhach()) {
                kq.add(b);
            }
        }
        return kq;
    }

    public void xoaBan(int soBan) throws KhongTimThayException {
        Ban b = timBan(soBan);
        if (b.isCoKhach()) {
            throw new DuLieuSaiException("Ban dang co khach, khong xoa duoc");
        }
        for (HoaDon hd : dsHoaDon) {
            if (hd.getBan().equals(b)) {
                throw new DuLieuSaiException("Ban da co hoa don " + hd.getMaHD() + ", khong xoa duoc");
            }
        }
        dsBan.remove(b);
    }

    // ========== KHACH HANG ==========
    public List<KhachHang> getDsKhach() {
        return new ArrayList<>(dsKhach);
    }

    public void themKhach(KhachHang kh) {
        for (KhachHang k : dsKhach) {
            if (k.equals(kh)) {
                throw new DuLieuSaiException("Trung ma khach " + kh.getMaKH());
            }
            if (k.getSdt().equals(kh.getSdt())) {
                throw new DuLieuSaiException("SDT da duoc dang ky");
            }
        }
        dsKhach.add(kh);
    }

    // doi sdt, khong cho trung voi khach khac
    public void doiSdt(KhachHang kh, String sdtMoi) {
        for (KhachHang k : dsKhach) {
            if (!k.equals(kh) && k.getSdt().equals(sdtMoi.trim())) {
                throw new DuLieuSaiException("SDT da duoc dang ky");
            }
        }
        kh.setSdt(sdtMoi);
    }

    public KhachHang timKhach(String maKH) throws KhongTimThayException {
        for (KhachHang kh : dsKhach) {
            if (kh.getMaKH().equalsIgnoreCase(maKH.trim())) {
                return kh;
            }
        }
        throw new KhongTimThayException("Khong co khach ma " + maKH);
    }

    public List<KhachHang> timKhachTheoTen(String tuKhoa) {
        List<KhachHang> kq = new ArrayList<>();
        for (KhachHang kh : dsKhach) {
            if (kh.getTenKH().toLowerCase().contains(tuKhoa.toLowerCase().trim())) {
                kq.add(kh);
            }
        }
        return kq;
    }

    public void xoaKhach(String maKH) throws KhongTimThayException {
        KhachHang kh = timKhach(maKH);
        for (HoaDon hd : dsHoaDon) {
            if (kh.equals(hd.getKhachHang())) {
                throw new DuLieuSaiException("Khach da co hoa don " + hd.getMaHD() + ", khong xoa duoc");
            }
        }
        dsKhach.remove(kh);
    }

    // ========== HOA DON ==========
    public List<HoaDon> getDsHoaDon() {
        return new ArrayList<>(dsHoaDon);
    }

    // maKH rong = khach le
    public HoaDon taoHoaDon(int soBan, String maKH) throws KhongTimThayException {
        Ban ban = timBan(soBan);
        if (ban.isCoKhach()) {
            throw new DuLieuSaiException("Ban " + soBan + " dang co khach");
        }
        KhachHang kh = (maKH == null || maKH.isBlank()) ? null : timKhach(maKH);
        HoaDon hd = new HoaDon(ban, kh);
        ban.setCoKhach(true);
        dsHoaDon.add(hd);
        return hd;
    }

    public HoaDon timHoaDon(String maHD) throws KhongTimThayException {
        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHD().equalsIgnoreCase(maHD.trim())) {
                return hd;
            }
        }
        throw new KhongTimThayException("Khong co hoa don " + maHD);
    }

    public List<HoaDon> dsChuaThanhToan() {
        List<HoaDon> kq = new ArrayList<>();
        for (HoaDon hd : dsHoaDon) {
            if (!hd.isDaThanhToan()) {
                kq.add(hd);
            }
        }
        return kq;
    }

    // chi huy duoc hoa don chua thanh toan
    public void huyHoaDon(String maHD) throws KhongTimThayException {
        HoaDon hd = timHoaDon(maHD);
        if (hd.isDaThanhToan()) {
            throw new DuLieuSaiException("Hoa don da thanh toan, khong huy duoc");
        }
        hd.getBan().setCoKhach(false);
        dsHoaDon.remove(hd);
    }

    // ========== THONG KE ==========
    // top n mon ban chay (chi tinh hoa don da thanh toan)
    public List<Map.Entry<Mon, Integer>> topMonBanChay(int n) {
        Map<Mon, Integer> dem = new HashMap<>();
        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan()) {
                for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                    dem.put(ct.getMon(), dem.getOrDefault(ct.getMon(), 0) + ct.getSoLuong());
                }
            }
        }
        List<Map.Entry<Mon, Integer>> ds = new ArrayList<>(dem.entrySet());
        ds.sort((a, b) -> b.getValue() - a.getValue());
        return ds.subList(0, Math.min(n, ds.size()));
    }

    // doanh thu 12 thang cua 1 nam
    public double[] doanhThuTheoThang(int nam) {
        double[] kq = new double[12];
        for (HoaDon hd : dsHoaDon) {
            if (hd.isDaThanhToan() && hd.getNgayLap().getYear() == nam) {
                kq[hd.getNgayLap().getMonthValue() - 1] += hd.tinhTien();
            }
        }
        return kq;
    }
}
