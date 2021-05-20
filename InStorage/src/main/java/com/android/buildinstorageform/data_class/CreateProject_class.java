package com.android.buildinstorageform.data_class;

public class CreateProject_class {
    private Number corporation,customer,manufacturer,consignee,admin;
    private String name;

    @Override
    public String toString() {
        return "CreateProject_class{" +
                "corporation=" + corporation +
                ", customer=" + customer +
                ", manufacturer=" + manufacturer +
                ", consignee=" + consignee +
                ", admin=" + admin +
                ", name='" + name + '\'' +
                '}';
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

    public Number getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Number manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Number getConsignee() {
        return consignee;
    }

    public void setConsignee(Number consignee) {
        this.consignee = consignee;
    }

    public Number getAdmin() {
        return admin;
    }

    public void setAdmin(Number admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String createProjectPostRequestBody(){
        return "corporation=" + corporation + "&name=" + name+ "&customer=" + customer+ "&manufacturer=" + manufacturer+ "&consignee=" + consignee+ "&admin=" + admin;
    }
}
