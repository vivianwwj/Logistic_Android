package com.android.buildinstorageform;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Admin_class;
import com.android.buildinstorageform.data_class.InStorageFormForCreate_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.data_class.Truck_class;
import com.android.buildinstorageform.data_class.WareHouse_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_oneColumn;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreateInStorageForm_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spinner_selectProject,spinner_selectTruck,spinner_selectWareHouse,spinner_selectPickWorker,spinner_selectAccountStatus;
    private Button button_scanCarPlate,button_createInStorageForm,button_complete;
    private TextView textView_lister,textView_corporation;

    private static final String URL_POST_CREATEINSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/create";
    private static final String URL_GET_PROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
    private static final String URL_GET_TRUCk = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
    private static final String URL_GET_WAREHOUSE = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Warehouse/query";
    private static final String URL_GET_PICKWORKER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";

    private String jsonString_getProject,jsonString_getTruck,jsonString_getWareHouse,jsonString_getPickerWorker,jsonString_createInStorageForm;
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<Project_class>();
    private ArrayList<Truck_class> arrayList_getTruck = new ArrayList<Truck_class>();
    private ArrayList<WareHouse_class> arrayList_getWareHouse = new ArrayList<WareHouse_class>();
    private ArrayList<Admin_class> arrayList_getPickerWorker = new ArrayList<Admin_class>();
    private ArrayList<String> arrayList_getAccountStatus = new ArrayList<String>();
    private InStorageFormForCreate_class inStorageFormForCreate_class = new InStorageFormForCreate_class();

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadDataListener_createInStorageForm;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_createInStorageForm(IsLoadDataListener dataComplete) {
        this.loadDataListener_createInStorageForm = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createinstorageform);

        textView_lister = (TextView)findViewById(R.id.textView_lister_createInStorageForm);
        textView_corporation = (TextView)findViewById(R.id.textView_corporation_createInStorageForm);

        button_scanCarPlate = (Button)findViewById(R.id.button_scanCarPlate_createInStorageForm);
        button_createInStorageForm = (Button)findViewById(R.id.button_createInStorageForm_createInStorageForm);
        button_complete = (Button)findViewById(R.id.button_complete_createInStorageForm);
        button_scanCarPlate.setOnClickListener(this);
        button_createInStorageForm.setOnClickListener(this);
        button_complete.setOnClickListener(this);

        spinner_selectProject = (Spinner)findViewById(R.id.spinner_selectProject_createInStorageForm);
        spinner_selectTruck = (Spinner)findViewById(R.id.spinner_selectTruck_createInStorageForm);
        spinner_selectWareHouse = (Spinner)findViewById(R.id.spinner_selectWareHouse_createInStorageForm);
        spinner_selectPickWorker = (Spinner)findViewById(R.id.spinner_selectPickWorker_createInStorageForm);
        spinner_selectAccountStatus = (Spinner)findViewById(R.id.spinner_selectAccountStatus_createInStorageForm);
        spinner_selectProject.setOnItemSelectedListener(this);
        spinner_selectTruck.setOnItemSelectedListener(this);
        spinner_selectWareHouse.setOnItemSelectedListener(this);
        spinner_selectPickWorker.setOnItemSelectedListener(this);
        spinner_selectAccountStatus.setOnItemSelectedListener(this);

        textView_lister.setText("1");
        textView_corporation.setText("1");

        new GetStringFromServer().execute();

       }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_complete_createInStorageForm) {
            finish();

        } else if (i == R.id.button_scanCarPlate_createInStorageForm) {
        } else if (i == R.id.button_createInStorageForm_createInStorageForm) {
            inStorageFormForCreate_class.setLister(1);
            inStorageFormForCreate_class.setCorporation(1);
            String postBody_createInStorageForm = inStorageFormForCreate_class.postBody_createInStorageForm();
            new CreateInStorageFormPost().execute(postBody_createInStorageForm);
            setLoadDataComplete_createInStorageForm(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    if (("" + JSONPath.read(jsonString_createInStorageForm, "$.msg")).equals("创建入库单信息成功")) {
                        Toast.makeText(getApplicationContext(), "创建入库单信息成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_createInStorageForm, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_selectProject_createInStorageForm) {
            inStorageFormForCreate_class.setProject(arrayList_getProject.get(position).getId());

        } else if (i == R.id.spinner_selectTruck_createInStorageForm) {
            inStorageFormForCreate_class.setTruck(arrayList_getTruck.get(position).getId());

        } else if (i == R.id.spinner_selectWareHouse_createInStorageForm) {
            inStorageFormForCreate_class.setWarehouse(arrayList_getWareHouse.get(position).getId());

        } else if (i == R.id.spinner_selectPickWorker_createInStorageForm) {
            inStorageFormForCreate_class.setPickWorker(arrayList_getPickerWorker.get(position).getId());

        } else if (i == R.id.spinner_selectAccountStatus_createInStorageForm) {
            inStorageFormForCreate_class.setAccountStatus(position);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask异步任务类:GetStringFromServer
     */
    public class GetStringFromServer extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getProject = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PROJECT));
                jsonString_getTruck = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_TRUCk));
                jsonString_getWareHouse = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_WAREHOUSE));
                jsonString_getPickerWorker = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PICKWORKER));
