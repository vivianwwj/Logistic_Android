package com.android.buildinstorageform.data_class;

public class Manufacturer_class {
    private String passWord,address,manName,corporation,updateTime,userName,
    userId,ifDeleted,createTime,phone,name,workshops;
    private Number id;

    @Override
    public String toString() {
        return "Manufacturer_class{" +
                "passWord='" + passWord + '\'' +
                ", address='" + address + '\'' +
                ", manName='" + manName + '\'' +
                ", corporation='" + corporation + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", ifDeleted='" + ifDeleted + '\'' +
                ", createTime='" + createTime + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", workshops='" + workshops + '\'' +
                ", id=" + id +
                '}';
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(String ifDeleted) {
        this.ifDeleted = ifDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getWorkshops() {
        return workshops;
    }

    public void setWorkshops(String workshops) {
        this.workshops = workshops;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }
}
