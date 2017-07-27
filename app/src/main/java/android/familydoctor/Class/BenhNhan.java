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
    private String xBenhNhan;
    private String yBenhNhan;


    public BenhNhan() {
    }

    public BenhNhan(String soDienThoaiBenhNhan, String namSinhBenhNhan, String hoTenBenhNhan, String diaChiBenhNhan) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
        this.namSinhBenhNhan = namSinhBenhNhan;
        this.hoTenBenhNhan = hoTenBenhNhan;
        this.diaChiBenhNhan = diaChiBenhNhan;
    }

    public BenhNhan(String soDienThoaiBenhNhan, String namSinhBenhNhan, String hoTenBenhNhan, String emailBenhNhan, String diaChiBenhNhan, String uriHinhAnhBenhNhan, String xBenhNhan, String yBenhNhan) {
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

    public String getxBenhNhan() {
        return xBenhNhan;
    }

    public void setxBenhNhan(String xBenhNhan) {
        this.xBenhNhan = xBenhNhan;
    }

    public String getyBenhNhan() {
        return yBenhNhan;
    }

    public void setyBenhNhan(String yBenhNhan) {
        this.yBenhNhan = yBenhNhan;
    }
}
