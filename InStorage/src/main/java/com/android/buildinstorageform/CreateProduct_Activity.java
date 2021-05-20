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
import com.android.buildinstorageform.data_class.ProductForQuery_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_oneColumn;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CreateProduct_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private final String URL_GET_CORPORATION = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String URL_GET_PROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
    private final String URL_GET_MATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/query";
    private final String URL_POST_CREATE_PRODUCT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/create";

    private EditText editText_name,editText_packetNumber,editText_length,editText_width,editText_height,editText_weight,editText_volume;
    private Spinner spinner_packetType,spinner_project,spinner_material,spinner_corporation;
    private Button button_return,button_createProduct;

    private String jsonString_getCorporation,jsonString_getProject,jsonString_getMaterial,jsonString_createProduct;
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<>();
    private ArrayList<Material_class> arrayList_getMaterial = new ArrayList<>();
    private ArrayList<String> arrayList_getPacketType = new ArrayList<>();
    private ProductForQuery_class productForQuery_class = new ProductForQuery_class();

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_createProduct;
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_createProduct(IsLoadDataListener dataComplete) {
        this.loadListener_createProduct = dataComplete;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createproduct);

        editText_name = (EditText)findViewById(R.id.editView_name_createProduct);
        editText_packetNumber = (EditText)findViewById(R.id.editView_packetNumber_createProduct);
        editText_length = (EditText)findViewById(R.id.editView_length_createProduct);
        editText_width = (EditText)findViewById(R.id.editView_width_createProduct);
        editText_height = (EditText)findViewById(R.id.editView_height_createProduct);
        editText_weight = (EditText)findViewById(R.id.editView_weight_createProduct);
        editText_volume = (EditText)findViewById(R.id.editView_volume_createProduct);

        button_return = (Button)findViewById(R.id.button_return_createProduct);
        button_createProduct = (Button)findViewById(R.id.button_createProduct_createProduct);
        button_return.setOnClickListener(this);
        button_createProduct.setOnClickListener(this);

        spinner_project = (Spinner)findViewById(R.id.spinner_project_createProduct);
        spinner_corporation = (Spinner)findViewById(R.id.spinner_corporation_createProduct);
        spinner_material = (Spinner)findViewById(R.id.spinner_material_createProduct);
        spinner_packetType = (Spinner)findViewById(R.id.spinner_packetType_creatProduct);
        spinner_project.setOnItemSelectedListener(this);
        spinner_corporation.setOnItemSelectedListener(this);
        spinner_material.setOnItemSelectedListener(this);
        spinner_packetType.setOnItemSelectedListener(this);

        new HTTP_GET().execute();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_createProduct) {
            finish();

        } else if (i == R.id.button_createProduct_createProduct) {
            productForQuery_class.setName(editText_name.getText() + "");
            productForQuery_class.setPacketNumber(editText_packetNumber.getText() + "");
            productForQuery_class.setLength(editText_length.getText() + "");
            productForQuery_class.setWidth(editText_width.getText() + "");
            productForQuery_class.setHeight(editText_height.getText() + "");
            productForQuery_class.setWeight(editText_weight.getText() + "");
            productForQuery_class.setVolume(editText_volume.getText() + "");

            Log.i("CreateProduct_Activity", "createProductPostBody:" + productForQuery_class.createProductPostBody());
            new HTTP_POST_createProduct().execute(productForQuery_class.createProductPostBody());
            setLoadDataComplete_createProduct(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_createProduct, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_packetType_creatProduct) {
            productForQuery_class.setPacketType(position + "");

        } else if (i == R.id.spinner_corporation_createProduct) {
            productForQuery_class.setCorporation(arrayList_getCorporation.get(position).getId());

        } else if (i == R.id.spinner_project_createProduct) {
            productForQuery_class.setProject(arrayList_getProject.get(position).getId());

        } else if (i == R.id.spinner_material_createProduct) {
            productForQuery_class.setMaterial(arrayList_getMaterial.get(position).getId());

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
                jsonString_getMaterial = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_MATERIAL));
                Log.i("CreateProduct_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("CreateProduct_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);
            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);
            arrayList_getMaterial = FastjsonTools.jsonStringParseToArrayList(jsonString_getMaterial,"$.content.data", Material_class.class);

            SpinnerAdapter_twoColumns spinnerAdapter_corporation = new SpinnerAdapter_twoColumns(CreateProduct_Activity.this,jsonString_getCorporation,arrayList_getCorporation,"id","name",false);
            spinner_corporation.setAdapter(spinnerAdapter_corporation);

            SpinnerAdapter_twoColumns spinnerAdapter_project = new SpinnerAdapter_twoColumns(CreateProduct_Activity.this,jsonString_getProject,arrayList_getProject,"id","name",false);
            spinner_project.setAdapter(spinnerAdapter_project);

            SpinnerAdapter_twoColumns spinnerAdapter_material = new SpinnerAdapter_twoColumns(CreateProduct_Activity.this,jsonString_getMaterial,arrayList_getMaterial,"id","name",false);
            spinner_material.setAdapter(spinnerAdapter_material);

            arrayList_getPacketType.add("铁框架");
            arrayList_getPacketType.add("铁支架");
            arrayList_getPacketType.add("木支架");
            arrayList_getPacketType.add("铁箱");
            arrayList_getPacketType.add("木箱");
            arrayList_getPacketType.add("捆扎");
            arrayList_getPacketType.add("裸装");
            SpinnerAdapter_oneColumn spinnerAdapter_packetType = new SpinnerAdapter_oneColumn(CreateProduct_Activity.this,"NO",arrayList_getPacketType,"NO","",false);
            spinner_packetType.setAdapter(spinnerAdapter_packetType);
        }
    }

    /**
     * AsyncTask异步任务类:HTTP_POST_createProduct
     */
    private class HTTP_POST_createProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_createProduct = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_CREATE_PRODUCT, params[0]);
                Log.i("CreateProduct_Activity","jsonString_createProduct:"+jsonString_createProduct);
            } catch (IOException e) {
                Log.e("CreateProduct_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_createProduct != null) {
                loadListener_createProduct.loadComplete();
            }
        }
    }
}
