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

public class SpinnerAdapter_oneColumn extends BaseAdapter {
    private Context context;
    private TextView textView;
    private String jsonString;
    private List mlist;
    private Number mlist_number;
    private String mlist_string;
    private String listKeyType_number,listKeyType_string;
    private boolean spinnerViewInvisible;

    public SpinnerAdapter_oneColumn(Context context, String jsonString, List mlist, String listKeyType_number ,String listKeyType_string ,boolean spinnerViewInvisible) {
        this.context = context;
        this.jsonString = jsonString;
        this.mlist = mlist;
        this.listKeyType_number = listKeyType_number;
        this.listKeyType_string = listKeyType_string;
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
        convertView=layoutInflater.inflate(R.layout.spinneradapt_onecolumn, null);
        if (spinnerViewInvisible){
            convertView.setVisibility(View.INVISIBLE);
            spinnerViewInvisible = false;
        }
        if(convertView!=null){
            textView=(TextView)convertView.findViewById(R.id.textView_spinnerAdapterOneColumn);
            if(jsonString.equals("NO")){
                mlist_string = (String) mlist.get(position);
            }else{
                mlist_number=(Number) JSONPath.read(jsonString,"$.content.data["+position+"]."+listKeyType_number);
                mlist_string=(String) JSONPath.read(jsonString,"$.content.data["+position+"]."+listKeyType_string);
            }
            if(listKeyType_number.equals("NO")){
                if(listKeyType_string.equals("NO")){
                    textView.setText("NONE");
                }else{
                    textView.setText(mlist_string);
                }
            }else{
                textView.setText(mlist_number+"");
            }

        }
        return convertView;
    }


}
