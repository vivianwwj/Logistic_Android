package com.kernal.plateid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kernal.plateid.adapter.GoodDisAdapter;
import com.kernal.plateid.interfacee.ListItemClickListener;
import com.kernal.plateid.objects.Good;
import com.kernal.plateid.my.MyData;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements ListItemClickListener {
    private final static String TAG=DetailActivity.class.getSimpleName();
    private ArrayList<Good> goodsDetail;
    private Button mFinish;
    private RecyclerView mRecyclerView;
    private GoodDisAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        getAllView();
        goodsDetail= MyData.getGoodsDetail();

        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new GoodDisAdapter(goodsDetail,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getAllView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_detail);
        mFinish=(Button)findViewById(R.id.btn_back);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onListItemClick(final int position) {    }
}
