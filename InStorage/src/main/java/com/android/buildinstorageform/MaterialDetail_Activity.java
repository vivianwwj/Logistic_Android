package com.android.buildinstorageform;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.data_class.Corporation_class;
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MaterialDetail_Activity extends AppCompatActivity implements View.OnClickListener{

    private final String URL_POST_UPDATE_MATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/update";
    private String url_get_corporation = "";

    private EditText editText_name,editText_figureNumber;
    private TextView textView_id,textView_corporation,textView_ifDeleted,textView_createTime,textView_updateTime;
    private Button button_return,button_update;

    private String jsonString_getMaterial,jsonString_getCorporation,jsonString_updateMaterial;
    private ArrayList<Material_class> arrayList_getMaterial = new ArrayList<>();
    private ArrayList<Corporation_class> arrayList_getCorporation = new ArrayList<>();
    private Material_class material_class = new Material_class();
    private Corporation_class corporation_class = new Corporation_class();
    private int clickedPosition_getMaterial;

    // 声明判断AsyncTask是运行结束的接口变量
    private IsLoadDataListener loadListener_updateMaterial;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_updateMaterial (IsLoadDataListener dataComplete) {
        this.loadListener_updateMaterial = dataComplete;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_detial);

        editText_name = (EditText)findViewById(R.id.editText_name_materialDetail);
        editText_figureNumber = (EditText)findViewById(R.id.editText_figuerNumber_materialDetail);

        textView_id = (TextView)findViewById(R.id.textView_id_materialDetail);
        textView_corporation = (TextView)findViewById(R.id.textView_corporation_materialDetail);
        textView_ifDeleted = (TextView)findViewById(R.id.textView_ifDeleted_materialDetail);
        textView_createTime = (TextView)findViewById(R.id.textView_createTime_materialDetail);
        textView_updateTime = (TextView)findViewById(R.id.textView_updateTime_materialDetail);

        button_return = (Button)findViewById(R.id.button_return_materialDetail);
        button_update = (Button)findViewById(R.id.button_update_materialDetail);
        button_return.setOnClickListener(this);
        button_update.setOnClickListener(this);

        jsonString_getMaterial = getIntent().getStringExtra("jsonString_getMaterial");
        clickedPosition_getMaterial =  getIntent().getIntExtra("clickedPosition_getMaterial",0);
        arrayList_getMaterial = FastjsonTools.jsonStringParseToArrayList(jsonString_getMaterial,"$.content.data",Material_class.class);
        material_class = arrayList_getMaterial.get(clickedPosition_getMaterial);

        url_get_corporation = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Corporation/query?id="+material_class.getCorporation();
        new HTTP_GET_info().execute();




    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_return_materialDetail) {
            finish();

        } else if (i == R.id.button_update_materialDetail) {
            material_class.setName(editText_name.getText() + "");
            material_class.setFigureNumber(editText_figureNumber.getText() + "");
            new HTTP_POST_updateMaterial().execute(material_class.updateMaterialPostBody());
            setLoadDataComplete_updateMaterial(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_updateMaterial, "$.msg"), Toast.LENGTH_LONG).show();
                }
            });

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
                Log.i("MaterialDetail_Activity","jsonString_getCorporation:"+jsonString_getCorporation);
            } catch (IOException e) {
                Log.e("MaterialDetail_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList_getCorporation = FastjsonTools.jsonStringParseToArrayList(jsonString_getCorporation,"$.content.data", Corporation_class.class);

            editText_name.setText(material_class.getName());
            editText_figureNumber.setText(material_class.getFigureNumber());
            textView_id.setText(material_class.getId()+"");

            Date date_createTime = new Date((long)material_class.getCreateTime());
            SimpleDateFormat format_createTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_createTime.setText(format_createTime.format(date_createTime));

            Date date_updateTime = new Date((long)material_class.getUpdateTime());
            SimpleDateFormat format_updateTime = new SimpleDateFormat("yyyy-MM-dd");
            textView_updateTime.setText(format_updateTime.format(date_updateTime));

            textView_corporation.setText(arrayList_getCorporation.get(0).getName());
            textView_ifDeleted.setText(material_class.getIfDeleted());

        }
    }
    /**
     * AsyncTask异步任务类:HTTP_POST_deleteMaterial
     */
    private class HTTP_POST_updateMaterial extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_updateMaterial = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_UPDATE_MATERIAL, params[0]);
                Log.i("MaterialDetail_Activity","jsonString_updateMaterial:"+jsonString_updateMaterial);
            } catch (IOException e) {
                Log.e("MaterialDetail_Activity", Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_updateMaterial != null) {
                loadListener_updateMaterial.loadComplete();
            }
        }
    }

}
