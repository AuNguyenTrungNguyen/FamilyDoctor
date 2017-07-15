package android.familydoctor.Class;
public class BacSi {

    String hoten;
    String sdt;
    String email;
    String linhvucchuyenmon;
    String diachi;
    String imgUserURL;
    String imgVanBang;
    float x;
    float y;

    public BacSi(String hoten, String sdt, String email, String linhvucchuyenmon, String diachi, String imgUserURL, String imgVanBang, float x, float y) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.linhvucchuyenmon = linhvucchuyenmon;
        this.diachi = diachi;
        this.imgUserURL = imgUserURL;
        this.imgVanBang = imgVanBang;
        this.x = x;
        this.y = y;
    }

    public BacSi() {
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

    public String getLinhvucchuyenmon() {
        return linhvucchuyenmon;
    }

    public void setLinhvucchuyenmon(String linhvucchuyenmon) {
        this.linhvucchuyenmon = linhvucchuyenmon;
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

    public String getImgVanBang() {
        return imgVanBang;
    }

    public void setImgVanBang(String imgVanBang) {
        this.imgVanBang = imgVanBang;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}