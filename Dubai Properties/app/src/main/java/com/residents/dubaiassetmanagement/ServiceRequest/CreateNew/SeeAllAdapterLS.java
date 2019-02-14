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

public class SeeAllAdapterLS extends RecyclerView.Adapter<SeeAllAdapterLS.ViewHolder> {

    private ArrayList<String> mData;
    private ArrayList<String> categoryID;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public SeeAllAdapterLS(Context context, ArrayList<String> data, ArrayList<String> catId) {
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

        /*new code*/
        if (position==0){
            holder.tv_category_name.setText(mData.get(0));
            holder.tv_categoryId.setText(categoryID.get(0));
            holder.iv_category.setImageResource(R.drawable.renewal_unselected);
        }
        if (position==1){
            holder.tv_category_name.setText(mData.get(1));
            holder.tv_categoryId.setText(categoryID.get(1));
            holder.iv_category.setImageResource(R.drawable.termination_unselect);
        }
        if (position==2){
            holder.tv_category_name.setText(mData.get(2));
            holder.tv_categoryId.setText(categoryID.get(2));
            holder.iv_category.setImageResource(R.drawable.change_unselect);
        }
        if (position==3){
            holder.tv_category_name.setText(mData.get(3));
            holder.tv_categoryId.setText(categoryID.get(3));
            holder.iv_category.setImageResource(R.drawable.transfer_unselect);
        }

        if (position==4){
            holder.tv_category_name.setText(mData.get(4));
            holder.tv_categoryId.setText(categoryID.get(4));
            holder.iv_category.setImageResource(R.drawable.payment_related_unselect);
        }
        if (position == 5) {
            holder.tv_category_name.setText(mData.get(5));
            holder.tv_categoryId.setText(categoryID.get(5));
            holder.iv_category.setImageResource(R.drawable.new_home_unselected);
        }


        /**/



/*
        if (mData.get(position).equals("General"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_window);

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


        else if (mData.get(position).equals("Air Conditioning"))
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
        }else if (mData.get(position).equals("Fire System"))
        {
            holder.iv_category.setBackgroundResource(R.drawable.unselected_fire);

        }*/

        //new Code
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