package com.android.buildinstorageform;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Admin_class;
import com.android.buildinstorageform.data_class.Consignee_class;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.CreateProject_class;
import com.android.buildinstorageform.data_class.Customer_class;
import com.android.buildinstorageform.data_class.Manufacturer_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreateProject_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText name_createProject;
    private Button button_create_createProject,button_complete;
    private Spinner spinner_corporation_createProject,spinner_admin_createProject, spinner_customer_createProject, spinner_manufacturer_createProject, spinner_consignee_createProject;
    private String createProjectPost_requestResult = "";
    private String getCorporation_requestResult,getAdmin_requestResult,getCustomer_requestResult,getManufacturer_requestResult,getConsignee_requestResult;

    private final String GETCORPORATION_GET_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String GETADMIN_GET_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private final String GETCUSTOMER_GET_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Customer/query";
    private final String GETMANUFACTURER_GET_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Manufacturer/query";
    private final String GETCONSIGNEE_GET_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Consignee/query";
    private final String CREATEPROJECT_POST_URL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/create";

    private String jsonString_getCorporation,jsonString_getAdmin,jsonString_getCustomer,jsonString_getManufacturer,jsonString_getConsignee;;
    private ArrayList<Corporation_class> arrayList_corporation = new ArrayList<Corporation_class>();
    private ArrayList<Admin_class> arrayList_admin = new ArrayList<Admin_class>();
    private ArrayList<Customer_class> arrayList_customer = new ArrayList<Customer_class>();
    private ArrayList<Manufacturer_class> arrayList_manufacture = new ArrayList<Manufacturer_class>();
    private ArrayList<Consignee_class> arrayList_consignee = new ArrayList<Consignee_class>();
    private CreateProject_class createProject_class = new CreateProject_class();
    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener createProject_loadListener;
    // 给接口赋值，得到接口对象
    public void createProject_setLoadDataComplete(IsLoadDataListener dataComplete) {
        this.createProject_loadListener = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project);

        button_create_createProject = (Button) findViewById(R.id.button_create_createProject);
        button_complete = (Button) findViewById(R.id.button_complete_createProject);
        button_create_createProject.setOnClickListener(this);
        button_complete.setOnClickListener(this);

        spinner_corporation_createProject = (Spinner) findViewById(R.id.spinner_corporation_createProject);
        spinner_admin_createProject = (Spinner) findViewById(R.id.spinner_admin_createProject);
        spinner_customer_createProject = (Spinner) findViewById(R.id.spinner_customer_createProject);
        spinner_manufacturer_createProject = (Spinner) findViewById(R.id.spinner_manufacturer_createProject);
        spinner_consignee_createProject = (Spinner) findViewById(R.id.spinner_consignee_createProject);
        spinner_corporation_createProject.setOnItemSelectedListener(this);
        spinner_admin_createProject.setOnItemSelectedListener(this);
        spinner_customer_createProject.setOnItemSelectedListener(this);
        spinner_manufacturer_createProject.setOnItemSelectedListener(this);
        spinner_consignee_createProject.setOnItemSelectedListener(this);

        name_createProject = (EditText) findViewById(R.id.name_createProject);

        new HTTP_GET().execute();


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_complete_createProject) {
            finish();

        } else if (i == R.id.button_create_createProject) {
            Log.i("CreateProject", "button_create_createProject选中！");
            createProject_class.setName("" + name_createProject.getText());

            String createProject_postBody = createProject_class.createProjectPostRequestBody();
            Log.i("CreateProject", "POST_请求体:" + createProject_postBody);
            new CreateProjectPost().execute(createProject_postBody);

            createProject_setLoadDataComplete(new IsLoadDataListener() {

                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(createProjectPost_requestResult, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_corporation_createProject) {
            createProject_class.setCorporation(arrayList_corporation.get(position).getId());

        } else if (i == R.id.spinner_admin_createProject) {
            createProject_class.setAdmin(arrayList_admin.get(position).getId());

        } else if (i == R.id.spinner_customer_createProject) {
            createProject_class.setCustomer(arrayList_customer.get(position).getId());

        } else if (i == R.id.spinner_manufacturer_createProject) {
            createProject_class.setManufacturer(arrayList_manufacture.get(position).getId());

        } else if (i == R.id.spinner_consignee_createProject) {
            createProject_class.setConsignee(arrayList_consignee.get(position).getId());

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask异步任务类:HTTP_GET
     */
    private class HTTP_GET extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getAdmin = NetworkUtils.getResponseFromHttpUrl_GET(new URL(GETADMIN_GET_URL));
                jsonString_getConsignee = NetworkUtils.getResponseFromHttpUrl_GET(new URL(GETCONSIGNEE_GET_URL));
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(GETCORPORATION_GET_URL));
                jsonString_getCustomer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(GETCUSTOMER_GET_URL));
                jsonString_getManufacturer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(GETMANUFACTURER_GET_URL));
                Log.i("CreateProject_Activity","jsonString_getAdmin:"+jsonString_getAdmin);
            } catch (IOException e) {
                Log.e("CreateProject_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_admin = FastjsonTools.jsonStringParseToArrayList(jsonString_getAdmin,"$.content.data", Admin_class.class);
            Log.i("CreateProject_Activity","arrayList_admin:"+arrayList_admin.toString());
            arrayList_corporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);
            arrayList_customer = FastjsonTools.jsonStringParseToArrayList(jsonString_getCustomer,"$.content.data", Customer_class.class);
            arrayList_manufacture = FastjsonTools.jsonStringParseToArrayList(jsonString_getManufacturer,"$.content.data", Manufacturer_class.class);
            arrayList_consignee = FastjsonTools.jsonStringParseToArrayList(jsonString_getConsignee,"$.content.data", Consignee_class.class);

            SpinnerAdapter_twoColumns corporationAdapter = new SpinnerAdapter_twoColumns(CreateProject_Activity.this,jsonString_getCorporation,arrayList_corporation,"id","name",false);
            spinner_corporation_createProject.setAdapter(corporationAdapter);

            SpinnerAdapter_twoColumns adminAdapter = new SpinnerAdapter_twoColumns(CreateProject_Activity.this,jsonString_getAdmin,arrayList_admin,"id","name",false);
            spinner_admin_createProject.setAdapter(adminAdapter);

            SpinnerAdapter_twoColumns customerAdapter = new SpinnerAdapter_twoColumns(CreateProject_Activity.this,jsonString_getCustomer,arrayList_customer,"id","name",false);
            spinner_customer_createProject.setAdapter(customerAdapter);

            SpinnerAdapter_twoColumns manufacturerAdapter = new SpinnerAdapter_twoColumns(CreateProject_Activity.this,jsonString_getManufacturer,arrayList_manufacture,"id","name",false);
            spinner_manufacturer_createProject.setAdapter(manufacturerAdapter);

            SpinnerAdapter_twoColumns consigneeAdapter = new SpinnerAdapter_twoColumns(CreateProject_Activity.this,jsonString_getConsignee,arrayList_consignee,"id","name",false);
            spinner_consignee_createProject.setAdapter(consigneeAdapter);

            //实例化SharedPreferences对象（第一步）
            SharedPreferences sharedPreferences_setCorporation = getSharedPreferences("corporation", Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = sharedPreferences_setCorporation.edit();
            //用putString的方法保存数据
            editor.putString("jsonString_getCorporation", jsonString_getCorporation);
            //提交当前数据
            editor.apply();
        }
    }
    /**
     * AsyncTask异步任务类:CreateProjectPost
     */
    public class CreateProjectPost extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                createProjectPost_requestResult = NetworkUtils.getResponseFromHttpUrl_POST(CREATEPROJECT_POST_URL, params[0]);
            } catch (IOException e) {
                Log.e("CreateProject", Log.getStackTraceString(e));
            }
            Log.i("CreateProject", "createProjectPost_requestResult:" + createProjectPost_requestResult);


            return createProjectPost_requestResult;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (createProject_loadListener != null) {
                createProject_loadListener.loadComplete();
            }
        }
    }
}
