package com.residents.dubaiassetmanagement.home_screen.model_service_home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.CreateNewFragment;
import com.residents.dubaiassetmanagement.home_screen.model_service_home.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.reopen_service.ReopenScheduleFragment;
import com.residents.dubaiassetmanagement.reopen_service.ReopenServiceFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class HomeReopenServiceFragment extends Fragment implements ResponseCallback,View.OnClickListener {

    private View view;
    private TextView fragmentTitle, tv_category, tv_id, tv_subCategory, tv_request_status, tv_date, tv_preferred_dateandtime, tv_description, tv_follow_up, tv_cancel, tv_reschedule;
    Bundle bundle;
    ArrayList<ServiceRequest> viewRequests;
    private int position;
    private ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_see_all, iv_open_gallery, iv_first_selected, iv_second_selected, iv_third_selected, iv_first_delete, iv_second_delete, iv_third_delete;
    String selectedImagePath, transactionId_first, transactionId_second, transactionId_third, image_ID1, imageID2, imageID3;

    private String date, dates,BASE_URL;
    private EditText et_description;
    private TextView tv_raise_new,tv_schedule;
    SavePreference mSavePreference;
    private Button bt_submit;
    Bitmap bitmap;
    private String REGISTER_URL, templateCode, CategoryName;

    private LinearLayout ll_iv_first_selected, ll_iv_second_selected, ll_iv_third_selected,ll_attach;
    private static final int PERMISSION_REQUEST_CODE = 200;
    protected static final int CAMERA_REQUEST = 0;
    Date dateObj;
    String ts;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private static int used = 0;
    private ImageView iv_cross;
    ViewGroup root;
    private Bitmap bitmap_1, bitmap_2, bitmap_3;
    private int a, b, c = 0;
    private Bitmap bitmap1, bitmap2, bitmap3;
    String encodedImage,encodedImage2,encodedImage1;
    private ArrayList<Uri> mArrayUri;
    private ApplicationPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reopen, container, false);
        bundle = new Bundle();
        viewRequests = new ArrayList<>();
        bundle = getArguments();
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
        preferences = ApplicationPreferences.getInstance(getActivity());
        mSavePreference = SavePreference.getInstance(getActivity());
        viewRequests = bundle.getParcelableArrayList("viewRequestList");
        position = bundle.getInt("position");
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("REOPEN REQUEST");
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_subCategory = (TextView) view.findViewById(R.id.tv_subCategory);
        tv_request_status = (TextView) view.findViewById(R.id.tv_request_status);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        et_description = (EditText) view.findViewById(R.id.et_description);
        tv_preferred_dateandtime = (TextView) view.findViewById(R.id.tv_preferred_dateandtime);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_follow_up = (TextView) view.findViewById(R.id.tv_follow_up);
        tv_schedule  = (TextView) view.findViewById(R.id.tv_schedule);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_reschedule = (TextView) view.findViewById(R.id.tv_reschedule);
        tv_raise_new = (TextView) view.findViewById(R.id.tv_raise_new);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        mArrayUri = new ArrayList<>();
        iv_first_selected = (ImageView) view.findViewById(R.id.iv_first_selected);
        iv_second_selected = (ImageView) view.findViewById(R.id.iv_second_selected);
        iv_third_selected = (ImageView) view.findViewById(R.id.iv_third_selected);


        iv_first_delete = (ImageView) view.findViewById(R.id.iv_first_delete);
        iv_second_delete = (ImageView) view.findViewById(R.id.iv_second_delete);
        iv_third_delete = (ImageView) view.findViewById(R.id.iv_third_delete);
        ll_iv_first_selected = (LinearLayout) view.findViewById(R.id.ll_iv_first_selected);
        ll_iv_second_selected = (LinearLayout) view.findViewById(R.id.ll_iv_second_selected);
        ll_iv_third_selected = (LinearLayout) view.findViewById(R.id.ll_iv_third_selected);

        ll_attach = (LinearLayout) view.findViewById(R.id.ll_attach);
        iv_open_gallery = (ImageView) view.findViewById(R.id.iv_open_gallery);


        iv_third_selected.setOnClickListener(this);
        iv_second_selected.setOnClickListener(this);
        iv_first_selected.setOnClickListener(this);

        iv_first_delete.setOnClickListener(this);
        iv_second_delete.setOnClickListener(this);
        iv_third_delete.setOnClickListener(this);
        ll_attach.setOnClickListener(this);
        iv_open_gallery.setOnClickListener(this);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MyObject1", "");

        prefsEditor.putString("CC_List", "");
        prefsEditor.putString("LS_List", "");

        prefsEditor.commit();
        tv_category.setText(viewRequests.get(position).getCategory());
        tv_subCategory.setText(viewRequests.get(position).getSubCategory());

        try {
            date = getMonth(viewRequests.get(position).getCallDate().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_date.setText(date.replaceAll("-", " "));

        if (viewRequests.get(position).getCompletionDate() != null) {
            try {

                dates = getMonth(viewRequests.get(position).getCompletionDate().toString().substring(0, 10));
                tv_preferred_dateandtime.setText(dates.replaceAll("-", " "));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tv_preferred_dateandtime.setText("Awaiting");


        }
if (viewRequests.get(position).getStatus().equalsIgnoreCase("Closed")){
    tv_schedule.setText("Closed On");
    tv_request_status.setTextColor(getResources().getColor(R.color.light_text_color));

}

        if (viewRequests.get(position).getStatus().equalsIgnoreCase("Canceled")){
            tv_schedule.setText("Canceled On");
            tv_request_status.setTextColor(getResources().getColor(R.color.red));
        }
        tv_id.setText("SR ID #"+viewRequests.get(position).getWOCode());
        tv_request_status.setText(viewRequests.get(position).getStatus());
        tv_description.setText(viewRequests.get(position).getProblemDescription());
        tv_description.setText(bundle.getString("des"));
        tv_raise_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new CreateNewFragment());
            }
        });


        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if (et_description.getText().toString().length() > 0) {
                    bt_submit.setEnabled(true);
                    bt_submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    bt_submit.setEnabled(false);
                    bt_submit.setBackgroundColor(getResources().getColor(R.color.disabled_button));

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

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewRequests.get(position).getIsEmergency() == null || viewRequests.get(position).getIsEmergency() == false ) {
                    Bundle bundle = new Bundle();


                    bundle.putString("Object_Type", "SR");
                    bundle.putString("Object_Reference", mSavePreference.getString(IpreferenceKey.TCODE));

                    if (transactionId_first == null) {
                        bundle.putString("Transaction_Id", "");

                    } else {
                        bundle.putString("Transaction_Id", transactionId_first);
                    }
                    bundle.putString("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
                    bundle.putString("Template_Code", viewRequests.get(position).getTemplateCode());
                    bundle.putString("Category", viewRequests.get(position).getCategory());
                    bundle.putString("SubCategory",  viewRequests.get(position).getSubCategory());
                    bundle.putString("Problem_Description", et_description.getText().toString());
                    bundle.putString("Caller_Name", mSavePreference.getString(IpreferenceKey.USERNAME));
                    if (viewRequests.get(position).getCallerPhone() !=null){
                        bundle.putString("Caller_Number", viewRequests.get(position).getCallerPhone().toString());

                    }else {
                        bundle.putString("Caller_Number", "");

                    }
                    bundle.putString("Related_WOID", viewRequests.get(position).getWOCode());

                    loadFragment(new ReopenScheduleFragment(), bundle);



                } else {
                    JSONObject jsonObject = new JSONObject();

                    try {

                        jsonObject.put("Object_Type", "SR");
                        jsonObject.put("Object_Reference", mSavePreference.getString(IpreferenceKey.TCODE));
                        jsonObject.put("Transaction_Id", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObjectPArent = new JSONObject();
                    try {
                        jsonObjectPArent.put("Tenant_Code", mSavePreference.getString(IpreferenceKey.TCODE));
                        jsonObjectPArent.put("Template_Code", viewRequests.get(position).getTemplateCode());
                        jsonObjectPArent.put("Category", viewRequests.get(position).getCategory());

                        jsonObjectPArent.put("SubCategory", viewRequests.get(position).getSubCategory());


                        jsonObjectPArent.put("Problem_Description", et_description.getText().toString());
                        jsonObjectPArent.put("Caller_Name", mSavePreference.getString(IpreferenceKey.USERNAME));
                        jsonObjectPArent.put("Caller_Number", viewRequests.get(position).getCallerPhone());
                        jsonObjectPArent.put("Common_Area", "");
                        jsonObjectPArent.put("Preferred_Date", viewRequests.get(position).getPreferredDate());
                        jsonObjectPArent.put("Preferred_TimeSlot", viewRequests.get(position).getPreferredTimeSlot());
                        jsonObjectPArent.put("Related_WOID", viewRequests.get(position).getWOCode());
                        jsonObjectPArent.put("CreateSRAttachment", jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    new RequestService(getActivity(), HomeReopenServiceFragment.this).postRequest("Yardi/CreateServiceRequest", jsonObjectPArent);

                }
            }
        });

        return view;
    }


    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

    private void loadFragments(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

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
            String srNumber = jsonObject1.getString("SR_Number");

            String status = jsonObject1.getString("Status");
            if (status.equalsIgnoreCase("Success")) {

                launchSuccessDialog(srNumber);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_first_selected:

                showPopUp_1();
                break;

            case R.id.iv_second_selected:

                showPopUp_2();

                break;

            case R.id.iv_third_selected:

                showPopUp_3();

                break;
            case R.id.iv_first_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("")
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                ll_iv_first_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL = BASE_URL + "Attachment?imageId=" + image_ID1;
                                JSONObject jsonObject1 = new JSONObject();

                                a=1;
                                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body = RequestBody.create(JSON, jsonObject1.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url(REQUEST_URL);
                                builder = builder.delete(body);
                                builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                builder =  builder.addHeader("SessionId", preferences.getSessionId());
                                builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

                                Request request = builder.build();
                                new DeleteAttachment().execute(request);
                            }
                        }).show();

                break;
            case R.id.iv_second_delete:
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                dialog1.setTitle("")
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                ll_iv_second_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL1 = BASE_URL + "Attachment?imageId=" + imageID2;
                                JSONObject jsonObject11 = new JSONObject();

                                b=1;
                                MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body1 = RequestBody.create(JSON1, jsonObject11.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder1 = new Request.Builder();
                                builder1 = builder1.url(REQUEST_URL1);
                                builder1 = builder1.delete(body1);
                                builder1 = builder1.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                builder1 =  builder1.addHeader("SessionId", preferences.getSessionId());
                                builder1 =   builder1.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

                                Request request1 = builder1.build();
                                new DeleteAttachment().execute(request1);
                            }
                        }).show();

                break;
            case R.id.iv_third_delete:
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(getActivity());
                dialog11.setTitle("")
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {


                                ll_iv_third_selected.setVisibility(View.GONE);
                                BASE_URL = IConstant.BASE_URL;

                                String REQUEST_URL11 = BASE_URL + "Attachment?imageId=" + imageID3;
                                JSONObject jsonObject111 = new JSONObject();
                                c=1;

                                MediaType JSON11 = MediaType.parse("application/json; charset=utf-8");

                                RequestBody body11 = RequestBody.create(JSON11, jsonObject111.toString());
                                // Request request = new Request().Builder().url().post(body).build();
                                Request.Builder builder11 = new Request.Builder();
                                builder11 = builder11.url(REQUEST_URL11);
                                builder11 = builder11.delete(body11);
                                builder11 = builder11.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
                                builder11 =  builder11.addHeader("SessionId", preferences.getSessionId());
                                builder11 =   builder11.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

                                Request request11 = builder11.build();
                                new DeleteAttachment().execute(request11);

                            }
                        }).show();

                break;

            case R.id.iv_open_gallery:
               /* if (used == 3){
                    Toast.makeText(getActivity(),"You can upload maximum 3 images",Toast.LENGTH_LONG).show();
                }else {*/
                if (mSavePreference.getString(IpreferenceKey.IS_PERMISSIONS_GRANTED).equalsIgnoreCase("false")) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                } else {


                    startDialog();
                }
                break;


            case R.id.ll_attach:
               /* if (used == 3){
                    Toast.makeText(getActivity(),"You can upload maximum 3 images",Toast.LENGTH_LONG).show();
                }else {*/
                if (mSavePreference.getString(IpreferenceKey.IS_PERMISSIONS_GRANTED).equalsIgnoreCase("false")) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                } else {


                    startDialog();
                }
                break;
        }

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




