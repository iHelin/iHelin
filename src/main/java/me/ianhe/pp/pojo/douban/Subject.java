package me.ianhe.pp.pojo.douban;

/**
 * @author iHelin
 * @since 2017/12/1 15:00
 */
public class Subject {

    private String rate;
    private int cover_x;
    private String title;
    private String url;
    private boolean playable;
    private String cover;
    private String id;
    private int cover_y;
    private boolean is_new;

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setCover_x(int cover_x) {
        this.cover_x = cover_x;
    }

    public int getCover_x() {
        return cover_x;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public boolean getPlayable() {
        return playable;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCover_y(int cover_y) {
        this.cover_y = cover_y;
    }

    public int getCover_y() {
        return cover_y;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }

    public boolean getIs_new() {
        return is_new;
    }
}
