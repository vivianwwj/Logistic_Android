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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreateMaterial_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText editText_name,editText_figureNumber;
    private Spinner spinner_selectCorporation;
    private Button button_return,button_createMaterial;

    private final String URL_GET_CORPORATION = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String URL_POST_CREATE_MATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/create";

    private String jsonString_getCorporation,jsonString_createMaterial;
    private ArrayList<Corporation_class> arrayList_corporation = new ArrayList<>();
    private Material_class material_class = new Material_class();

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_createMaterial;
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_createMaterial(IsLoadDataListener dataComplete) {
        this.loadListener_createMaterial = dataComplete;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creatematerial);

        editText_name = (EditText) findViewById(R.id.editView_name_createMaterial);
        editText_figureNumber = (EditText)findViewById(R.id.editView_figureNumber_createMaterial);

        button_return = (Button)findViewById(R.id.button_return_createMaterial);
        button_createMaterial = (Button)findViewById(R.id.button_createMaterial_createMaterial);
        button_return.setOnClickListener(this);
        button_createMaterial.setOnClickListener(this);

        spinner_selectCorporation = (Spinner)findViewById(R.id.spinner_selectCorporation_createMaterial);
        spinner_selectCorporation.setOnItemSelectedListener(this);

        new HTTP_GET().execute();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_createMaterial) {
            finish();

        } else if (i == R.id.button_createMaterial_createMaterial) {
            material_class.setName(editText_name.getText() + "");
            material_class.setFigureNumber(editText_figureNumber.getText() + "");
            new HTTP_POST_createMaterial().execute(material_class.createMaterialPostBody());
            setLoadDataComplete_createMaterial(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_createMaterial, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_selectCorporation_createMaterial) {
            material_class.setCorporation(arrayList_corporation.get(position).getId() + "");

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
                Log.i("CreateMaterial_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("CreateMaterial_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_corporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);

            SpinnerAdapter_twoColumns corporationAdapter = new SpinnerAdapter_twoColumns(CreateMaterial_Activity.this,jsonString_getCorporation,arrayList_corporation,"id","name",false);
            spinner_selectCorporation.setAdapter(corporationAdapter);

        }
    }

    /**
     * AsyncTask异步任务类:HTTP_POST_createMaterial
     */
    private class HTTP_POST_createMaterial extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_createMaterial = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_CREATE_MATERIAL, params[0]);
                Log.i("CreateMaterial_Activity","jsonString_createMaterial:"+jsonString_createMaterial);
            } catch (IOException e) {
                Log.e("CreateMaterial_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_createMaterial != null) {
                loadListener_createMaterial.loadComplete();
            }
        }
    }
}
