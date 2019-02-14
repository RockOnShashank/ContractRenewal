package com.residents.dubaiassetmanagement.Profile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.BuildConfig;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Model.OccupantDetail;
import com.residents.dubaiassetmanagement.Model.PetDetail;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DependantsFragment extends Fragment implements View.OnClickListener, MyRecyclerViewAdapter.ItemClickListener,
        MyRecyclerViewAdapter.AdapterInterface,MyRecyclerViewAdapter.DeleteOccupant ,HouseHelpAdapter.ItemClickListener,HouseHelpAdapter.HouseAdapterInterface
        ,HouseHelpAdapter.DeleteHouseHelp,PetAdapter.ItemClickListener,PetAdapter.PetAdapterInterface,PetAdapter.DeletePetInterface {

    private static final String TAG = "tab1";
    private Spinner spinRelationship;

    private Button bt_cancel, bt_save, bt_house_cancel, bt_house_save, bt_pets_save, bt_pets_cancel;

    private LinearLayout ll_add_family, ll_house_help, ll_pets, ll_family, ll_show_family, ll_house_add_family,
            ll_house_show_tv_full_namefamily, ll_pets_add_family, ll_pets_show_tv_full_namefamily;
    private ImageView iv_add_family, iv_add_house, iv_add_pets;
    private MyRecyclerViewAdapter adapter;
    private HouseHelpAdapter houseHelpAdapter;
    private PetAdapter petAdapter;
private RelativeLayout ll_show_tv_full_namefamily;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_house;
    private RecyclerView recyclerView_pets;

    private int adapterPostion;
    private SavePreference mSavePreference;


    private EditText et_first_name, et_last_name, et_relationship, et_email, et_mobile_number, tv_full_name, tv_relationship, tv_email, tv_phone_number,
            et_house_name, et_occupantId_House,et_house_last_name,et_house_contact, et_pets_name, et_pets_breed, et_pets_pet_type,et_occupantId,et_petId;
    private View view;
    private ArrayList<String> familyMemberInfoList;
    private ArrayList<FamilyInfo> familyList;
    private ArrayList<HouseHelp> houseHelpList;
    private ArrayList<PetDetails> petDetailsList;
    private TenantDetails tenantDetails;
    private ArrayList<OccupantDetail> tenantDetailsArrayList;
    private ArrayList<PetDetail> petDetailsArrayList;

    private ArrayList<OccupantDetail> familyList1;
    private ArrayList<OccupantDetail> maidList;
    private ArrayList<OccupantDetail> occupantList;
    private TextView register_error_message,register_error_message1;

    private ApplicationPreferences preferences;
    private  String BASE_URL;
    private String fNames,lNames,mobileNumber,emailId,relationshipStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dependants, container, false);

        tenantDetails = VolleySingleton.getInstance().getTenantDetails();

        // data to populate the RecyclerView with
        familyMemberInfoList = new ArrayList<>();
        familyList = new ArrayList<>();
        familyList1 = new ArrayList<>();
        houseHelpList = new ArrayList<>();
        petDetailsList = new ArrayList<>();
        maidList = new ArrayList<>();
        petDetailsArrayList = new ArrayList<>();
        occupantList = new ArrayList<>();
        preferences = ApplicationPreferences.getInstance(getActivity());
        mSavePreference = SavePreference.getInstance(getActivity());
        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.rv_family);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        int originalItemSize = occupantList.size();

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();

        if (occupantList.size() > originalItemSize ){
            params.height = params.height + 100;
            originalItemSize  = occupantList.size();
            recyclerView.setLayoutParams(params);
        }









        recyclerView_house = view.findViewById(R.id.rv_house_family);
        recyclerView_house.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_house = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecorationhouse = new DividerItemDecoration(recyclerView_house.getContext(),
                linearLayoutManager_house.getOrientation());
        recyclerView_house.addItemDecoration(dividerItemDecorationhouse);


        recyclerView_pets = view.findViewById(R.id.rv_pets_family);
        recyclerView_pets.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_pets = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecorationpets = new DividerItemDecoration(recyclerView_pets.getContext(),
                linearLayoutManager_pets.getOrientation());
        recyclerView_pets.addItemDecoration(dividerItemDecorationpets);

        spinRelationship = (Spinner) view.findViewById(R.id.spin_relationship);
        ArrayAdapter<String> adapterRelationship = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.relationship_array));//setting the country_array to spinner
        adapterRelationship.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRelationship.setAdapter(adapterRelationship);
        spinRelationship.setPrompt("Relationship*");
        spinRelationship.setSelection(0);

