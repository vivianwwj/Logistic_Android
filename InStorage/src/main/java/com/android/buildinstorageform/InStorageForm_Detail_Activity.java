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
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.InStorageFormForQuery_class;
import com.android.buildinstorageform.data_class.InStorageFormForUpdate_class;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InStorageForm_Detail_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private TextView textView_id,textView_inStorageNumber,textView_inStorageStatus,textView_createTime,textView_updateTime,
                        textView_project,textView_corporation,textView_ifCompleted,textView_ifDeleted,textView_products,
                        textView_totalAmount,textView_totalVolume,textView_totalWeight,textView_inStorageTime;
    private Spinner spinner_accountStatus,spinner_lister,spinner_pickWorker,spinner_wareHouse,spinner_truck;
    private Button button_update,button_return;

    private String jsonString_getCorporation,jsonString_getProject,jsonString_getLister,jsonString_getPickWorker,jsonString_getWareHouse,jsonString_getTruck,jsonString_updateInStorageForm;
    private ArrayList<Admin_class> arrayList_getLister = new ArrayList<Admin_class>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<>();
    private ArrayList<Admin_class> arrayList_getPickWorker = new ArrayList<Admin_class>();
    private ArrayList<WareHouse_class> arrayList_getWareHouse = new ArrayList<WareHouse_class>();
    private ArrayList<Truck_class> arrayList_getTruck = new ArrayList<Truck_class>();
    private ArrayList<String> arrayList_getAccountStatus = new ArrayList<String>();
    InStorageFormForQuery_class inStorageForm_clicked = new InStorageFormForQuery_class();
    InStorageFormForUpdate_class inStorageFormForUpdate_class = new InStorageFormForUpdate_class();

    private static final String URL_GET_LISTER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private static final String URL_GET_PICKWORKER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private static final String URL_GET_WAREHOUSE= "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Warehouse/query";
    private static final String URL_GET_TRUCK = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
    private static final String URL_POST_UPDATE_INSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/update";
    private String url_get_corporation = "";
    private String url_get_project = "";
    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_updateInStorageForm;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_updateInStorageForm (IsLoadDataListener dataComplete) {
        this.loadListener_updateInStorageForm = dataComplete;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instorageform_detail);

        textView_id = (TextView)findViewById(R.id.textView_id_inStorageFormDetail);
        textView_inStorageNumber = (TextView)findViewById(R.id.textView_inStorageNumber_inStorageFormDetail);
        textView_inStorageStatus = (TextView)findViewById(R.id.textView_inStorageStatus_inStorageFormDetail);
        textView_createTime = (TextView)findViewById(R.id.textView_createTime_inStorageFormDetail);
        textView_updateTime = (TextView)findViewById(R.id.textView_updateTime_inStorageFormDetail);
        textView_project = (TextView)findViewById(R.id.textView_project_inStorageFormDetail);
        textView_corporation = (TextView)findViewById(R.id.textView_corporation_inStorageFormDetail);
        textView_ifCompleted = (TextView)findViewById(R.id.textView_ifCompleted_inStorageFormDetail);
        textView_ifDeleted = (TextView)findViewById(R.id.textView_ifDeleted_inStorageFormDetail);
        textView_products = (TextView)findViewById(R.id.textView_products_inStorageFormDetail);
        textView_totalAmount = (TextView)findViewById(R.id.textView_totalAmount_inStorageFormDetail);
        textView_totalVolume = (TextView)findViewById(R.id.textView_totalVolume_inStorageFormDetail);
        textView_totalWeight = (TextView)findViewById(R.id.textView_totalWeight_inStorageFormDetail);
        textView_inStorageTime = (TextView)findViewById(R.id.textView_inStorageTime_inStorageFormDetail);


        button_update = (Button)findViewById(R.id.button_update_inStorageFormDetail);
        button_return = (Button)findViewById(R.id.button_return_inStorageFormDetail);
        button_update.setOnClickListener(this);
        button_return.setOnClickListener(this);

        spinner_accountStatus = (Spinner)findViewById(R.id.spinner_accountStatus_inStorageFormDetail);
        spinner_lister = (Spinner)findViewById(R.id.spinner_lister_inStorageFormDetail);
        spinner_pickWorker = (Spinner)findViewById(R.id.spinner_pickWorker_inStorageFormDetail);
        spinner_wareHouse = (Spinner)findViewById(R.id.spinner_wareHouse_inStorageFormDetail);
        spinner_truck = (Spinner)findViewById(R.id.spinner_truck_inStorageFormDetail);
        spinner_accountStatus.setOnItemSelectedListener(this);
        spinner_lister.setOnItemSelectedListener(this);
        spinner_pickWorker.setOnItemSelectedListener(this);
        spinner_wareHouse.setOnItemSelectedListener(this);
        spinner_truck.setOnItemSelectedListener(this);

        String jsonString_getInStorageForm = getIntent().getStringExtra("jsonString_getInStorageForm");
        int clickedPosition =  getIntent().getIntExtra("clickedPosition",0);

        ArrayList<InStorageFormForQuery_class> arrayList_getInStorageForm = FastjsonTools.jsonStringParseToArrayList(jsonString_getInStorageForm,"$.content.data",InStorageFormForQuery_class.class);
        inStorageForm_clicked = arrayList_getInStorageForm.get(clickedPosition);



        url_get_corporation = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query?id="+inStorageForm_clicked.getCorporation();
        url_get_project = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query?id="+inStorageForm_clicked.getProject();

        new HTTP_GET().execute();
        new HTTP_GET_info().execute();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_inStorageFormDetail) {
            finish();

        } else if (i == R.id.button_update_inStorageFormDetail) {
            inStorageFormForUpdate_class.setEntityId(inStorageForm_clicked.getId());
            Log.i("InStorageForm_Detail_Activity", "inStorageFormForUpdate_class.getPostBody():" + inStorageFormForUpdate_class.getPostBody());

            new HTTP_POST_updateInStorageForm().execute(inStorageFormForUpdate_class.getPostBody());
            setLoadDataComplete_updateInStorageForm(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_updateInStorageForm, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_accountStatus_inStorageFormDetail) {
            inStorageFormForUpdate_class.setAccountStatus(position);

        } else if (i == R.id.spinner_lister_inStorageFormDetail) {
            inStorageFormForUpdate_class.setLister(arrayList_getLister.get(position).getId());

        } else if (i == R.id.spinner_pickWorker_inStorageFormDetail) {
            inStorageFormForUpdate_class.setPickWorker(arrayList_getPickWorker.get(position).getId());

        } else if (i == R.id.spinner_wareHouse_inStorageFormDetail) {
            inStorageFormForUpdate_class.setWareHouse(arrayList_getWareHouse.get(position).getId());

        } else if (i == R.id.spinner_truck_inStorageFormDetail) {
            inStorageFormForUpdate_class.setTruck(arrayList_getTruck.get(position).getId());

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
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_corporation));
                jsonString_getProject = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_get_project));
            } catch (IOException e) {
                Log.e("MaterialDetail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);
            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);

            textView_id.setText(inStorageForm_clicked.getId()+"");
            textView_inStorageNumber.setText(inStorageForm_clicked.getInStorageNumber());
            textView_inStorageStatus.setText(inStorageForm_clicked.getInStorageStatus());
            textView_ifCompleted.setText(inStorageForm_clicked.getIfCompleted());
            textView_ifDeleted.setText(inStorageForm_clicked.getIfDeleted());
            textView_products.setText(inStorageForm_clicked.getProducts().toString());
            textView_totalAmount.setText(inStorageForm_clicked.getTotalAmount()+"");
            textView_totalVolume.setText(inStorageForm_clicked.getTotalVolume()+"");
            textView_totalWeight.setText(inStorageForm_clicked.getTotalWeight()+"");

            textView_project.setText(arrayList_getProject.get(0).getName());
            textView_corporation.setText(arrayList_getCorporation.get(0).getName());

            Date date_createTime = new Date((long)inStorageForm_clicked.getCreateTime());
            SimpleDateFormat format_createTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_createTime.setText(format_createTime.format(date_createTime));

            Date date_updateTime = new Date((long)inStorageForm_clicked.getUpdateTime());
            SimpleDateFormat format_updateTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_updateTime.setText(format_updateTime.format(date_updateTime));

            Date date_inStorageTime = new Date((long)inStorageForm_clicked.getInStorageTime());
            SimpleDateFormat format_inStorageTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_inStorageTime.setText(format_inStorageTime.format(date_inStorageTime));



        }
    }
    /**
     * AsyncTask异步任务类:HTTP_GET
     */
    private class HTTP_GET extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getLister = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_LISTER));
                jsonString_getPickWorker= NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PICKWORKER));
                jsonString_getWareHouse = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_WAREHOUSE));
                jsonString_getTruck = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_TRUCK));
            } catch (IOException e) {
//                Log.e("InStorageForm_Detail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getLister = FastjsonTools.jsonStringParseToArrayList(jsonString_getLister,"$.content.data", Admin_class.class);
//            Log.i("InStorageForm_Detail_Activity", "arrayList_getMaterial:"+arrayList_getLister.toString());
            arrayList_getPickWorker = FastjsonTools.jsonStringParseToArrayList(jsonString_getPickWorker,"$.content.data", Admin_class.class);
            arrayList_getWareHouse = FastjsonTools.jsonStringParseToArrayList(jsonString_getWareHouse,"$.content.data", WareHouse_class.class);
            arrayList_getTruck = FastjsonTools.jsonStringParseToArrayList(jsonString_getTruck,"$.content.data", Truck_class.class);

            arrayList_getAccountStatus.add("未入账");
            arrayList_getAccountStatus.add("已入账");
            SpinnerAdapter_oneColumn spinnerAdapter_getAccountStatus = new SpinnerAdapter_oneColumn(InStorageForm_Detail_Activity.this,"NO",arrayList_getAccountStatus,"NO","",false);
            spinner_accountStatus.setAdapter(spinnerAdapter_getAccountStatus);
            if(inStorageForm_clicked.getAccountStatus().equals("未入账")){
                spinner_accountStatus.setSelection(0,true);
            }else {
                spinner_accountStatus.setSelection(1,true);
            }


            SpinnerAdapter_twoColumns spinnerAdapter_getLister = new SpinnerAdapter_twoColumns(InStorageForm_Detail_Activity.this,jsonString_getLister,arrayList_getLister,"id","name",false);
            spinner_lister.setAdapter(spinnerAdapter_getLister);
            int count_lister;
            for(count_lister = 0;count_lister<arrayList_getLister.size();count_lister++){
                if((""+arrayList_getLister.get(count_lister).getId()).equals(inStorageForm_clicked.getLister())){
                    spinner_lister.setSelection(count_lister,true);
                    break;
                }
            }


            SpinnerAdapter_twoColumns spinnerAdapter_getPickWorker = new SpinnerAdapter_twoColumns(InStorageForm_Detail_Activity.this,jsonString_getPickWorker,arrayList_getPickWorker,"id","name",false);
            spinner_pickWorker.setAdapter(spinnerAdapter_getPickWorker);
            int count_pickWorker;
            for(count_pickWorker = 0;count_pickWorker<arrayList_getPickWorker.size();count_pickWorker++){
                if((""+arrayList_getPickWorker.get(count_pickWorker).getId()).equals(inStorageForm_clicked.getPickWorker())){
                    spinner_pickWorker.setSelection(count_pickWorker,true);
                    break;
                }
            }

            SpinnerAdapter_twoColumns spinnerAdapter_getWareHouse = new SpinnerAdapter_twoColumns(InStorageForm_Detail_Activity.this,jsonString_getWareHouse,arrayList_getWareHouse,"id","name",false);
            spinner_wareHouse.setAdapter(spinnerAdapter_getWareHouse);
            int count_wareHouse;
            for(count_wareHouse = 0;count_wareHouse<arrayList_getWareHouse.size();count_wareHouse++){
                if(arrayList_getWareHouse.get(count_wareHouse).getId().equals(inStorageForm_clicked.getWarehouse())){
                    spinner_wareHouse.setSelection(count_wareHouse,true);
                    break;
                }
            }

            SpinnerAdapter_oneColumn spinnerAdapter_getTruck = new SpinnerAdapter_oneColumn(InStorageForm_Detail_Activity.this,jsonString_getTruck,arrayList_getTruck,"NO","carNumber",false);
            spinner_truck.setAdapter(spinnerAdapter_getTruck);
            int count_truck;
            for(count_truck = 0;count_truck<arrayList_getTruck.size();count_truck++){
                if((""+arrayList_getTruck.get(count_truck).getId()).equals(inStorageForm_clicked.getTruck())){
                    spinner_truck.setSelection(count_truck,true);
                    break;
                }
            }

        }
    }

    /**
     * AsyncTask异步任务类:HTTP_POST
     */
    private class HTTP_POST_updateInStorageForm extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_updateInStorageForm = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_UPDATE_INSTORAGEFORM,params[0]);
                Log.i("InStorageForm_Detail_Activity","jsonString_updateInStorageForm:"+jsonString_updateInStorageForm);
            } catch (IOException e) {
//                Log.e("InStorageForm_Detail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_updateInStorageForm != null) {
                loadListener_updateInStorageForm.loadComplete();
            }
        }
    }
}
