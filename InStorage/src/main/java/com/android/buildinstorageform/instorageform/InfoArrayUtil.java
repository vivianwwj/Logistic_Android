package com.android.buildinstorageform.instorageform;


import java.io.Serializable;

public class InfoArrayUtil implements Serializable {
	private String accountStatus, ifCompleted, inStorageNumber, inStorageStatus, products,corporation, id, lister, pickWorker, project, truck, warehouse, workingProduct,createTime, inStorageTime, updateTime,ifDeleted;

	public String getInStorageStatus() {
		return inStorageStatus;
	}

	public void setInStorageStatus(String inStorageStatus) {
		this.inStorageStatus = inStorageStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getIfCompleted() {
		return ifCompleted;
	}

	public void setIfCompleted(String ifCompleted) {
		this.ifCompleted = ifCompleted;
	}

	public String getInStorageNumber() {
		return inStorageNumber;
	}

	public void setInStorageNumber(String inStorageNumber) {
		this.inStorageNumber = inStorageNumber;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLister() {
		return lister;
	}

	public void setLister(String lister) {
		this.lister = lister;
	}

	public String getPickWorker() {
		return pickWorker;
	}

	public void setPickWorker(String pickWorker) {
		this.pickWorker = pickWorker;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTruck() {
		return truck;
	}

	public void setTruck(String truck) {
		this.truck = truck;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWorkingProduct() {
		return workingProduct;
	}

	public void setWorkingProduct(String workingProduct) {
		this.workingProduct = workingProduct;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(String inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIfDeleted() {
		return ifDeleted;
	}

	public void setIfDeleted(String ifDeleted) {
		this.ifDeleted = ifDeleted;
	}

	@Override
	public String toString() {
		return "InfoArrayUtil [accountStatus=" + accountStatus + ", ifCompleted=" + ifCompleted + ", inStorageNumber="
				+ inStorageNumber + ", inStorageStatus=" + inStorageStatus + ", products=" + products + ", corporation="
				+ corporation + ", id=" + id + ", lister=" + lister + ", pickWorker=" + pickWorker + ", project="
				+ project + ", truck=" + truck + ", warehouse=" + warehouse + ", workingProduct=" + workingProduct
				+ ", createTime=" + createTime + ", inStorageTime=" + inStorageTime + ", updateTime=" + updateTime
				+ ", ifDeleted=" + ifDeleted + "]";
	}


}
