package com.wdy.list.objects;


import com.alibaba.fastjson.JSONArray;

public class ListOnTruck extends SCItem {
    private String accountStatus;
    private int corporation;
    private long createTime;
    private String formNumber;
    private String ifCompleted;
    private boolean ifDeleted;
    private int outStorageForm;
    private JSONArray pictures;
    private int project;
    private int qualityTestMan;
    private String signMan;
    private long signTime;
    private int tallyMan;
    private long updateTime;

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

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
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

    public int getOutStorageForm() {
        return outStorageForm;
    }

    public void setOutStorageForm(int outStorageForm) {
        this.outStorageForm = outStorageForm;
    }

    public JSONArray getPictures() {
        return pictures;
    }

    public void setPictures(JSONArray pictures) {
        this.pictures = pictures;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getQualityTestMan() {
        return qualityTestMan;
    }

    public void setQualityTestMan(int qualityTestMan) {
        this.qualityTestMan = qualityTestMan;
    }

    public String getSignMan() {
        return signMan;
    }

    public void setSignMan(String signMan) {
        this.signMan = signMan;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public int getTallyMan() {
        return tallyMan;
    }

    public void setTallyMan(int tallyMan) {
        this.tallyMan = tallyMan;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
