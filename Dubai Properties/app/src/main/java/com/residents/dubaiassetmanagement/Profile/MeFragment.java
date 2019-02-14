package com.residents.dubaiassetmanagement.Profile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.common.eventbus.Subscribe;
import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.events.ProfileUpdate;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.notification_list.NotificationList;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements ResponseCallback {

    private TextView fragmentTitle;
    private boolean isProfilePic = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SectionsPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private TextView firstName;
    private TextView Address1, Address2;
    private String BASE_URL;
    private String REQUEST_URL;
    private Button EditImage;
    ViewPager viewPager;
    CircleImageView profile_image;
    private Bitmap bitMap;
    private TextView RenewalDate;
    private JSONObject responseObject;
    private TextView firstname_frag;
    private TextView lastname_frag;
    private TextView dob_frag;
    private TextView Nationality_frag;
    private TextView VisaNo_frag, profile_RenewalDateH;
    private TextView VisaExp_frag;
    private TextView EmiratesNo_frag;
    private EditText mobileno_frag;
    private String REQUEST_URL1;
    private JSONObject responseObject1;
    private SavePreference mSavePreference;
    private TenantDetails tenantDetails;
    public static int event;
    OkHttpClient okHttpClient = new OkHttpClient();
    static final int RESULT_GALLERY = 500;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;

    String mCurrentPhotoPath;
    String imageFileName;
    Uri photoURI;
    final static String TAG = "MeFragment";
    byte[] b, bytesEncoded, bytesFromFile, bbb;
    String encImage;
    private RelativeLayout bac_dim_layout;
    private Bitmap bitmap, bitmap_new;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");
    final HashMap<String, String> param = new HashMap<>();
    private View view;
    private PopupWindow popupWindow;
    ApplicationPreferences applicationPreferences;
    ViewGroup root;
    String date, dates;
    private RelativeLayout iv_bell_icon;
    private TextView tv_count;
    private BottomNavigationView bottomNavigation;
    ArrayList<NotificationData> notificationDataList;
    private static int z = 0;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        view = inflater.inflate(R.layout.fragment_me, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        EventBus.getDefault().register(this);


        mSavePreference = SavePreference.getInstance(getActivity());
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("ACCOUNT");
        notificationDataList = new ArrayList<>();

        new RequestService(getActivity(), MeFragment.this).setArgumentsNotificationCount("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");


        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.VISIBLE);

        tv_count = (TextView) getActivity().findViewById(R.id.tv_count);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.VISIBLE);


        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLocker) getActivity()).setDrawerOpen(true);
            }
        });
        mSavePreference.putString(IpreferenceKey.NOTI_COUNT, "0");

        if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT).equalsIgnoreCase("0")) {
            tv_count.setVisibility(View.GONE);
        } else {
            tv_count.setVisibility(View.VISIBLE);

            iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
            iv_bell_icon.setVisibility(View.VISIBLE);
        }

        applicationPreferences = ApplicationPreferences.getInstance(getActivity());
       /* OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(50, TimeUnit.SECONDS);
        builder.readTimeout(50, TimeUnit.SECONDS);
        builder.writeTimeout(50, TimeUnit.SECONDS);

        okHttpClient = builder.build();*/

        mSectionPageAdapter = new SectionsPageAdapter(getChildFragmentManager());
        EditImage = (Button) view.findViewById(R.id.EditImageButton);
        bac_dim_layout = (RelativeLayout) view.findViewById(R.id.bac_dim_layout);
        bac_dim_layout.setVisibility(View.GONE);

        firstName = (TextView) view.findViewById(R.id.profile_Name);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        Address1 = (TextView) view.findViewById(R.id.profile_address);
        Address2 = (TextView) view.findViewById(R.id.profile_BuildingNo);
        RenewalDate = (TextView) view.findViewById(R.id.profile_RenewalDate);

        profile_RenewalDateH = (TextView) view.findViewById(R.id.profile_RenewalDateH);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#585d63"), Color.parseColor("#333f48"));

        Glide.with(getActivity())
                .load(R.raw.animated)
                .into(profile_image);

        /// Tenant Details
        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL = BASE_URL + "Yardi/GetDetails?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);

