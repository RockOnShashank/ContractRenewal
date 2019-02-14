package com.residents.dubaiassetmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.PasswordStrength;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;
import com.residents.dubaiassetmanagement.terms_of_service.TermsofServiceFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements TextWatcher, ResponseCallback,ResponseCallbackRecaptcha {

    private EditText userName, userPassword, userPasswordConfirm;
    private TextView textViewErrorMessage,textView2;
    private String UserIdInput, UserPassInput, UserPassConfInput;
    private Button createUser;
    private String REGISTER_URL;
  //my   private static final String SAFETY_NET_API_SITE_KEY = "6LdHW38UAAAAAJPFbzkTZK3qYkv-BzdBwTvQXEyD";
    private static final String SAFETY_NET_API_SITE_KEY = "6Lfaw4cUAAAAAA8dCs8UWt7L77Dye7DKdZC7OIeJ";
    private static final String TAG = "IdTokenActivity";
    private static final int RC_GET_TOKEN = 9002;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mIdTokenTextView;
    private Button mRefreshButton;
    private String sessionID;
    private char c0, c1, c2;


    //Facebook Login
    private LinearLayout fbButton, googleButton;
    private LoginButton FbLogin;
    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    private String fbEmailID;
    private String REQUEST_URL_REGISTER;
    private TextView tv_password_status, tv_password_mismatch, tv_login;
    private String gmailID, password,gmailToken;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInCLient;
    private static final int RC_SIGN_IN = 9001;
    private ImageView iv_conf_visible,iv_pass_visible;
private SavePreference mSavePreference;
private ApplicationPreferences preferences;
    OkHttpClient okHttpClient = new OkHttpClient();
    int len = 50;
    private int t=0;
    public static String key;

    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_new);
        mSavePreference = SavePreference.getInstance(RegisterActivity.this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpClient = builder.build();
        saveCredentials();
        preferences = ApplicationPreferences.getInstance(ApplicationController.getContext());

        try {
            PackageInfo info =     getPackageManager().getPackageInfo("MY PACKAGE NAME",     PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }




        key = preferences.getLastStoredPassword();
        // Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbButton = (LinearLayout) findViewById(R.id.face_login_button);
        FbLogin = (LoginButton) findViewById(R.id.login_fb_button);
        FbLogin.setReadPermissions("email");
        textView2 = (TextView) findViewById(R.id.textView2);

        userName = (EditText) findViewById(R.id.edit_text_email);
        userPassword = (EditText) findViewById(R.id.edit_text_password);
        userPasswordConfirm = (EditText) findViewById(R.id.edit_text_password_confirm);
        createUser = (Button) findViewById(R.id.create_user);
        textViewErrorMessage = (TextView) findViewById(R.id.register_error_message);
        tv_password_status = (TextView) findViewById(R.id.tv_password_status);
        tv_password_mismatch = (TextView) findViewById(R.id.tv_password_mismatch);
        tv_login = (TextView) findViewById(R.id.tv_login);
        googleButton = (LinearLayout) findViewById(R.id.google_login_button);
        iv_pass_visible = (ImageView) findViewById(R.id.iv_pass_visible);
        iv_conf_visible = (ImageView) findViewById(R.id.iv_conf_visible);





        userPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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

        userPasswordConfirm.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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



        userPassword.addTextChangedListener(this);

        //Google Login
        validateServerClientID();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        //Facebook login callback manager

        FbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

              String  mAccessToken = loginResult.getAccessToken().getToken();
                getUserProfile(mAccessToken);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userName.getText().toString().length() > 0 && userPassword.getText().toString().length() >0&& userPasswordConfirm.getText().toString().length() >0) {
                    createUser.setEnabled(true);
                    createUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    createUser.setEnabled(false);
                    createUser.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userName.getText().toString().length() > 0 && userPassword.getText().toString().length() >0&& userPasswordConfirm.getText().toString().length() >0) {
                    createUser.setEnabled(true);
                    createUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    createUser.setEnabled(false);
                    createUser.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }
        });
        userPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userName.getText().toString().length() > 0 && userPassword.getText().toString().length() >0&& userPasswordConfirm.getText().toString().length() >0) {
                    createUser.setEnabled(true);
                    createUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    createUser.setEnabled(false);
                    createUser.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userName.getText().toString().length() > 0 && userPassword.getText().toString().length() >0&& userPasswordConfirm.getText().toString().length() >0) {
                    createUser.setEnabled(true);
                    createUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    createUser.setEnabled(false);
                    createUser.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {





                if(userName.getText().toString().length()==3) {

                    c0 = userName.getText().toString().charAt(0);
                    c1 = userName.getText().toString().charAt(1);
                    c2 = userName.getText().toString().charAt(2);
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

/*

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
*/



        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startActivity(new Intent(RegisterActivity.this,TermsofServiceActivity.class));
                // loadFragment(new TermsofServiceFragment());
            }
        });

        ApplicationPreferences preferences1 = ApplicationPreferences.getInstance(this);
        preferences1.setCredentialsSaveEnabled(true);
        preferences1.setUsernameJwt(getString(R.string.username_jwt));
        preferences1.setPassowordJwt(getString(R.string.password_jwt));


        JSONObject jsonObjectPArent = new JSONObject();
        try {
            jsonObjectPArent.put("Username", preferences1.getUsernameJwt());
            jsonObjectPArent.put("Password", preferences1.getPasswordJwt());
        } catch (JSONException e) {
            e.printStackTrace();
        }

      //  new RequestService(RegisterActivity.this, RegisterActivity.this,RegisterActivity.this).postRequestLoginJWT("JWTLogin", jsonObjectPArent);


    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public void setEditTextMaxLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        userName.setFilters(filterArray);
    }
    public void CreateUser(View view) {
        // Showing reCAPTCHA dialog

        if(count>=5) {
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
                                Log.d("accesstoken", "Error message: " + token);
                                UserIdInput = userName.getText().toString();
                                UserPassInput = userPassword.getText().toString();
                                UserPassConfInput = userPasswordConfirm.getText().toString();

                                if (UserIdInput.isEmpty() || UserPassInput.isEmpty() || UserPassConfInput.isEmpty()) {
                                    tv_password_mismatch.setVisibility(View.GONE);
                                    textViewErrorMessage.setVisibility(View.VISIBLE);
                                    textViewErrorMessage.setText(getString(R.string.Errror_Message_Id_or_pass_empty));

                                } else if (!UserPassInput.equals(UserPassConfInput)) {
                                    textViewErrorMessage.setVisibility(View.GONE);
                                    tv_password_mismatch.setVisibility(View.VISIBLE);
                                    tv_password_mismatch.setText("Passwords do not match");
                                } else if (UserPassInput.equals(UserPassConfInput)) {
                                    tv_password_mismatch.setVisibility(View.GONE);

                                    if (!isValidEmail(UserIdInput) && !isValidPhoneNumber(UserIdInput)) {
                                        textViewErrorMessage.setVisibility(View.VISIBLE);
                                        textViewErrorMessage.setText("Invalid Email/Mobile Number.");

                                    } else {

                                        if (t == 1) {
                                            if (userName.getText().toString().length() < 11) {
                                                textViewErrorMessage.setVisibility(View.VISIBLE);
                                                textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                                tv_password_mismatch.setVisibility(View.GONE);

                                            } else {
                                                tv_password_mismatch.setVisibility(View.INVISIBLE);
                                                textViewErrorMessage.setVisibility(View.INVISIBLE);
                                                //API call to register user
                                                JSONObject jsonObject1 = new JSONObject();

                                                try {
                                                    jsonObject1.put("Username", "" + UserIdInput);
                                                    jsonObject1.put("Password", "" + UserPassInput);
                                                    jsonObject1.put("isSocial", "false");
                                                    jsonObject1.put("Captcha_Token", token);


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                                RequestBody body = RequestBody.create(JSON, jsonObject1.toString());
                                                // Request request = new Request().Builder().url().post(body).build();
                                                REGISTER_URL = IConstant.BASE_URL;
                                                Request.Builder builder = new Request.Builder();
                                                builder = builder.url(REGISTER_URL + "Registration?isAndroid=true");
                                                builder = builder.post(body);
                                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                                Request request = builder.build();
                                                if (userName.getText().toString().length() == 3) {

                                                    c0 = userName.getText().toString().charAt(0);
                                                    c1 = userName.getText().toString().charAt(1);
                                                    c2 = userName.getText().toString().charAt(2);
                                                }

                                                if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {

                                                    if (isValid(userName.getText().toString())) {
                                                        textViewErrorMessage.setVisibility(View.GONE);
                                                        new HttpAsyncTask().execute(request);
                                                    } else {
                                                        textViewErrorMessage.setVisibility(View.VISIBLE);
                                                        textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                                    }
                                                } else {
                                                    new HttpAsyncTask().execute(request);

                                                }
                                            }


                                        } else {

                                            if (Password_Validation(userPassword.getText().toString())) {
                                                tv_password_mismatch.setVisibility(View.INVISIBLE);
                                                textViewErrorMessage.setVisibility(View.INVISIBLE);
                                                //API call to register user
                                                JSONObject jsonObject2 = new JSONObject();

                                                try {
                                                    jsonObject2.put("Username", "" + UserIdInput);
                                                    jsonObject2.put("Password", "" + UserPassInput);
                                                    jsonObject2.put("isSocial", "false");
                                                    jsonObject2.put("Captcha_Token", token);


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                                RequestBody body = RequestBody.create(JSON, jsonObject2.toString());
                                                // Request request = new Request().Builder().url().post(body).build();
                                                REGISTER_URL = IConstant.BASE_URL;
                                                Request.Builder builder = new Request.Builder();
                                                builder = builder.url(REGISTER_URL + "Registration?isAndroid=true");
                                                builder = builder.post(body);
                                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                                Request request = builder.build();
                                                if (userName.getText().toString().length() == 3) {

                                                    c0 = userName.getText().toString().charAt(0);
                                                    c1 = userName.getText().toString().charAt(1);
                                                    c2 = userName.getText().toString().charAt(2);

                                                }


                                                if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {

                                                    if (isValid(userName.getText().toString())) {
                                                        textViewErrorMessage.setVisibility(View.GONE);
                                                        new HttpAsyncTask().execute(request);
                                                    } else {
                                                        textViewErrorMessage.setVisibility(View.VISIBLE);
                                                        textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                                    }
                                                } else {
                                                    new HttpAsyncTask().execute(request);

                                                }
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Password Policy does not match.", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    }

                                } else {
                                    textViewErrorMessage.setVisibility(View.VISIBLE);
                                    textViewErrorMessage.setText(R.string.pass_match_register);
                                }


                                //  new RequestService(RegisterActivity.this, RegisterActivity.this,RegisterActivity.this).setArgumentsRecaptcha("Registration/ValidateCaptcha?response=" + token +"&isAndroid=true");


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

/*
        UserIdInput = userName.getText().toString();
        UserPassInput = userPassword.getText().toString();
        UserPassConfInput = userPasswordConfirm.getText().toString();

        if (UserIdInput.isEmpty() || UserPassInput.isEmpty() || UserPassConfInput.isEmpty()) {
            tv_password_mismatch.setVisibility(View.GONE);
            textViewErrorMessage.setVisibility(View.VISIBLE);
            textViewErrorMessage.setText(getString(R.string.Errror_Message_Id_or_pass_empty));

        } else if (!UserPassInput.equals(UserPassConfInput)) {
            textViewErrorMessage.setVisibility(View.GONE);
            tv_password_mismatch.setVisibility(View.VISIBLE);
            tv_password_mismatch.setText("Passwords do not match");
        } else if (UserPassInput.equals(UserPassConfInput)) {
            tv_password_mismatch.setVisibility(View.GONE);

            if (!isValidEmail(UserIdInput) && !isValidPhoneNumber(UserIdInput)) {
                textViewErrorMessage.setVisibility(View.VISIBLE);
                textViewErrorMessage.setText("Invalid Email/Mobile Number.");

            } else {

                if (t == 1){
                    if (userName.getText().toString().length()<11) {
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                        tv_password_mismatch.setVisibility(View.GONE);

                    }else {
                        tv_password_mismatch.setVisibility(View.INVISIBLE);
                        textViewErrorMessage.setVisibility(View.INVISIBLE);
                        //API call to register user
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("Username", "" + UserIdInput);
                            jsonObject.put("Password", "" + UserPassInput);
                            jsonObject.put("isSocial", "false");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                        // Request request = new Request().Builder().url().post(body).build();
                        REGISTER_URL = IConstant.BASE_URL;
                        Request.Builder builder = new Request.Builder();
                        builder = builder.url(REGISTER_URL + "Registration");
                        builder = builder.post(body);
                        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                        Request request = builder.build();
                        new HttpAsyncTask().execute(request);
                    }


                }else {

                    if (Password_Validation(userPassword.getText().toString())) {
                        tv_password_mismatch.setVisibility(View.INVISIBLE);
                        textViewErrorMessage.setVisibility(View.INVISIBLE);
                        //API call to register user
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("Username", "" + UserIdInput);
                            jsonObject.put("Password", "" + UserPassInput);
                            jsonObject.put("isSocial", "false");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                        // Request request = new Request().Builder().url().post(body).build();
                        REGISTER_URL = IConstant.BASE_URL;
                        Request.Builder builder = new Request.Builder();
                        builder = builder.url(REGISTER_URL + "Registration");
                        builder = builder.post(body);
                        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                        Request request = builder.build();

                        new HttpAsyncTask().execute(request);
                    }else {
                        Toast.makeText(this, "Password Policy does not match.", Toast.LENGTH_LONG).show();
                    }
                }

            }

        } else {
            textViewErrorMessage.setVisibility(View.VISIBLE);
            textViewErrorMessage.setText(R.string.pass_match_register);
        }*/
        }else {

            // Received captcha token
            // This token still needs to be validated on the server
            // using the SECRET key
            UserIdInput = userName.getText().toString();
            UserPassInput = userPassword.getText().toString();
            UserPassConfInput = userPasswordConfirm.getText().toString();

            if (UserIdInput.isEmpty() || UserPassInput.isEmpty() || UserPassConfInput.isEmpty()) {
                tv_password_mismatch.setVisibility(View.GONE);
                textViewErrorMessage.setVisibility(View.VISIBLE);
                textViewErrorMessage.setText(getString(R.string.Errror_Message_Id_or_pass_empty));

            } else if (!UserPassInput.equals(UserPassConfInput)) {
                textViewErrorMessage.setVisibility(View.GONE);
                tv_password_mismatch.setVisibility(View.VISIBLE);
                tv_password_mismatch.setText("Passwords do not match");
            } else if (UserPassInput.equals(UserPassConfInput)) {
                tv_password_mismatch.setVisibility(View.GONE);

                if (!isValidEmail(UserIdInput) && !isValidPhoneNumber(UserIdInput)) {
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText("Invalid Email/Mobile Number.");

                } else {

                    if (t == 1) {
                        if (userName.getText().toString().length() < 11) {
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                            tv_password_mismatch.setVisibility(View.GONE);

                        } else {
                            tv_password_mismatch.setVisibility(View.INVISIBLE);
                            textViewErrorMessage.setVisibility(View.INVISIBLE);
                            //API call to register user
                            JSONObject jsonObject1 = new JSONObject();

                            try {
                                jsonObject1.put("Username", "" + UserIdInput);
                                jsonObject1.put("Password", "" + UserPassInput);
                                jsonObject1.put("isSocial", "false");
                                jsonObject1.put("Captcha_Token", "");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                            RequestBody body = RequestBody.create(JSON, jsonObject1.toString());
                            // Request request = new Request().Builder().url().post(body).build();
                            REGISTER_URL = IConstant.BASE_URL;
                            Request.Builder builder = new Request.Builder();
                            builder = builder.url(REGISTER_URL + "Registration?isAndroid=true");
                            builder = builder.post(body);
                            builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                            Request request = builder.build();
                            if (userName.getText().toString().length() == 3) {

                                c0 = userName.getText().toString().charAt(0);
                                c1 = userName.getText().toString().charAt(1);
                                c2 = userName.getText().toString().charAt(2);
                            }

                            if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {

                                if (isValid(userName.getText().toString())) {
                                    textViewErrorMessage.setVisibility(View.GONE);
                                    new HttpAsyncTask().execute(request);
                                } else {
                                    textViewErrorMessage.setVisibility(View.VISIBLE);
                                    textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                }
                            } else {
                                new HttpAsyncTask().execute(request);

                            }
                        }


                    } else {

                        if (Password_Validation(userPassword.getText().toString())) {
                            tv_password_mismatch.setVisibility(View.INVISIBLE);
                            textViewErrorMessage.setVisibility(View.INVISIBLE);
                            //API call to register user
                            JSONObject jsonObject2 = new JSONObject();

                            try {
                                jsonObject2.put("Username", "" + UserIdInput);
                                jsonObject2.put("Password", "" + UserPassInput);
                                jsonObject2.put("isSocial", "false");
                                jsonObject2.put("Captcha_Token", "");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                            RequestBody body = RequestBody.create(JSON, jsonObject2.toString());
                            // Request request = new Request().Builder().url().post(body).build();
                            REGISTER_URL = IConstant.BASE_URL;
                            Request.Builder builder = new Request.Builder();
                            builder = builder.url(REGISTER_URL + "Registration?isAndroid=true");
                            builder = builder.post(body);
                            builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                            Request request = builder.build();
                            if (userName.getText().toString().length() == 3) {

                                c0 = userName.getText().toString().charAt(0);
                                c1 = userName.getText().toString().charAt(1);
                                c2 = userName.getText().toString().charAt(2);

                            }


                            if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {

                                if (isValid(userName.getText().toString())) {
                                    textViewErrorMessage.setVisibility(View.GONE);
                                    new HttpAsyncTask().execute(request);
                                } else {
                                    textViewErrorMessage.setVisibility(View.VISIBLE);
                                    textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                }
                            } else {
                                new HttpAsyncTask().execute(request);

                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Password Policy does not match.", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            } else {
                textViewErrorMessage.setVisibility(View.VISIBLE);
                textViewErrorMessage.setText(R.string.pass_match_register);
            }


            //  new RequestService(RegisterActivity.this, RegisterActivity.this,RegisterActivity.this).setArgumentsRecaptcha("Registration/ValidateCaptcha?response=" + token +"&isAndroid=true");

        }
    }


    //Method to get user details (Facebook Graph request)

    private void getUserProfile(final String mAccessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //You can fetch user info like this…
                            //object.getJSONObject(“picture”).
                            //getJSONObject(“data”).getString(“url”);
                            //object.getString(“name”);
                            //object.getString("email");
                            //editTextUserId.setText(object.getString("email"));
                            fbEmailID = object.getString("email");
                            //object.getString(“id”));

                            // Takes us to socialAsync activity, feeds the email id from facebook
                            if (!fbEmailID.isEmpty()) {

                                //API call to register user

                              /*  JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("Username", "" + fbEmailID);
                                    jsonObject.put("Password", "");
                                    jsonObject.put("isSocial", "true");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                             //   new RequestService(RegisterActivity.this, RegisterActivity.this).postRequest("Registration", jsonObject);
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("Access_Token", mAccessToken);
                                    jsonObject.put("Id_Token", "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                new RequestService(RegisterActivity.this, RegisterActivity.this).postRequestFacebook("Registration/SocialLogin", jsonObject);



/*
                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                REGISTER_URL = IConstant.BASE_URL;
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REGISTER_URL + "Registration");
                               builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);

                                builder = builder.post(body);
                                Request request = builder.build();

                                new HttpAsyncTaskSocialLogin().execute(request);*/

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    public void FBloginOnClick(View view) {

        FbLogin.performClick();
        userName.setText("");
        userPassword.setText("");
        userPasswordConfirm.setText("");

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tv_password_status.setVisibility(View.GONE);
        updatePasswordStrengthText(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

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
            saveCredentialsAfterSignInFacebook(sessionId);
            JSONObject responseObject = new JSONObject(response);
            Log.i("Response Object", "" + responseObject);

            if (responseObject.has("tCode")) {

                String tcode = responseObject.getString("tCode");
                String status = responseObject.getString("status");

                Log.i("tCOde Value", "" + tcode);

                if (tcode.equalsIgnoreCase("null")) {
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(status);

                } else {
                    HomeActivity.sendDeviceToken=1;
                    mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                    mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                    mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                    //   mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                }

            } else {
                if (responseObject.has("TCode")) {

                    String tcode = responseObject.getString("TCode");
                    String status = responseObject.getString("Status");

                    Log.i("tCOde Value", "" + tcode);

                    if (tcode.equalsIgnoreCase("null")) {
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(status);

                    } else {
                        HomeActivity.sendDeviceToken=1;
                        mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                        mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                        mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("Tenant_Name"));

                        //      mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    }


                    LoginManager.getInstance().logOut();
                    String status1 = responseObject.getString("status");
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(status1);
                    Log.i("Status object value", "" + status1);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessRecaptcha(String response) {

        String res =  response;

        try {
            JSONObject jsonObject = new JSONObject(res);

            String status = jsonObject.getString("success");
            if (status.equalsIgnoreCase("true")){
                UserIdInput = userName.getText().toString();
                UserPassInput = userPassword.getText().toString();
                UserPassConfInput = userPasswordConfirm.getText().toString();

                if (UserIdInput.isEmpty() || UserPassInput.isEmpty() || UserPassConfInput.isEmpty()) {
                    tv_password_mismatch.setVisibility(View.GONE);
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(getString(R.string.Errror_Message_Id_or_pass_empty));

                } else if (!UserPassInput.equals(UserPassConfInput)) {
                    textViewErrorMessage.setVisibility(View.GONE);
                    tv_password_mismatch.setVisibility(View.VISIBLE);
                    tv_password_mismatch.setText("Passwords do not match");
                } else if (UserPassInput.equals(UserPassConfInput)) {
                    tv_password_mismatch.setVisibility(View.GONE);

                    if (!isValidEmail(UserIdInput) && !isValidPhoneNumber(UserIdInput)) {
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText("Invalid Email/Mobile Number.");

                    } else {

                        if (t == 1){
                            if (userName.getText().toString().length()<11) {
                                textViewErrorMessage.setVisibility(View.VISIBLE);
                                textViewErrorMessage.setText("Invalid Email/Mobile Number.");
                                tv_password_mismatch.setVisibility(View.GONE);

                            }else {
                                tv_password_mismatch.setVisibility(View.INVISIBLE);
                                textViewErrorMessage.setVisibility(View.INVISIBLE);
                                //API call to register user
                                JSONObject jsonObject1 = new JSONObject();

                                try {
                                    jsonObject1.put("Username", "" + UserIdInput);
                                    jsonObject1.put("Password", "" + UserPassInput);
                                    jsonObject1.put("isSocial", "false");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject1.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                REGISTER_URL = IConstant.BASE_URL;
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REGISTER_URL + "Registration");
                                builder = builder.post(body);
                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                Request request = builder.build();
                                new HttpAsyncTask().execute(request);
                            }


                        }else {

                            if (Password_Validation(userPassword.getText().toString())) {
                                tv_password_mismatch.setVisibility(View.INVISIBLE);
                                textViewErrorMessage.setVisibility(View.INVISIBLE);
                                //API call to register user
                                JSONObject jsonObject2 = new JSONObject();

                                try {
                                    jsonObject2.put("Username", "" + UserIdInput);
                                    jsonObject2.put("Password", "" + UserPassInput);
                                    jsonObject2.put("isSocial", "false");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject2.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                REGISTER_URL = IConstant.BASE_URL;
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REGISTER_URL + "Registration");
                                builder = builder.post(body);
                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                Request request = builder.build();

                                new HttpAsyncTask().execute(request);
                            }else {
                                Toast.makeText(this, "Password Policy does not match.", Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                } else {
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(R.string.pass_match_register);
                }

            }else {
                Toast.makeText(this,"reCaptcha Validation Failed!",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSuccessJWT(String response) {
        String res =  response;
        preferences.setAccessToken(res);
    }

    //Http Request is handled below
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
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
                Log.i("RegisterResponse", "" + response);
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
                sessionID = response.header("SessionId");
                saveCredentialsAfterSignIn();
                try {

                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("RegisterResponse", "" + responseObject);
                    if (responseObject.has("tCode")) {
                        String tcode = responseObject.getString("tCode");
                        Log.i("tCOde Value", "" + tcode);
                        //startActivity(new Intent(RegisterActivity.this, UserVerificationActivity.class));
                        if (!tcode.equalsIgnoreCase("null")) {
                            String code = responseObject.getString("code");
                            if (code.equalsIgnoreCase("403")) {
                                count++;
                                String status = responseObject.getString("status");
                                textViewErrorMessage.setVisibility(View.VISIBLE);
                                textViewErrorMessage.setText(status);

                            } else {

                                if (isValidEmail(UserIdInput)) {
                                    Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                                    intent.putExtra("email_id", UserIdInput);
                                    startActivity(intent);
                                } else if (isValidPhoneNumber(UserIdInput)) {
                                    Intent intent = new Intent(RegisterActivity.this, RegisterOTPActivity.class);
                                    intent.putExtra("phoneNumber", UserIdInput);
                                    intent.putExtra("userName", UserIdInput);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            count++;
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText("This username is not registered!");
                        }

                    } else {


                        if (responseObject.has("TCode")) {
                            String tcode = responseObject.getString("TCode");
                            String status = responseObject.getString("Status");

                            Log.i("tCOde Value", "" + tcode);
                            if (status.equalsIgnoreCase("Success")) {
                                if (isValidEmail(UserIdInput)) {
                                    Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                                    intent.putExtra("email_id", UserIdInput);
                                    intent.putExtra("profiles", "profile");

                                    startActivity(intent);
                                } else if (isValidPhoneNumber(UserIdInput)) {
                                    Intent intent = new Intent(RegisterActivity.this, RegisterOTPActivity.class);
                                    intent.putExtra("phoneNumber", UserIdInput);
                                    intent.putExtra("userName", UserIdInput);
                                    startActivity(intent);
                                }
                            } else {
                                count++;
                                textViewErrorMessage.setVisibility(View.VISIBLE);
                                textViewErrorMessage.setText(status);
                            }
                            if (status.equalsIgnoreCase("Pending")){
                                textViewErrorMessage.setVisibility(View.GONE);
                                if (isValidEmail(UserIdInput)) {
                                    ResendLink();
                                } else if (isValidPhoneNumber(UserIdInput)) {
                                    resendOTP();

                                }




                            }

                       /* String status = responseObject.getString("status");
                        Log.i("Status object value", "" + status);
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(status);*/
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

    //Social Login Async Task

    class HttpAsyncTaskSocialLogin extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering user ..");
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

            try {

                sessionID = response.header("SessionId");
                saveCredentialsAfterSignIn();

                JSONObject responseObject = new JSONObject(response.body().string());
                Log.i("Response Object", "" + responseObject);
                progressDialog.dismiss();

                if (responseObject.has("tCode")) {

                    String tcode = responseObject.getString("tCode");
                    String status = responseObject.getString("status");

                    Log.i("tCOde Value", "" + tcode);

                    if (tcode.equalsIgnoreCase("null")) {
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(status);

                    } else {
                        HomeActivity.sendDeviceToken=1;
                        mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                        mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                        mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                     //   mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    }

                } else {
                    if (responseObject.has("TCode")) {

                        String tcode = responseObject.getString("TCode");
                        String status = responseObject.getString("Status");

                        Log.i("tCOde Value", "" + tcode);

                        if (tcode.equalsIgnoreCase("null")) {
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(status);

                        } else {
                            HomeActivity.sendDeviceToken=1;
                            mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                            mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                            mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("Tenant_Name"));

                            //      mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            finish();
                        }


                        LoginManager.getInstance().logOut();
                        String status1 = responseObject.getString("status");
                       // textViewErrorMessage.setVisibility(View.VISIBLE);
                        //textViewErrorMessage.setText(status1);
                        Log.i("Status object value", "" + status1);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private void updatePasswordStrengthText(String password) {

        TextView strengthView = (TextView) findViewById(R.id.tv_password_status);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());
    }

    public void openLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
    //Update UI Google Method

    //Google signin OnActivity result



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For Google
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            // This task is always completed immediately, there is no need to attach an
            // asynchronous listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // [END get_id_token]
        } else {
            //For FB
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }

    // Google Login Button Click
    public void GoogleLogin(View view) {
        userName.setText("");
        userPassword.setText("");
        userPasswordConfirm.setText("");
        getIdToken();
        /*Intent signInIntent = mGoogleSignInCLient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);*/

    }

    public void ViewPassword(View view) {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (userPassword.getInputType() != InputType.TYPE_CLASS_TEXT) {

            userPassword.setInputType(InputType.TYPE_CLASS_TEXT);

            userPassword.setSelection(userPassword.getText().length());

            iv_pass_visible.setImageDrawable(res2);

        } else {

            userPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            userPassword.setSelection(userPassword.getText().length());

            iv_pass_visible.setImageDrawable(res);

        }


    }

    public void ViewPasswordConfirm(View view) {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (userPasswordConfirm.getInputType() != InputType.TYPE_CLASS_TEXT) {

            userPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT);

            userPasswordConfirm.setSelection(userPasswordConfirm.getText().length());

            iv_conf_visible.setImageDrawable(res2);

        } else {

            userPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            userPasswordConfirm.setSelection(userPasswordConfirm.getText().length());

            iv_conf_visible.setImageDrawable(res);

        }
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

private void sendGmailToken(){
    JSONObject jsonObject = new JSONObject();
    try {
        jsonObject.put("Access_Token", "");
        jsonObject.put("Id_Token", gmailToken);

    } catch (JSONException e) {
        e.printStackTrace();
    }

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    RequestBody body = RequestBody.create(JSON, jsonObject.toString());
    // Request request = new Request().Builder().url().post(body).build();
    REGISTER_URL = IConstant.BASE_URL;
    Request.Builder builder = new Request.Builder();
    builder = builder.url(REGISTER_URL+"Registration/SocialLogin");
    builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
    builder = builder.post(body);
    Request request = builder.build();

    new HttpAsyncTaskSocialLogin().execute(request);
}

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }
    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    // [START handle_sign_in_result]
    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            // TODO(developer): send ID Token to server and validate

            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:error", e);
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            //  ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);

            String idToken = account.getIdToken();
            gmailToken = account.getIdToken();
            sendGmailToken();
          //  Toast.makeText(this, idToken, Toast.LENGTH_LONG).show();

            //  mIdTokenTextView.setText(getString(R.string.id_token_fmt, idToken));

            //  findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            //mRefreshButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();

            //  ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
            //   mIdTokenTextView.setText(getString(R.string.id_token_fmt, "null"));

            //  findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //   findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            //mRefreshButton.setVisibility(View.GONE);
        }
    }
    private void saveCredentials() {
        ApplicationPreferences preferences = ApplicationPreferences.getInstance(this);
        preferences.setCredentialsSaveEnabled(true);
        preferences.savePassword(BuildConfig.KEY_STORE);
        //preferences.savePassword(IConstant.KEY_STORE);

    }
    private void saveCredentialsAfterSignIn() {
        ApplicationPreferences preferences = ApplicationPreferences.getInstance(this);
        preferences.setCredentialsSaveEnabled(true);
       preferences.savePassword(BuildConfig.KEY_STORE);
      //  preferences.savePassword(IConstant.KEY_STORE);

        preferences.setSessionId(sessionID);
    }
    private void saveCredentialsAfterSignInFacebook(String sessionID) {
        ApplicationPreferences preferences = ApplicationPreferences.getInstance(this);
        preferences.setCredentialsSaveEnabled(true);
        preferences.savePassword(BuildConfig.KEY_STORE);
      //  preferences.savePassword(IConstant.KEY_STORE);

        preferences.setSessionId(sessionID);
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
    public void resendOTP(){

        //API call to register user

        JSONObject jsonObject = new JSONObject();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL+"Token/Resend?userName="+userName.getText().toString()+"&registrationMode=Phone"+"&tokenType=1");
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
//        builder =  builder.addHeader("SessionId", preferences.getSessionId());
//        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
        Request request = builder.build();
        new HttpAsyncTaskResendOTP().execute(request);

    }
    //Http Request is handled below
    class HttpAsyncTaskResendOTP extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
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
            try {

                JSONObject responseObject = new JSONObject(response.body().string());
                Log.i("Response Object",""+responseObject);
                if (responseObject.toString().contains("tCode")) {
                    String tcode = responseObject.getString("tCode");
                    Log.i("tCOde Value", "" + tcode);
                    if (isValidEmail(UserIdInput)) {
                        Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                        intent.putExtra("email_id", UserIdInput);
                        intent.putExtra("profiles", "profile");

                        startActivity(intent);
                    } else if (isValidPhoneNumber(UserIdInput)) {
                        Intent intent = new Intent(RegisterActivity.this, RegisterOTPActivity.class);
                        intent.putExtra("phoneNumber", UserIdInput);
                        intent.putExtra("userName", UserIdInput);
                        startActivity(intent);
                    }

                } else {
                    if (responseObject.toString().contains("Status")) {
                        if (isValidEmail(UserIdInput)) {
                            Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                            intent.putExtra("email_id", UserIdInput);
                            intent.putExtra("profiles", "profile");

                            startActivity(intent);
                        } else if (isValidPhoneNumber(UserIdInput)) {
                            Intent intent = new Intent(RegisterActivity.this, RegisterOTPActivity.class);
                            intent.putExtra("phoneNumber", UserIdInput);
                            intent.putExtra("userName", UserIdInput);
                            startActivity(intent);
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
    public void ResendLink() {
       String BASE_URL = IConstant.BASE_URL;
      String  REQUEST_URL = BASE_URL+"Token/Resend" + "?userName=" +userName.getText().toString() + "&registrationMode=" + "Email" + "&tokenType=1";

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

    class CallResendOtp extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
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
                            Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                            intent.putExtra("email_id", UserIdInput);

                            intent.putExtra("profiles", "profile");
                            startActivity(intent);
                            //tv_errorMessage.setVisibility(View.VISIBLE);
                            //  tv_errorMessage.setText(status);
                        }
                        else {
                        }

                    }else {
                        if (responseObject.has("Status")) {

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")) {
                                Intent intent = new Intent(RegisterActivity.this, UserVerificationActivity.class);
                                intent.putExtra("email_id", UserIdInput);
                                intent.putExtra("profiles", "profile");
                                startActivity(intent);
                                //tv_errorMessage.setVisibility(View.VISIBLE);
                                //  tv_errorMessage.setText(status);
                            }else {

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
}
