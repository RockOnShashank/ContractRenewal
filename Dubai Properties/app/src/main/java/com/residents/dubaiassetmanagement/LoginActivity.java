package com.residents.dubaiassetmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;
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
import com.google.android.gms.common.SignInButton;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class LoginActivity extends AppCompatActivity implements ResponseCallback , ResponseCallbackRecaptcha {

    private String BASE_URL, REGISTER_URL;
    private EditText editTextUserId, editTextPassword;
    private TextView textViewErrorMessage;
    private Button buttonLogin;
    private String UserIdInput, UserPasswordInput, REQUEST_URL;
    //my key
    //private static final String SAFETY_NET_API_SITE_KEY = "6LdHW38UAAAAAJPFbzkTZK3qYkv-BzdBwTvQXEyD";

    //dubai key
    private static final String SAFETY_NET_API_SITE_KEY = "6Lfaw4cUAAAAAA8dCs8UWt7L77Dye7DKdZC7OIeJ";


    //    private CheckBox checkBoxRememberMe;
    // Google Login
    private LinearLayout googleButton;
    private SignInButton signInButton;
    private String gmailID, gmailToken;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInCLient;
    private static final int RC_SIGN_IN = 9001;
    //Facebook Login
    private LinearLayout fbButton;
    private LoginButton FbLogin;
    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    private String fbEmailID;
    private String REQUEST_URL_REGISTER, sessionId;

    private ImageView viewPassword;

    SharedPreferences loginPreferences;
    SharedPreferences.Editor editor;
    private Boolean saveLogin;
    private SavePreference mSavePreference;
    //private static final String header = IConstant.KEY;
    String tcode;
    private String regId = null;
    private static final char space = ' ';
    private int v = 0;
    private char c0, c1, c2;
    private int isLogin = 0;
    OkHttpClient okHttpClient = new OkHttpClient();

    private EnCryptor encryptor;
    private DeCryptor decryptor;
    private static final String SAMPLE_ALIAS = "MYALIAS";
    ApplicationPreferences preferences;
    String tvEncryptedText, tvDecryptedText, edTextToEncrypt;
    public static String key;


    private static final String TAG = "IdTokenActivity";
    private static final int RC_GET_TOKEN = 9002;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mIdTokenTextView;
    private Button mRefreshButton;
private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);


//
// Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.residents.dubaiassetmanagement",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        saveCredentials();
        preferences = ApplicationPreferences.getInstance(ApplicationController.getContext());

        key = preferences.getLastStoredPassword();

        //
        mSavePreference = SavePreference.getInstance(LoginActivity.this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        editTextUserId = (EditText) findViewById(R.id.edit_text_email);


        editTextUserId.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextUserId.getText().toString().length() == 3) {

                    c0 = editTextUserId.getText().toString().charAt(0);
                    c1 = editTextUserId.getText().toString().charAt(1);
                    c2 = editTextUserId.getText().toString().charAt(2);
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

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("Home_List", "");


        prefsEditor.commit();
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);


        editTextUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextPassword.getText().toString().length() > 0 && editTextUserId.getText().toString().length() >0) {
                    buttonLogin.setEnabled(true);
                    buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    buttonLogin.setEnabled(false);
                    buttonLogin.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextPassword.getText().toString().length() > 0 && editTextUserId.getText().toString().length() >0) {
                    buttonLogin.setEnabled(true);
                    buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    buttonLogin.setEnabled(false);
                    buttonLogin.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }
        });
editTextPassword.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editTextPassword.getText().toString().length() > 0 && editTextUserId.getText().toString().length() >0) {
            buttonLogin.setEnabled(true);
            buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            buttonLogin.setEnabled(false);
            buttonLogin.setBackgroundColor(getResources().getColor(R.color.disabled_button));

        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (editTextPassword.getText().toString().length() > 0 && editTextUserId.getText().toString().length() >0) {
            buttonLogin.setEnabled(true);
            buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            buttonLogin.setEnabled(false);
            buttonLogin.setBackgroundColor(getResources().getColor(R.color.disabled_button));

        }
    }
});
        viewPassword = (ImageView) findViewById(R.id.view_password_login);

        textViewErrorMessage = (TextView) findViewById(R.id.text_view_error_log_in);

        buttonLogin = (Button) findViewById(R.id.button_login);
