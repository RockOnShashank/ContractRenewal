package com.residents.dubaiassetmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.reflect.TypeToken;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.CreateNewFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new.CommunityCareFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewRequestCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.create_new.LeasingServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestDetailFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.contract_renewal.ContractRenewalActivity;
import com.residents.dubaiassetmanagement.contract_renewal.DashboardContractRenewal;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.events.BottomNavigationStatus;
import com.residents.dubaiassetmanagement.home_screen.HomeDetailViewRequest;
import com.residents.dubaiassetmanagement.home_screen.HomeFeedbackFragment;
import com.residents.dubaiassetmanagement.home_screen.HomeViewDetailCCFragment;
import com.residents.dubaiassetmanagement.home_screen.model_service_home.ServiceRequest;
import com.residents.dubaiassetmanagement.home_screen.model_service_home.ServiceRequestHome;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.my_documents.MyDocumentFragment;
import com.residents.dubaiassetmanagement.my_documents.models.DPGAttachmentDetailsV;
import com.residents.dubaiassetmanagement.my_documents.models.MyDocuments;
import com.residents.dubaiassetmanagement.notification_list.NotificationList;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ResponseCallback {

    private TextView fragmentTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SavePreference mSavePreference;
    private ServiceRequestHome serviceRequestHome;
    private TextView tv_category,tv_id,tv_subCategory,tv_request_status,tv_date,tv_preferred_dateandtime,tv_description,tv_see_msr,tv_schedule1,tv_schedule2,tv_schedule3;
    private TextView tv_category1,tv_id1,tv_subCategory1,tv_request_status1,tv_date1,tv_preferred_dateandtime1,tv_description1;
    private TextView tv_category2,tv_comm_name,tv_count,tv_id2,tv_subCategory2,tv_request_status2,tv_date2,tv_preferred_dateandtime2,tv_description2,home_user_name_title,tv_ccr;
    private LinearLayout ll_msr,ll_ccr,ll_lsr;
    private LinearLayout ll_msr_title,ll_ccr_title,ll_lsr_title;
    int showFeeds=0;
    private ArrayList<ServiceRequest> list;
    private ArrayList<ServiceRequest> listHome;

    private String responseFeed;
    private RelativeLayout iv_bell_icon;
    private String date01,date0,date1,date2,date22,date21;
    ArrayList<NotificationData> notificationDataList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private WebView wv_instafeed;
    private OnFragmentInteractionListener mListener;
    private LinearLayout ll_item1,ll_item2,ll_item3;
    private BottomNavigationView bottomNavigation;
    private LinearLayout ll_createService;
    private LinearLayout ll_create_new_mr,ll_create_new_cc,ll_create_new_rs;

    SharedPreferences appSharedPrefs;
    Button bt_startContract;
    SharedPreferences.Editor prefsEditor;
    Gson gson;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavePreference = SavePreference.getInstance(getActivity());

       /* // calling to get community
        if (mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)==null) {
            new RequestService(getActivity(), HomeFragment.this).setArgumentsSecond("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications/Community");
        }*/
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);

        fragmentTitle.setText("HOME");

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.VISIBLE);


        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.VISIBLE);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        list = new ArrayList<>();
        listHome = new ArrayList<>();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        listHome = new ArrayList<>();
        wv_instafeed = view.findViewById(R.id.wv_instafeed);
        WebSettings webSettings = wv_instafeed.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ((DrawerLocker)getActivity()).setDrawerEnabled(false);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLocker)getActivity()).setDrawerOpen(true);
            }
        });
        //Set Fragment Title
        notificationDataList = new ArrayList<>();
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("HOME");

        // calling to get community
        if (mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)==null) {
            new RequestService(getActivity(), HomeFragment.this).setArgumentsSecond("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications/Community");
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RequestService(getActivity(), HomeFragment.this).setArgumentsNotificationCount("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");
                new RequestService(getActivity(), HomeFragment.this).setArguments("ServiceRequest/Home/" + mSavePreference.getString(IpreferenceKey.TCODE));
                new RequestService(getActivity(), HomeFragment.this).setArgumentsSecond("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/Community");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.VISIBLE);

        tv_count = (TextView) getActivity().findViewById(R.id.tv_count);
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        prefsEditor = appSharedPrefs.edit();
tv_count.setVisibility(View.GONE);
        gson = new Gson();

        if (!appSharedPrefs.getString("Home_List", "").equalsIgnoreCase("")) {
        }else {
          //  new RequestService(getActivity(), HomeFragment.this).setArgumentsNotificationCount("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");

        }



        //  ((DrawerLocker)getActivity()).setDrawerEnabled(false);

        tv_schedule1 = (TextView) view.findViewById(R.id.tv_schedule1);
        tv_schedule2 = (TextView) view.findViewById(R.id.tv_schedule2);
        tv_schedule3 = (TextView) view.findViewById(R.id.tv_schedule3);


        tv_comm_name = (TextView) view.findViewById(R.id.tv_comm_name);


        ll_msr = (LinearLayout) view.findViewById(R.id.ll_msr);
        ll_ccr = (LinearLayout) view.findViewById(R.id.ll_ccr);
        ll_lsr = (LinearLayout) view.findViewById(R.id.ll_lsr);
        tv_see_msr = (TextView) view.findViewById(R.id.tv_see_msr);
        ll_msr_title = (LinearLayout) view.findViewById(R.id.ll_msr_title);
        ll_ccr_title = (LinearLayout) view.findViewById(R.id.ll_ccr_title);
        ll_lsr_title = (LinearLayout) view.findViewById(R.id.ll_lsr_title);
        ll_createService = (LinearLayout) view.findViewById(R.id.ll_createService);
        ll_item1 = (LinearLayout) view.findViewById(R.id.ll_item1);
        ll_item2 = (LinearLayout) view.findViewById(R.id.ll_item2);
        ll_item3 = (LinearLayout) view.findViewById(R.id.ll_item3);

        bt_startContract = (Button) view.findViewById(R.id.home_button_renew);

        ll_create_new_mr = (LinearLayout) view.findViewById(R.id.ll_create_new_mr);
        ll_create_new_cc = (LinearLayout) view.findViewById(R.id.ll_create_new_cc);
        ll_create_new_rs = (LinearLayout) view.findViewById(R.id.ll_create_new_rs);

        tv_ccr = (TextView) view.findViewById(R.id.tv_ccr);

        tv_category = (TextView) view.findViewById(R.id.tv_category);
        home_user_name_title = (TextView) view.findViewById(R.id.home_user_name_title);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);


        tv_category1 = (TextView) view.findViewById(R.id.tv_category1);
        tv_id1 = (TextView) view.findViewById(R.id.tv_id1);
        tv_subCategory1 = (TextView) view.findViewById(R.id.tv_subCategory1);
        tv_request_status1 = (TextView) view.findViewById(R.id.tv_request_status1);
        tv_date1 = (TextView) view.findViewById(R.id.tv_date1);
        tv_preferred_dateandtime1 = (TextView) view.findViewById(R.id.tv_preferred_dateandtime1);


        tv_category2 = (TextView) view.findViewById(R.id.tv_category2);
        tv_id2 = (TextView) view.findViewById(R.id.tv_id2);
        tv_subCategory2 = (TextView) view.findViewById(R.id.tv_subCategory2);
        tv_request_status2 = (TextView) view.findViewById(R.id.tv_request_status2);
        tv_date2 = (TextView) view.findViewById(R.id.tv_date2);
        tv_preferred_dateandtime2 = (TextView) view.findViewById(R.id.tv_preferred_dateandtime2);

        home_user_name_title.setText(mSavePreference.getString(IpreferenceKey.FIRSTNAME));

        if (!appSharedPrefs.getString("Home_List", "").equalsIgnoreCase("")) {
            tv_comm_name.setVisibility(View.VISIBLE);
            String json = appSharedPrefs.getString("Home_List", "");
            Type type = new TypeToken<List<ServiceRequest>>(){}.getType();
            list = gson.fromJson(json, type);
            ll_createService.setVisibility(View.GONE);

            /*ll_msr.setVisibility(View.VISIBLE);
            ll_ccr.setVisibility(View.VISIBLE);
            ll_lsr.setVisibility(View.VISIBLE);*/
            /*
                if (list.size()==0){

                }else {*/


            for (int i = 0; i < list.size(); i++) {

                if (i == 0) {
                    ll_msr.setVisibility(View.VISIBLE);

                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                        ll_msr_title.setVisibility(View.VISIBLE);
                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                        ll_ccr_title.setVisibility(View.VISIBLE);

                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                        ll_lsr_title.setVisibility(View.VISIBLE);
                    }
                    tv_category.setText(list.get(i).getCategory());
                    tv_subCategory.setText(list.get(i).getSubCategory());

                    try {
                        date0 = getMonth(list.get(i).getCallDate().substring(0, 10));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tv_date.setText(date0.replaceAll("-"," "));

                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        if (list.get(i).getCompletionDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime.setText(date01.replaceAll("-", " "));
                        } else {
                            tv_preferred_dateandtime.setText("NA");
                        }
                    }else {
                        if (list.get(i).getPreferredDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime.setText(date01.replaceAll("-", " "));

                            if (list.get(i).getPreferredTimeSlot() != null){
                                tv_preferred_dateandtime.setText(date01.replaceAll("-", " ")+", "+ list.get(i).getPreferredTimeSlot());

                            }
                        } else {
                            tv_preferred_dateandtime.setText("Awaiting");
                        }


                    }



                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        tv_request_status.setTextColor(getResources().getColor(R.color.red));
                        tv_schedule1.setText("Canceled On");
                    }else if (list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        tv_request_status.setTextColor(getResources().getColor(R.color.vertical_line));
                        tv_schedule1.setText("Closed On");

                    }else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")){
                        tv_request_status.setTextColor(getResources().getColor(R.color.green_color));

                    }
                    else if (list.get(i).getStatus().equalsIgnoreCase("Open")){
                        tv_request_status.setTextColor(getResources().getColor(R.color.orange));

                    }
                    tv_id.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ",""));
                    tv_request_status.setText(list.get(i).getStatus());
                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")){
                        tv_schedule1.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime.setText("NA");
                    }


                    if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        try {
                            tv_preferred_dateandtime.setVisibility(View.VISIBLE);
                            tv_preferred_dateandtime.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));
                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_preferred_dateandtime.setText("NA");
                            }
                            } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (i == 1) {
                    ll_ccr.setVisibility(View.VISIBLE);
                    ll_ccr_title.setVisibility(View.GONE);

                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                        // ll_msr_title.setVisibility(View.VISIBLE);
                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                        ll_ccr_title.setVisibility(View.VISIBLE);

                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                        ll_lsr_title.setVisibility(View.VISIBLE);

                    }
                    tv_category1.setText(list.get(i).getCategory());
                    tv_subCategory1.setText(list.get(i).getSubCategory());
                    try {
                        date1 = getMonth(list.get(i).getCallDate().substring(0, 10));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tv_date1.setText(date1.replaceAll("-"," "));
                    if (list.get(i).getPreferredDate()!=null) {
                        try {
                            date22 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tv_preferred_dateandtime1.setText(date22.replaceAll("-", " "));
                    }else {
                        tv_preferred_dateandtime1.setText("Awaiting");
                    }


                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        if (list.get(i).getCompletionDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime1.setText(date01.replaceAll("-", " "));
                        } else {
                            tv_preferred_dateandtime1.setText("NA");
                        }
                    }else {
                        if (list.get(i).getPreferredDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime1.setText(date01.replaceAll("-", " "));
                            if (list.get(i).getPreferredTimeSlot() != null){
                                tv_preferred_dateandtime1.setText(date01.replaceAll("-", " ")+", "+ list.get(i).getPreferredTimeSlot());

                            }
                        } else {
                            tv_preferred_dateandtime1.setText("Awaiting");
                        }
                    }


                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        tv_request_status1.setTextColor(getResources().getColor(R.color.red));
                        tv_schedule2.setText("Canceled On");

                    }else if (list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        tv_request_status1.setTextColor(getResources().getColor(R.color.vertical_line));
                        tv_schedule2.setText("Closed On");

                    }else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")){
                        tv_request_status1.setTextColor(getResources().getColor(R.color.green_color));

                    }
                    else if (list.get(i).getStatus().equalsIgnoreCase("Open")){
                        tv_request_status1.setTextColor(getResources().getColor(R.color.orange));

                    }
                    tv_id1.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ",""));
                    tv_request_status1.setText(list.get(i).getStatus());
                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")){
                        tv_schedule2.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime1.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime1.setText("NA");


                    }
                    if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        try {
                            tv_preferred_dateandtime1.setVisibility(View.VISIBLE);
                            tv_preferred_dateandtime1.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));
                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_preferred_dateandtime1.setText("NA");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (i == 2) {
                    ll_lsr.setVisibility(View.VISIBLE);
                    ll_lsr_title.setVisibility(View.GONE);

                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                        // ll_msr_title.setVisibility(View.VISIBLE);
                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                        ll_ccr_title.setVisibility(View.VISIBLE);

                    } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                        ll_lsr_title.setVisibility(View.VISIBLE);
                    }
                    tv_category2.setText(list.get(i).getCategory());
                    tv_subCategory2.setText(list.get(i).getSubCategory());

                    try {
                        date2 = getMonth(list.get(i).getCallDate().substring(0, 10));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tv_date2.setText(date2.replaceAll("-"," "));

                    if (list.get(i).getPreferredDate()!=null) {
                        try {
                            date21 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        tv_preferred_dateandtime2.setText(date21.replaceAll("-", " "));
                    }
                    else {
                        tv_preferred_dateandtime2.setText("Awaiting");
                    }


                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        if (list.get(i).getCompletionDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime2.setText(date01.replaceAll("-", " "));
                        } else {
                            tv_preferred_dateandtime2.setText("NA");
                        }
                    }else {
                        if (list.get(i).getPreferredDate() != null) {
                            try {
                                date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_preferred_dateandtime2.setText(date01.replaceAll("-", " "));

                            if (list.get(i).getPreferredTimeSlot() != null){
                                tv_preferred_dateandtime2.setText(date01.replaceAll("-", " ")+", "+ list.get(i).getPreferredTimeSlot());

                            }
                        } else {
                            tv_preferred_dateandtime2.setText("Awaiting");
                        }
                    }





                    if (list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        tv_request_status2.setTextColor(getResources().getColor(R.color.red));
                        tv_schedule3.setText("Canceled On");

                    }else if (list.get(i).getStatus().equalsIgnoreCase("Closed")){
                        tv_request_status2.setTextColor(getResources().getColor(R.color.vertical_line));
                        tv_schedule3.setText("Closed On");


                    }else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")){
                        tv_request_status2.setTextColor(getResources().getColor(R.color.green_color));

                    }
                    else if (list.get(i).getStatus().equalsIgnoreCase("Open")){
                        tv_request_status2.setTextColor(getResources().getColor(R.color.orange));

                    }
                    tv_id2.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ",""));
                    tv_request_status2.setText(list.get(i).getStatus());

                    if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")){
                        tv_schedule3.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime2.setVisibility(View.VISIBLE);
                        tv_preferred_dateandtime2.setText("NA");

                    }
                    if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                        try {
                            tv_preferred_dateandtime2.setVisibility(View.VISIBLE);
                            tv_preferred_dateandtime2.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));

                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_preferred_dateandtime2.setText("NA");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
