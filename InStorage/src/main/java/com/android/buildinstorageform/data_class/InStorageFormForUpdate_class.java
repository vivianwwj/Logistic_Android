package com.android.buildinstorageform.data_class;

public class InStorageFormForUpdate_class {
    private Number entityId,wareHouse,truck,pickWorker,lister,accountStatus;


    public String getPostBody() {
        return "entityId=" + entityId +
                "&wareHouse=" + wareHouse +
                "&truck=" + truck +
                "&pickWorker=" + pickWorker +
                "&lister=" + lister +
                "&accountStatus=" + accountStatus;
    }

    @Override
    public String toString() {
        return "InStorageFormForUpdate_class{" +
                "entityId=" + entityId +
                ", wareHouse=" + wareHouse +
                ", truck=" + truck +
                ", pickWorker=" + pickWorker +
                ", lister=" + lister +
                ", accountStatus=" + accountStatus +
                '}';
    }

    public Number getEntityId() {
        return entityId;
    }

    public void setEntityId(Number entityId) {
        this.entityId = entityId;
    }

    public Number getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(Number wareHouse) {
        this.wareHouse = wareHouse;
    }

    public Number getTruck() {
        return truck;
    }

    public void setTruck(Number truck) {
        this.truck = truck;
    }

    public Number getPickWorker() {
        return pickWorker;
    }

    public void setPickWorker(Number pickWorker) {
        this.pickWorker = pickWorker;
    }

    public Number getLister() {
        return lister;
    }

    public void setLister(Number lister) {
        this.lister = lister;
    }

    public Number getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Number accountStatus) {
        this.accountStatus = accountStatus;
    }
}
