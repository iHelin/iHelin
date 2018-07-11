package me.ianhe.isite.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Score
 *
 * @author linhe2
 * @since 2018/7/9 12:37
 */
public class Score implements Serializable {

    private static final long serialVersionUID = -7279480633434967639L;

    private Integer id;

    private Integer value;

    private String writer;

    private Date createTime;

    private Date updateTime;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}