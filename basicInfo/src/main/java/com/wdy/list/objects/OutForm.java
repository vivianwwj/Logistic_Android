package com.wdy.list.objects;

import com.alibaba.fastjson.JSONArray;

public class OutForm extends SCItem {
    String accountStatus;
    int corporation;
    long createTime;
    float driveWorkerAverageWeight;
    int id;
    String ifCompleted;
    boolean ifDeleted;
    float liftWorkerAverageWeight;
    int lister;
    String outStorageNumber;
    String outStorageStatus;
    long outStorageTime;
    int pickWorker;
    JSONArray products;
    int project;
    int totalAmount;
    float totalVolume;
    float totalWeight;
    int truck;
    long updateTime;
    int warehouse;

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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

    public float getDriveWorkerAverageWeight() {
        return driveWorkerAverageWeight;
    }

    public void setDriveWorkerAverageWeight(float driveWorkerAverageWeight) {
        this.driveWorkerAverageWeight = driveWorkerAverageWeight;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getIfCompleted() {
        return ifCompleted;
    }

    public void setIfCompleted(String ifCompleted) {
        this.ifCompleted = ifCompleted;
    }

    public boolean isIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(boolean ifDeleted) {
        this.ifDeleted = ifDeleted;
    }

    public float getLiftWorkerAverageWeight() {
        return liftWorkerAverageWeight;
    }

    public void setLiftWorkerAverageWeight(float liftWorkerAverageWeight) {
        this.liftWorkerAverageWeight = liftWorkerAverageWeight;
    }

    public int getLister() {
        return lister;
    }

    public void setLister(int lister) {
        this.lister = lister;
    }

    public String getOutStorageNumber() {
        return outStorageNumber;
    }

    public void setOutStorageNumber(String outStorageNumber) {
        this.outStorageNumber = outStorageNumber;
    }

    public String getOutStorageStatus() {
        return outStorageStatus;
    }

    public void setOutStorageStatus(String outStorageStatus) {
        this.outStorageStatus = outStorageStatus;
    }

    public long getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(long outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public int getPickWorker() {
        return pickWorker;
    }

    public void setPickWorker(int pickWorker) {
        this.pickWorker = pickWorker;
    }

    public JSONArray getProducts() {
        return products;
    }

    public void setProducts(JSONArray products) {
        this.products = products;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(float totalVolume) {
        this.totalVolume = totalVolume;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTruck() {
        return truck;
    }

    public void setTruck(int truck) {
        this.truck = truck;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }
}
