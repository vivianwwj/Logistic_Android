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
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Admin_class;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.InStorageFormForQuery_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.data_class.Truck_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetInStorageForm_recyclerViewAdapter;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_oneColumn;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class QueryInStorageForm_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button button_queryInstorageForm,button_return,button_createInStorageForm,button_completeInStorageForm,button_deleteInStorageForm,button_addProductToInStorageForm;
    private Spinner spinner_selectProject,spinner_selectTruck,spinner_selectPickWorker,spinner_selectLister,spinner_selectAccountStatus,spinner_selectCorporation;
    private RecyclerView recyclerView_inStorageForm;
    private GetInStorageForm_recyclerViewAdapter recyclerViewAdapter_inStorageForm;

    private static final String URL_GET_PROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
    private static final String URL_GET_TRUCK = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Truck/query";
    private static final String URL_GET_PICKWORKER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private static final String URL_GET_LISTER = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Admin/query";
    private static final String URL_GET_CORPORATION= "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private static final String URL_POST_COMPLETE_INSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/complete";
    private static final String URL_POST_DELETE_INSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/delete";
    private String url_GET_inStorageFormQuery = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/query";

    private ArrayList<InStorageFormForQuery_class> arrayList_getInStorageForm = new ArrayList<InStorageFormForQuery_class>();
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();
    private String jsonString_getInStorageForm,jsonString_completeInStorageForm,jsonString_deleteInStorageForm;

    private String jsonString_getProject,jsonString_getTruck,jsonString_getLister,jsonString_getPickWorker,jsonString_getCorporation;
    private ArrayList<String> arrayList_getAccountStatus = new ArrayList<String>();
    private ArrayList<Admin_class> arrayList_getLister = new ArrayList<Admin_class>();
    private ArrayList<Admin_class> arrayList_getPickWorker = new ArrayList<Admin_class>();
    private ArrayList<Project_class> arrayList_getProject= new ArrayList<Project_class>();
    private ArrayList<Truck_class> arrayList_getTruck = new ArrayList<Truck_class>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<Corporation_class>();
    private InStorageFormForQuery_class inStorageFormForQuery_class = new InStorageFormForQuery_class();
    private boolean isSpinnerFirstShow_corporation = true,isSpinnerFirstShow_project = true,isSpinnerFirstShow_lister = true
            ,isSpinnerFirstShow_pickWorker = true,isSpinnerFirstShow_truck = true,isSpinnerFirstShow_accountStatus = true;

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_completeInStorageForm;
    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_deleteInStorageForm;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_completeInStorageForm (IsLoadDataListener dataComplete) {
        this.loadListener_completeInStorageForm = dataComplete;
    }
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_deleteInStorageForm (IsLoadDataListener dataComplete) {
        this.loadListener_deleteInStorageForm = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryinstorageform);

        recyclerView_inStorageForm = (RecyclerView)findViewById(R.id.recyclerView_inStorageForm_queryInStorageForm);

        button_queryInstorageForm = (Button)findViewById(R.id.button_query_queryInStorageForm);
        button_return = (Button)findViewById(R.id.button_return_queryInStorageForm);
        button_createInStorageForm = (Button)findViewById(R.id.button_createInStorageForm_queryInStorageForm);
        button_completeInStorageForm = (Button)findViewById(R.id.button_completeInStorageForm_queryInStorageForm);
        button_deleteInStorageForm = (Button)findViewById(R.id.button_deleteInStorageForm_queryInStorageForm);
        button_addProductToInStorageForm = (Button)findViewById(R.id.button_addProductToInStorageForm_queryInStorageForm);
        button_queryInstorageForm.setOnClickListener(this);
        button_return.setOnClickListener(this);
        button_createInStorageForm.setOnClickListener(this);
        button_completeInStorageForm.setOnClickListener(this);
        button_deleteInStorageForm.setOnClickListener(this);
        button_addProductToInStorageForm.setOnClickListener(this);

        spinner_selectProject = (Spinner)findViewById(R.id.spinner_selectProject_queryInStorageForm);
        spinner_selectTruck = (Spinner)findViewById(R.id.spinner_selectTruck_queryInStorageForm);
        spinner_selectPickWorker = (Spinner)findViewById(R.id.spinner_selectPickWorker_queryInStorageForm);
        spinner_selectLister = (Spinner)findViewById(R.id.spinner_selectLister_queryInStorageForm);
        spinner_selectAccountStatus = (Spinner)findViewById(R.id.spinner_selectAccountStatus_queryInStorageForm);
        spinner_selectCorporation = (Spinner)findViewById(R.id.spinner_selectCorporation_queryInStorageForm);
        spinner_selectProject.setOnItemSelectedListener(this);
        spinner_selectTruck.setOnItemSelectedListener(this);
        spinner_selectPickWorker.setOnItemSelectedListener(this);
        spinner_selectLister.setOnItemSelectedListener(this);
        spinner_selectAccountStatus.setOnItemSelectedListener(this);
        spinner_selectCorporation.setOnItemSelectedListener(this);

        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences_getCorporation = getSharedPreferences("inStorageFormQuery", Activity.MODE_PRIVATE);
        //使用getString方法获得value，注意第2个参数是value的默认值
        url_GET_inStorageFormQuery = sharedPreferences_getCorporation.getString("getQueryURL","");
