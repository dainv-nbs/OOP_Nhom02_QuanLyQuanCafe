package model;

import ngoaile.DuLieuSaiException;

// ban trong quan
public class Ban {

    private int soBan;
    private int soCho;
    private boolean coKhach;

    public Ban(int soBan, int soCho, boolean coKhach) {
        setSoBan(soBan);
        setSoCho(soCho);
        this.coKhach = coKhach;
    }

    // ban moi thi chua co khach
    public Ban(int soBan, int soCho) {
        this(soBan, soCho, false);
    }

    public String toCsv() {
        return soBan + "," + soCho + "," + coKhach;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        if (soBan <= 0) {
            throw new DuLieuSaiException("So ban phai lon hon 0");
        }
        this.soBan = soBan;
    }

    public int getSoCho() {
        return soCho;
    }

    public void setSoCho(int soCho) {
        if (soCho < 1 || soCho > 20) {
            throw new DuLieuSaiException("So cho tu 1 den 20");
        }
        this.soCho = soCho;
    }

    public boolean isCoKhach() {
        return coKhach;
    }

    public void setCoKhach(boolean coKhach) {
        this.coKhach = coKhach;
    }

    @Override
    public String toString() {
        return String.format("Ban %-3d %2d cho   %s", soBan, soCho, coKhach ? "Co khach" : "Trong");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ban)) {
            return false;
        }
        return soBan == ((Ban) o).soBan;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(soBan);
    }
}
