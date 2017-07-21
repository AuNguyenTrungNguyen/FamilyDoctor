package android.familydoctor.Class;

/**
 * Created by buimi on 6/24/2017.
 */

public class BenhNhan {

    private String hoten;
    private String sdt;
    private String email;
    private String diachi;
    private String imgUserURL;
    String age;
    String x;
    String y;

    public BenhNhan() {
    }

    public BenhNhan(String hoten, String sdt, String email, String diachi, String imgUserURL, String x, String y) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
        this.imgUserURL = imgUserURL;
        this.x = x;
        this.y = y;
    }

    public BenhNhan(String hoTen, String namSinh, String sdt, String diaChi) {
        hoten=hoTen;
        age=namSinh;
        this.sdt=sdt;
        diachi=diaChi;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