ll_ccr_title.setVisibility(View.GONE);
            ll_lsr_title.setVisibility(View.GONE);



        }else {
            new RequestService(getActivity(), HomeFragment.this).setArguments("ServiceRequest/Home/" + mSavePreference.getString(IpreferenceKey.TCODE));

        }
        /*if(mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)==null || mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME).isEmpty()){
            new RequestService(getActivity(), HomeFragment.this).setArgumentsSecond("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/Community");
        }*/

        wv_instafeed.getSettings().setJavaScriptEnabled(true);
        wv_instafeed.getSettings().setUseWideViewPort(true);
        wv_instafeed.loadUrl("https://residents.dubaiam.ae/InstagramFeed?communityCode="+mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME));


        tv_ccr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewRequestCCFragment viewRequestFragment = new ViewRequestCCFragment();
                switchFragment(viewRequestFragment);
            }
        });

        tv_see_msr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewRequestFragment viewRequestFragment = new ViewRequestFragment();
                switchFragment(viewRequestFragment);
            }
        });
        ll_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                listHome.addAll(list);
                bundle.putParcelableArrayList("viewRequestList",listHome );
                bundle.putInt("position",0);

                if (listHome.get(0).getServiceRequestType().equalsIgnoreCase("2")){
                    loadFragment(new HomeViewDetailCCFragment(), bundle);

                }else {
                    if (listHome.get(0).getStatus().equalsIgnoreCase("Canceled") || listHome.get(0).getStatus().equalsIgnoreCase("Closed"))
                    {
                        Bundle bundle1 = new Bundle();
                        bundle1.putParcelableArrayList("viewRequestList",listHome );
                        bundle1.putInt("position",0);
                        loadFragmentsBundle(new HomeFeedbackFragment(), bundle);
                    }else {
                        loadFragment(new HomeDetailViewRequest(), bundle);
                    }
                }

            }
        });
        ll_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                listHome.addAll(list);
                bundle.putParcelableArrayList("viewRequestList",listHome );
                bundle.putInt("position",1);

                if (listHome.get(1).getServiceRequestType().equalsIgnoreCase("2")) {
                    loadFragment(new HomeViewDetailCCFragment(), bundle);

                }else {

                    if (listHome.get(1).getStatus().equalsIgnoreCase("Canceled") || listHome.get(1).getStatus().equalsIgnoreCase("Closed"))
                    {
                        Bundle bundle1 = new Bundle();
                        bundle1.putParcelableArrayList("viewRequestList",listHome );
                        bundle1.putInt("position",1);
                        loadFragmentsBundle(new HomeFeedbackFragment(), bundle);
                    }else {
                        loadFragment(new HomeDetailViewRequest(), bundle);
                    }
                }
            }
        });
        ll_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                listHome.addAll(list);
                bundle.putParcelableArrayList("viewRequestList",listHome );
                bundle.putInt("position",2);

                if (listHome.get(2).getServiceRequestType().equalsIgnoreCase("2")) {
                    loadFragment(new HomeViewDetailCCFragment(), bundle);

                }else {
                    if (listHome.get(2).getStatus().equalsIgnoreCase("Canceled") || listHome.get(2).getStatus().equalsIgnoreCase("Closed"))
                    {
                        Bundle bundle1 = new Bundle();
                        bundle1.putParcelableArrayList("viewRequestList",listHome );
                        bundle1.putInt("position",2);
                        loadFragmentsBundle(new HomeFeedbackFragment(), bundle);
                    }else {
                        loadFragment(new HomeDetailViewRequest(), bundle);
                    }
                }
            }
        });
        iv_bell_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentNotification(new NotificationList());
            }
        });

        ll_create_new_mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new CreateNewFragment());

            }
        });
        ll_create_new_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new CommunityCareFragment());

            }
        });
        ll_create_new_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new LeasingServicesFragment());

            }
        });
        bt_startContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContractRenewalActivity.class));

                //loadFragmentsCR(new DashboardContractRenewal());
            }
        });
        if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT) != null) {
            if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT).equalsIgnoreCase("0")) {
                tv_count.setVisibility(View.GONE);
            } else {
                tv_count.setText(mSavePreference.getString(IpreferenceKey.NOTI_COUNT));
                tv_count.setVisibility(View.GONE);
                iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
                iv_bell_icon.setVisibility(View.VISIBLE);
            }
        }
