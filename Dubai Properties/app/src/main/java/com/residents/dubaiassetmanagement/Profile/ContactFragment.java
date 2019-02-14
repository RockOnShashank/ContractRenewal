package com.residents.dubaiassetmanagement.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.BuildConfig;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.Model.UpdateTenantProfile;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.RegisterActivity;
import com.residents.dubaiassetmanagement.RegisterOTPActivity;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.UserVerificationActivity;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ContactFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "tab1";
    private ImageView editButton;
    private Button saveButton;
    private Button cancelButton;
    private View Line;
    private EditText editText;
    private String text;
    private String text1;
    private View view;
    private LinearLayout saveAndCancel;
    private LinearLayout saveAndCancel1;
    private LinearLayout saveAndCancel2;

    private String AlternateNumber,AlternateNumber1;
    private String LandlineNumber,LandlineNumber1;
    private String ContactNumber;
    private String EmergencyContact,EmergencyContact1;
    private String PO;
    private String CompanyName;
    private String CompanyAddress, BASE_URL;

    private EditText Alternate;
    private EditText Landline;
    private EditText Contact;
    private EditText Emergency;
    private EditText PO1;
    private EditText ComName;
    private EditText ComAdd;


    private ImageView editButton1;
    private Button saveButton1;
    private Button cancelButton1;
    private EditText editText1;

    private ImageView editButton2;
    private Button saveButton2;
    private Button cancelButton2;
    private TextView tv_error;
    private int isSave,isSave1,isSave2=0;

    private String tempPhoneNumber;
    private String REGISTER_URL;
    private TenantDetails tenantDetails;
    public boolean isPhoneNumber = false;
    OkHttpClient okHttpClient = new OkHttpClient();
    UpdateTenantProfile updateTenantProfile;
    private View edittextLine_contact, edittextLine_contact2, edittextLine_contact3, edittextLine_contact4, edittextLine_contact5, edittextLine_contact6, edittextLine_contact7;
    private TextView tv_error_phone, tv_error_email, tv_error_landline, tv_error_alternate, tv_error_emergency, tv_error_pobox;
    private SavePreference mSavePreference;
    private ApplicationPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_fragment, container, false);
        mSavePreference = SavePreference.getInstance(getActivity());
        EventBus.getDefault().register(this);

        tenantDetails = VolleySingleton.getInstance().getTenantDetails();
        tv_error_email = (TextView) view.findViewById(R.id.tv_error_email);
        saveAndCancel = (LinearLayout) view.findViewById(R.id.saveAndCancel);
        saveAndCancel1 = (LinearLayout) view.findViewById(R.id.saveAndCancel1);
        saveAndCancel2 = (LinearLayout) view.findViewById(R.id.saveAndCancel2);
        tv_error = (TextView) view.findViewById(R.id.tv_error_phone);
        preferences = ApplicationPreferences.getInstance(getActivity());
    //    EventBus.getDefault().register(getActivity());

        tv_error_email = (TextView) view.findViewById(R.id.tv_error_email);
        tv_error_landline = (TextView) view.findViewById(R.id.tv_error_landline);
        tv_error_emergency = (TextView) view.findViewById(R.id.tv_error_emergency);
        tv_error_alternate = (TextView) view.findViewById(R.id.tv_error_alternate);
        tv_error_pobox = (TextView) view.findViewById(R.id.tv_error_pobox);


        edittextLine_contact = (View) view.findViewById(R.id.edittextLine_contact);
        edittextLine_contact2 = (View) view.findViewById(R.id.edittextLine_contact2);
        edittextLine_contact3 = (View) view.findViewById(R.id.edittextLine_contact3);
        edittextLine_contact7 = (View) view.findViewById(R.id.edittextLine_contact7);

        edittextLine_contact4 = (View) view.findViewById(R.id.edittextLine_contact4);
        edittextLine_contact5 = (View) view.findViewById(R.id.edittextLine_contact5);
        edittextLine_contact6 = (View) view.findViewById(R.id.edittextLine_contact6);


        saveAndCancel.setVisibility(View.GONE);
        saveAndCancel1.setVisibility(View.GONE);
        saveAndCancel2.setVisibility(View.GONE);

        //validations

        tv_error_landline = (TextView) view.findViewById(R.id.tv_error_landline);

        //mobileNo
        editText = (EditText) view.findViewById(R.id.profile_editText_mobileNo);
        //email
        editText1 = (EditText) view.findViewById(R.id.profile_editText1);