//        checkBoxRememberMe = (CheckBox)findViewById(R.id.checkbox_remember_me);

        // Google Login
        //Google Login
        validateServerClientID();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Facebook login
        fbButton = (LinearLayout) findViewById(R.id.face_login_button);
        FbLogin = (LoginButton) findViewById(R.id.login_fb_button);
        FbLogin.setReadPermissions("email");


        // Shared Preference remember me implementation
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        editor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
//        if (saveLogin == true){
//            editTextUserId.setText(loginPreferences.getString("username",""));
//            editTextPassword.setText(loginPreferences.getString("password",""));
//            checkBoxRememberMe.setChecked(true);
//        }

        //Facebook login callback manager

        FbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String mAccessToken = loginResult.getAccessToken().getToken();
                getUserProfile(mAccessToken);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

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

       // new RequestService(LoginActivity.this, LoginActivity.this,LoginActivity.this).postRequestLoginJWT("JWTLogin", jsonObjectPArent);

    }

    //Google signin OnActivity result




    //Update UI Google Method



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


                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("Access_Token", mAccessToken);
                                    jsonObject.put("Id_Token", "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                new RequestService(LoginActivity.this, LoginActivity.this).postRequestFacebook("Registration/SocialLogin", jsonObject);


                              /*  try {
                                    jsonObject.put("Username", "" + fbEmailID);
                                    jsonObject.put("Password", "");
                                    jsonObject.put("isSocial", "true");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                new RequestService(LoginActivity.this, LoginActivity.this).postRequest("Registration", jsonObject);
*/
                               /* MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                REQUEST_URL_REGISTER = IConstant.BASE_URL+"Registration";
                              //  REQUEST_URL_REGISTER = UrlHelper.getConfigValue(LoginActivity.this,"REGISTER_URL");
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REQUEST_URL_REGISTER);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //For FB
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //For Google
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            // This task is always completed immediately, there is no need to attach an
            // asynchronous listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // [END get_id_token]
        }

    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    //Login Button Onclick

    public void LoginAuthentication(View view) {
      //  Crashlytics.getInstance().crash(); // Force a crash

        if (BuildConfig.BaseUrl.equalsIgnoreCase("https://dubaiam-apigateway-qa.azure-api.net/api/")){
    Log.d("RegisterActivity", "onSuccess");
}else {
    Log.d("RegisterActivity", "onSuccess");

}




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

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editTextUserId.getWindowToken(), 0);

                        UserIdInput = editTextUserId.getText().toString();
                        UserPasswordInput = editTextPassword.getText().toString();


                        if (UserIdInput.isEmpty() || UserPasswordInput.isEmpty()) {
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);
                        } else {

                            BASE_URL = IConstant.BASE_URL;

                            Log.i("Base URL", "" + BASE_URL);

                            JSONObject jsonObjectPArent = new JSONObject();
                            try {
                                jsonObjectPArent.put("UserName", UserIdInput);
                                jsonObjectPArent.put("Password", UserPasswordInput);
                                jsonObjectPArent.put("Captcha_Token", token);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            isLogin = 1;
                            String mobile = editTextUserId.getText().toString();

                            if (editTextUserId.getText().toString().length() == 3) {

                                c0 = editTextUserId.getText().toString().charAt(0);
                                c1 = editTextUserId.getText().toString().charAt(1);
                                c2 = editTextUserId.getText().toString().charAt(2);
                            }
                            if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {
                                if (isValid(mobile)) {
                                    textViewErrorMessage.setVisibility(View.GONE);
                                    new RequestService(LoginActivity.this, LoginActivity.this).postRequestLogin("Profile/VerifyMobile?isAndroid=true", jsonObjectPArent);
                                } else {
                                    textViewErrorMessage.setVisibility(View.VISIBLE);
                                    textViewErrorMessage.setText("Invalid mobile number");
                                }
                            } else {
                                new RequestService(LoginActivity.this, LoginActivity.this).postRequestLogin("Profile/VerifyMobile?isAndroid=true", jsonObjectPArent);

                            }


                        }
                        //  new RequestService(LoginActivity.this, LoginActivity.this,LoginActivity.this).setArgumentsRecaptcha("Registration/ValidateCaptcha?response=" + token +"&isAndroid=true");

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


}else {

    // Received captcha token
    // This token still needs to be validated on the server
    // using the SECRET key

    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editTextUserId.getWindowToken(), 0);

    UserIdInput = editTextUserId.getText().toString();
    UserPasswordInput = editTextPassword.getText().toString();


    if (UserIdInput.isEmpty() || UserPasswordInput.isEmpty()) {
        textViewErrorMessage.setVisibility(View.VISIBLE);
        textViewErrorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);
    } else {

        BASE_URL = IConstant.BASE_URL;

        Log.i("Base URL", "" + BASE_URL);

        JSONObject jsonObjectPArent = new JSONObject();
        try {
            jsonObjectPArent.put("UserName", UserIdInput);
            jsonObjectPArent.put("Password", UserPasswordInput);
            jsonObjectPArent.put("Captcha_Token", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        isLogin = 1;
        String mobile = editTextUserId.getText().toString();

        if (editTextUserId.getText().toString().length() == 3) {

            c0 = editTextUserId.getText().toString().charAt(0);
            c1 = editTextUserId.getText().toString().charAt(1);
            c2 = editTextUserId.getText().toString().charAt(2);
        }
        if (Character.isDigit(c0) && Character.isDigit(c1) && Character.isDigit(c2)) {
            if (isValid(mobile)) {
                textViewErrorMessage.setVisibility(View.GONE);
                new RequestService(LoginActivity.this, LoginActivity.this).postRequestLogin("Profile/VerifyMobile?isAndroid=true", jsonObjectPArent);
            } else {
                textViewErrorMessage.setVisibility(View.VISIBLE);
                textViewErrorMessage.setText("Invalid mobile number");
            }
        } else {
            new RequestService(LoginActivity.this, LoginActivity.this).postRequestLogin("Profile/VerifyMobile?isAndroid=true", jsonObjectPArent);

        }


    }

}
    }

    // Toggle View Password

    public void ViewPassword(View view) {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (editTextPassword.getInputType() != InputType.TYPE_CLASS_TEXT) {

            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);

            editTextPassword.setSelection(editTextPassword.getText().length());

            viewPassword.setImageDrawable(res2);

        } else {

            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            editTextPassword.setSelection(editTextPassword.getText().length());

            viewPassword.setImageDrawable(res);

        }

    }

    public void NavRegister(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }

    public void FBloginOnClick(View view) {

        FbLogin.performClick();
        editTextUserId.setText("");
        editTextPassword.setText("");


    }

    // Forgot Password Nav
    public void ForgotPassword(View view) {

        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

    }

    // Google Login Button Click
    public void GoogleLogin(View view) {

        getIdToken();

    }

    @Override
    public void onSuccess(String response) {
String res = response;
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
    public void onPostSuccess(String response, String sessionID) {


        sessionId = sessionID;
        saveCredentialsAfterSignIn();
        if (isLogin == 1) {
            try {
                JSONObject responseObject = new JSONObject(response);

                Log.i("Response Object", "" + responseObject);


                if (responseObject.has("tCode")) {
                    Log.i("tCOde Value", "" + tcode);
                    tcode = responseObject.getString("tCode");

                    if (!tcode.equalsIgnoreCase("null")) {
                        HomeActivity.sendDeviceToken = 1;
                        mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                        mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                        mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                       mSavePreference.putString(IpreferenceKey.FIRSTNAME, responseObject.getString("FirstName"));

                        // mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

                    } else {
                        count++;
                        String status = responseObject.getString("status");
                        Log.i("Status object value", "" + status);
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(status);
                    }
                } else {
                    if (responseObject.has("Status")) {
                        Log.i("tCOde Value", "" + tcode);
                        String status = responseObject.getString("Status");

                        if (status.equalsIgnoreCase("Success")) {

                            mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                            mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                            mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                            //   mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            count++;
                            Log.i("Status object value", "" + status);
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(status);
                        }
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {

            JSONObject responseObject = null;
            try {
                responseObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Response Object", "" + responseObject);

            if (responseObject.has("tcode")) {

                String tcode = null;
                try {
                    tcode = responseObject.getString("tCode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String status = null;
                try {
                    status = responseObject.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("tCOde Value", "" + tcode);
                if (tcode != null) {
                    HomeActivity.sendDeviceToken = 1;
                    mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                    mSavePreference.putString(IpreferenceKey.TCODE, tcode);

                    //      mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    count++;
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(status);
                }
            } else {
                if (responseObject.has("TCode")) {

                    String tcode = null;
                    try {
                        tcode = responseObject.getString("Status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String tcode1 = null;
                    try {
                        tcode1 = responseObject.getString("TCode");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!tcode1.equalsIgnoreCase("null")) {
                        HomeActivity.sendDeviceToken = 1;
                        mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                        mSavePreference.putString(IpreferenceKey.TCODE, tcode1);

                        try {
                            mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("Tenant_Name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("tCOde Value", "" + tcode);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        count++;
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(tcode);
                    }
                }

//                        // Google log out
//                        if (mGoogleApiClient.isConnected()) {
//                            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                        }
                // FB log out
                LoginManager.getInstance().logOut();


                // String status = responseObject.getString("status");
                //  textViewErrorMessage.setVisibility(View.VISIBLE);
                //  textViewErrorMessage.setText(status);
                //Log.i("Status object value", "" + status);

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
                //login

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextUserId.getWindowToken(), 0);

                UserIdInput = editTextUserId.getText().toString();
                UserPasswordInput = editTextPassword.getText().toString();



                if (UserIdInput.isEmpty() || UserPasswordInput.isEmpty()) {
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    textViewErrorMessage.setText(R.string.Errror_Message_Id_or_pass_empty);
                } else {

                    BASE_URL = IConstant.BASE_URL;

                    Log.i("Base URL", "" + BASE_URL);

                    JSONObject jsonObjectPArent = new JSONObject();
                    try {
                        jsonObjectPArent.put("UserName", UserIdInput);
                        jsonObjectPArent.put("Password", UserPasswordInput);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    isLogin = 1;
                    new RequestService(LoginActivity.this, LoginActivity.this).postRequestLogin("Profile/VerifyMobile", jsonObjectPArent);
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

    //Login api call as an AsynTask

  /*  class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in ..");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();


        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
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
                        if (responseObject.toString().contains("tCode")){
                            String tcode = responseObject.getString("tCode");
                            Log.i("tCOde Value",""+tcode);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {

                            String status = responseObject.getString("status");
                            Log.i("Status object value", "" + status);
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(status);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    progressDialog.dismiss();



        }
    }*/


    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in ..");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
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
            if (response != null) {
                progressDialog.dismiss();

                try {
                    JSONObject responseObject = new JSONObject(response);
                    Log.i("Response Object", "" + responseObject);


                    if (responseObject.has("tCode")) {
                        Log.i("tCOde Value", "" + tcode);
                        tcode = responseObject.getString("tCode");

                        if (!tcode.equalsIgnoreCase("null")) {
                            HomeActivity.sendDeviceToken = 1;
                            mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                            mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                            mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                            // mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();

                        } else {
                            String status = responseObject.getString("status");
                            Log.i("Status object value", "" + status);
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(status);
                        }
                    } else {
                        if (responseObject.has("Status")) {
                            Log.i("tCOde Value", "" + tcode);
                            String status = responseObject.getString("Status");

                            if (status.equalsIgnoreCase("Success")) {

                                mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                                mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                                mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                                //   mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Log.i("Status object value", "" + status);
                                textViewErrorMessage.setVisibility(View.VISIBLE);
                                textViewErrorMessage.setText(status);
                            }
                        }

                    }


                } catch (JSONException e) {
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
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in...");
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
                sessionId = response.header("SessionId");
                saveCredentialsAfterSignIn();
                JSONObject responseObject = new JSONObject(response.body().string());
                Log.i("Response Object", "" + responseObject);

                if (responseObject.has("tcode")) {

                    String tcode = responseObject.getString("tCode");
                    String status = responseObject.getString("status");

                    Log.i("tCOde Value", "" + tcode);
                    if (tcode != null) {
                        HomeActivity.sendDeviceToken = 1;
                        mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                        mSavePreference.putString(IpreferenceKey.TCODE, tcode);
                        mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("tenant_name"));
                        //   mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        textViewErrorMessage.setVisibility(View.VISIBLE);
                        textViewErrorMessage.setText(status);
                    }
                } else {
                    if (responseObject.has("TCode")) {

                        String tcode = responseObject.getString("Status");
                        String tcode1 = responseObject.getString("TCode");

                        if (!tcode1.equalsIgnoreCase("null")) {
                            HomeActivity.sendDeviceToken = 1;
                            mSavePreference.putString(IpreferenceKey.IS_LOGIN, "true");
                            mSavePreference.putString(IpreferenceKey.TCODE, tcode1);
                            mSavePreference.putString(IpreferenceKey.USERNAME, responseObject.getString("Tenant_Name"));
                            //    mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, editTextUserId.getText().toString());
                            Log.i("tCOde Value", "" + tcode);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            textViewErrorMessage.setVisibility(View.VISIBLE);
                            textViewErrorMessage.setText(tcode);
                        }
                    }

//                        // Google log out
                    //   mGoogleSignInCLient.signOut();
//                        if (mGoogleApiClient.isConnected()) {
//                            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                        }
                    // FB log out
                    LoginManager.getInstance().logOut();


                    // String status = responseObject.getString("status");
                    //  textViewErrorMessage.setVisibility(View.VISIBLE);
                    //  textViewErrorMessage.setText(status);
                    //Log.i("Status object value", "" + status);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void decryptText() {
        try {
            tvDecryptedText = decryptor
                    .decryptData(SAMPLE_ALIAS, encryptor.getEncryption(), encryptor.getIv());
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException |
                KeyStoreException | NoSuchPaddingException | NoSuchProviderException |
                IOException | InvalidKeyException e) {
            Log.e("Login", "decryptData() called with: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private void encryptText() {
        try {
            final byte[] encryptedText = encryptor
                    .encryptText(SAMPLE_ALIAS, edTextToEncrypt);
            tvEncryptedText = Base64.encodeToString(encryptedText, Base64.DEFAULT);
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | NoSuchProviderException |
                KeyStoreException | IOException | NoSuchPaddingException | InvalidKeyException e) {
            Log.e("Login", "onClick() called with: " + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException | SignatureException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    private void saveCredentials() {
        ApplicationPreferences preferences = ApplicationPreferences.getInstance(this);
        preferences.setCredentialsSaveEnabled(true);
        preferences.savePassword(BuildConfig.KEY_STORE);
    }
    private void saveCredentialsAfterSignIn() {
        ApplicationPreferences preferences = ApplicationPreferences.getInstance(this);
        preferences.setCredentialsSaveEnabled(true);
        preferences.savePassword(BuildConfig.KEY_STORE);
        preferences.setSessionId(sessionId);

    }

    private void sendGmailToken() {
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
        builder = builder.url(REGISTER_URL + "Registration/SocialLogin");
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);

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
