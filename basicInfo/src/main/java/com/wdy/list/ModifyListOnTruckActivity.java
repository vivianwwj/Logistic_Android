package com.wdy.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wdy.basicinfo.FastJsonReturn;
import com.wdy.basicinfo.ListItemClick;
import com.wdy.basicinfo.MySingleton;
import com.wdy.basicinfo.R;
import com.wdy.list.objects.ListOnTruck;
import com.wdy.list.objects.OutForm;
import com.wdy.list.objects.Project;
import com.wdy.list.objects.SpinnerAdapter;
import com.wdy.list.objects.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyListOnTruckActivity extends AppCompatActivity  {
    public static final String TAG = ModifyListOnTruckActivity.class.getSimpleName();

    TextView mListOnTruckNumber,mOutFormNumber,mProjectId,mProjectName;//mIfComplete,mQuality,mTally,mAccountStatus;

    String urlQ;
    StringRequest requestProject;
    ListOnTruck myListOnTruck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_on_truck_modify);
        Intent intent=getIntent();
        final int id=intent.getIntExtra("Id",1);
        getAllView();

        urlQ="http://47.111.122.217:8888/ScsyERP-web-Boss/OnTruckForm/query";
        requestProject = new StringRequest(Request.Method.GET, urlQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        List<ListOnTruck> listListOnTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<ListOnTruck>>() {});
                        myListOnTruck =listListOnTruck.get(id);
                        mListOnTruckNumber.setText("随车清单号："+myListOnTruck.getFormNumber());
                        mOutFormNumber.setText("出库单号："+myListOnTruck.getOutStorageForm());
                        mProjectId.setText("项目工号："+myListOnTruck.getProject());

                        urlQ="http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
                        requestProject = new StringRequest(Request.Method.GET, urlQ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {//s为请求返回的字符串数据
                                        Log.d(TAG,s);
                                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                        List<Project> listProject=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Project>>() {});
                                        for(Project p:listProject){
                                            if(p.getId()==myListOnTruck.getProject()){
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
                        MySingleton.getInstance(ModifyListOnTruckActivity.this).addToRequestQueue(requestProject);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(ModifyListOnTruckActivity.this).addToRequestQueue(requestProject);





    }

    private  void getAllView(){
        mListOnTruckNumber=findViewById(R.id.tv_list_on_truck_number);
        mOutFormNumber=findViewById(R.id.tv_out_form_number);
        mProjectId=findViewById(R.id.tv_project_id);
        mProjectName=findViewById(R.id.tv_project_name);

    }

}
