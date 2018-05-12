package me.ianhe.isite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author iHelin
 * @since 2017/11/9 19:44
 */
public class User {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    private String openId;

    private String nickName;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String headimgurl;

    private Date subscribeTime;

    private Integer status;

    private Integer subscribe;

    private String language;

    private String unionid;

    private String remark;

    private Integer groupid;

    private String tagidList;

    private String phone;

    private Boolean isBind;

    private List<SysRole> sysRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBind() {
        return isBind;
    }

    public void setBind(Boolean bind) {
        isBind = bind;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsBind() {
        return isBind;
    }

    public void setIsBind(Boolean isBind) {
        this.isBind = isBind;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

}