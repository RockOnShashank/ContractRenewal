package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.events.BottomNavigationStatus;
import com.residents.dubaiassetmanagement.events.CreateNewMSR;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment implements View.OnClickListener, ResponseCallback {

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
    private RelativeLayout iv_bell_icon;
private BottomNavigationView bottomNavigation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("SCHEDULE");

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        newViewRequest = new ArrayList<>();
mSavePreference = SavePreference.getInstance(getActivity());
        bundle = new Bundle();
        bundle = getArguments();
if (bundle.containsKey("position")) {
    newViewRequest = bundle.getParcelableArrayList("viewRequestList");
    position = bundle.getInt("position");
//Set Fragment Title
    fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
    fragmentTitle.setText("RESCHEDULE");


    bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
    bottomNavigation.setVisibility(View.GONE);

}

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new CreateNewMSR());


/*                Fragment fragment = getFragmentManager().findFragmentById(R.id.home_frame_layout);
if (fragment instanceof CreateNewFragment){
    loadFragment(new ServicesFragment());
}else {
    getFragmentManager().popBackStack();
    EventBus.getDefault().post(new BottomNavigationStatus());
}*/

            }
        });
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


        if(String.valueOf(currentDay).equalsIgnoreCase("31")){
            mPicker.setStartDate(currentDay , currentMonth + 1, currentYear); // set start date for the picker
            mPicker.setEndDate(Integer.parseInt(day), Integer.parseInt(monthNumber), Integer.parseInt(year)); // set end date for the picker
        }else{

            mPicker.setStartDate(currentDay + 1, currentMonth + 1, currentYear); // set start date for the picker
            mPicker.setEndDate(Integer.parseInt(day), Integer.parseInt(monthNumber), Integer.parseInt(year)); // set end date for the picker
        }


    }

    private void getValue() {
        mPicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                if (date != null) {

                     String pattern = "yyyy-MM-dd";
                     //String pattern = "MM/dd/yyyy";
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

                  if (bundle.containsKey("position")) {

                  /*
                        JSONObject jsonObject = new JSONObject();

                        try {

                            jsonObject.put("Object_Type", "SR");
                            jsonObject.put("Object_Reference",mSavePreference.getString(IpreferenceKey.TCODE));
                            jsonObject.put("Transaction_Id", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject jsonObjectPArent = new JSONObject();
                        try {
                            jsonObjectPArent.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
                            jsonObjectPArent.put("Template_Code", newViewRequest.get(position).getTemplateCode());
                            jsonObjectPArent.put("Category", newViewRequest.get(position).getCategory());


                                jsonObjectPArent.put("SubCategory",newViewRequest.get(position).getSubCategory());

                            jsonObjectPArent.put("Problem_Description",newViewRequest.get(position).getProblemDescription());
                            jsonObjectPArent.put("Caller_Name", newViewRequest.get(position).getCallerName());
                            jsonObjectPArent.put("Caller_Number",mSavePreference.getString(IpreferenceKey.PHONE_NUMBER));
                            jsonObjectPArent.put("Common_Area", "");
                            jsonObjectPArent.put("Preferred_Date", tomorrow);
                            jsonObjectPArent.put("Preferred_TimeSlot", time);
                            jsonObjectPArent.put("Related_WOID", "");
                            jsonObjectPArent.put("CreateSRAttachment", jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
*/
                    JSONObject jsonObject = new JSONObject();

                    try {

                        jsonObject.put("SITEID", newViewRequest.get(position).getMaximoSiteID());
                        jsonObject.put("WONUM",newViewRequest.get(position).getMaximoID());
                    //    jsonObject.put("DESCRIPTION", newViewRequest.get(position).getProblemDescription());
                        jsonObject.put("DESCRIPTION", "Your request for rescheduling has been registered for SR "+newViewRequest.get(position).getWOCode());
                        jsonObject.put("YARAPPDATE", tomorrow);
                        jsonObject.put("SCHED_SLOT", time);



                      /*  DESCRIPTION: "The appointment is required" 
                        SCHED_SLOT: "10:00 am - 12:00 pm" 
                        SITEID: "GM" 
                        WONUM: "28410388" 
                        YARAPPDATE: "2018-12-31"*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        new RequestService(getActivity(), ScheduleFragment.this).postRequest("Yardi/CreateUpdateScheduleRequest", jsonObject);
                    } else {

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


                        new RequestService(getActivity(), ScheduleFragment.this).postRequest("Yardi/CreateServiceRequest", jsonObjectPArent);
                    }
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


            if (bundle.containsKey("position")) {


                    String reschedule = jsonObject.getString("ReScheduleSR");
                    if (reschedule.equalsIgnoreCase("null")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();

                    }

                else {


                    JSONObject jsonObject1 = jsonObject.getJSONObject("ReScheduleSR");
                    String creationTime = jsonObject1.getString("creationDateTime");
                    if (creationTime.equalsIgnoreCase("null")) {
                        launchSuccessDialogforError("123");
                    } else {
                        SharedPreferences appSharedPrefs = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                        prefsEditor.putString("MyObject1", "");

                        prefsEditor.putString("CC_List", "");
                        prefsEditor.putString("LS_List", "");


                        prefsEditor.commit();
                        launchSuccessDialogs_Rescedule(newViewRequest.get(position).getWOCode());
                    }
                }

            } else {

                JSONObject jsonObject1 = jsonObject.getJSONObject("CreateServiceRequest");

                String status = jsonObject1.getString("Status");
                String srNumber = jsonObject1.getString("SR_Number");

                if (status.equalsIgnoreCase("Success")) {
                    launchSuccessDialog(srNumber);
                    // loadFragment(new ViewRequestFragment());
                } else {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                }
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
    private void launchSuccessDialogs(String srID) {

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
        srid.setVisibility(View.GONE);
        //srid.setText("SR ID #"+srID);
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


    private void launchSuccessDialogs_Rescedule(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reschedule_pop_up);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        TextView tv_dateandtime = (TextView) dialog.findViewById(R.id.tv_dateandtime);
        tv_dateandtime.setVisibility(View.GONE);
       /* try {
            tv_dateandtime.setText(getMonth(tomorrow).replaceAll("-"," ")+", "+ time);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        srid.setVisibility(View.VISIBLE);
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
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
}
