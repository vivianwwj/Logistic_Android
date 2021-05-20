package com.wdy.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wdy.basicinfo.R;
import com.wdy.contract.ContractActivity;

public class Basic2Activity extends AppCompatActivity {
    Button mCar,mProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        mCar=findViewById(R.id.btn_car_info);
        mProduct=findViewById(R.id.btn_product_info);
        mCar.setText("随车清单");
        mProduct.setText("运输合同");
        mCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Basic2Activity.this, ListOnTruckActivity.class);
                startActivity(intent);
            }
        });
        mProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Basic2Activity.this, ContractActivity.class);
                startActivity(intent);
            }
        });
    }
}
