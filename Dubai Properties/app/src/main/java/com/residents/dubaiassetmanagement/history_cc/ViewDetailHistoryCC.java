package com.residents.dubaiassetmanagement.history_cc;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FollowUpAdapter;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.home_screen.HomeViewDetailCCFragment;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDetailHistoryCC extends Fragment implements ResponseCallback {

    Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private TextView tv_category, tv_id, tv_subCategory,tv_tag_location,tv_tag_location_text, tv_request_status, tv_date, tv_preferred_dateandtime, tv_description, tv_follow_up, tv_cancel, tv_reschedule;
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
private String description, separated,location;
private ImageView iv_loc;
private ImageView iv_1,iv_2,iv_3;
    Bitmap decodedByte1, decodedByte2, decodedByte3;

    ViewGroup root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_request_detail_cc, container, false);
        bundle = new Bundle();
        newViewRequest = new ArrayList<>();
        bundle = getArguments();
       // meResWorkorderMemos = new ArrayList<>();


        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");


        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
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
        tv_tag_location= (TextView) view.findViewById(R.id.tv_tag_location);

        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);

        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_1);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_2);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_3);


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
                if (decodedByte3!=null) {
                    showPopUp_3();
                }
            }
        });
        iv_loc = (ImageView) view.findViewById(R.id.iv_loc);
                tv_tag_location_text= (TextView) view.findViewById(R.id.tv_tag_location_text);
        newViewRequest = bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");
        new RequestService(getActivity(), ViewDetailHistoryCC.this).setArgumentsAttachment("ServiceRequest/Attachments/" + newViewRequest.get(position).getWOCode());


        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());

        try {
            date = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_date.setText(date.replaceAll("-", " "));

        if (newViewRequest.get(position).getCallDate() != null) {
            try {
                dates = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
             tv_preferred_dateandtime.setText(dates.replaceAll("-", " "));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tv_preferred_dateandtime.setText("Awaiting");


        }

        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());
        tv_description.setText(newViewRequest.get(position).getProblemDescription());

        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Closed")){
            tv_schedule.setText("Closed On");
        }

        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Canceled")){
            tv_schedule.setText("Canceled On");
            tv_request_status.setTextColor(getResources().getColor(R.color.red));
        }


        description = newViewRequest.get(position).getProblemDescription();

        separated = newViewRequest.get(position).getProblemDescription().substring(newViewRequest.get(position).getProblemDescription().indexOf("$") + 1);;
        location = separated;

        description = description.replaceAll(location,"");

        String new_des = description.replace(location,"");
        description = description.replaceAll("$","");
        if (!new_des.equalsIgnoreCase("")) {
            tv_description.setText(getDescription(new_des));
        }

        if (!location.equalsIgnoreCase("")){
            tv_tag_location.setVisibility(View.VISIBLE);
            tv_tag_location_text.setVisibility(View.VISIBLE);
            if (!location.equalsIgnoreCase(""))
            {
                tv_tag_location_text.setText(getDescription(location));
                iv_loc.setVisibility(View.VISIBLE);

            }

        }else {
            iv_loc.setVisibility(View.GONE);
            tv_tag_location.setVisibility(View.GONE);
            tv_tag_location_text.setVisibility(View.GONE);
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
                loadFragmentafterCancel(new HistoryCCFragment());
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

    @Override
    public void onSuccess(String response) {

        try {

            iv_1.setImageDrawable(null);
            iv_2.setImageDrawable(null);
            iv_3.setImageDrawable(null);
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
}
