package com.residents.dubaiassetmanagement.contact;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsFragment extends Fragment implements View.OnClickListener,ResponseCallback, ResponseCallbackRecaptcha {

   private View view;
   private TextView fragmentTitle;
   private ImageView iv_facebook,iv_youtube,iv_linkedin,iv_instagram,iv_call;
   private EditText et_message;
   private Button bt_confirm;
private SavePreference mSavePreference;
    private static final String SAFETY_NET_API_SITE_KEY = "6Lfaw4cUAAAAAA8dCs8UWt7L77Dye7DKdZC7OIeJ";
private RelativeLayout iv_bell_icon;
private BottomNavigationView bottomNavigation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("CONTACT US");

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
               // getFragmentManager().popBackStack();
            }
        });
        mSavePreference = SavePreference.getInstance(getActivity());
        iv_facebook = (ImageView) view.findViewById(R.id.iv_facebook);
        iv_instagram = (ImageView) view.findViewById(R.id.iv_instagram);
        iv_linkedin = (ImageView) view.findViewById(R.id.iv_linkedin);
        iv_call = (ImageView) view.findViewById(R.id.iv_call);
        iv_youtube = (ImageView) view.findViewById(R.id.iv_youtube);
        bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        et_message = (EditText) view.findViewById(R.id.et_message);

        iv_facebook.setOnClickListener(this);
        iv_instagram.setOnClickListener(this);
        iv_linkedin.setOnClickListener(this);
        iv_youtube.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        iv_call.setOnClickListener(this);
        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_message.getText().toString().length() > 0) {
                    bt_confirm.setEnabled(true);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    bt_confirm.setEnabled(false);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_message.getText().toString().length() > 0) {
                    bt_confirm.setEnabled(true);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    bt_confirm.setEnabled(false);
                    bt_confirm.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.iv_facebook:
                Uri uri = Uri.parse("https:www.facebook.com/DubaiAMLife/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;

            case R.id.iv_instagram:
                Uri uri1 = Uri.parse("https://www.instagram.com/Dubaiamlife/");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);

                break;
            case R.id.iv_linkedin:

                Uri uri2 = Uri.parse("https://www.linkedin.com/company/dubai-asset-management/");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
            case R.id.iv_youtube:

                Uri uri3 = Uri.parse("https://www.youtube.com/channel/UCZlHlNmWhso6fYNu5LPRTAw");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent3);
                break;

            case R.id.iv_call:


                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:800 9933"));
                startActivity(callIntent);
                break;
            case R.id.bt_confirm:

                if (et_message.getText().toString().isEmpty()){

                }else {
                    // Showing reCAPTCHA dialog
                    SafetyNet.getClient(getActivity()).verifyWithRecaptcha(SAFETY_NET_API_SITE_KEY)
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                @Override
                                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                    Log.d("RegisterActivity", "onSuccess");

                                    if (!response.getTokenResult().isEmpty()) {

                                        // Received captcha token
                                        // This token still needs to be validated on the server
                                        // using the SECRET key
                                        String token = response.getTokenResult();
                                        JSONObject jsonObject1 = new JSONObject();

                                        try {

                                            String message = "{{FirstName}} {{LastName}} has the following query:<br/>"+et_message.getText().toString()+"<br/>Tenant Unique ID: {{TenantId}}<br/>Community: {{Community}}<br/>Property#: {{Property}}<br/>Unit#: {{Unit}}<br/>Contact Details: <br/>Email: {{Email}}<br/>Phone: {{Phone}}";

                                            jsonObject1.put("profileId",mSavePreference.getString(IpreferenceKey.TCODE));
                                            jsonObject1.put("Subject", "Feedback from App / Portal - {{FirstName}} {{LastName}} {{TenantId}}");
                                            jsonObject1.put("EmailBody", message);
                                            jsonObject1.put("IsValid", "true");
                                            jsonObject1.put("Captcha_Token", token);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        new RequestService(getActivity(), ContactUsFragment.this).postRequest("Email?isAndroid=true", jsonObject1);

                                        //  new RequestService(getActivity(), ContactUsFragment.this,ContactUsFragment.this).setArgumentsRecaptcha("Registration/ValidateCaptcha?response=" + token +"&isAndroid=true");


                                    }
                                }
                            })
                            .addOnFailureListener(getActivity(), new OnFailureListener() {
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


                    JSONObject jsonObject = new JSONObject();

                    try {

                        String message = "String = String(format: \"{{FirstName}} {{LastName}} has the following query:(br/>"+et_message.getText().toString()+"<br/>Tenant Unique ID: {{TenantId}}<br/>Community: {{Community}}<br/>Property#: {{Property}}<br/>Unit#: {{Unit}}<br/>Contact Details: <br/>Email: {{Email}}<br/>Phone: {{Phone}}\", self.message.text)";

                        jsonObject.put("profileId",mSavePreference.getString(IpreferenceKey.TCODE));
                        jsonObject.put("Subject", "{{FirstName}} {{LastName}} ({{TenantId}})has a query");
                        jsonObject.put("EmailBody", message);
                        jsonObject.put("IsValid", "true");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   // new RequestService(getActivity(), ContactUsFragment.this).postRequest("Email", jsonObject);
                }

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

    }

    @Override
    public void onSuccessSecond(String response) {

    }



    @Override
    public void onPostSuccess(String response, String sessionId) {
String res = response;
et_message.setText("");

        if (res.equalsIgnoreCase("true")) {
            launchSuccessDialogforError("123");

        }
    }


    private void launchSuccessDialogforError(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);
        ImageView iv_title = (ImageView) dialog.findViewById(R.id.iv_title);
        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        iv_title.setVisibility(View.VISIBLE);
        tv_message.setText("Your message has been delivered successfully.\n" +
                "One of our representatives will be in touch with you soon.");
        srid.setText("SR ID #"+srID);
        srid.setVisibility(View.GONE);
        ok.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onSuccessRecaptcha(String response) {
        String res =  response;

        try {
            JSONObject jsonObject = new JSONObject(res);

            String status = jsonObject.getString("success");
            if (status.equalsIgnoreCase("true")){

                JSONObject jsonObject1 = new JSONObject();

                try {

                    String message = "String = String(format: \"{{FirstName}} {{LastName}} has the following query:(br/>"+et_message.getText().toString()+"<br/>Tenant Unique ID: {{TenantId}}<br/>Community: {{Community}}<br/>Property#: {{Property}}<br/>Unit#: {{Unit}}<br/>Contact Details: <br/>Email: {{Email}}<br/>Phone: {{Phone}}\", self.message.text)";

                    jsonObject1.put("profileId",mSavePreference.getString(IpreferenceKey.TCODE));
                    jsonObject1.put("Subject", "{{FirstName}} {{LastName}} ({{TenantId}})has a query");
                    jsonObject1.put("EmailBody", message);
                    jsonObject1.put("IsValid", "true");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new RequestService(getActivity(), ContactUsFragment.this).postRequest("Email?isAndroid=true", jsonObject1);

            }else {
                Toast.makeText(getActivity(),"reCaptcha Validation Failed!",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSuccessJWT(String response) {

    }
}
// https://deloittecustomerportalscal-333870-cd.azurewebsites.net/terms-and-conditions-mbl"