package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

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
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewRequestLSFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDetailHistoryLC extends Fragment {

    Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private TextView tv_category, tv_id, tv_subCategory, tv_request_status, tv_date, tv_preferred_dateandtime, tv_description, tv_follow_up, tv_cancel, tv_reschedule;
    private TextView fragmentTitle;
    private String raisedDate = null;
    private String currentDate = null;
    private long diffDays;
    private TextView tv_show_follow_comments;
    // private FollowUp followUp;
    //  private ArrayList<MEResWorkorderMemo> meResWorkorderMemos;
    private FollowUpAdapter followUpAdapter;
    public static int p = 0;
    private String date, dates;
    private int isAttachments = 0;
    private TextView tv_attached_pictures;
    private LinearLayout ll_pictures;
    private TextView tv_follow_up_message, tv_reschedule_message, tv_schedule;
    private View view;
    private BottomNavigationView bottomNavigation;
    private RelativeLayout iv_bell_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_request_detail_ls, container, false);
        bundle = new Bundle();
        newViewRequest = new ArrayList<>();
        bundle = getArguments();
        // meResWorkorderMemos = new ArrayList<>();


        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        tv_reschedule_message= (TextView) view.findViewById(R.id.tv_reschedule_message);
        tv_follow_up_message = (TextView) view.findViewById(R.id.tv_follow_up_message);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_schedule  = (TextView) view.findViewById(R.id.tv_schedule);
        tv_show_follow_comments = (TextView) view.findViewById(R.id.tv_show_follow_comments);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_attached_pictures = (TextView) view.findViewById(R.id.tv_attached_pictures);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        ll_pictures = (LinearLayout) view.findViewById(R.id.ll_pictures);
        tv_follow_up = (TextView) view.findViewById(R.id.tv_follow_up);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_reschedule = (TextView) view.findViewById(R.id.tv_reschedule);

        newViewRequest = bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");

if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Closed")){
    tv_schedule.setText("Closed On");
}

        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Canceled")){
            tv_schedule.setText("Canceled On");
        }
        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());

        try {
            date = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_date.setText(date.replaceAll("-", " "));

        if (newViewRequest.get(position).getCompletionDate() != null) {
            try {

                dates = getMonth(newViewRequest.get(position).getCompletionDate().substring(0, 10));
                tv_preferred_dateandtime.setText(dates.replaceAll("-", " "));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tv_preferred_dateandtime.setText("NA");


        }

        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());
        tv_description.setText(newViewRequest.get(position).getProblemDescription());
      /*  ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragments(new HistoryLSFragment());
                //getFragmentManager().popBackStack();
            }
        });
*/

      if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Canceled")){
          tv_request_status.setTextColor(getResources().getColor(R.color.red));
          try {
              tv_preferred_dateandtime.setText(getMonth(newViewRequest.get(position).getCallDate()).substring(0,11).replaceAll("-"," "));
          } catch (ParseException e) {
              e.printStackTrace();
          }
      }
        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Closed")){
            tv_request_status.setTextColor(getResources().getColor(R.color.light_text_color));
            try {
                tv_preferred_dateandtime.setText(getMonth(newViewRequest.get(position).getCallDate()).substring(0,11).replaceAll("-"," "));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragments(new HistoryLSFragment());
                //getFragmentManager().popBackStack();
            }
        });
    }

    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

    private void loadFragments(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
