package com.residents.dubaiassetmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements ResponseCallbackRecaptcha, ResponseCallback {

    private EditText userId;
    private Button btnSubmit;
    private TextView errorMessage;
    private String REQUEST_URL,BASE_URL, idType;
    private static final String SAFETY_NET_API_SITE_KEY = "6Lfaw4cUAAAAAA8dCs8UWt7L77Dye7DKdZC7OIeJ";

    OkHttpClient okHttpClient = new OkHttpClient();
    private char c0, c1, c2;
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        userId = (EditText) findViewById(R.id.edit_text_email_mobile);
        btnSubmit = (Button) findViewById(R.id.forgot_pass_submit_btn);
        errorMessage = (TextView) findViewById(R.id.text_view_error_message_forgot_pass);
        userId.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(userId.getText().toString().length()==3) {

                    c0 = userId.getText().toString().charAt(0);
                    c1 = userId.getText().toString().charAt(1);
                    c2 = userId.getText().toString().charAt(2);
                }
                if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {
                    // Remove spacing char
                    if (s.length() > 0 && (s.length() % 4) == 0) {
                        final char c = s.charAt(s.length() - 1);
                        if (space == c) {
                            // s.delete(s.length() - 1, s.length());
                        }
                    }
                    // Insert char where needed.
                    if (s.length() == 0) {
                        v = 0;
                    }
                    if (s.length() == 3) {
                        v = 0;
                    }

                    if (s.length() > 0 && (s.length() % 4) == 0 && v == 0) {
                        char c = s.charAt(s.length() - 1);
                        v = 1;
                        // Only if its a digit where there should be a space we insert a space
                        if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                            s.insert(s.length() - 1, String.valueOf(space));
                        }
                    }
                }
            }
        });

    }

    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void BackButton(View view) {
        finish();
    }

    public void SubmitForgotPassword(View view) {

      //  if (PhoneNumberUtils.isGlobalPhoneNumber(userId.getText().toString())) {
if (count>=5) {

    // Showing reCAPTCHA dialog
    SafetyNet.getClient(this).verifyWithRecaptcha(SAFETY_NET_API_SITE_KEY)
            .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                @Override
                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                    Log.d("RegisterActivity", "onSuccess");

                    if (!response.getTokenResult().isEmpty()) {

                        // Received captcha token
                        // This token still needs to be validated on the server
                        // using the SECRET key
                        String token = response.getTokenResult();
                        if (isEmailValid(userId.getText().toString())) {

                            idType = "Email";

                        } else {
                            idType = "Phone";

                        }
                        BASE_URL = IConstant.BASE_URL;


                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        JSONObject jsonObject1 = new JSONObject();

                        REQUEST_URL = BASE_URL + "Token/Resend?isAndroid=true" + "&userName=" + userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3" + "&Captcha_Token=" + token;
                        // REQUEST_URL = BASE_URL+"Profile/UpdatePassword" + "?userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";


                        //final Request request = new Request.Builder().url(REQUEST_URL).get().build();
//        new HttpAsyncTask().execute(request);
//        final Request request = new Request().Builder.url(REQUEST_URL).put().build();

                        Request.Builder builder = new Request.Builder();
                        RequestBody body = new FormBody.Builder().build();
                        builder = builder.url(REQUEST_URL);
                        builder = builder.put(body);
                        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);

                        Request request = builder.build();

//           OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

//
//            OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);
                        if (userId.getText().toString().length() == 3) {

                            c0 = userId.getText().toString().charAt(0);
                            c1 = userId.getText().toString().charAt(1);
                            c2 = userId.getText().toString().charAt(2);
                        }
                        if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {
                            if (isValid(userId.getText().toString())) {
                                errorMessage.setVisibility(View.GONE);

                                new HttpAsyncTaskSocialLogin().execute(request);
                            } else {
                                errorMessage.setVisibility(View.VISIBLE);
                                errorMessage.setText("Invalid Email/Mobile Number.");
                            }
                        } else {
                            new HttpAsyncTaskSocialLogin().execute(request);

                        }
       /* } else {

            if (userId.getText().toString().isEmpty()) {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);

            } else {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.validate_userid_error);

            }

        }*/
                        //new RequestService(ForgotPasswordActivity.this, ForgotPasswordActivity.this,ForgotPasswordActivity.this).setArgumentsRecaptcha("Registration/ValidateCaptcha?response=" + token +"&isAndroid=true");

                    }
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ApiException) {
                        ApiException apiException = (ApiException) e;
                        Log.d("RegisterActivity", "Error message: " +
                                CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                    } else {
                        Log.d("RegisterActivity", "Unknown type of error: " + e.getMessage());
                    }
                }
            });
           /* BASE_URL = IConstant.BASE_URL;



            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();

    REQUEST_URL = BASE_URL+"Token/Resend?isAndroid=true" + "&userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";
       // REQUEST_URL = BASE_URL+"Profile/UpdatePassword" + "?userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";






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

           new HttpAsyncTaskSocialLogin().execute(request);

       *//* } else {

            if (userId.getText().toString().isEmpty()) {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);

            } else {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.validate_userid_error);

            }

        }*/


}else {
    // Received captcha token
    // This token still needs to be validated on the server
    // using the SECRET key
    if (isEmailValid(userId.getText().toString())) {

        idType = "Email";

    } else {
        idType = "Phone";

    }
    BASE_URL = IConstant.BASE_URL;


    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    JSONObject jsonObject1 = new JSONObject();

    REQUEST_URL = BASE_URL + "Token/Resend?isAndroid=true" + "&userName=" + userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3" + "&Captcha_Token=" + "";
    // REQUEST_URL = BASE_URL+"Profile/UpdatePassword" + "?userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";


    //final Request request = new Request.Builder().url(REQUEST_URL).get().build();
//        new HttpAsyncTask().execute(request);
//        final Request request = new Request().Builder.url(REQUEST_URL).put().build();

    Request.Builder builder = new Request.Builder();
    RequestBody body = new FormBody.Builder().build();
    builder = builder.url(REQUEST_URL);
    builder = builder.put(body);
    builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);

    Request request = builder.build();

