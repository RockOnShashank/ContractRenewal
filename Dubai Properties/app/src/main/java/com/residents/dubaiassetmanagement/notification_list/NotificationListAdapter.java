package com.residents.dubaiassetmanagement.notification_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestAdapter;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<NotificationData> notificationData;
    String date,dates;
    Context ctx;


    // data is passed into the constructor
    NotificationListAdapter(Context context, ArrayList<NotificationData> list) {
        this.mInflater = LayoutInflater.from(context);
        this.notificationData = list;
        ctx=context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notification_list, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_title.setText(notificationData.get(position).title);
        holder.tv_description.setText(notificationData.get(position).description);
       /* Glide.with(ctx).load(notificationData.get(position).photo_url+".png")
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_icon_notification);*/

if (position %3 ==0){
    holder.ll_background.setBackgroundColor(ctx.getResources().getColor(R.color.light_pink_color));

}
if (position% 3==1){
    holder.ll_background.setBackgroundColor(ctx.getResources().getColor(R.color.cheque_background));

}
if (position%3==2){
    holder.ll_background.setBackgroundColor(ctx.getResources().getColor(R.color.document_noti));
}
/*
       if (notificationData.get(position).title.contains("Document")){
           holder.ll_background.setBackgroundColor(ctx.getResources().getColor(R.color.light_pink_color));
       }
        if (notificationData.get(position).title.contains("Request")){
            holder.ll_background.setBackgroundColor(ctx.getResources().getColor(R.color.cheque_background));
        }*/
        new DownloadImageTask(holder.iv_icon_notification).execute(notificationData.get(position).photo_url);

      //  Picasso.get().load(notificationData.get(position).photo_url+".jpg").into(holder.iv_icon_notification);

    }
    // total number of rows
    @Override
    public int getItemCount() {
        return notificationData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      private   TextView tv_title,tv_description;
     private    ImageView iv_icon_notification;
     private LinearLayout ll_background;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            ll_background = itemView.findViewById(R.id.ll_background);
            iv_icon_notification = itemView.findViewById(R.id.iv_icon_notification);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(notificationData.get(id));
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Bitmap image;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                image = null;
            }
            return image;
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}

