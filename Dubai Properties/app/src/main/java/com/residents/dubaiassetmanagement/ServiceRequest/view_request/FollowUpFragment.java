package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.ScheduleFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FollowUpFragment extends Fragment implements ResponseCallback {

private View view;
private TextView fragmentTitle;
    private TextView tv_category,tv_id,tv_subCategory,tv_request_status,tv_date,tv_preferred_dateandtime,tv_description,tv_follow_up,tv_cancel, tv_reschedule;
private EditText et_comments;
private Button bt_submit;
private Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    private int position;
private String formattedDate,date,dates;
    JSONObject jsonObject;
    FollowedUp followedUp;
    RelativeLayout iv_bell_icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_follow_up, container, false);
        newViewRequest = new ArrayList<>();

        bundle = new Bundle();
        bundle = getArguments();
        if (bundle.containsKey("position")) {
            newViewRequest = bundle.getParcelableArrayList("viewRequestList");
            position = bundle.getInt("position");

        }
//Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("FOLLOW UP");

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
et_comments = (EditText) view.findViewById(R.id.et_comment);
        et_comments.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (et_comments.getText().toString().length() > 0 ) {
                    bt_submit.setEnabled(true);
                    bt_submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    bt_submit.setEnabled(false);
                    bt_submit.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }

                if (s.toString().length()>0) {
                    String last = s.toString().substring(s.toString().length() - 1);

                    if (last.equalsIgnoreCase(">")) {
                        String out = s.toString().replaceAll(">", "greater than");
                        et_comments.setText(out);
                    }
                    if (last.equalsIgnoreCase("<")) {
                        String out = s.toString().replaceAll("<", "lesser than");
                        et_comments.setText(out);
                    }
                    if (last.equalsIgnoreCase("&")) {
                        String out = s.toString().replaceAll("&", "and");
                        et_comments.setText(out);
                    }
                    if (last.equalsIgnoreCase(",")) {
                        String out = s.toString().replaceAll(",", "comma");
                        et_comments.setText(out);
                    }
                    if (last.equalsIgnoreCase("$")) {
                        String out =getDescription(s.toString());
                        et_comments.setText(out);
                    }

                }
                et_comments.setSelection(et_comments.getText().length());

            }
        });
bt_submit = (Button) view.findViewById(R.id.bt_submit);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);



        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        tv_description = (TextView) view.findViewById(R.id.tv_description);


        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());

        try {
            date = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_date.setText(date.replaceAll("-", " "));

        if (newViewRequest.get(position).getPreferredDate()!=null){
        try {
            dates = getMonth(newViewRequest.get(position).getPreferredDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();

            if (newViewRequest.get(position).getPreferredTimeSlot() != null) {
                //date is null
                tv_preferred_dateandtime.setText(dates.replaceAll("-", " ") + "," + newViewRequest.get(position).getPreferredTimeSlot().replaceAll(" ", ""));
            } else {
                tv_preferred_dateandtime.setText(dates.replaceAll("-", " "));
            }
        }
        }else {
            tv_preferred_dateandtime.setText("Awaiting");

        }
        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());

        tv_request_status.setText(newViewRequest.get(position).getStatus());
        tv_description.setText(newViewRequest.get(position).getProblemDescription());



        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         formattedDate = df.format(c);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (et_comments.getText().toString().isEmpty()){
    return;

}else {


    try {
        jsonObject = new JSONObject();
        jsonObject.put("SITEID", newViewRequest.get(position).getMaximoSiteID());
        jsonObject.put("WONUM",newViewRequest.get(position).getMaximoID());
        jsonObject.put("DESCRIPTION", et_comments.getText().toString());//yyyy-mm-dd
        jsonObject.put("YARAPPDATE", "");
        jsonObject.put("SCHED_SLOT", "");

    } catch (JSONException e) {
        e.printStackTrace();
    }
    new RequestService(getActivity(), FollowUpFragment.this).postRequest("ServiceRequest/CreateFollowUp", jsonObject);

 /*   try {
         jsonObject = new JSONObject();
        jsonObject.put("Record_Code", newViewRequest.get(position).getWOCode());
        jsonObject.put("Record_Type","25");
        jsonObject.put("DateTime", formattedDate);//yyyy-mm-dd
        jsonObject.put("Notes", et_comments.getText().toString());

    } catch (JSONException e) {
        e.printStackTrace();
    }
    new RequestService(getActivity(), FollowUpFragment.this).postRequest("Yardi/CreateFollowSR", jsonObject);*/

}
            }
        });
       ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                loadFragmentafterCancel(new ViewRequestDetailFragment(),bundle);
                //getFragmentManager().popBackStack();

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            followedUp = (FollowedUp) context;
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
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONObject jsonObject1= jsonObject.getJSONObject("ReScheduleSR");
            String creationTime = jsonObject1.getString("creationDateTime");
            if (creationTime.equalsIgnoreCase("null")){

            }else {
                followedUp.message("disable");
                launchSuccessDialog("You have successfully followed up on the request!");
            }

/*
            String status = jsonObject.getString("Status");
            if (status.equalsIgnoreCase("Success")){
            followedUp.message("disable");
                launchSuccessDialog("You have successfully followed up on the request!");
          //      loadFragment(new ViewRequestFragment());

            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void loadFragment(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void launchSuccessDialog(String message) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        srid.setVisibility(View.GONE);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        tv_message.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  getFragmentManager().popBackStack();
                //Set Fragment Title
                fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
                fragmentTitle.setText("REQUEST DETAILS");*/
                Bundle bundle = new Bundle();
                loadFragmentafterCancel(new ViewRequestDetailFragment(),bundle);

                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    public interface FollowedUp{
        public void message(String msg);
    }
    @Override
    public void onDetach() {
        followedUp = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }

    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
    private void loadFragmentafterCancel(Fragment fragment,Bundle bundle) {

        //load fragment
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        fragment.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
    }
}