//           OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);

//
//            OkHttpHandler okHttpHandler = new OkHttpHandler();
//            okHttpHandler.execute(REQUEST_URL);
    if (userId.getText().toString().length() == 3) {

        c0 = userId.getText().toString().charAt(0);
        c1 = userId.getText().toString().charAt(1);
        c2 = userId.getText().toString().charAt(2);
    }
    if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {
        if (isValid(userId.getText().toString())) {
            errorMessage.setVisibility(View.GONE);

            new HttpAsyncTaskSocialLogin().execute(request);
        } else {
            count++;
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Invalid Email/Mobile Number.");
        }
    } else {
        new HttpAsyncTaskSocialLogin().execute(request);

    }
}
    }

    @Override
    public void onSuccessRecaptcha(String response) {

        String res =  response;

        try {
            JSONObject jsonObject = new JSONObject(res);

            String status = jsonObject.getString("success");
            if (status.equalsIgnoreCase("true")){

        if (isEmailValid(userId.getText().toString())){

            idType = "Email";

        } else {
            idType = "Phone";

        }
        BASE_URL = IConstant.BASE_URL;



        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject1 = new JSONObject();

        REQUEST_URL = BASE_URL+"Token/Resend" + "?userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";
        // REQUEST_URL = BASE_URL+"Profile/UpdatePassword" + "?userName=" +userId.getText().toString() + "&registrationMode=" + idType + "&tokenType=3";






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

        new HttpAsyncTaskSocialLogin().execute(request);

       /* } else {

            if (userId.getText().toString().isEmpty()) {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);

            } else {

                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(R.string.validate_userid_error);

            }

        }*/

            }else {
                Toast.makeText(this,"reCaptcha Validation Failed!",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessJWT(String response) {

    }

    @Override
    public void onSuccess(String response) {

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


    // Forgot password async task
    class HttpAsyncTaskSocialLogin extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage("Submitting...");
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
                        if (idType.equalsIgnoreCase("Phone")) {

                            Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class);
                            intent.putExtra("UserID", "" + userId.getText().toString());
                            intent.putExtra("IdType", "" + idType);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(ForgotPasswordActivity.this, UserVerificationActivity.class);
                            intent.putExtra("UserID", "" + userId.getText().toString());
                            intent.putExtra("IdType", "" + idType);
                            intent.putExtra("profiles", "profile");

                            startActivity(intent);
                        }
                        //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));


                    } else if(status.equalsIgnoreCase("No active profile"))
                    {
                        count++;
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText(status);

                    }

                }
else {
                    if (responseObject.has("Status")){

                        String status = responseObject.getString("Status");

                        if (status.equals("Success")){
if (idType.equalsIgnoreCase("Phone")) {
    Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class);
    intent.putExtra("UserID", "" + userId.getText().toString());
    intent.putExtra("IdType", "" + idType);
    startActivity(intent);
    //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));
}else {
    Intent intent = new Intent(ForgotPasswordActivity.this, UserVerificationActivity.class);
    intent.putExtra("UserID", "" + userId.getText().toString());
    intent.putExtra("IdType", "" + idType);
    intent.putExtra("profiles", "profile");

    startActivity(intent);
}

                        } else if(status.equalsIgnoreCase("Invalid Email/Mobile Number."))
                        {
                            count++;
                            errorMessage.setVisibility(View.VISIBLE);
                            errorMessage.setText(status);

                        }else if (status.equalsIgnoreCase("No Active Profile")){
                            count++;
                            errorMessage.setVisibility(View.VISIBLE);
                            errorMessage.setText("We could not find an account linked to the email/mobile number entered ");
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



    public static boolean isValid(String s)
    {
        Pattern p;
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        if (BuildConfig.BaseUrl.equalsIgnoreCase("https://dubaiam-apigateway-qa.azure-api.net/api/")) {


            String reg= "^(?:\\+0|0|0)?(?:60|61|62|63|64|65|66|67|68|69|50|51|52|53|54|55|56|57|58|59) ?\\d{7}$";
            String regex = "((064) ?){7}[0-9]$";
            p = Pattern.compile(reg);
        }else {
            String regex = "((054) ?){7}[0-9]$";
            String reg= "^(?:\\+0|0|0)?(?:50|51|52|53|54|55|56|57|58|59) ?\\d{7}$";
            p = Pattern.compile(reg);

        }
        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    }




