package com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewDetailCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDetailLSFragment extends Fragment implements ResponseCallback {
    Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private TextView fragmentTitle;
    private View view;
    private String location;
    String separated,description,dates;
    private LinearLayout ll_child_view;
    private BottomNavigationView bottomNavigation;
    RelativeLayout iv_bell_icon;

    private TextView tv_category,tv_id,tv_subCategory,tv_request_status,tv_date,tv_preferred_dateandtime,tv_description,tv_follow_up,tv_cancel, tv_reschedule;
    private TextView tv_tag_location_text,tv_tag_location;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_vd_ls, container, false);
        bundle = new Bundle();
        newViewRequest = new ArrayList<>();
        bundle = getArguments();

        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");


        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        ll_child_view = (LinearLayout) view.findViewById(R.id.ll_child_view);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_tag_location_text= (TextView) view.findViewById(R.id.tv_tag_location_text);
        tv_tag_location = (TextView) view.findViewById(R.id.tv_tag_location);
        tv_follow_up = (TextView) view.findViewById(R.id.tv_follow_up);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_reschedule = (TextView) view.findViewById(R.id.tv_reschedule);

        newViewRequest= bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");

        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentafterCancel(new ViewRequestLSFragment());
                //getFragmentManager().popBackStack();

            }
        });
        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());

        try {
            dates = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_date.setText(dates.replaceAll("-"," "));
     //   tv_preferred_dateandtime.setText(newViewRequest.get(position).getCallDate().substring(0, 10));
        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());

        description = newViewRequest.get(position).getProblemDescription();
/*
        separated = newViewRequest.get(position).getProblemDescription().substring(newViewRequest.get(position).getProblemDescription().lastIndexOf("$") + 1);;
        location = separated;

        description = description.replaceAll(location,"");
        description = description.replaceAll("$","");

        String descriptions = description.replaceAll("$","");*/

        tv_description.setText(description);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle( "" )
                        .setMessage("Are you sure you want to cancel?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                new RequestService(getActivity(),ViewDetailLSFragment.this).putRequest("ServiceRequest/Cancel/serviceId="+newViewRequest.get(position).getWOCode(),"");
                            }
                        }).show();

            }
        });

        if (newViewRequest.get(position).getIsCancellable()){
            ll_child_view.setVisibility(View.VISIBLE);

            tv_cancel.setEnabled(true);
            tv_cancel.setTextColor(getResources().getColor(R.color.dark_blue));

        }else {
            ll_child_view.setVisibility(View.GONE);
            tv_cancel.setTextColor(getResources().getColor(R.color.disabled));
            tv_cancel.setEnabled(false);

        }



        return view;
    }

    @Override
    public void onSuccess(String response) {

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
        Bundle bundle = new Bundle();
        bundle.putString("value","2");
        loadFragment(new HistoryFragment(),bundle);

    }

    private void switchFragment(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadFragment(Fragment fragment,Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
    }
    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
