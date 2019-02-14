package com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.Category;

import java.util.ArrayList;

public class CommunityCareAdapter extends RecyclerView.Adapter<CommunityCareAdapter.ViewHolder> {

    private ArrayList<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int row_index=-1;

    // data is passed into the constructor
    CommunityCareAdapter(Context context, ArrayList<Category> data) {
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //holder.iv_category.setImageResource(R.drawable.unselected_ac);
        if (mData.get(position).getCategoryName().length()>11){
            holder.tv_category_name1.setVisibility(View.GONE);
            holder.tv_category_name.setText(mData.get(position).getCategoryName());

        }
else {
            holder.tv_category_name1.setVisibility(View.INVISIBLE);
            holder.tv_category_name.setText(mData.get(position).getCategoryName());

        }







        if (position==0){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Emergency")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.emergency_unselect);
                }
            }
        }
        if (position==1){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Suggestion")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.suggestion_unselect);
                }
            }
        }
        if (position==2){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Community Maintenance")){
                    holder.tv_category_name1.setVisibility(View.GONE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.maintenance_unselect);
                }
            }
        }
        if (position==3){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Complaint")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.complaint_unselect);
                }
            }
        }


        if(row_index==position){



            if (position==0){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Emergency")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.emergency_select);
                    }
                }
            }
            if (position==1){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Suggestion")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.ic_suggestion_select);
                    }
                }
            }
            if (position==2){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Community Maintenance")){
                        holder.tv_category_name1.setVisibility(View.GONE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.maintenance_select);
                    }
                }
            }
            if (position==3){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Complaint")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.complaint_select);
                    }
                }
            }
        }
        else
        {


            if (position==0){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Emergency")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.emergency_unselect);
                    }
                }
            }
            if (position==1){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Suggestion")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.suggestion_unselect);
                    }
                }
            }
            if (position==2){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Community Maintenance")){
                        holder.tv_category_name1.setVisibility(View.GONE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.maintenance_unselect);
                    }
                }
            }
            if (position==3){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Complaint")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.complaint_unselect);
                    }
                }
            }        }
        holder.iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,position);
                row_index=position;
                notifyDataSetChanged();
                if (position==0 ){
                    holder.iv_category.setImageResource(R.drawable.emergency_select);
                }else {

                }

                if (position==1){
                    holder.iv_category.setImageResource(R.drawable.complaint_select);

                }else {

                }
                if (position==2){
                    holder.iv_category.setImageResource(R.drawable.maintenance_select);

                }else {

                }
                if (position==3){

                    holder.iv_category.setImageResource(R.drawable.ic_suggestion_select);

                }else {

                }


            }
        });


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
            if (getAdapterPosition()==0 ){
                iv_category.setImageResource(R.drawable.emergency_select);
            }else {

            }

            if (getAdapterPosition()==1){
                iv_category.setImageResource(R.drawable.ic_suggestion_select);

            }else {

            }
            if (getAdapterPosition()==2){
                iv_category.setImageResource(R.drawable.maintenance_select);

            }else {

            }
            if (getAdapterPosition()==3){

                iv_category.setImageResource(R.drawable.complaint_select);

            }else {

            }

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
