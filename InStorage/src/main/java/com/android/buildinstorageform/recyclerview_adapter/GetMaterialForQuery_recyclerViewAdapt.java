package com.android.buildinstorageform.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.buildinstorageform.R;
import com.android.buildinstorageform.data_class.Material_class;
import com.android.buildinstorageform.port.OnItemClickListener;

import java.util.ArrayList;

public class GetMaterialForQuery_recyclerViewAdapt extends RecyclerView.Adapter<GetMaterialForQuery_recyclerViewAdapt.AddMaterialToProject_ViewHolder> {

    private ArrayList<Material_class> arrayList_material;
    private OnItemClickListener mOnItemClickListener;//声明接口
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public GetMaterialForQuery_recyclerViewAdapt(ArrayList<Material_class> arrayList_material) {
        this.arrayList_material = arrayList_material;
    }

    public ArrayList<Integer> getArrayList_checkBoxSelected(){
        return arrayList_checkBoxSelected;
    }

    @Override
    public AddMaterialToProject_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addmaterial_recyclerview_item,parent,false);
        return new AddMaterialToProject_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddMaterialToProject_ViewHolder holder, int position) {

        holder.id_recyclerViewAdapter.setText(arrayList_material.get(position).getId()+"");
        holder.materialName_recyclerViewAdapter.setText(arrayList_material.get(position).getName());
        holder.figureNumber_recyclerViewAdapter.setText(arrayList_material.get(position).getFigureNumber()+"");



    }

    @Override
    public int getItemCount() {
        return arrayList_material.size();
    }

    public class AddMaterialToProject_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id_recyclerViewAdapter,materialName_recyclerViewAdapter,figureNumber_recyclerViewAdapter;
        private final CheckBox checkBox_recyclerViewAdapter;

        AddMaterialToProject_ViewHolder(View itemView) {
            super(itemView);

            checkBox_recyclerViewAdapter = (CheckBox) itemView.findViewById(R.id.checkBox_recyclerViewAdapter);
            checkBox_recyclerViewAdapter.setOnClickListener(this);

            id_recyclerViewAdapter = (TextView)itemView.findViewById(R.id.id_recyclerViewAdapter);
            materialName_recyclerViewAdapter = (TextView)itemView.findViewById(R.id.materialName_recyclerViewAdapter);
            figureNumber_recyclerViewAdapter = (TextView)itemView.findViewById(R.id.figureNumber_recyclerViewAdapter);

            id_recyclerViewAdapter.setOnClickListener(this);
            materialName_recyclerViewAdapter.setOnClickListener(this);
            figureNumber_recyclerViewAdapter.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            int i = view.getId();
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
