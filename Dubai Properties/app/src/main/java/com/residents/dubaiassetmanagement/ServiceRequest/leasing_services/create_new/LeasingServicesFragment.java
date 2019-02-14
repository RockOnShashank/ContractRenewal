package com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.create_new;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.BuildConfig;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.SeeAllAdapterLS;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.adapter.SubCategoriesAdapter;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.Category;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.CreateRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.SubCategories;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models.SubCategory;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewRequestLSFragment;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LeasingServicesFragment  extends Fragment implements View.OnClickListener, SubCategoriesAdapter.ItemClickListener, LeasingServicesAdapter.ItemClickListener , SeeAllAdapterLS.ItemClickListener, ResponseCallback {

    private View view;
    private TextView fragmentTitle;

    private String BASE_URL, REQUEST_URL;
    private CreateRequest createRequestsList;
    private ImageView iv_1, iv_2, iv_3,iv_edit_phone, iv_4, iv_5, iv_see_all,iv_open_gallery, iv_first_selected, iv_second_selected,iv_third_selected, iv_first_delete, iv_second_delete, iv_third_delete;
    private LinearLayout  ll_iv_first_selected, ll_iv_second_selected, ll_iv_third_selected;
    private String image_ID1,imageID2,imageID3;
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_dialog;
    private String[] idArray, seeAllArray,categoryId;
    private int j = 0;
    private RecyclerView rv_see_All;
    private SubCategories subCategories;
    private LinearLayout ll_subCategories;
    private RecyclerView rv_categories;
    private RecyclerView rv_subCategories;
    private SubCategoriesAdapter subCategoriesAdapter;
    private ArrayList<SubCategory> subCategoriesArrayList;
    private int callComplete = 0;
    private static int lastClicked;
    private ProgressDialog progressDialog;
    CoordinatorLayout coordinator_layout;
    private SeeAllAdapterLS seeAllAdapter;
    private LeasingServicesAdapter leasingServicesAdapter;
    private PopupWindow popupWindow;
    Button cancelButton,saveButton;
    private String Cate;
    private Uri outputFileUri;
    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 2;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String REGISTER_URL;
    ArrayList<Category> categoryArrayList;
    String selectedImagePath, transactionId_first, transactionId_second, transactionId_third;
    private static int used = 0;
    private String categoryName, subCategoryName,templateCode,categoryid;
    private EditText et_number,et_description;
    private SavePreference mSavePreference;
    private ArrayList<String> arrayList;
    private ArrayList<String> categoryID;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String encodedImage,encodedImage2,encodedImage1;
    private ApplicationPreferences preferences;

    private ArrayList<Uri> mArrayUri;

    private Bitmap bitmap_1,bitmap_2,bitmap_3;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    ViewGroup root;
    private ImageView iv_cross;
    private LinearLayout ll_attach;
    private RelativeLayout iv_bell_icon;
    private BottomNavigationView bottomNavigation;
    private TextView register_error_message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("NEW REQUEST");
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leasing_services, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);

        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mSavePreference = SavePreference.getInstance(getActivity());
        categoryArrayList = new ArrayList<>();
        iv_1 = (ImageView) view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
        iv_4 = (ImageView) view.findViewById(R.id.iv_4);
        iv_5 = (ImageView) view.findViewById(R.id.iv_5);
        // ApplicationPreferences preferences =  ApplicationPreferences.getInstance(ApplicationController.getContext());
        preferences = ApplicationPreferences.getInstance(getActivity());
        used=0;
        et_description = (EditText) view.findViewById(R.id.et_description);
        et_number = (EditText) view.findViewById(R.id.et_number);

        iv_first_selected = (ImageView) view.findViewById(R.id.iv_first_selected);
        iv_second_selected = (ImageView) view.findViewById(R.id.iv_second_selected);
        iv_third_selected = (ImageView) view.findViewById(R.id.iv_third_selected);

        iv_edit_phone = (ImageView) view.findViewById(R.id.iv_edit_phone);
        iv_open_gallery = (ImageView) view.findViewById(R.id.iv_open_gallery);
        iv_see_all = (ImageView) view.findViewById(R.id.iv_see_all);
        tv_1 = (TextView) view.findViewById(R.id.tv_1);
        tv_2 = (TextView) view.findViewById(R.id.tv_2);
        tv_3 = (TextView) view.findViewById(R.id.tv_3);
        tv_4 = (TextView) view.findViewById(R.id.tv_4);
        tv_5 = (TextView) view.findViewById(R.id.tv_5);

        register_error_message = (TextView) view.findViewById(R.id.register_error_message);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();


        subCategoriesArrayList = new ArrayList<>();
        ll_subCategories = (LinearLayout) view.findViewById(R.id.ll_subCategories);
        ll_attach = (LinearLayout) view.findViewById(R.id.ll_attach);
        ll_attach.setVisibility(View.GONE);
        rv_categories = (RecyclerView) view.findViewById(R.id.rv_categories);
        rv_subCategories = (RecyclerView) view.findViewById(R.id.rv_subCategories);

        ll_iv_first_selected = (LinearLayout) view.findViewById(R.id.ll_iv_first_selected);
        ll_iv_second_selected = (LinearLayout) view.findViewById(R.id.ll_iv_second_selected);
        ll_iv_third_selected = (LinearLayout) view.findViewById(R.id.ll_iv_third_selected);


        iv_first_delete = (ImageView) view.findViewById(R.id.iv_first_delete);
        iv_second_delete = (ImageView) view.findViewById(R.id.iv_second_delete);
        iv_third_delete = (ImageView) view.findViewById(R.id.iv_third_delete);

        iv_first_delete.setOnClickListener(this);
        iv_second_delete.setOnClickListener(this);
        iv_third_delete.setOnClickListener(this);

        iv_see_all.setOnClickListener(this);
        iv_1.setOnClickListener(this);
        iv_2.setOnClickListener(this);
        iv_3.setOnClickListener(this);
        iv_4.setOnClickListener(this);
        iv_5.setOnClickListener(this);
        iv_edit_phone.setOnClickListener(this);

        iv_open_gallery.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        getCategories();
        et_number.setText(mSavePreference.getString(IpreferenceKey.PHONE_NUMBER));

        et_number.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            private int v = 0;
            private char c0, c1, c2;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_number.getText().toString().length() == 3) {

                    c0 = et_number.getText().toString().charAt(0);
                    c1 = et_number.getText().toString().charAt(1);
                    c2 = et_number.getText().toString().charAt(2);
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

        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if (et_description.getText().toString().length()>0 && et_number.getText().toString().length()==11) {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if (s.toString().length()>0) {
                    String last = s.toString().substring(s.toString().length() - 1);

                    if (last.equalsIgnoreCase(">")) {
                        String out = s.toString().replaceAll(">", "greater than");
                        et_description.setText(out);
                    }
                    if (last.equalsIgnoreCase("<")) {
                        String out = s.toString().replaceAll("<", "lesser than");
                        et_description.setText(out);
                    }
                    if (last.equalsIgnoreCase("&")) {
                        String out = s.toString().replaceAll("&", "and");
                        et_description.setText(out);
                    }
                    if (last.equalsIgnoreCase(",")) {
                        String out = s.toString().replaceAll(",", "comma");
                        et_description.setText(out);
                    }
                    if (last.equalsIgnoreCase("$")) {
                        String out =getDescription(s.toString());
                        et_description.setText(out);
                    }

                }
                et_description.setSelection(et_description.getText().length());

            }
        });
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_description.getText().toString().length() > 0 && et_number.getText().toString().length() == 11) {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_description.getText().toString().length() > 0 && et_number.getText().toString().length() == 11) {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.disabled_button));

                }
            }
        });
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragments(new ServicesFragment());
                // getFragmentManager().popBackStack();
            }
        });
        return view;

    }

    @Override
    public void onClick(View v) {
        callComplete = 0;

        switch (v.getId()) {

            case R.id.iv_first_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle( "" )
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                ll_iv_first_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL = BASE_URL + "Attachment?imageId=" + image_ID1;
                                JSONObject jsonObject1 = new JSONObject();


                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject1.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REQUEST_URL);
                                builder = builder.delete(body);
                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                builder =  builder.addHeader("SessionId", preferences.getSessionId());
                                builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
                                //

                                Request request = builder.build();
                                new DeleteAttachment().execute(request);
                            } }).show();

                break;
            case R.id.iv_second_delete:
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                dialog1.setTitle( "" )
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                ll_iv_second_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL1 = BASE_URL+"Attachment?imageId="+imageID2;
                                JSONObject jsonObject11 = new JSONObject();



                                MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body1 = RequestBody.create(JSON1, jsonObject11.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder1 = new Request.Builder();
                                builder1 = builder1.url(REQUEST_URL1);
                                builder1 = builder1.delete(body1);
                                builder1 = builder1.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
                                builder1 =  builder1.addHeader("SessionId", preferences.getSessionId());
                                builder1 =   builder1.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

                                Request request1 = builder1.build();
                                new DeleteAttachment().execute(request1);
                            } }).show();

                break;
            case R.id.iv_third_delete:
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(getActivity());
                dialog11.setTitle( "" )
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {


                                ll_iv_third_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL11 = BASE_URL+"Attachment?imageId="+imageID3;
                                JSONObject jsonObject111 = new JSONObject();



                                MediaType JSON11 = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body11 = RequestBody.create(JSON11, jsonObject111.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder11 = new Request.Builder();
                                builder11 = builder11.url(REQUEST_URL11);
                                builder11 = builder11.delete(body11);
                                builder11 = builder11.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
                                builder11 =  builder11.addHeader("SessionId", preferences.getSessionId());
                                builder11 =   builder11.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

                                Request request11 = builder11.build();
                                new DeleteAttachment().execute(request11);

                            } }).show();

                break;


            case R.id.iv_see_all:
                onButtonShowPopupWindowClick(v);
                break;
            case R.id.iv_1:
                lastClicked=1;

                callComplete = 1;
                getCategories();
                //setViewatPosition1();
                break;
            case R.id.iv_2:
                lastClicked=2;

                callComplete = 2;

                getCategories();

                //       setViewatPosition2();
                break;
            case R.id.iv_3:
                lastClicked=3;

                callComplete = 3;

                getCategories();

                //     setViewatPosition3();
                break;
            case R.id.iv_4:
                lastClicked=4;

                callComplete = 4;

                getCategories();

                //       setViewatPosition4();
                break;
            case R.id.iv_5:
                lastClicked=5;

                callComplete = 5;

                getCategories();

                //       setViewatPosition5();
                break;
            case R.id.cancelButton:
                getFragmentManager().popBackStack();

                break;
            case R.id.saveButton:

                Bundle bundle = new Bundle();

                bundle.putString("Object_Type","SR");
                bundle.putString("Object_Reference",mSavePreference.getString(IpreferenceKey.TCODE));

                if (transactionId_first==null) {
                    bundle.putString("Transaction_Id","");

                }
                else{
                    bundle.putString("Transaction_Id",transactionId_first);


                }

                bundle.putString("Tenant_Code",mSavePreference.getString(IpreferenceKey.TCODE));
                bundle.putString("Template_Code",templateCode);
                bundle.putString("Category",categoryName);
                bundle.putString("SubCategory",subCategoryName);
                bundle.putString("Problem_Description",et_description.getText().toString());
                bundle.putString("Caller_Name",mSavePreference.getString(IpreferenceKey.USERNAME));
                bundle.putString("Caller_Number",et_number.getText().toString());
                bundle.putString("Related_WOID","");
                if (et_description.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Description cannot be blank",Toast.LENGTH_LONG).show();
                }else {

                    JSONObject jsonObject = new JSONObject();

                    try {

                        jsonObject.put("Object_Type", "SR");
                        jsonObject.put("Object_Reference", mSavePreference.getString(IpreferenceKey.TCODE));
                        if (transactionId_first==null) {
                            jsonObject.put("Transaction_Id", "");

                        }else {
                            jsonObject.put("Transaction_Id", transactionId_first);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObjectPArent = new JSONObject();
                    try {
                        jsonObjectPArent.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
                        jsonObjectPArent.put("Template_Code",templateCode);
                        jsonObjectPArent.put("Category", categoryName);

                        if (bundle.getString("SubCategory") == null) {
                            jsonObjectPArent.put("SubCategory", "");

                        } else {
                            jsonObjectPArent.put("SubCategory", subCategoryName);
                        }
                        jsonObjectPArent.put("Problem_Description", et_description.getText().toString());
                        jsonObjectPArent.put("Caller_Name", mSavePreference.getString(IpreferenceKey.USERNAME));
                        jsonObjectPArent.put("Caller_Number", et_number.getText().toString());
                        jsonObjectPArent.put("Common_Area", "");
                        jsonObjectPArent.put("Preferred_Date", "");
                        jsonObjectPArent.put("Preferred_TimeSlot", "");
                        jsonObjectPArent.put("Related_WOID", "");
                        jsonObjectPArent.put("CreateSRAttachment", jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (isValid(et_number.getText().toString())){
                        new RequestService(getActivity(), LeasingServicesFragment.this).postRequest("Yardi/CreateServiceRequest", jsonObjectPArent);
                        register_error_message.setVisibility(View.GONE);
                    }else {
                        register_error_message.setVisibility(View.VISIBLE);
                        register_error_message.setText("Mobile number format is 05X XXXXXXX");

                    }

                }
                break;
            case R.id.iv_open_gallery:
               /* if (used == 3){
                    Toast.makeText(getActivity(),"You can upload maximum 3 images",Toast.LENGTH_LONG).show();
                }else {*/
                if (mSavePreference.getString(IpreferenceKey.IS_PERMISSIONS_GRANTED).equalsIgnoreCase("false")) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }else {
                    startDialog();


                }

                break;

            case R.id.iv_first_selected:

                showPopUp_1();
                break;

            case R.id.iv_second_selected:

                showPopUp_2();

                break;

            case R.id.iv_third_selected:

                showPopUp_3();

                break;
            case R.id.iv_edit_phone:
                et_number.requestFocus();

                et_number.setEnabled(true);
                break;
        }
    }

    private void setViewatPosition5() {
        String category_fifth = tv_5.getText().toString();

        if (category_fifth.equals("Air Conditioning")) {
            iv_5.setBackgroundResource(R.drawable.selected_ac);

        } else if (category_fifth.equals("Carpentry")) {
            iv_5.setBackgroundResource(R.drawable.selected_hammer);

        } else if (category_fifth.equals("Doors")) {
            iv_5.setBackgroundResource(R.drawable.selected_window);

        } else if (category_fifth.equals("Electrical")) {
            iv_5.setBackgroundResource(R.drawable.selected_plug);

        } else if (category_fifth.equals("Fire System")) {
            iv_5.setBackgroundResource(R.drawable.selected_fire);

        } else if (category_fifth.equals("General")) {
            iv_5.setBackgroundResource(R.drawable.selected_window);

        } else if (category_fifth.equals("Lighting")) {
            iv_5.setBackgroundResource(R.drawable.selected_bulb);

        } else if (category_fifth.equals("Masonry")) {
            iv_5.setBackgroundResource(R.drawable.selected_brick);

        } else if (category_fifth.equals("Painting")) {
            iv_5.setBackgroundResource(R.drawable.selected_paint);

        } else if (category_fifth.equals("Plumbing")) {
            iv_5.setBackgroundResource(R.drawable.selected_leakage);

        } else if (category_fifth.equals("Windows")) {
            iv_5.setBackgroundResource(R.drawable.selected_window);

        }
        // getSubCategories("5");
    }

    private void setViewatPosition4() {
        String category_fourth = tv_4.getText().toString();

        if (category_fourth.equals("Air Conditioning")) {
            iv_4.setBackgroundResource(R.drawable.selected_ac);

        } else if (category_fourth.equals("Carpentry")) {
            iv_4.setBackgroundResource(R.drawable.selected_hammer);

        } else if (category_fourth.equals("Doors")) {
            iv_4.setBackgroundResource(R.drawable.selected_window);

        } else if (category_fourth.equals("Electrical")) {
            iv_4.setBackgroundResource(R.drawable.selected_plug);

        } else if (category_fourth.equals("Fire System")) {
            iv_4.setBackgroundResource(R.drawable.selected_fire);

        } else if (category_fourth.equals("General")) {
            iv_4.setBackgroundResource(R.drawable.selected_window);

        } else if (category_fourth.equals("Lighting")) {
            iv_4.setBackgroundResource(R.drawable.selected_bulb);

        } else if (category_fourth.equals("Masonry")) {
            iv_4.setBackgroundResource(R.drawable.selected_brick);

        } else if (category_fourth.equals("Painting")) {
            iv_4.setBackgroundResource(R.drawable.selected_paint);

        } else if (category_fourth.equals("Plumbing")) {
            iv_4.setBackgroundResource(R.drawable.selected_leakage);

        } else if (category_fourth.equals("Windows")) {
            iv_4.setBackgroundResource(R.drawable.selected_window);

        }

        // getSubCategories("4");
    }

    private void setViewatPosition3() {
        String category_third = tv_3.getText().toString();

        if (category_third.equals("Air Conditioning")) {
            iv_3.setBackgroundResource(R.drawable.selected_ac);

        } else if (category_third.equals("Carpentry")) {
            iv_3.setBackgroundResource(R.drawable.selected_hammer);

        } else if (category_third.equals("Doors")) {
            iv_3.setBackgroundResource(R.drawable.selected_window);

        } else if (category_third.equals("Electrical")) {
            iv_3.setBackgroundResource(R.drawable.selected_plug);

        } else if (category_third.equals("Fire System")) {
            iv_3.setBackgroundResource(R.drawable.selected_fire);

        } else if (category_third.equals("General")) {
            iv_3.setBackgroundResource(R.drawable.selected_window);

        } else if (category_third.equals("Lighting")) {
            iv_3.setBackgroundResource(R.drawable.selected_bulb);

        } else if (category_third.equals("Masonry")) {
            iv_3.setBackgroundResource(R.drawable.selected_brick);

        } else if (category_third.equals("Painting")) {
            iv_3.setBackgroundResource(R.drawable.selected_paint);

        } else if (category_third.equals("Plumbing")) {
            iv_3.setBackgroundResource(R.drawable.selected_leakage);

        } else if (category_third.equals("Windows")) {
            iv_3.setBackgroundResource(R.drawable.selected_window);

        }
        //    getSubCategories("3");

    }

    private void setViewatPosition2() {
        String category_second = tv_2.getText().toString();

        if (category_second.equals("Air Conditioning")) {
            iv_2.setBackgroundResource(R.drawable.selected_ac);

        } else if (category_second.equals("Carpentry")) {
            iv_2.setBackgroundResource(R.drawable.selected_hammer);

        } else if (category_second.equals("Doors")) {
            iv_2.setBackgroundResource(R.drawable.selected_window);

        } else if (category_second.equals("Electrical")) {
            iv_2.setBackgroundResource(R.drawable.selected_plug);

        } else if (category_second.equals("Fire System")) {
            iv_2.setBackgroundResource(R.drawable.selected_fire);

        } else if (category_second.equals("General")) {
            iv_2.setBackgroundResource(R.drawable.selected_window);

        } else if (category_second.equals("Lighting")) {
            iv_2.setBackgroundResource(R.drawable.selected_bulb);

        } else if (category_second.equals("Masonry")) {
            iv_2.setBackgroundResource(R.drawable.selected_brick);

        } else if (category_second.equals("Painting")) {
            iv_2.setBackgroundResource(R.drawable.selected_paint);

        } else if (category_second.equals("Plumbing")) {
            iv_2.setBackgroundResource(R.drawable.selected_leakage);

        } else if (category_second.equals("Windows")) {
            iv_2.setBackgroundResource(R.drawable.selected_window);

        }
        //   getSubCategories("2");

    }

    @Override
    public void onItemClick(View view, int position) {


            int id = categoryArrayList.get(position).getId();
            categoryName = categoryArrayList.get(position).getCategoryName();
            //getSubCategories(id);

    }

    @Override
    public void onItemClickSecond(View view, int position) {


        subCategoryName = subCategoriesArrayList.get(position).getSubCategoryName();
        templateCode = subCategoriesArrayList.get(position).getTemplateCode();

    }

    @Override
    public void onItemClickSeeAll(View view, int position, int catId) {

        categoryName = arrayList.get(position);
        categoryid = categoryID.get(position);
        //  getSubCategories(Integer.parseInt(categoryid));
        popupWindow.dismiss();
        // show selected category at first position

        int numberOfColumns = 3;
        rv_categories.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        leasingServicesAdapter = new LeasingServicesAdapter(getActivity(), categoryArrayList,categoryName);
        leasingServicesAdapter.setClickListener(LeasingServicesFragment.this);
        rv_categories.setAdapter(leasingServicesAdapter);

        if (position==5){
            LeasingServicesAdapter.t=1;
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
        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONObject jsonObject1 = jsonObject.getJSONObject("CreateServiceRequest");

            String status = jsonObject1.getString("Status");
            if (status.equalsIgnoreCase("Success")){
                String srNumber = jsonObject1.getString("SR_Number");
                launchSuccessDialog(srNumber);
                //loadFragments(new ViewRequestLSFragment());

            }else {
                Toast.makeText(getActivity(),"Server Error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
            //

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
            if (callComplete == 0) {
                progressDialog.dismiss();
            }
            if (response != null) {

                Log.i("getCategories", "" + response);

                Gson gson = new Gson();
                createRequestsList = gson.fromJson(response, CreateRequest.class);

                int number = createRequestsList.getCategories().size();
                seeAllArray = new String[number];
                categoryId = new String[number];
                for (int j = 0; j < number; j++) {
                    seeAllArray[j] = createRequestsList.getCategories().get(j).getCategoryName();
                    categoryId[j] = createRequestsList.getCategories().get(j).getId().toString();
                }



                ///old

                if (createRequestsList.getCategories().size() < 7)

                {
                    iv_see_all.setVisibility(View.INVISIBLE);
                    categoryArrayList.addAll(createRequestsList.getCategories());

                    int numberOfColumns = 3;
                    rv_categories.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                    leasingServicesAdapter = new LeasingServicesAdapter(getActivity(), categoryArrayList,"");
                    leasingServicesAdapter.setClickListener(LeasingServicesFragment.this);
                    rv_categories.setAdapter(leasingServicesAdapter);



                } else {

                    idArray = new String[5];
                    for (int i = 0; i < 5; i++) {
                        idArray[i] = createRequestsList.getCategories().get(i).getCategoryName();
                        Log.i("final Array", idArray[i]);

                    }
                    int n = createRequestsList.getCategories().size();
                    seeAllArray = new String[n];
                    categoryId = new String[n];
                    for (int j = 0; j < n; j++) {
                        seeAllArray[j] = createRequestsList.getCategories().get(j).getCategoryName();
                        categoryId[j] = createRequestsList.getCategories().get(j).getId().toString();
                    }


                    for (j = 0; j <= idArray.length; j++) {

                        if (j == 0) {
                            String category = idArray[0];
                            tv_1.setText(category);
                            if (category.equals("Air Conditioning")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_ac);
                            }
                            if (category.equals("Carpentry")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_hammer);

                            }
                            if (category.equals("Doors")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (category.equals("Electrical")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_plug);

                            }
                            if (idArray[0].equalsIgnoreCase("Fire System")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_fire);

                            }
                            if (idArray[0].equalsIgnoreCase("General")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[0].equalsIgnoreCase("Lighting")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_bulb);

                            }
                            if (idArray[0].equalsIgnoreCase("Masonry")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_brick);

                            }
                            if (idArray[0].equalsIgnoreCase("Painting")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_paint);

                            }
                            if (idArray[0].equalsIgnoreCase("Plumbing")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_leakage);

                            }
                            if (idArray[0].equalsIgnoreCase("Windows")) {
                                iv_1.setBackgroundResource(R.drawable.unselected_window);

                            }
                        }
//////
                        if (j == 1) {
                            String category = idArray[1];
                            tv_2.setText(category);

                            if (idArray[1].equalsIgnoreCase("Air Conditioning")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_ac);
                            }
                            if (idArray[1].equalsIgnoreCase("Carpentry")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_hammer);

                            }
                            if (idArray[1].equalsIgnoreCase("Doors")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[1].equalsIgnoreCase("Electrical")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_plug);

                            }
                            if (idArray[1].equalsIgnoreCase("Fire System")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_fire);

                            }
                            if (idArray[1].equalsIgnoreCase("General")) ;
                            {
                                iv_2.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[1].equalsIgnoreCase("Lighting")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_bulb);

                            }
                            if (idArray[1].equalsIgnoreCase("Masonry")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_brick);

                            }
                            if (idArray[1].equalsIgnoreCase("Painting")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_paint);

                            }
                            if (idArray[1].equalsIgnoreCase("Plumbing")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_leakage);

                            }
                            if (idArray[1].equalsIgnoreCase("Windows")) {
                                iv_2.setBackgroundResource(R.drawable.unselected_window);
                            }
