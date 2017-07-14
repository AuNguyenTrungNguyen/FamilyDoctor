package android.familydoctor.Adapter;

/**
 * Created by Admin on 5/27/2017.
 */

public class Item {
    private String src;
    private String ten;
    private String nguoitao;
    private String loai;
    private String gia;
    private String id;




    public Item() {
    }

    public Item(String src, String ten, String nguoitao, String loai,String gia, String id) {
        this.src = src;
        this.ten = ten;
        this.nguoitao = nguoitao;
        this.loai = loai;
        this.gia = gia;
        this.id = id;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNguoitao() {
        return nguoitao;
    }

    public void setNguoitao(String nguoitao) {
        this.nguoitao = nguoitao;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
