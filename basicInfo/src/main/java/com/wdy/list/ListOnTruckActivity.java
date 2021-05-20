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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wdy.basicinfo.BasicActivity;
import com.wdy.basicinfo.CarInfoActivity;
import com.wdy.basicinfo.FastJsonReturn;
import com.wdy.basicinfo.ListItemClick;
import com.wdy.basicinfo.ModifyCarActivity;
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

public class ListOnTruckActivity extends AppCompatActivity implements ListItemClick {
    public static final String TAG = ListOnTruckActivity.class.getSimpleName();

    Spinner mProject,mOutForm,mIfComplete,mQuality,mTally,mAccountStatus;
    CheckBox iProject,iOutForm,iIfComplete,iQuality,iTally,iAccountStatus,iCreateTime,iSignTime;
    EditText mCreateTime,mSignTime;
    Button mSearch;

    String url,urlQ;
    StringRequest requestProject;
    private int projectId,outFormId,ifCompleteId,qualityId,tallyId,accountStatusId;

    List<Project> listProject=new ArrayList<>();
    List<OutForm> listOutForm=new ArrayList<>();
    List<String> listIfComplete=new ArrayList<>(Arrays.asList("已完成","未完成"));
    List<Staff> listQuality=new ArrayList<>();
    List<Staff> listTally=new ArrayList<>();
    List<String> listAccountStatus =new ArrayList<>(Arrays.asList("未记账","已记账"));
    List<ListOnTruck> listListOnTruck=new ArrayList<>();

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_on_truck);

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
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2,ListOnTruckActivity.this);
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
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2,ListOnTruckActivity.this);
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
        SpinnerAdapter saProject =new SpinnerAdapter(listIfComplete,new ArrayList<String>(),ListOnTruckActivity.this);
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
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listQuality=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Staff>>() {});
                        List<String> list1=new ArrayList<>();
                        List<String> list2=new ArrayList<>();
                        for(Staff p:listQuality){
                            list1.add(p.getName());
                            list2.add(p.getUserName());
                        }
                        SpinnerAdapter saProject =new SpinnerAdapter(list1,list2,ListOnTruckActivity.this);
                        mQuality.setAdapter(saProject);
                        mQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                qualityId=i;
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {}
                        });

                        listTally=listQuality;
                        mTally.setAdapter(saProject);
                        mTally.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                tallyId=i;
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

        // accountStatus 列表
        saProject =new SpinnerAdapter(listAccountStatus,new ArrayList<String>(),ListOnTruckActivity.this);
        mAccountStatus.setAdapter(saProject);
        mAccountStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountStatusId=i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //获取 listOnTruck 列表
        urlQ = "http://47.111.122.217:8888/ScsyERP-web-Boss/OnTruckForm/query";
        requestProject = new StringRequest(Request.Method.GET, urlQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listListOnTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<ListOnTruck>>() {});
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListOnTruckActivity.this));
                        mRecyclerView.setAdapter(new ListOnTruckAdapter(listListOnTruck,ListOnTruckActivity.this));

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
        mProject=findViewById(R.id.sp_project);
        mOutForm=findViewById(R.id.sp_out_form);
        mIfComplete=findViewById(R.id.sp_if_complete);
        mQuality=findViewById(R.id.sp_quality);
        mTally=findViewById(R.id.sp_tally);
        mAccountStatus=findViewById(R.id.sp_account_status);
        iProject=findViewById(R.id.cb_project);
        iOutForm=findViewById(R.id.cb_out_form);
        iIfComplete=findViewById(R.id.cb_if_complete);
        iQuality=findViewById(R.id.cb_quality);
        iTally=findViewById(R.id.cb_tally);
        iAccountStatus=findViewById(R.id.cb_account_status);
        mSearch=findViewById(R.id.btn_query);
        mRecyclerView=findViewById(R.id.rv_list);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlQ="http://47.111.122.217:8888/ScsyERP-web-Boss/OnTruckForm/query?";
                urlQ+=iProject.isChecked()?"&project="+listProject.get(projectId).getId():"";
                urlQ+=iOutForm.isChecked()?"&outStorageForm="+listOutForm.get(outFormId).getId():"";
                urlQ+=iIfComplete.isChecked()?"&ifCompleted="+ifCompleteId:"";
                urlQ+=iAccountStatus.isChecked()?"&accountStatus="+accountStatusId:"";
                urlQ+=iQuality.isChecked()?"&qualityTestMan="+listQuality.get(qualityId).getId():"";
                urlQ+=iTally.isChecked()?"&tallyMan="+listTally.get(tallyId).getId():"";

                requestProject = new StringRequest(Request.Method.GET, urlQ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {//s为请求返回的字符串数据
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                listListOnTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<ListOnTruck>>() {});
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(ListOnTruckActivity.this));
                                mRecyclerView.setAdapter(new ListOnTruckAdapter(listListOnTruck,ListOnTruckActivity.this));

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(TAG,volleyError.toString());
                            }
                        }){};
                MySingleton.getInstance(ListOnTruckActivity.this).addToRequestQueue(requestProject);

            }
        });

    }

    @Override
    public void onListItemClick(int id) {
        Intent intent  = new Intent(ListOnTruckActivity.this, ModifyListOnTruckActivity.class);
        intent.putExtra("Id",id);
        startActivity(intent);
    }
}
