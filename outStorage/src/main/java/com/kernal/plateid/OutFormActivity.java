package com.kernal.plateid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.kernal.plateid.adapter.ProjectAdapter;
import com.kernal.plateid.adapter.StaffAdapter;
import com.kernal.plateid.adapter.TruckAdapter;
import com.kernal.plateid.adapter.WarehouseAdapter;
import com.kernal.plateid.javabean.FastJsonReturn;
import com.kernal.plateid.my.MyData;
import com.kernal.plateid.my.MyMethod;
import com.kernal.plateid.my.MySingleton;
import com.kernal.plateid.objects.Project;
import com.kernal.plateid.objects.Staff;
import com.kernal.plateid.objects.Truck;
import com.kernal.plateid.objects.Warehouse;
import com.kernal.plateid.utills.CheckPermission;
import com.kernal.plateid.utills.PermissionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kernal.plateid.CreateActivity.PERMISSION;

public class OutFormActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG=OutFormActivity.class.getSimpleName();
    private Spinner mProject,mWarehouse,mTruck,mStaff;
    private Button mScan,mSearchGoods,mShowList,mShowContract,mChange,mFinish,mDetail;
    List<Project> listProject;
    List<Warehouse> listWarehouse;
    List<Truck> listTruck;
    List<Staff> listStaff;
    ProjectAdapter projectAdapter;
    WarehouseAdapter warehouseAdapter;
    TruckAdapter truckAdapter;
    StaffAdapter staffAdapter;
    private int projectId,warehouseId,truckId,staffId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StringRequest requestProject;
        String url;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_form);
        getAllView();

        SharedPreferences sharedPreferences=getSharedPreferences("zju",MODE_PRIVATE);
        warehouseId=sharedPreferences.getInt("warehouse",0);
        projectId=sharedPreferences.getInt("project",0);
        truckId=sharedPreferences.getInt("truck",0);
        staffId=sharedPreferences.getInt("staff",0);

        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/OutStorageForm/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        int totalCount=fastJsonReturn.getContent().getInteger("TotalCount");
                        JSONArray jsonArray =fastJsonReturn.getContent().getJSONArray("data");
                        int outStorageId =jsonArray.getJSONObject(totalCount-1).getInteger("id");
                        SharedPreferences sharedPreferences=getSharedPreferences("zju",MODE_PRIVATE);
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putInt("outStorageId",outStorageId);
                        editor.apply();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);

        //获取warehouse列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Warehouse/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listWarehouse=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Warehouse>>() {});
                            warehouseAdapter=new WarehouseAdapter(listWarehouse,OutFormActivity.this);
                        mWarehouse.setAdapter(warehouseAdapter);
                        mWarehouse.setSelection((int)warehouseAdapter.getItemId(warehouseId));
                        mWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                warehouseId=warehouseAdapter.getObjectId(i);
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
                        projectAdapter=new ProjectAdapter(listProject,OutFormActivity.this);
                        mProject.setAdapter(projectAdapter);
                        mProject.setSelection((int)projectAdapter.getItemId(projectId));
                        mProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                projectId=projectAdapter.getObjectId(i);
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

        //获取truck列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listTruck=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Truck>>() {});
                        truckAdapter=new TruckAdapter(listTruck,OutFormActivity.this);
                        mTruck.setAdapter(truckAdapter);
                        mTruck.setSelection((int)truckAdapter.getItemId(truckId));
                        mTruck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                truckId=truckAdapter.getObjectId(i);
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

        //获取staff列表
        url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
        requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listStaff=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Staff>>() {});
                        staffAdapter=new StaffAdapter(listStaff,OutFormActivity.this);
                        mStaff.setAdapter(staffAdapter);
                        mStaff.setSelection((int)staffAdapter.getItemId(staffId));
                        mStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                staffId=staffAdapter.getObjectId(i);
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

    }

    private void getAllView(){
        mWarehouse=(Spinner)this.findViewById(R.id.sp_warehouse);
        mProject=(Spinner)findViewById(R.id.sp_project);
        mTruck=(Spinner)findViewById(R.id.sp_truck);
        mStaff =(Spinner)findViewById(R.id.sp_pick_worker);
        mScan=(Button)findViewById(R.id.btn_scan);
        mSearchGoods=(Button)findViewById(R.id.btn_search_goods);
        mShowList=(Button)findViewById(R.id.btn_show_list);
        mShowContract=(Button)findViewById(R.id.btn_show_contract);
        mChange=(Button)findViewById(R.id.btn_change);
        mFinish=(Button)findViewById(R.id.btn_finish);
        mDetail=(Button)findViewById(R.id.btn_detail);

        mScan.setOnClickListener(this);
        mSearchGoods.setOnClickListener(this);
        mShowList.setOnClickListener(this);
        mShowContract.setOnClickListener(this);
        mChange.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        mDetail.setOnClickListener(this);

        mShowContract.setEnabled(false);
        mShowList.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_scan) {
            //点击“扫一扫”，立即跳转到扫描界面
            Intent video_intent = new Intent(OutFormActivity.this, MemoryCameraActivity.class);
            if (Build.VERSION.SDK_INT >= 23) {
                CheckPermission checkPermission = new CheckPermission(OutFormActivity.this);
                if (checkPermission.permissionSet(PERMISSION)) {
                    PermissionActivity.startActivityForResult(OutFormActivity.this, 0, "true", PERMISSION);
                } else {
                    video_intent.setClass(getApplicationContext(), MemoryCameraActivity.class);
                    video_intent.putExtra("camera", true);
                    startActivityForResult(video_intent, 4);
                }
            } else {
                video_intent.setClass(getApplicationContext(), MemoryCameraActivity.class);
                video_intent.putExtra("camera", true);
                startActivityForResult(video_intent, 4);
            }
        }else if(v.getId()==R.id.btn_search_goods){
            Intent intent=new Intent(OutFormActivity.this,GoodsInWarehouseActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.btn_show_list){

        }else if(v.getId()==R.id.btn_show_contract){

        }else if(v.getId()==R.id.btn_change){

        }else if(v.getId()==R.id.btn_finish){
            //使用post 完成出库单
            String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/OutStorageForm/complete";
            StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG,s);
                            FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                            int status=fastJsonReturn.getStatus();
                            if(status!=0){
                                Toast.makeText(OutFormActivity.this,"\ncomplete outForm failed!",Toast.LENGTH_SHORT).show();
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
                    SharedPreferences sharedPreferences=getSharedPreferences("zju",MODE_PRIVATE);
                    int outStorageId=sharedPreferences.getInt("outStorageId",1);

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("outStorageForm", ""+outStorageId);
                    Log.d(TAG,hashMap.toString());
                    return hashMap;
                }
            };
            MySingleton.getInstance(OutFormActivity.this).addToRequestQueue(requestProject);
            //END：使用post 完成出库单

            mSearchGoods.setEnabled(false); //查询在库货物失效
            mChange.setEnabled(false);      //修改失效
            mScan.setEnabled(false);        //扫一扫失效
            mShowContract.setEnabled(true); //随车清单使能
            mShowList.setEnabled(true);     //运输合同使能
            mWarehouse.setEnabled(false);   //Spinner 全部失效
            mProject.setEnabled(false);
            mTruck.setEnabled(false);
            mStaff.setEnabled(false);

        }else if(v.getId()==R.id.btn_detail){
            Intent intent=new Intent(OutFormActivity.this,DetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && (resultCode >= 4 && resultCode <=6) ) {
            Log.d(TAG,"resultcode="+resultCode);
            final String carNumber = data.getStringExtra("number");
            boolean hasCar=false;
            int i;
            for(i=0;i<listTruck.size();i++){
                if(listTruck.get(i).getCarNumber().equals(carNumber)){
                    hasCar=true;
                    break;
                }
            }
            if(hasCar){
                mTruck.setSelection(i);
            }
            else{
                Intent intent=new Intent(OutFormActivity.this,AddTruckActivity.class);
                intent.putExtra("number",carNumber);
                startActivityForResult(intent,7);
            }
        }else if(requestCode == 7 && resultCode ==7){
            //使用get查询车辆，并显示在recyclerVIew里面
            String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
            StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {//s为请求返回的字符串数据
                            Log.d(TAG, s);
                            FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                            JSONArray jsonArray = fastJsonReturn.getContent().getJSONArray("data");
                            listTruck = JSON.parseObject(JSON.toJSONString(jsonArray), new TypeReference<ArrayList<Truck>>() {
                            });
                            truckAdapter = new TruckAdapter(listTruck, OutFormActivity.this);
                            mTruck.setAdapter(truckAdapter);
                            mTruck.setSelection(listTruck.size()-1);
                            mTruck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    truckId = truckAdapter.getObjectId(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e(TAG, volleyError.toString());
                        }
                    }) {
            };
            MySingleton.getInstance(this).addToRequestQueue(requestProject);
        }
    }



}


