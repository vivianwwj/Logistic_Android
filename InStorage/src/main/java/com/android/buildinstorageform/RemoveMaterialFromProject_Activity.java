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
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.data_class.Project_class;
import com.android.buildinstorageform.fastjsontools.FastjsonTools;
import com.android.buildinstorageform.port.IsLoadDataListener;
import com.android.buildinstorageform.port.OnItemClickListener;
import com.android.buildinstorageform.recyclerview_adapter.GetMaterial_recyclerViewAdapt;
import com.android.buildinstorageform.spinner_adapter.SpinnerAdapter_twoColumns;
import com.android.buildinstorageform.utilies.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RemoveMaterialFromProject_Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spinner_selectProject;
    private Button button_removeMaterial,button_addMaterial,button_complete;
    private RecyclerView recyclerView_materialInProject,recyclerView_material;
    private GetMaterial_recyclerViewAdapt recyclerViewAdapt_materialInProject,recyclerViewAdapt_material;

    private ArrayList<Material_class> arrayList_getMaterial = new ArrayList<Material_class>();
    private ArrayList<Material_class> arrayList_getMaterialAccordingToProject = new ArrayList<Material_class>();
    private ArrayList<Project_class> arrayList_getProject = new ArrayList<Project_class>();
    private ArrayList<Integer> arrayList_getMaterialInProjectId = new ArrayList<Integer>();
    private ArrayList<Integer> arrayList_checkBoxSelected_materialInProject = new ArrayList<Integer>();
    private ArrayList<Integer> arrayList_checkBoxSelected_material = new ArrayList<Integer>();
    private Project_class project_class = new Project_class();
    private String jsonString_getMaterial,jsonString_getProject,jsonString_removeMaterial,jsonString_addMaterial;
    private Boolean boolean_firstTime = true;
    private int int_projectSelectedPosition = 0;

    private static final String URL_GETPROJECT = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/query";
    private static final String URL_GETMATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Material/query";
    private static final String URL_REMOVEMATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/removeMaterial";
    private static final String URL_POST_ADDMATERIAL = "http://47.111.122.217:8888/ScsyERP-web-Boss/BasicInfo/Project/addMaterial";

    private IsLoadDataListener loadListener_removeMaterial;
    private IsLoadDataListener loadListener_getMaterialFromProject;
    private IsLoadDataListener loadListener_addMaterial_addMaterialToProject;

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_addMaterial_AddMaterialToProject (IsLoadDataListener dataComplete) {
        this.loadListener_addMaterial_addMaterialToProject = dataComplete;
    }
    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_removeMaterial (IsLoadDataListener dataComplete) {
        this.loadListener_removeMaterial = dataComplete;
    }

    // 给接口赋值，得到接口对象
    public void setLoadDataComplete_getMaterialFromProject (IsLoadDataListener dataComplete) {
        this.loadListener_getMaterialFromProject = dataComplete;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removematerialfromproject);

        spinner_selectProject = (Spinner)findViewById(R.id.spinner_selectProject_removeMaterialFromProject);
        spinner_selectProject.setOnItemSelectedListener(this);

        button_removeMaterial = (Button)findViewById(R.id.button_removeMaterial_removeMaterialFromProject);
        button_addMaterial = (Button)findViewById(R.id.button_addMaterial_removeMaterialFromProject);
        button_complete = (Button)findViewById(R.id.button_complete_removeMaterialFromProject);
        button_removeMaterial.setOnClickListener(this);
        button_addMaterial.setOnClickListener(this);
        button_complete.setOnClickListener(this);

        recyclerView_materialInProject = (RecyclerView)findViewById(R.id.recyclerView_materialInProject_removeMaterialFromProject);
        recyclerView_material = (RecyclerView)findViewById(R.id.recyclerView_material_removeMaterialFromProject);


        new GetMaterialFromProject().execute(URL_GETPROJECT);

    }

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.button_complete_removeMaterialFromProject) {
            finish();

        } else if (i1 == R.id.button_addMaterial_removeMaterialFromProject) {
            arrayList_checkBoxSelected_material = recyclerViewAdapt_material.getArrayList_checkBoxSelected();
//            Log.i("RemoveMaterialFromProject_Activity", "arrayList_checkBoxSelected_material:" + arrayList_checkBoxSelected_material.toString());

            for (int i = 0; i < arrayList_checkBoxSelected_material.size(); i++) {
                String postParams_addMaterialToProject = "project=" + project_class.getId() + "&material=" + arrayList_getMaterial.get(arrayList_checkBoxSelected_material.get(i)).getId();
                new AddMaterial().execute(postParams_addMaterialToProject);
                setLoadDataComplete_addMaterial_AddMaterialToProject(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_addMaterial, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }

            new GetMaterialFromProject().execute(URL_GETPROJECT);
            setLoadDataComplete_getMaterialFromProject(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    showRecyclerViewFromProjectSelected(int_projectSelectedPosition);
                }
            });


        } else if (i1 == R.id.button_removeMaterial_removeMaterialFromProject) {
//            Log.i("RemoveMaterialFromProject_Activity", "——————————————————————————————————————————");

            arrayList_checkBoxSelected_materialInProject = recyclerViewAdapt_materialInProject.getArrayList_checkBoxSelected();//获取被选中的checkbox的指针数组
//            Log.i("RemoveMaterialFromProject_Activity", "arrayList_checkBoxSelected_materialInProject:" + arrayList_checkBoxSelected_materialInProject.toString());

            int count_removeMaterial;
            //遍历提交被选中的物料信息给服务器，以执行移除物料操作
            for (count_removeMaterial = 0; count_removeMaterial < arrayList_checkBoxSelected_materialInProject.size(); count_removeMaterial++) {
                String postParams_removeMaterial = "project=" + project_class.getId() + "&material=" + arrayList_getMaterialAccordingToProject.get(arrayList_checkBoxSelected_materialInProject.get(count_removeMaterial)).getId();
//                Log.i("RemoveMaterialFromProject_Activity", "postParams_removeMaterial:" + postParams_removeMaterial);
                new RemoveMaterial().execute(postParams_removeMaterial);
                setLoadDataComplete_removeMaterial(new IsLoadDataListener() {
                    @Override
                    public void loadComplete() {
                        Toast.makeText(getApplicationContext(), "" + JSONPath.read(jsonString_removeMaterial, "$.msg"), Toast.LENGTH_LONG).show();
                    }
                });
            }

            new GetMaterialFromProject().execute(URL_GETPROJECT);
            setLoadDataComplete_getMaterialFromProject(new IsLoadDataListener() {
                @Override
                public void loadComplete() {
                    showRecyclerViewFromProjectSelected(int_projectSelectedPosition);
                }
            });

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i = parent.getId();
        if (i == R.id.spinner_selectProject_removeMaterialFromProject) {
            project_class.setId(arrayList_getProject.get(position).getId());
            int_projectSelectedPosition = position;
            showRecyclerViewFromProjectSelected(int_projectSelectedPosition);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 根据spinner_selectProject选中的工程，显示该工程中的物料信息
     * @param projectSelectedPosition spinner_selectProject的指针位置
     */
    @SuppressLint("LongLogTag")
    public void showRecyclerViewFromProjectSelected(int projectSelectedPosition){
        arrayList_getMaterialInProjectId = arrayList_getProject.get(projectSelectedPosition).getMaterials();
        Log.i("RemoveMaterialFromProject_Activity", "arrayList_getMaterialInProjectId:"+arrayList_getMaterialInProjectId.toString());

        int count_MaterialsId,count_Material;
        arrayList_getMaterialAccordingToProject.clear();
        for(count_MaterialsId = 0;count_MaterialsId < arrayList_getMaterialInProjectId.size();count_MaterialsId++){
            for(count_Material = 0;count_Material < arrayList_getMaterial.size();count_Material++){
                Log.i("RemoveMaterialFromProject_Activity", "arrayList_getMaterial.getId  vs  arrayList_getMaterialsId:"+arrayList_getMaterial.get(count_Material).getId()+"vs"+arrayList_getMaterialInProjectId.get(count_MaterialsId));
                if(arrayList_getMaterial.get(count_Material).getId().equals(arrayList_getMaterialInProjectId.get(count_MaterialsId))){
                    arrayList_getMaterialAccordingToProject.add(arrayList_getMaterial.get(count_Material));
                    break;
                }
            }
        }
        Log.i("RemoveMaterialFromProject_Activity", "arrayList_getMaterialAccordingToProject(onItemSelected):"+arrayList_getMaterialAccordingToProject.toString());

        RecyclerView.LayoutManager recyclerViewLayoutManager_getMaterialInProject = new LinearLayoutManager(RemoveMaterialFromProject_Activity.this);
        recyclerView_materialInProject.setLayoutManager(recyclerViewLayoutManager_getMaterialInProject);
        recyclerViewAdapt_materialInProject= new GetMaterial_recyclerViewAdapt(arrayList_getMaterialAccordingToProject);
        recyclerView_materialInProject.setAdapter(recyclerViewAdapt_materialInProject);
        recyclerViewAdapt_materialInProject.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });

        RecyclerView.LayoutManager recyclerViewLayoutManager_getMaterial = new LinearLayoutManager(RemoveMaterialFromProject_Activity.this);
        recyclerView_material.setLayoutManager(recyclerViewLayoutManager_getMaterial);
        recyclerViewAdapt_material= new GetMaterial_recyclerViewAdapt(arrayList_getMaterial);
        recyclerView_material.setAdapter(recyclerViewAdapt_material);
        recyclerViewAdapt_material.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });
    }

    /**
     * AsyncTask异步任务类:GetMaterial
     */
    public class GetMaterialFromProject extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {
            try {
                jsonString_getProject = NetworkUtils.getResponseFromHttpUrl_GET(new URL(params[0]));
                jsonString_getMaterial = NetworkUtils.getResponseFromHttpUrl_GET(new URL(URL_GETMATERIAL));
                Log.i("RemoveMaterialFromProject_Activity","jsonString_getMaterial:"+jsonString_getMaterial);
            } catch (IOException e) {
                Log.e("RemoveMaterialFromProject_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            arrayList_getProject = FastjsonTools.jsonStringParseToArrayList(jsonString_getProject,"$.content.data", Project_class.class);
            Log.i("RemoveMaterialFromProject_Activity", "arrayList_getProject:"+arrayList_getProject.toString());

            arrayList_getMaterial = FastjsonTools.jsonStringParseToArrayList(jsonString_getMaterial,"$.content.data",Material_class.class);
            Log.i("RemoveMaterialFromProject_Activity", "arrayList_getMaterial:"+arrayList_getMaterial.toString());

            if(boolean_firstTime){
                SpinnerAdapter_twoColumns spinnerAdapter_getProject = new SpinnerAdapter_twoColumns(RemoveMaterialFromProject_Activity.this,jsonString_getProject,arrayList_getProject,"id","name",false);
                spinner_selectProject.setAdapter(spinnerAdapter_getProject);
                boolean_firstTime = false;
            }
            if (loadListener_getMaterialFromProject != null) {
                loadListener_getMaterialFromProject.loadComplete();
            }

        }
    }

    /**
     * AsyncTask异步任务类:AddMaterial
     */
    public class AddMaterial extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {

            try {
                jsonString_addMaterial = NetworkUtils.getResponseFromHttpUrl_POST(URL_POST_ADDMATERIAL,params[0]);
                Log.i("RemoveMaterialFromProject_Activity","jsonString_addMaterial:"+jsonString_addMaterial);
            } catch (IOException e) {
                Log.e("RemoveMaterialFromProject_Activity",Log.getStackTraceString(e));
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_addMaterial_addMaterialToProject != null) {
                loadListener_addMaterial_addMaterialToProject.loadComplete();
            }
        }
    }

    /**
     * AsyncTask异步任务类:RemoveMaterial
     */
    public class RemoveMaterial extends AsyncTask<String,Void,String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... params) {

            try {
                jsonString_removeMaterial = NetworkUtils.getResponseFromHttpUrl_POST(URL_REMOVEMATERIAL,params[0]);
                Log.i("RemoveMaterialFromProject_Activity","jsonString_removeMaterial:"+jsonString_removeMaterial);
            } catch (IOException e) {
                Log.e("RemoveMaterialFromProject_Activity",Log.getStackTraceString(e));
            }
            return jsonString_removeMaterial;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (loadListener_removeMaterial != null) {
                loadListener_removeMaterial.loadComplete();
            }
        }
    }
}
