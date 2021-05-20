package com.android.buildinstorageform.data_class;


import java.util.ArrayList;

public class Project_class {
    private String ifDeleted,name,projectNumber;
    private Number admin,corporation;
    private Number consignee;
    private Number customer;
    private Number id;
    private Number manufacturer;
    private Number createTime;
    private Number updateTime;
    private ArrayList materials;

    public String createProjectPostBody(){
        return "corporation="+corporation+"&projectName="+name+"&customer="+customer+"&manufacturer="+manufacturer+"&consignee="+consignee+"&admin="+admin;
    }
    public String updateProjectPostBody(){
        return "entityId="+id+"&name="+name;
    }

    public String deleteProjectPostBody(){
        return "entityId="+id;
    }


    public String getQueryURL() {
        String QueryURL_projectName,QueryURL_projectNumber,QueryURL_corporation,QueryURL_customer,QueryURL_manufacturer,QueryURL_consignee,QueryURL_admin;
        if(name.equals("null")){
            QueryURL_projectName = "";
        }else {
            QueryURL_projectName = "ProjectName=" + name;
        }
        if(projectNumber.equals("null")){
            QueryURL_projectNumber = "";
        }else {
            QueryURL_projectNumber = "&ProjectNumber=" + projectNumber;
        }
        if((""+corporation).equals("null")){
            QueryURL_corporation = "";
        }else {
            QueryURL_corporation = "&Corporation=" + corporation;
        }
        if((""+customer).equals("null")){
            QueryURL_customer = "";
        }else {
            QueryURL_customer = "&Customer=" + customer;
        }
        if((""+manufacturer).equals("null")){
            QueryURL_manufacturer = "";
        }else {
            QueryURL_manufacturer = "&Manufacturer=" + manufacturer;
        }
        if((""+consignee).equals("null")){
            QueryURL_consignee = "";
        }else {
            QueryURL_consignee = "&Consignee=" + consignee;
        }
        if((""+admin).equals("null")){
            QueryURL_admin = "";
        }else {
            QueryURL_admin = "&Admin=" + admin;
        }

        return "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query?" +
                QueryURL_projectName+
                QueryURL_projectNumber +
                QueryURL_corporation +
                QueryURL_customer +
                QueryURL_manufacturer +
                QueryURL_consignee +
                QueryURL_admin ;
    }
    @Override
    public String toString() {
        return "Project_class{" +
                "ifDeleted='" + ifDeleted + '\'' +
                ", name='" + name + '\'' +
                ", projectNumber='" + projectNumber + '\'' +
                ", admin=" + admin +
                ", consignee=" + consignee +
                ", corporation=" + corporation +
                ", customer=" + customer +
                ", id=" + id +
                ", manufacturer=" + manufacturer +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", materials=" + materials +
                '}';
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

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Number getAdmin() {
        return admin;
    }

    public void setAdmin(Number admin) {
        this.admin = admin;
    }

    public Number getConsignee() {
        return consignee;
    }

    public void setConsignee(Number consignee) {
        this.consignee = consignee;
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getCustomer() {
        return customer;
    }

    public void setCustomer(Number customer) {
        this.customer = customer;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Number manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Number getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Number createTime) {
        this.createTime = createTime;
    }

    public Number getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Number updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList materials) {
        this.materials = materials;
    }
}
