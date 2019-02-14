package com.residents.dubaiassetmanagement.notification_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewDetailLSFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.my_documents.MyDocumentFragment;
import com.residents.dubaiassetmanagement.notifications_dashboard.DashboardNotification;
import com.residents.dubaiassetmanagement.notifications_dashboard.NotificationAdapter;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationList extends Fragment implements ResponseCallback , NotificationListAdapter.ItemClickListener{
private SavePreference mSavePreference;
    private View view;
    private int i;
    ArrayList<NotificationData> notificationDataList;
NotificationListAdapter notificationAdapter;
private RecyclerView rv_notifications;
private TextView fragmentTitle,tv_no_notification;
private RelativeLayout iv_bell_icon;
private BottomNavigationView bottomNavigation;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_notification, container, false);
        mSavePreference = SavePreference.getInstance(getActivity());
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);

        fragmentTitle.setText("NOTIFICATIONS");

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);


        tv_no_notification = (TextView) view.findViewById(R.id.tv_no_notification);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        rv_notifications = (RecyclerView) view.findViewById(R.id.rv_notifications);
        notificationDataList = new ArrayList<>();
        rv_notifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_house = new LinearLayoutManager(getActivity());
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentafterCancel(new HomeFragment());
                //getFragmentManager().popBackStack();

            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RequestService(getActivity(), NotificationList.this).setArguments("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        new RequestService(getActivity(), NotificationList.this).setArguments("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");
        new RequestService(getActivity(),NotificationList.this).putRequest("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/MarkAsSeen","");


        return view;
    }

    @Override
    public void onSuccess(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            notificationDataList.clear();

            for (i = 0; i <= jsonArray.length()-1; i++) {
                NotificationData notificationData = new NotificationData();

                notificationData.id = jsonArray.getJSONObject(i).getString("Id");
                notificationData.isSeen = jsonArray.getJSONObject(i).getString("IsSeen");

                JSONObject notification = jsonArray.getJSONObject(i).getJSONObject("Notification");
                notificationData.title = notification.getString("Title");
                notificationData.description = notification.getString("Description");
                notificationData.photo_url = notification.getString("Icon");
                notificationData.notificationId = notification.getString("NotificationId");



                notificationDataList.add(notificationData);

            }

            if (notificationDataList.size()==0){
                rv_notifications.setVisibility(View.GONE);
                tv_no_notification.setVisibility(View.VISIBLE);
            }else {
                tv_no_notification.setVisibility(View.GONE);

                rv_notifications.setVisibility(View.VISIBLE);
                notificationAdapter = new NotificationListAdapter(getActivity(), notificationDataList);
                rv_notifications.setAdapter(notificationAdapter);
                notificationAdapter.setClickListener(this);
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

        String res = response;


    }

    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onItemClick(View view, int position) {





            new RequestService(getActivity(),NotificationList.this).putRequest("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/"+notificationDataList.get(position).id,"");
            if (notificationDataList.get(position).notificationId.equalsIgnoreCase("DOC_EXPIRY") ||notificationDataList.get(position).notificationId.equalsIgnoreCase("DOC_VERIFICATION_FAILED") ||notificationDataList.get(position).notificationId.equalsIgnoreCase("DOC_VERIFICATION") ){

loadFragmentafterCancel(new MyDocumentFragment());
            }

        if (notificationDataList.get(position).notificationId.equalsIgnoreCase("SR_APPOINTMENT_CONFIRMED") || notificationDataList.get(position).notificationId.equalsIgnoreCase("SR_APPOINTMENT_CONFIRMED") ||notificationDataList.get(position).notificationId.equalsIgnoreCase("SR_APPOINTMENT_REMINDER") ){

            loadFragmentafterCancel(new ServicesFragment());
        }
        }





}
