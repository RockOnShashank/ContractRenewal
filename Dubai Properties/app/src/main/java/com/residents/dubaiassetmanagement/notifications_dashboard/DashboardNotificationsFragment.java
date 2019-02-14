package com.residents.dubaiassetmanagement.notifications_dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.notification_list.NotificationList;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.greenrobot.event.EventBus;

public class DashboardNotificationsFragment extends Fragment {

    private TextView tv_description,tv_title;
    private String detail,title,photo_url;
    private View view;
    private ImageView iv_icon;
private LinearLayout ll_background;
private String position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_screen, container, false);
        tv_title = view.findViewById(R.id.tv_title);
        tv_description = view.findViewById(R.id.tv_description);
        ll_background = view.findViewById(R.id.ll_background);
        iv_icon = view.findViewById(R.id.iv_icon);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
       /* Glide.with(getActivity()).load(getPhotoUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_icon);*/
        tv_title.setText(getDescription(getTitle()));
        tv_description.setText(getDetail());

        position= getTitle().substring(getTitle().length() - 1);

        if (Integer.parseInt(position) %3 ==0){
            ll_background.setBackgroundColor(getResources().getColor(R.color.light_pink_color));

        }
        if (Integer.parseInt(position) %3 ==1){
            ll_background.setBackgroundColor(getResources().getColor(R.color.cheque_background));

        }
        if (Integer.parseInt(position) %3 ==2){
            ll_background.setBackgroundColor(getResources().getColor(R.color.document_noti));
        }
        ll_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new StepperStatus());

                    }
                }, 500);
            }
        });
         return view;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPhotoUrl() {
        return photo_url;
    }

    public void setPhotoUrl(String photo_url) {
        this.photo_url = photo_url;
    }
    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentManager fragmentManager = this.getChildFragmentManager();



        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