//                Log.i("CreateInStorageForm_Activity","jsonString_getProject:"+jsonString_getProject);
            } catch (IOException e) {
//                Log.e("CreateInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);
            SpinnerAdapter_twoColumns spinnerAdapter_getProject = new SpinnerAdapter_twoColumns(CreateInStorageForm_Activity.this,jsonString_getProject,arrayList_getProject,"id","name",false);
            spinner_selectProject.setAdapter(spinnerAdapter_getProject);

            arrayList_getTruck = FastjsonTools.jsonStringParseToArrayList(jsonString_getTruck,"$.content.data", Truck_class.class);
            SpinnerAdapter_oneColumn spinnerAdapter_getTruck = new SpinnerAdapter_oneColumn(CreateInStorageForm_Activity.this,jsonString_getTruck,arrayList_getTruck,"NO","carNumber",false);
            spinner_selectTruck.setAdapter(spinnerAdapter_getTruck);

            arrayList_getWareHouse = FastjsonTools.jsonStringParseToArrayList(jsonString_getWareHouse,"$.content.data", WareHouse_class.class);
            SpinnerAdapter_twoColumns spinnerAdapter_getWareHouse = new SpinnerAdapter_twoColumns(CreateInStorageForm_Activity.this,jsonString_getWareHouse,arrayList_getWareHouse,"id","name",false);
            spinner_selectWareHouse.setAdapter(spinnerAdapter_getWareHouse);

            arrayList_getPickerWorker =  FastjsonTools.jsonStringParseToArrayList(jsonString_getPickerWorker,"$.content.data", Admin_class.class);
            SpinnerAdapter_twoColumns spinnerAdapter_getPickerWorker = new SpinnerAdapter_twoColumns(CreateInStorageForm_Activity.this,jsonString_getPickerWorker,arrayList_getPickerWorker,"id","name",false);
            spinner_selectPickWorker.setAdapter(spinnerAdapter_getPickerWorker);

            arrayList_getAccountStatus.add("未入账");
            arrayList_getAccountStatus.add("已入账");
            SpinnerAdapter_oneColumn spinnerAdapter_getAccountStatus = new SpinnerAdapter_oneColumn(CreateInStorageForm_Activity.this,"NO",arrayList_getAccountStatus,"NO","",false);
            spinner_selectAccountStatus.setAdapter(spinnerAdapter_getAccountStatus);

//            if (loadListener_getMaterialFromProject != null) {
//                loadListener_getMaterialFromProject.loadComplete();
//            }

        }
    }

    /**
     * AsyncTask异步任务类:CreateProjectPost
     */
    public class CreateInStorageFormPost extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                jsonString_createInStorageForm = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_CREATEINSTORAGEFORM, params[0]);
            } catch (IOException e) {
//                Log.e("CreateInStorageForm_Activity", Log.getStackTraceString(e));
            }
//            Log.i("CreateInStorageForm_Activity", "jsonString_createInStorageForm:" + jsonString_createInStorageForm);


            return jsonString_createInStorageForm;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadDataListener_createInStorageForm != null) {
                loadDataListener_createInStorageForm.loadComplete();
            }
        }
    }
}
