package com.residents.dubaiassetmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPasswordOTPActivity extends AppCompatActivity {

    private EditText otp_1, otp_2, otp_3, otp_4;
    private String OTP_FULL = "";
    private String UserId, IdType;
    private String BASE_URL, REQUEST_URL;
    private TextView tv_resendOtp,tv_errorMessage;

    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);

        // Data from previous Intent
       // Bundle bundle = getIntent().getExtras();
        UserId = getIntent().getExtras().getString("UserID");
        IdType = getIntent().getExtras().getString("IdType");


        otp_1 = (EditText) findViewById(R.id.otp_1);
        otp_2 = (EditText) findViewById(R.id.otp_2);
        otp_3 = (EditText) findViewById(R.id.otp_3);
        otp_4 = (EditText) findViewById(R.id.otp_4);
        tv_resendOtp = (TextView) findViewById(R.id.tv_resendOtp);
        tv_errorMessage = (TextView) findViewById(R.id.tv_errorMessage);

        otp_1.addTextChangedListener(new OTPTextWatcher(otp_1));
        otp_2.addTextChangedListener(new OTPTextWatcher(otp_2));
        otp_3.addTextChangedListener(new OTPTextWatcher(otp_3));
        otp_4.addTextChangedListener(new OTPTextWatcher(otp_4));


    }

    public void ResendOTP(View view) {
        BASE_URL = IConstant.BASE_URL;


        REQUEST_URL = BASE_URL+"Token/Resend" + "?userName=" +UserId + "&registrationMode=" + IdType + "&tokenType=3";

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

    public void SubmitOTP(View view) {

        if (otp_1.getText().toString()!=null && otp_2.getText().toString()!=null && otp_3.getText().toString()!=null && otp_4.getText().toString()!=null ) {
            OTP_FULL = otp_1.getText().toString() + otp_2.getText().toString() + otp_3.getText().toString() + otp_4.getText().toString();
        }

        BASE_URL = IConstant.BASE_URL;

        REQUEST_URL = BASE_URL+"Token" + "?UserName=" + UserId + "&token="+ OTP_FULL + "&registrationMode=" + IdType+ "&tokenType=3" ;

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);


    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotPasswordOTPActivity.this);
            progressDialog.setMessage("Verifying OTP ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
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
            if (response!=null) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    Log.i("Response Object", "" + responseObject);
if (responseObject.has("status"))
{
    String status = responseObject.getString("status");
    if (status.equalsIgnoreCase("Success")){

        Intent intent = new Intent(ForgotPasswordOTPActivity.this, NewPasswordActivity.class);
        intent.putExtra("username",""+ UserId);
        startActivity(intent);
       // startActivity(new Intent(ForgotPasswordOTPActivity.this,NewPasswordActivity.class));
    }
    else {
        tv_errorMessage.setVisibility(View.VISIBLE);
        tv_errorMessage.setText(status);
    }
}
else{
    if (responseObject.has("Status"))
    {
        String status = responseObject.getString("Status");
        if (status.equalsIgnoreCase("Success")){
String tCode = responseObject.getString("TCode");
            Intent intent = new Intent(ForgotPasswordOTPActivity.this, NewPasswordActivity.class);
            intent.putExtra("username",""+ UserId);
            intent.putExtra("tCode",tCode);
            startActivity(intent);
            // startActivity(new Intent(ForgotPasswordOTPActivity.this,NewPasswordActivity.class));
        }
        else {
            tv_errorMessage.setVisibility(View.VISIBLE);
            tv_errorMessage.setText(status);
        }
    }
}


                } catch (JSONException e) {
                    e.printStackTrace();
                    //  txtString.setText(s);
                }
                progressDialog.dismiss();
            }else {
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT);
            }


        }
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

        }
    }

    class CallResendOtp extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotPasswordOTPActivity.this);
            progressDialog.setMessage("Submitting ...");
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
                            tv_errorMessage.setTextColor(getResources().getColor(R.color.green_color));

                            tv_errorMessage.setText("OTP has been sent successfully");
                                                    }

                    }else {
                        if (responseObject.has("Status")){

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")){
                                tv_errorMessage.setVisibility(View.VISIBLE);
                                tv_errorMessage.setTextColor(getResources().getColor(R.color.green_color));
                                tv_errorMessage.setText("OTP has been sent successfully");
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
    public void BackButton(View view) {
        finish();
    }
}
