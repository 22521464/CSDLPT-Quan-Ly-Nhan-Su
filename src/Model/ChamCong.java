package Model;

import java.io.Serializable;
import java.util.Date;

public class ChamCong implements Serializable {
    private String maCC;
    private String maNV;
    private Date ngayChamCong;
    private String gioVao;
    private String gioRa;
    private String ghiChu;

    public ChamCong(String maCC, String maNV, Date ngayChamCong, String gioVao, String gioRa, String ghiChu) {
        this.maCC = maCC;
        this.maNV = maNV;
        this.ngayChamCong = ngayChamCong;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.ghiChu = ghiChu;
    }

    public String getMaCC() {
        return maCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public Date getNgayChamCong() {
        return ngayChamCong;
    }

    public String getGioVao() {
        return gioVao;
    }

    public String getGioRa() {
        return gioRa;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGioVao(String gioVao) {
        this.gioVao = gioVao;
    }

    public void setGioRa(String gioRa) {
        this.gioRa = gioRa;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}