package ddwucom.mobile.finalproject.ma01_20170922;

import java.io.Serializable;

public class SpendingDto implements Serializable {
    private long _id;
    private String title;
    private String price;
    private String category;
    private String date;

    public SpendingDto(String title, String price, String category, String date) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public SpendingDto(long _id, String title, String price, String category, String date) {
        this._id = _id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.date = date;
    }

    public long getId() { return _id; }

    public void setId(long _id) { this._id = _id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}

