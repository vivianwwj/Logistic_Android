package com.wdy.contract;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wdy.basicinfo.FastJsonReturn;
import com.wdy.basicinfo.MySingleton;
import com.wdy.basicinfo.R;
import com.wdy.basicinfo.Truck;
import com.wdy.list.objects.Project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModifyContractActivity extends AppCompatActivity  {
    public static final String TAG = ModifyContractActivity.class.getSimpleName();

    TextView mContractNumber,mOutFormNumber,mProjectId,mProjectName,mTruckNumber,mCreateTime;

    String urlQ;
    StringRequest requestProject;
    Contract myContract;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_modify);
        Intent intent=getIntent();
        final int id=intent.getIntExtra("Id",1);
        getAllView();

        urlQ="http://47.111.122.217:8888/ScsyERP-web-Boss/TransportContract/query";
        requestProject = new StringRequest(Request.Method.GET, urlQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        List<Contract> listContract=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Contract>>() {});
                        myContract =listContract.get(id);
                        mContractNumber.setText("合同单号："+ myContract.getContractNumber());
                        mOutFormNumber.setText("出库单号："+ myContract.getOutStorageForm());
                        mProjectId.setText("项目工号："+ myContract.getProject());
                        Date date = new Date(myContract.getCreateTime());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        mCreateTime.setText("创建时间："+format.format(date));

                        urlQ="47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
                        requestProject = new StringRequest(Request.Method.GET, urlQ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {//s为请求返回的字符串数据
                                        Log.d(TAG,s);
                                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                        List<Project> listProject=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Project>>() {});
                                        for(Project p:listProject){
                                            if(p.getId()== myContract.getProject()){
                                                mProjectName.setText("项目名称："+p.getName());
                                                break;
                                            }
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.e(TAG,volleyError.toString());
                                    }
                                }){};
                        MySingleton.getInstance(ModifyContractActivity.this).addToRequestQueue(requestProject);

                        urlQ="47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
                        requestProject = new StringRequest(Request.Method.GET, urlQ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {//s为请求返回的字符串数据
                                        Log.d(TAG,s);
                                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                        List<Truck> listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                                        for(Truck p:listTruck){
                                            if(p.getId()== myContract.getTruck()){
                                                mTruckNumber.setText("车牌号："+p.getCarNumber());
                                                break;
                                            }
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.e(TAG,volleyError.toString());
                                    }
                                }){};
                        MySingleton.getInstance(ModifyContractActivity.this).addToRequestQueue(requestProject);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(ModifyContractActivity.this).addToRequestQueue(requestProject);





    }

    private  void getAllView(){
        mContractNumber =findViewById(R.id.textView48);
        mOutFormNumber=findViewById(R.id.textView55);
        mProjectId=findViewById(R.id.textView49);
        mProjectName=findViewById(R.id.textView54);
        mCreateTime=findViewById(R.id.textView53);
        mTruckNumber=findViewById(R.id.textView62);
    }

}
