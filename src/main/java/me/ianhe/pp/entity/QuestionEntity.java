package me.ianhe.pp.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-04-16 12:00:30
 */
@TableName("t_question")
public class QuestionEntity {

    @TableId
    private Integer id;
    private String question;
    private String answer;
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id='" + id + '\'' +
                "question='" + question + '\'' +
                "answer='" + answer + '\'' +
                "version='" + version + '\'' +
                "sort='" + sort + '\'' +
                "createTime='" + createTime + '\'' +
                "updateTime='" + updateTime + '\'' +
                '}';
    }

}
