package Model;

import java.io.Serializable;
import java.util.Date;

public class NhanVien implements Serializable {
    private String maNV;
    private String hoTen;
    private Date ngaySinh;
    private String sdt;
    private String chucVu;
    private double luongCoBan;
    private String maPB;
    private String maCN;

    public NhanVien(String maNV, String hoTen, Date ngaySinh, String sdt, String chucVu, double luongCoBan, String maPB, String maCN) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.chucVu = chucVu;
        this.luongCoBan = luongCoBan;
        this.maPB = maPB;
        this.maCN = maCN;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public String getChucVu() {
        return chucVu;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public String getMaPB() {
        return maPB;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }
}