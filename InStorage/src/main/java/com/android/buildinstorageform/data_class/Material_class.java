package com.android.buildinstorageform.data_class;

public class Material_class {
    private String figureNumber = "",ifDeleted,name = "";
    private String corporation = "";
    private Number createTime;
    private Number id;
    private Number updateTime;

    public String createMaterialPostBody(){
        return "name="+name+"&figureNumber="+figureNumber+"&corporation="+corporation;
    }
    public String updateMaterialPostBody(){
        return "entityId="+id+"&figureNumber="+figureNumber+"&name="+name;
    }

    public String deleteMaterialPostBody(){
        return "entityId="+id;
    }

    public void initalGetQueryURL(){
        name="";figureNumber="";corporation="";
    }
    public String getQueryURL() {
        String QueryURL_name,QueryURL_figureNumber,QueryURL_corporation;
        if(name.equals("")){
            QueryURL_name = "";
        }else {
            QueryURL_name = "Name=" + name;
        }
        if(figureNumber.equals("")){
            QueryURL_figureNumber = "";
        }else {
            QueryURL_figureNumber = "&FigureNumber=" + figureNumber;
        }
        if(corporation.equals("")){
            QueryURL_corporation = "";
        }else {
            QueryURL_corporation = "&corporation=" + corporation;
        }


        return "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/query?" +
                QueryURL_name+
                QueryURL_figureNumber +
                QueryURL_corporation ;
    }

    @Override
    public String toString() {
        return "Material_class{" +
                "figureNumber='" + figureNumber + '\'' +
                ", ifDeleted='" + ifDeleted + '\'' +
                ", name='" + name + '\'' +
                ", corporation='" + corporation + '\'' +
                ", createTime=" + createTime +
                ", id=" + id +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getFigureNumber() {
        return figureNumber;
    }

    public void setFigureNumber(String figureNumber) {
        this.figureNumber = figureNumber;
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

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public Number getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Number createTime) {
        this.createTime = createTime;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Number updateTime) {
        this.updateTime = updateTime;
    }
}
