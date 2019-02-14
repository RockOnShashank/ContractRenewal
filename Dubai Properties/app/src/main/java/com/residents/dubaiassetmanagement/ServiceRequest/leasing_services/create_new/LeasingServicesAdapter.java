package com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.create_new;

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
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new.CommunityCareAdapter;

import java.util.ArrayList;

public class LeasingServicesAdapter extends RecyclerView.Adapter<LeasingServicesAdapter.ViewHolder> {

    private ArrayList<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int row_index=-1;
    private String selectedName;
    int num = 1;
    public static int t=0;
    private int v=0;

    // data is passed into the constructor
    LeasingServicesAdapter(Context context, ArrayList<Category> data,String name) {
        this.mInflater = LayoutInflater.from(context);
        this.selectedName = name;
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public LeasingServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_leasing_services, parent, false);
        return new LeasingServicesAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //holder.iv_category.setImageResource(R.drawable.unselected_ac);



                if (mData.get(position).getCategoryName().length() > 11) {
                    holder.tv_category_name1.setVisibility(View.GONE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.unselected_ac);

                } else {
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.unselected_ac);
            }




        holder.iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,position);
                row_index=position;
                notifyDataSetChanged();
            }
        });




        if (position==0){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Renewal Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.renewal_unselected);
                }
            }
        }
        if (position==0 && !selectedName.equalsIgnoreCase("")){
                if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.renewal_selected);
                }
            }

        if (position==1){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Termination Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.termination_unselect);
                }
            }
        }
        if (position==2){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Change of Unit")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.change_unselect);
                }
            }
        }
        if (position==3){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Transfer of Lease")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.transfer_unselect);
                }
            }
        }

        if (position==4){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("Payment Related Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.payment_related_unselect);
                }
            }
        }
        if (position==5){
            for (int i=0;i<mData.size();i++){
                if (mData.get(i).getCategoryName().equalsIgnoreCase("New Home Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(mData.get(position).getCategoryName());
                    holder.iv_category.setImageResource(R.drawable.new_home_unselected);
                }
            }
        }
        /*if (position == 5) {
            holder.tv_category_name1.setVisibility(View.INVISIBLE);
            holder.iv_category.setImageBitmap(null);
            holder.iv_category.setImageResource(R.drawable.unselected_see_all);
            holder.tv_category_name.setText("SEE ALL");
        }*/


        if(row_index==position){



            if (position==0){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Renewal Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.renewal_selected);
                    }
                }
            }


            if (position==0 && !selectedName.equalsIgnoreCase("")){
                if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.renewal_selected);
                }
            }
            if (position==1){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Termination Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.termination_selected);
                    }
                }
            }
            if (position==2){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Change of Unit")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.change_select);
                    }
                }
            }
            if (position==3){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Transfer of Lease")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.transfer_selected);
                    }
                }
            }

            if (position==4){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Payment Related Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.payment_related_select);
                    }
                }
            }

            if (position==5){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("New Home Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.new_home_selected);
                    }
                }
            }

        }
        else
        {


            if (position==0){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Renewal Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.renewal_unselected);
                    }
                }
            }

            /*if (position==0 && !selectedName.equalsIgnoreCase("")){
                if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.payment_related_select);
                }
                if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.renewal_selected);
                }
                if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.transfer_selected);
                }
                if (selectedName.equalsIgnoreCase("Change of Unit")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.change_select);
                }
                if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.termination_selected);
                }
                if (selectedName.equalsIgnoreCase("New Home Enquiry"))
                {
                    holder.tv_category_name1.setVisibility(View.INVISIBLE);
                    holder.tv_category_name.setText(selectedName);
                    holder.iv_category.setImageResource(R.drawable.new_home_selected);
                }
            }*/









            if (position==1){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Termination Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.termination_unselect);
                    }
                }
            }
            if (position==2){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Change of Unit")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.change_unselect);
                    }
                }
            }
            if (position==3){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Transfer of Lease")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.transfer_unselect);
                    }
                }
            }

            if (position==4){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("Payment Related Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.payment_related_unselect);
                    }
                }
            }

            if (position==5){
                for (int i=0;i<mData.size();i++){
                    if (mData.get(i).getCategoryName().equalsIgnoreCase("New Home Enquiry")){
                        holder.tv_category_name1.setVisibility(View.INVISIBLE);
                        holder.tv_category_name.setText(mData.get(position).getCategoryName());
                        holder.iv_category.setImageResource(R.drawable.new_home_unselected);
                    }
                }
            }
            /*rajeev logic*/
            if (!selectedName.equalsIgnoreCase("")){
                if (position==0){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;

                        }


                    }

                }
                if (position==1){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);

v=1;

                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;
                        }


                    }

                }
                if (position==2){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;
                        }

                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;
                        }


                    }

                }
                if (position==3){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;

                        }
                    }

                }
                if (position==4){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;
                        }

                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;
                        }
                    }

                }

                if (position==5){
                    if (holder.tv_category_name.getText().toString().equalsIgnoreCase(selectedName))
                    {

                        holder.tv_category_name.setText(selectedName);
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.iv_category.setImageResource(R.drawable.change_select);
                            v=1;
                        }

                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                            v=1;
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry")){
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                            v=1;
                        }
                    }

                }
                if (v == 0 && !selectedName.equalsIgnoreCase("") && t==1){

                    if (position==0 && !selectedName.equalsIgnoreCase("")){
                        if (selectedName.equalsIgnoreCase("Payment Related Enquiry")){
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.payment_related_select);
                        }
                        if (selectedName.equalsIgnoreCase("Renewal Enquiry")){
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.renewal_selected);
                        }
                        if (selectedName.equalsIgnoreCase("Transfer of Lease")){
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.transfer_selected);
                        }
                        if (selectedName.equalsIgnoreCase("Change of Unit")){
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.change_select);
                        }
                        if (selectedName.equalsIgnoreCase("Termination Enquiry")){
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.termination_selected);
                        }
                        if (selectedName.equalsIgnoreCase("New Home Enquiry"))
                        {
                            holder.tv_category_name1.setVisibility(View.INVISIBLE);
                            holder.tv_category_name.setText(selectedName);
                            holder.iv_category.setImageResource(R.drawable.new_home_selected);
                        }

                    }
                }


            }




            /**/


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
            iv_category = itemView.findViewById(R.id.iv_category);
            tv_category_name1 = itemView.findViewById(R.id.tv_category_name1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(mData.get(id));
    }

    // allows clicks events to be caught
   public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {

        void onItemClick(View view, int position);

    }
}
