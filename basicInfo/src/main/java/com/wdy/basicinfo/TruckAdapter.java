package com.wdy.basicinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.TruckViewHolder> {

    private ListItemClick mListItemClickListener;
    List<Truck> mTruck=new ArrayList<>();
    public TruckAdapter(List<Truck> mTruck, ListItemClick listener) {
        this.mTruck=mTruck;
        this.mListItemClickListener=listener;
    }

    @NonNull
    @Override
    public TruckAdapter.TruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_car_info,parent,false);
        TruckViewHolder viewHolder=new TruckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TruckAdapter.TruckViewHolder holder, int position) {
        holder.mName.setText(mTruck.get(position).getName());
        holder.mCarNumber.setText(mTruck.get(position).getCarNumber());
        holder.mLicense.setText(mTruck.get(position).getCarLicense());
        holder.mCarId.setText(mTruck.get(position).getCarId());
        holder.mAffiliation.setText(mTruck.get(position).getAffiliation());
    }

    @Override
    public int getItemCount() {
        return mTruck.size();
    }

    class TruckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mName,mCarNumber,mLicense,mCarId,mAffiliation;
        public TruckViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.tv_name);
            mCarNumber=itemView.findViewById(R.id.tv_packet_number);
            mLicense=itemView.findViewById(R.id.tv_packet_type);
            mCarId=itemView.findViewById(R.id.tv_status);
            mAffiliation=itemView.findViewById(R.id.tv_warehouse);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i=getAdapterPosition();
            mListItemClickListener.onListItemClick(mTruck.get(i).getId());
        }
    }
}
