package ddwucom.mobile.finalproject.ma01_20170922;

import java.io.Serializable;

public class ScheduleDto implements Serializable {
    private long _id;
    private String title;
    private String address;
    private String mapx;
    private String mapy;
    private String date;
    private String time;
    private String memo;

    public ScheduleDto(String title, String address, String mapx, String mapy, String date, String time, String memo) {
        this.title = title;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
        this.date = date;
        this.time = time;
        this.memo = memo;
    }

    public ScheduleDto(long _id, String title, String address, String mapx, String mapy, String date, String time, String memo) {
        this._id = _id;
        this.title = title;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
        this.date = date;
        this.time = time;
        this.memo = memo;
    }

    public long getId() { return _id; }

    public void setId(long _id) { this._id = _id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getMapx() { return mapx; }

    public void setMapx(String mapx) { this.mapx = mapx; }

    public String getMapy() { return mapy; }

    public void setMapy(String mapy) { this.mapy = mapy; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }
}
