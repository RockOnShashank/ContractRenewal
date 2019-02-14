package com.residents.dubaiassetmanagement.contract_renewal.payment_schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models.InstallmentPlan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentScheduleAdapter  extends RecyclerView.Adapter<PaymentScheduleAdapter.ViewHolder> {
private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<InstallmentPlan> installmentlist;
    private String noOfInstallment;
    Integer[] pdcs = {
            R.drawable.pdc_one,
            R.drawable.pdc_two,
            R.drawable.pdc_three,
            R.drawable.pdc_four,
            R.drawable.pdc_five,
            R.drawable.pdc_six,
            R.drawable.pdc_seven,
            R.drawable.pdc_eight,
            R.drawable.pdc_nine,
            R.drawable.pdc_ten,
            R.drawable.pdc_eleven,
            R.drawable.pdc_twelve

    };


    PaymentScheduleAdapter(Context context,ArrayList<InstallmentPlan> installmentlists,String noOfInstallments) {
        this.mInflater = LayoutInflater.from(context);
        this.installmentlist = installmentlists;
        this.noOfInstallment = noOfInstallments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.payment_schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // String animal = mData.get(position);
     //   holder.myTextView.setText(animal);

        if (noOfInstallment.equalsIgnoreCase("1")){
            holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
            holder.tv_date.setText(installmentlist.get(0).getPdcResponse().getPdcList().get(0).getInstallementDate());
            holder.tv_amount.setText(installmentlist.get(0).getPdcResponse().getPdcList().get(0).getInstallmentAmount());

        }
        if (noOfInstallment.equalsIgnoreCase("2")){
            if (position==0) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(1).getPdcResponse().getPdcList().get(0).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(1).getPdcResponse().getPdcList().get(0).getInstallmentAmount());

            }
            if (position==1) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_two);
                holder.tv_date.setText(installmentlist.get(1).getPdcResponse().getPdcList().get(1).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(1).getPdcResponse().getPdcList().get(1).getInstallmentAmount());
            }
        }
        if (noOfInstallment.equalsIgnoreCase("4")){
            if (position==0) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(0).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(0).getInstallmentAmount());

            }
            if (position==1) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_two);
                holder.tv_date.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(1).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(1).getInstallmentAmount());
            }
            if (position==2) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_three);
                holder.tv_date.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(2).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(2).getInstallmentAmount());
            }
            if (position==3) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_four);
                holder.tv_date.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(3).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(2).getPdcResponse().getPdcList().get(3).getInstallmentAmount());
            }
        }

        if (noOfInstallment.equalsIgnoreCase("12")){
            if (position==0) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(0).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(0).getInstallmentAmount());

            }
            if (position==1) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(1).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(1).getInstallmentAmount());

            }
            if (position==2) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(2).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(2).getInstallmentAmount());

            }
            if (position==3) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(3).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(3).getInstallmentAmount());

            }
            if (position==4) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(4).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(4).getInstallmentAmount());

            }
            if (position==5) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(5).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(5).getInstallmentAmount());

            }
            if (position==6) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(6).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(6).getInstallmentAmount());

            }
            if (position==0) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(7).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(7).getInstallmentAmount());

            }
            if (position==7) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(8).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(8).getInstallmentAmount());

            }
            if (position==8) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(9).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(9).getInstallmentAmount());

            }
            if (position==9) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(10).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(10).getInstallmentAmount());

            }
            if (position==10) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(11).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(11).getInstallmentAmount());

            }
            if (position==11) {
                holder.iv_pdc.setBackgroundResource(R.drawable.pdc_one);
                holder.tv_date.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(12).getInstallementDate());
                holder.tv_amount.setText(installmentlist.get(4).getPdcResponse().getPdcList().get(12).getInstallmentAmount());

            }

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return Integer.parseInt(noOfInstallment);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      private   TextView tv_date,tv_amount;
     private    ImageView iv_pdc;

        ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            iv_pdc = itemView.findViewById(R.id.iv_pdc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
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