/////
                        }

                        if (j == 2) {
                            String category = idArray[2];
                            tv_3.setText(category);
                            if (idArray[2].equalsIgnoreCase("Air Conditioning")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_ac);
                            }
                            if (idArray[2].equalsIgnoreCase("Carpentry")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_hammer);

                            }
                            if (idArray[2].equalsIgnoreCase("Doors")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[2].equalsIgnoreCase("Electrical")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_plug);

                            }
                            if (idArray[2].equalsIgnoreCase("Fire System")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_fire);

                            }
                            if (idArray[2].equalsIgnoreCase("General")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[2].equalsIgnoreCase("Lighting")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_bulb);

                            }
                            if (idArray[2].equalsIgnoreCase("Masonry")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_brick);

                            }
                            if (idArray[2].equalsIgnoreCase("Painting")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_paint);

                            }
                            if (idArray[2].equalsIgnoreCase("Plumbing")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_leakage);

                            }
                            if (idArray[2].equalsIgnoreCase("Windows")) {
                                iv_3.setBackgroundResource(R.drawable.unselected_window);

                            }

                        }
                        ///////
                        if (j == 3) {
                            String category = idArray[3];
                            tv_4.setText(category);
                            if (idArray[3].equalsIgnoreCase("Air Conditioning")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_ac);
                            }
                            if (idArray[3].equalsIgnoreCase("Carpentry")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_hammer);

                            }
                            if (idArray[3].equalsIgnoreCase("Doors")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[3].equalsIgnoreCase("Electrical")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_plug);

                            }
                            if (idArray[3].equalsIgnoreCase("Fire System")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_fire);

                            }
                            if (idArray[3].equalsIgnoreCase("General")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[3].equalsIgnoreCase("Lighting")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_bulb);

                            }
                            if (idArray[3].equalsIgnoreCase("Masonry")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_brick);

                            }
                            if (idArray[3].equalsIgnoreCase("Painting")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_paint);

                            }
                            if (idArray[3].equalsIgnoreCase("Plumbing")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_leakage);

                            }
                            if (idArray[3].equalsIgnoreCase("Windows")) {
                                iv_4.setBackgroundResource(R.drawable.unselected_window);

                            }
                        }
                        //////
                        if (j == 4) {
                            String category = idArray[4];
                            tv_5.setText(category);
                            if (idArray[4].equalsIgnoreCase("Air Conditioning")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_ac);
                            }
                            if (idArray[4].equalsIgnoreCase("Carpentry")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_hammer);

                            }
                            if (idArray[4].equalsIgnoreCase("Doors")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[4].equalsIgnoreCase("Electrical")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_plug);

                            }
                            if (idArray[4].equalsIgnoreCase("Fire System")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_fire);

                            }
                            if (idArray[4].equalsIgnoreCase("General")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_window);

                            }
                            if (idArray[4].equalsIgnoreCase("Lighting")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_bulb);

                            }
                            if (idArray[4].equalsIgnoreCase("Masonry")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_brick);

                            }
                            if (idArray[4].equalsIgnoreCase("Painting")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_paint);

                            }
                            if (idArray[4].equalsIgnoreCase("Plumbing")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_leakage);

                            }
                            if (idArray[4].equalsIgnoreCase("Windows")) {
                                iv_5.setBackgroundResource(R.drawable.unselected_window);

                            }
                        }
                    }


                    if (callComplete == 1) {
                        lastClicked = 1;
                        setViewatPosition1();
                    } else if (callComplete == 2) {
                        lastClicked = 2;
                        setViewatPosition2();

                    } else if (callComplete == 3) {
                        lastClicked = 3;
                        setViewatPosition3();

                    } else if (callComplete == 4) {
                        lastClicked = 4;
                        setViewatPosition4();

                    } else if (callComplete == 5) {
                        lastClicked = 5;
                        setViewatPosition5();

                    }
                }
            }
        }
    }

    private void setViewatPosition1() {

        String category = tv_1.getText().toString();

        if (category.equals("Air Conditioning")) {
            iv_1.setBackgroundResource(R.drawable.selected_ac);

        } else if (category.equals("Carpentry")) {
            iv_1.setBackgroundResource(R.drawable.selected_hammer);

        } else if (category.equals("Doors")) {
            iv_1.setBackgroundResource(R.drawable.selected_window);

        } else if (category.equals("Electrical")) {
            iv_1.setBackgroundResource(R.drawable.selected_plug);

        } else if (category.equals("Fire System")) {
            iv_1.setBackgroundResource(R.drawable.selected_fire);

        } else if (category.equals("General")) {
            iv_1.setBackgroundResource(R.drawable.selected_window);

        } else if (category.equals("Lighting")) {
            iv_1.setBackgroundResource(R.drawable.selected_bulb);

        } else if (category.equals("Masonry")) {
            iv_1.setBackgroundResource(R.drawable.selected_brick);

        } else if (category.equals("Painting")) {
            iv_1.setBackgroundResource(R.drawable.selected_paint);

        } else if (category.equals("Plumbing")) {
            iv_1.setBackgroundResource(R.drawable.selected_leakage);

        } else if (category.equals("Windows")) {
            iv_1.setBackgroundResource(R.drawable.selected_window);


        }
        //getSubCategories("1");

    }


    public class GetSubCategories extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
            //

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
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (response != null) {
                if (response.equalsIgnoreCase("")){
                    ll_subCategories.setVisibility(View.GONE);

                }else {
                    subCategoriesArrayList.clear();
                    Log.i("getCategories", "" + response);

                    Gson gson = new Gson();
                    subCategories = gson.fromJson(response, SubCategories.class);
                    subCategoriesArrayList.addAll(subCategories.getSubCategories());
                    ll_subCategories.setVisibility(View.VISIBLE);

                    subCategoriesAdapter = new SubCategoriesAdapter(getActivity(), subCategoriesArrayList);
                    subCategoriesAdapter.setClickListener(LeasingServicesFragment.this);

                    rv_subCategories.setLayoutManager(new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.HORIZONTAL));
                    rv_subCategories.setAdapter(subCategoriesAdapter);
                }
            }
        }
    }

    public void getCategories() {
        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL = BASE_URL + "Yardi/GetCategories?serviceRequestType=" + "3";
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);
    }

    public void getSubCategories(int id) {
        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL = BASE_URL + "Yardi/GetSubCategories?categoryId=" + id;
        GetSubCategories okHttpHandler = new GetSubCategories();
        okHttpHandler.execute(REQUEST_URL);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // applyDim(root, 0.5f);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.dialog_list_view, null);
        rv_see_All = (RecyclerView) popupView.findViewById(R.id.rv_see_all);
        iv_cross = (ImageView) popupView.findViewById(R.id.iv_cross);

        rv_see_All.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_house = new LinearLayoutManager(getActivity());

        categoryID= new ArrayList<>();

        arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(seeAllArray));
        categoryID.addAll(Arrays.asList(categoryId));


       /* arrayList.remove(0);
        arrayList.remove(0);
        arrayList.remove(0);
        arrayList.remove(0);
        arrayList.remove(0);

        categoryID.remove(0);
        categoryID.remove(0);
        categoryID.remove(0);
        categoryID.remove(0);
        categoryID.remove(0);*/

        seeAllAdapter = new SeeAllAdapterLS(getActivity(), arrayList,categoryID);
        seeAllAdapter.setClickListener(this);
        rv_see_All.setAdapter(seeAllAdapter);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDim(root);
                popupWindow.dismiss();
            }
        });
    }


    /*@Override
    public void onItemClick(View view, int position,int pos) {
        String catName;

        if (lastClicked==1){
            catName=tv_1.getText().toString();
            if (catName.equals("Air Conditioning")){
                iv_1.setBackgroundResource(R.drawable.unselected_ac);
            }
            else if (catName.equals("Carpentry"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_hammer);

            }
            else if (catName.equals("Doors"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_lock);

            }else if (catName.equals("Electrical"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_plug);

            }else if (catName.equals("Fire System"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_fire);

            }else if (catName.equals("General"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_window);

            }else if (catName.equals("Lighting"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_bulb);

            }else if (catName.equals("Masonry"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_brick);

            }else if (catName.equals("Painting"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_paint);

            }else if (catName.equals("Plumbing"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_leakage);

            }else if (catName.equals("Windows"))
            {
                iv_1.setBackgroundResource(R.drawable.unselected_window);

            }


        }else if ( lastClicked==2 ){
            catName=tv_2.getText().toString();
            if (catName.equals("Air Conditioning")){
                iv_2.setBackgroundResource(R.drawable.unselected_ac);
            }
            else if (catName.equals("Carpentry"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_hammer);

            }
            else if (catName.equals("Doors"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_lock);

            }else if (catName.equals("Electrical"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_plug);

            }else if (catName.equals("Fire System"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_fire);

            }else if (catName.equals("General"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_window);

            }else if (catName.equals("Lighting"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_bulb);

            }else if (catName.equals("Masonry"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_brick);

            }else if (catName.equals("Painting"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_paint);

            }else if (catName.equals("Plumbing"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_leakage);

            }else if (catName.equals("Windows"))
            {
                iv_2.setBackgroundResource(R.drawable.unselected_window);

            }
        }
        else if (lastClicked==3){
            catName=tv_3.getText().toString();
            if (catName.equals("Air Conditioning")){
                iv_3.setBackgroundResource(R.drawable.unselected_ac);
            }
            else if (catName.equals("Carpentry"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_hammer);

            }
            else if (catName.equals("Doors"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_lock);

            }else if (catName.equals("Electrical"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_plug);

            }else if (catName.equals("Fire System"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_fire);

            }else if (catName.equals("General"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_window);

            }else if (catName.equals("Lighting"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_bulb);

            }else if (catName.equals("Masonry"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_brick);

            }else if (catName.equals("Painting"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_paint);

            }else if (catName.equals("Plumbing"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_leakage);

            }else if (catName.equals("Windows"))
            {
                iv_3.setBackgroundResource(R.drawable.unselected_window);

            }

        }
        else if (lastClicked==4){
            catName=tv_4.getText().toString();
            if (catName.equals("Air Conditioning")){
                iv_4.setBackgroundResource(R.drawable.unselected_ac);
            }
            else if (catName.equals("Carpentry"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_hammer);

            }
            else if (catName.equals("Doors"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_lock);

            }else if (catName.equals("Electrical"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_plug);

            }else if (catName.equals("Fire System"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_fire);

            }else if (catName.equals("General"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_window);

            }else if (catName.equals("Lighting"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_bulb);

            }else if (catName.equals("Masonry"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_brick);

            }else if (catName.equals("Painting"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_paint);

            }else if (catName.equals("Plumbing"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_leakage);

            }else if (catName.equals("Windows"))
            {
                iv_4.setBackgroundResource(R.drawable.unselected_window);

            }
        }
        else if (lastClicked==5){
            catName=tv_5.getText().toString();
            if (catName.equals("Air Conditioning")){
                iv_5.setBackgroundResource(R.drawable.unselected_ac);
            }
            else if (catName.equals("Carpentry"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_hammer);

            }
            else if (catName.equals("Doors"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_lock);

            }else if (catName.equals("Electrical"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_plug);

            }else if (catName.equals("Fire System"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_fire);

            }else if (catName.equals("General"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_window);

            }else if (catName.equals("Lighting"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_bulb);

            }else if (catName.equals("Masonry"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_brick);

            }else if (catName.equals("Painting"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_paint);

            }else if (catName.equals("Plumbing"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_leakage);

            }else if (catName.equals("Windows"))
            {
                iv_5.setBackgroundResource(R.drawable.unselected_window);

            }
        }


        Toast.makeText(getActivity(), "You clicked " + seeAllAdapter.getItem(position) + " on row number " + pos, Toast.LENGTH_SHORT).show();
        popupWindow.dismiss();
        if (seeAllAdapter.getItem(position).equals("General")) {
            iv_1.setBackgroundResource(R.drawable.selected_window);
            tv_1.setText(seeAllAdapter.getItem(position));
        } else if (seeAllAdapter.getItem(position).equals("Lighting")) {
            iv_1.setBackgroundResource(R.drawable.selected_bulb);
            tv_1.setText(seeAllAdapter.getItem(position));

        } else if (seeAllAdapter.getItem(position).equals("Masonry")) {
            iv_1.setBackgroundResource(R.drawable.selected_brick);
            tv_1.setText(seeAllAdapter.getItem(position));

        } else if (seeAllAdapter.getItem(position).equals("Painting")) {
            iv_1.setBackgroundResource(R.drawable.selected_paint);
            tv_1.setText(seeAllAdapter.getItem(position));


        } else if (seeAllAdapter.getItem(position).equals("Plumbing")) {
            iv_1.setBackgroundResource(R.drawable.selected_leakage);
            tv_1.setText(seeAllAdapter.getItem(position));

        } else if (seeAllAdapter.getItem(position).equals("Windows")) {
            iv_1.setBackgroundResource(R.drawable.selected_window);
            tv_1.setText(seeAllAdapter.getItem(position));

        }
        getSubCategories(String.valueOf(pos));
    }*/
    private void loadFragment(Fragment fragment,Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
    private void loadFragments(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

                       /* Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

*/
                       /* Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

*/
                       /* Intent pictureActionIntent = null;
                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                GALLERY_PICTURE);
*/
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        if (!checkPermission()) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                        }else {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment
                                    .getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(f));

                            startActivityForResult(intent,
                                    CAMERA_REQUEST);
                        }

                    }
                });
        myAlertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {




        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(getActivity(),

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                if (used == 0) {
                    // imageView has image in it
                    ll_iv_first_selected.setVisibility(View.VISIBLE);

                    iv_first_selected.setVisibility(View.VISIBLE);
                    iv_first_selected.setImageBitmap(bitmap);
                }
                if (used == 1) {
                    // imageView has image in it
                    ll_iv_second_selected.setVisibility(View.VISIBLE);

                    iv_second_selected.setVisibility(View.VISIBLE);
                    iv_second_selected.setImageBitmap(bitmap);
                }
                if (used == 2) {
                    ll_iv_third_selected.setVisibility(View.VISIBLE);

                    iv_third_selected.setVisibility(View.VISIBLE);
                    iv_third_selected.setImageBitmap(bitmap);
                }


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                uploadImage(encoded);
                storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data

            try {
                // When an Image is picked

                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();



                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
                if (mArrayUri.size()<=3) {

                    for (int position = 0; position < mArrayUri.size(); position++) {

                        if (position == 0) {

                            ll_iv_first_selected.setVisibility(View.VISIBLE);
                            iv_first_selected.setVisibility(View.VISIBLE);
                            iv_first_selected.setImageURI(mArrayUri.get(0));

                            final Uri imageUri = data.getData();
                            final InputStream imageStream;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(0));

                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                encodedImage = encodeImage(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            uploadImage(encodedImage);
                        }
                        if (position == 1) {

                            ll_iv_second_selected.setVisibility(View.VISIBLE);

                            iv_second_selected.setVisibility(View.VISIBLE);
                            iv_second_selected.setImageURI(mArrayUri.get(1));
                            final Uri imageUri = data.getData();
                            final InputStream imageStream;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(1));

                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                encodedImage1 = encodeImage(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            uploadImage(encodedImage1);
                        }
                        if (position == 2) {

                            ll_iv_third_selected.setVisibility(View.VISIBLE);

                            iv_third_selected.setVisibility(View.VISIBLE);
                            iv_third_selected.setImageURI(mArrayUri.get(2));

                            final Uri imageUri = data.getData();
                            final InputStream imageStream;
                            try {
                                imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(2));

                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                encodedImage2 = encodeImage(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            uploadImage(encodedImage2);
                            //   uploadImage(encoded);
                        }


                    }
                }else {
                    Toast.makeText(getActivity(), "You can only upload maximum 3 pictures!", Toast.LENGTH_LONG)
                            .show();
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something went wrong. Please try again", Toast.LENGTH_LONG)
                        .show();
            }

            super.onActivityResult(requestCode, resultCode, data);

        }
    }
    private void storeImageTosdCard(Bitmap processedBitmap) {
        try {
            // TODO Auto-generated method stub

            OutputStream output;
            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();
            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath() + "/appName/");
            dir.mkdirs();

            String imge_name = "appName" + System.currentTimeMillis()
                    + ".jpg";
            // Create a name for the saved image
            File file = new File(dir, imge_name);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();

            }

            try {

                output = new FileOutputStream(file);

                // Compress into png format image from 0% - 100%
                processedBitmap
                        .compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                int file_size = Integer
                        .parseInt(String.valueOf(file.length() / 1024));
                System.out.println("size ===>>> " + file_size);
                System.out.println("file.length() ===>>> " + file.length());

                selectedImagePath = file.getAbsolutePath();



            }

            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //Http Request is handled below
    class HttpAsyncTask extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(220, TimeUnit.SECONDS)
                .writeTimeout(220, TimeUnit.SECONDS)
                .readTimeout(220, TimeUnit.SECONDS)
                .build();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading image ..");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
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
            if (response != null) {
                progressDialog.dismiss();
                used++;
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("UploadResponse", "" + responseObject);
                    String status= responseObject.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {
                        //   Toast.makeText(getActivity(),"Picture Uploaded Successfully",Toast.LENGTH_LONG).show();

                        String transactionId = responseObject.getString("TransactionID");
                        transactionId_first = transactionId;
                        if (!transactionId_first.isEmpty()){
                            transactionId_second = transactionId;
                        }
                        if (!transactionId_second.isEmpty()){
                            transactionId_third = transactionId;
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
    public void uploadImage(String  encoded){
        JSONObject jsonObject = new JSONObject();

        try {

            if (used == 0) {

                jsonObject.put("TransactionID", "" + "");

            }
            if (used == 1){
                jsonObject.put("TransactionID", "" + transactionId_first);

            }
            if (used == 2){
                jsonObject.put("TransactionID", "" + transactionId_second);

            }



            jsonObject.put("Attachment", "" + encoded);
            jsonObject.put("FileName", "abcdxyz.jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL+"Attachment");
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",IConstant.KEY);
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
        //

        Request request = builder.build();
        new HttpAsyncTask().execute(request);
    }


    class DeleteAttachment extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Deleting...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                Log.i("UploadProfilePicture_do", "" + response);
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
                    Log.i("DeletePhoto", "" + responseObject);
                    if (responseObject.has("status")) {

                        String status = responseObject.getString("status");

                        if (status.equals("Success")) {


                            Toast.makeText(getActivity(),"Profile Picture Deleted",Toast.LENGTH_LONG).show();

                        } else if (status.equalsIgnoreCase("No active profile")) {
                            //errorMessage.setVisibility(View.VISIBLE);
                            // errorMessage.setText(status);

                        }

                    }else {
                        if (responseObject.has("Status")) {

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")) {


                                Toast.makeText(getActivity(),"Attachment Deleted",Toast.LENGTH_LONG).show();

                            } else if (status.equalsIgnoreCase("No active profile")) {
                                //errorMessage.setVisibility(View.VISIBLE);
                                // errorMessage.setText(status);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED,"true");
                    } //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED,"false");

// Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                //  showMessageOKCancel("You need to allow access to both the permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                };
                                return;
                            }
                        }

                    }
                }

                break;
        }
    }
    private void launchSuccessDialog(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        srid.setText("SR ID #"+srID);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new ViewRequestLSFragment());
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        clearDim(root);
    }

    private void showPopUp_1(){
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(bitmap_1);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }


    private void showPopUp_2(){
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(bitmap_2);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }


    private void showPopUp_3(){
        applyDim(root, 0.5f);
        final Dialog nagDialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(bitmap_3);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);
                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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
    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
    }
}
