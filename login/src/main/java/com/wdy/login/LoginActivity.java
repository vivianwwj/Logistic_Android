package com.wdy.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG= LoginActivity.class.getSimpleName();
    EditText mUser,mPwd;
    Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        String userName=sharedPreferences.getString("userName","");
        if(!userName.isEmpty()){
            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);

        mLogin=findViewById(R.id.btn_login);
        mUser=findViewById(R.id.et_username);
        mPwd=findViewById(R.id.et_pwd);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用post 完成出库单
                String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/user/dologin";
                StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.d(TAG,s);
                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                int status=fastJsonReturn.getStatus();
                                JSONObject content=fastJsonReturn.getContent();
                                if(status!=0){
                                    Toast.makeText(LoginActivity.this,"\nlogin failed!"+fastJsonReturn.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                                    SharedPreferences.Editor editor =sharedPreferences.edit();
                                    editor.putString("name",content.getString("name"));
                                    editor.putString("phone",content.getString("phone"));
                                    editor.putString("userName",content.getString("userName"));
                                    editor.putInt("corporation",content.getInteger("id"));
                                    editor.apply();
                                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
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
                        hashMap.put("userName", mUser.getText().toString());
                        hashMap.put("passWord", mPwd.getText().toString());
                        Log.d(TAG,hashMap.toString());
                        return hashMap;
                    }
                };
                MySingleton.getInstance(LoginActivity.this).addToRequestQueue(requestProject);

            }
        });
    }



}
