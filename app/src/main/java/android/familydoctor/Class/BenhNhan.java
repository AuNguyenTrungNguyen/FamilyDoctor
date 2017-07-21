package android.familydoctor.Class;

/**
 * Created by buimi on 6/24/2017.
 */

public class BenhNhan {

    private String soDienThoaiBenhNhan;
    private String namSinhBenhNhan;
    private String hotTenBenhNhan;
    private String emailBenhNhan;
    private String diaChiBenhNhan;
    private String uriBenhNhan;
    private String xBenhNhan;
    private String yBenhNhan;

    public BenhNhan() {
    }

    public BenhNhan(String soDienThoaiBenhNhan, String namSinhBenhNhan, String hotTenBenhNhan, String emailBenhNhan, String diaChiBenhNhan, String uriBenhNhan, String xBenhNhan, String yBenhNhan) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
        this.namSinhBenhNhan = namSinhBenhNhan;
        this.hotTenBenhNhan = hotTenBenhNhan;
        this.emailBenhNhan = emailBenhNhan;
        this.diaChiBenhNhan = diaChiBenhNhan;
        this.uriBenhNhan = uriBenhNhan;
        this.xBenhNhan = xBenhNhan;
        this.yBenhNhan = yBenhNhan;
    }

    public BenhNhan(String hotTenBenhNhan, String namSinhBenhNhan, String soDienThoaiBenhNhan, String diaChiBenhNhan) {
        this.soDienThoaiBenhNhan = soDienThoaiBenhNhan;
        this.namSinhBenhNhan = namSinhBenhNhan;
        this.hotTenBenhNhan = hotTenBenhNhan;
        this.diaChiBenhNhan = diaChiBenhNhan;
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

    public String getHotTenBenhNhan() {
        return hotTenBenhNhan;
    }

    public void setHotTenBenhNhan(String hotTenBenhNhan) {
        this.hotTenBenhNhan = hotTenBenhNhan;
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

    public String getUriBenhNhan() {
        return uriBenhNhan;
    }

    public void setUriBenhNhan(String uriBenhNhan) {
        this.uriBenhNhan = uriBenhNhan;
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
