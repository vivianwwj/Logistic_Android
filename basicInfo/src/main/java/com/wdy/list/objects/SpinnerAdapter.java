package com.wdy.list.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wdy.basicinfo.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    List<String> mList1,mList2;
    private Context context;
    private TextView m1,m2;

    public SpinnerAdapter(List<String> mList1,List<String> mList2, Context context) {
        this.mList1 = mList1;
        this.mList2 = mList2;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return mList1.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.list_item_spinner_item2,null);
        if(view!=null){
            m1=view.findViewById(R.id.tv1);
            m2=view.findViewById(R.id.tv2);
            m1.setText(mList1.get(position));
            if(mList2.size()>0)
                m2.setText(mList2.get(position));
        }
        return view;
    }

}
