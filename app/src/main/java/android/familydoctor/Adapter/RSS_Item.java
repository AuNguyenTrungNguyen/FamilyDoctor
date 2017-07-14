package android.familydoctor.Adapter;

/**
 * Created by buimi on 6/25/2017.
 */

public class RSS_Item {
    private String tieude;
    private String url_img;
    private String noidung;
    private String link;

    public RSS_Item(String tieude, String url_img,String noidung, String link) {
        this.tieude = tieude;
        this.url_img = url_img;
        this.noidung=noidung;
        this.link = link;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public RSS_Item() {
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