if (mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)!=null){
    tv_comm_name.setVisibility(View.VISIBLE);
}
        tv_comm_name.setText("#"+mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)+"Life");
        EventBus.getDefault().post(new BottomNavigationStatus());

fragmentTitle.setText("HOME");
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSuccess(String response) {

        //       new RequestService(getActivity(), HomeFragment.this).setArgumentsSecond("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/Community");

        if (response.equalsIgnoreCase("")){
            ll_createService.setVisibility(View.VISIBLE);
        }else {
            tv_comm_name.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            serviceRequestHome = gson.fromJson(response, ServiceRequestHome.class);
            if (serviceRequestHome != null) {
                list.clear();
                if (serviceRequestHome.getServiceRequests() != null) {
                    ll_createService.setVisibility(View.GONE);

                    list.addAll(serviceRequestHome.getServiceRequests());

                    if (list.size() == 1) {
                        ll_msr.setVisibility(View.VISIBLE);

                    } else if (list.size() == 2) {
                        ll_msr.setVisibility(View.VISIBLE);
                        ll_ccr.setVisibility(View.VISIBLE);
                    } else {
                        ll_msr.setVisibility(View.VISIBLE);
                        ll_ccr.setVisibility(View.VISIBLE);
                        ll_lsr.setVisibility(View.VISIBLE);

                    }
            /*
                if (list.size()==0){

                }else {*/


                    for (int i = 0; i < list.size(); i++) {

                        if (i == 0) {
                            ll_msr.setVisibility(View.VISIBLE);

                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                                ll_msr_title.setVisibility(View.VISIBLE);
                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                ll_ccr_title.setVisibility(View.VISIBLE);

                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                                ll_lsr_title.setVisibility(View.VISIBLE);
                            }
                            tv_category.setText(list.get(i).getCategory());
                            tv_subCategory.setText(list.get(i).getSubCategory());

                            try {
                                date0 = getMonth(list.get(i).getCallDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_date.setText(date0.replaceAll("-", " "));

                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                if (list.get(i).getCompletionDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime.setText(date01.replaceAll("-", " "));
                                } else {
                                    tv_preferred_dateandtime.setText("NA");
                                }
                            } else {
                                if (list.get(i).getPreferredDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime.setText(date01.replaceAll("-", " "));
                                    if (list.get(i).getPreferredTimeSlot() != null) {
                                        tv_preferred_dateandtime.setText(date01.replaceAll("-", " ") + "\n" + list.get(i).getPreferredTimeSlot());

                                    }
                                } else {
                                    tv_preferred_dateandtime.setText("Awaiting");
                                }
                            }

                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled")) {
                                tv_request_status.setTextColor(getResources().getColor(R.color.red));
                                tv_schedule1.setText("Canceled On");
                            } else if (list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                tv_request_status.setTextColor(getResources().getColor(R.color.vertical_line));
                                tv_schedule1.setText("Closed On");


                            } else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")) {
                                tv_request_status.setTextColor(getResources().getColor(R.color.green_color));

                            } else if (list.get(i).getStatus().equalsIgnoreCase("Open")) {
                                tv_request_status.setTextColor(getResources().getColor(R.color.orange));

                            }
                            tv_id.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ", ""));
                            tv_request_status.setText(list.get(i).getStatus());
                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_schedule1.setVisibility(View.VISIBLE);
                                tv_preferred_dateandtime.setVisibility(View.VISIBLE);
                                try {
                                    tv_preferred_dateandtime.setText(getMonth(list.get(i).getCallDate().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                                try {
                                    tv_preferred_dateandtime.setVisibility(View.VISIBLE);
                                    tv_preferred_dateandtime.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (i == 1) {
                            ll_ccr_title.setVisibility(View.GONE);
                            ll_ccr.setVisibility(View.VISIBLE);

                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                                // ll_msr_title.setVisibility(View.VISIBLE);
                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                ll_ccr_title.setVisibility(View.VISIBLE);

                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                                ll_lsr_title.setVisibility(View.VISIBLE);

                            }
                            tv_category1.setText(list.get(i).getCategory());
                            tv_subCategory1.setText(list.get(i).getSubCategory());
                            try {
                                date1 = getMonth(list.get(i).getCallDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_date1.setText(date1.replaceAll("-", " "));
                            if (list.get(i).getPreferredDate() != null) {
                                try {
                                    date22 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                tv_preferred_dateandtime1.setText(date22.replaceAll("-", " "));
                            } else {
                                tv_preferred_dateandtime1.setText("Awaiting");
                            }


                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                if (list.get(i).getCompletionDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime1.setText(date01.replaceAll("-", " "));
                                } else {
                                    tv_preferred_dateandtime1.setText("NA");
                                }
                            } else {
                                if (list.get(i).getPreferredDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime1.setText(date01.replaceAll("-", " "));
                                    if (list.get(i).getPreferredTimeSlot() != null) {
                                        tv_preferred_dateandtime1.setText(date01.replaceAll("-", " ") + "\n" + list.get(i).getPreferredTimeSlot());

                                    }
                                } else {
                                    tv_preferred_dateandtime1.setText("Awaiting");
                                }
                            }


                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled")) {
                                tv_request_status1.setTextColor(getResources().getColor(R.color.red));
                                tv_schedule2.setText("Canceled On");

                            } else if (list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                tv_request_status1.setTextColor(getResources().getColor(R.color.vertical_line));
                                tv_schedule2.setText("Closed On");


                            } else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")) {
                                tv_request_status1.setTextColor(getResources().getColor(R.color.green_color));

                            } else if (list.get(i).getStatus().equalsIgnoreCase("Open")) {
                                tv_request_status1.setTextColor(getResources().getColor(R.color.orange));

                            }
                            tv_id1.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ", ""));
                            tv_request_status1.setText(list.get(i).getStatus());
                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_schedule2.setVisibility(View.VISIBLE);
                                tv_preferred_dateandtime1.setVisibility(View.VISIBLE);
                                tv_preferred_dateandtime1.setText("NA");
                                try {
                                    tv_preferred_dateandtime.setText(getMonth(list.get(i).getCallDate().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                                try {
                                    tv_preferred_dateandtime1.setVisibility(View.VISIBLE);
                                    tv_preferred_dateandtime1.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (i == 2) {
                            ll_lsr.setVisibility(View.VISIBLE);

                            ll_lsr_title.setVisibility(View.GONE);

                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("1")) {
                                // ll_msr_title.setVisibility(View.VISIBLE);
                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                ll_ccr_title.setVisibility(View.VISIBLE);

                            } else if (list.get(i).getServiceRequestType().equalsIgnoreCase("3")) {
                                ll_lsr_title.setVisibility(View.VISIBLE);
                            }
                            tv_category2.setText(list.get(i).getCategory());
                            tv_subCategory2.setText(list.get(i).getSubCategory());

                            try {
                                date2 = getMonth(list.get(i).getCallDate().substring(0, 10));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            tv_date2.setText(date2.replaceAll("-", " "));

                            if (list.get(i).getPreferredDate() != null) {
                                try {
                                    date21 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                tv_preferred_dateandtime2.setText(date21.replaceAll("-", " "));
                            } else {
                                tv_preferred_dateandtime2.setText("Awaiting");
                            }


                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled") || list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                if (list.get(i).getCompletionDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getCompletionDate().toString().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime2.setText(date01.replaceAll("-", " "));
                                } else {
                                    tv_preferred_dateandtime2.setText("NA");
                                }
                            } else {
                                if (list.get(i).getPreferredDate() != null) {
                                    try {
                                        date01 = getMonth(list.get(i).getPreferredDate().substring(0, 10));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    tv_preferred_dateandtime2.setText(date01.replaceAll("-", " "));
                                    if (list.get(i).getPreferredTimeSlot() != null) {
                                        tv_preferred_dateandtime2.setText(date01.replaceAll("-", " ") + "\n" + list.get(i).getPreferredTimeSlot());

                                    }
                                } else {
                                    tv_preferred_dateandtime2.setText("Awaiting");
                                }
                            }


                            if (list.get(i).getStatus().equalsIgnoreCase("Canceled")) {
                                tv_request_status2.setTextColor(getResources().getColor(R.color.red));
                                tv_schedule3.setText("Canceled On");

                            } else if (list.get(i).getStatus().equalsIgnoreCase("Closed")) {
                                tv_request_status2.setTextColor(getResources().getColor(R.color.vertical_line));
                                tv_schedule3.setText("Closed On");


                            } else if (list.get(i).getStatus().equalsIgnoreCase("In Progress")) {
                                tv_request_status2.setTextColor(getResources().getColor(R.color.green_color));

                            } else if (list.get(i).getStatus().equalsIgnoreCase("Open")) {
                                tv_request_status2.setTextColor(getResources().getColor(R.color.orange));

                            }
                            tv_id2.setText("SR ID #" + list.get(i).getWOCode().replaceAll(" ", ""));
                            tv_request_status2.setText(list.get(i).getStatus());

                            if (list.get(i).getServiceRequestType().equalsIgnoreCase("2")) {
                                tv_schedule3.setVisibility(View.VISIBLE);
                                tv_preferred_dateandtime2.setVisibility(View.VISIBLE);
                                tv_preferred_dateandtime2.setText("NA");
                                try {
                                    tv_preferred_dateandtime.setText(getMonth(list.get(i).getCallDate().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (list.get(i).getStatus().equalsIgnoreCase("Closed") || list.get(i).getStatus().equalsIgnoreCase("Canceled")){
                                try {
                                    tv_preferred_dateandtime2.setVisibility(View.VISIBLE);
                                    tv_preferred_dateandtime2.setText(getMonth(list.get(i).getCompletionDate().toString().substring(0,10)).replaceAll("-"," "));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }

                    ll_ccr_title.setVisibility(View.GONE);
                    ll_lsr_title.setVisibility(View.GONE);

                    //save in shared preference
                    String json = gson.toJson(list);
                    prefsEditor.putString("Home_List", json);
                    prefsEditor.commit();

                } else {
                    ll_createService.setVisibility(View.VISIBLE);


                }
            }
        }
    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {
        int i;
        try {
            notificationDataList.clear();
            mSavePreference.putString(IpreferenceKey.NOTI_COUNT,"0");
            JSONArray jsonArray = new JSONArray(response);
            NotificationData notificationData = new NotificationData();
            for (i = 0; i < jsonArray.length(); i++) {


                JSONObject notification = jsonArray.getJSONObject(i).getJSONObject("Notification");
                notificationData.title = notification.getString("Title");
                notificationData.description = notification.getString("Description");
                notificationData.photo_url = notification.getString("Icon");
                notificationData.isSeen = jsonArray.getJSONObject(i).getString("IsSeen");
                if (notificationData.isSeen.equalsIgnoreCase("false")) {
                    notificationDataList.add(notificationData);
                }
            }
            if (notificationDataList.size()==0){
                mSavePreference.putString(IpreferenceKey.NOTI_COUNT, "0");

            }else {
                mSavePreference.putString(IpreferenceKey.NOTI_COUNT, String.valueOf(notificationDataList.size()));
            }
            if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT).equalsIgnoreCase("0")){
                tv_count.setVisibility(View.GONE);
            }else {
                tv_count.setVisibility(View.VISIBLE);
                tv_count.setText(mSavePreference.getString(IpreferenceKey.NOTI_COUNT));
                iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
                iv_bell_icon.setVisibility(View.VISIBLE);
            }



        }catch (Exception e){

        }
    }

    @Override
    public void onSuccessSecond(String response) {
        mSavePreference.putString(IpreferenceKey.COMMUNITY_NAME,response.replaceAll("^\"|\"$", ""));
        /*wv_instafeed.reload();*/
        wv_instafeed.getSettings().setJavaScriptEnabled(true);
        wv_instafeed.getSettings().setUseWideViewPort(true);
        wv_instafeed.loadUrl("https://residents.dubaiam.ae/InstagramFeed?communityCode="+mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME));
        if (mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME).equalsIgnoreCase("AKG")){
            mSavePreference.putString(IpreferenceKey.COMMUNITY_NAME,"AlKhailGate");

        }
        tv_comm_name.setText("#"+mSavePreference.getString(IpreferenceKey.COMMUNITY_NAME)+"Life");
        tv_comm_name.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPostSuccess(String response, String sessionId) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void switchFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    private void loadFragment(Fragment fragment,Bundle bundle) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();
    }
    private void loadFragmentNotification(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

    private void setupWebView() {
        wv_instafeed.getSettings().setJavaScriptEnabled(true);
        wv_instafeed.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                wv_instafeed.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
        wv_instafeed.addJavascriptInterface(this, "MyApp");
    }

    @JavascriptInterface
    public void resize(final float height) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wv_instafeed.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (200 * getResources().getDisplayMetrics().density)));
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("")) {
                // Designate Urls that you want to load in WebView still.
                return false;
            }

            // Otherwise, give the default behavior (open in browser)
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://residents.dubaiam.ae/InstagramFeed?communityCode="+"Remraam"));
            startActivity(intent);
            return true;
        }

    }
    private void loadFragments(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void loadFragmentsCR(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void loadFragmentsBundle(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }


}
