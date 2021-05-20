package com.android.buildinstorageform.data_class;

public class Truck_class {
    private Number id,corporation,createTime,updateTime;
    private String ifDeleted,name,phone,carNumber;

    @Override
    public String toString() {
        return "Truck_class{" +
                "id=" + id +
                ", corporation=" + corporation +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", ifDeleted='" + ifDeleted + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", carNumber='" + carNumber + '\'' +
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

    public String getIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(String ifDeleted) {
        this.ifDeleted = ifDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