//        Log.i("QueryInStorageForm_Activity", "url_GET_inStorageFormQuery:"+url_GET_inStorageFormQuery);

        inStorageFormForQuery_class.initalGetQueryURL();
        new HTTP_GET().execute();
        new HTTP_GET_inStorageForm().execute();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_queryInStorageForm) {
            finish();

        } else if (i == R.id.button_createInStorageForm_queryInStorageForm) {
            Intent intent_createInStorageForm = new Intent(QueryInStorageForm_Activity.this, CreateInStorageForm_Activity.class);
            startActivity(intent_createInStorageForm);

        } else if (i == R.id.button_addProductToInStorageForm_queryInStorageForm) {
            Intent intent_addProductToInStorageForm = new Intent(QueryInStorageForm_Activity.this, RemoveProductFromInStorageForm_Activity.class);
            startActivity(intent_addProductToInStorageForm);

        } else if (i == R.id.button_completeInStorageForm_queryInStorageForm) {
            arrayList_checkBoxSelected = recyclerViewAdapter_inStorageForm.getArrayList_checkBoxSelected();
//            Log.i("QueryInStorageForm_Activity", "arrayList_checkBoxSelected:" + arrayList_checkBoxSelected.toString());
            int count_completeInStorageForm;
            //遍历提交被选中的物料信息给服务器，以执行移除物料操作
            for (count_completeInStorageForm = 0; count_completeInStorageForm < arrayList_checkBoxSelected.size(); count_completeInStorageForm++) {
                String postParams_completeInStorageForm = "inStorageForm=" + arrayList_getInStorageForm.get(arrayList_checkBoxSelected.get(count_completeInStorageForm)).getId();
//                Log.i("QueryInStorageForm_Activity", "postParams_completeInStorageForm:" + postParams_completeInStorageForm);
                new HTTP_POST_completeInStorageForm().execute(postParams_completeInStorageForm);
                setLoadDataComplete_completeInStorageForm(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_completeInStorageForm, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }
            new HTTP_GET_inStorageForm().execute();

        } else if (i == R.id.button_deleteInStorageForm_queryInStorageForm) {
            arrayList_checkBoxSelected = recyclerViewAdapter_inStorageForm.getArrayList_checkBoxSelected();
//            Log.i("QueryInStorageForm_Activity", "arrayList_checkBoxSelected:" + arrayList_checkBoxSelected.toString());
            int count_deleteInStorageForm;
            for (count_deleteInStorageForm = 0; count_deleteInStorageForm < arrayList_checkBoxSelected.size(); count_deleteInStorageForm++) {
                String postParams_deleteInStorageForm = "entityId=" + arrayList_getInStorageForm.get(arrayList_checkBoxSelected.get(count_deleteInStorageForm)).getId();
//                Log.i("QueryInStorageForm_Activity", "postParams_removeMaterial:" + postParams_deleteInStorageForm);
                new HTTP_POST_deleteInStorageForm().execute(postParams_deleteInStorageForm);
                setLoadDataComplete_deleteInStorageForm(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_deleteInStorageForm, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }
            new HTTP_GET_inStorageForm().execute();

        } else if (i == R.id.button_query_queryInStorageForm) {
            url_GET_inStorageFormQuery = inStorageFormForQuery_class.getQueryURL();
            //实例化SharedPreferences对象（第一步）
            SharedPreferences sharedPreferences_setCorporation = getSharedPreferences("inStorageFormQuery", Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor = sharedPreferences_setCorporation.edit();
//            Log.i("QueryInStorageForm_Activity", "getQueryURL:" + inStorageFormForQuery_class.getQueryURL());

            //用putString的方法保存数据
            editor.putString("getQueryURL", inStorageFormForQuery_class.getQueryURL());
            //提交当前数据
            editor.apply();
            new HTTP_GET_inStorageForm().execute();

        } else {
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_selectCorporation_queryInStorageForm) {
            if (isSpinnerFirstShow_corporation) {
                isSpinnerFirstShow_corporation = false;
            } else {
                inStorageFormForQuery_class.setCorporation(arrayList_getCorporation.get(position).getId() + "");
            }

        } else if (i == R.id.spinner_selectProject_queryInStorageForm) {
            if (isSpinnerFirstShow_project) {
                isSpinnerFirstShow_project = false;
            } else {
                inStorageFormForQuery_class.setProject(arrayList_getProject.get(position).getId() + "");
            }

        } else if (i == R.id.spinner_selectLister_queryInStorageForm) {
            if (isSpinnerFirstShow_lister) {
                isSpinnerFirstShow_lister = false;
            } else {
                inStorageFormForQuery_class.setLister(arrayList_getLister.get(position).getId() + "");
            }

        } else if (i == R.id.spinner_selectPickWorker_queryInStorageForm) {
            if (isSpinnerFirstShow_pickWorker) {
                isSpinnerFirstShow_pickWorker = false;
            } else {
                inStorageFormForQuery_class.setPickWorker(arrayList_getPickWorker.get(position).getId() + "");
            }

        } else if (i == R.id.spinner_selectTruck_queryInStorageForm) {
            if (isSpinnerFirstShow_truck) {
                isSpinnerFirstShow_truck = false;
            } else {
                inStorageFormForQuery_class.setTruck(arrayList_getTruck.get(position).getId() + "");
            }

        } else if (i == R.id.spinner_selectAccountStatus_queryInStorageForm) {
            if (isSpinnerFirstShow_accountStatus) {
                isSpinnerFirstShow_accountStatus = false;
            } else {
                inStorageFormForQuery_class.setAccountStatus(position + "");
            }

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
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_CORPORATION));
                jsonString_getProject = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PROJECT));
                jsonString_getTruck = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_TRUCK));
                jsonString_getLister = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_LISTER));
                jsonString_getPickWorker = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PICKWORKER));
