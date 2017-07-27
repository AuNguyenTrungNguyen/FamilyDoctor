package android.familydoctor.Class;

/**
 * Created by buimi on 6/24/2017.
 */

public class BenhNhan {

    private String soDienThoaiBenhNhan;
    private String namSinhBenhNhan;
    private String hoTenBenhNhan;
    private String emailBenhNhan;
    private String diaChiBenhNhan;
    private String uriHinhAnhBenhNhan;
    private double xBenhNhan;
    private double yBenhNhan;


    public BenhNhan() {
    }

    public BenhNhan(String hoTenBenhNhan, String namSinhBenhNhan, String soDienThoaiBenhNhan, String diaChiBenhNhan, double x, double y) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
        this.namSinhBenhNhan = namSinhBenhNhan;
        this.hoTenBenhNhan = hoTenBenhNhan;
        this.diaChiBenhNhan = diaChiBenhNhan;
        xBenhNhan=x;
        yBenhNhan=y;
    }

    public BenhNhan(String soDienThoaiBenhNhan, String namSinhBenhNhan, String hoTenBenhNhan, String emailBenhNhan, String diaChiBenhNhan, String uriHinhAnhBenhNhan, double xBenhNhan, double yBenhNhan) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
        this.namSinhBenhNhan = namSinhBenhNhan;
        this.hoTenBenhNhan = hoTenBenhNhan;
        this.emailBenhNhan = emailBenhNhan;
        this.diaChiBenhNhan = diaChiBenhNhan;
        this.uriHinhAnhBenhNhan = uriHinhAnhBenhNhan;
        this.xBenhNhan = xBenhNhan;
        this.yBenhNhan = yBenhNhan;
    }

    public String getSoDienThoaiBenhNhan() {
        return soDienThoaiBenhNhan;
    }

    public void setSoDienThoaiBenhNhan(String soDienThoaiBenhNhan) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
    }

    public String getNamSinhBenhNhan() {
        return namSinhBenhNhan;
    }

    public void setNamSinhBenhNhan(String namSinhBenhNhan) {
        this.namSinhBenhNhan = namSinhBenhNhan;
    }

    public String getHoTenBenhNhan() {
        return hoTenBenhNhan;
    }

    public void setHoTenBenhNhan(String hoTenBenhNhan) {
        this.hoTenBenhNhan = hoTenBenhNhan;
    }

    public String getEmailBenhNhan() {
        return emailBenhNhan;
    }

    public void setEmailBenhNhan(String emailBenhNhan) {
        this.emailBenhNhan = emailBenhNhan;
    }

    public String getDiaChiBenhNhan() {
        return diaChiBenhNhan;
    }

    public void setDiaChiBenhNhan(String diaChiBenhNhan) {
        this.diaChiBenhNhan = diaChiBenhNhan;
    }

    public String getUriHinhAnhBenhNhan() {
        return uriHinhAnhBenhNhan;
    }

    public void setUriHinhAnhBenhNhan(String uriHinhAnhBenhNhan) {
        this.uriHinhAnhBenhNhan = uriHinhAnhBenhNhan;
    }

    public double getxBenhNhan() {
        return xBenhNhan;
    }

    public void setxBenhNhan(double xBenhNhan) {
        this.xBenhNhan = xBenhNhan;
    }

    public double getyBenhNhan() {
        return yBenhNhan;
    }

    public void setyBenhNhan(double yBenhNhan) {
        this.yBenhNhan = yBenhNhan;
    }
}
