package android.familydoctor.Class;

/**
 * Created by buimi on 7/14/2017.
 */

public class Thuoc {
    private String idThuoc;
    private String tenThuoc;
    private String soLuong;
    private String lieuDung;

    public Thuoc(String idThuoc, String tenThuoc, String soLuong, String lieuDung) {
        this.idThuoc = idThuoc;
        this.tenThuoc = tenThuoc;
        this.soLuong = soLuong;
        this.lieuDung = lieuDung;
    }

    public Thuoc() {
    }

    public String getIdThuoc() {
        return idThuoc;
    }

    public void setIdThuoc(String idThuoc) {
        this.idThuoc = idThuoc;
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

    public String getLieuDung() {
        return lieuDung;
    }

    public void setLieuDung(String lieuDung) {
        this.lieuDung = lieuDung;
    }
}
