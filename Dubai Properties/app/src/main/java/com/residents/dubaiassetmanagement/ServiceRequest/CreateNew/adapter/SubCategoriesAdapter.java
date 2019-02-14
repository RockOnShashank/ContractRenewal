package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.SubCategory;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

    private List<SubCategory> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int row_index=-1;

    // data is passed into the constructor
    public SubCategoriesAdapter(Context context, List<SubCategory> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_subcategories, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFullSpan(false);


        holder.tv_subCategory.setText(mData.get(position).getSubCategoryName());
        holder.tv_subCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClickSecond(v,position);
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){

            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#000000"));
            holder.tv_subCategory.setTextColor(Color.parseColor("#ffffff"));

        }
        else
        {
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv_subCategory.setTextColor(Color.parseColor("#585d63"));



        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subCategory;
        private ConstraintLayout row_linearlayout;

        ViewHolder(View itemView) {
            super(itemView);
            tv_subCategory = itemView.findViewById(R.id.tv_subCategory);
            row_linearlayout= itemView.findViewById(R.id.row_linearlayout);

              itemView.setOnClickListener(this);
              row_linearlayout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      mClickListener.onItemClickSecond(view,getAdapterPosition());
                  }
              });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickSecond(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
 /*   String getItem(int id) {
        return mData.get();
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickSecond(View view, int position);
    }
}