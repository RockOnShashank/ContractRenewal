package com.residents.dubaiassetmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewDetailLSFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
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

public class NewPasswordActivity extends AppCompatActivity implements ResponseCallback {
    EditText et_password,et_confirm;
    private String BASE_URL,REQUEST_URL;
    private TextView tv_message;
    OkHttpClient okHttpClient = new OkHttpClient();
    private String username,tcode;
    private Button button_login;
    private SavePreference mSavePreference;
    private ImageView iv_1,iv_2,iv_back;
    private static final String SAFETY_NET_API_SITE_KEY = "6LdHW38UAAAAAJPFbzkTZK3qYkv-BzdBwTvQXEyD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_new);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_message = (TextView) findViewById(R.id.text_view_error_message);
        button_login = (Button) findViewById(R.id.button_login);
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        iv_back = (ImageView) findViewById(R.id.iv_back);





        et_confirm.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        et_password.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });






        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ViewPassword();
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ViewPassword1();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        username = getIntent().getExtras().getString("username");
        tcode = getIntent().getExtras().getString("tCode");
        mSavePreference = SavePreference.getInstance(this);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_password.getText().toString().equalsIgnoreCase(et_confirm.getText().toString())){

                    if (Password_Validation(et_password.getText().toString())) {

                        BASE_URL = IConstant.BASE_URL;

                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("UserName", username);
                            jsonObject.put("NewPassword", et_password.getText().toString());
                            jsonObject.put("ProfileId", tcode);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

if (et_password.getText().toString().equalsIgnoreCase(et_confirm.getText().toString())) {
    new RequestService(NewPasswordActivity.this, NewPasswordActivity.this).putRequestForgetPassword("Profile/UpdatePassword", jsonObject.toString(), tcode);
}else {
    tv_message.setVisibility(View.VISIBLE);
}


                    }else {
                        Toast.makeText(NewPasswordActivity.this,"Password Policy does not match.", Toast.LENGTH_LONG).show();
                    }

                }else{
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("The passwords you have entered do not match");
                }

            }
        });

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
        try {
            Log.i("Here", "" + response);
            JSONObject responseObject = new JSONObject(response);
            Log.i("NewPassResponseObject", "" + responseObject);
            if (responseObject.has("status")) {

                String status = responseObject.getString("status");

                if (status.equals("Success")) {
                    launchSuccessDialog("You have successfully created a new password. Please Login");

                   /* Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                    //  intent.putExtra("UserID",""+ userId.getText().toString());
                    //  intent.putExtra("IdType",""+idType);
                    startActivity(intent);*/
                    //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));


                } else if (status.equalsIgnoreCase("No active profile")) {
                    //errorMessage.setVisibility(View.VISIBLE);
                    // errorMessage.setText(status);

                }

            } else {
                if (responseObject.has("Status")) {

                    String status = responseObject.getString("Status");

                    if (status.equals("Success")) {
                        launchSuccessDialog("You have successfully created a new password. Please Login");

                      /*  Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                        //  intent.putExtra("UserID",""+ userId.getText().toString());
                        //  intent.putExtra("IdType",""+idType);
                        startActivity(intent);*/

                        //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));
                    } else if (status.equalsIgnoreCase("No active profile")) {
                        //errorMessage.setVisibility(View.VISIBLE);
                        // errorMessage.setText(status);
                    }

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // Forgot password async task
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewPasswordActivity.this);
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
                    Log.i("Here", "" + response);
                    assert response.body() != null;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("NewPassResponseObject", "" + responseObject);
                    if (responseObject.has("status")) {

                        String status = responseObject.getString("status");

                        if (status.equals("Success")) {

                            Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                            //  intent.putExtra("UserID",""+ userId.getText().toString());
                            //  intent.putExtra("IdType",""+idType);
                            startActivity(intent);
                            //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));


                        } else if (status.equalsIgnoreCase("No active profile")) {
                            //errorMessage.setVisibility(View.VISIBLE);
                            // errorMessage.setText(status);

                        }

                    } else {
                        if (responseObject.has("Status")) {

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")) {


                                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                                //  intent.putExtra("UserID",""+ userId.getText().toString());
                                //  intent.putExtra("IdType",""+idType);
                                startActivity(intent);

                                //startActivity(new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class));


                            } else if (status.equalsIgnoreCase("No active profile")) {
                                //errorMessage.setVisibility(View.VISIBLE);
                                // errorMessage.setText(status);

                            }

                        }

                    }
                    launchSuccessDialog("You have successfully created a new password. Please Login");


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void ViewPassword() {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (et_password.getInputType() != InputType.TYPE_CLASS_TEXT) {

            et_password.setInputType(InputType.TYPE_CLASS_TEXT);

            et_password.setSelection(et_password.getText().length());

            iv_1.setImageDrawable(res2);

        } else {

            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            et_password.setSelection(et_password.getText().length());

            iv_1.setImageDrawable(res);

        }

    }

    public void ViewPassword1() {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (et_confirm.getInputType() != InputType.TYPE_CLASS_TEXT) {

            et_confirm.setInputType(InputType.TYPE_CLASS_TEXT);

            et_confirm.setSelection(et_confirm.getText().length());

            iv_2.setImageDrawable(res2);

        } else {

            et_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            et_confirm.setSelection(et_confirm.getText().length());

            iv_2.setImageDrawable(res);

        }

    }


    private void launchSuccessDialog(String message) {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        srid.setVisibility(View.GONE);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        tv_message.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPasswordActivity.this, LoginActivity.class));
                finish();
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    public static boolean Password_Validation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
}
