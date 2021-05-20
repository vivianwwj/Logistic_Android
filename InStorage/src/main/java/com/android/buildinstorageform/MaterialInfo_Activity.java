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
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetMaterialForQuery_recyclerViewAdapt;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MaterialInfo_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private final String URL_GET_CORPORATION = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String URL_POST_Delete_MATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/delete";
    private String url_GET_material = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/query";

    private EditText editText_name,editText_figureNumber;
    private Spinner spinner_corporation;
    private RecyclerView recyclerView_material;
    private GetMaterialForQuery_recyclerViewAdapt recyclerViewAdapt_getMaterial;
    private Button button_return,button_createMaterial,button_queryMaterial,button_deleteMaterial;

    private String jsonString_getCorporation,jsonString_getMaterial,jsonString_deleteMaterial;
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ArrayList<Material_class> arrayList_getMaterial = new ArrayList<>();
    private Material_class material_class = new Material_class();
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();
    private boolean isSpinnerFirstShow_corporation = true;

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_deleteMaterial;
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_deleteMaterial(IsLoadDataListener dataComplete) {
        this.loadListener_deleteMaterial = dataComplete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_info);

        editText_name = (EditText)findViewById(R.id.editText_name_materialInfo);
        editText_figureNumber = (EditText)findViewById(R.id.editText_figureNumber_materialInfo);

        spinner_corporation = (Spinner)findViewById(R.id.spinner_corporation_materialInfo);
        spinner_corporation.setOnItemSelectedListener(this);

        recyclerView_material = (RecyclerView)findViewById(R.id.recyclerView_material_materialInfo);

        button_return = (Button)findViewById(R.id.button_return_materialInfo);
        button_createMaterial = (Button)findViewById(R.id.button_createMaterial_materialInfo);
        button_queryMaterial = (Button)findViewById(R.id.button_queryMaterial_materialInfo);
        button_deleteMaterial = (Button)findViewById(R.id.button_deleteMaterial_materialInfo);
        button_return.setOnClickListener(this);
        button_createMaterial.setOnClickListener(this);
        button_queryMaterial.setOnClickListener(this);
        button_deleteMaterial.setOnClickListener(this);



        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences_getMaterial = getSharedPreferences("QueryMaterial", Activity.MODE_PRIVATE);
        //使用getString方法获得value，注意第2个参数是value的默认值
        url_GET_material = sharedPreferences_getMaterial.getString("getQueryURL_material","");
        Log.i("MaterialInfo_Activity", "url_GET_material:"+url_GET_material);

        new HTTP_GET_info().execute();
        new HTTP_GET_Material().execute(url_GET_material);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_materialInfo) {
            finish();

        } else if (i == R.id.button_createMaterial_materialInfo) {
            Intent intent_createMaterial = new Intent(MaterialInfo_Activity.this, CreateMaterial_Activity.class);
            startActivity(intent_createMaterial);

        } else if (i == R.id.button_queryMaterial_materialInfo) {
            material_class.setName(editText_name.getText() + "");
            material_class.setFigureNumber(editText_figureNumber.getText() + "");
            Log.i("MaterialInfo_Activity", "material_class.getQueryURL():" + material_class.getQueryURL());

            //实例化SharedPreferences对象（第一步）
            SharedPreferences sharedPreferences_setMaterial = getSharedPreferences("QueryMaterial", Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象（第二步）
            SharedPreferences.Editor editor_setMaterial = sharedPreferences_setMaterial.edit();
            //用putString的方法保存数据
            editor_setMaterial.putString("getQueryURL_material", material_class.getQueryURL());
            //提交当前数据
            editor_setMaterial.apply();

            new HTTP_GET_Material().execute(material_class.getQueryURL());

        } else if (i == R.id.button_deleteMaterial_materialInfo) {
            arrayList_checkBoxSelected = recyclerViewAdapt_getMaterial.getArrayList_checkBoxSelected();

            int count_deleteMaterial;
            //遍历提交被选中的物料信息给服务器，以执行移除物料操作
            for (count_deleteMaterial = 0; count_deleteMaterial < arrayList_checkBoxSelected.size(); count_deleteMaterial++) {
                material_class.setId(arrayList_getMaterial.get(arrayList_checkBoxSelected.get(count_deleteMaterial)).getId());
                new HTTP_POST_deleteMaterial().execute(material_class.deleteMaterialPostBody());
                setLoadDataComplete_deleteMaterial(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_deleteMaterial, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }
            new HTTP_GET_Material().execute(material_class.getQueryURL());

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_corporation_materialInfo) {
            if (isSpinnerFirstShow_corporation) {
                isSpinnerFirstShow_corporation = false;
            } else {
                material_class.setCorporation(arrayList_getCorporation.get(position).getId() + "");
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
                Log.i("MaterialInfo_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("MaterialInfo_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);

            SpinnerAdapter_twoColumns spinnerAdapter_corporation = new SpinnerAdapter_twoColumns(MaterialInfo_Activity.this,jsonString_getCorporation,arrayList_getCorporation,"id","name",true);
            spinner_corporation.setAdapter(spinnerAdapter_corporation);

        }
    }
    /**
     * AsyncTask异步任务类:HTTP_GET_Material
     */
    private class HTTP_GET_Material extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getMaterial = NetworkUtils.getResponseFromHttpUrl_GET(new URL(params[0]));
                Log.i("MaterialInfo_Activity","jsonString_getMaterial:"+jsonString_getMaterial);
            } catch (IOException e) {
                Log.e("MaterialInfo_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getMaterial = FastjsonTools.jsonStringParseToArrayList(jsonString_getMaterial,"$.content.data", Material_class.class);

            RecyclerView.LayoutManager material_layoutManager = new LinearLayoutManager(MaterialInfo_Activity.this);
            recyclerView_material.setLayoutManager(material_layoutManager);
            recyclerViewAdapt_getMaterial = new GetMaterialForQuery_recyclerViewAdapt(arrayList_getMaterial);
            recyclerView_material.setAdapter(recyclerViewAdapt_getMaterial);
            recyclerViewAdapt_getMaterial.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent_getMaterial = new Intent(MaterialInfo_Activity.this,MaterialDetail_Activity.class);
                    intent_getMaterial.putExtra("clickedPosition_getMaterial",position);
                    intent_getMaterial.putExtra("jsonString_getMaterial",jsonString_getMaterial);
                    intent_getMaterial.putExtra("jsonString_getCorporation",jsonString_getCorporation);
                    startActivity(intent_getMaterial);
                }
            });

        }
    }
    /**
     * AsyncTask异步任务类:HTTP_POST_deleteMaterial
     */
    private class HTTP_POST_deleteMaterial extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_deleteMaterial = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_Delete_MATERIAL, params[0]);
                Log.i("MaterialInfo_Activity","jsonString_createProduct:"+jsonString_deleteMaterial);
            } catch (IOException e) {
                Log.e("MaterialInfo_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_deleteMaterial != null) {
                loadListener_deleteMaterial.loadComplete();
            }
        }
    }
}
