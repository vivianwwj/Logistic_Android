package com.android.buildinstorageform.data_class;

public class ProductForQuery_class {
    private Number corporation,id,material,project,warehouse,inStorageForm,createTime,updateTime;
    private String name = "",ifDeleted,packetNumber = "",packetType,status = "",wareHouseLocation;
    private String length="",width="",height="",weight="",volume="";

    public String createProductPostBody(){
        return "name="+name+"&packetNumber="+packetNumber+"&packetType="+packetType+"&project="+project+"&material="+material+"&corporation="+corporation+"&length="+length+"&width="+width+"&height="+height+"&weight="+weight+"&volume="+volume;
    }
    public String updateProductPostBody(){
        return "entityId="+id+"&name="+name+"&packetNumber="+packetNumber+"&packetType="+packetType+"&material="+material+"&wareHouse="+warehouse+"&warehouseLocation="+wareHouseLocation+"&length="+length+"&width="+width+"&height="+height+"&volume="+volume+"&weight="+weight;
    }

    public String deleteProductPostBody(){
        return "entityId="+id;
    }


    public String getQueryURL() {
        String QueryURL_Name,QueryURL_packetNumber,QueryURL_corporation,QueryURL_status;
        if(name.equals("")){
            QueryURL_Name = "";
        }else {
            QueryURL_Name = "Name=" + name;
        }
        if(packetNumber.equals("")){
            QueryURL_packetNumber = "";
        }else {
            QueryURL_packetNumber = "&PacketNumber=" + packetNumber;
        }
        if((""+corporation).equals("null")){
            QueryURL_corporation = "";
        }else {
            QueryURL_corporation = "&Corporation=" + corporation;
        }

        if(status.equals("")){
            QueryURL_status = "";
        }else {
            QueryURL_status = "&Status=" + status;
        }

        return "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/query?" +
                QueryURL_Name+
                QueryURL_packetNumber +
                QueryURL_corporation +
                QueryURL_status ;
    }

    @Override
    public String toString() {
        return "ProductForQuery_class{" +
                "corporation=" + corporation +
                ", id=" + id +
                ", material=" + material +
                ", project=" + project +
                ", warehouse=" + warehouse +
                ", inStorageForm=" + inStorageForm +
                ", createTime='" + createTime + '\'' +
                ", name='" + name + '\'' +
                ", ifDeleted='" + ifDeleted + '\'' +
                ", packetNumber='" + packetNumber + '\'' +
                ", packetType='" + packetType + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", wareHouseLocation='" + wareHouseLocation + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

    public String getWareHouseLocation() {
        return wareHouseLocation;
    }

    public void setWareHouseLocation(String wareHouseLocation) {
        this.wareHouseLocation = wareHouseLocation;
    }

    public Number getInStorageForm() {
        return inStorageForm;
    }

    public void setInStorageForm(Number inStorageForm) {
        this.inStorageForm = inStorageForm;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Number getCorporation() {
        return corporation;
    }

    public void setCorporation(Number corporation) {
        this.corporation = corporation;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getMaterial() {
        return material;
    }

    public void setMaterial(Number material) {
        this.material = material;
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

    public Number getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Number createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfDeleted() {
        return ifDeleted;
    }

    public void setIfDeleted(String ifDeleted) {
        this.ifDeleted = ifDeleted;
    }

    public String getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(String packetNumber) {
        this.packetNumber = packetNumber;
    }

    public String getPacketType() {
        return packetType;
    }

    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Number getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Number updateTime) {
        this.updateTime = updateTime;
    }
}
