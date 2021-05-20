package com.kernal.plateid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kernal.plateid.adapter.GoodAdapter;
import com.kernal.plateid.adapter.ProjectAdapter;
import com.kernal.plateid.adapter.WarehouseAdapter;
import com.kernal.plateid.interfacee.ListItemClickListener;
import com.kernal.plateid.javabean.FastJsonReturn;
import com.kernal.plateid.my.MySingleton;
import com.kernal.plateid.objects.Good;
import com.kernal.plateid.my.MyData;
import com.kernal.plateid.objects.Project;
import com.kernal.plateid.objects.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class GoodsInWarehouseActivity extends AppCompatActivity implements ListItemClickListener {
    private final static String TAG=GoodsInWarehouseActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Good> goods;
    private Button mOk;
    private Spinner mProject;
    private List<Project> listProject;
    private int projectId;
    ProjectAdapter projectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_in_store);
        getAllView();

        //获取project列表
        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
        StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//s为请求返回的字符串数据
                        Log.d(TAG,s);
                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                        JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                        listProject=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Project>>() {});
                        projectAdapter=new ProjectAdapter(listProject,GoodsInWarehouseActivity.this);
                        mProject.setAdapter(projectAdapter);
                        mProject.setSelection((int)projectAdapter.getItemId(projectId));
                        mProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                projectId=projectAdapter.getObjectId(i);
                                // get goodsList from server
                                String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Product/query?project="+projectId;
                                StringRequest requestProject = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {//s为请求返回的字符串数据
                                                Log.d(TAG,s);
                                                FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                                JSONArray jsonArray=fastJsonReturn.getContent().getJSONArray("data");
                                                goods=JSON.parseObject(JSON.toJSONString(jsonArray),new TypeReference<ArrayList<Good>>() {});
                                                //TODO 这个for是用来本地筛选的
                                                for(int i=0;i<goods.size();i++){
                                                    if(goods.get(i).getProject()!=projectId){
                                                        goods.remove(i--);
                                                    }
                                                }
                                                //TODO 这个for是用来本地筛选的，添加读取status的筛选功能，筛去已经出库的货物
                                                for(int i=0;i<goods.size();i++){
                                                    if(goods.get(i).getStatus().equals("已出库")){
                                                        goods.remove(i--);
                                                    }
                                                }
                                                mLayoutManager=new LinearLayoutManager(GoodsInWarehouseActivity.this);
                                                mRecyclerView.setLayoutManager(mLayoutManager);
                                                mAdapter=new GoodAdapter(goods,GoodsInWarehouseActivity.this);
                                                mRecyclerView.setAdapter(mAdapter);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Log.e(TAG,volleyError.toString());
                                            }
                                        }){};
                                MySingleton.getInstance(GoodsInWarehouseActivity.this).addToRequestQueue(requestProject);
                                // END: get goodsList from server
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {}
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.toString());
                    }
                }){};
        MySingleton.getInstance(this).addToRequestQueue(requestProject);
        //END：获取project列表

    }

    private void getAllView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_goods_in_store);
        mOk=(Button)findViewById(R.id.btn_ok);
        mProject=(Spinner)findViewById(R.id.sp_project);

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Good> goodsOut=new ArrayList<>();
                boolean[] mIsChecked=mAdapter.getChecked();
                for(int i=0;i<mAdapter.getItemCount();i++){
                    if(mIsChecked[i]){
                        goodsOut.add(mAdapter.getmGoods().get(i));
                    }
                }
                if(!goodsOut.isEmpty()){
                    MyData.setGoodsOut(goodsOut);
                    Intent intent=new Intent(GoodsInWarehouseActivity.this,DistributionActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    @Override
    public void onListItemClick(int position) {

    }
}
