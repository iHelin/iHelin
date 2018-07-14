package me.ianhe.isite.entity;

/**
 * Poem
 *
 * @author linhe2
 * @since 2018/7/9 12:38
 */
public class Poem {
    private Integer id;

    private String title;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}