package android.familydoctor.Class;

/**
 * Created by buimi on 6/24/2017.
 */

public class BenhNhan {
    String id, hoten, sdt, email, khuvuc,linhvucchuyenmon,kynang,diachi;
    String imgUserURL;

    public BenhNhan(String id, String hoten, String sdt, String email, String khuvuc, String linhvucchuyenmon, String kynang, String diachi, String imgUserURL) {
        this.id = id;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.khuvuc = khuvuc;
        this.linhvucchuyenmon = linhvucchuyenmon;
        this.kynang = kynang;
        this.diachi = diachi;
        this.imgUserURL = imgUserURL;
    }

    public BenhNhan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKhuvuc() {
        return khuvuc;
    }

    public void setKhuvuc(String khuvuc) {
        this.khuvuc = khuvuc;
    }

    public String getLinhvucchuyenmon() {
        return linhvucchuyenmon;
    }

    public void setLinhvucchuyenmon(String linhvucchuyenmon) {
        this.linhvucchuyenmon = linhvucchuyenmon;
    }

    public String getKynang() {
        return kynang;
    }

    public void setKynang(String kynang) {
        this.kynang = kynang;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getImgUserURL() {
        return imgUserURL;
    }

    public void setImgUserURL(String imgUserURL) {
        this.imgUserURL = imgUserURL;
    }
}
