package com.residents.dubaiassetmanagement.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateNumberOTPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateNumberOTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateNumberOTPFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText otp_1,otp_2,otp_3,otp_4;
    Button bt_confirm;

    private BottomNavigationView bottomNavigation;
    private RelativeLayout iv_bell_icon;
    OkHttpClient okHttpClient = new OkHttpClient();
    String phoneNumber,userName,otp;
    private OnFragmentInteractionListener mListener;
    Bundle bundle;
    String oldPhone,newNumber,OTP_FULL,BASE_URL,REQUEST_URL;
    private TextView tv_errorMessage,fragmentTitle,tv_resend,tv_enterOTP;
private SavePreference mSavePreference;
    ApplicationPreferences preferences;
    public Toolbar toolbar;

    public UpdateNumberOTPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateNumberOTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateNumberOTPFragment newInstance(String param1, String param2) {
        UpdateNumberOTPFragment fragment = new UpdateNumberOTPFragment();
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
        return inflater.inflate(R.layout.updatephone_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();
        bundle=getArguments();
        mSavePreference = SavePreference.getInstance(getActivity());
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("");
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

loadFragment(new MeFragment());
            }
        });
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        oldPhone = bundle.getString("oldNumber");
        newNumber = bundle.getString("newNumber");
        otp_1 = (EditText) getActivity().findViewById(R.id.otp_1);
        otp_2 = (EditText) getActivity().findViewById(R.id.otp_2);
        otp_3 = (EditText) getActivity().findViewById(R.id.otp_3);
        otp_4 = (EditText) getActivity().findViewById(R.id.otp_4);
        tv_resend = (TextView) getActivity().findViewById(R.id.tv_resend);
        tv_enterOTP = (TextView) getActivity().findViewById(R.id.tv_enterOTP);
       // tv_enterOTP.setText("ENTER OTP");
        tv_errorMessage = (TextView) getActivity().findViewById(R.id.tv_errorMessage);
        otp_1.addTextChangedListener(new OTPTextWatcher(otp_1));
        otp_2.addTextChangedListener(new OTPTextWatcher(otp_2));
        otp_3.addTextChangedListener(new OTPTextWatcher(otp_3));
        otp_4.addTextChangedListener(new OTPTextWatcher(otp_4));
        bt_confirm = (Button) getActivity().findViewById(R.id.button_confirm);
        bt_confirm.setOnClickListener(this);

        tv_resend.setOnClickListener(this);
       preferences  =  ApplicationPreferences.getInstance(ApplicationController.getContext());

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
        switch(v.getId()){
            case R.id.button_confirm:
                otp =otp_1.getText().toString()+otp_2.getText().toString()+otp_3.getText().toString()+otp_4.getText().toString();
                if (otp.isEmpty()){
                    tv_errorMessage.setText("Please Enter the OTP");
                }else {
                    SubmitOTP();
                }
                break;
            case R.id.tv_resend:
               ResendOTP(v);
                break;

        }
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

    private class OTPTextWatcher implements TextWatcher {
        private View view;
        private OTPTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId())
            {
                case R.id.otp_1:
                    if(text.length()==1)
                        otp_2.requestFocus();
                    break;
                case R.id.otp_2:
                    if(text.length()==1)
                        otp_3.requestFocus();
                    break;
                case R.id.otp_3:
                    if(text.length()==1)
                        otp_4.requestFocus();
                    break;
                case R.id.otp_4:
                    break;
            }
            if (otp_1.getText().toString().length() > 0 && otp_2.getText().toString().length() >0&& otp_3.getText().toString().length() >0&& otp_4.getText().toString().length() >0) {
                bt_confirm.setEnabled(true);
                bt_confirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                bt_confirm.setEnabled(false);
                bt_confirm.setBackgroundColor(getResources().getColor(R.color.disabled_button));

            }

        }
    }

    public void SubmitOTP() {

        if (otp_1.getText().toString()!=null && otp_2.getText().toString()!=null && otp_3.getText().toString()!=null && otp_4.getText().toString()!=null ) {
            OTP_FULL = otp_1.getText().toString() + otp_2.getText().toString() + otp_3.getText().toString() + otp_4.getText().toString();
        }

        BASE_URL = IConstant.BASE_URL;

        REQUEST_URL = BASE_URL+"Token/Validate?"+"token="+OTP_FULL + "&registrationMode=Phone" + "&profileId="+ mSavePreference.getString(IpreferenceKey.TCODE) + "&newPhoneNumber=" + newNumber;



        JSONObject jsonObject = new JSONObject();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());


        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        Request request = builder.build();


        new OkHttpHandler().execute(request);



    }

    public class OkHttpHandler extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Verifying...");
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
            if (response != null) {
                progressDialog.dismiss();

                try {

                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("UploadResponse", "" + responseObject);
                    if (responseObject.has("status")) {
                        String status = responseObject.getString("status");
if (status.equalsIgnoreCase("Success")){
    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
    alertDialog.setTitle("Alert");
    alertDialog.setMessage("Successfully Verified!");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    dialog.dismiss();
                }
            });
    alertDialog.show();
}

                    } else {
                        if (responseObject.has("Status")) {
                            String status = responseObject.getString("Status");
                            if (status.equalsIgnoreCase("Success")){
                                launchSuccessDialogs("123");
                               /* AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Successfully Verified!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                loadFragment(new MeFragment());

                                                // startActivity(new Intent(getActivity(), HomeActivity.class));
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();*/
                            }else {
                                otp_1.setText("");
                                otp_2.setText("");
                                otp_3.setText("");
                                otp_4.setText("");
otp_1.requestFocus();
                                tv_errorMessage.setVisibility(View.VISIBLE);
                                tv_errorMessage.setText(status);
                                tv_errorMessage.setTextColor(getActivity().getResources().getColor(R.color.red));
                            }

                        }

                       // tv_errorMessage.setText(response.toString());


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
}
    public void ResendOTP(View view) {
        BASE_URL = IConstant.BASE_URL;
        String num = newNumber.replace("%20"," ");

        REQUEST_URL = BASE_URL+"Token/Send?" + "profileId=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&registrationMode=" + "Phone" + "&newEmailorPhone=" + num + "&tokenType=" + "4";

        //final Request request = new Request.Builder().url(REQUEST_URL).get().build();
//        new HttpAsyncTask().execute(request);
//        final Request request = new Request().Builder.url(REQUEST_URL).put().build();

        Request.Builder builder = new Request.Builder();
        RequestBody body = new FormBody.Builder().build();
        builder = builder.url(REQUEST_URL);
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
       builder =  builder.addHeader("SessionId", preferences.getSessionId());
     builder =    builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

        Request request = builder.build();

//           OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

//
//            OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

        new CallResendOtp().execute(request);


    }
    class CallResendOtp extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Submitting ..");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("Seee", "" + response);
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
                    Log.i("Here",""+response);
                    assert response.body() != null;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("Response Object", "" + responseObject);
                    if (responseObject.has("status")){

                        String status = responseObject.getString("status");

                        if (status.equals("Success")){
                            tv_errorMessage.setVisibility(View.VISIBLE);
                            tv_errorMessage.setText(status);
                        }

                    }else {
                        if (responseObject.has("Status")){

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")){
                                tv_errorMessage.setVisibility(View.VISIBLE);
                                tv_errorMessage.setText("OTP has been sent successfully.");
                                tv_errorMessage.setTextColor(getActivity().getResources().getColor(R.color.green_color));
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
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void launchSuccessDialogs(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_dialog);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message0 = (TextView) dialog.findViewById(R.id.tv_message0);
        TextView tv_message1 = (TextView) dialog.findViewById(R.id.tv_message1);
        TextView tv_message2 = (TextView) dialog.findViewById(R.id.tv_message2);

        tv_message0.setText("Congratulations!");
        tv_message1.setText("You have successfully authenticated your new mobile number.");
        tv_message2.setText("All communication will be sent to your updated contact details. Your login credentials will not change");


        srid.setVisibility(View.GONE);
        //srid.setText("SR ID #"+srID);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MeFragment());
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((HomeActivity)getActivity()).getSupportActionBar().show();
    }
}