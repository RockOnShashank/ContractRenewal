package com.residents.dubaiassetmanagement.reopen_service;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.ScheduleFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReopenScheduleFragment extends Fragment implements View.OnClickListener, ResponseCallback {

    private View view;
    private Button bt_confirm;
    private RadioGroup radioGroup;
    private String time=null;
    private String dateSelected= null;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private Bundle bundle;
    private String tomorrow;
    private DayScrollDatePicker mPicker;
    private int currentMonth,currentYear,currentDay;
    private TextView fragmentTitle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private SavePreference mSavePreference;
    private ConstraintLayout cl_parent;
    private  BottomNavigationView bottomNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("SCHEDULE");
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        newViewRequest = new ArrayList<>();
        mSavePreference = SavePreference.getInstance(getActivity());
        bundle = new Bundle();
        bundle = getArguments();
           // newViewRequest = bundle.getParcelableArrayList("viewRequestList");
            //position = bundle.getInt("position");


        bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup) ;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    time = rb.getText().toString();
                    // Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
                if (time != null && tomorrow!=null) {
                    bt_confirm.setEnabled(true);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    bt_confirm.setEnabled(false);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }

            }
        });


        Calendar instance = Calendar.getInstance();
        currentMonth = instance.get(Calendar.MONTH);
        currentYear = instance.get(Calendar.YEAR);
        currentDay = instance.get(Calendar.DATE);
        initViews();
        getValue();



        return view;

    }

    // Initialise views
    private void initViews() {
        mPicker = (DayScrollDatePicker) view.findViewById(R.id.day_date_picker);
        setUpPicker();
    }

    private void setUpPicker() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date d = c.getTime();

        System.out.println("30 days after today is: " + d);

        String day          = (String) android.text.format.DateFormat.format("dd",d); // 20
        String monthString  = (String) android.text.format.DateFormat.format("MMM",  d); // Jun
        String monthNumber  = (String) android.text.format.DateFormat.format("MM",   d); // 06
        String year         = (String) android.text.format.DateFormat.format("yyyy", d);

        mPicker.setStartDate(currentDay+1, currentMonth+1, currentYear); // set start date for the picker
        mPicker.setEndDate(Integer.parseInt(day), Integer.parseInt(monthNumber), Integer.parseInt(year)); // set end date for the picker



    }

    private void getValue() {
        mPicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                if (date != null) {
                    String pattern = "yyyy/MM/dd";
                    String dateInString =new SimpleDateFormat(pattern).format(date);
                    tomorrow=dateInString;


                    if (tomorrow != null && time!=null ) {
                        bt_confirm.setEnabled(true);
                        bt_confirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        bt_confirm.setEnabled(false);
                        bt_confirm.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                    }
                }
            }
        });
    }
    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_confirm:

                if (time == null){
                    Toast.makeText(getActivity(), "Please Select date and time for Scheduling", Toast.LENGTH_SHORT).show();

                }else {
                        JSONObject jsonObject = new JSONObject();

                        try {

                            jsonObject.put("Object_Type", bundle.getString("Object_Type"));
                            jsonObject.put("Object_Reference", bundle.getString("Object_Reference"));
                            jsonObject.put("Transaction_Id", bundle.getString("Transaction_Id"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject jsonObjectPArent = new JSONObject();
                        try {
                            jsonObjectPArent.put("Tenant_Code", bundle.getString("Tenant_Code"));
                            jsonObjectPArent.put("Template_Code", bundle.getString("Template_Code"));
                            jsonObjectPArent.put("Category", bundle.getString("Category"));

                            if (bundle.getString("SubCategory") == null) {
                                jsonObjectPArent.put("SubCategory", "");

                            } else {
                                jsonObjectPArent.put("SubCategory", bundle.getString("SubCategory"));
                            }
                            jsonObjectPArent.put("Problem_Description", bundle.getString("Problem_Description"));
                            jsonObjectPArent.put("Caller_Name", bundle.getString("Caller_Name"));
                            jsonObjectPArent.put("Caller_Number", bundle.getString("Caller_Number"));
                            jsonObjectPArent.put("Common_Area", "");
                            jsonObjectPArent.put("Preferred_Date", tomorrow);
                            jsonObjectPArent.put("Preferred_TimeSlot", time);
                            jsonObjectPArent.put("Related_WOID", bundle.getString("Related_WOID"));
                            jsonObjectPArent.put("CreateSRAttachment", jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        new RequestService(getActivity(), ReopenScheduleFragment.this).postRequest("Yardi/CreateServiceRequest", jsonObjectPArent);

                }
                break;
        }
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



                JSONObject jsonObject1 = jsonObject.getJSONObject("CreateServiceRequest");

                String status = jsonObject1.getString("Status");
                String srNumber = jsonObject1.getString("SR_Number");

                if (status.equalsIgnoreCase("Success")) {
                    launchSuccessDialog(srNumber);
                    // loadFragment(new ViewRequestFragment());
                } else {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                }





        } catch(JSONException e){
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
    private void launchSuccessDialog(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        if (bundle.containsKey("position")){
            tv_message.setText("You have successfully reschedule the request.");
        }else {
            tv_message.setText("You have successfully raised the request.");

        }
        srid.setText("SR ID #"+srID);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ViewRequestFragment());
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    private void launchSuccessDialogforError(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);
        ImageView iv_title = (ImageView) dialog.findViewById(R.id.iv_title);
        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        iv_title.setVisibility(View.GONE);
        tv_message.setText("Oops! Something went wrong. Please try after some time.");


        srid.setText("SR ID #"+srID);
        srid.setVisibility(View.GONE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ViewRequestFragment());

                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
}
