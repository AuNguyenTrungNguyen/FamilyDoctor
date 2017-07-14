package android.familydoctor.Class;
public class BacSi {
    String id, hoten, sdt, email, khuvuc,linhvucchuyenmon,kynang,diachi;
    String imgUserURL;

    public String getImgUserURL() {
        return imgUserURL;
    }

    public void setImgUserURL(String imgUserURL) {
        this.imgUserURL = imgUserURL;
    }

    public BacSi() {
    }

    public BacSi(String id, String hoten, String email) {
        this.id = id;
        this.hoten = hoten;
        this.email = email;
    }

    public BacSi(String id, String hoten, String email, String sdt, String khuvuc, String linhvucchuyenmon, String kynang, String diachi) {
        this.id = id;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.khuvuc = khuvuc;
        this.linhvucchuyenmon = linhvucchuyenmon;
        this.kynang = kynang;
        this.diachi = diachi;
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
}