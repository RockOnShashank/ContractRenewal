package com.residents.dubaiassetmanagement.ServiceRequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.Category;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new.CommunityCareAdapter;

import java.util.ArrayList;

public class MaintenanceRequestAdapter extends RecyclerView.Adapter<MaintenanceRequestAdapter.ViewHolder> {

    private ArrayList<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int row_index=-1;

    // data is passed into the constructor
    MaintenanceRequestAdapter(Context context, ArrayList<Category> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_community_care, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //holder.iv_category.setImageResource(R.drawable.unselected_ac);
        if (mData.get(position).getCategoryName().length()>11){
            holder.tv_category_name1.setVisibility(View.GONE);
            holder.tv_category_name.setText(mData.get(position).getCategoryName());

        }
        else {
            holder.tv_category_name1.setVisibility(View.INVISIBLE);
            holder.tv_category_name.setText(mData.get(position).getCategoryName());

        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_category_name,tv_category_name1;
        ImageView iv_category;
        ViewHolder(View itemView) {
            super(itemView);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            tv_category_name1 =  itemView.findViewById(R.id.tv_category_name1);
            iv_category = itemView.findViewById(R.id.iv_category);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(mData.get(id));
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
