package com.wdy.contract;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wdy.basicinfo.ListItemClick;
import com.wdy.basicinfo.R;

import java.util.ArrayList;
import java.util.List;


public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ListOnTruckViewHolder> {

    private ListItemClick mListItemClickListener;
    List<Contract> listContract =new ArrayList<>();
    public ContractAdapter(List<Contract> listContract, ListItemClick listener) {
        this.listContract = listContract;
        this.mListItemClickListener=listener;
    }

    @NonNull
    @Override
    public ListOnTruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_car_info,parent,false);
        ListOnTruckViewHolder viewHolder=new ListOnTruckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOnTruckViewHolder holder, int position) {
        holder.mName.setText(listContract.get(position).getContractNumber());
        holder.mCarNumber.setText(listContract.get(position).getProject()+"");
        holder.mLicense.setText(listContract.get(position).getOutStorageForm()+"");
        holder.mCarId.setText(listContract.get(position).getIfCompleted());
        holder.mAffiliation.setText("");
    }

    @Override
    public int getItemCount() {
        return listContract.size();
    }

    class ListOnTruckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mName,mCarNumber,mLicense,mCarId,mAffiliation;
        public ListOnTruckViewHolder(@NonNull View itemView) {
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
            mListItemClickListener.onListItemClick(i);
        }
    }
}