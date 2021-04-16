package me.ianhe.isite.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-03-26 14:39:56
 */
@TableName("t_feedback")
public class FeedbackEntity {

    @TableId
    private Integer id;
    private Integer userId;
    private String name;
    private String contract;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT)
    private Boolean handled;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
    }

    @Override
    public String toString() {
        return "FeedbackEntity{" +
                "id='" + id + '\'' +
                "userId='" + userId + '\'' +
                "name='" + name + '\'' +
                "contract='" + contract + '\'' +
                "content='" + content + '\'' +
                "createTime='" + createTime + '\'' +
                "handled='" + handled + '\'' +
                '}';
    }

}
