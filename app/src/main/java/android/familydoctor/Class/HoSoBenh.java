package android.familydoctor.Class;

import java.util.List;

/**
 * Created by buimi on 6/24/2017.
 */

public class HoSoBenh {

    //ID Năm,tháng,ngày,giờ,phút, giây , miligiay

    private String idBacSi;
    private String idBenhNhan;
    private String idHoSo;
    private String tenBenh;
    private String ngayKham;
    private String ngayTaiKham;
    private List<Thuoc> thuocDung;




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

    public String getIdHoSo() {
        return idHoSo;
    }

    public void setIdHoSo(String idHoSo) {
        this.idHoSo = idHoSo;
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
