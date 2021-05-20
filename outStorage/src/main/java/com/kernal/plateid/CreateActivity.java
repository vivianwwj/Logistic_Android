package com.kernal.plateid;

import android.Manifest.permission;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
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

public class CreateActivity extends AppCompatActivity implements OnClickListener {
    public static final String TAG = CreateActivity.class.getSimpleName();
    String sn;
    private int ReturnAuthority = -1;
    public AuthService.MyBinder authBinder;

    private Spinner mWarehouse,mProject,mTruck,mStaff;
    private Button mScan,mCreate;
    List<Project> listProject=new ArrayList<>();
    List<Warehouse> listWarehouse=new ArrayList<>();
    List<Truck> listTruck=new ArrayList<>();
    List<Staff> listStaff=new ArrayList<>();
    ProjectAdapter projectAdapter;
    WarehouseAdapter warehouseAdapter;
    TruckAdapter truckAdapter;
    StaffAdapter staffAdapter;
    private int projectId,warehouseId,truckId,staffId;

    public ServiceConnection authConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            authBinder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            authBinder = (AuthService.MyBinder) service;
            try {
                PlateAuthParameter pap = new PlateAuthParameter();
                pap.sn = sn;
                ReturnAuthority = authBinder.getAuth(pap);
                if (ReturnAuthority != 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.license_verification_failed) + ":" + ReturnAuthority, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.license_verification_success, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.failed_check_failure, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                if (authBinder != null) {
                    unbindService(authConn);
                }
            }
        }
    };

    static final String[] PERMISSION = new String[]{
            permission.CAMERA,
            permission.WRITE_EXTERNAL_STORAGE,// 写入权限
            permission.READ_EXTERNAL_STORAGE, // 读取权限
            permission.READ_PHONE_STATE,
            permission.VIBRATE, permission.INTERNET,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StringRequest requestProject;
        String url;

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.out_form_create);
        getAllView();    //找到指定的xml控件

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
                        warehouseAdapter=new WarehouseAdapter(listWarehouse,CreateActivity.this);
                        mWarehouse.setAdapter(warehouseAdapter);
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
                        projectAdapter=new ProjectAdapter(listProject,CreateActivity.this);
                        mProject.setAdapter(projectAdapter);
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
                        truckAdapter=new TruckAdapter(listTruck,CreateActivity.this);
                        mTruck.setAdapter(truckAdapter);
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
                        staffAdapter=new StaffAdapter(listStaff,CreateActivity.this);
                        mStaff.setAdapter(staffAdapter);
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

    private void getAllView() {
        mWarehouse=(Spinner)findViewById(R.id.sp_warehouse);
        mProject=(Spinner)findViewById(R.id.sp_project);
        mTruck=(Spinner)findViewById(R.id.sp_truck);
        mStaff=(Spinner)findViewById(R.id.sp_pick_worker);
        mScan=(Button)findViewById(R.id.btn_scan);
        mCreate=(Button)findViewById(R.id.btn_create);

        mScan.setOnClickListener(this);
        mCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_scan) {
            //点击“扫一扫”，立即跳转到扫描界面
            Intent video_intent = new Intent(CreateActivity.this, MemoryCameraActivity.class);
            if (Build.VERSION.SDK_INT >= 23) {
                CheckPermission checkPermission = new CheckPermission(CreateActivity.this);
                if (checkPermission.permissionSet(PERMISSION)) {
                    PermissionActivity.startActivityForResult(CreateActivity.this, 0, "true", PERMISSION);
                } else {
                    video_intent.putExtra("camera", true);
                    startActivityForResult(video_intent,4);
                }
            } else {
                video_intent.putExtra("camera", true);
                startActivityForResult(video_intent,4);
            }
        } else if (v.getId()==R.id.btn_create) {
            //TODO 创建出库单
            //使用post 创建出库单
            String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/OutStorageForm/create";
            StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG,s);
                            FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                            int status=fastJsonReturn.getStatus();
                            if(status!=0){
                                Toast.makeText(CreateActivity.this,"\ncreate outForm failed!",Toast.LENGTH_SHORT).show();
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
                    hashMap.put("corporation", ""+1);
                    hashMap.put("warehouse", ""+warehouseId);
                    hashMap.put("project", ""+projectId);
                    hashMap.put("truck", ""+truckId);
                    hashMap.put("lister", ""+staffId);
                    hashMap.put("pickWorker", ""+staffId);
                    return hashMap;
                }
            };
            MySingleton.getInstance(CreateActivity.this).addToRequestQueue(requestProject);

            SharedPreferences sharedPreferences=getSharedPreferences("zju",MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putInt("warehouse",warehouseId);
            editor.putInt("project",projectId);
            editor.putInt("truck",truckId);
            editor.putInt("staff",staffId);
            editor.apply();
            Toast.makeText(CreateActivity.this,"\n"+warehouseId+"\n"+projectId+"\n"+truckId+"\n"+staffId+"\n",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CreateActivity.this, OutFormActivity.class);
            startActivity(intent);
            finish();
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
                Intent intent=new Intent(CreateActivity.this,AddTruckActivity.class);
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
                            truckAdapter = new TruckAdapter(listTruck, CreateActivity.this);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}