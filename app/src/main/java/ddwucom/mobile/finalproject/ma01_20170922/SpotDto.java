package ddwucom.mobile.finalproject.ma01_20170922;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class SpotDto implements Serializable {
    private int _id;
    private String title;
    private String address;
    private String mapx;
    private String mapy;
    private String imageLink;
    private String imageFileName;

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id; }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
    }

    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getMapx() { return mapx; }

    public void setMapx(String mapx) { this.mapx = mapx; }

    public String getMapy() { return mapy; }

    public void setMapy(String mapy) { this.mapy = mapy; }

    public String getImageLink() { return imageLink; }

    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public String getImageFileName() { return imageFileName; }

    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }
}
