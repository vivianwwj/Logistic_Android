package com.android.buildinstorageform.data_class;

public class InStorageFormForCreate_class {
    private Number corporation,project,warehouse,truck,pickWorker,lister,accountStatus;

    @Override
    public String toString() {
        return "InStorageForm_class{" +
                "corporation=" + corporation +
                ", project=" + project +
                ", warehouse=" + warehouse +
                ", truck=" + truck +
                ", pickWorker=" + pickWorker +
                ", lister=" + lister +
                ", accountStatus=" + accountStatus +
                '}';
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getProject() {
        return project;
    }

    public void setProject(Number project) {
        this.project = project;
    }

    public Number getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Number warehouse) {
        this.warehouse = warehouse;
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

    public String postBody_createInStorageForm(){
        return "corporation=" + corporation + "&project=" + project+ "&warehouse=" + warehouse+ "&truck=" + truck+ "&pickWorker=" + pickWorker+ "&lister=" + lister+"&accountStatus="+accountStatus;
    }
}