//if you want to set any action you can do in this listener
        spinRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

                // First item will be gray



                String select_item =spinRelationship.getItemAtPosition(position).toString();
                et_relationship.setText(select_item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ll_add_family = (LinearLayout) view.findViewById(R.id.ll_add_family);
        ll_house_help = (LinearLayout) view.findViewById(R.id.ll_house_help);
        ll_pets = (LinearLayout) view.findViewById(R.id.ll_pets);
        ll_family = (LinearLayout) view.findViewById(R.id.ll_family);
        ll_show_tv_full_namefamily = (RelativeLayout) view.findViewById(R.id.ll_show_tv_full_namefamily);
        ll_house_show_tv_full_namefamily = (LinearLayout) view.findViewById(R.id.ll_house_show_tv_full_namefamily);
        ll_pets_show_tv_full_namefamily = (LinearLayout) view.findViewById(R.id.ll_pets_show_tv_full_namefamily);
        ll_pets_add_family = (LinearLayout) view.findViewById(R.id.ll_pets_add_family);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_save = (Button) view.findViewById(R.id.bt_save);

        bt_house_cancel = (Button) view.findViewById(R.id.bt_house_cancel);
        bt_house_save = (Button) view.findViewById(R.id.bt_house_save);
        bt_pets_save = (Button) view.findViewById(R.id.bt_pets_save);
        bt_pets_cancel = (Button) view.findViewById(R.id.bt_pets_cancel);

        et_first_name = (EditText) view.findViewById(R.id.et_first_name);
        et_last_name = (EditText) view.findViewById(R.id.et_last_name);
        et_relationship = (EditText) view.findViewById(R.id.et_relationship);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_mobile_number = (EditText) view.findViewById(R.id.et_mobile_number);
        et_occupantId = (EditText) view.findViewById(R.id.et_occupantId);
        ll_show_family = (LinearLayout) view.findViewById(R.id.ll_show_family);
        ll_house_add_family = (LinearLayout) view.findViewById(R.id.ll_house_add_family);
        iv_add_family = (ImageView) view.findViewById(R.id.iv_add_family);
        iv_add_house = (ImageView) view.findViewById(R.id.iv_add_house);
        iv_add_pets = (ImageView) view.findViewById(R.id.iv_add_pets);

        tv_full_name = (EditText) view.findViewById(R.id.tv_full_name);
        tv_relationship = (EditText) view.findViewById(R.id.tv_relationship);
        tv_email = (EditText) view.findViewById(R.id.tv_email);
        tv_phone_number = (EditText) view.findViewById(R.id.tv_phone_number);
        et_house_name = (EditText) view.findViewById(R.id.et_house_name);
        et_occupantId_House = (EditText) view.findViewById(R.id.et_occupantId_House);
        et_house_last_name= (EditText) view.findViewById(R.id.et_house_last_name);
        et_house_contact = (EditText) view.findViewById(R.id.et_house_contact);

        register_error_message1 = (TextView) view.findViewById(R.id.register_error_message1);
        register_error_message = (TextView) view.findViewById(R.id.register_error_message);

        et_pets_name = (EditText) view.findViewById(R.id.et_pets_name);
        et_pets_pet_type = (EditText) view.findViewById(R.id.et_pets_pet_type);
        et_pets_breed = (EditText) view.findViewById(R.id.et_pets_breed);
        et_petId = (EditText) view.findViewById(R.id.et_petId);
        ll_family.setOnClickListener(this);
        ll_house_help.setOnClickListener(this);
        ll_pets.setOnClickListener(this);
        ll_add_family.setOnClickListener(this);
        ll_house_add_family.setOnClickListener(this);
        iv_add_family.setOnClickListener(this);
        iv_add_house.setOnClickListener(this);
        iv_add_pets.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        bt_house_cancel.setOnClickListener(this);
        bt_house_save.setOnClickListener(this);
        bt_pets_cancel.setOnClickListener(this);
        bt_pets_save.setOnClickListener(this);
        ll_show_tv_full_namefamily.setOnClickListener(this);
        ll_house_show_tv_full_namefamily.setOnClickListener(this);

        if (tenantDetails != null){
            if (tenantDetails.getOccupantDetails() != null) {
                if (tenantDetails.getOccupantDetails().size() > 0) {

                    tenantDetailsArrayList = new ArrayList<>();
                    tenantDetailsArrayList.addAll(tenantDetails.getOccupantDetails());

                    for (int i = 0; i < tenantDetailsArrayList.size(); i++) {
                        if (tenantDetailsArrayList.get(i).getOccupantRelation().equalsIgnoreCase("Maid") || tenantDetailsArrayList.get(i).getOccupantRelation().equalsIgnoreCase("Other")) {
                            maidList.add(tenantDetailsArrayList.get(i));
                        }else{
                            occupantList.add(tenantDetailsArrayList.get(i));
                        }
                    }

                    if (maidList != null) {
                        ll_house_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                        houseHelpAdapter = new HouseHelpAdapter(getActivity(), maidList, this, this);
                        houseHelpAdapter.setClickListener(this);
                        recyclerView_house.setAdapter(houseHelpAdapter);
                    } else
                    {
                        ll_house_show_tv_full_namefamily.setVisibility(View.GONE);
                    }

                    if (occupantList.size()>0) {
                        ll_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                        adapter = new MyRecyclerViewAdapter(getActivity(), occupantList, this, this);
                        adapter.setClickListener(this);
                        recyclerView.setAdapter(adapter);
                    }

                }else{
                    ll_show_tv_full_namefamily.setVisibility(View.GONE);

                }
            }



        }
        if (tenantDetails!=null) {
            if (tenantDetails.getPetDetails() != null) {
                if (tenantDetails.getPetDetails().size() > 0) {
                    petDetailsArrayList.addAll(tenantDetails.getPetDetails());
                    ll_pets_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                    petAdapter = new PetAdapter(getActivity(), petDetailsArrayList, this, this);
                    petAdapter.setClickListener(this);
                    recyclerView_pets.setAdapter(petAdapter);

                }
            } else {
                ll_pets_show_tv_full_namefamily.setVisibility(View.GONE);

            }
        }


     /*   et_pets_breed.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });*/

      /*  et_pets_pet_type.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });*/

      /*  et_pets_name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(40) {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {

                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z0-9. ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });*/
      /*  et_first_name.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });*/
              /*  et_first_name.setFilters(new InputFilter[] {
                        new InputFilter() {
                            public CharSequence filter(CharSequence src, int start,
                                                       int end, Spanned dst, int dstart, int dend) {
                                if(src.equals("")){ // for backspace
                                    return src;
                                }
                                if(src.toString().matches("[a-zA-Z ]+")){
                                    return src;
                                }
                                return "";
                            }
                        }
                });*/

        et_mobile_number.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(et_mobile_number.getText().toString().length()==3) {

                    c0 = et_mobile_number.getText().toString().charAt(0);
                    c1 = et_mobile_number.getText().toString().charAt(1);
                    c2 = et_mobile_number.getText().toString().charAt(2);
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


        et_house_contact.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private  int v = 0;
            private char c0,c1,c2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(et_house_contact.getText().toString().length()==3) {

                    c0 = et_house_contact.getText().toString().charAt(0);
                    c1 = et_house_contact.getText().toString().charAt(1);
                    c2 = et_house_contact.getText().toString().charAt(2);
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


        return view;

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_family:


                break;
            case R.id.ll_house_help:


                break;
            case R.id.ll_pets:

                break;
            case R.id.ll_add_family:

                break;
            case R.id.iv_add_family:
                iv_add_family.setVisibility(View.INVISIBLE);
                register_error_message.setVisibility(View.GONE);
                register_error_message1.setVisibility(View.GONE);
                et_occupantId.setText("");
                et_first_name.setText("");
                et_last_name.setText("");
                et_email.setText("");
                et_relationship.setText("");
                et_mobile_number.setText("");
                ll_add_family.setVisibility(View.VISIBLE);
                et_first_name.requestFocus();

                break;
            case R.id.iv_add_house:
                iv_add_house.setVisibility(View.INVISIBLE);
                register_error_message1.setVisibility(View.GONE);
                register_error_message.setVisibility(View.GONE);

                et_house_name.setText("");
                et_house_last_name.setText("");
                et_house_contact.setText("");
                et_occupantId_House.setText("");
                ll_house_add_family.setVisibility(View.VISIBLE);

                break;
            case R.id.iv_add_pets:
                iv_add_pets.setVisibility(View.INVISIBLE);

                et_pets_name.setText("");
                et_pets_pet_type.setText("");
                et_pets_breed.setText("");
                ll_pets_add_family.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_cancel:
                iv_add_family.setVisibility(View.VISIBLE);
                ll_add_family.setVisibility(View.GONE);

                if (et_occupantId.getText().toString().equalsIgnoreCase("")) {

                }else {
                    setAdapterCancel(et_occupantId.getText().toString());
                }
                break;
            case R.id.bt_save:
                iv_add_family.setVisibility(View.VISIBLE);

                if (!checkMadatoryFieldsforFamilies(et_first_name.getText().toString(),et_last_name.getText().toString(),et_relationship.getText().toString())){
                    Toast.makeText(getActivity(),"Please fill all the mandatory fields",Toast.LENGTH_LONG).show();
                }else{
                    ll_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                    // setAdapter();
                    if(et_email.getText().toString().length()>1 || et_mobile_number.getText().toString().length()>1) {

                        if (et_mobile_number.getText().toString().length() < 11 && et_mobile_number.getText().toString().length()>1 ) {
                            //  Toast.makeText(getActivity(), "Please Enter correct email or number", Toast.LENGTH_LONG).show();
                            ll_add_family.setVisibility(View.VISIBLE);
                            register_error_message.setVisibility(View.VISIBLE);
                            register_error_message.setText("Mobile number format is 05X XXXXXXX");
                        } else {


                            if(et_mobile_number.getText().toString().length() == 11) {

                                if (isValidMobileNumber(et_mobile_number.getText().toString()) && et_mobile_number.getText().toString().length() == 11) {
                                    if (et_occupantId.getText().toString().equalsIgnoreCase("")) {
                                        if (isValid(et_mobile_number.getText().toString())) {
                                            register_error_message.setVisibility(View.GONE);

                                            if (et_email.getText().toString().length()>1){
                                                if (isValidEmail(et_email.getText().toString())){
                                                    ll_add_family.setVisibility(View.GONE);

                                                    createDependant();
                                                }else {
                                                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                            else {
                                                ll_add_family.setVisibility(View.GONE);
                                                createDependant();
                                            }


                                        } else {
                                            register_error_message.setVisibility(View.VISIBLE);
                                            register_error_message.setText("Mobile number format is 05X XXXXXXX");
                                            // Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                                        }
                                    } else {
                                        if (isValid(et_mobile_number.getText().toString())) {
                                            if (et_email.getText().toString().length()>1){
                                                if (isValidEmail(et_email.getText().toString())){
                                                    ll_add_family.setVisibility(View.GONE);

                                                    updateDependant();
                                                }else {
                                                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                                }
                                            }else {
                                                ll_add_family.setVisibility(View.GONE);
                                                updateDependant();

                                            }
                                        } else {
                                            register_error_message.setVisibility(View.VISIBLE);
                                            register_error_message.setText("Mobile number format is 05X XXXXXXX");
                                            //Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                } else {

                                    if (et_email.getText().toString().length()>1){
                                        if (isValidEmail(et_email.getText().toString())){
                                            ll_add_family.setVisibility(View.GONE);
                                            createDependant();
                                        }else {
                                            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                        }
                                    }else {
                                        Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                    }



                                    // register_error_message.setVisibility(View.VISIBLE);
                                    //register_error_message.setText("Mobile number format is 05X XXXXXXX");
                                    //  Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();
                                }

                            }else {
                                if (et_email.getText().toString().length()>1){
                                    if (isValidEmail(et_email.getText().toString())){
                                        ll_add_family.setVisibility(View.GONE);
                                        createDependant();
                                    }else {
                                        Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();

                                }
                                //  register_error_message.setVisibility(View.VISIBLE);
                                //register_error_message.setText("Mobile number format is 05X XXXXXXX");
                                //  Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                            }


                        }
                    }else {
                        ll_add_family.setVisibility(View.GONE);
                        if (et_occupantId.getText().toString().equalsIgnoreCase("")) {
                            createDependant();
                        } else {
                            updateDependant();
                        }
                    }
                }

                break;

            case R.id.bt_house_cancel:
                iv_add_house.setVisibility(View.VISIBLE);
                ll_house_add_family.setVisibility(View.GONE);

                if(et_occupantId_House.getText().toString().equalsIgnoreCase("")) {

                }else {
                    setHomeAdapter(et_occupantId_House.getText().toString());
                }
                break;
            case R.id.bt_house_save:
                iv_add_house.setVisibility(View.VISIBLE);
                if (!checkMadatoryFieldsforHouse(et_house_name.getText().toString(),et_house_last_name.getText().toString())){
                    Toast.makeText(getActivity(),"Please fill all the mandatory fields",Toast.LENGTH_LONG).show();

                }else {
                    if (et_house_contact.getText().toString().isEmpty()){

                        ll_house_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                        //setHomeAdapter();
                        ll_house_add_family.setVisibility(View.GONE);
                        if(et_occupantId_House.getText().toString().equalsIgnoreCase("")) {
                            createHouseHelp();
                        }else {
                            updateHouseHelp();
                        }
                    }else {

                        if(et_house_contact.getText().toString().length() == 11) {

                            if (isValidMobileNumber(et_house_contact.getText().toString()) && et_house_contact.getText().toString().length() == 11) {
                                ll_house_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                                //setHomeAdapter();
                                ll_house_add_family.setVisibility(View.GONE);
                                if (et_occupantId_House.getText().toString().equalsIgnoreCase("")) {
                                    if (isValid(et_house_contact.getText().toString())) {
                                        createHouseHelp();

                                    } else {
                                        ll_house_add_family.setVisibility(View.VISIBLE);
                                        register_error_message1.setVisibility(View.VISIBLE);
                                        register_error_message1.setText("Mobile number format is 05X XXXXXXX");
                                        //Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    updateHouseHelp();
                                }
                            } else {
                                register_error_message1.setVisibility(View.VISIBLE);
                                register_error_message1.setText("Mobile number format is 05X XXXXXXX");
                                //Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                            }
                        }else {
                            register_error_message1.setVisibility(View.VISIBLE);
                            register_error_message1.setText("Mobile number format is 05X XXXXXXX");
                            //  Toast.makeText(getActivity(), "Mobile number format is 05X XXXXXXX", Toast.LENGTH_LONG).show();

                        }

                    }
                }
                break;


            case R.id.bt_pets_cancel:
                iv_add_pets.setVisibility(View.VISIBLE);

                ll_pets_add_family.setVisibility(View.GONE);
                if (et_petId.getText().toString().equalsIgnoreCase("")) {
                }else {
                    setPetsAdapter(et_petId.getText().toString());
                }
                break;
            case R.id.bt_pets_save:
                iv_add_pets.setVisibility(View.VISIBLE);

                if (!checkMadatoryFieldsforFamily(et_pets_name.getText().toString(), et_pets_pet_type.getText().toString())){
                    Toast.makeText(getActivity(),"Please fill all the mandatory fields",Toast.LENGTH_LONG).show();

                }else {
                    ll_pets_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                    //  setPetsAdapter();
                    ll_pets_add_family.setVisibility(View.GONE);
                    if (et_petId.getText().toString().equalsIgnoreCase("")) {
                        createPet();
                    }else {
                        updatePet();
                    }
                }
                break;
            case R.id.ll_house_show_tv_full_namefamily:

               /* if (!checkMadatoryFieldsforFamily(et_pets_name.getText().toString(),et_pets_pet_type.getText().toString(),et_pets_breed.getText().toString())){
                    Toast.makeText(getActivity(),"Please fill all the mandatory fields",Toast.LENGTH_LONG).show();

                }else {
                    ll_pets_show_tv_full_namefamily.setVisibility(View.VISIBLE);
                    //  setPetsAdapter();
                    ll_pets_add_family.setVisibility(View.GONE);
                    if (et_petId.getText().toString().equalsIgnoreCase("")) {

                        createPet();
                    }else {

                        updatePet();
                    }
                }*/
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        /*ll_show_tv_full_namefamily.setVisibility(View.GONE);
        ll_house_show_tv_full_namefamily.setVisibility(View.GONE);
        ll_pets_show_tv_full_namefamily.setVisibility(View.GONE);*/
    }

    /*  public void setAdapter(String occupantId) {
          FamilyInfo familyInfo = new FamilyInfo(et_first_name.getText().toString(), et_last_name.getText().toString(), et_relationship.getText().toString(), et_email.getText().toString(), et_mobile_number.getText().toString(), adapterPostion, occupantId);
          familyList.add(familyInfo);
          adapter = new MyRecyclerViewAdapter(getActivity(), familyList, this,this);
          adapter.setClickListener(this);
          recyclerView.setAdapter(adapter);
      }*/

    /*public void setAdapter(String occupantId) {
        //public OccupantDetail(String tenantCode, String occupantId, String occupantName, String occupantRelation, String occupantEmail, String occupantMobile) {
        OccupantDetail familyInfo = new OccupantDetail(mSavePreference.getString(IpreferenceKey.TCODE),occupantId,fNames+" "+lNames, relationshipStatus, emailId, mobileNumber, adapterPostion );
        occupantList.add(familyInfo);
        adapter = new MyRecyclerViewAdapter(getActivity(), occupantList, this,this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }*/
    public void setAdapter(String occupantId) {
        //public OccupantDetail(String tenantCode, String occupantId, String occupantName, String occupantRelation, String occupantEmail, String occupantMobile) {
        OccupantDetail familyInfo = new OccupantDetail(mSavePreference.getString(IpreferenceKey.TCODE),occupantId,et_first_name.getText().toString()+" "+et_last_name.getText().toString(), et_relationship.getText().toString(), et_email.getText().toString(), et_mobile_number.getText().toString(), adapterPostion );
        occupantList.add(familyInfo);
        adapter = new MyRecyclerViewAdapter(getActivity(), occupantList, this,this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    public void setAdapterCancel(String occupantId) {
        //public OccupantDetail(String tenantCode, String occupantId, String occupantName, String occupantRelation, String occupantEmail, String occupantMobile) {
        OccupantDetail familyInfo = new OccupantDetail(mSavePreference.getString(IpreferenceKey.TCODE),occupantId,fNames+" "+lNames, relationshipStatus, emailId, mobileNumber, adapterPostion );
        occupantList.add(familyInfo);
        adapter = new MyRecyclerViewAdapter(getActivity(), occupantList, this,this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    /* public void setHomeAdapter(String occupantId) {
         HouseHelp houseHelp = new HouseHelp(et_house_name.getText().toString(),et_house_last_name.getText().toString(), et_house_contact.getText().toString(), adapterPostion, occupantId);
         houseHelpList.add(houseHelp);
         houseHelpAdapter = new HouseHelpAdapter(getActivity(), houseHelpList, this,this);
         houseHelpAdapter.setClickListener(this);
         recyclerView_house.setAdapter(houseHelpAdapter);
     }*/
    public void setHomeAdapter(String occupantId) {

        OccupantDetail houseHelp = new OccupantDetail(et_house_name.getText().toString()+" "+et_house_last_name.getText().toString(), et_house_contact.getText().toString(), adapterPostion, occupantId);
        maidList.add(houseHelp);
        houseHelpAdapter = new HouseHelpAdapter(getActivity(), maidList, this,this);
        houseHelpAdapter.setClickListener(this);
        recyclerView_house.setAdapter(houseHelpAdapter);
    }

 /*   public void setPetsAdapter(String petId) {
        PetDetails petDetails = new PetDetails(et_pets_name.getText().toString(), et_pets_pet_type.getText().toString(), et_pets_breed.getText().toString(), adapterPostion,petId);
        petDetailsList.add(petDetails);
        petAdapter = new PetAdapter(getActivity(), petDetailsArrayList, this,this);
        petAdapter.setClickListener(this);
        recyclerView_pets.setAdapter(petAdapter);
    }
*/

    public void setPetsAdapter(String petId) {
        PetDetail petDetails = new PetDetail(et_pets_name.getText().toString(), et_pets_pet_type.getText().toString()+"-"+et_pets_breed.getText().toString(), adapterPostion,petId);
        petDetailsArrayList.add(petDetails);
        petAdapter = new PetAdapter(getActivity(), petDetailsArrayList, this,this);
        petAdapter.setClickListener(this);
        recyclerView_pets.setAdapter(petAdapter);
    }

    @Override
    public void editPressed(String fName, String lName, String relationship, String email, String phoneNumber, int pos,String occupantId) {
iv_add_family.setVisibility(View.INVISIBLE);
et_first_name.requestFocus();
ll_add_family.requestFocus();
        fNames = fName;
        lNames = lName;
        mobileNumber = phoneNumber;
        emailId = email;
        relationshipStatus = relationship;


        ll_add_family.setVisibility(View.VISIBLE);
        et_first_name.setText(fName);
        et_last_name.setText(lName);
        et_relationship.setText(relationship);
        et_email.setText(email);
        et_mobile_number.setText(phoneNumber);
        et_occupantId.setText(occupantId);
        adapterPostion = pos;
        if (relationship.equalsIgnoreCase("Spouse")){
            spinRelationship.setSelection(1);
        }
        if (relationship.equalsIgnoreCase("Son")){
            spinRelationship.setSelection(2);
        }
        if (relationship.equalsIgnoreCase("Daughter")){
            spinRelationship.setSelection(3);
        }
        if (relationship.equalsIgnoreCase("Father")){
            spinRelationship.setSelection(4);
        }
        if (relationship.equalsIgnoreCase("Mother")){
            spinRelationship.setSelection(5);
        }


    }
    @Override
    public void deletePressed(String firstName, String lastName, String relationship, String email, String phoneNumber, int pos, String occupantId) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Occupant_Id",occupantId);
            jsonObject.put("Occupant_MoveOut_Date", getDate());
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", firstName);
            jsonObject.put("Occupant_Last_Name", lastName);
            jsonObject.put("Occupant_Relation", relationship);
            jsonObject.put("Occupant_Email",email);
            jsonObject.put("Occupant_Mobile", phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));


        Request request = builder.build();

        new DeleteDependantCall().execute(request);
    }

    @Override
    public void editPressedHouse(String firstName, String lastName, String contact, int pos,String occupantId) {
        iv_add_house.setVisibility(View.INVISIBLE);
        register_error_message.setVisibility(View.GONE);
        register_error_message1.setVisibility(View.GONE);
        ll_house_add_family.setVisibility(View.VISIBLE);
        et_house_name.setText(firstName);
        et_house_last_name.setText(lastName);
        et_house_contact.setText(contact);
        et_occupantId_House.setText(occupantId);
        adapterPostion = pos;
    }

    @Override
    public void editPressedPets(String name, String petType, String breed, int pos, String petId) {

iv_add_pets.setVisibility(View.INVISIBLE);
        ll_pets_add_family.setVisibility(View.VISIBLE);
        et_pets_name.requestFocus();
        et_pets_name.setText(name);

        String full =petType;
        String breeds = full.substring(full.lastIndexOf("-")+1);
        String type= full.replaceAll(breeds,"");

        et_pets_pet_type.setText(type.replace("-",""));
        et_pets_breed.setText(breeds);
        et_petId.setText(petId);
        adapterPostion = pos;
    }

    public boolean checkMadatoryFieldsforFamily(String fName, String lName) {

        if (fName.isEmpty() || lName.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkMadatoryFieldsforFamilies(String fName, String lName, String relationShip) {

        if (fName.isEmpty() || lName.isEmpty() || relationShip.equalsIgnoreCase("Relationship*")) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkMadatoryFieldsforHouse(String name, String lName) {

        if (name.isEmpty()  || lName.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void deletePressedHouse(String firstName, String lastName, String contact, int pos, String occupantId) {


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Occupant_Id",occupantId);
            jsonObject.put("Occupant_MoveOut_Date", getDate());
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", firstName);
            jsonObject.put("Occupant_Last_Name", lastName);
            jsonObject.put("Occupant_Relation", "Other");
            jsonObject.put("Occupant_Email","");
            jsonObject.put("Occupant_Mobile", contact);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new DeleteHouseHelpCall().execute(request);

    }

    @Override
    public void deletePressedPets(final String name, final String petType, final String breed, int pos,final String petId) {



        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Pet_Id", petId);
            jsonObject.put("Pet_Weight", "0");
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Pet_Name", name);
            jsonObject.put("Pet_Type", petType+"-"+breed);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdatePet";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new DeletePetCall().execute(request);




    }

    public class CreatePetCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Adding Pet...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("CreatePet", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("CreatePet");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        String petId = jsonArray.getString("Pet_Id");
                        setPetsAdapter(petId);

                        Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class CreateDependantCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Adding Dependant...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    JSONArray jsonObject = responseObject.getJSONArray("CreateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        String occupant_Id = jsonArray.getString("Occupant_Id");
                        setAdapter(occupant_Id);
                        Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_LONG).show();
                    }
                    Log.i("CreateDependant", "" + responseObject);
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class CreateHouseHelpCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Adding House Help...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("CreateHouseHelp", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("CreateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        String occupant_Id = jsonArray.getString("Occupant_Id");
                        setHomeAdapter(occupant_Id);
                        Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void createDependant()
    {
        register_error_message.setVisibility(View.GONE);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Tenant_Code", "" +mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", "" + et_first_name.getText().toString());
            jsonObject.put("Occupant_Last_Name", et_last_name.getText().toString());
            jsonObject.put("Occupant_Relation", et_relationship.getText().toString());
            jsonObject.put("Occupant_Email", et_email.getText().toString());
            jsonObject.put("Occupant_Mobile", et_mobile_number.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/CreateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new CreateDependantCall().execute(request);



    }

    public void createPet()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Tenant_Code", "" +mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Pet_Name", "" + et_pets_name.getText().toString());
            jsonObject.put("Pet_Type", et_pets_pet_type.getText().toString()+"-"+et_pets_breed.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/CreatePet";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new CreatePetCall().execute(request);

    }
    public void createHouseHelp()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Tenant_Code", "" +mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", "" + et_house_name.getText().toString());
            jsonObject.put("Occupant_Last_Name", et_house_last_name.getText().toString());
            jsonObject.put("Occupant_Relation", "Other");
            jsonObject.put("Occupant_Email", "");
            jsonObject.put("Occupant_Mobile", et_house_contact.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/CreateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new CreateHouseHelpCall().execute(request);



    }
    public void updateDependant(){
        register_error_message.setVisibility(View.GONE);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Occupant_Id", et_occupantId.getText().toString());
            jsonObject.put("Occupant_MoveOut_Date", "");
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", et_first_name.getText().toString());
            jsonObject.put("Occupant_Last_Name", et_last_name.getText().toString());
            jsonObject.put("Occupant_Relation", et_relationship.getText().toString());
            jsonObject.put("Occupant_Email", et_email.getText().toString());
            jsonObject.put("Occupant_Mobile", et_mobile_number.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new UpdateDependantCall().execute(request);


    }
    public void updatePet(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Pet_Id", et_petId.getText().toString());
            jsonObject.put("Pet_Weight", "1");
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Pet_Name", et_pets_name.getText().toString());
            jsonObject.put("Pet_Type", et_pets_pet_type.getText().toString()+"-"+et_pets_breed.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdatePet";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new UpdatePetCall().execute(request);


    }

    public void updateHouseHelp(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Occupant_Id", et_occupantId_House.getText().toString());
            jsonObject.put("Occupant_MoveOut_Date", "");
            jsonObject.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Occupant_First_Name", et_house_name.getText().toString());
            jsonObject.put("Occupant_Last_Name", et_house_last_name.getText().toString());
            jsonObject.put("Occupant_Relation", "Other");
            jsonObject.put("Occupant_Email", "");
            jsonObject.put("Occupant_Mobile", et_house_contact.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+"Yardi/UpdateDependent";
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder= builder.addHeader("SessionId", preferences.getSessionId());
        builder= builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();

        new UpdateHouseHelpCall().execute(request);


    }
    public class UpdateHouseHelpCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating House Help...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("UpdateHouseHelp", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("UpdateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        setHomeAdapter(et_occupantId_House.getText().toString());
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class UpdateDependantCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating Dependant...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("UpdateDependant", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("UpdateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        setAdapter(et_occupantId.getText().toString());
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class UpdatePetCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating Pet...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("UpdatePet", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("UpdatePet");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        setPetsAdapter(et_occupantId.getText().toString());
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class DeletePetCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Deleting Pet...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("DeletePet", "" + responseObject);
                    JSONArray jsonObject = responseObject.getJSONArray("UpdatePet");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        //   setPetsAdapter(et_occupantId.getText().toString());
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_LONG).show();

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public class DeleteDependantCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Deleting Dependant...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("DeleteDependant", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("UpdateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        //   setAdapter(et_occupantId.getText().toString());
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class DeleteHouseHelpCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Deleting House Help...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("CreateDependant", "" + response);
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
                    Log.i("DeleteHouseHelp", "" + responseObject);

                    JSONArray jsonObject = responseObject.getJSONArray("UpdateOccupant");
                    JSONObject jsonArray = jsonObject.getJSONObject(0);
                    String status = jsonArray.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        //   setAdapter(et_occupantId.getText().toString());
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public String getDate(){
        Date d = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        return sdf.format(d);
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);

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


            String reg= "^(?:\\+0|0|0)?(?:60|61|62|63|64|65|67|68|69|50|51|52|53|54|55|56|57|58|59) ?\\d{7}$";
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
