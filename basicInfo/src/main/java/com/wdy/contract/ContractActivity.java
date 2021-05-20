package com.wdy.contract;

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
import com.wdy.basicinfo.Truck;
import com.wdy.list.objects.OutForm;
import com.wdy.list.objects.Project;
import com.wdy.list.objects.SpinnerAdapter;
import com.wdy.list.objects.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContractActivity extends AppCompatActivity implements ListItemClick {
    public static final String TAG = ContractActivity.class.getSimpleName();

    Spinner mProject,mOutForm,mIfComplete, mTruck;
    CheckBox iProject,iOutForm,iIfComplete, iTruck,iCreateTime;
    EditText mCreateTime;
    Button mSearch;

    String url,urlQ;
    StringRequest requestProject;
    private int projectId,outFormId,ifCompleteId, truckId;

    List<Project> listProject=new ArrayList<>();
    List<OutForm> listOutForm=new ArrayList<>();
    List<String> listIfComplete=new ArrayList<>(Arrays.asList("已完成","未完成"));
    List<Truck> listTruck =new ArrayList<>();
    List<Contract> listContract =new ArrayList<>();

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        getAllView();

        //获取project列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listProject=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Project>>() {});
                        List<String> list1=new ArrayList<>();
                        List<String> list2=new ArrayList<>();
                        for(Project p:listProject){
                            list1.add(p.getId()+"");
                            list2.add(p.getName());
                        }
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2, ContractActivity.this);
                        mProject.setAdapter(saProject);
                        mProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                projectId=i;
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {}
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);

        //获取 outForm 列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/OutStorageForm/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listOutForm=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<OutForm>>() {});
                        List<String> list1=new ArrayList<>();
                        List<String> list2=new ArrayList<>();
                        for(OutForm p:listOutForm){
                            list1.add(p.getId()+"");
                            list2.add(p.getOutStorageNumber());
                        }
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2, ContractActivity.this);
                        mOutForm.setAdapter(saProject);
                        mOutForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                outFormId=i;
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {}
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);
        // ifcomplete 列表
        SpinnerAdapter saProject =new SpinnerAdapter(listIfComplete,new ArrayList<String>(), ContractActivity.this);
        mIfComplete.setAdapter(saProject);
        mIfComplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ifCompleteId=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //获取 quality tally 列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listTruck =JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                        List<String> list1=new ArrayList<>();
                        List<String> list2=new ArrayList<>();
                        for(Truck p: listTruck){
                            list1.add(p.getName());
                            list2.add(p.getCarNumber());
                        }
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2, ContractActivity.this);
                        mTruck.setAdapter(saProject);
                        mTruck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                truckId =i;
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {}
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);

        //获取 listOnTruck 列表
        urlQ = "http://47.111.122.217:8888/ScsyERP-web-Boss/TransportContract/query";
        requestProject = new StringRequest(Request.Method.GET, urlQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listContract =JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Contract>>() {});
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ContractActivity.this));
                        mRecyclerView.setAdapter(new ContractAdapter(listContract, ContractActivity.this));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);
    }

    private  void getAllView(){
        mProject=findViewById(R.id.spinner1);
        mOutForm=findViewById(R.id.spinner2);
        mIfComplete=findViewById(R.id.spinner3);
        mTruck =findViewById(R.id.spinner4);
        iProject=findViewById(R.id.checkBox1);
        iOutForm=findViewById(R.id.checkBox2);
        iIfComplete=findViewById(R.id.checkBox3);
        iTruck =findViewById(R.id.checkBox4);
        mSearch=findViewById(R.id.button);
        mRecyclerView=findViewById(R.id.recyclerView);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlQ="http://47.111.122.217:8888/ScsyERP-web-Boss/TransportContract/query?";
                urlQ+=iProject.isChecked()?"&project="+listProject.get(projectId).getId():"";
                urlQ+=iOutForm.isChecked()?"&outStorageForm="+listOutForm.get(outFormId).getId():"";
                urlQ+=iIfComplete.isChecked()?"&ifCompleted="+ifCompleteId:"";
                urlQ+= iTruck.isChecked()?"&truck="+ listTruck.get(truckId).getId():"";

                requestProject = new StringRequest(Request.Method.GET, urlQ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {//s为请求返回的字符串数据
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                listContract =JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Contract>>() {});
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(ContractActivity.this));
                                mRecyclerView.setAdapter(new ContractAdapter(listContract, ContractActivity.this));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(TAG,volleyError.toString());
                            }
                        }){};
                MySingleton.getInstance(ContractActivity.this).addToRequestQueue(requestProject);

            }
        });

    }

    @Override
    public void onListItemClick(int id) {
        Intent intent  = new Intent(ContractActivity.this, ModifyContractActivity.class);
        intent.putExtra("Id",id);
        startActivity(intent);
    }
}
