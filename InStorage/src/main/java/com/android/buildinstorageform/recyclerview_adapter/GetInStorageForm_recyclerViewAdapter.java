package com.android.buildinstorageform.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.buildinstorageform.R;
import com.android.buildinstorageform.data_class.InStorageFormForQuery_class;
import com.android.buildinstorageform.port.OnItemClickListener;

import java.util.ArrayList;

public class GetInStorageForm_recyclerViewAdapter extends RecyclerView.Adapter<GetInStorageForm_recyclerViewAdapter.InStorageForm_ViewHolder> {
    private ArrayList<InStorageFormForQuery_class> mInStorageForm;
    private OnItemClickListener mOnItemClickListener;//声明接口
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();

    public GetInStorageForm_recyclerViewAdapter(ArrayList<InStorageFormForQuery_class> inStorageForm){
        mInStorageForm=inStorageForm;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ArrayList<Integer> getArrayList_checkBoxSelected(){
        return arrayList_checkBoxSelected;
    }

    @Override
    public InStorageForm_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addmaterial_recyclerview_item,parent,false);
        return new InStorageForm_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InStorageForm_ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(mInStorageForm.get(position).getId()));
        holder.inStorageNumber.setText(mInStorageForm.get(position).getInStorageNumber());
        holder.inStorageStatus.setText(mInStorageForm.get(position).getInStorageStatus());

    }

    @Override
    public int getItemCount() {
        return mInStorageForm.size();
    }

    class InStorageForm_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView id,inStorageNumber,inStorageStatus;
        private final CheckBox checkBox_recyclerViewAdapter;

        InStorageForm_ViewHolder(View itemView){
            super(itemView);

            checkBox_recyclerViewAdapter = (CheckBox) itemView.findViewById(R.id.checkBox_recyclerViewAdapter);
            checkBox_recyclerViewAdapter.setOnClickListener(this);

            id=(TextView)itemView.findViewById(R.id.id_recyclerViewAdapter);
            inStorageNumber=(TextView)itemView.findViewById(R.id.materialName_recyclerViewAdapter);
            inStorageStatus=(TextView)itemView.findViewById(R.id.figureNumber_recyclerViewAdapter);
            id.setOnClickListener(this);
            inStorageNumber.setOnClickListener(this);
            inStorageStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.checkBox_recyclerViewAdapter) {
                if (arrayList_checkBoxSelected.contains(getLayoutPosition())) {
                    arrayList_checkBoxSelected.remove(arrayList_checkBoxSelected.indexOf(getLayoutPosition()));
                } else if (checkBox_recyclerViewAdapter.isChecked()) {
                    arrayList_checkBoxSelected.add(getLayoutPosition());
                }

            } else {
                mOnItemClickListener.onItemClick(itemView, getLayoutPosition());

            }





        }
    }
}
