package com.kernal.plateid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kernal.plateid.R;
import com.kernal.plateid.objects.Warehouse;

import java.util.List;

public class WarehouseAdapter extends BaseAdapter {
    List<Warehouse> mList;
    private Context context;
    private TextView mName, mAddress;

    public WarehouseAdapter(List<Warehouse> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }
    public int getObjectId(int position){
        return mList.get(position).getId();
    }
    @Override
    public long getItemId(int id) {
        for(int i=0;i<mList.size();i++){
            if(mList.get(i).getId()==id){
                return i;
            }
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.warehouse_list_item,null);
        if(view!=null){
            mName=(TextView)view.findViewById(R.id.tv_name);
            mAddress =(TextView)view.findViewById(R.id.tv_address);
            mName.setText(mList.get(position).getName());
            mAddress.setText(mList.get(position).getAddress());
        }
        return view;
    }
}
