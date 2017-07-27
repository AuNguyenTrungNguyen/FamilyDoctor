package android.familydoctor.Class;

/**
 * Created by quocb14005xx on 7/21/2017.
 */

public class TinTuc {
    String title;
    String link;
    String image;
    String publish;

    public TinTuc() {
    }



    public TinTuc(String title, String link, String image) {
        this.title = title;
        this.link = link;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
}
