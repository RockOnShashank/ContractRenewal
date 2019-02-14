package com.residents.dubaiassetmanagement.notifications_dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.notification_list.NotificationList;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.Notification;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationsDashboard;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardNotification extends AppCompatActivity implements ResponseCallback,ViewPager.OnPageChangeListener {
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ViewPager viewPager;
    private List<String> listItem = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    SavePreference mSavePreference;
TextView tv_no_noti;
    int i;
    JSONObject jsonObject = null;
    String title,description;
    ArrayList<NotificationData> notificationDataList;
    private Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        mSavePreference = SavePreference.getInstance(this);

        new RequestService(this, DashboardNotification.this).setArguments("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");

        setContentView(R.layout.fragment_dashboard_noti);
        notificationDataList = new ArrayList<>();
        indicator = (LinearLayout) findViewById(R.id.indicators);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        tv_no_noti = (TextView) findViewById(R.id.tv_no_noti);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationList());
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new RequestService(DashboardNotification.this,DashboardNotification.this).putRequestSkip("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/MarkAsSeen","");

                finish();
            }
        });
        setData();
    }

    @Override
    public void onSuccess(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (i = 0; i <= jsonArray.length()-1; i++) {
                NotificationData notificationData = new NotificationData();
                JSONObject notification = jsonArray.getJSONObject(i).getJSONObject("Notification");
                notificationData.title = notification.getString("Title");
                notificationData.description = notification.getString("Description");
                notificationData.photo_url = notification.getString("Icon");
                notificationData.isSeen = jsonArray.getJSONObject(i).getString("IsSeen");

                if (notificationData.isSeen.equalsIgnoreCase("false")){
                    notificationDataList.add(notificationData);

                }

            }
            if (notificationDataList.size()==0){
                tv_no_noti.setVisibility(View.VISIBLE);
            }else {
                tv_no_noti.setVisibility(View.GONE);

                notificationAdapter = new NotificationAdapter(this, getSupportFragmentManager(), notificationDataList);
                viewPager.setAdapter(notificationAdapter);
                viewPager.setCurrentItem(0);
                viewPager.setOnPageChangeListener(this);
                setUiPageViewController();
            }

        }catch (Exception e){

        }
    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {

    }

    @Override
    public void onSuccessSecond(String response) {

    }

    @Override
    public void onPostSuccess(String response, String sessionId) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i=0; i<mDotCount; i++){
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[position].setBackgroundResource(R.drawable.selected_item);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void setData(){



    }
    private void setUiPageViewController(){
        mDotCount = notificationAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for(int i=0; i<mDotCount; i++){
            mDots[i] = new LinearLayout(this);
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4,0,4,0);
            indicator.addView(mDots[i],params);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
