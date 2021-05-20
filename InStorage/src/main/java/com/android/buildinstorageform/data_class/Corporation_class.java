package com.android.buildinstorageform.data_class;

import java.io.Serializable;

public class Corporation_class implements Serializable {
    private Number id,corporation,createTime,updateTime,userId;
    private String IfDeleted,Name,passWord,phone,userName;

    public Corporation_class() {

    }

    @Override
    public String toString() {
        return "Corporation_class{" +
                "id=" + id +
                ", corporation=" + corporation +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userId=" + userId +
                ", IfDeleted='" + IfDeleted + '\'' +
                ", Name='" + Name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Number createTime) {
        this.createTime = createTime;
    }

    public Number getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Number updateTime) {
        this.updateTime = updateTime;
    }

    public Number getUserId() {
        return userId;
    }

    public void setUserId(Number userId) {
        this.userId = userId;
    }

    public String getIfDeleted() {
        return IfDeleted;
    }

    public void setIfDeleted(String ifDeleted) {
        IfDeleted = ifDeleted;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
