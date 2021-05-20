package com.android.buildinstorageform;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.android.buildinstorageform.data_class.Customer_class;
import com.android.buildinstorageform.data_class.Manufacturer_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetProjectForQuery_recyclerViewAdapt;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ProjectInfo_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private final String URL_GET_CORPORATION = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String URL_GET_CUSTOMER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Customer/query";
    private final String URL_GET_MANUFACTURER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Manufacturer/query";
    private final String URL_GET_CONSIGNEE = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Consignee/query";
    private final String URL_GET_ADMIN = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private final String URL_POST_Delete_PROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/delete";
    private String url_GET_project = "";

    private EditText editText_name,editText_projectNumber;
    private Spinner spinner_customer,spinner_manufacturer,spinner_consignee,spinner_admin,spinner_corporation;
    private RecyclerView recyclerView_project;
    private GetProjectForQuery_recyclerViewAdapt recyclerViewAdapt_getProject;
    private Button button_return,button_createProject,button_queryProject,button_deleteProject,button_addMaterialToProject;

    private String jsonString_getProject,jsonString_getCustomer,jsonString_getManufacturer,jsonString_getConsignee,jsonString_getAdmin,jsonString_getCorporation,jsonString_deleteProject;
    private ArrayList<Customer_class> arrayList_getCustomer = new ArrayList<>();
    private ArrayList<Manufacturer_class> arrayList_getManufacturer = new ArrayList<>();
    private ArrayList<Consignee_class> arrayList_getConsignee = new ArrayList<>();
    private ArrayList<Admin_class> arrayList_getAdmin = new ArrayList<>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<>();
    private Project_class project_class = new Project_class();
    private boolean isSpinnerFirstShow_corporation = true,isSpinnerFirstShow_customer = true,isSpinnerFirstShow_manufacturer = true,isSpinnerFirstShow_consignee = true,isSpinnerFirstShow_admin = true;
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();
    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_deleteProject;
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_deleteProject(IsLoadDataListener dataComplete) {
        this.loadListener_deleteProject = dataComplete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_info);

        editText_name = (EditText)findViewById(R.id.editText_name_projectInfo);
        editText_projectNumber = (EditText)findViewById(R.id.editText_projectNumber_projectInfo);

        spinner_corporation = (Spinner)findViewById(R.id.spinner_corporation_projectInfo);
        spinner_customer = (Spinner)findViewById(R.id.spinner_customer_projectInfo);
        spinner_manufacturer = (Spinner)findViewById(R.id.spinner_manufacturer_projectInfo);
        spinner_consignee = (Spinner)findViewById(R.id.spinner_consignee_projectInfo);
        spinner_admin = (Spinner)findViewById(R.id.spinner_admin_projectInfo);
        spinner_admin.setOnItemSelectedListener(this);
        spinner_corporation.setOnItemSelectedListener(this);
        spinner_customer.setOnItemSelectedListener(this);
        spinner_manufacturer.setOnItemSelectedListener(this);
        spinner_consignee.setOnItemSelectedListener(this);

        recyclerView_project = (RecyclerView)findViewById(R.id.recyclerView_project_projectInfo);

        button_return = (Button)findViewById(R.id.button_return_projectInfo);
        button_createProject = (Button)findViewById(R.id.button_createProject_projectInfo);
        button_queryProject = (Button)findViewById(R.id.button_queryProject_projectInfo);
        button_deleteProject = (Button)findViewById(R.id.button_deleteProject_projectInfo);
        button_addMaterialToProject = (Button)findViewById(R.id.button_addMaterialToProject_projectInfo);
        button_return.setOnClickListener(this);
        button_createProject.setOnClickListener(this);
        button_queryProject.setOnClickListener(this);
        button_deleteProject.setOnClickListener(this);
        button_addMaterialToProject.setOnClickListener(this);

        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences_get = getSharedPreferences("QueryProject", Activity.MODE_PRIVATE);
        //使用getString方法获得value，注意第2个参数是value的默认值
        url_GET_project = sharedPreferences_get.getString("getQueryURL_project","");

        new HTTP_GET_info().execute();
        new HTTP_GET_project().execute(url_GET_project);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_projectInfo) {
            finish();

        } else if (i == R.id.button_createProject_projectInfo) {
            Intent intent_createProject = new Intent(ProjectInfo_Activity.this, CreateProject_Activity.class);
            startActivity(intent_createProject);

        } else if (i == R.id.button_addMaterialToProject_projectInfo) {
            Intent intent_addMaterialToProject = new Intent(ProjectInfo_Activity.this, RemoveMaterialFromProject_Activity.class);
            startActivity(intent_addMaterialToProject);

        } else if (i == R.id.button_queryProject_projectInfo) {
            project_class.setName(editText_name.getText() + "");
            project_class.setProjectNumber(editText_projectNumber.getText() + "");
            Log.i("ProjectInfo_Activity", "project_class.getQueryURL():" + project_class.getQueryURL());
            //实例化SharedPreferences对象（第一步）
            SharedPreferences sharedPreferences_set = getSharedPreferences("QueryProject", Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = sharedPreferences_set.edit();
            //用putString的方法保存数据
            editor.putString("getQueryURL_project", project_class.getQueryURL());
            //提交当前数据
            editor.apply();

            new HTTP_GET_project().execute(project_class.getQueryURL());

        } else if (i == R.id.button_deleteProject_projectInfo) {
            arrayList_checkBoxSelected = recyclerViewAdapt_getProject.getArrayList_checkBoxSelected();

            int count_deleteProject;
            //遍历提交被选中的物料信息给服务器，以执行移除物料操作
            for (count_deleteProject = 0; count_deleteProject < arrayList_checkBoxSelected.size(); count_deleteProject++) {
                project_class.setId(arrayList_getProject.get(arrayList_checkBoxSelected.get(count_deleteProject)).getId());
                new HTTP_POST_deleteProject().execute(project_class.deleteProjectPostBody());
                setLoadDataComplete_deleteProject(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_deleteProject, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }
            new HTTP_GET_project().execute(project_class.getQueryURL());

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_corporation_projectInfo) {
            if (isSpinnerFirstShow_corporation) {
                isSpinnerFirstShow_corporation = false;
            } else {
                project_class.setCorporation(arrayList_getCorporation.get(position).getId());
            }

        } else if (i == R.id.spinner_customer_projectInfo) {
            if (isSpinnerFirstShow_customer) {
                isSpinnerFirstShow_customer = false;
            } else {
                project_class.setCustomer(arrayList_getCustomer.get(position).getId());
            }

        } else if (i == R.id.spinner_manufacturer_projectInfo) {
            if (isSpinnerFirstShow_manufacturer) {
                isSpinnerFirstShow_manufacturer = false;
            } else {
                project_class.setManufacturer(arrayList_getManufacturer.get(position).getId());
            }

        } else if (i == R.id.spinner_consignee_projectInfo) {
            if (isSpinnerFirstShow_consignee) {
                isSpinnerFirstShow_consignee = false;
            } else {
                project_class.setConsignee(arrayList_getConsignee.get(position).getId());
            }

        } else if (i == R.id.spinner_admin_projectInfo) {
            if (isSpinnerFirstShow_admin) {
                isSpinnerFirstShow_admin = false;
            } else {
                project_class.setAdmin(arrayList_getAdmin.get(position).getId());
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask异步任务类:HTTP_GET_info
     */
    private class HTTP_GET_info extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_CORPORATION));
                jsonString_getCustomer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_CUSTOMER));
                jsonString_getManufacturer = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_MANUFACTURER));
                jsonString_getConsignee = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_CONSIGNEE));
                jsonString_getAdmin = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_ADMIN));
                Log.i("ProjectInfo_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("ProjectInfo_Activity",Log.getStackTraceString(e));
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

            SpinnerAdapter_twoColumns spinnerAdapter_corporation = new SpinnerAdapter_twoColumns(ProjectInfo_Activity.this,jsonString_getCorporation,arrayList_getCorporation,"id","name",true);
            spinner_corporation.setAdapter(spinnerAdapter_corporation);

            SpinnerAdapter_twoColumns spinnerAdapter_customer = new SpinnerAdapter_twoColumns(ProjectInfo_Activity.this,jsonString_getCustomer,arrayList_getCustomer,"id","name",true);
            spinner_customer.setAdapter(spinnerAdapter_customer);

            SpinnerAdapter_twoColumns spinnerAdapter_manufacturer = new SpinnerAdapter_twoColumns(ProjectInfo_Activity.this,jsonString_getManufacturer,arrayList_getManufacturer,"id","name",true);
            spinner_manufacturer.setAdapter(spinnerAdapter_manufacturer);

            SpinnerAdapter_twoColumns spinnerAdapter_consignee = new SpinnerAdapter_twoColumns(ProjectInfo_Activity.this,jsonString_getConsignee,arrayList_getConsignee,"id","name",true);
            spinner_consignee.setAdapter(spinnerAdapter_consignee);

            SpinnerAdapter_twoColumns spinnerAdapter_admin = new SpinnerAdapter_twoColumns(ProjectInfo_Activity.this,jsonString_getAdmin,arrayList_getAdmin,"id","name",true);
            spinner_admin.setAdapter(spinnerAdapter_admin);

        }
    }
    /**
     * AsyncTask异步任务类:HTTP_GET_project
     */
    private class HTTP_GET_project extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getProject = NetworkUtils.getResponseFromHttpUrl_GET(new URL(params[0]));
                Log.i("ProjectInfo_Activity","jsonString_getProject:"+jsonString_getProject);
            } catch (IOException e) {
                Log.e("ProjectInfo_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);

            Log.i("ProjectInfo_Activity","arrayList_getProject:"+arrayList_getProject.toString());
            RecyclerView.LayoutManager layoutManager_project = new LinearLayoutManager(ProjectInfo_Activity.this);
            recyclerView_project.setLayoutManager(layoutManager_project);
            recyclerViewAdapt_getProject = new GetProjectForQuery_recyclerViewAdapt(arrayList_getProject);
            recyclerView_project.setAdapter(recyclerViewAdapt_getProject);
            recyclerViewAdapt_getProject.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent_getProject = new Intent(ProjectInfo_Activity.this,ProjectDetail_Activity.class);
                    intent_getProject.putExtra("clickedPosition_getProject",position);
                    intent_getProject.putExtra("jsonString_getProject",jsonString_getProject);
                    startActivity(intent_getProject);
                }
            });

        }
    }
    /**
     * AsyncTask异步任务类:HTTP_POST_deleteProject
     */
    private class HTTP_POST_deleteProject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_deleteProject = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_Delete_PROJECT, params[0]);
                Log.i("ProjectInfo_Activity","jsonString_deleteProject:"+jsonString_deleteProject);
            } catch (IOException e) {
                Log.e("ProjectInfo_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_deleteProject != null) {
                loadListener_deleteProject.loadComplete();
            }
        }
    }
}
