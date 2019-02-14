package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private ArrayList<String> categoryID;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public SeeAllAdapter(Context context, ArrayList<String> data,ArrayList<String> catId) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.categoryID=catId;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.see_all_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_category_name.setText(mData.get(position));

        holder.tv_categoryId.setText(categoryID.get(position));
        if (mData.get(position).equals("General"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.general_unselect);

        }else if (mData.get(position).equals("Lighting"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_bulb);

        }
        else if (mData.get(position).equals("Masonry"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_brick);

        }else if (mData.get(position).equals("Painting"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_paint);


        }else if (mData.get(position).equals("Plumbing"))
        {        holder.iv_category.setBackgroundResource(R.drawable.unselected_leakage);


        }else if (mData.get(position).equals("Windows"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_window);

        }


        else if (mData.get(position).equals("Air Conditioning"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_ac);

        }else if (mData.get(position).equals("Carpentry"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_hammer);

        }else if (mData.get(position).equals("Doors"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_lock);

        }else if (mData.get(position).equals("Electrical"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_plug);
        }else if (mData.get(position).equals("Fire System"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_fire);

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_category_name,tv_categoryId;
        ImageView iv_category;

        ViewHolder(View itemView) {
            super(itemView);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            iv_category  = itemView.findViewById(R.id.iv_category);
            tv_categoryId = itemView.findViewById(R.id.tv_categoryId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickSeeAll(view, getAdapterPosition(), Integer.parseInt(categoryID.get(getAdapterPosition())));


        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickSeeAll(View view, int position,int catId);
    }
}