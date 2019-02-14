package com.residents.dubaiassetmanagement.home_screen;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.home_screen.model_service_home.HomeReopenServiceFragment;
import com.residents.dubaiassetmanagement.home_screen.model_service_home.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.reopen_service.ReopenServiceFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFeedbackFragment extends Fragment implements ResponseCallback {

    private View view;
    private TextView fragmentTitle;
    private TextView tv_category,tv_id,tv_subCategory,tv_request_status,tv_date,tv_preferred_dateandtime,tv_description,tv_follow_up,tv_cancel, tv_reschedule;
    ArrayList<ServiceRequest> newViewRequest;
    Bundle bundle;
    private int position;
    private long diffDays;

    private String date, dates;
    private String raisedDate = null;
    private String currentDate = null;
    Button bt_submit;
    private ImageView iv_1,iv_2,iv_3;
    private int isAttachments = 0;
    private TextView tv_attached_pictures,tv_status;
    private LinearLayout ll_pictures;
    RelativeLayout iv_bell_icon;
    ViewGroup root;
    BottomNavigationView bottomNavigation;
    Bitmap decodedByte1, decodedByte2, decodedByte3;
    private SavePreference mSavePreference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_feedback, container, false);

        bundle = new Bundle();
        mSavePreference = SavePreference.getInstance(getActivity());
        newViewRequest = new ArrayList<>();
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentafterCancel(new HomeFragment());
                //  getFragmentManager().popBackStack();

            }
        });

        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
        bundle = getArguments();
        newViewRequest= bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REQUEST DETAILS");
        new RequestService(getActivity(), HomeFeedbackFragment.this).setArgumentsforHome("ServiceRequest/" + newViewRequest.get(position).getWOCode().replaceAll(" ","")+"?tCode="+mSavePreference.getString(IpreferenceKey.TCODE));



        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);


        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        tv_category.setText(newViewRequest.get(position).getCategory());
        tv_subCategory.setText(newViewRequest.get(position).getSubCategory());
        tv_attached_pictures = (TextView) view.findViewById(R.id.tv_attached_pictures);
        ll_pictures = (LinearLayout) view.findViewById(R.id.ll_pictures);
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp_1();
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp_2();
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp_3();
            }
        });
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_1);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_2);
        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(iv_3);
        try {
            dates = getMonth(newViewRequest.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_date.setText(dates.replaceAll("-"," "));


        if (newViewRequest.get(position).getCompletionDate()!=null) {
            try {
                date = getMonth(newViewRequest.get(position).getCompletionDate().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_preferred_dateandtime.setText(date.replaceAll("-", " "));
        }

        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Canceled")){
            tv_status.setText("Canceled On");
            tv_request_status.setTextColor(getResources().getColor(R.color.red));
        }
        if (newViewRequest.get(position).getStatus().equalsIgnoreCase("Closed")){
            tv_status.setText("Closed On");
            tv_request_status.setTextColor(getResources().getColor(R.color.light_text_color));

        }
        tv_id.setText("SR ID #"+newViewRequest.get(position).getWOCode());
        tv_request_status.setText(newViewRequest.get(position).getStatus());
        tv_description.setText(newViewRequest.get(position).getProblemDescription());

        new RequestService(getActivity(), HomeFeedbackFragment.this).setArgumentsAttachment("ServiceRequest/Attachments/" + newViewRequest.get(position).getWOCode());

        //Reopen Scenario

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
        if (diffDays == 6 || diffDays > 6)
        {
            bt_submit.setEnabled(false);
            bt_submit.setAlpha((float) 0.255);
            //    bt_submit.setBackgroundColor(getResources().getColor(R.color.disabled));


        }
        else {

            bt_submit.setEnabled(true);
            bt_submit.setBackgroundColor(getResources().getColor(R.color.darkest_blue_color));

        }
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("viewRequestList",newViewRequest );
                bundle.putInt("position",position);
                bundle.putString("des",tv_description.getText().toString());

                loadFragments(new HomeReopenServiceFragment(), bundle);
                // Toast.makeText(getActivity(),"OK", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

    private void loadFragments(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }

    @Override
    public void onSuccess(String response) {

        if (response != null) {
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

    @Override
    public void onSuccessHome(String response) {

        String res = response;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(res);
            JSONObject jsonObject1 = jsonObject.getJSONObject("ServiceRequest");
            tv_description.setText(jsonObject1.getString("Problem_Description"));

        } catch (JSONException e) {
            e.printStackTrace();
        }





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
    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
