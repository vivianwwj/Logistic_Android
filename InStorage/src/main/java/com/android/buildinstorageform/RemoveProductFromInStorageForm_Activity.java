package com.android.buildinstorageform;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.android.buildinstorageform.data_class.InStorageFormForQuery_class;
import com.android.buildinstorageform.data_class.ProductForQuery_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetProduct_recyclerViewAdapt;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RemoveProductFromInStorageForm_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spinner_selectInStorageForm;
    private Button button_addProduct,button_complete,button_removeProduct;
    private RecyclerView recyclerView_queryProduct,recyclerView_productInInStorageForm;

    private static final String URL_POST_REMOVEPRODUCTFROMINSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/removeProduct";
    private static final String URL_GET_INSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/query";
    private static final String URL_GET_PRODUCT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/query";
    private static final String URL_POST_ADDPRODUCTTOINSTORAGEFORM = "http://47.111.122.217:8888/ScsyERP-web-Boss/InStorageForm/addProduct";

    private String jsonString_getInStorageForm,jsonString_productInInStorageForm,jsonString_queryProduct,jsonString_addProduct,jsonString_removeProduct;
    private ArrayList<InStorageFormForQuery_class> arrayList_getInStorageForm = new ArrayList<InStorageFormForQuery_class>();
    private ArrayList<ProductForQuery_class> arrayList_queryProduct = new ArrayList<ProductForQuery_class>();
    private ArrayList<ProductForQuery_class> arrayList_productInInStorageForm = new ArrayList<ProductForQuery_class>();
    private ArrayList<Number> arrayList_getProductsId_InInStorageForm = new ArrayList<Number>();
    private ArrayList<ProductForQuery_class> arrayList_getProductsFromInStorageForm = new ArrayList<ProductForQuery_class>();
    private ArrayList<Integer> arrayList_checkBoxSelected_productInInStorageForm = new ArrayList<Integer>();
    private ArrayList<Integer> arrayList_checkBoxSelected_queryProduct = new ArrayList<Integer>();
    private GetProduct_recyclerViewAdapt recyclerViewAdapt_productInInStorageForm,recyclerViewAdapt_queryProduct;
    private InStorageFormForQuery_class inStorageFormForQuery_class = new InStorageFormForQuery_class();
    private int int_InStorageFormSelectedPosition = 0;
    private boolean boolean_spinnerSelectedFirstTime = true;

    private IsLoadDataListener loadListener_addProduct;
    private IsLoadDataListener loadListener_removeProduct;
    private IsLoadDataListener loadListener_HTTP_GET;
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_removeProduct (IsLoadDataListener dataComplete) {
        this.loadListener_removeProduct = dataComplete;
    }
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_HTTP_GET (IsLoadDataListener dataComplete) {
        this.loadListener_HTTP_GET = dataComplete;
    }
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_addProduct (IsLoadDataListener dataComplete) {
        this.loadListener_addProduct = dataComplete;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removeproductfrominstorageform);

        button_addProduct = (Button)findViewById(R.id.button_addProduct_removeProductFromInStorageForm);
        button_complete = (Button)findViewById(R.id.button_complete_removeProductFromInStorageForm);
        button_removeProduct = (Button)findViewById(R.id.button_removeProduct_removeProductFromInStorageForm);
        button_addProduct.setOnClickListener(this);
        button_complete.setOnClickListener(this);
        button_removeProduct.setOnClickListener(this);

        recyclerView_productInInStorageForm = (RecyclerView)findViewById(R.id.recyclerView_productInInStorageForm_removeProductFromInStorageForm);
        recyclerView_queryProduct = (RecyclerView)findViewById(R.id.recyclerView_queryProduct_removeProductFormInStorageForm);


        spinner_selectInStorageForm = (Spinner)findViewById(R.id.spinner_selectInStorageForm_removeProductFromInStorageForm);
        spinner_selectInStorageForm.setOnItemSelectedListener(this);

        new HTTP_GET().execute();

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.button_addProduct_removeProductFromInStorageForm) {
            arrayList_checkBoxSelected_queryProduct = recyclerViewAdapt_queryProduct.getArrayList_checkBoxSelected();
            Log.i("RemoveProductFromInStorageForm_Activity", "arrayList_checkBoxSelected_queryProduct:" + arrayList_checkBoxSelected_queryProduct.toString());

            for (int i = 0; i < arrayList_checkBoxSelected_queryProduct.size(); i++) {
                String postParams_addProduct = "inStorageForm=" + inStorageFormForQuery_class.getId() + "&product=" + arrayList_queryProduct.get(arrayList_checkBoxSelected_queryProduct.get(i)).getId();
                Log.i("RemoveProductFromInStorageForm_Activity", "postParams_addProduct:" + postParams_addProduct);
                new AddProduct().execute(postParams_addProduct);
                setLoadDataComplete_addProduct(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_addProduct, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }

            new HTTP_GET().execute();
            setLoadDataComplete_HTTP_GET(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    showRecyclerViewFromInStorageFormSelected(int_InStorageFormSelectedPosition);
                }
            });

        } else if (i1 == R.id.button_complete_removeProductFromInStorageForm) {
            finish();


        } else if (i1 == R.id.button_removeProduct_removeProductFromInStorageForm) {
            Log.i("RemoveProductFromInStorageForm_Activity", "——————————————————————————————————————————");
            arrayList_checkBoxSelected_productInInStorageForm = recyclerViewAdapt_productInInStorageForm.getArrayList_checkBoxSelected();//获取被选中的checkbox的指针数组
            Log.i("RemoveProductFromInStorageForm_Activity", "arrayList_checkBoxSelected_productInInStorageForm:" + arrayList_checkBoxSelected_productInInStorageForm.toString());
            int count_removeMaterial;
            //遍历提交被选中的物料信息给服务器，以执行移除物料操作
            for (count_removeMaterial = 0; count_removeMaterial < arrayList_checkBoxSelected_productInInStorageForm.size(); count_removeMaterial++) {
                String postParams_removeProduct = "inStorageForm=" + inStorageFormForQuery_class.getId() + "&product=" + arrayList_getProductsFromInStorageForm.get(arrayList_checkBoxSelected_productInInStorageForm.get(count_removeMaterial)).getId();
                Log.i("RemoveMaterialFromProject_Activity", "postParams_removeMaterial:" + postParams_removeProduct);
                new RemoveProduct().execute(postParams_removeProduct);
                setLoadDataComplete_removeProduct(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_removeProduct, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }

            new HTTP_GET().execute();
            setLoadDataComplete_HTTP_GET(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    showRecyclerViewFromInStorageFormSelected(int_InStorageFormSelectedPosition);
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_selectInStorageForm_removeProductFromInStorageForm) {
            inStorageFormForQuery_class.setId(arrayList_getInStorageForm.get(position).getId());
            int_InStorageFormSelectedPosition = position;
            showRecyclerViewFromInStorageFormSelected(int_InStorageFormSelectedPosition);


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /**
     * 根据spinner_selectInStorageForm选中的入库单，显示该入库单中的货物信息
     * @param inStorageFormSelectedPosition spinner_selectProject的指针位置
     */
    @SuppressLint("LongLogTag")
    public void showRecyclerViewFromInStorageFormSelected(int inStorageFormSelectedPosition){
        arrayList_getProductsId_InInStorageForm = arrayList_getInStorageForm.get(inStorageFormSelectedPosition).getProducts();
        Log.i("RemoveProductFromInStorageForm_Activity", "arrayList_getProductsId_InInStorageForm:"+arrayList_getProductsId_InInStorageForm.toString());

        int count_ProductsId,count_Product;
        arrayList_getProductsFromInStorageForm.clear();
        for(count_ProductsId = 0;count_ProductsId < arrayList_getProductsId_InInStorageForm.size();count_ProductsId++){
            for(count_Product = 0;count_Product < arrayList_queryProduct.size();count_Product++){
                Log.i("RemoveProductFromInStorageForm_Activity", "arrayList_getProductsId  vs  arrayList_getProduct.getId:"+arrayList_getProductsId_InInStorageForm.get(count_ProductsId)+"vs"+arrayList_queryProduct.get(count_Product).getId());
                if(arrayList_queryProduct.get(count_Product).getId().equals(arrayList_getProductsId_InInStorageForm.get(count_ProductsId))){
                    arrayList_getProductsFromInStorageForm.add(arrayList_queryProduct.get(count_Product));
                    break;
                }
            }
        }
        Log.i("RemoveProductFromInStorageForm_Activity", "arrayList_getProductsFromInStorageForm:"+arrayList_getProductsFromInStorageForm.toString());

        RecyclerView.LayoutManager recyclerViewLayoutManager_productInInStorageForm = new LinearLayoutManager(RemoveProductFromInStorageForm_Activity.this);
        recyclerView_productInInStorageForm.setLayoutManager(recyclerViewLayoutManager_productInInStorageForm);
        recyclerViewAdapt_productInInStorageForm= new GetProduct_recyclerViewAdapt(arrayList_getProductsFromInStorageForm);
        recyclerView_productInInStorageForm.setAdapter(recyclerViewAdapt_productInInStorageForm);
        recyclerViewAdapt_productInInStorageForm.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });

        RecyclerView.LayoutManager recyclerViewLayoutManager_queryProduct = new LinearLayoutManager(RemoveProductFromInStorageForm_Activity.this);
        recyclerView_queryProduct.setLayoutManager(recyclerViewLayoutManager_queryProduct);
        recyclerViewAdapt_queryProduct= new GetProduct_recyclerViewAdapt(arrayList_queryProduct);
        recyclerView_queryProduct.setAdapter(recyclerViewAdapt_queryProduct);
        recyclerViewAdapt_queryProduct.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });
    }
    /**
     * AsyncTask异步任务类:HTTP_GET
     */
    private class HTTP_GET extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getInStorageForm = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_INSTORAGEFORM));
                jsonString_queryProduct = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GET_PRODUCT));
            } catch (IOException e) {
                Log.e("RemoveProductFromInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getInStorageForm = FastjsonTools.jsonStringParseToArrayList(jsonString_getInStorageForm,"$.content.data", InStorageFormForQuery_class.class);
            arrayList_queryProduct = FastjsonTools.jsonStringParseToArrayList(jsonString_queryProduct,"$.content.data", ProductForQuery_class.class);

            if(boolean_spinnerSelectedFirstTime) {
                SpinnerAdapter_twoColumns spinnerAdapter_getInStorageForm = new SpinnerAdapter_twoColumns(RemoveProductFromInStorageForm_Activity.this,jsonString_getInStorageForm,arrayList_getInStorageForm,"id","inStorageNumber",false);
                spinner_selectInStorageForm.setAdapter(spinnerAdapter_getInStorageForm);
                boolean_spinnerSelectedFirstTime = false;
            }

            if (loadListener_HTTP_GET != null) {
                loadListener_HTTP_GET.loadComplete();
            }

        }
    }
    /**
     * AsyncTask异步任务类:AddProduct
     */
    private class AddProduct extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {

            try {
                jsonString_addProduct = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_ADDPRODUCTTOINSTORAGEFORM,params[0]);
                Log.i("RemoveProductFromInStorageForm_Activity","jsonString_addProduct:"+jsonString_addProduct);
            } catch (IOException e) {
                Log.e("RemoveProductFromInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return jsonString_addProduct;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_addProduct != null) {
                loadListener_addProduct.loadComplete();
            }
        }
    }
    /**
     * AsyncTask异步任务类:RemoveProduct
     */
    public class RemoveProduct extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {

            try {
                jsonString_removeProduct = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_REMOVEPRODUCTFROMINSTORAGEFORM,params[0]);
                Log.i("RemoveProductFromInStorageForm_Activity","jsonString_removeProduct:"+jsonString_removeProduct);
            } catch (IOException e) {
                Log.e("RemoveProductFromInStorageForm_Activity",Log.getStackTraceString(e));
            }
            return jsonString_removeProduct;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_removeProduct != null) {
                loadListener_removeProduct.loadComplete();
            }
        }
    }
}
