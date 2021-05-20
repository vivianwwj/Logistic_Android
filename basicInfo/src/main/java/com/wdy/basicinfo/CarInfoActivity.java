package com.wdy.basicinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class CarInfoActivity extends AppCompatActivity implements ListItemClick {
    public static final String TAG = CarInfoActivity.class.getSimpleName();

    EditText mCarNumber,mCarLicense,mCarId;
    Button mQueryCar,mAddCar;
    List<Truck> listTruck=new ArrayList<>();
    TruckAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        mCarNumber=findViewById(R.id.et_car_number);
        mCarLicense=findViewById(R.id.et_car_license);
        mCarId=findViewById(R.id.et_car_id);
        mQueryCar=findViewById(R.id.btn_query_car);
        mAddCar=findViewById(R.id.btn_add_car);
        mRecyclerView=findViewById(R.id.rv_truck);


        mQueryCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query?";
                if(!mCarNumber.getText().toString().equals("")  ||
                        !mCarLicense.getText().toString().equals("") ||
                        !mCarId.getText().toString().equals("")){
                    url+=(mCarNumber.getText().toString().equals(""))?"":"&carNumber="+mCarNumber.getText();
                    url+=(mCarLicense.getText().toString().equals(""))?"":"&carLicense="+mCarLicense.getText();
                    url+=(mCarId.getText().toString().equals(""))?"":"&carId="+mCarId.getText();
                }
                Log.d(TAG,url);
                //使用get查询车辆，并显示在recyclerVIew里面
                StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {//s为请求返回的字符串数据
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                                mLayoutManager=new LinearLayoutManager(CarInfoActivity.this);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mAdapter=new TruckAdapter(listTruck,CarInfoActivity.this);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e(TAG,volleyError.toString());
                            }
                        }){};
                MySingleton.getInstance(CarInfoActivity.this).addToRequestQueue(requestProject);
            }
        });
        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(CarInfoActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });

        //使用get查询车辆，并显示在recyclerVIew里面
        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
        StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                        mLayoutManager=new LinearLayoutManager(CarInfoActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter=new TruckAdapter(listTruck,CarInfoActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(CarInfoActivity.this).addToRequestQueue(requestProject);

    }

    @Override
    public void onListItemClick(int id) {
        Intent intent  = new Intent(CarInfoActivity.this, ModifyCarActivity.class);
        intent.putExtra("Id",id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //使用get查询车辆，并显示在recyclerVIew里面
        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
        StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                        mLayoutManager=new LinearLayoutManager(CarInfoActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter=new TruckAdapter(listTruck,CarInfoActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(CarInfoActivity.this).addToRequestQueue(requestProject);
    }
}
