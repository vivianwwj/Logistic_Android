package com.wdy.basicinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class AddCarActivity extends AppCompatActivity {
    public static final String TAG = AddCarActivity.class.getSimpleName();
    EditText mCarNumber,mCarLicense,mCarId,mAffiliation;
    Spinner mDriver;
    Button mAddCar,mScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        mCarNumber=findViewById(R.id.et_car_number);
        mCarLicense=findViewById(R.id.et_car_license);
        mCarId=findViewById(R.id.et_car_id);
        mAffiliation=findViewById(R.id.et_affiliation);
        mDriver=findViewById(R.id.sp_driver);
        mAddCar=findViewById(R.id.btn_add);
        mScan=findViewById(R.id.button2);

        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 使用post创建车辆
                String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/create";
                StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                int status=fastJsonReturn.getStatus();
                                if(status!=0){
                                    Toast.makeText(AddCarActivity.this,"\ncreate truck failed!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AddCarActivity.this,"\ncreate truck success!",Toast.LENGTH_SHORT).show();
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
                        if(!mCarNumber.getText().toString().isEmpty()){
                            hashMap.put("corporation",""+1);
                            hashMap.put("carNumber",mCarNumber.getText().toString());
                            if(!mCarLicense.getText().toString().isEmpty())
                                hashMap.put("carLicense",mCarLicense.getText().toString());
                            if(!mCarId.getText().toString().isEmpty())
                                hashMap.put("carId", mCarId.getText().toString());
                            if(!mAffiliation.getText().toString().isEmpty())
                                hashMap.put("affiliation", mAffiliation.getText().toString());
                            //todo 司机的特殊处理
//                            hashMap.put("driver", ""+staffId);
                        }
                        return hashMap;
                    }
                };
                MySingleton.getInstance(AddCarActivity.this).addToRequestQueue(requestProject);
            }
        });

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && (resultCode >= 4 && resultCode <=6) ) {
            Log.d(TAG,"resultcode="+resultCode);
            final String carNumber = data.getStringExtra("number");
            //TODO 使用post创建车辆
            String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/create";
            StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG,s);
                            FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                            int status=fastJsonReturn.getStatus();
                            if(status!=0){
                                Toast.makeText(AddCarActivity.this,"\ncreate truck failed, or car number has existed!",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddCarActivity.this,"\ncreate truck success!",Toast.LENGTH_SHORT).show();
                                finish();
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
                    if(!mCarNumber.getText().toString().isEmpty()){
                        hashMap.put("corporation",""+1);
                        hashMap.put("carNumber",mCarNumber.getText().toString());
                        if(!mCarLicense.getText().toString().isEmpty())
                            hashMap.put("carLicense",mCarLicense.getText().toString());
                        if(!mCarId.getText().toString().isEmpty())
                            hashMap.put("carId", mCarId.getText().toString());
                        if(!mAffiliation.getText().toString().isEmpty())
                            hashMap.put("affiliation", mAffiliation.getText().toString());
                        //todo 司机的特殊处理
//                            hashMap.put("driver", ""+staffId);
                    }
                    return hashMap;
                }
            };
            MySingleton.getInstance(AddCarActivity.this).addToRequestQueue(requestProject);
        }
    }
}
