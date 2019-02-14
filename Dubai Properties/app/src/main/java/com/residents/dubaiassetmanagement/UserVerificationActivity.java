package com.residents.dubaiassetmanagement;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.Profile.MeFragment;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserVerificationActivity extends AppCompatActivity implements MeFragment.OnFragmentInteractionListener {

    private TextView tv_verificationLink,tv_lastpara,tv_verify;
    private String email;
    private String BASE_URL;
    private String REQUEST_URL,profiles;
        private TextView tv_resend;
    OkHttpClient okHttpClient = new OkHttpClient();
    private SavePreference mSavePreference;
    private ApplicationPreferences preferences;
    Button button_login,button_login_resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);
        tv_verificationLink = (TextView) findViewById(R.id.tv_verificationLink);
        tv_resend = (TextView) findViewById(R.id.tv_resend);
        tv_lastpara =  (TextView) findViewById(R.id.tv_lastpara);
        tv_verify=   (TextView) findViewById(R.id.tv_verify);
        button_login = (Button) findViewById(R.id.button_login);
        button_login_resend = (Button) findViewById(R.id.button_login_resend);
        mSavePreference = SavePreference.getInstance(this);
        preferences = ApplicationPreferences.getInstance(this);
        if (getIntent().getExtras()!=null) {
            email = getIntent().getExtras().getString("email_id");
            profiles = getIntent().getExtras().getString("profiles");
if (profiles.equalsIgnoreCase("profiles")){
    tv_lastpara.setVisibility(View.VISIBLE);
    button_login_resend.setVisibility(View.GONE);
}else {
    button_login_resend.setVisibility(View.VISIBLE);
    button_login.setVisibility(View.GONE);
    tv_verify.setText("Please click on the link to verify.");

}
            if (email!=null){
                tv_verificationLink.setText(email);
            }else {
                email = getIntent().getExtras().getString("UserID");
                tv_verificationLink.setText(email);

            }
        }
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // loadFragment(new MeFragment());
            }
        });
    }

    public void BackButton(View view) {
        finish();
    }

    public void ResendLink(View view) {
        BASE_URL = IConstant.BASE_URL;


        REQUEST_URL = BASE_URL+"Token/Resend" + "?userName=" +email + "&registrationMode=" + "Email" + "&tokenType=1";

        //final Request request = new Request.Builder().url(REQUEST_URL).get().build();
//        new HttpAsyncTask().execute(request);
//        final Request request = new Request().Builder.url(REQUEST_URL).put().build();

        Request.Builder builder = new Request.Builder();
        RequestBody body = new FormBody.Builder().build();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);


        Request request = builder.build();

//           OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

//
//            OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

        new CallResendOtp().execute(request);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class CallResendOtp extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserVerificationActivity.this);
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
                            tv_resend.setVisibility(View.VISIBLE);

                            //tv_errorMessage.setVisibility(View.VISIBLE);
                          //  tv_errorMessage.setText(status);
                        }
                        else {
                            tv_resend.setVisibility(View.VISIBLE);
                        }

                    }else {
                    if (responseObject.has("Status")) {

                        String status = responseObject.getString("Status");

                        if (status.equals("Success")) {
                            tv_resend.setVisibility(View.VISIBLE);
                            //tv_errorMessage.setVisibility(View.VISIBLE);
                            //  tv_errorMessage.setText(status);
                        }else {
                            tv_resend.setVisibility(View.VISIBLE);

                        }
                    }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
