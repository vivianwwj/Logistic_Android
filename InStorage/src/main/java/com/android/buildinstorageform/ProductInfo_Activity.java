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
import com.android.buildinstorageform.data_class.ProductForQuery_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetProductForQuery_recyclerViewAdapt;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_oneColumn;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ProductInfo_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private final String URL_GET_CORPORATION = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query";
    private final String URL_POST_Delete_PRODUCT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/delete";
    private String url_GET_product = "";

    private EditText editText_name,editText_packetNumber;
    private Spinner spinner_corporation,spinner_status;
    private RecyclerView recyclerView_product;
    private GetProductForQuery_recyclerViewAdapt recyclerViewAdapt_getProduct;
    private Button button_return,button_createProduct,button_queryProduct,button_deleteProduct;

    private String jsonString_getProduct,jsonString_getCorporation,jsonString_deleteProduct;
    private ArrayList<ProductForQuery_class> arrayList_getProduct = new ArrayList<>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private ArrayList<String> arrayList_getPacketType = new ArrayList<>();
    private ArrayList<String> arrayList_getStatus = new ArrayList<>();
    private boolean isSpinnerFirstShow_corporation = true,isSpinnerFirstShow_status = true;
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();
    private ProductForQuery_class productForQuery_class = new ProductForQuery_class();

    // ????????????AsyncTask??????????????????????????????
    private IsLoadDataListener loadListener_deleteProduct;
    // ????????????????????????????????????
    public void setLoadDataComplete_deleteProduct(IsLoadDataListener dataComplete) {
        this.loadListener_deleteProduct = dataComplete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);

        editText_name = (EditText)findViewById(R.id.editText_name_productInfo);
        editText_packetNumber = (EditText)findViewById(R.id.editText_packetNumber_productInfo);

        spinner_corporation = (Spinner)findViewById(R.id.spinner_corporation_productInfo);
        spinner_status = (Spinner)findViewById(R.id.spinner_status_productInfo);
        spinner_corporation.setOnItemSelectedListener(this);
        spinner_status.setOnItemSelectedListener(this);

        recyclerView_product = (RecyclerView)findViewById(R.id.recyclerView_product_productInfo);

        button_return = (Button)findViewById(R.id.button_return_productInfo);
        button_createProduct = (Button)findViewById(R.id.button_createProduct_productInfo);
        button_queryProduct = (Button)findViewById(R.id.button_queryProduct_productInfo);
        button_deleteProduct = (Button)findViewById(R.id.button_deleteProduct_productInfo);
        button_return.setOnClickListener(this);
        button_createProduct.setOnClickListener(this);
        button_queryProduct.setOnClickListener(this);
        button_deleteProduct.setOnClickListener(this);

        //?????????SharedPreferences??????????????????????????????SharedPreferences??????
        SharedPreferences sharedPreferences_get = getSharedPreferences("QueryProduct", Activity.MODE_PRIVATE);
        //??????getString????????????value????????????2????????????value????????????
        url_GET_product = sharedPreferences_get.getString("getQueryURL_product","");

        new HTTP_GET_info().execute();
        new HTTP_GET_product().execute(url_GET_product);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_productInfo) {
            finish();

        } else if (i == R.id.button_createProduct_productInfo) {
            Intent intent_createProduct = new Intent(ProductInfo_Activity.this, CreateProduct_Activity.class);
            startActivity(intent_createProduct);

        } else if (i == R.id.button_queryProduct_productInfo) {
            productForQuery_class.setName(editText_name.getText() + "");
            productForQuery_class.setPacketNumber(editText_packetNumber.getText() + "");
            Log.i("ProductInfo_Activity", "productForQuery_class.getQueryURL():" + productForQuery_class.getQueryURL());
            //?????????SharedPreferences?????????????????????
            SharedPreferences sharedPreferences_set = getSharedPreferences("QueryProduct", Activity.MODE_PRIVATE);
            //?????????SharedPreferences.Editor?????????????????????
            SharedPreferences.Editor editor = sharedPreferences_set.edit();
            //???putString?????????????????????
            editor.putString("getQueryURL_product", productForQuery_class.getQueryURL());
            //??????????????????
            editor.apply();
            new HTTP_GET_product().execute(productForQuery_class.getQueryURL());

        } else if (i == R.id.button_deleteProduct_productInfo) {
            arrayList_checkBoxSelected = recyclerViewAdapt_getProduct.getArrayList_checkBoxSelected();

            int count_deleteProduct;
            //??????????????????????????????????????????????????????????????????????????????
            for (count_deleteProduct = 0; count_deleteProduct < arrayList_checkBoxSelected.size(); count_deleteProduct++) {
                productForQuery_class.setId(arrayList_getProduct.get(arrayList_checkBoxSelected.get(count_deleteProduct)).getId());
                new HTTP_POST_deleteProduct().execute(productForQuery_class.deleteProductPostBody());
                setLoadDataComplete_deleteProduct(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_deleteProduct, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }
            new HTTP_GET_product().execute(productForQuery_class.getQueryURL());

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_corporation_productInfo) {
            if (isSpinnerFirstShow_corporation) {
                isSpinnerFirstShow_corporation = false;
            } else {
                productForQuery_class.setCorporation(arrayList_getCorporation.get(position).getId());
            }

        } else if (i == R.id.spinner_status_productInfo) {
            if (isSpinnerFirstShow_status) {
                isSpinnerFirstShow_status = false;
                productForQuery_class.setStatus("");
            } else {
                productForQuery_class.setStatus(position + "");
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask???????????????:HTTP_GET_info
     */
    private class HTTP_GET_info extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getCorporation = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_CORPORATION));
                Log.i("ProductInfo_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("ProductInfo_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);

            SpinnerAdapter_twoColumns spinnerAdapter_corporation = new SpinnerAdapter_twoColumns(ProductInfo_Activity.this,jsonString_getCorporation,arrayList_getCorporation,"id","name",true);
            spinner_corporation.setAdapter(spinnerAdapter_corporation);


            arrayList_getStatus.add("?????????");
            arrayList_getStatus.add("?????????????????????");
            arrayList_getStatus.add("?????????????????????");
            SpinnerAdapter_oneColumn spinnerAdapter_status = new SpinnerAdapter_oneColumn(ProductInfo_Activity.this,"NO",arrayList_getStatus,"NO","",true);
            spinner_status.setAdapter(spinnerAdapter_status);


        }
    }
    /**
     * AsyncTask???????????????:HTTP_GET_product
     */
    private class HTTP_GET_product extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getProduct = NetworkUtils.getResponseFromHttpUrl_GET(new URL(params[0]));
                Log.i("ProductInfo_Activity","jsonString_getProduct:"+jsonString_getProduct);
            } catch (IOException e) {
                Log.e("ProductInfo_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getProduct = FastjsonTools.jsonStringParseToArrayList(jsonString_getProduct,"$.content.data", ProductForQuery_class.class);
            Log.i("ProductInfo_Activity","arrayList_getProduct:"+arrayList_getProduct.toString());
            RecyclerView.LayoutManager layoutManager_project = new LinearLayoutManager(ProductInfo_Activity.this);
            recyclerView_product.setLayoutManager(layoutManager_project);
            recyclerViewAdapt_getProduct = new GetProductForQuery_recyclerViewAdapt(arrayList_getProduct);
            recyclerView_product.setAdapter(recyclerViewAdapt_getProduct);
            recyclerViewAdapt_getProduct.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent_getProduct = new Intent(ProductInfo_Activity.this,ProductDetail_Activity.class);
                    intent_getProduct.putExtra("clickedPosition_getProduct",position);
                    intent_getProduct.putExtra("jsonString_getProduct",jsonString_getProduct);
                    startActivity(intent_getProduct);
                }
            });

        }
    }
    /**
     * AsyncTask???????????????:HTTP_POST_deleteProduct
     */
    private class HTTP_POST_deleteProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_deleteProduct = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_Delete_PRODUCT, params[0]);
                Log.i("ProductInfo_Activity","jsonString_deleteProduct:"+jsonString_deleteProduct);
            } catch (IOException e) {
                Log.e("ProductInfo_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_deleteProduct != null) {
                loadListener_deleteProduct.loadComplete();
            }
        }
    }
}
