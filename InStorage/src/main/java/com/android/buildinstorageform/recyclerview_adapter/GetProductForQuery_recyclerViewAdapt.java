package com.android.buildinstorageform.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.buildinstorageform.R;
import com.android.buildinstorageform.data_class.ProductForQuery_class;
import com.android.buildinstorageform.port.OnItemClickListener;

import java.util.ArrayList;

public class GetProductForQuery_recyclerViewAdapt extends RecyclerView.Adapter<GetProductForQuery_recyclerViewAdapt.Product_ViewHolder> {

    private ArrayList<ProductForQuery_class> arrayList_product;
    private OnItemClickListener mOnItemClickListener;//声明接口
    private ArrayList<Integer> arrayList_checkBoxSelected = new ArrayList<Integer>();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public GetProductForQuery_recyclerViewAdapt(ArrayList<ProductForQuery_class> arrayList_product) {
        this.arrayList_product = arrayList_product;

    }

    public ArrayList<Integer> getArrayList_checkBoxSelected(){
        return arrayList_checkBoxSelected;
    }

    @Override
    public Product_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addmaterial_recyclerview_item,parent,false);
        return new Product_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Product_ViewHolder holder, int position) {

        holder.id.setText(arrayList_product.get(position).getId()+"");
        holder.Name.setText(arrayList_product.get(position).getName());
        holder.packetNumber.setText(arrayList_product.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return arrayList_product.size();
    }

    public class Product_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id,Name,packetNumber;
        private final CheckBox checkBox_recyclerViewAdapter;
        Product_ViewHolder(View itemView) {
            super(itemView);

            checkBox_recyclerViewAdapter = (CheckBox) itemView.findViewById(R.id.checkBox_recyclerViewAdapter);
            checkBox_recyclerViewAdapter.setOnClickListener(this);

            id = (TextView)itemView.findViewById(R.id.id_recyclerViewAdapter);
            Name = (TextView)itemView.findViewById(R.id.materialName_recyclerViewAdapter);
            packetNumber = (TextView)itemView.findViewById(R.id.figureNumber_recyclerViewAdapter);

            id.setOnClickListener(this);
            Name.setOnClickListener(this);
            packetNumber.setOnClickListener(this);

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
