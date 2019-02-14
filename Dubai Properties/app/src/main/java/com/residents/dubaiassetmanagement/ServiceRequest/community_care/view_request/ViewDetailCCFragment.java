package com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestDetailFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up.FollowUp;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up.MEResWorkorderMemo;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.history_cc.HistoryCCFragment;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDetailCCFragment extends Fragment implements ResponseCallback {
    Bundle bundle;
    ArrayList<ServiceRequest> newViewRequest;
    int position;
    private TextView fragmentTitle;
    private View view;
    private String location;
    String separated,description,dates;
    private ImageView iv_loc;
    String new_des;

    private ArrayList<MEResWorkorderMemo> meResWorkorderMemos;


    ViewGroup root;

    private int isAttachments = 0;
    private TextView tv_attached_pictures;
    private LinearLayout ll_pictures,ll_child_view;

    private ImageView iv_1,iv_2,iv_3;
BottomNavigationView bottomNavigation;
    RelativeLayout iv_bell_icon;
    Bitmap decodedByte1, decodedByte2, decodedByte3;

    private TextView tv_category,tv_id,tv_subCategory,tv_status,tv_request_status,tv_date,tv_preferred_dateandtime,tv_description,tv_follow_up,tv_cancel, tv_reschedule;
private TextView tv_tag_location_text,tv_tag_location;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_vd_cc, container, false);
        bundle = new Bundle();
        newViewRequest = new ArrayList<>();
        bundle = getArguments();
        meResWorkorderMemos = new ArrayList<>();

        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();

        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        tv_attached_pictures = (TextView) view.findViewById(R.id.tv_attached_pictures);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_tag_location_text= (TextView) view.findViewById(R.id.tv_tag_location_text);
                tv_tag_location = (TextView) view.findViewById(R.id.tv_tag_location);
        ll_pictures = (LinearLayout) view.findViewById(R.id.ll_pictures);
        ll_child_view = (LinearLayout) view.findViewById(R.id.ll_child_view);
        tv_follow_up = (TextView) view.findViewById(R.id.tv_follow_up);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_reschedule = (TextView) view.findViewById(R.id.tv_reschedule);
        tv_status = (TextView) view.findViewById(R.id.tv_status);

        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
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
        newViewRequest= bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");


        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentafterCancel(new ViewRequestCCFragment());
                //getFragmentManager().popBackStack();

            }
        });
        new RequestService(getActivity(), ViewDetailCCFragment.this).setArgumentsAttachment("ServiceRequest/Attachments/" + newViewRequest.get(position).getWOCode());

        try {
            dates = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_date.setText(dates.replaceAll("-"," "));
  //      tv_preferred_dateandtime.setText(newViewRequest.get(position).getCallDate().substring(0, 10));
        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());
if (newViewRequest.get(position).getProblemDescription() != null){
        description = newViewRequest.get(position).getProblemDescription();

        if (description.contains("$")) {
            try {
                separated = newViewRequest.get(position).getProblemDescription().substring(newViewRequest.get(position).getProblemDescription().indexOf("$") + 1);

                location = separated;
            } catch (Exception e) {

            }

            if (description != null) {
                description = description.replaceAll(location, "");

                new_des = description.replace(location, "");
                description = description.replaceAll("$", "");
            }
            if (!new_des.equalsIgnoreCase("")) {
                tv_description.setText(getDescription(new_des));
            }

            if (!location.equalsIgnoreCase("")) {
                tv_tag_location.setVisibility(View.VISIBLE);
                tv_tag_location_text.setVisibility(View.VISIBLE);
                if (location.contains("$")) {
                    tv_tag_location_text.setText(getDescription(location));

                } else {
                    tv_tag_location_text.setText(location);

                }
                iv_loc.setVisibility(View.VISIBLE);

            } else {
                iv_loc.setVisibility(View.GONE);
                tv_tag_location.setVisibility(View.GONE);
                tv_tag_location_text.setVisibility(View.GONE);
            }
        }else {
            iv_loc.setVisibility(View.GONE);
            tv_tag_location.setVisibility(View.GONE);
            tv_tag_location_text.setVisibility(View.GONE);
            tv_description.setText(description);

        }}else {
    iv_loc.setVisibility(View.GONE);
    tv_tag_location.setVisibility(View.GONE);
    tv_tag_location_text.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle( "" )
                        .setMessage("Are you sure you want to cancel?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                new RequestService(getActivity(),ViewDetailCCFragment.this).putRequest("ServiceRequest/Cancel/"+newViewRequest.get(position).getWOCode()+"?maximoID="+newViewRequest.get(position).getMaximoID()+"&templateCode="+newViewRequest.get(position).getTemplateCode()+"&maximoSiteId="+newViewRequest.get(position).getMaximoSiteID(),"");
                            }
                        }).show();

            }
        });




if (newViewRequest.get(position).getIsCancellable()){
    ll_child_view.setVisibility(View.VISIBLE);

    tv_cancel.setVisibility(View.VISIBLE);
    tv_cancel.setEnabled(true);
    tv_cancel.setTextColor(getResources().getColor(R.color.dark_blue));

}else {
    ll_child_view.setVisibility(View.GONE);
    tv_cancel.setVisibility(View.GONE);
    tv_cancel.setTextColor(getResources().getColor(R.color.disabled));
    tv_cancel.setEnabled(false);

}


        tv_preferred_dateandtime.setVisibility(View.INVISIBLE);
        tv_status.setVisibility(View.INVISIBLE);
return view;
    }

    @Override
    public void onSuccess(String response) {
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
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MyObject1", "");

        prefsEditor.putString("CC_List", "");
        prefsEditor.putString("LS_List", "");
        prefsEditor.commit();
        Bundle bundle = new Bundle();
        bundle.putString("value","2");
        loadFragment(new ViewRequestCCFragment(),bundle);

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
    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
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
}
