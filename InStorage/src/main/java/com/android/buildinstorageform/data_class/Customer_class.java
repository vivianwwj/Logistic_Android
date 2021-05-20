package com.android.buildinstorageform.data_class;

public class Customer_class {
    private String ifDeleted,passWord,phone,name,userName;
    private Number createTime,corporation,updateTime,id,userId;

    @Override
    public String toString() {
        return "Customer_class{" +
                "ifDeleted='" + ifDeleted + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", corporation=" + corporation +
                ", updateTime=" + updateTime +
                ", id=" + id +
                ", userId=" + userId +
                '}';
    }

    public String getIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(String ifDeleted) {
        this.ifDeleted = ifDeleted;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Number getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Number createTime) {
        this.createTime = createTime;
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Number updateTime) {
        this.updateTime = updateTime;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getUserId() {
        return userId;
    }

    public void setUserId(Number userId) {
        this.userId = userId;
    }
}
