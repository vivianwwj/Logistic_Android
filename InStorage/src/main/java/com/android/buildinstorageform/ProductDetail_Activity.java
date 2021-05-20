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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.data_class.ProductForQuery_class;
import com.android.buildinstorageform.data_class.Project_class;
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

public class ProductDetail_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static final String URL_GET_WAREHOUSE= "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Warehouse/query";
    private static final String URL_GET_MATERIAL= "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/query";
    private static final String URL_POST_UPDATE_PRODUCT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/update";
    private String url_get_corporation = "";
    private String url_get_project = "";

    private EditText editText_name,editText_packetNumber,editText_wareHouseLocation,editText_length,editText_width,editText_height,editText_volume,editText_weight;
    private Spinner spinner_material,spinner_packetType,spinner_wareHouse;
    private TextView textView_id,textView_project,textView_corporation,textView_inStorageForm,textView_ifDeleted,textView_status,textView_createTime,textView_updateTime;
    private Button button_return,button_update;

    private String jsonString_getProduct,jsonString_updateProduct,jsonString_getMaterial,jsonString_getWareHouse,jsonString_getCorporation,jsonString_getProject;
    private ArrayList<String> arrayList_getPacketType = new ArrayList<>();

    private ArrayList<Project_class> arrayList_getProject = new ArrayList<>();
    private ArrayList<Material_class> arrayList_getMaterial = new ArrayList<>();
    private ArrayList<WareHouse_class> arrayList_getWareHouse = new ArrayList<>();
    private ArrayList<ProductForQuery_class> arrayList_getProduct = new ArrayList<>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ProductForQuery_class productForQuery_class = new ProductForQuery_class();
    private int clickedPosition_getProduct;

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_updateProduct;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_updateProduct (IsLoadDataListener dataComplete) {
        this.loadListener_updateProduct = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        editText_name = (EditText)findViewById(R.id.editText_name_productDetail);
        editText_packetNumber = (EditText)findViewById(R.id.editText_packetNumber_productDetail);
        editText_wareHouseLocation = (EditText) findViewById(R.id.editText_wareHouseLocation_productDetail);
        editText_length = (EditText) findViewById(R.id.editText_length_productDetail);
        editText_width = (EditText) findViewById(R.id.editText_width_productDetail);
        editText_height = (EditText) findViewById(R.id.editText_height_productDetail);
        editText_volume = (EditText) findViewById(R.id.editText_volume_productDetail);
        editText_weight = (EditText) findViewById(R.id.editText_weight_productDetail);



        textView_id = (TextView)findViewById(R.id.textView_id_productDetail);
        textView_corporation = (TextView)findViewById(R.id.textView_corporation_productDetail);
        textView_createTime = (TextView)findViewById(R.id.textView_createTime_productDetail);
        textView_ifDeleted = (TextView)findViewById(R.id.textView_ifDeleted_productDetail);
        textView_inStorageForm = (TextView)findViewById(R.id.textView_inStorageForm_productDetail);
        textView_project = (TextView)findViewById(R.id.textView_project_productDetail);
        textView_status = (TextView)findViewById(R.id.textView_status_productDetail);
        textView_updateTime = (TextView)findViewById(R.id.textView_updateTime_productDetail);

        spinner_material = (Spinner)findViewById(R.id.spinner_material_productDetail);
        spinner_packetType = (Spinner)findViewById(R.id.spinner_packetType_productDetail);
        spinner_wareHouse = (Spinner)findViewById(R.id.spinner_wareHouse_productDetail);
        spinner_material.setOnItemSelectedListener(this);
        spinner_packetType.setOnItemSelectedListener(this);
        spinner_wareHouse.setOnItemSelectedListener(this);

        button_return = (Button)findViewById(R.id.button_return_productDetail);
        button_update = (Button)findViewById(R.id.button_update_productDetail);
        button_return.setOnClickListener(this);
        button_update.setOnClickListener(this);

        jsonString_getProduct = getIntent().getStringExtra("jsonString_getProduct");
        clickedPosition_getProduct =  getIntent().getIntExtra("clickedPosition_getProduct",0);
        arrayList_getProduct = FastjsonTools.jsonStringParseToArrayList(jsonString_getProduct,"$.content.data",ProductForQuery_class.class);
        productForQuery_class = arrayList_getProduct.get(clickedPosition_getProduct);



        url_get_corporation = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query?id="+productForQuery_class.getCorporation();
        url_get_project = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query?id="+productForQuery_class.getProject();

        new HTTP_GET().execute();

        new HTTP_GET_info().execute();


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_productDetail) {
            finish();

        } else if (i == R.id.button_update_productDetail) {
            productForQuery_class.setName(editText_name.getText() + "");
            productForQuery_class.setPacketNumber(editText_packetNumber.getText() + "");
            productForQuery_class.setWareHouseLocation(editText_wareHouseLocation.getText() + "");
            productForQuery_class.setLength(editText_length.getText() + "");
            productForQuery_class.setWidth(editText_width.getText() + "");
            productForQuery_class.setHeight(editText_height.getText() + "");
            productForQuery_class.setVolume(editText_volume.getText() + "");
            productForQuery_class.setWeight(editText_weight.getText() + "");

            new HTTP_POST_updateProduct().execute(productForQuery_class.updateProductPostBody());
            setLoadDataComplete_updateProduct(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_updateProduct, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_packetType_productDetail) {
            productForQuery_class.setPacketType(arrayList_getPacketType.get(position));

        } else if (i == R.id.spinner_material_productDetail) {
            productForQuery_class.setMaterial(arrayList_getMaterial.get(position).getId());

        } else if (i == R.id.spinner_wareHouse_productDetail) {
            productForQuery_class.setWarehouse(arrayList_getWareHouse.get(position).getId());

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
                jsonString_getMaterial = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_MATERIAL));
                jsonString_getWareHouse = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_WAREHOUSE));

            } catch (IOException e) {
//                Log.e("InStorageForm_Detail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getWareHouse = FastjsonTools.jsonStringParseToArrayList(jsonString_getWareHouse,"$.content.data", WareHouse_class.class);
            arrayList_getMaterial = FastjsonTools.jsonStringParseToArrayList(jsonString_getMaterial,"$.content.data", Material_class.class);

            arrayList_getPacketType.add("铁框架");
            arrayList_getPacketType.add("铁支架");
            arrayList_getPacketType.add("木支架");
            arrayList_getPacketType.add("铁箱");
            arrayList_getPacketType.add("木箱");
            arrayList_getPacketType.add("捆扎");
            arrayList_getPacketType.add("裸装");
            SpinnerAdapter_oneColumn spinnerAdapter_packetType = new SpinnerAdapter_oneColumn(ProductDetail_Activity.this,"NO",arrayList_getPacketType,"NO","",false);
            spinner_packetType.setAdapter(spinnerAdapter_packetType);
            if(productForQuery_class.getPacketType().equals("铁框架")){
                spinner_packetType.setSelection(0,true);
            }else if(productForQuery_class.getPacketType().equals("铁支架")){
                spinner_packetType.setSelection(1,true);
            }else if(productForQuery_class.getPacketType().equals("木支架")){
                spinner_packetType.setSelection(2,true);
            }else if(productForQuery_class.getPacketType().equals("铁箱")){
                spinner_packetType.setSelection(3,true);
            }else if(productForQuery_class.getPacketType().equals("木箱")){
                spinner_packetType.setSelection(4,true);
            }else if(productForQuery_class.getPacketType().equals("捆扎")){
                spinner_packetType.setSelection(5,true);
            }else if(productForQuery_class.getPacketType().equals("裸装")){
                spinner_packetType.setSelection(6,true);
            }else {
                spinner_packetType.setSelection(7,true);
            }

            SpinnerAdapter_twoColumns spinnerAdapter_getWareHouse = new SpinnerAdapter_twoColumns(ProductDetail_Activity.this,jsonString_getWareHouse,arrayList_getWareHouse,"id","name",false);
            spinner_wareHouse.setAdapter(spinnerAdapter_getWareHouse);
            int count_wareHouse;
            for(count_wareHouse = 0;count_wareHouse<arrayList_getWareHouse.size();count_wareHouse++){
                if(arrayList_getWareHouse.get(count_wareHouse).getId().equals(productForQuery_class.getWarehouse())){
                    spinner_wareHouse.setSelection(count_wareHouse,true);
                    break;
                }
            }

            SpinnerAdapter_twoColumns spinnerAdapter_getMaterial = new SpinnerAdapter_twoColumns(ProductDetail_Activity.this,jsonString_getMaterial,arrayList_getMaterial,"id","name",false);
            spinner_material.setAdapter(spinnerAdapter_getMaterial);
            int count_material;
            for(count_material = 0;count_material<arrayList_getMaterial.size();count_material++){
                if(arrayList_getMaterial.get(count_material).getId().equals(productForQuery_class.getMaterial())){
                    spinner_material.setSelection(count_material,true);
                    break;
                }
            }

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

            editText_name.setText(productForQuery_class.getName());
            editText_packetNumber.setText(productForQuery_class.getPacketNumber());
            editText_wareHouseLocation.setText(productForQuery_class.getWareHouseLocation());
            editText_length.setText(productForQuery_class.getLength());
            editText_width.setText(productForQuery_class.getWidth());
            editText_height.setText(productForQuery_class.getHeight());
            editText_volume.setText(productForQuery_class.getVolume());
            editText_weight.setText(productForQuery_class.getWeight());

            textView_status.setText(productForQuery_class.getStatus());
            textView_project.setText(arrayList_getProject.get(0).getName());
            textView_inStorageForm.setText(productForQuery_class.getInStorageForm()+"");
            textView_ifDeleted.setText(productForQuery_class.getIfDeleted());
            textView_corporation.setText(arrayList_getCorporation.get(0).getName());
            textView_id.setText(productForQuery_class.getId()+"");

            Date date_createTime = new Date((long)productForQuery_class.getCreateTime());
            SimpleDateFormat format_createTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_createTime.setText(format_createTime.format(date_createTime));

            Date date_updateTime = new Date((long)productForQuery_class.getUpdateTime());
            SimpleDateFormat format_updateTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_updateTime.setText(format_updateTime.format(date_updateTime));



        }
    }
    /**
         * AsyncTask异步任务类:HTTP_POST
         */
        private class HTTP_POST_updateProduct extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {
                try {
                    jsonString_updateProduct = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_UPDATE_PRODUCT,params[0]);
                    Log.i("ProductDetail_Activity","jsonString_updateProduct:"+jsonString_updateProduct);
                } catch (IOException e) {
                    Log.e("ProductDetail_Activity",Log.getStackTraceString(e));
                }
                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (loadListener_updateProduct != null) {
                    loadListener_updateProduct.loadComplete();
                }
            }
    }


}
