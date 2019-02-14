package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.LoginActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.ScheduleFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.SeeAllAdapter;
import com.residents.dubaiassetmanagement.ServiceRequest.followup_model.FollowUps;
import com.residents.dubaiassetmanagement.ServiceRequest.followup_model.MaximoMemo;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ViewRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up.FollowUp;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up.MEResWorkorderMemo;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ViewRequestDetailFragment extends Fragment implements View.OnClickListener, ResponseCallback, FollowUpAdapter.ItemClickListener {
    private View view;
    Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private TextView tv_category, tv_id, tv_subCategory, tv_request_status, tv_date, tv_preferred_dateandtime, tv_description, tv_follow_up, tv_cancel, tv_reschedule;
    private TextView fragmentTitle;
    private String raisedDate = null;
    private String currentDate = null;
    private long diffDays;
    private TextView tv_show_follow_comments;
    private FollowUp followUp;
    private ArrayList<MEResWorkorderMemo> meResWorkorderMemos;
    private FollowUpAdapter followUpAdapter;
    public static int p = 0;
    private String date, dates;
    private int isAttachments = 0;
    private TextView tv_attached_pictures;
    private LinearLayout ll_pictures;
    private TextView tv_follow_up_message, tv_reschedule_message, tv_schedule, tv_new_srid;
    private LinearLayout ll_linksrid;
private ImageView iv_1,iv_2,iv_3;
    ViewGroup root;
    Bitmap decodedByte1, decodedByte2, decodedByte3;
RelativeLayout iv_bell_icon;

private BottomNavigationView bottomNavigation;
private FollowUps followUps;
private ArrayList<MaximoMemo> maximoMemoArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_request_detail, container, false);

        bundle = new Bundle();
        newViewRequest = new ArrayList<>();
        bundle = getArguments();
        meResWorkorderMemos = new ArrayList<>();

        maximoMemoArrayList = new ArrayList<>();
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);


        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);

        tv_reschedule_message= (TextView) view.findViewById(R.id.tv_reschedule_message);
                tv_follow_up_message = (TextView) view.findViewById(R.id.tv_follow_up_message);


        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
        ll_linksrid = (LinearLayout) view.findViewById(R.id.ll_linksrid);
        tv_new_srid = (TextView) view.findViewById(R.id.tv_new_srid);
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
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
loadFragmentafterCancel(new ViewRequestFragment());
                //getFragmentManager().popBackStack();

            }
        });
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();


        if (!appSharedPrefs.getString("MyObject", "").equalsIgnoreCase("") && !bundle.containsKey("isViewRequest")) {

            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<List<ServiceRequest>>(){}.getType();
            newViewRequest = gson.fromJson(json, type);
            position = appSharedPrefs.getInt("pos",0);

            Log.e("dafd",""+newViewRequest);

        }else {


            newViewRequest = bundle.getParcelableArrayList("viewRequestList");
            position = bundle.getInt("position");


            String json = gson.toJson(newViewRequest);
            prefsEditor.putString("MyObject", json);
            prefsEditor.putInt("pos", position);
            prefsEditor.commit();

        }

        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_1);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_2);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_3);

        new RequestService(getActivity(), ViewRequestDetailFragment.this).setArgumentsAttachment("ServiceRequest/Attachments/" + newViewRequest.get(position).getWOCode());

        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());

        try {
            date = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_date.setText(date.replaceAll("-", " "));

        if (newViewRequest.get(position).getPreferredDate() != null) {
            try {

                dates = getMonth(newViewRequest.get(position).getPreferredDate().substring(0, 10));
                if (dates != null) {
                    tv_preferred_dateandtime.setText(dates.replaceAll("-", " ") + ", " + newViewRequest.get(position).getPreferredTimeSlot());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tv_preferred_dateandtime.setText("Awaiting");
        }

        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());
        tv_description.setText(newViewRequest.get(position).getProblemDescription());
        raisedDate = newViewRequest.get(position).getCallDate();
        raisedDate = raisedDate.replace("T", " ");
        raisedDate = raisedDate.replace("Z", "");
        raisedDate = raisedDate.replaceAll("-", "/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(sdf.format(new Date()));
        currentDate = sdf.format(new Date());
        currentDate = currentDate.replace("T", " ");
        currentDate = currentDate.replace("Z", "");

/////
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(raisedDate);
            d2 = format.parse(currentDate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (newViewRequest.get(position).getViewMore().contains("FOLLOW UP")) {
            tv_follow_up.setEnabled(true);
            tv_follow_up.setTextColor(getResources().getColor(R.color.dark_blue));
            tv_follow_up_message.setVisibility(View.GONE);
        } else {
            tv_follow_up_message.setVisibility(View.VISIBLE);
            tv_follow_up.setEnabled(false);
            tv_follow_up.setTextColor(getResources().getColor(R.color.disabled));
        }


        if (newViewRequest.get(position).getViewMore().contains("RESCHEDULE")) {

            tv_reschedule.setTextColor(getResources().getColor(R.color.dark_blue));

            tv_reschedule.setEnabled(true);

        } else {
            tv_reschedule.setEnabled(false);
            tv_reschedule.setTextColor(getResources().getColor(R.color.disabled));
        }

        if (newViewRequest.get(position).getIsCancellable() != null) {
            if (newViewRequest.get(position).getIsCancellable() == true) {
                tv_reschedule_message.setVisibility(View.GONE);
                tv_cancel.setEnabled(true);
                tv_cancel.setTextColor(getResources().getColor(R.color.dark_blue));

            } else {
                tv_reschedule_message.setVisibility(View.VISIBLE);

                tv_cancel.setTextColor(getResources().getColor(R.color.disabled));
                tv_cancel.setEnabled(false);
            }
        }


        tv_show_follow_comments.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_reschedule.setOnClickListener(this);
        tv_follow_up.setOnClickListener(this);

        if (newViewRequest.get(position).getIsEmergency()!=null) {
            if (newViewRequest.get(position).getIsEmergency()) {

                tv_schedule.setVisibility(View.INVISIBLE);
                tv_preferred_dateandtime.setVisibility(View.INVISIBLE);
            } else {
                tv_preferred_dateandtime.setVisibility(View.VISIBLE);

                tv_schedule.setVisibility(View.GONE);

            }
        }

        if (newViewRequest.get(position).getRelatedWo().equalsIgnoreCase("0")){
            ll_linksrid.setVisibility(View.GONE);
        }else {
            ll_linksrid.setVisibility(View.VISIBLE);
            tv_new_srid.setText("New SR ID #"+newViewRequest.get(position).getWOCode());
            tv_id.setText("SR ID #"+newViewRequest.get(position).getRelatedWo());
        }

        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Canceled")){
            tv_request_status.setTextColor(getResources().getColor(R.color.red));
        }else if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Closed")){
            tv_request_status.setTextColor(getResources().getColor(R.color.vertical_line));

        }else if (newViewRequest.get(position).getStatus().equalsIgnoreCase("In Progress")){
            tv_request_status.setTextColor(getResources().getColor(R.color.green_color));

        }



        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (decodedByte1!=null) {
                    showPopUp_1();
                }
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (decodedByte2!=null) {
                    showPopUp_2();
                }
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (decodedByte3!=null)
                {
                    showPopUp_3();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.tv_show_follow_comments:
                isAttachments = 1;
                //new RequestService(getActivity(), ViewRequestDetailFragment.this).setArguments("ServiceRequest/MaximoMemo/" + newViewRequest.get(position).getMaximoID());

                new RequestService(getActivity(), ViewRequestDetailFragment.this).setArguments("ServiceRequest/MaximoMemo/" + newViewRequest.get(position).getMaximoID());

                break;
            case R.id.tv_follow_up:
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("viewRequestList", newViewRequest);
                bundle1.putInt("position", position);
                loadFragmentReschedule(new FollowUpFragment(), bundle1);
                break;

            case R.id.tv_reschedule:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("viewRequestList", newViewRequest);
                bundle.putInt("position", position);
                loadFragmentReschedule(new ScheduleFragment(), bundle);
                break;

            case R.id.tv_cancel:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("")
                        .setMessage("Are you sure you want to cancel?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();

                                new RequestService(getActivity(), ViewRequestDetailFragment.this).putRequest("ServiceRequest/Cancel/serviceId=" + newViewRequest.get(position).getWOCode()+"?maximoID="+newViewRequest.get(position).getMaximoID()+"&templateCode="+newViewRequest.get(position).getTemplateCode().replaceAll(" ","")+"&maximoSiteId="+newViewRequest.get(position).getMaximoSiteID(), "");
                            }
                        }).show();

                break;
        }

    }

    @Override
    public void onSuccess(String response) {

        if (response != null) {

            if (isAttachments == 1) {
                maximoMemoArrayList.clear();

                try {
                    JSONObject responseObject = new JSONObject(response);
                    String srMemo = responseObject.getString("MaximoMemo");
                    if (srMemo.equalsIgnoreCase("null")) {
                        Toast.makeText(getActivity(), "There are no follow-up comments", Toast.LENGTH_LONG).show();
                    }else
                    {

                    Gson gson = new GsonBuilder().serializeNulls().create();
                    followUps = gson.fromJson(response, FollowUps.class);

                    maximoMemoArrayList.addAll(followUps.getMaximoMemo());
                        tv_follow_up.setEnabled(false);
                        tv_follow_up.setTextColor(getResources().getColor(R.color.disabled));
                        tv_follow_up_message.setVisibility(View.VISIBLE);
                        launchSuccessDialog();
                        isAttachments = 0;
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




              /*  try {
                    meResWorkorderMemos.clear();
                    JSONObject responseObject = new JSONObject(response);
                    String srMemo = responseObject.getString("SRMemo");

                    if (srMemo.equalsIgnoreCase("null")) {
                        Toast.makeText(getActivity(), "There are no follow-up comments", Toast.LENGTH_LONG).show();
                    } else {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        followUp = gson.fromJson(response, FollowUp.class);
                        meResWorkorderMemos.addAll(followUp.getSRMemo().getMEResWorkorderMemo());
                        tv_follow_up.setEnabled(false);
                        tv_follow_up.setTextColor(getResources().getColor(R.color.disabled));
                        tv_follow_up_message.setVisibility(View.VISIBLE);
                        launchSuccessDialog();
                        isAttachments = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            } else {
                iv_1.setImageDrawable(null);
                iv_2.setImageDrawable(null);
                iv_3.setImageDrawable(null);

                try {
                    JSONObject responseObjects = new JSONObject(response);
                    if (responseObjects.getJSONArray("Attachments").isNull(0)){
                        tv_attached_pictures.setVisibility(View.GONE);
                        ll_pictures.setVisibility(View.GONE);
                    }else {
                        tv_attached_pictures.setVisibility(View.VISIBLE);
                        ll_pictures.setVisibility(View.VISIBLE);
                        if (!responseObjects.getJSONArray("Attachments").isNull(0)) {
                            JSONArray jsonObject = responseObjects.getJSONArray("Attachments");
                            JSONObject jsonObject2 = jsonObject.getJSONObject(0);
                            String base64 = jsonObject2.getString("FileBase64String");
                            if (!base64.equalsIgnoreCase("")) {

                                byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                decodedByte1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                iv_1.setImageBitmap(decodedByte1);
                            }
                        }


                        if (!responseObjects.getJSONArray("Attachments").isNull(1)) {
                            JSONArray jsonObject = responseObjects.getJSONArray("Attachments");
                            JSONObject jsonObject2 = jsonObject.getJSONObject(1);
                            String base64 = jsonObject2.getString("FileBase64String");
                            if (!base64.equalsIgnoreCase("")) {

                                byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                decodedByte2 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                iv_2.setImageBitmap(decodedByte2);
                            }
                        }

                        if (!responseObjects.getJSONArray("Attachments").isNull(2)) {
                            JSONArray jsonObject = responseObjects.getJSONArray("Attachments");
                            JSONObject jsonObject2 = jsonObject.getJSONObject(2);
                            String base64 = jsonObject2.getString("FileBase64String");
                            if (!base64.equalsIgnoreCase("")) {

                                byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                decodedByte3 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                iv_3.setImageBitmap(decodedByte3);
                            }
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


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
        try {
            JSONObject jsonObject = new JSONObject(response);//{"CancelSR":[{"Status":"Success"}]}
JSONArray jsonObject1 = jsonObject.getJSONArray("CancelSR");

JSONObject jsonObject2 = jsonObject1.getJSONObject(0);
String status = jsonObject2.getString("Status");
if (status.equalsIgnoreCase("Success")){
    launchSuccessDialogforCancelResponse(newViewRequest.get(position).getWOCode());

}
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void loadFragment(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }

    private void loadFragmentReschedule(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void switchFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void launchSuccessDialog() {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comments_follow_up);
        dialog.setCanceledOnTouchOutside(true);
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
        RecyclerView rv_followup = (RecyclerView) dialog.findViewById(R.id.rv_followup);
        rv_followup.setLayoutManager(new LinearLayoutManager(getActivity()));
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        followUpAdapter = new FollowUpAdapter(getActivity(), maximoMemoArrayList);
        followUpAdapter.setClickListener(this);
        rv_followup.setAdapter(followUpAdapter);


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    @Override
    public void onItemClickSeeAll(View view, int position, int catId) {

    }

    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
    private void loadFragments(Fragment fragment,Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }

    private void showPopUp_1() {
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(decodedByte1);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }

    private void showPopUp_2() {
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(decodedByte2);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);
                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }
    private void showPopUp_3() {
        applyDim(root, 0.5f);
        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(decodedByte3);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount) {
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
    private void launchSuccessDialogforCancelResponse(String srId) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MyObject1", "");
        prefsEditor.commit();
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);
        ImageView iv_title = (ImageView) dialog.findViewById(R.id.iv_title);
        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        iv_title.setVisibility(View.VISIBLE);
        tv_message.setText("Your cancellation request has been received. Status update might take a few minutes.");


        srid.setText("SR ID #"+srId);
        srid.setVisibility(View.VISIBLE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


/*
                Bundle bundle = new Bundle();
                bundle.putString("value","1");
                loadFragments(new HistoryFragment(),bundle);*/
                loadFragmentafterCancel(new ViewRequestFragment());

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
