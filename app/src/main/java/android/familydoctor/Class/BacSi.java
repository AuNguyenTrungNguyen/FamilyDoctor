package android.familydoctor.Class;
public class BacSi {
    private String hoTenBacSi;
    private String soDienThoaiBacSi;
    private String namSinhBacSi;
    private String emailBacSi;
    private String chuyenMonBacSi;
    private String diaChiBacSi;
    private String uriHinhAnhBacSi;
    private String uriVanBangBacSi;
    private double xBacSi;
    private double yBacSi;

    public BacSi(String hoTenBacSi, String soDienThoaiBacSi, String namSinhBacSi, String diaChiBacSi ,double x , double y ) {
        this.hoTenBacSi = hoTenBacSi;
        this.soDienThoaiBacSi = soDienThoaiBacSi;
        this.namSinhBacSi = namSinhBacSi;
        this.emailBacSi = emailBacSi;
        this.diaChiBacSi = diaChiBacSi;
    }

    public BacSi(String soDienThoaiBacSi, String namSinhBacSi, String hoTenBacSi, String chuyenMonBacSi, String emailBacSi, String diaChiBacSi, String uriHinhAnhBacSi, String uriVanBangBacSi, double xBacSi, double yBacSi) {
        this.soDienThoaiBacSi = soDienThoaiBacSi;
        this.namSinhBacSi = namSinhBacSi;
        this.hoTenBacSi = hoTenBacSi;
        this.chuyenMonBacSi = chuyenMonBacSi;
        this.emailBacSi = emailBacSi;
        this.diaChiBacSi = diaChiBacSi;
        this.uriHinhAnhBacSi = uriHinhAnhBacSi;
        this.uriVanBangBacSi = uriVanBangBacSi;
        this.xBacSi = xBacSi;
        this.yBacSi = yBacSi;
    }

    public String getSoDienThoaiBacSi() {
        return soDienThoaiBacSi;
    }

    public void setSoDienThoaiBacSi(String soDienThoaiBacSi) {
        this.soDienThoaiBacSi = soDienThoaiBacSi;
    }

    public String getNamSinhBacSi() {
        return namSinhBacSi;
    }

    public void setNamSinhBacSi(String namSinhBacSi) {
        this.namSinhBacSi = namSinhBacSi;
    }

    public String getHoTenBacSi() {
        return hoTenBacSi;
    }

    public void setHoTenBacSi(String hoTenBacSi) {
        this.hoTenBacSi = hoTenBacSi;
    }

    public String getChuyenMonBacSi() {
        return chuyenMonBacSi;
    }

    public void setChuyenMonBacSi(String chuyenMonBacSi) {
        this.chuyenMonBacSi = chuyenMonBacSi;
    }

    public String getEmailBacSi() {
        return emailBacSi;
    }

    public void setEmailBacSi(String emailBacSi) {
        this.emailBacSi = emailBacSi;
    }

    public String getDiaChiBacSi() {
        return diaChiBacSi;
    }

    public void setDiaChiBacSi(String diaChiBacSi) {
        this.diaChiBacSi = diaChiBacSi;
    }

    public String getUriHinhAnhBacSi() {
        return uriHinhAnhBacSi;
    }

    public void setUriHinhAnhBacSi(String uriHinhAnhBacSi) {
        this.uriHinhAnhBacSi = uriHinhAnhBacSi;
    }

    public String getUriVanBangBacSi() {
        return uriVanBangBacSi;
    }

    public void setUriVanBangBacSi(String uriVanBangBacSi) {
        this.uriVanBangBacSi = uriVanBangBacSi;
    }

    public double getxBacSi() {
        return xBacSi;
    }

    public void setxBacSi(double xBacSi) {
        this.xBacSi = xBacSi;
    }

    public double getyBacSi() {
        return yBacSi;
    }

    public void setyBacSi(double yBacSi) {
        this.yBacSi = yBacSi;
    }
}