//                Log.i("QueryInStorageForm_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
//                Log.e("QueryInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);
//            Log.i("QueryInStorageForm_Activity","arrayList_getCorporation:"+arrayList_getCorporation.toString());
            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);
            arrayList_getTruck = FastjsonTools.jsonStringParseToArrayList(jsonString_getTruck,"$.content.data", Truck_class.class);
            arrayList_getLister = FastjsonTools.jsonStringParseToArrayList(jsonString_getLister,"$.content.data", Admin_class.class);
            arrayList_getPickWorker = FastjsonTools.jsonStringParseToArrayList(jsonString_getPickWorker,"$.content.data", Admin_class.class);


            SpinnerAdapter_twoColumns spinnerAdapter_getCorporation = new SpinnerAdapter_twoColumns(QueryInStorageForm_Activity.this,jsonString_getCorporation,arrayList_getCorporation,"id","name",true);
            spinner_selectCorporation.setAdapter(spinnerAdapter_getCorporation);


            SpinnerAdapter_twoColumns spinnerAdapter_getProject = new SpinnerAdapter_twoColumns(QueryInStorageForm_Activity.this,jsonString_getProject,arrayList_getProject,"id","name",true);
            spinner_selectProject.setAdapter(spinnerAdapter_getProject);


            SpinnerAdapter_oneColumn spinnerAdapter_getTruck = new SpinnerAdapter_oneColumn(QueryInStorageForm_Activity.this,jsonString_getTruck,arrayList_getTruck,"NO","carNumber",true);
            spinner_selectTruck.setAdapter(spinnerAdapter_getTruck);

            SpinnerAdapter_twoColumns spinnerAdapter_getLister = new SpinnerAdapter_twoColumns(QueryInStorageForm_Activity.this,jsonString_getLister,arrayList_getLister,"id","name",true);
            spinner_selectLister.setAdapter(spinnerAdapter_getLister);

            SpinnerAdapter_twoColumns spinnerAdapter_getPickWorker= new SpinnerAdapter_twoColumns(QueryInStorageForm_Activity.this,jsonString_getPickWorker,arrayList_getPickWorker,"id","name",true);
            spinner_selectPickWorker.setAdapter(spinnerAdapter_getPickWorker);

            arrayList_getAccountStatus.add("未入账");
            arrayList_getAccountStatus.add("已入账");
            SpinnerAdapter_oneColumn spinnerAdapter_getAccountStatus = new SpinnerAdapter_oneColumn(QueryInStorageForm_Activity.this,"NO",arrayList_getAccountStatus,"NO","",true);
            spinner_selectAccountStatus.setAdapter(spinnerAdapter_getAccountStatus);


        }
    }
    /**
     * 多线程
     */
    private class HTTP_GET_inStorageForm extends AsyncTask<URL,Void,String>  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            try{
                jsonString_getInStorageForm = NetworkUtils.getResponseFromHttpUrl_GET(new URL(url_GET_inStorageFormQuery));
//                Log.i("QueryInStorageForm_Activity","jsonString_getInStorageForm:"+jsonString_getInStorageForm);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);
            try {
                arrayList_getInStorageForm = FastjsonTools.jsonStringParseToArrayList(jsonString_getInStorageForm,"$.content.data",InStorageFormForQuery_class.class);
//                Log.i("QueryInStorageForm_Activity", "arrayList_getInStorageForm:"+arrayList_getInStorageForm.toString());

                RecyclerView.LayoutManager recyclerViewLayoutManager_inStorageForm = new LinearLayoutManager(QueryInStorageForm_Activity.this);
                recyclerView_inStorageForm.setLayoutManager(recyclerViewLayoutManager_inStorageForm);
                recyclerViewAdapter_inStorageForm = new GetInStorageForm_recyclerViewAdapter(arrayList_getInStorageForm);
                recyclerView_inStorageForm.setAdapter(recyclerViewAdapter_inStorageForm);
                recyclerViewAdapter_inStorageForm.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent_InStorageFormPreview = new Intent(QueryInStorageForm_Activity.this,InStorageForm_Detail_Activity.class);
                        intent_InStorageFormPreview.putExtra("clickedPosition",position);
                        intent_InStorageFormPreview.putExtra("jsonString_getInStorageForm",jsonString_getInStorageForm);
                        startActivity(intent_InStorageFormPreview);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * AsyncTask异步任务类:HTTP_POST
     */
    private class HTTP_POST_completeInStorageForm extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_completeInStorageForm = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_COMPLETE_INSTORAGEFORM,params[0]);
//                Log.i("QueryInStorageForm_Activity","jsonString_completeInStorageForm:"+jsonString_completeInStorageForm);
            } catch (IOException e) {
//                Log.e("QueryInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_completeInStorageForm != null) {
                loadListener_completeInStorageForm.loadComplete();
            }
        }
    }
    /**
     * AsyncTask异步任务类:HTTP_POST
     */
    private class HTTP_POST_deleteInStorageForm extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_deleteInStorageForm = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_DELETE_INSTORAGEFORM,params[0]);
            } catch (IOException e) {
//                Log.e("QueryInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_deleteInStorageForm != null) {
                loadListener_deleteInStorageForm.loadComplete();
            }
        }
    }
}
