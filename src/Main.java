import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import dichvu.QuanCafe;
import model.Ban;
import model.HoaDon;
import model.KhachHang;
import model.Mon;
import model.MonFactory;
import ngoaile.DuLieuSaiException;
import ngoaile.KhongTimThayException;

// chuong trinh chinh, noi cac phan lai voi nhau
public class Main {

    private static final QuanCafe qc = QuanCafe.getInstance();

    public static void main(String[] args) {
        try {
            qc.taiDuLieu();
        } catch (IOException e) {
            // doc loi ma van chay thi luc luu se ghi de mat file, nen dung luon
            System.out.println("Khong doc duoc file du lieu: " + e.getMessage());
            return;
        }
        try {
            while (true) {
                System.out.println("\n===== QUAN LY QUAN CAFE - NHOM 2 =====");
                System.out.println("1. Quan ly mon");
                System.out.println("2. Quan ly ban");
                System.out.println("3. Quan ly khach hang");
                System.out.println("4. Quan ly hoa don");
                System.out.println("5. Thong ke");
                System.out.println("0. Thoat");
                int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 5);
                switch (chon) {
                    case 1 -> menuMon();
                    case 2 -> menuBan();
                    case 3 -> menuKhach();
                    case 4 -> menuHoaDon();
                    case 5 -> menuThongKe();
                    default -> {
                        luu();
                        System.out.println("Tam biet!");
                        return;
                    }
                }
            }
        } catch (IllegalStateException e) {
            // het input (vd chay bang file) thi luu roi thoat
            luu();
            System.out.println("Ket thuc: " + e.getMessage());
        }
    }

    // luu sau moi thao tac de tat may khong mat du lieu
    private static void luu() {
        try {
            qc.luuDuLieu();
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    private static <T> void inDanhSach(List<T> ds) {
        if (ds.isEmpty()) {
            System.out.println("(Danh sach rong)");
        }
        for (T x : ds) {
            System.out.println(x);
        }
    }

    // ========== MON ==========
    private static void menuMon() {
        while (true) {
            System.out.println("\n--- QUAN LY MON ---");
            System.out.println("1. Xem menu  2. Them  3. Sua  4. Xoa  5. Tim theo ten  6. Tim theo gia  0. Quay lai");
            int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 6);
            if (chon == 0) {
                return;
            }
            try {
                switch (chon) {
                    case 1 -> inDanhSach(qc.getDsMon());
                    case 2 -> themMon();
                    case 3 -> {
                        Mon m = qc.timMon(NhapLieu.nhapChuoi("Ma mon can sua: "));
                        System.out.println(m);
                        String ten = NhapLieu.nhapChuoi("Ten moi (Enter de giu): ", true);
                        double gia = NhapLieu.nhapSoThuc("Gia moi (0 de giu): ");
                        if (gia != 0) {
                            m.setGia(gia);
                        }
                        if (!ten.isEmpty()) {
                            m.setTenMon(ten);
                        }
                        System.out.println("Da sua: " + m);
                    }
                    case 4 -> {
                        qc.xoaMon(NhapLieu.nhapChuoi("Ma mon can xoa: "));
                        System.out.println("Da xoa mon.");
                    }
                    case 5 -> inDanhSach(qc.timMonTheoTen(NhapLieu.nhapChuoi("Tu khoa: ")));
                    case 6 -> {
                        double tu = NhapLieu.nhapSoThuc("Gia tu: ");
                        double den = NhapLieu.nhapSoThuc("Gia den: ");
                        inDanhSach(qc.timMon(tu, den));
                    }
                }
            } catch (KhongTimThayException | DuLieuSaiException e) {
                System.out.println("Loi: " + e.getMessage());
            }
            luu();
        }
    }

    private static void themMon() {
        int loai = NhapLieu.nhapSoNguyen("Loai (1-Do an, 2-Do uong, 3-Combo): ", 1, 3);
        String ma = NhapLieu.nhapChuoi("Ma mon: ");
        String ten = NhapLieu.nhapChuoi("Ten mon: ");
        double gia = NhapLieu.nhapSoThuc("Gia: ");
        String them;
        String tenLoai;
        if (loai == 1) {
            tenLoai = "DoAn";
            them = NhapLieu.nhapChuoi("Don vi (phan/cai/dia): ");
        } else if (loai == 2) {
            tenLoai = "DoUong";
            them = NhapLieu.nhapChuoi("Size (S/M/L): ");
        } else {
            tenLoai = "Combo";
            them = NhapLieu.nhapChuoi("Mo ta combo: ");
        }
        Mon m = MonFactory.taoMon(tenLoai, ma, ten, gia, them);
        qc.themMon(m);
        System.out.println("Da them: " + m);
    }

    // ========== BAN ==========
    private static void menuBan() {
        while (true) {
            System.out.println("\n--- QUAN LY BAN ---");
            System.out.println("1. Xem tat ca  2. Ban trong  3. Them  4. Sua so cho  5. Xoa  6. Tim ban  0. Quay lai");
            int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 6);
            if (chon == 0) {
                return;
            }
            try {
                switch (chon) {
                    case 1 -> inDanhSach(qc.getDsBan());
                    case 2 -> inDanhSach(qc.dsBanTrong());
                    case 3 -> {
                        int so = NhapLieu.nhapSoNguyen("So ban: ");
                        int cho = NhapLieu.nhapSoNguyen("So cho: ");
                        qc.themBan(new Ban(so, cho));
                        System.out.println("Da them ban " + so);
                    }
                    case 4 -> {
                        Ban b = qc.timBan(NhapLieu.nhapSoNguyen("So ban can sua: "));
                        b.setSoCho(NhapLieu.nhapSoNguyen("So cho moi: "));
                        System.out.println("Da sua: " + b);
                    }
                    case 5 -> {
                        qc.xoaBan(NhapLieu.nhapSoNguyen("So ban can xoa: "));
                        System.out.println("Da xoa ban.");
                    }
                    case 6 -> System.out.println(qc.timBan(NhapLieu.nhapSoNguyen("So ban: ")));
                }
            } catch (KhongTimThayException | DuLieuSaiException e) {
                System.out.println("Loi: " + e.getMessage());
            }
            luu();
        }
    }

    // ========== KHACH HANG ==========
    private static void menuKhach() {
        while (true) {
            System.out.println("\n--- QUAN LY KHACH HANG ---");
            System.out.println("1. Xem  2. Them  3. Sua  4. Xoa  5. Tim theo ten  6. Tim theo ma  0. Quay lai");
            int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 6);
            if (chon == 0) {
                return;
            }
            try {
                switch (chon) {
                    case 1 -> inDanhSach(qc.getDsKhach());
                    case 2 -> {
                        String ma = NhapLieu.nhapChuoi("Ma KH: ");
                        String ten = NhapLieu.nhapChuoi("Ten: ");
                        String sdt = NhapLieu.nhapChuoi("SDT: ");
                        qc.themKhach(new KhachHang(ma, ten, sdt));
                        System.out.println("Da them khach " + ten);
                    }
                    case 3 -> {
                        KhachHang kh = qc.timKhach(NhapLieu.nhapChuoi("Ma KH can sua: "));
                        String ten = NhapLieu.nhapChuoi("Ten moi (Enter de giu): ", true);
                        if (!ten.isEmpty()) {
                            kh.setTenKH(ten);
                        }
                        String sdt = NhapLieu.nhapChuoi("SDT moi (Enter de giu): ", true);
                        if (!sdt.isEmpty()) {
                            qc.doiSdt(kh, sdt);
                        }
                        System.out.println("Da sua: " + kh);
                    }
                    case 4 -> {
                        qc.xoaKhach(NhapLieu.nhapChuoi("Ma KH can xoa: "));
                        System.out.println("Da xoa khach.");
                    }
                    case 5 -> inDanhSach(qc.timKhachTheoTen(NhapLieu.nhapChuoi("Tu khoa: ")));
                    case 6 -> System.out.println(qc.timKhach(NhapLieu.nhapChuoi("Ma KH: ")));
                }
            } catch (KhongTimThayException | DuLieuSaiException e) {
                System.out.println("Loi: " + e.getMessage());
            }
            luu();
        }
    }

    // ========== HOA DON ==========
    private static void menuHoaDon() {
        while (true) {
            System.out.println("\n--- QUAN LY HOA DON ---");
            System.out.println("1. HD chua thanh toan  2. Tao HD  3. Goi mon  4. Bot mon  5. Thanh toan");
            System.out.println("6. Huy HD  7. Tim HD  8. Xem tat ca  0. Quay lai");
            int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 8);
            if (chon == 0) {
                return;
            }
            try {
                switch (chon) {
                    case 1 -> inDanhSach(qc.dsChuaThanhToan());
                    case 2 -> {
                        int soBan = NhapLieu.nhapSoNguyen("So ban: ");
                        String maKH = NhapLieu.nhapChuoi("Ma KH (Enter neu khach le): ", true);
                        HoaDon hd = qc.taoHoaDon(soBan, maKH);
                        System.out.println("Da tao hoa don " + hd.getMaHD());
                    }
                    case 3 -> {
                        HoaDon hd = qc.timHoaDon(NhapLieu.nhapChuoi("Ma HD: "));
                        Mon m = qc.timMon(NhapLieu.nhapChuoi("Ma mon: "));
                        int sl = NhapLieu.nhapSoNguyen("So luong: ");
                        hd.themMon(m, sl);
                        System.out.println(hd);
                    }
                    case 4 -> {
                        HoaDon hd = qc.timHoaDon(NhapLieu.nhapChuoi("Ma HD: "));
                        hd.botMon(NhapLieu.nhapChuoi("Ma mon: "), NhapLieu.nhapSoNguyen("So luong bot: "));
                        System.out.println(hd);
                    }
                    case 5 -> {
                        HoaDon hd = qc.timHoaDon(NhapLieu.nhapChuoi("Ma HD: "));
                        System.out.println(hd);
                        if (NhapLieu.nhapSoNguyen("Xac nhan (1-Co, 0-Khong): ", 0, 1) == 1) {
                            hd.thanhToan();
                            System.out.println("Thanh toan thanh cong " + hd.dinhDangTien());
                        }
                    }
                    case 6 -> {
                        qc.huyHoaDon(NhapLieu.nhapChuoi("Ma HD can huy: "));
                        System.out.println("Da huy hoa don.");
                    }
                    case 7 -> System.out.println(qc.timHoaDon(NhapLieu.nhapChuoi("Ma HD: ")));
                    case 8 -> inDanhSach(qc.getDsHoaDon());
                }
            } catch (KhongTimThayException | DuLieuSaiException e) {
                System.out.println("Loi: " + e.getMessage());
            }
            luu();
        }
    }

    // ========== THONG KE ==========
    private static void menuThongKe() {
        while (true) {
            System.out.println("\n--- THONG KE ---");
            System.out.println("1. Top 5 mon ban chay  2. Doanh thu theo thang  0. Quay lai");
            int chon = NhapLieu.nhapSoNguyen("Chon: ", 0, 2);
            if (chon == 0) {
                return;
            }
            if (chon == 1) {
                List<Map.Entry<Mon, Integer>> top = qc.topMonBanChay(5);
                if (top.isEmpty()) {
                    System.out.println("Chua co hoa don nao thanh toan");
                }
                int hang = 1;
                for (Map.Entry<Mon, Integer> e : top) {
                    System.out.printf("%d. %-22s %d ly/phan%n", hang++, e.getKey().getTenMon(), e.getValue());
                }
            } else {
                int nam = NhapLieu.nhapSoNguyen("Nam: ", 2000, LocalDate.now().getYear());
                double[] dt = qc.doanhThuTheoThang(nam);
                double tong = 0;
                for (int i = 0; i < 12; i++) {
                    System.out.printf("Thang %2d: %,15.0f VND%n", i + 1, dt[i]);
                    tong += dt[i];
                }
                System.out.printf("Ca nam : %,15.0f VND%n", tong);
            }
        }
    }
}
