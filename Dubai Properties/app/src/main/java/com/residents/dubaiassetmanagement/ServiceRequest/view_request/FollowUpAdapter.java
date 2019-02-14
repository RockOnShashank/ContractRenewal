package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.ServiceRequest.followup_model.MaximoMemo;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up.MEResWorkorderMemo;
import com.residents.dubaiassetmanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FollowUpAdapter extends RecyclerView.Adapter<FollowUpAdapter.ViewHolder> {

    private ArrayList<MaximoMemo> mData;
    private ArrayList<String> categoryID;
private String date;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Date d;

    // data is passed into the constructor
    public FollowUpAdapter(Context context, ArrayList<MaximoMemo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_follow_up, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_comment.setText(mData.get(position).getDESCRIPTION());
        try {
            date = getMonth(mData.get(position).getCREATEDATE().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 0);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        String output = sdf1.format(c.getTime());


        holder.tv_date.setText(output.replaceAll("-"," "));

    }



    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_date,tv_comment;

        ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_comment  = itemView.findViewById(R.id.tv_comment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickSeeAll(view, getAdapterPosition(), Integer.parseInt(categoryID.get(getAdapterPosition())));


        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return String.valueOf(mData.get(id));
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClickSeeAll(View view, int position,int catId);
    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }



}