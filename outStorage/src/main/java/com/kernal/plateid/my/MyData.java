package com.kernal.plateid.my;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kernal.plateid.javabean.FastJsonReturn;
import com.kernal.plateid.objects.Good;
import com.kernal.plateid.objects.Project;
import com.kernal.plateid.objects.Staff;
import com.kernal.plateid.objects.Truck;
import com.kernal.plateid.objects.Warehouse;

import java.util.ArrayList;
import java.util.List;

public final class MyData {
    private final static String TAG=MyData.class.getSimpleName();

    private static ArrayList<Good> goodsOut;
    private static ArrayList<Good> goodsDetail=new ArrayList<>();

    private static ArrayList<Good> goodsInstore;

    private static ArrayList<Project> projects;
    private static ArrayList<Warehouse> warehouses;
    private static ArrayList<Truck> trucks;
    private static ArrayList<Staff> staff;
    public static boolean hasTruck(String s){
        for(Truck truck:trucks){
            if(truck.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Good> getGoodsDetail() {
//        if(goodsDetail.isEmpty() )
//            return null;
        return goodsDetail;
    }

    public static void addGoodsDetail(Good good){
        goodsDetail.add(good);
    }

    public static ArrayList<Good> getGoodsOut() {
        return goodsOut;
    }

    public static void setGoodsOut(ArrayList<Good> goodsOut) {
        MyData.goodsOut = goodsOut;
    }

    public static ArrayList<Project> getProjects() {
        projects=new ArrayList<>();
//        projects.add(new Project("SC1","project 1",1));
//        projects.add(new Project("QY6","project 2",2));
//        projects.add(new Project("DJ4","project 3",3));
        return projects;
    }

    public static ArrayList<Warehouse> getWarehouses() {
        warehouses=new ArrayList<>();
//        warehouses.add(new Warehouse("浙江杭州","Ware 1",11));
//        warehouses.add(new Warehouse("上海","Ware 2",22));
//        warehouses.add(new Warehouse("江苏南京","Ware 3",333));
        return warehouses;
    }

    public static ArrayList<Truck> getTrucks() {
        trucks=new ArrayList<>();
//        trucks.add(new Truck("浙A12345","truck 1",111));
//        trucks.add(new Truck("川A43245","truck 2",222));
//        trucks.add(new Truck("琼B12345","truck 3",333));
        return trucks;
    }

    public static ArrayList<Staff> getStaff(final Context context) {
        staff=new ArrayList<>();
//        staff.add(new Staff("admin1","John",1111));
//        staff.add(new Staff("admin2","Tom",2222));
//        staff.add(new Staff("superadmin","Mark",3333));
        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
        StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        staff=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Staff>>() {});
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(context).addToRequestQueue(requestProject);
        return staff;
    }
}
