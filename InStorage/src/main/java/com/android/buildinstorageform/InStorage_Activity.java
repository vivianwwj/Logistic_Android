package com.android.buildinstorageform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InStorage_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button button_queryOrUpdateInstorageForm_instorage,button_queryOrUpdateProject_instorage,button_queryOrUpdateMaterial_instorage,button_queryOrUpdateProduct_instorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instorage);

        button_queryOrUpdateInstorageForm_instorage = (Button)findViewById(R.id.button_queryOrUpdateInstorageForm_instorage);
        button_queryOrUpdateProject_instorage =(Button)findViewById(R.id.button_queryOrUpdateProject_instorage);
        button_queryOrUpdateMaterial_instorage =(Button)findViewById(R.id.button_queryOrUpdateMaterial_instorage);
        button_queryOrUpdateProduct_instorage =(Button)findViewById(R.id.button_queryOrUpdateProduct_instorage);

        button_queryOrUpdateInstorageForm_instorage.setOnClickListener(this);
        button_queryOrUpdateProject_instorage.setOnClickListener(this);
        button_queryOrUpdateMaterial_instorage.setOnClickListener(this);
        button_queryOrUpdateProduct_instorage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_queryOrUpdateInstorageForm_instorage){
            Intent intent_queryInstorageForm_instorage = new Intent(InStorage_Activity.this, QueryInStorageForm_Activity.class);
            startActivity(intent_queryInstorageForm_instorage);

        }else if(v.getId()==R.id.button_queryOrUpdateProject_instorage){
            Intent intent_project = new Intent(InStorage_Activity.this,ProjectInfo_Activity.class);
            startActivity(intent_project);

        }else if(v.getId()==R.id.button_queryOrUpdateMaterial_instorage){
            Intent intent_material = new Intent(InStorage_Activity.this,MaterialInfo_Activity.class);
            startActivity(intent_material);

        }else if(v.getId()==R.id.button_queryOrUpdateProduct_instorage){
            Intent intent_product = new Intent(InStorage_Activity.this,ProductInfo_Activity.class);
            startActivity(intent_product);

        }


    }

}
