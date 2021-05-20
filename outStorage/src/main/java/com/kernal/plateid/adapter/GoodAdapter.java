package com.kernal.plateid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kernal.plateid.R;
import com.kernal.plateid.interfacee.ListItemClickListener;
import com.kernal.plateid.objects.Good;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.GoodViewHolder> {
    private List<Good> mGoods;
    private ListItemClickListener listItemClickListener;
    private boolean[] mIsChecked;
    public GoodAdapter(List<Good> goods, ListItemClickListener listener){
        mGoods=goods;
        mIsChecked =new boolean[mGoods.size()];
        for(boolean i: mIsChecked){
            i=false;
        }
        listItemClickListener=listener;
    }
    public boolean[] getChecked(){
        return mIsChecked;
    }

    public List<Good> getmGoods() {
        return mGoods;
    }

    @Override
    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goods_in_store_list_item,parent,false);
        GoodViewHolder viewHolder=new GoodViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GoodViewHolder holder, int position) {
        holder.mName.setText(mGoods.get(position).getName());
        holder.mWarehouse.setText("仓库"+mGoods.get(position).getWarehouse());
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    class GoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox checkBox;
        private TextView mName,mWarehouse;
        public GoodViewHolder(View itemView){
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.cb_item);
            mName=(TextView)itemView.findViewById(R.id.tv_name);
            mWarehouse=(TextView)itemView.findViewById(R.id.tv_warehouse);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            checkBox.setChecked(!checkBox.isChecked());
            mIsChecked[getAdapterPosition()]=checkBox.isChecked();
        }
        public boolean isChecked(){
            return checkBox.isChecked();
        }
    }
}
