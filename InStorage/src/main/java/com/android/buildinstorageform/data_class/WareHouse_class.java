package com.android.buildinstorageform.data_class;

import java.util.ArrayList;

public class WareHouse_class {
    private Number admin,corporation,id;
    private String address,createTime,ifDeleted,name,phone,updateTime;
    private ArrayList driveWorkers,liftWorkers;

    @Override
    public String toString() {
        return "WareHouse_class{" +
                "admin=" + admin +
                ", corporation=" + corporation +
                ", id=" + id +
                ", address='" + address + '\'' +
                ", createTime='" + createTime + '\'' +
                ", ifDeleted='" + ifDeleted + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", driveWorkers=" + driveWorkers +
                ", liftWorkers=" + liftWorkers +
                '}';
    }

    public Number getAdmin() {
        return admin;
    }

    public void setAdmin(Number admin) {
        this.admin = admin;
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList getDriveWorkers() {
        return driveWorkers;
    }

    public void setDriveWorkers(ArrayList driveWorkers) {
        this.driveWorkers = driveWorkers;
    }

    public ArrayList getLiftWorkers() {
        return liftWorkers;
    }

    public void setLiftWorkers(ArrayList liftWorkers) {
        this.liftWorkers = liftWorkers;
    }
}
