package com.android.buildinstorageform;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Admin_class;
import com.android.buildinstorageform.data_class.Consignee_class;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.Customer_class;
import com.android.buildinstorageform.data_class.Manufacturer_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProjectDetail_Activity extends AppCompatActivity implements View.OnClickListener{

    private final String URL_POST_UPDATE_PROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/update";
    private String url_get_customer = "";
    private String url_get_manufacturer = "";
    private String url_get_consignee = "";
    private String url_get_corporation = "";
    private String url_get_admin = "";


    private EditText editText_projectName;
    private TextView textView_projectNumber,textView_customer,textView_manufacturer,textView_consignee,textView_admin,
            textView_id,textView_corporation,textView_material,textView_ifDeleted,textView_createTime,
            textView_updateTime;
    private Button button_return,button_update;

    private String jsonString_getProject,jsonString_updateProject,jsonString_getCustomer,jsonString_getManufacturer,
            jsonString_getConsignee,jsonString_getCorporation,jsonString_getAdmin;
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<>();
    private ArrayList<Customer_class> arrayList_getCustomer = new ArrayList<>();
    private ArrayList<Manufacturer_class> arrayList_getManufacturer = new ArrayList<>();
    private ArrayList<Consignee_class> arrayList_getConsignee = new ArrayList<>();
    private ArrayList<Admin_class> arrayList_getAdmin = new ArrayList<>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private Project_class project_class = new Project_class();
    private int clickedPosition_getProject;

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_updateProject;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_updateProject (IsLoadDataListener dataComplete) {
        this.loadListener_updateProject = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_detail);

        editText_projectName = (EditText)findViewById(R.id.editText_projectName_projectDetail);

        textView_admin = (TextView)findViewById(R.id.textView_admin_projectDetail);
        textView_consignee = (TextView)findViewById(R.id.textView_consignee_projectDetail);
        textView_corporation = (TextView)findViewById(R.id.textView_corporation_projectDetail);
        textView_createTime = (TextView)findViewById(R.id.textView_createTime_projectDetail);
        textView_customer = (TextView)findViewById(R.id.textView_customer_projectDetail);
        textView_id = (TextView)findViewById(R.id.textView_id_projectDetail);
        textView_ifDeleted = (TextView)findViewById(R.id.textView_ifDeleted_projectDetail);
        textView_manufacturer = (TextView)findViewById(R.id.textView_manufacturer_projectDetail);
        textView_material = (TextView)findViewById(R.id.textView_material_projectDetail);
        textView_projectNumber = (TextView)findViewById(R.id.textView_projectNumber_projectDetail);
        textView_updateTime = (TextView)findViewById(R.id.textView_updateTime_projectDetail);

        button_return = (Button)findViewById(R.id.button_return_projectDetail);
        button_update = (Button)findViewById(R.id.button_update_projectDetail);
        button_return.setOnClickListener(this);
        button_update.setOnClickListener(this);

        jsonString_getProject = getIntent().getStringExtra("jsonString_getProject");
        clickedPosition_getProject =  getIntent().getIntExtra("clickedPosition_getProject",0);
        arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data",Project_class.class);
        project_class = arrayList_getProject.get(clickedPosition_getProject);

        url_get_customer = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Customer/query?id="+project_class.getCustomer();
        url_get_manufacturer = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Manufacturer/query?id="+project_class.getManufacturer();
        url_get_consignee = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Consignee/query?id="+project_class.getConsignee();
        url_get_corporation = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query?id="+project_class.getCorporation();
        url_get_admin = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query?id="+project_class.getAdmin();

        new HTTP_GET_info().execute();


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_projectDetail) {
            finish();

        } else if (i == R.id.button_update_projectDetail) {
            project_class.setName(editText_projectName.getText() + "");
            Log.i("ProjectDetail_Activity", "project_class.updateProjectPostBody():" + project_class.updateProjectPostBody());

            new HTTP_POST_updateProject().execute(project_class.updateProjectPostBody());

            setLoadDataComplete_updateProject(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_updateProject, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /**
     * AsyncTask异步任务类:HTTP_GET_info
     */
    private class HTTP_GET_info extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_corporation));
                jsonString_getCustomer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_customer));
                jsonString_getManufacturer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_manufacturer));
                jsonString_getConsignee = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_consignee));
                jsonString_getAdmin = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_admin));
                Log.i("MaterialDetail_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("MaterialDetail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);
            arrayList_getCustomer = FastjsonTools.jsonStringParseToArrayList(jsonString_getCustomer,"$.content.data", Customer_class.class);
            arrayList_getManufacturer = FastjsonTools.jsonStringParseToArrayList(jsonString_getManufacturer,"$.content.data", Manufacturer_class.class);
            arrayList_getConsignee = FastjsonTools.jsonStringParseToArrayList(jsonString_getConsignee,"$.content.data", Consignee_class.class);
            arrayList_getAdmin = FastjsonTools.jsonStringParseToArrayList(jsonString_getAdmin,"$.content.data", Admin_class.class);

            editText_projectName.setText(project_class.getName());
            textView_projectNumber.setText(project_class.getProjectNumber());
            textView_material.setText(project_class.getMaterials()+"");
            textView_manufacturer.setText(arrayList_getManufacturer.get(0).getName());
            textView_ifDeleted.setText(project_class.getIfDeleted()+"");
            textView_id.setText(project_class.getId()+"");
            textView_customer.setText(arrayList_getCustomer.get(0).getName());
            textView_corporation.setText(arrayList_getCorporation.get(0).getName());
            textView_admin.setText(arrayList_getAdmin.get(0).getName());
            textView_consignee.setText(arrayList_getConsignee.get(0).getName());

            Date date_createTime = new Date((long)project_class.getCreateTime());
            SimpleDateFormat format_createTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_createTime.setText(format_createTime.format(date_createTime));

            Date date_updateTime = new Date((long)project_class.getUpdateTime());
            SimpleDateFormat format_updateTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_updateTime.setText(format_updateTime.format(date_updateTime));



        }
    }

    /**
     * AsyncTask异步任务类
     */
    private class HTTP_POST_updateProject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_updateProject = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_UPDATE_PROJECT, params[0]);
                Log.i("ProjectDetail_Activity","jsonString_updateProject:"+jsonString_updateProject);
            } catch (IOException e) {
                Log.e("ProjectDetail_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_updateProject != null) {
                loadListener_updateProject.loadComplete();
            }
        }
    }

}