////


        EditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSavePreference.getString(IpreferenceKey.IS_PERMISSIONS_GRANTED).equalsIgnoreCase("false")) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                } else {
                    if (isProfilePic) {
                        bitMap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
                        onButtonShowPopupWindowClick(v, bitMap);

                    } else {

                        onButtonShowPopupWindowClickDefault(v);

                    }
                }
            }
        });
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();

        return view;
    }


    private void setupViewPager(ViewPager viewPager) {

        //    adapter.addFragment(new PersonalInfoFragment(), "Personal");
        mSectionPageAdapter.addFragment(new ContactFragment(), "Contact");
        mSectionPageAdapter.addFragment(new LeaseDetailsFragment(), "Lease Details");
        mSectionPageAdapter.addFragment(new DependantsFragment(), "Dependants");
        viewPager.setAdapter(mSectionPageAdapter);

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
    public void onSuccess(String response) {

    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {
        int i;
        try {
            notificationDataList.clear();

            mSavePreference.putString(IpreferenceKey.NOTI_COUNT, "0");
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
            if (notificationDataList.size() == 0) {
                mSavePreference.putString(IpreferenceKey.NOTI_COUNT, "0");

            } else {
                mSavePreference.putString(IpreferenceKey.NOTI_COUNT, String.valueOf(notificationDataList.size()));
            }
            if (mSavePreference.getString(IpreferenceKey.NOTI_COUNT).equalsIgnoreCase("0")) {
                tv_count.setVisibility(View.GONE);
            } else {
                tv_count.setVisibility(View.VISIBLE);
                tv_count.setText(mSavePreference.getString(IpreferenceKey.NOTI_COUNT));
                iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
                iv_bell_icon.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {

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

/*
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();

            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            try {
                if (response.body().string() != null) {


                        // Log.i("responseHere",""+response.body().string());


                        Gson gson = new Gson();
                        tenantDetails = gson.fromJson(response.body().string(), TenantDetails.class);


                      //  responseObject = new JSONObject(response.body().string());

                        Log.i("The array is", String.valueOf(responseObject));
                      //  JSONArray responseArray = new JSONArray(responseObject.getString("tenant_Details"));

                     //   Log.i("The array is ", "" + responseArray.getJSONObject(0).getString("Tenant_Name"));

                       // firstName.setText(responseArray.getJSONObject(0).getString("Tenant_Name"));

                        firstName.setText(tenantDetails.getTenantDetails().get(0).getTenantName());

                        RenewalDate.setText(tenantDetails.getTenantDetails().get(0).getLeaseEndDate());

                        firstname_frag = (TextView) getActivity().findViewById(R.id.profile_personal_firstname);
                        firstname_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantFirstName());

                        lastname_frag = (TextView) getActivity().findViewById(R.id.profile_personal_lastname);
                        lastname_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantLastName());

                        dob_frag = (TextView) getActivity().findViewById(R.id.profile_personal_dob);
                        dob_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantDob());

                        Nationality_frag = (TextView) getActivity().findViewById(R.id.profile_personal_nationality);
                        Nationality_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantNationality());

                        VisaNo_frag = (TextView) getActivity().findViewById(R.id.profile_personal_visano);
                        VisaNo_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantVisaNumber());

                        VisaExp_frag = (TextView) getActivity().findViewById(R.id.profile_personal_visaexp);
                        VisaExp_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantVisaExpDate());

                        EmiratesNo_frag = (TextView) getActivity().findViewById(R.id.profile_personal_emiratesid);
                        EmiratesNo_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantEmiratesId());

                        mobileno_frag = (EditText) getActivity().findViewById(R.id.profile_editText_mobileNo);
                        mobileno_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantMobile());


                        // Log.i("REsponse value:",""+responseObject.getString("Tenant_Name"));
                    }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }


    }*/


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
            builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
            builder.addHeader("SessionId", applicationPreferences.getSessionId());
            builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));
            //

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
            if (response != null) {
                if (!response.equalsIgnoreCase("")) {
                    if (response.contains("Message")) {
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("ProfileDetails", "" + response);

                        Gson gson = new Gson();
                        tenantDetails = gson.fromJson(response, TenantDetails.class);

                        mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, tenantDetails.getTenantDetails().get(0).getTenantMobile());

                        firstName.setText(tenantDetails.getTenantDetails().get(0).getTenantName());
                        if (tenantDetails.getTenantDetails().get(0).getTenantName().length() > 30) {
                            firstName.setTextSize(15);
                        }
                        Address1.setText(tenantDetails.getTenantDetails().get(0).getTenantProjectName().trim() + ", " + tenantDetails.getTenantDetails().get(0).getPropertyCode().trim() + ", " + tenantDetails.getTenantDetails().get(0).getUnitCode());
                        try {
                            dates = getMonth(tenantDetails.getTenantDetails().get(0).getLeaseStartDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Address2.setText("Lease Start Date: " + dates.replaceAll("-", " "));


                        try {
                            date = getMonth(tenantDetails.getTenantDetails().get(0).getLeaseEndDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        RenewalDate.setText(date.replaceAll("-", " "));
                        VolleySingleton.getInstance().setTenantDetails(tenantDetails);
                        EditImage.setVisibility(View.VISIBLE);
                        profile_RenewalDateH.setVisibility(View.VISIBLE);
                        setupViewPager(mViewPager);

                        getProfilePicture();


/*
                Log.i("ProfileResponse", response);

                firstName.setText(tenantDetails.getTenantDetails().get(0).getTenantName());

                RenewalDate.setText(tenantDetails.getTenantDetails().get(0).getLeaseEndDate());

                firstname_frag = (TextView) getActivity().findViewById(R.id.profile_personal_firstname);
                if (tenantDetails.getTenantDetails().get(0).getTenantFirstName().equalsIgnoreCase("")) {
                    firstname_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantFirstName());
                }
                lastname_frag = (TextView) getActivity().findViewById(R.id.profile_personal_lastname);
                lastname_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantLastName());

                dob_frag = (TextView) getActivity().findViewById(R.id.profile_personal_dob);
                dob_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantDob());

                Nationality_frag = (TextView) getActivity().findViewById(R.id.profile_personal_nationality);
                Nationality_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantNationality());

                VisaNo_frag = (TextView) getActivity().findViewById(R.id.profile_personal_visano);
                VisaNo_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantVisaNumber());

                VisaExp_frag = (TextView) getActivity().findViewById(R.id.profile_personal_visaexp);
                VisaExp_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantVisaExpDate());

                EmiratesNo_frag = (TextView) getActivity().findViewById(R.id.profile_personal_emiratesid);
                EmiratesNo_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantEmiratesId());

                mobileno_frag = (EditText) getActivity().findViewById(R.id.profile_editText_mobileNo);
                mobileno_frag.setText(tenantDetails.getTenantDetails().get(0).getTenantMobile());
                // Log.i("REsponse value:",""+responseObject.getString("Tenant_Name"));*/
                    }
                } else {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();

            }
        }


    }


    public void getProfilePicture() {
        BASE_URL = IConstant.BASE_URL;
        Log.i("Base URL", "" + BASE_URL);
        REQUEST_URL = BASE_URL + "ProfileMedia?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);
        GetProfilePic getProfilePic = new GetProfilePic();
        getProfilePic.execute(REQUEST_URL);
    }

    public class GetProfilePic extends AsyncTask<String, Void, String> {

        //  OkHttpClient client = new OkHttpClient();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
            builder.addHeader("SessionId", applicationPreferences.getSessionId());
            builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));


            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {


                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response);

                    Log.i("Response Object", "" + responseObject);

                    if (responseObject.has("ProfilePicture")) {
                        Log.i("Status", "" + response);
                        String base64String = responseObject.getString("ProfilePicture");
                        isProfilePic = true;
                        if (!base64String.equalsIgnoreCase("")) {
                            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profile_image.setImageBitmap(decodedByte);
                        }
                    } else {
                        isProfilePic = false;
                        profile_image.setImageResource(R.drawable.default_profile_pic);
                    }
                    // setupViewPager(mViewPager);

                    //     progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void onButtonShowPopupWindowClick(final View view, final Bitmap bp) {
        applyDim(root, 0.5f);
        //bac_dim_layout.setVisibility(View.VISIBLE);
        LinearLayout ll_camera, ll_removePhoto, ll_gallery;
        TextView tv_cancel, tv_ok;
        CircleImageView iv_remove_photo;
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.profilepic_dialog, null);
        ll_camera = (LinearLayout) popupView.findViewById(R.id.ll_camera);
        ll_removePhoto = (LinearLayout) popupView.findViewById(R.id.ll_removePhoto);
        ll_gallery = (LinearLayout) popupView.findViewById(R.id.ll_gallery);
        iv_remove_photo = (CircleImageView) popupView.findViewById(R.id.iv_remove_photo);
        iv_remove_photo.setImageBitmap(bp);
        tv_cancel = (TextView) popupView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) popupView.findViewById(R.id.tv_ok);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_GALLERY);

              /*  Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
                bac_dim_layout.setVisibility(View.GONE);*/

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);

                popupWindow.dismiss();
                bac_dim_layout.setVisibility(View.GONE);

            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);

                popupWindow.dismiss();
                bac_dim_layout.setVisibility(View.GONE);

            }
        });
        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);

                /*    popupWindow.dismiss();
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    *//* Ensure that there's a camera activity to handle the intent *//*

                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        *//* Create the File where the photo should go *//*
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            ltag("error: " + ex.toString());
                        }
                        *//* Continue only if the File was successfully created *//*

                        if (photoFile != null) {
                            photoURI = FileProvider.getUriForFile(getActivity(),
                                    "com.residents.dubaiassetmanagement.fileprovider",
                                    photoFile);
                            ltag("photoURI: " + photoURI.getPath().toString());
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }*/
                ////
                popupWindow.dismiss();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    clearDim(root);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }


        });

        ll_removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

                onRemovePopupDialog(bp);

            }
        });

        // dismiss the popup window when touched
       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });*/
    }


    public void onButtonShowPopupWindowClickDefault(View view) {
        applyDim(root, 0.5f);
        LinearLayout ll_camera, ll_removePhoto, ll_gallery;
        View view_camera;
        TextView tv_cancel, tv_ok;
        CircleImageView iv_remove_photo;
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.profilepic_dialog, null);
        ll_camera = (LinearLayout) popupView.findViewById(R.id.ll_camera);

        ll_removePhoto = (LinearLayout) popupView.findViewById(R.id.ll_removePhoto);
        ll_removePhoto.setVisibility(View.GONE);
        view_camera = (View) popupView.findViewById(R.id.view_camera);
        view_camera.setVisibility(View.GONE);
        ll_gallery = (LinearLayout) popupView.findViewById(R.id.ll_gallery);
        iv_remove_photo = (CircleImageView) popupView.findViewById(R.id.iv_remove_photo);
        // iv_remove_photo.setImageBitmap(bp);
        tv_cancel = (TextView) popupView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) popupView.findViewById(R.id.tv_ok);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_GALLERY);

              /*  Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
                bac_dim_layout.setVisibility(View.GONE);*/

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

            }
        });
        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);

                /*popupWindow.dismiss();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                *//* Ensure that there's a camera activity to handle the intent *//*

                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    *//* Create the File where the photo should go *//*
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ltag("error: " + ex.toString());
                    }
                    *//* Continue only if the File was successfully created *//*

                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.residents.dubaiassetmanagement.fileprovider",
                                photoFile);
                        ltag("photoURI: " + photoURI.getPath().toString());
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }*/

                popupWindow.dismiss();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                ///
            }


        });

        ll_removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

                //  onRemovePopupDialog(v,bp);

            }
        });

        // dismiss the popup window when touched
       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });*/

    }

    public void onRemovePopupDialog(Bitmap bitmap) {
        applyDim(root, 0.5f);
        TextView tv_cancel, tv_ok;
        CircleImageView iv_remove_photo;

        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.dialog_remove_photo, null);


        iv_remove_photo = (CircleImageView) popupView.findViewById(R.id.iv_remove_photo);
        iv_remove_photo.setImageBitmap(bitmap);
        tv_cancel = (TextView) popupView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) popupView.findViewById(R.id.tv_ok);


        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);
        popupWindow.setOutsideTouchable(false);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);

                popupWindow.dismiss();
                profile_image.setImageResource(R.drawable.default_profile_pic);

                BASE_URL = IConstant.BASE_URL;

                String REQUEST_URL = BASE_URL + "ProfileMedia/Delete?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);
                JSONObject jsonObject = new JSONObject();


                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                // Request request = new Request().Builder().url().post(body).build();
                Request.Builder builder = new Request.Builder();
                builder = builder.url(REQUEST_URL);
                builder = builder.delete(body);
                builder = builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
                builder = builder.addHeader("SessionId", applicationPreferences.getSessionId());
                builder = builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

                Request request = builder.build();
                new DeleteProfilePic().execute(request);

            }
        });


    }

    private File createImageFile() throws IOException {
        /* Create an image file name */

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";

        //Album
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //create full size image
        File image = File.createTempFile(
                imageFileName,  /* prefix */

                ".jpg",         /* suffix */

                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    /*
        if you use MediaStore.EXTRA_OUTPUT, the data will be null.
     */
        popupWindow.dismiss();
        clearDim(root);


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

          /*      ContentResolver contentResolver = getActivity().getContentResolver();
                try {
                    //this is full size image
                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoURI);

                    if (fullBitmap.getWidth() > fullBitmap.getHeight()) {
                        fullBitmap = rotate90(fullBitmap);
                    }

                    profile_image.setImageBitmap(fullBitmap);
                    profile_image.setVisibility(View.VISIBLE);

                    // Bitmap bmp = intent.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    fullBitmap.recycle();
*//*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                fullBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();*//*

                //    uploadPic(byteArray);

                    saveImage(fullBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            ////
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profile_image.setImageBitmap(imageBitmap);

            encImage = Base64.encodeToString(getBytesFromBitmap(imageBitmap),
                    Base64.NO_WRAP);

            Upload(encImage);


        } else if (requestCode == RESULT_GALLERY && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            FileInputStream fis = null;

            try {
                bitmap_new = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                fis = new FileInputStream(picturePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //   profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            //Conversion into byte array
            bitmap = BitmapFactory.decodeStream(fis);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            b = baos.toByteArray();
            profile_image.setImageBitmap(bitmap);

            try {
                File f = new File(picturePath);
                bytesFromFile = ToBytes.getBytes(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
//
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            bitmap_new.compress(Bitmap.CompressFormat.JPEG, 10, baos1);
            final byte[] b1 = baos.toByteArray();


            encImage = Base64.encodeToString(getBytesFromBitmap(bitmap_new),
                    Base64.NO_WRAP);

            try {
                bbb = encImage.getBytes("UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Upload(encImage);


        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    /*
        Rotate 90 degree if the image is in landscape.
     */
    public Bitmap rotate90(Bitmap paramBitmap) {
        int rotateAngle = 90;
        Matrix localMatrix = new Matrix();
        float f1 = paramBitmap.getWidth() / 2;
        float f2 = paramBitmap.getHeight() / 2;
        localMatrix.postTranslate(-paramBitmap.getWidth() / 2, -paramBitmap.getHeight() / 2);
        localMatrix.postRotate(rotateAngle);
        localMatrix.postTranslate(f1, f2);
        paramBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
        new Canvas(paramBitmap).drawBitmap(paramBitmap, 0.0F, 0.0F, null);
        return paramBitmap;
    }

    /*
    Save full size image
 */
    private void saveImage(Bitmap bitmap) {

        try {
            String albumName = "CameraDemoAlbum";
            File albumDir = getAlbumStorageDir(albumName);

            OutputStream imageOut = null;
            File file = new File(albumDir, imageFileName + ".jpg");

            imageOut = new FileOutputStream(file);

            //Bitmap -> JPEG with 85% compression rate
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, imageOut);
            imageOut.flush();
            imageOut.close();

            //update gallery so you can view the image in gallery
            updateGallery(albumName, albumDir, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Set the personal album in DCIM
     */
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), albumName);
        if (!file.mkdirs()) {
            ltag("Directory not created");
        }
        return file;
    }

    /*
        Add the image and image album into gallery
     */
    private void updateGallery(String albumName, File albumDir, File file) {
        //metadata of new image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, imageFileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, albumName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        values.put("_data", file.getAbsolutePath());

        ContentResolver cr = getActivity().getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        ltag("album Directory: " + albumDir.getAbsolutePath());

        File f = new File(albumDir.getAbsolutePath());
        Uri contentUri = Uri.fromFile(f);
        //notify gallery for new image
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    public static void msg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /* Log tag and shortcut */

    public static void ltag(String message) {
        Log.i(TAG, message);
    }


    //Http Request is handled below
    class UploadProfilePictureCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading Picture...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            OkHttpClient client = new OkHttpClient();
            Response response = null;

            try {

                response = client.newCall(requests[0]).execute();
                Log.i("UploadResponse", "" + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressDialog.dismiss();


            if (response != null) {

                try {
                    isProfilePic = true;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("UploadResponse", "" + responseObject);
                    if (responseObject.has("tCode")) {
                        String tcode = responseObject.getString("tCode");


                    } else {
                        if (responseObject.has("Status")) {
                            String status = responseObject.getString("Status");
                            if (status.equalsIgnoreCase("Success")) {
                                Toast.makeText(getActivity(), "Updated Successfully.", Toast.LENGTH_LONG).show();

                            }
                        }

                        Toast.makeText(getActivity(), "Updated Successfully.", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class DeleteProfilePic extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Removing...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("UploadProfilePicture_do", "" + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            if (response != null) {
                try {
                    isProfilePic = false;

                    Log.i("Here", "" + response);
                    assert response.body() != null;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("DeletePhoto", "" + responseObject);
                    if (responseObject.has("status")) {

                        String status = responseObject.getString("status");

                        if (status.equals("Success")) {


                            Toast.makeText(getActivity(), "Profile Picture Deleted", Toast.LENGTH_LONG).show();

                        } else if (status.equalsIgnoreCase("No active profile")) {
                            //errorMessage.setVisibility(View.VISIBLE);
                            // errorMessage.setText(status);

                        }

                    } else {
                        if (responseObject.has("Status")) {

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")) {


                                Toast.makeText(getActivity(), "Profile Picture Deleted", Toast.LENGTH_LONG).show();

                            } else if (status.equalsIgnoreCase("No active profile")) {
                                //errorMessage.setVisibility(View.VISIBLE);
                                // errorMessage.setText(status);

                            }

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
  /*  public void uploadPic(byte[] byteArray) {

        BASE_URL = IConstant.BASE_URL;

        okHttpClient = new OkHttpClient.Builder()
                .build();



        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("TCode",mSavePreference.getString(IpreferenceKey.TCODE))
                .addFormDataPart("FileName","Pic")
                .addFormDataPart("RequestType","1")
                .addFormDataPart("IsValid","true")
                .addFormDataPart("ProfilePicture", "ProfilePicture",
                        RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL+"ProfileMedia")
                .put(requestBody)
                .addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Toast.makeText(getApplicationContext(), "This is a message displayed in a Toast", Toast.LENGTH_SHORT).show();
            }
        });
    }

*/


    public void Upload(String encodedString) {
      /*Log.i("ByteArray", "" + bitMap);
      Log.i("ByteArray_string","" + bitMap.toString());*/

        BASE_URL = IConstant.BASE_URL;

        String REQUEST_URL = BASE_URL + "ProfileMedia/Update?tcode=" + mSavePreference.getString(IpreferenceKey.TCODE);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("tcode", "" + mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("ProfilePicture", encodedString);
            jsonObject.put("RequestType", "1");
            Log.i("Array", encodedString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
        builder = builder.addHeader("SessionId", applicationPreferences.getSessionId());
        builder = builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new UploadProfilePictureCall().execute(request);


        //   builder = builder.addHeader("content-type", "application/json; charset=utf-8");

    }


    public void multipart(byte[] byteArray) {
        OkHttpClient httpClient = new OkHttpClient();
        BASE_URL = IConstant.BASE_URL;
        String REQUEST_URL = BASE_URL + "ProfileMedia/Put?tcode=" + mSavePreference.getString(IpreferenceKey.TCODE);

        //creates multipart http requests
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tcode", mSavePreference.getString(IpreferenceKey.TCODE))
                .addFormDataPart("RequestType", "1")
                .addFormDataPart("ProfilePicture", "ProfilePicture",
                        RequestBody.create(MediaType.parse("image/*jpg"), new String(byteArray)))
                .build();
        Request request = new Request.Builder()
                .url(REQUEST_URL)
                .put(requestBody)
                .addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword())
                .addHeader("SessionId", applicationPreferences.getSessionId())
                .addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE))
                .build();

        new UploadProfilePictureCall().execute(request);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        return stream.toByteArray();

    }


    public static byte[] toByteArray(Bitmap raw) {

        byte[] byteArray = null;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            raw.compress(Bitmap.CompressFormat.JPEG, 10, stream);
            byteArray = stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArray;
    }


    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void volleyUpload(final byte[] bytearray) {

        BASE_URL = IConstant.BASE_URL;

        String REQUEST_URL = BASE_URL + "ProfileMedia/Put?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);

        JSONObject jsonRequest = new JSONObject();
        try {

                /*    mSavePreference1.putString(IpreferenceKey.USERNAME, username.getText().toString());
                    mSavePreference1.putString(IpreferenceKey.PASSWORD, password.getText().toString());
*/
            jsonRequest.put("TCode", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonRequest.put("ProfilePicture", bytearray);
            jsonRequest.put("RequestType", 1);
/*
                    jsonRequest.put("password", password.getText().toString().trim());
                    jsonRequest.put("username", username.getText().toString().trim());*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog finalProgressDialog = new ProgressDialog(getActivity());
        JsonObjectRequest json = new JsonObjectRequest(com.android.volley.Request.Method.PUT, REQUEST_URL, jsonRequest, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
             /*   try {
                    String token = response.getString("token");
                    Boolean passwordChangeRequired = response.getBoolean("passwordChangeRequired");
                    savePreference.putString(IpreferenceKey.TOKEN, token);
                    savePreference.putBoolean(IpreferenceKey.PASSWORDCHANGEREQUIRED, passwordChangeRequired);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                Log.e("Login Token", response.toString());*/
                finalProgressDialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_LONG).show();

              /*  if (error instanceof NetworkError) {
                    response("NetworkError");

                } else if (error instanceof TimeoutError) {
                    response("TimeoutError");

                } else if (error instanceof ServerError) {
                    response("ServerError");
                }

                *//*   else if(error.getMessage().equalsIgnoreCase("Unauthorized"))
                    {

                        //response("ServerError");
                        // *//*
                Snackbar snackbar = Snackbar.make(ll, "Please enter valid credentials", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                finalProgressDialog.dismiss();


            }

        }) {
            @Override
            public byte[] getBody() {
                return bytearray;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
                headers.put("SessionId", applicationPreferences.getSessionId());
                headers.put("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

                return headers;
            }

        };


        ApplicationController.getInstance().addToRequestQueue(json);

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "true");
                    } //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "false");

// Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                //  showMessageOKCancel("You need to allow access to both the permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                };
                                return;
                            }
                        }

                    }
                }

                break;
        }
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

    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
    @Subscribe
    public void onEvent(ProfileUpdate event) {
        loadFragment(new MeFragment());

    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}


     /*   Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "Got response from server for multipart request using OkHttp ");
            }

        } catch (IOException e) {
            Log.e(TAG, "error in getting response for multipart request okhttp");
        }*/


