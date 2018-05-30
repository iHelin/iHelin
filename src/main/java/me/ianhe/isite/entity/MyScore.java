package me.ianhe.isite.entity;

import java.util.Date;

public class MyScore {
    private Integer id;

    private Integer score;

    private Integer addWriter;

    private Date addDate;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAddWriter() {
        return addWriter;
    }

    public void setAddWriter(Integer addWriter) {
        this.addWriter = addWriter;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}