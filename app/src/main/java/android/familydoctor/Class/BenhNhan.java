package android.familydoctor.Class;

/**
 * Created by buimi on 6/24/2017.
 */

public class BenhNhan {

    String hoten;
    String sdt;
    String email;
    String diachi;
    String imgUserURL;

    //GPS ID
    float x;
    float y;

    public BenhNhan(String hoten, String sdt, String email, String diachi, String imgUserURL, float x, float y) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
        this.imgUserURL = imgUserURL;
        this.x = x;
        this.y = y;
    }

    public BenhNhan() {
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
