package com.residents.dubaiassetmanagement.history_cc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryAdapter;
import com.residents.dubaiassetmanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryCCAdapter extends RecyclerView.Adapter<HistoryCCAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<ServiceRequest> viewRequests;
    private String dates,date;
    private Context ctx;

    // data is passed into the constructor
    HistoryCCAdapter(Context context, ArrayList<ServiceRequest> list) {
        this.mInflater = LayoutInflater.from(context);
        this.viewRequests = list;
        this.ctx = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_history_c, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_category.setText(viewRequests.get(position).getCategory());
        holder.tv_subCategory.setText(viewRequests.get(position).getSubCategory());
        holder.tv_id.setText("SR ID #"+viewRequests.get(position).getWOCode());
        holder.tv_request_status.setText(viewRequests.get(position).getStatus());

        try {
            dates = getMonth(viewRequests.get(position).getCallDate().substring(0, 10));
            holder.tv_date.setText(dates.replaceAll("-", " "));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            date = getMonth(viewRequests.get(position).getCompletionDate().substring(0, 10));
            holder.tv_preferred_dateandtime.setText(date.replaceAll("-", " "));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (viewRequests.get(position).getStatus().equalsIgnoreCase("Canceled")){
            holder.tv_status.setText("Canceled On");
            holder.tv_request_status.setTextColor(ctx.getResources().getColor(R.color.red));
        }
        if (viewRequests.get(position).getStatus().equalsIgnoreCase("Closed")){
            holder.tv_status.setText("Closed On");
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return viewRequests.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_category,tv_id,tv_subCategory,tv_request_status,tv_date,tv_preferred_dateandtime,tv_status;

        ViewHolder(View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_subCategory = itemView.findViewById(R.id.tv_subCategory);
            tv_request_status = itemView.findViewById(R.id.tv_request_status);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_preferred_dateandtime = itemView.findViewById(R.id.tv_preferred_dateandtime);
            tv_status  = itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(viewRequests.get(id));
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
}

