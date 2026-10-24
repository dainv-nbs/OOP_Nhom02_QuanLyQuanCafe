import java.util.Scanner;

// nhap tu ban phim, nhap sai thi bat nhap lai chu khong crash
public class NhapLieu {

    private static final Scanner sc = new Scanner(System.in);

    public static String nhapChuoi(String loiNhac) {
        return nhapChuoi(loiNhac, false);
    }

    // choPhepRong = true: Enter de bo qua (dung khi sua)
    public static String nhapChuoi(String loiNhac, boolean choPhepRong) {
        while (true) {
            System.out.print(loiNhac);
            if (!sc.hasNextLine()) {
                throw new IllegalStateException("Het du lieu nhap");
            }
            String s = sc.nextLine().trim();
            if (!s.isEmpty() || choPhepRong) {
                return s;
            }
            System.out.println("Khong duoc de trong!");
        }
    }

    public static int nhapSoNguyen(String loiNhac) {
        while (true) {
            String s = nhapChuoi(loiNhac);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Phai nhap so nguyen!");
            }
        }
    }

    // nap chong: co gioi han min max
    public static int nhapSoNguyen(String loiNhac, int min, int max) {
        while (true) {
            int so = nhapSoNguyen(loiNhac);
            if (so >= min && so <= max) {
                return so;
            }
            System.out.println("Chi nhap tu " + min + " den " + max + "!");
        }
    }

    public static double nhapSoThuc(String loiNhac) {
        while (true) {
            String s = nhapChuoi(loiNhac);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Phai nhap so!");
            }
        }
    }
}
