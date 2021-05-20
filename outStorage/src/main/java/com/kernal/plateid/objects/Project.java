package com.kernal.plateid.objects;

import com.alibaba.fastjson.JSONArray;

public class Project extends SCItem{
    private String projectNumber;
    private int admin,consignee,corporation,customer,manufacturer;
    private long createTime,updateTime;
    private boolean ifDeleted;
    private JSONArray materials;

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getConsignee() {
        return consignee;
    }

    public void setConsignee(int consignee) {
        this.consignee = consignee;
    }

    public int getCorporation() {
        return corporation;
    }

    public void setCorporation(int corporation) {
        this.corporation = corporation;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
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

    public JSONArray getMaterials() {
        return materials;
    }

    public void setMaterials(JSONArray materials) {
        this.materials = materials;
    }
}
