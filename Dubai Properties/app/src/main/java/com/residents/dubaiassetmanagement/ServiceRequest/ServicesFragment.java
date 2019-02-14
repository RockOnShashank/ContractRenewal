package com.residents.dubaiassetmanagement.ServiceRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.Profile.MeFragment;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.CreateNewFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new.CommunityCareFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewRequestCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.create_new.LeasingServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewRequestLSFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.events.BottomNavigationService;
import com.residents.dubaiassetmanagement.events.BottomNavigationStatus;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction v4.app.Fragmentevents.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ServicesFragment extends Fragment implements View.OnClickListener, ResponseCallback {

    private TextView fragmentTitle,tv_count;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String BASE_URL;
    private String REQUEST_URL;
    ArrayList<NotificationData> notificationDataList;

    private LinearLayout ll_create_new_mr, ll_create_new_cc, ll_create_new_rs;
    private LinearLayout ll_view_request_mr, ll_view_request_cc, ll_view_request_rs;
    private TenantDetails tenantDetails;
    private ApplicationPreferences preferences;

private SavePreference mSavePreference;
private BottomNavigationView bottomNavigation;
private RelativeLayout iv_bell_icon;
    public ServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.service_dashboard_screen, container, false);
        /// Tenant Details

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialising view
        mSavePreference = SavePreference.getInstance(getActivity());
       // ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        new RequestService(getActivity(), ServicesFragment.this).setArgumentsNotificationCount("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");

        notificationDataList = new ArrayList<>();

        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLocker) getActivity()).setDrawerOpen(true);
            }
        });
preferences = ApplicationPreferences.getInstance(getActivity());
        if (mSavePreference.getString(IpreferenceKey.PHONE_NUMBER)==null) {
            BASE_URL = IConstant.BASE_URL;
            REQUEST_URL = BASE_URL + "Yardi/GetDetails?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);
           // OkHttpHandler okHttpHandler = new OkHttpHandler();
            //okHttpHandler.execute(REQUEST_URL);
        }
        ll_create_new_mr = (LinearLayout) getActivity().findViewById(R.id.ll_create_new_mr);
        ll_create_new_cc = (LinearLayout) getActivity().findViewById(R.id.ll_create_new_cc);
        ll_create_new_rs = (LinearLayout) getActivity().findViewById(R.id.ll_create_new_rs);
        ll_view_request_mr = (LinearLayout) getActivity().findViewById(R.id.ll_view_request_mr);
        ll_view_request_cc = (LinearLayout) getActivity().findViewById(R.id.ll_view_request_cc);
        ll_view_request_rs = (LinearLayout) getActivity().findViewById(R.id.ll_view_request_rs);


        ll_create_new_mr.setOnClickListener(this);
        ll_create_new_cc.setOnClickListener(this);
        ll_create_new_rs.setOnClickListener(this);
        ll_view_request_mr.setOnClickListener(this);
        ll_view_request_cc.setOnClickListener(this);
        ll_view_request_rs.setOnClickListener(this);

//Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("SERVICES");


        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.VISIBLE);

        tv_count = (TextView) getActivity().findViewById(R.id.tv_count);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.VISIBLE);
        mSavePreference.putString(IpreferenceKey.NOTI_COUNT, "0");
        if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT).equalsIgnoreCase("0")){
            tv_count.setVisibility(View.GONE);
        }else {
            tv_count.setVisibility(View.VISIBLE);

            iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
            iv_bell_icon.setVisibility(View.VISIBLE);
        }

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MyObject1", "");

        prefsEditor.putString("CC_List", "");
        prefsEditor.putString("LS_List", "");


        prefsEditor.commit();
        EventBus.getDefault().post(new BottomNavigationService());


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
    public void onClick(View v) {

       switch (v.getId()) {

            case R.id.ll_create_new_mr:
                switchFragment(new CreateNewFragment());
                break;
            case R.id.ll_create_new_cc:
                switchFragment(new CommunityCareFragment());
                break;
            case R.id.ll_create_new_rs:
                switchFragment(new LeasingServicesFragment());

                break;
            case R.id.ll_view_request_mr:
                ViewRequestFragment viewRequestFragment = new ViewRequestFragment();
                switchFragment(viewRequestFragment);
                break;
            case R.id.ll_view_request_cc:
                ViewRequestCCFragment viewRequestCCFragment = new ViewRequestCCFragment();
                switchFragment(viewRequestCCFragment);
                break;
            case R.id.ll_view_request_rs:
                ViewRequestLSFragment viewRequestLSFragment = new ViewRequestLSFragment();
                switchFragment(viewRequestLSFragment);


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
        int i;
        try {
            mSavePreference.putString(IpreferenceKey.NOTI_COUNT,"0");
            notificationDataList.clear();
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

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
           builder.addHeader("SessionId", preferences.getSessionId());
          builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            if (response!=null) {
                if (response.contains("Message")) {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                } else {
                    Log.i("ProfileDetails", "" + response);
                    Gson gson = new Gson();
                    tenantDetails = gson.fromJson(response, TenantDetails.class);
                    mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, tenantDetails.getTenantDetails().get(0).getTenantMobile());
                  //  firstName.setText(tenantDetails.getTenantDetails().get(0).getTenantName());
                                      VolleySingleton.getInstance().setTenantDetails(tenantDetails);

                }
            }else {
                Toast.makeText(getActivity(),"Server Error", Toast.LENGTH_LONG).show();
            }
        }


    }

}