/*
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);*/



                      /*  Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);*/


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
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "true");
                    } //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "false");

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
             /*   bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
*/

            /*    if (used == 0) {
                    // imageView has image in it
                    bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);

                    ll_iv_first_selected.setVisibility(View.VISIBLE);

                    iv_first_selected.setVisibility(View.VISIBLE);
                    iv_first_selected.setImageBitmap(bitmap1);
                }
                if (used == 1) {
                    // imageView has image in it
                    bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);

                    ll_iv_second_selected.setVisibility(View.VISIBLE);

                    iv_second_selected.setVisibility(View.VISIBLE);
                    iv_second_selected.setImageBitmap(bitmap2);
                }
                if (used == 2) {

                    bitmap3 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);

                    ll_iv_third_selected.setVisibility(View.VISIBLE);

                    iv_third_selected.setVisibility(View.VISIBLE);
                    iv_third_selected.setImageBitmap(bitmap3);
                }
*/

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);

                mArrayUri.add(Uri.parse(path));

                // uploadImage(encoded);
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


                    mArrayUri.add(mImageUri);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
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


            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something went wrong. Please try again", Toast.LENGTH_LONG)
                        .show();
            }

            super.onActivityResult(requestCode, resultCode, data);

        }
        if (mArrayUri.size() <= 3) {

            for (int position = 0; position < mArrayUri.size(); position++) {

                if (position == 0) {

                    ll_iv_first_selected.setVisibility(View.VISIBLE);
                    iv_first_selected.setVisibility(View.VISIBLE);
                    iv_first_selected.setImageURI(mArrayUri.get(0));

                    //  final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(0));

                        bitmap1 = BitmapFactory.decodeStream(imageStream);
                        encodedImage = encodeImage(bitmap1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    uploadImage(encodedImage);
                }
                if (position == 1) {

                    ll_iv_second_selected.setVisibility(View.VISIBLE);

                    iv_second_selected.setVisibility(View.VISIBLE);
                    iv_second_selected.setImageURI(mArrayUri.get(1));
                    //    final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(1));

                        bitmap2 = BitmapFactory.decodeStream(imageStream);
                        encodedImage1 = encodeImage(bitmap2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //  uploadImage(encodedImage1);
                }
                if (position == 2) {

                    ll_iv_third_selected.setVisibility(View.VISIBLE);

                    iv_third_selected.setVisibility(View.VISIBLE);
                    iv_third_selected.setImageURI(mArrayUri.get(2));

                    //      final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(mArrayUri.get(2));

                        bitmap3 = BitmapFactory.decodeStream(imageStream);
                        encodedImage2 = encodeImage(bitmap3);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //  uploadImage(encodedImage2);
                    //   uploadImage(encoded);
                }
            }
        } else {
            Toast.makeText(getActivity(), "You can only upload maximum 3 pictures!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void uploadImage(String encoded) {
        JSONObject jsonObject = new JSONObject();

        try {
            if (used == 0) {
                jsonObject.put("TransactionID", "" + "");
            }
            if (used == 1) {
                jsonObject.put("TransactionID", "" + transactionId_first);
            }
            if (used == 2) {
                jsonObject.put("TransactionID", "" + transactionId_second);
            }
            jsonObject.put("Attachment", "" + encoded);
           /* Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();*/

            try {  String timeStr = "2016-11-01T09:45:00.000+02:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                dateObj= null;

                dateObj = sdf.parse(timeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ts = String.valueOf(dateObj.getTime()*1000);
            System.out.println(dateObj.getTime());

            long ts = System.nanoTime();
            jsonObject.put("FileName", "UploadPicture_"+ts+".jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL + "Attachment");
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new HttpAsyncTask().execute(request);
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
                        .compress(Bitmap.CompressFormat.PNG, 10, output);
                output.flush();
                output.close();

                int file_size = Integer
                        .parseInt(String.valueOf(file.length() / 1024));
                System.out.println("size ===>>> " + file_size);
                System.out.println("file.length() ===>>> " + file.length());

                selectedImagePath = file.getAbsolutePath();


            } catch (Exception e) {
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
            progressDialog.setMessage("Uploading image...");
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
                if (mArrayUri.size() == 1) {

                } else {
                    used++;
                }
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("UploadResponse", "" + responseObject);
                    String status = responseObject.getString("Status");
                    if (status.equalsIgnoreCase("Success")) {

                        String transactionId = responseObject.getString("TransactionID");
                        String imageId = responseObject.getString("ImageId");
                        image_ID1 = imageId;
                        transactionId_first = transactionId;


                        if (!transactionId_first.isEmpty()) {
                            imageID2 = imageId;

                            transactionId_second = transactionId;
                        }
                        if (!transactionId_second.isEmpty()) {
                            imageID3 = imageId;
                            transactionId_third = transactionId;
                        }

                        if (mArrayUri.size() > 1) {
                            if (used == 1) {
                                uploadImage(encodedImage1);
                            }
                        }
                        if (mArrayUri.size() == 3) {
                            if (used == 2) {
                                uploadImage(encodedImage2);
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // Toast.makeText(getActivity(),"Picture Uploaded Successfully",Toast.LENGTH_LONG).show();

        }

    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount) {
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
    private void launchSuccessDialog(String srID) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_msr);
        dialog.setCanceledOnTouchOutside(true);

        TextView srid = (TextView) dialog.findViewById(R.id.srid);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        srid.setText("SR ID #" + srID);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new ViewRequestFragment());
                dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    private void loadFragment(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,10,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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
                    used--;
                    if (a == 1) {
                        mArrayUri.remove(0);
                    }
                    if (b == 1) {
                        mArrayUri.remove(1);
                    }
                    if (c == 1) {
                        mArrayUri.remove(2);
                    }
                    a = 0;
                    b = 0;
                    c = 0;
                    Log.i("Here", "" + response);
                    assert response.body() != null;
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("DeletePhoto", "" + responseObject);
                    if (responseObject.has("status")) {

                        String status = responseObject.getString("status");

                        if (status.equals("Success")) {


                            Toast.makeText(getActivity(), "Profile Picture Deleted", Toast.LENGTH_LONG).show();

                        } else if (status.equalsIgnoreCase("No active profile")) {
                            //errorMessage.setVisibility(View.VISIBLE);
                            // errorMessage.setText(status);

                        }

                    } else {
                        if (responseObject.has("Status")) {

                            String status = responseObject.getString("Status");

                            if (status.equals("Success")) {


                                Toast.makeText(getActivity(), "Attachment Deleted", Toast.LENGTH_LONG).show();

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

    private void showPopUp_1() {
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        if (bitmap1 == null) {
            ivPreview.setImageBitmap(bitmap);
        } else {
            ivPreview.setImageBitmap(bitmap1);

        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);
                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }


    private void showPopUp_2() {
        applyDim(root, 0.5f);

        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        if (bitmap2 == null) {
            ivPreview.setImageBitmap(bitmap);
        } else {
            ivPreview.setImageBitmap(bitmap2);
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }


    private void showPopUp_3() {
        applyDim(root, 0.5f);
        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        if (bitmap3 == null) {
            ivPreview.setImageBitmap(bitmap);
        } else {
            ivPreview.setImageBitmap(bitmap3);
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clearDim(root);
                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }
    private static String getDescription(String str) {
        return str.substring(0, str.length() - 1);
    }

}
