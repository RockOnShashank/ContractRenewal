package com.residents.dubaiassetmanagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText otp_1,otp_2,otp_3,otp_4;
    Button bt_confirm;
    OkHttpClient okHttpClient = new OkHttpClient();
    String phoneNumber,userName,otp;
    private String REGISTER_URL;
    private TextView tv_resend_otp,tv_errorMessage;
private SavePreference mSavePreference;
private ApplicationPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_otp);
        if (getIntent().getExtras() !=null) {
            phoneNumber = getIntent().getExtras().getString("phoneNumber");
            userName = getIntent().getExtras().getString("userName");
        }
        mSavePreference  = SavePreference.getInstance(this);
        preferences = ApplicationPreferences.getInstance(this);
        tv_resend_otp =(TextView) findViewById(R.id.tv_resend_otp);
        tv_errorMessage = (TextView) findViewById(R.id.tv_errorMessage);
        otp_1 = (EditText) findViewById(R.id.otp_1);
        otp_2 = (EditText) findViewById(R.id.otp_2);
        otp_3 = (EditText) findViewById(R.id.otp_3);
        otp_4 = (EditText) findViewById(R.id.otp_4);
        otp_1.addTextChangedListener(new OTPTextWatcher(otp_1));
        otp_2.addTextChangedListener(new OTPTextWatcher(otp_2));
        otp_3.addTextChangedListener(new OTPTextWatcher(otp_3));
        otp_4.addTextChangedListener(new OTPTextWatcher(otp_4));
        bt_confirm = (Button) findViewById(R.id.button_confirm);
        bt_confirm.setOnClickListener(this);
        tv_resend_otp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_confirm:
                otp =otp_1.getText().toString()+otp_2.getText().toString()+otp_3.getText().toString()+otp_4.getText().toString();
                apicall();
                break;
            case R.id.tv_resend_otp:
                resendOTP();
                break;
        }
    }
    //Http Request is handled below
    class HttpAsyncTaskResendOTP extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterOTPActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("Seee",""+response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            tv_errorMessage.setVisibility(View.INVISIBLE);
            try {

                JSONObject responseObject = new JSONObject(response.body().string());
                Log.i("Response Object",""+responseObject);
                if (responseObject.toString().contains("tCode")) {
                    String tcode = responseObject.getString("tCode");
                    Log.i("tCOde Value", "" + tcode);
                    //startActivity(new Intent(RegisterActivity.this, UserVerificationActivity.class));
                    Intent intent = new Intent(RegisterOTPActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    if (responseObject.toString().contains("Status")) {
                        String status = responseObject.getString("Status");
                        Log.i("tCOde Value", "" + status);
                        //startActivity(new Intent(RegisterActivity.this, UserVerificationActivity.class));
                        Toast.makeText(RegisterOTPActivity.this,"OTP has been sent successfully.",Toast.LENGTH_LONG).show();

                    }
                    String status = responseObject.getString("status");
                    Log.i("Status object value", "" + status);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
    //Http Request is handled below
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterOTPActivity.this);
            progressDialog.setMessage("Registering user...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("Seee",""+response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);

            try {

                JSONObject responseObject = new JSONObject(response.body().string());
                Log.i("Response Object",""+responseObject);
                if (responseObject.toString().contains("status")) {
                    String status = responseObject.getString("status");
                    Log.i("tCOde Value", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        //startActivity(new Intent(RegisterActivity.this, UserVerificationActivity.class));
                        Intent intent = new Intent(RegisterOTPActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (responseObject.toString().contains("Status")) {
                        String status = responseObject.getString("Status");
                        Log.i("tCOde Value", "" + status);
                        if (status.equalsIgnoreCase("Success")) {


                            tv_errorMessage.setVisibility(View.INVISIBLE);

                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterOTPActivity.this).create();
                            alertDialog.setTitle("");
                            alertDialog.setMessage("You have successfully registered with Dubai Asset Management! Please Login");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegisterOTPActivity.this, LoginActivity.class);
                                            startActivity(intent);


                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                            //startActivity(new Intent(RegisterActivity.this, UserVerificationActivity.class));

                        }else {
                            tv_errorMessage.setVisibility(View.VISIBLE);
                            tv_errorMessage.setText(status);
                        }

                    }
                    String status = responseObject.getString("status");
                    Log.i("Status object value", "" + status);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
    public void apicall(){

        //API call to register user

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("UserName", "" + userName);
            jsonObject.put("token", "" + otp);
            jsonObject.put("registrationMode", "Phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL+"Token?token="+otp+"&registrationMode="+"Phone"+"&userName="+userName);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
   /*     builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));*/
        Request request = builder.build();
        new HttpAsyncTask().execute(request);

    }
    public void resendOTP(){

        //API call to register user

        JSONObject jsonObject = new JSONObject();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL+"Token/Resend?userName="+phoneNumber+"&registrationMode=Phone"+"&tokenType=1");
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
//        builder =  builder.addHeader("SessionId", preferences.getSessionId());
//        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
        Request request = builder.build();
        new HttpAsyncTaskResendOTP().execute(request);

    }
    private class OTPTextWatcher implements TextWatcher{
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
    public void BackButton(View view) {
        finish();
    }

}
