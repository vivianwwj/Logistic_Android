package com.wdy.basicinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModifyCarActivity extends AppCompatActivity {
    public static final String TAG = CarInfoActivity.class.getSimpleName();
    List<Truck> listTruck=new ArrayList<>();
    EditText mCarLicense,mCarId,mName,mAffiliation;
    Button mModify;
    Spinner mDriver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_modify);

        Intent intent=getIntent();
        final int id=intent.getIntExtra("Id",1);

        mName=findViewById(R.id.et_name);
        mCarLicense=findViewById(R.id.et_car_license);
        mCarId=findViewById(R.id.et_car_id);
        mAffiliation=findViewById(R.id.et_affiliation);
        mDriver=findViewById(R.id.sp_driver);
        mModify=findViewById(R.id.btn_modify);
        mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用post修改车辆信息
                String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/update";
                StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                int status=fastJsonReturn.getStatus();
                                if(status!=0){
                                    Toast.makeText(ModifyCarActivity.this,"\ncreate outForm failed!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ModifyCarActivity.this,"\nmodify success!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.toString());
                    }
                }) {
                    // 携带参数
                    @Override
                    protected HashMap<String, String> getParams()
                            throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("entityId",""+id);
                        if(!mName.getText().toString().isEmpty())
                            hashMap.put("name", mName.getText().toString());
                        if(!mCarLicense.getText().toString().isEmpty())
                            hashMap.put("carLicense",mCarLicense.getText().toString());
                        if(!mCarId.getText().toString().isEmpty())
                            hashMap.put("carId", mCarId.getText().toString());
                        if(!mAffiliation.getText().toString().isEmpty())
                            hashMap.put("affiliation", mAffiliation.getText().toString());
                        //todo 司机的特殊处理
//                        hashMap.put("driver", ""+staffId);
                        return hashMap;
                    }
                };
                MySingleton.getInstance(ModifyCarActivity.this).addToRequestQueue(requestProject);

            }
        });

        //使用get查询车辆，并显示在recyclerVIew里面
        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query?id="+id;
        StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                        Truck truck=listTruck.get(0);
                        mName.setText(truck.getName());
                        mCarLicense.setText(truck.getCarLicense());
                        mCarId.setText(truck.getCarId());
                        mAffiliation.setText(truck.getAffiliation());
                        //todo 添加司机spinner

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(ModifyCarActivity.this).addToRequestQueue(requestProject);

    }
}
