package com.android.buildinstorageform.spinner_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONPath;
import com.android.buildinstorageform.R;

import java.util.List;

public class SpinnerAdapter_twoColumns extends BaseAdapter {
    private Context context;
    private TextView id,name;
    private String jsonString;
    private List mlist;
    private Number mlist_id;
    private String mlist_name;
    private String No_1,No_2;
    private boolean spinnerViewInvisible;

    /**
     *
     * @param context
     * @param jsonString
     * @param mlist
     * @param No_1
     * @param No_2
     * @param spinnerViewInvisible 设置spinner初始是否可见
     */
    public SpinnerAdapter_twoColumns(Context context, String jsonString, List mlist, String No_1 , String No_2 ,boolean spinnerViewInvisible) {
        this.context = context;
        this.jsonString = jsonString;
        this.mlist = mlist;
        this.No_1 = No_1;
        this.No_2 = No_2;
        this.spinnerViewInvisible = spinnerViewInvisible;
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.spinneradapt_twocolumns, null);
        if (spinnerViewInvisible){
            convertView.setVisibility(View.INVISIBLE);
            spinnerViewInvisible = false;
        }
        if(convertView!=null){
            id=(TextView)convertView.findViewById(R.id.id_corporationAdapter);
            name =(TextView)convertView.findViewById(R.id.name_corporationAdapter);
            mlist_id=(Number) JSONPath.read(jsonString,"$.content.data["+position+"]."+No_1);
            mlist_name = (String)JSONPath.read(jsonString,"$.content.data["+position+"]."+No_2);
            id.setText(mlist_id+"");
            name.setText(mlist_name);
        }
        return convertView;
    }


}
