package com.wdy.list.objects;

import com.alibaba.fastjson.JSONArray;

public class Warehouse extends SCItem{
    private String address,phone;
    private int admin,corporation;
    private long createTime,updateTime;
    private boolean ifDeleted;
    private JSONArray driveWorkers,liftWorkers;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getCorporation() {
        return corporation;
    }

    public void setCorporation(int corporation) {
        this.corporation = corporation;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(boolean ifDeleted) {
        this.ifDeleted = ifDeleted;
    }

    public JSONArray getDriveWorkers() {
        return driveWorkers;
    }

    public void setDriveWorkers(JSONArray driveWorkers) {
        this.driveWorkers = driveWorkers;
    }

    public JSONArray getLiftWorkers() {
        return liftWorkers;
    }

    public void setLiftWorkers(JSONArray liftWorkers) {
        this.liftWorkers = liftWorkers;
    }
}
