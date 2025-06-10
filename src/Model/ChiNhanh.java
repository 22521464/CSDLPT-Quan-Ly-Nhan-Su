package Model;

import java.io.Serializable;

public class ChiNhanh implements Serializable {
    private String maCN;
    private String tenCN;
    private String diaChi;

    public ChiNhanh(String maCN, String tenCN, String diaChi) {
        this.maCN = maCN;
        this.tenCN = tenCN;
        this.diaChi = diaChi;
    }

    public String getMaCN() {
        return maCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}