//alternate contact
        Alternate = (EditText) view.findViewById(R.id.profile_editText_alternate);
        Landline = (EditText) view.findViewById(R.id.profile_editText_landline);
        Contact = (EditText) view.findViewById(R.id.profile_editText_contact);
        Emergency = (EditText) view.findViewById(R.id.profile_editText_emergency);
        PO1 = (EditText) view.findViewById(R.id.profile_editText_PO);
        ComName = (EditText) view.findViewById(R.id.profile_editText_companyName);
        ComAdd = (EditText) view.findViewById(R.id.profile_editText_companyAddress);
        edittextLine_contact4 = (View) view.findViewById(R.id.edittextLine_contact4);

        edittextLine_contact5 = (View) view.findViewById(R.id.edittextLine_contact5);


        editButton = (ImageView) view.findViewById(R.id.editButton);
        editButton.setOnClickListener(this);

        saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        editButton1 = (ImageView) view.findViewById(R.id.editButton1);
        editButton1.setOnClickListener(this);

        saveButton1 = (Button) view.findViewById(R.id.saveButton1);
        saveButton1.setOnClickListener(this);

        cancelButton1 = (Button) view.findViewById(R.id.cancelButton1);
        cancelButton1.setOnClickListener(this);


        editButton2 = (ImageView) view.findViewById(R.id.editButton2);
        editButton2.setOnClickListener(this);
        editButton.setVisibility(View.VISIBLE);

        cancelButton2 = (Button) view.findViewById(R.id.cancelButton2);
        cancelButton2.setOnClickListener(this);

        saveButton2 = (Button) view.findViewById(R.id.saveButton2);
        saveButton2.setOnClickListener(this);

        editText.setEnabled(false);
        editText1.setEnabled(false);

        Alternate.setEnabled(false);
        Landline.setEnabled(false);
        Contact.setEnabled(false);
        Emergency.setEnabled(false);
        PO1.setEnabled(false);
        ComName.setEnabled(false);
        ComAdd.setEnabled(false);

        setTextViews(view);


        Emergency.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(Emergency.getText().toString().length()==3) {

                    c0 = Emergency.getText().toString().charAt(0);
                    c1 = Emergency.getText().toString().charAt(1);
                    c2 = Emergency.getText().toString().charAt(2);
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




        editText.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString().length()==3) {

                    c0 = editText.getText().toString().charAt(0);
                    c1 = editText.getText().toString().charAt(1);
                    c2 = editText.getText().toString().charAt(2);
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


        Alternate.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(Alternate.getText().toString().length()==3) {

                    c0 = Alternate.getText().toString().charAt(0);
                    c1 = Alternate.getText().toString().charAt(1);
                    c2 = Alternate.getText().toString().charAt(2);
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


        Landline.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(Landline.getText().toString().length()==3) {

                    c0 = Landline.getText().toString().charAt(0);
                    c1 = Landline.getText().toString().charAt(1);
                }
                if (Character.isDigit(c0) && Character.isDigit(c1) ) {
                    // Remove spacing char
                    if (s.length() > 0 && (s.length() % 3) == 0) {
                        final char c = s.charAt(s.length() - 1);
                        if (space == c) {
                            // s.delete(s.length() - 1, s.length());
                        }
                    }
                    // Insert char where needed.
                    if (s.length() == 0) {
                        v = 0;
                    }
                    if (s.length() == 2) {
                        v = 0;
                    }

                    if (s.length() > 0 && (s.length() % 3) == 0 && v == 0) {
                        char c = s.charAt(s.length() - 1);
                        v = 1;
                        // Only if its a digit where there should be a space we insert a space
                        if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 2) {
                            s.insert(s.length() - 1, String.valueOf(space));
                        }
                    }
                }
            }
        });










        return view;

    }

    public void setTextViews(View view) {

        //mobileNo
        if (tenantDetails != null) {
            if (tenantDetails.getTenantDetails().get(0).getTenantMobile()!=null) {
                editText.setText(tenantDetails.getTenantDetails().get(0).getTenantMobile());
            }
            //email
            if (tenantDetails.getTenantDetails().get(0).getTenantEmail() != null) {
                editText1.setText(tenantDetails.getTenantDetails().get(0).getTenantEmail());
            }
            HomeActivity.count = 1;
//alternate contact
            if (tenantDetails.getTenantDetails().get(0).getTenantAltMobNo() != null) {
                Alternate.setText(tenantDetails.getTenantDetails().get(0).getTenantAltMobNo());
            }
            if (tenantDetails.getTenantDetails().get(0).getTenantLandlineNo() != null) {
                Landline.setText(tenantDetails.getTenantDetails().get(0).getTenantLandlineNo());
            }
            if (tenantDetails.getTenantDetails().get(0).getTenantMobile()!=null) {
                Contact.setText(tenantDetails.getTenantDetails().get(0).getTenantMobile());
            }
            if (tenantDetails.getTenantDetails().get(0).getTenantEmergencyNo() != null) {
                Emergency.setText(tenantDetails.getTenantDetails().get(0).getTenantEmergencyNo());
            }
if (tenantDetails.getTenantDetails().get(0).getTenantZip()!=null) {
    if (tenantDetails.getTenantDetails().get(0).getTenantZip().equalsIgnoreCase("")) {
        PO1.setText("NA");

    } else {
        PO1.setText(tenantDetails.getTenantDetails().get(0).getTenantZip());
    }
}

            if (tenantDetails.getTenantDetails().get(0).getCompanyName() != null) {
                if (tenantDetails.getTenantDetails().get(0).getCompanyName().equalsIgnoreCase("")) {
                    ComName.setText("NA");
                } else {
                    ComName.setText(tenantDetails.getTenantDetails().get(0).getCompanyName());
                }
            }
            if (tenantDetails.getTenantDetails().get(0).getCompanyAddr() == null) {
                ComAdd.setText("NA");
            } else {
                ComAdd.setText(tenantDetails.getTenantDetails().get(0).getCompanyAddr().toString());
            }

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editButton:

                tempPhoneNumber = editText.getText().toString();
                saveAndCancel.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
                editText.setFocusable(true);
                editText.setClickable(true);
                editText.setEnabled(true);
                editText.requestFocus();
                break;

            case R.id.saveButton:
                editButton.setVisibility(View.VISIBLE);
                text = editText.getText().toString();
                editText.setText(text);
                isPhoneNumber = true;
                if (editText.getText().toString().length() < 11) {
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Mobile number format is 05X XXXXXXX");

                } else {
                    tv_error.setVisibility(View.GONE);
                    if (editText.getText().toString().length() > 4) {
                        if (isValidMobileNumber(editText.getText().toString())) {

                            if (isValid(editText.getText().toString())){
                                saveAndCancel.setVisibility(View.GONE);
                                editText.setEnabled(false);
                                UpdateNumber(view);
                            }else {
                                tv_error.setVisibility(View.VISIBLE);
                                tv_error.setText("Mobile number format is 05X XXXXXXX");

                            }

                        } else {
                            tv_error.setVisibility(View.VISIBLE);
                            tv_error.setText("Mobile number format is 05X XXXXXXX");
                        }
                    } else {
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText("Mobile number format is 05X XXXXXXX");
                    }
                }
                editButton.setVisibility(View.GONE);

                break;

            case R.id.cancelButton:
                editText.setEnabled(false);
                editText.setText(tenantDetails.getTenantDetails().get(0).getTenantMobile());
                saveAndCancel.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                tv_error.setVisibility(View.GONE);
                // editText.setText(text);
                break;

            case R.id.cancelButton1:
                editText1.setEnabled(false);
                saveAndCancel1.setVisibility(View.GONE);
                editButton1.setVisibility(View.VISIBLE);
                editText1.setText(tenantDetails.getTenantDetails().get(0).getTenantEmail());
                break;

            case R.id.editButton1:
                text1 = editText1.getText().toString();
                editText1.setEnabled(true);
                editText1.setFocusable(true);
                editText1.setClickable(true);
                saveAndCancel1.setVisibility(View.VISIBLE);
                editButton1.setVisibility(View.GONE);

                break;

            case R.id.saveButton1:
                editText1.setEnabled(false);
                text1 = editText1.getText().toString();
                editText1.setText(text1);
                isPhoneNumber = false;
                if (isValidEmail(editText1.getText().toString())) {
                    editButton1.setVisibility(View.VISIBLE);
                    saveAndCancel1.setVisibility(View.GONE);
                    tv_error_email.setVisibility(View.GONE);
                    UpdateNumber(view);

                } else {
                    editButton1.setVisibility(View.GONE);

                    tv_error_email.setVisibility(View.VISIBLE);
                    tv_error_email.setText("Invalid Email.");
                }
                break;
            case R.id.editButton2:

                AlternateNumber1 = Alternate.getText().toString();
                LandlineNumber1 = Landline.getText().toString();
                ContactNumber = Contact.getText().toString();
                EmergencyContact1 = Emergency.getText().toString();
                PO = PO1.getText().toString();
                CompanyName = ComName.getText().toString();
                CompanyAddress = ComAdd.getText().toString();


                edittextLine_contact.setVisibility(View.VISIBLE);
                edittextLine_contact2.setVisibility(View.VISIBLE);
                edittextLine_contact3.setVisibility(View.GONE);
                edittextLine_contact4.setVisibility(View.VISIBLE);
                edittextLine_contact5.setVisibility(View.VISIBLE);
                edittextLine_contact6.setVisibility(View.VISIBLE);
                edittextLine_contact7.setVisibility(View.VISIBLE);


                saveAndCancel2.setVisibility(View.VISIBLE);
                editButton2.setVisibility(View.GONE);
                Alternate.setEnabled(true);
                Landline.setEnabled(true);
                Contact.setEnabled(true);
                Emergency.setEnabled(true);
                PO1.setEnabled(true);
                ComName.setEnabled(true);
                ComAdd.setEnabled(true);


                Alternate.setFocusable(true);
                Alternate.setClickable(true);
                Landline.setFocusable(true);
                Landline.setClickable(true);
                Contact.setFocusable(true);
                Contact.setClickable(true);
                Emergency.setFocusable(true);
                Emergency.setClickable(true);
                PO1.setFocusable(true);
                PO1.setClickable(true);
                ComName.setFocusable(true);
                ComName.setClickable(true);
                ComAdd.setFocusable(true);
                ComAdd.setClickable(true);
                Alternate.requestFocus();
                break;

            case R.id.cancelButton2:

                tv_error_alternate.setVisibility(View.GONE);
                tv_error_emergency.setVisibility(View.GONE);
                tv_error_landline.setVisibility(View.GONE);
                saveAndCancel2.setVisibility(View.GONE);
                editButton2.setVisibility(View.VISIBLE);

                edittextLine_contact.setVisibility(View.GONE);
                edittextLine_contact2.setVisibility(View.GONE);
                edittextLine_contact3.setVisibility(View.GONE);
                edittextLine_contact4.setVisibility(View.GONE);
                edittextLine_contact5.setVisibility(View.GONE);
                edittextLine_contact6.setVisibility(View.GONE);
                edittextLine_contact7.setVisibility(View.GONE);

                Alternate.setEnabled(false);
                Landline.setEnabled(false);
                Contact.setEnabled(false);
                Emergency.setEnabled(false);
                PO1.setEnabled(false);
                ComName.setEnabled(false);
                ComAdd.setEnabled(false);


                Alternate.setText(AlternateNumber1);

                Landline.setText(LandlineNumber1);
                Contact.setText(ContactNumber);
                Emergency.setText(EmergencyContact1);
                PO1.setText(PO);
                ComName.setText(CompanyName);
                ComAdd.setText(CompanyAddress);

                break;

            case R.id.saveButton2:



                edittextLine_contact.setVisibility(View.GONE);
                edittextLine_contact2.setVisibility(View.GONE);
                edittextLine_contact3.setVisibility(View.GONE);
                edittextLine_contact4.setVisibility(View.GONE);
                edittextLine_contact5.setVisibility(View.GONE);
                edittextLine_contact6.setVisibility(View.GONE);
                edittextLine_contact7.setVisibility(View.GONE);

                AlternateNumber = Alternate.getText().toString();
                Alternate.setText(AlternateNumber);
                LandlineNumber = Landline.getText().toString();
                Landline.setText(LandlineNumber);
                ContactNumber = Contact.getText().toString();
                Contact.setText(ContactNumber);
                EmergencyContact = Emergency.getText().toString();
                Emergency.setText(EmergencyContact);
                PO = PO1.getText().toString();
                PO1.setText(PO);
                CompanyName = ComName.getText().toString();
                ComName.setText(CompanyName);
                CompanyAddress = ComAdd.getText().toString();
                ComAdd.setText(CompanyAddress);

//
                if (AlternateNumber.length()>1 && AlternateNumber.matches(".*\\d+.*")) {
                    if (AlternateNumber.length() < 11) {
                        tv_error_alternate.setVisibility(View.VISIBLE);
                        tv_error_alternate.setText("Mobile number format is 05X XXXXXXX");

                    } else {
                        tv_error_alternate.setVisibility(View.GONE);
                        if (AlternateNumber.length() > 4 ) {
                            if (isValidMobileNumber(AlternateNumber) && AlternateNumber.length()==11) {

                                Alternate.setEnabled(false);
                                Landline.setEnabled(false);
                                Contact.setEnabled(false);
                                Emergency.setEnabled(false);
                                PO1.setEnabled(false);
                                ComName.setEnabled(false);
                                ComAdd.setEnabled(false);
                                saveAndCancel2.setVisibility(View.GONE);
                                editButton2.setVisibility(View.VISIBLE);
                                if (isValid(Alternate.getText().toString())){
                                    isSave=1;
                                }else {
                                    tv_error_alternate.setVisibility(View.VISIBLE);

                                    tv_error_alternate.setText("Mobile number format is 05X XXXXXXX");

                                }


                            } else {
                                tv_error_alternate.setVisibility(View.VISIBLE);
                                tv_error_alternate.setText("Mobile number format is 05X XXXXXXX");
                            }
                        } else {
                            tv_error_alternate.setVisibility(View.VISIBLE);
                            tv_error_alternate.setText("Mobile number format is 05X XXXXXXX");
                        }

                    }
                }else if (AlternateNumber.isEmpty()){

                    isSave=1;

                }
                // landline

                if (LandlineNumber.length()>1 && LandlineNumber.matches(".*\\d+.*"))
                    if (LandlineNumber.length() < 10) {
                        tv_error_landline.setVisibility(View.VISIBLE);
                        tv_error_landline.setText("Landline number format is 0X XXXXXXX");

                    } else {
                        tv_error_landline.setVisibility(View.GONE);
                        if (LandlineNumber.length() > 4) {
                            if (isValidMobileNumberLandline(LandlineNumber)  && LandlineNumber.length()==10) {

                                Alternate.setEnabled(false);
                                Landline.setEnabled(false);
                                Contact.setEnabled(false);
                                Emergency.setEnabled(false);
                                PO1.setEnabled(false);
                                ComName.setEnabled(false);
                                ComAdd.setEnabled(false);

                                saveAndCancel2.setVisibility(View.GONE);
                                editButton2.setVisibility(View.VISIBLE);
                                if (isValidLL(Landline.getText().toString())){
                                    isSave1=1;

                                }else {
                                    tv_error_landline.setVisibility(View.VISIBLE);
                                    tv_error_landline.setText("Landline number format is 0X XXXXXXX");
                                }



                            } else {
                                tv_error_landline.setVisibility(View.VISIBLE);
                                tv_error_landline.setText("Landline number format is 0X XXXXXXX");
                            }
                        } else {
                            tv_error_landline.setVisibility(View.VISIBLE);
                            tv_error_landline.setText("Landline number format is 0X XXXXXXX");
                        }
                    }else if (LandlineNumber.isEmpty()){
                    isSave1=1;
                }

//Emergency\
                if (EmergencyContact.length() >1  && EmergencyContact.matches(".*\\d+.*")) {
                    if (EmergencyContact.length() < 11) {
                        tv_error_emergency.setVisibility(View.VISIBLE);
                        tv_error_emergency.setText("Mobile number format is 05X XXXXXXX");

                    } else {
                        tv_error_emergency.setVisibility(View.GONE);
                        if (EmergencyContact.length() > 4) {
                            if (isValidMobileNumber(EmergencyContact) && EmergencyContact.length()==11) {
                                Alternate.setEnabled(false);
                                Landline.setEnabled(false);
                                Contact.setEnabled(false);
                                Emergency.setEnabled(false);
                                PO1.setEnabled(false);
                                ComName.setEnabled(false);
                                ComAdd.setEnabled(false);

                                saveAndCancel2.setVisibility(View.GONE);
                                editButton2.setVisibility(View.VISIBLE);

                                if (isValid(Emergency.getText().toString()))
                                {
                                    isSave2=1;
                                }else {
                                    tv_error_emergency.setVisibility(View.VISIBLE);
                                    tv_error_emergency.setText("Mobile number format is 05X XXXXXXX");
                                }

                            } else {
                                tv_error_emergency.setVisibility(View.VISIBLE);
                                tv_error_emergency.setText("Mobile number format is 05X XXXXXXX");
                            }
                        } else {
                            tv_error_emergency.setVisibility(View.VISIBLE);
                            tv_error_emergency.setText("Mobile number format is 05X XXXXXXX");
                        }

                    }
                }else if (EmergencyContact.isEmpty() || EmergencyContact.contains("-")){
                    isSave2=1;
                }
                //po box


                if (isSave==1 && isSave1==1 && isSave2==1 && !PO1.getText().toString().equalsIgnoreCase("")){
                    tv_error_emergency.setVisibility(View.GONE);
                    tv_error_landline.setVisibility(View.GONE);
                    tv_error_alternate.setVisibility(View.GONE);
                    tv_error_pobox.setVisibility(View.GONE);

                    UpdateTenantDetials();
                    saveAndCancel2.setVisibility(View.GONE);
                    editButton2.setVisibility(View.VISIBLE);
                    Alternate.setEnabled(false);
                    Landline.setEnabled(false);
                    Contact.setEnabled(false);
                    Emergency.setEnabled(false);
                    PO1.setEnabled(false);
                    ComName.setEnabled(false);
                    ComAdd.setEnabled(false);

                }else {
                    tv_error_pobox.setVisibility(View.VISIBLE);
                    Alternate.setEnabled(true);
                    Landline.setEnabled(true);
                    Contact.setEnabled(true);
                    Emergency.setEnabled(true);
                    PO1.setEnabled(true);
                    ComName.setEnabled(true);
                    ComAdd.setEnabled(true);
                    saveAndCancel2.setVisibility(View.VISIBLE);
                    editButton2.setVisibility(View.GONE);

                }



                break;

            default:
                break;
        }
    }

    //Http Request is handled below
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating...");
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

                try {

                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("ContactResponse", "" + responseObject);
                    if (responseObject.has("status")) {
                        String status = responseObject.getString("status");
                        Log.i("status", "" + status);
                        if (status.equalsIgnoreCase("Success")) {
                            tv_error.setVisibility(View.GONE);

                            //loadFragment(new UpdateNumberOTPFragment());
//load fragment

                            if (isPhoneNumber) {
                                Intent intent = new Intent(getActivity(), UpdateNumberOTPActivity.class);
                                intent.putExtra("oldNumber", tenantDetails.getTenantDetails().get(0).getTenantMobile());
                                intent.putExtra("newNumber", editText.getText().toString());
                                startActivity(intent);


                               /* Bundle bundle = new Bundle();
                                bundle.putString("oldNumber", tenantDetails.getTenantDetails().get(0).getTenantMobile());
                                bundle.putString("newNumber", editText.getText().toString());
                                loadFragment(new UpdateNumberOTPFragment(), bundle);*/
                            } else {
                                Intent intent = new Intent(getActivity(), UserVerificationActivity.class);
                                intent.putExtra("email_id", editText1.getText().toString());
                                intent.putExtra("profiles", "profiles");

                                startActivity(intent);
                            }
                        }

                    } else {

                        String status = responseObject.getString("Status");
                        Log.i("status", "" + status);
                        if (status.equalsIgnoreCase("Success")) {
                            tv_error.setVisibility(View.GONE);

                            //loadFragment(new UpdateNumberOTPFragment());
//load fragment

                            if (isPhoneNumber) {


                                Intent intent = new Intent(getActivity(), UpdateNumberOTPActivity.class);
                                intent.putExtra("oldNumber", tenantDetails.getTenantDetails().get(0).getTenantMobile());
                                intent.putExtra("newNumber", editText.getText().toString());
                                startActivity(intent);
/*
                                Bundle bundle = new Bundle();
                                bundle.putString("oldNumber", tenantDetails.getTenantDetails().get(0).getTenantMobile());
                                bundle.putString("newNumber", editText.getText().toString());
                                loadFragment(new UpdateNumberOTPFragment(), bundle);*/
                            } else {

                                Intent intent = new Intent(getActivity(), UserVerificationActivity.class);
                                intent.putExtra("email_id", editText1.getText().toString());
                                intent.putExtra("profiles", "profiles");

                                startActivity(intent);

                            }
                        } else {

                            try {
                                String status1 = responseObject.getString("Status");

                                if (status1.equalsIgnoreCase("Profile is not available.")) {
                                    tv_error.setVisibility(View.VISIBLE);
                                    tv_error.setText(status1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        Log.i("Status object value", "" + status);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void UpdateNumber(View view) {
        //API call to register user
        JSONObject jsonObject = new JSONObject();

      /*  try {
            jsonObject.put("oldEmailorPhone",tenantDetails.getTenantDetails().get(0).getTenantMobile());
            jsonObject.put("registrationMode", "Phone");
            jsonObject.put("newEmailorPhone", editText.getText().toString());
            jsonObject.put("tokenType", "4");

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();

        if (isPhoneNumber) {
            builder = builder.url(REGISTER_URL + "Token/Send?" + "profileId=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&registrationMode=" + "Phone" + "&newEmailorPhone=" + editText.getText().toString() + "&tokenType=" + "4");
        } else {
            builder = builder.url(REGISTER_URL + "Token/Send?" + "profileId=" + mSavePreference.getString(IpreferenceKey.TCODE)  + "&registrationMode=" + "Email" + "&newEmailorPhone=" + editText1.getText().toString() + "&tokenType=" + "4");
        }
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new HttpAsyncTask().execute(request);

    }


    public void validateToken(View view) {
        //API call to register user
        JSONObject jsonObject = new JSONObject();


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL + "Token/Validate?" + "token=" + tenantDetails.getTenantDetails().get(0).getTenantMobile() + "&registrationMode=Phone" + "&oldPhoneNumber=" + editText.getText().toString() + "&newPhoneNumber=" + "");
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new HttpAsyncTask().execute(request);


    }

    public void UpdateTenantDetials() {
        isSave=0;
        isSave1=0;
        isSave2=0;
        BASE_URL = IConstant.BASE_URL;

        String REQUEST_URL = BASE_URL + "Yardi/UpdateTenantDetails";
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("Tenant_Alt_Mob_No", Alternate.getText().toString());

            jsonObject.put("Tenant_Landline_No", Landline.getText().toString());
            // jsonObject.put("Tenant_Mobile", Contact.getText().toString());
            jsonObject.put("Tenant_Emergency_No", Emergency.getText().toString());
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            //   jsonObject.put("Tenant_Visa_Number", tenantDetails.getTenantDetails().get(0).getTenantVisaNumber());

            jsonObject.put("Tenant_Zip", PO1.getText().toString());
            jsonObject.put("Company_Name", ComName.getText().toString());
            jsonObject.put("Company_Addr", ComAdd.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new UpdateTenantDetails().execute(request);
        //   builder = builder.addHeader("content-type", "application/json; charset=utf-8");

    }

    //Http Request is handled below
    class UpdateTenantDetails extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating Details...");
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
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("UpdateTenantProfile");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String status = jsonObject1.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Profile Details");
                        alertDialog.setMessage("Successfully Updated!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // startActivity(new Intent(getActivity(), HomeActivity.class));
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

    }


    private void loadFragment(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isValidMobileNumber(CharSequence number) {

        boolean found1;
        Pattern pattern = Pattern.compile("\\s");
        String space = String.valueOf(number.charAt(3));
        Matcher matcher = pattern.matcher(space);
        boolean found = matcher.find();
        if (number.charAt(0) == '0') {
            found1 = true;
        } else {
            found1 = false;
        }
        if (found1 && found) {
            return true;
        } else {
            return false;

        }
    }

    private boolean isValidMobileNumberLandline(CharSequence number) {

        boolean found1;
        Pattern pattern = Pattern.compile("\\s");
        String space = String.valueOf(number.charAt(2));
        Matcher matcher = pattern.matcher(space);
        boolean found = matcher.find();
        if (number.charAt(0) == '0') {
            found1 = true;
        } else {
            found1 = false;
        }
        if (found1 && found) {
            return true;
        } else {
            return false;

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
    public static boolean isValidLL(String s)
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


            String reg= "^(?:\\+0|0|0)?(?:1|2|3|4|6|5|7|8|9) ?\\d{7}$";
            String regex = "((064) ?){7}[0-9]$";
            p = Pattern.compile(reg);
        }else {
            String regex = "((054) ?){7}[0-9]$";
            String reg= "^(?:\\+0|0|0)?(?:1|2|3|4|6|5|7|8|9) ?\\d{7}$";
            p = Pattern.compile(reg);

        }
        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }


    @Subscribe
    public void onEvent(StepperStatus event) {
        editButton.setVisibility(View.VISIBLE);
        editText.setText(tempPhoneNumber);
    }
}

