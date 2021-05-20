package com.android.buildinstorageform.data_class;


import java.io.Serializable;
import java.util.ArrayList;

public class InStorageFormForQuery_class implements Serializable {
	private Number warehouse,updateTime,id,createTime,inStorageTime,totalAmount,totalVolume,totalWeight;
	private String ifCompleted,ifDeleted,inStorageNumber,inStorageStatus;
	private ArrayList<Number> products;
	private String truck="",project="",corporation="",lister="",pickWorker="",accountStatus="";
	public String getQueryURL() {
		return "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/query?" +
				"truck=" + truck +
				"&project=" + project +
				"&corporation=" + corporation +
				"&lister=" + lister +
				"&pickWorker=" + pickWorker +
				"&accountStatus=" + accountStatus;
	}
	public void initalGetQueryURL(){
		truck="";project="";corporation="";lister="";pickWorker="";accountStatus="";
	}
	@Override
	public String toString() {
		return "InStorageFormForQuery_class{" +
				"warehouse=" + warehouse +
				", updateTime=" + updateTime +
				", id=" + id +
				", createTime=" + createTime +
				", inStorageTime=" + inStorageTime +
				", totalAmount=" + totalAmount +
				", totalVolume=" + totalVolume +
				", totalWeight=" + totalWeight +
				", ifCompleted='" + ifCompleted + '\'' +
				", ifDeleted='" + ifDeleted + '\'' +
				", inStorageNumber='" + inStorageNumber + '\'' +
				", inStorageStatus='" + inStorageStatus + '\'' +
				", products=" + products +
				", truck='" + truck + '\'' +
				", project='" + project + '\'' +
				", corporation='" + corporation + '\'' +
				", lister='" + lister + '\'' +
				", pickWorker='" + pickWorker + '\'' +
				", accountStatus='" + accountStatus + '\'' +
				'}';
	}

	public Number getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Number warehouse) {
		this.warehouse = warehouse;
	}

	public Number getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Number updateTime) {
		this.updateTime = updateTime;
	}

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public Number getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Number createTime) {
		this.createTime = createTime;
	}

	public Number getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Number inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	public Number getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Number totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Number getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Number totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Number getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Number totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getIfCompleted() {
		return ifCompleted;
	}

	public void setIfCompleted(String ifCompleted) {
		this.ifCompleted = ifCompleted;
	}

	public String getIfDeleted() {
		return ifDeleted;
	}

	public void setIfDeleted(String ifDeleted) {
		this.ifDeleted = ifDeleted;
	}

	public String getInStorageNumber() {
		return inStorageNumber;
	}

	public void setInStorageNumber(String inStorageNumber) {
		this.inStorageNumber = inStorageNumber;
	}

	public String getInStorageStatus() {
		return inStorageStatus;
	}

	public void setInStorageStatus(String inStorageStatus) {
		this.inStorageStatus = inStorageStatus;
	}

	public ArrayList<Number> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Number> products) {
		this.products = products;
	}

	public String getTruck() {
		return truck;
	}

	public void setTruck(String truck) {
		this.truck = truck;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
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

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
}
