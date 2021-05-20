package com.wdy.login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.buildinstorageform.InStorage_Activity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kernal.plateid.CreateActivity;
import com.kernal.plateid.MemoryCameraActivity;
import com.kernal.plateid.adapter.TruckAdapter;
import com.kernal.plateid.javabean.FastJsonReturn;
import com.kernal.plateid.my.MySingleton;
import com.kernal.plateid.objects.Truck;
import com.kernal.plateid.utills.CheckPermission;
import com.kernal.plateid.utills.PermissionActivity;
import com.wdy.basicinfo.BasicActivity;
import com.wdy.list.Basic2Activity;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG= LoginActivity.class.getSimpleName();
    Button mOut,mIn,mFee,mInfo,mRoad,mData;

    static final String[] PERMISSION = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE, // 读取权限
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        String userName=sharedPreferences.getString("userName","");

        getAllView();


    }
    private void getAllView(){
        mInfo=findViewById(R.id.btn_basic_info);
        mIn=findViewById(R.id.btn_in);
        mOut=findViewById(R.id.btn_out);
        mFee=findViewById(R.id.btn_fee);

        mInfo.setOnClickListener(this);
        mOut.setOnClickListener(this);
        mFee.setOnClickListener(this);
        mIn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_out){
            Intent intent=new Intent(HomeActivity.this, CreateActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_fee){
            Intent intent=new Intent(HomeActivity.this, Basic2Activity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_basic_info){
            Intent intent=new Intent(HomeActivity.this, BasicActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_in){
            Intent intent=new Intent(HomeActivity.this, InStorage_Activity.class);
            startActivity(intent);
        }
    }


}

