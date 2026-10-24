package dichvu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Ban;
import model.HoaDon;
import model.KhachHang;
import model.Mon;
import model.MonFactory;
import ngoaile.DuLieuSaiException;

// doc ghi file csv trong thu muc data
public class DocGhiFile {

    public static final String THU_MUC = "data/";
    public static final String FILE_MON = THU_MUC + "mon.csv";
    public static final String FILE_BAN = THU_MUC + "ban.csv";
    public static final String FILE_KHACH = THU_MUC + "khachhang.csv";
    public static final String FILE_HOA_DON = THU_MUC + "hoadon.csv";
    public static final String FILE_CHI_TIET = THU_MUC + "chitiet.csv";

    // chua co file thi tra ve rong (lan chay dau)
    private List<String> docDong(String tenFile) throws IOException {
        Path p = Paths.get(tenFile);
        List<String> kq = new ArrayList<>();
        if (!Files.exists(p)) {
            return kq;
        }
        for (String dong : Files.readAllLines(p, StandardCharsets.UTF_8)) {
            dong = dong.replace("\uFEFF", ""); // bo ky tu BOM khi file luu bang Excel/Notepad
            if (!dong.isBlank()) {
                kq.add(dong.trim());
            }
        }
        return kq;
    }

    public void ghiFile(String tenFile, List<String> dsDong) throws IOException {
        Files.createDirectories(Paths.get(THU_MUC));
        Files.write(Paths.get(tenFile), dsDong, StandardCharsets.UTF_8);
    }

    private void baoDongLoi(String tenFile, String dong, Exception e) {
        System.out.println("Bo qua dong loi trong " + tenFile + ": " + dong + " (" + e.getMessage() + ")");
    }

    // loai,ma,ten,gia,thongTinThem
    public List<Mon> docMon() throws IOException {
        List<Mon> ds = new ArrayList<>();
        for (String dong : docDong(FILE_MON)) {
            try {
                String[] p = dong.split(",");
                ds.add(MonFactory.taoMon(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4]));
            } catch (DuLieuSaiException | NumberFormatException | IndexOutOfBoundsException e) {
                baoDongLoi(FILE_MON, dong, e);
            }
        }
        return ds;
    }

    // soBan,soCho,coKhach
    public List<Ban> docBan() throws IOException {
        List<Ban> ds = new ArrayList<>();
        for (String dong : docDong(FILE_BAN)) {
            try {
                String[] p = dong.split(",");
                ds.add(new Ban(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Boolean.parseBoolean(p[2])));
            } catch (DuLieuSaiException | NumberFormatException | IndexOutOfBoundsException e) {
                baoDongLoi(FILE_BAN, dong, e);
            }
        }
        return ds;
    }

    // ma,ten,sdt,diem
    public List<KhachHang> docKhach() throws IOException {
        List<KhachHang> ds = new ArrayList<>();
        for (String dong : docDong(FILE_KHACH)) {
            try {
                String[] p = dong.split(",");
                ds.add(new KhachHang(p[0], p[1], p[2], Integer.parseInt(p[3])));
            } catch (DuLieuSaiException | NumberFormatException | IndexOutOfBoundsException e) {
                baoDongLoi(FILE_KHACH, dong, e);
            }
        }
        return ds;
    }

    // hoadon.csv: maHD,soBan,maKH,ngay,daTT  |  chitiet.csv: maHD,maMon,soLuong,donGia
    public List<HoaDon> docHoaDon(List<Ban> dsBan, List<KhachHang> dsKhach, List<Mon> dsMon) throws IOException {
        Map<String, HoaDon> map = new HashMap<>();
        Map<String, Boolean> trangThai = new HashMap<>();
        List<HoaDon> ds = new ArrayList<>();
        for (String dong : docDong(FILE_HOA_DON)) {
            try {
                String[] p = dong.split(",");
                Ban ban = timBan(dsBan, Integer.parseInt(p[1]));
                KhachHang kh = timKhach(dsKhach, p[2]);
                // tao chua thanh toan truoc de con them chi tiet
                HoaDon hd = new HoaDon(p[0], ban, kh, LocalDate.parse(p[3]), false);
                HoaDon.capNhatDem(p[0]);
                map.put(p[0], hd);
                trangThai.put(p[0], Boolean.parseBoolean(p[4]));
                ds.add(hd);
            } catch (DuLieuSaiException | NumberFormatException | IndexOutOfBoundsException
                    | DateTimeParseException e) {
                baoDongLoi(FILE_HOA_DON, dong, e);
            }
        }
        for (String dong : docDong(FILE_CHI_TIET)) {
            try {
                String[] p = dong.split(",");
                HoaDon hd = map.get(p[0]);
                Mon mon = timMon(dsMon, p[1]);
                if (hd == null || mon == null) {
                    throw new DuLieuSaiException("khong khop hoa don hoac mon");
                }
                hd.themMon(mon, Integer.parseInt(p[2]), Double.parseDouble(p[3]));
            } catch (DuLieuSaiException | NumberFormatException | IndexOutOfBoundsException e) {
                baoDongLoi(FILE_CHI_TIET, dong, e);
            }
        }
        for (HoaDon hd : ds) {
            hd.setDaThanhToan(trangThai.get(hd.getMaHD()));
        }
        return ds;
    }

    private Ban timBan(List<Ban> dsBan, int soBan) {
        for (Ban b : dsBan) {
            if (b.getSoBan() == soBan) {
                return b;
            }
        }
        throw new DuLieuSaiException("khong co ban " + soBan);
    }

    // "-" la khach le
    private KhachHang timKhach(List<KhachHang> dsKhach, String maKH) {
        if (maKH.equals("-")) {
            return null;
        }
        for (KhachHang kh : dsKhach) {
            if (kh.getMaKH().equals(maKH)) {
                return kh;
            }
        }
        throw new DuLieuSaiException("khong co khach " + maKH);
    }

    private Mon timMon(List<Mon> dsMon, String maMon) {
        for (Mon m : dsMon) {
            if (m.getMaMon().equals(maMon)) {
                return m;
            }
        }
        return null;
    }
}
