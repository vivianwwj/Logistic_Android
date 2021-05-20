package com.kernal.plateid;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kernal.plateid.adapter.GoodDisAdapter;
import com.kernal.plateid.adapter.TruckAdapter;
import com.kernal.plateid.interfacee.ListItemClickListener;
import com.kernal.plateid.javabean.FastJsonReturn;
import com.kernal.plateid.my.MySingleton;
import com.kernal.plateid.objects.Good;
import com.kernal.plateid.my.MyData;
import com.kernal.plateid.objects.Truck;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributionActivity extends AppCompatActivity implements ListItemClickListener {
    private final static String TAG=DistributionActivity.class.getSimpleName();
    private ArrayList<Good> goodsOut;
    private Button mFinish;
    private RecyclerView mRecyclerView;
    private GoodDisAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int outStorageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribution_form);
        getAllView();
        goodsOut=MyData.getGoodsOut();
        SharedPreferences sharedPreferences=getSharedPreferences("zju",MODE_PRIVATE);
        outStorageId=sharedPreferences.getInt("outStorageId",1);

        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new GoodDisAdapter(goodsOut,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getAllView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_goods_distributed);
        mFinish=(Button)findViewById(R.id.btn_finish);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdapter.getItemCount()>0){
                    Toast.makeText(DistributionActivity.this,"请将所有货物出库后再完成出库单",Toast.LENGTH_SHORT).show();
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    public void onListItemClick(final int position) {
        String name=mAdapter.getName(position);
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(DistributionActivity.this);
        builder.setTitle("货物出库");
        builder.setMessage("你确定"+ name +"已经出库？\n确定后将删除该货物");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo use api to add detail
                        final Good rmGood=mAdapter.remove(position);
                        //使用post 添加出库单明细
                        String url = "http://47.111.122.217:8888/ScsyERP-web-Boss/OutStorageForm/addProduct";
                        StringRequest requestProject = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        Log.d(TAG,s);
                                        FastJsonReturn fastJsonReturn = JSON.parseObject(s, FastJsonReturn.class);
                                        int status=fastJsonReturn.getStatus();
                                        if(status!=0){
                                            Toast.makeText(DistributionActivity.this,"\nadd product failed!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG,error.toString());
                            }
                        }) {
                            // 携带参数
                            @Override
                            protected HashMap<String, String> getParams()
                                    throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("outStorageForm", ""+outStorageId);
                                hashMap.put("product", ""+rmGood.getId());
                                Log.d(TAG,hashMap.toString());
                                return hashMap;
                            }
                        };
                        MySingleton.getInstance(DistributionActivity.this).addToRequestQueue(requestProject);
                        //END：使用post 添加出库单明细

                    }
                });
        builder.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        builder.show();
    }
}