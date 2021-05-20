package com.android.buildinstorageform.data_class;

import android.widget.EditText;

public class GetAccount_class {

    private String token,userName,passWord,phone,name,dept;
    private Number type;

    public GetAccount_class(String token, String userName, String passWord, String phone, String name, String dept, Number type) {
        this.token = token;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.name = name;
        this.dept = dept;
        this.type = type;
    }

    public GetAccount_class() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Number getType() {
        return type;
    }

    public void setType(Number type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GetAccount{" +
                "token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", type=" + type +
                '}';
    }

    public String getAccountPostRequestBody(){
        return "token=" + token + "&UserName=" + userName+ "&PassWord=" + passWord+ "&Phone=" + phone+ "&name=" + name+ "&type=" + type+ "&dept=" + dept;
    }
}

