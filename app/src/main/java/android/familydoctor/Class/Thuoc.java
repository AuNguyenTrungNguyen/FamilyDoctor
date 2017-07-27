package android.familydoctor.Class;

import java.io.Serializable;

/**
 * Created by buimi on 7/14/2017.
 */

public class Thuoc implements Serializable {
    private String tenThuoc;
    private String soLuong;
    private String lieuDungSang;
    private String lieuDungTrua;
    private String lieuDungChieu;

    public Thuoc() {

    }

    public Thuoc(String tenThuoc, String soLuong, String lieuDungSang, String lieuDungTrua, String lieuDungChieu) {
        this.tenThuoc = tenThuoc;
        this.soLuong = soLuong;
        this.lieuDungSang = lieuDungSang;
        this.lieuDungTrua = lieuDungTrua;
        this.lieuDungChieu = lieuDungChieu;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getLieuDungSang() {
        return lieuDungSang;
    }

    public void setLieuDungSang(String lieuDungSang) {
        this.lieuDungSang = lieuDungSang;
    }

    public String getLieuDungTrua() {
        return lieuDungTrua;
    }

    public void setLieuDungTrua(String lieuDungTrua) {
        this.lieuDungTrua = lieuDungTrua;
    }

    public String getLieuDungChieu() {
        return lieuDungChieu;
    }

    public void setLieuDungChieu(String lieuDungChieu) {
        this.lieuDungChieu = lieuDungChieu;
    }
}
