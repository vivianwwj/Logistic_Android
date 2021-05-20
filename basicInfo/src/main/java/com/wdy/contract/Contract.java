package com.wdy.contract;

import com.wdy.list.objects.SCItem;

public class Contract extends SCItem {
    private String contractNumber;
    private int corporation   ;
    private long createTime    ;
    private String ifCompleted   ;
    private boolean ifDeleted ;
    private String oilCardType   ;
    private int onTruckForm   ;
    private int outStorageForm;
    private int project       ;
    private int truck         ;
    private long updateTime    ;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public String getOilCardType() {
        return oilCardType;
    }

    public void setOilCardType(String oilCardType) {
        this.oilCardType = oilCardType;
    }

    public int getOnTruckForm() {
        return onTruckForm;
    }

    public void setOnTruckForm(int onTruckForm) {
        this.onTruckForm = onTruckForm;
    }

    public int getOutStorageForm() {
        return outStorageForm;
    }

    public void setOutStorageForm(int outStorageForm) {
        this.outStorageForm = outStorageForm;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
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
}
