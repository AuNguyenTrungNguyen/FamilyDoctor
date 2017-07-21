package android.familydoctor.Class;

import java.util.List;

/**
 * Created by buimi on 6/24/2017.
 */

public class HoSoBenh {

    //ID Năm,tháng,ngày,giờ,phút, giây , miligiay

    private String idHoSo;
    private String idBacSi;
    private String idBenhNhan;
    private String tenBenh;
    private String ngayKham;
    private String ngayTaiKham;
    private List<Thuoc> thuocDung;


    public HoSoBenh() {
    }

    public HoSoBenh(String idHoSo, String idBacSi, String idBenhNhan, String tenBenh, String ngayKham, String ngayTaiKham, List<Thuoc> thuocDung) {
        this.idHoSo = idHoSo;
        this.idBacSi = idBacSi;
        this.idBenhNhan = idBenhNhan;
        this.tenBenh = tenBenh;
        this.ngayKham = ngayKham;
        this.ngayTaiKham = ngayTaiKham;
        this.thuocDung = thuocDung;
    }

    public String getIdHoSo() {
        return idHoSo;
    }

    public void setIdHoSo(String idHoSo) {
        this.idHoSo = idHoSo;
    }

    public String getIdBacSi() {
        return idBacSi;
    }

    public void setIdBacSi(String idBacSi) {
        this.idBacSi = idBacSi;
    }

    public String getIdBenhNhan() {
        return idBenhNhan;
    }

    public void setIdBenhNhan(String idBenhNhan) {
        this.idBenhNhan = idBenhNhan;
    }

    public String getTenBenh() {
        return tenBenh;
    }

    public void setTenBenh(String tenBenh) {
        this.tenBenh = tenBenh;
    }

    public String getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(String ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getNgayTaiKham() {
        return ngayTaiKham;
    }

    public void setNgayTaiKham(String ngayTaiKham) {
        this.ngayTaiKham = ngayTaiKham;
    }

    public List<Thuoc> getThuocDung() {
        return thuocDung;
    }

    public void setThuocDung(List<Thuoc> thuocDung) {
        this.thuocDung = thuocDung;
    }
}
