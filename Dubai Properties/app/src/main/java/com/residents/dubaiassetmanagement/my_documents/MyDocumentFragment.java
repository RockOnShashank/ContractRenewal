package com.residents.dubaiassetmanagement.my_documents;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.IConstant;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.Profile.ToBytes;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.DialogPdfViewer;
import com.residents.dubaiassetmanagement.Utils.FileUtils;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.my_documents.models.DPGAttachmentDetailsV;
import com.residents.dubaiassetmanagement.my_documents.models.MyDocuments;
import com.residents.dubaiassetmanagement.my_documents.models_status.DocumentsStatus;
import com.residents.dubaiassetmanagement.my_documents.models_status.ServiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import in.gauriinfotech.commons.Commons;
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

public class MyDocumentFragment extends Fragment implements ResponseCallback, MyDocumentsAdapter.ItemClickListener,MyDocumentsAdapter.AdapterInterface, DialogPdfViewer.OnDialogPdfViewerListener {


    private MyDocumentsAdapter myDocumentsAdapter;
    private SavePreference mSavePreference;
    private View view;
    private MyDocuments myDocuments;
    private ArrayList<DPGAttachmentDetailsV> list;
    private RecyclerView rv_documents;
    private String base64;
    private RequestQueue requestQueue = null;
    private String r;
    HashMap<String, String> param, param1;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private TextView fragmentTitle, tv_no_documents;
    static final int RESULT_GALLERY = 500;
    private int isPDF = 0;
    static final int RESULT_PDF = 600;
    String imageEncoded;


    private PopupWindow popupWindow;
    ViewGroup root;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private TenantDetails tenantDetails;
    private String BASE_URL, REQUEST_URL;
    Bitmap bitmap_new;
    DocumentsStatus documentsStatus;
    String mRequestBody;
    String attachmentTypeName, REGISTER_URL;
    ArrayList<ServiceRequest> documentsStatusList;
    ApplicationPreferences applicationPreferences;
    RelativeLayout iv_bell_icon;
BottomNavigationView bottomNavigation;
    long imageLength;
    String encodedImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_documents, container, false);
       // requestPermissionAndContinue();
        applicationPreferences = ApplicationPreferences.getInstance(getActivity());
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("MY DOCUMENTS");


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
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        mSavePreference = SavePreference.getInstance(getActivity());
        new RequestService(getActivity(), MyDocumentFragment.this).setArguments("MyDocuments/Attachment/" + mSavePreference.getString(IpreferenceKey.TCODE));
        list = new ArrayList<>();
        documentsStatusList = new ArrayList<>();
        rv_documents = (RecyclerView) view.findViewById(R.id.rv_documents);
        tv_no_documents = (TextView) view.findViewById(R.id.tv_no_documents);
        rv_documents.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_house = new LinearLayoutManager(getActivity());
        param = new HashMap<>();
        param1 = new HashMap<>();
        param1.put("Ocp-Apim-Subscription-Key", IConstant.KEY);
        param1.put("SessionId", applicationPreferences.getSessionId());
        param1.put("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
//
        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL = BASE_URL + "Yardi/GetDetails?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE);
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);
        //
        return view;
    }

   /* private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }*/

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Necessary");
                alertBuilder.setMessage("Necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                }

            } else {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }

    @Override
    public void onSuccess(String response) {
        if (response.equalsIgnoreCase("")) {
         //   tv_no_documents.setVisibility(View.VISIBLE);
         //   rv_documents.setVisibility(View.GONE);

            myDocumentsAdapter = new MyDocumentsAdapter(getActivity(), list, documentsStatusList, MyDocumentFragment.this);
            myDocumentsAdapter.setClickListener(this);
            rv_documents.setAdapter(myDocumentsAdapter);
        } else {

            Gson gson = new Gson();
            myDocuments = gson.fromJson(response, MyDocuments.class);
            if (myDocuments.getAttachmentDetails().getDPGAttachmentDetailsV()!=null) {
                list.addAll(myDocuments.getAttachmentDetails().getDPGAttachmentDetailsV());
            }

        }
    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {

    }

    @Override
    public void onSuccessSecond(String response) {
        String responses = response;
        Gson gson = new Gson();
        documentsStatus = gson.fromJson(response, DocumentsStatus.class);
        if (documentsStatus.getServiceRequest() != null) {
            documentsStatusList.addAll(documentsStatus.getServiceRequest());
            myDocumentsAdapter = new MyDocumentsAdapter(getActivity(), list, documentsStatusList, MyDocumentFragment.this);
            myDocumentsAdapter.setClickListener(this);
            rv_documents.setAdapter(myDocumentsAdapter);
        }


         /*   Gson gson = new Gson();
            myDocuments = gson.fromJson(response, MyDocuments.class);
            list.addAll(myDocuments.getAttachmentDetails().getDPGAttachmentDetailsV());
            myDocumentsAdapter = new MyDocumentsAdapter(getActivity(), list);
            myDocumentsAdapter.setClickListener(this);
            rv_documents.setAdapter(myDocumentsAdapter);*/

    }

    @Override
    public void onPostSuccess(String response, String sessionId) {


        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

        JSONObject jsonObject = new JSONObject();
        try {
            base64 = jsonObject.getString("FileBase64String");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "newFile" + ".pdf");
        byte[] pdfAsBytes = Base64.decode(base64, 0);

        FileOutputStream os;
        try {
            os = new FileOutputStream(dwldsPath, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position, int status) {

        if (status == 0) {
            if (position == 2) {
                attachmentTypeName = "Passport Copy of Tenant";
            }

            if (position == 3) {
                attachmentTypeName = "Emirates ID Copy";
            }
            if (position == 4) {
                attachmentTypeName = "Resident Visa Copy";
            }
            if (position == 5) {
                attachmentTypeName = "DEWA Registration Receipt";
            }
            if (position == 6) {
                attachmentTypeName = "Empower Registration Receipt";
            }
            if (position == 7) {
                attachmentTypeName = "Final DEWA Bill";
            }
            if (position == 8) {
                attachmentTypeName = "Final Empower Bill";
            }

            onButtonShowPopupWindowClickDefault(view);

        } else {


        }


    }

    private String callpostApi(final HashMap<String, String> param, final HashMap<String, String> param1) {
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        // String url = IConstant.MARKASREAD + accid + "&reportId=" + reportid;
        requestQueue = Volley.newRequestQueue(getActivity());

        //    StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, IConstant.BASE_URL+"MyDocuments/Attachment"+"t0008498",


        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, IConstant.BASE_URL + "MyDocuments/Attachment" + mSavePreference.getString(IpreferenceKey.TCODE),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        r = response;
                        JSONObject jsonObject = null;
                        final int READ_BLOCK_SIZE = 100;

                        try {
                            jsonObject = new JSONObject(r);
                            String base64 = jsonObject.getString("FileBase64String");
                            byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);

                            //     new DialogPdfViewer(getActivity(),base64,MyDocumentFragment.this).show();


                            String text = new String(pdfAsBytes, StandardCharsets.UTF_8);
                            createandDisplayPdf(text);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mProgressDialog.dismiss();

                        if (r.equals("true")) {

                            Log.d("ani", "insidetrue");
                        } else {
                            Log.d("ani", "insidefalse");

                        }
                        Log.d("Response", response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof NetworkError) {
                            r = "NetworkError";
                            Toast.makeText(getActivity(), "Network error,Something Went Wrong please try again later", Toast.LENGTH_LONG).show();


                        } else if (error instanceof TimeoutError) {
                            r = "TimeoutError";
                            Toast.makeText(getActivity(), "Timeout Error,Something Went Wrong please try again later", Toast.LENGTH_LONG).show();


                        } else if (error instanceof ServerError) {
                            r = "ServerError";
                            Toast.makeText(getActivity(), "Server Error,Something Went Wrong please try again later", Toast.LENGTH_LONG).show();

                        }
                        mProgressDialog.dismiss();

                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return param1;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return param;
            }
        };
        requestQueue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


//        if (saveLocation.get_true() != null) {
//            String t = saveLocation.get_true();
//            Log.d("ani", t + "true");
//        }

        return r;
    }

    @Override
    public void onAgreeClick(DialogPdfViewer dialogFullEula) {

    }

    @Override
    public void onCloseClick(DialogPdfViewer dialogFullEula) {

    }

    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf(String text) {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            //    Font paraFont= new Font(Font.NORMAL);
            //    p1.setAlignment(Paragraph.ALIGN_CENTER);
            // p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        viewPdf("newFile.pdf", "Dir");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    /*
        if you use MediaStore.EXTRA_OUTPUT, the data will be null.
     */
        popupWindow.dismiss();
        clearDim(root);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

          /*      ContentResolver contentResolver = getActivity().getContentResolver();
                try {
                    //this is full size image
                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoURI);

                    if (fullBitmap.getWidth() > fullBitmap.getHeight()) {
                        fullBitmap = rotate90(fullBitmap);
                    }

                    profile_image.setImageBitmap(fullBitmap);
                    profile_image.setVisibility(View.VISIBLE);

                    // Bitmap bmp = intent.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    fullBitmap.recycle();
*//*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                fullBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();*//*

                //    uploadPic(byteArray);

                    saveImage(fullBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            ////
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //profile_image.setImageBitmap(imageBitmap);

            String encImage = Base64.encodeToString(getBytesFromBitmap(imageBitmap),
                    Base64.NO_WRAP);

            uploadImagenew(encImage);


        } else if (requestCode == RESULT_GALLERY && resultCode == RESULT_OK && isPDF==0) {

         /*   String[] filePathColumn = {MediaStore.Images.Media.DATA};
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


                final InputStream imageStream;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(mImageUri);

                    Bitmap bitmap1 = BitmapFactory.decodeStream(imageStream);
                     encodedImage = encodeImage(bitmap1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            if (imageLength<10000) {
                uploadImagenew(encodedImage);
            }else {
                Toast.makeText(getActivity(),"Document should be less than 5MB.",Toast.LENGTH_LONG).show();
            }

*/







            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            FileInputStream fis = null;

            try {
                bitmap_new = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                fis = new FileInputStream(picturePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //   profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            //Conversion into byte array
            Bitmap bitmap = BitmapFactory.decodeStream(fis);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            // profile_image.setImageBitmap(bitmap);

            try {
                File f = new File(picturePath);
                byte[] bytesFromFile = ToBytes.getBytes(f);
                 imageLength = f.length() / 1024;

            } catch (IOException e) {
                e.printStackTrace();
            }
//
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
             bitmap_new.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
            final byte[] b1 = baos.toByteArray();


            String encImage = Base64.encodeToString(getBytesFromBitmap(bitmap_new),
                    Base64.NO_WRAP);

            if (imageLength<5000) {
                uploadImagenew(encImage);
            }else {
                Toast.makeText(getActivity(),"Document should be less than 5MB.",Toast.LENGTH_LONG).show();
            }

            //  Upload(encImage);

          /*  param1.put("Ocp-Apim-Subscription-Key", IConstant.KEY);
            param.put("Object_type", list.get(position).getObjectType());
            param.put("Object_reference", list.get(position).getObjectReference());
            param.put("Attachment_type", list.get(position).getAttachmentType());
            param.put("Attachment_hmy", list.get(position).getHAttachment());
            callpostApi(param, param1);
        */


        }else if (requestCode == RESULT_PDF && resultCode == RESULT_OK && isPDF==1){

            Uri filePath = data.getData();
            Toast.makeText(getActivity(),"sfdf",Toast.LENGTH_LONG).show();
            if (resultCode == RESULT_OK) {

                String fullPath = FileUtils.getRealPathFromURI_API19(getActivity(),filePath);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    private String getPath(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if(isKitKat) {
            // MediaStore (and general)
            return getForApi19(uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    @TargetApi(19)
    private String getForApi19(Uri uri) {
        Log.e("sdfsdf", "+++ API 19 URI :: " + uri);
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            Log.e("sfsdf", "+++ Document URI");
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                Log.e("sdfsf", "+++ External Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    Log.e("sdfsd", "+++ Primary External Document URI");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                Log.e("sdfsf", "+++ Downloads External Document URI");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                Log.e("dfsdfds", "+++ Media Document URI");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    Log.e("sdfsd", "+++ Image Media Document URI");
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    Log.e("sdfsdf", "+++ Video Media Document URI");
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    Log.e("sdfsdf", "+++ Audio Media Document URI");
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.e("sdfd", "+++ No DOCUMENT URI :: CONTENT ");

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.e("sdfsdf", "+++ No DOCUMENT URI :: FILE ");
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public static String convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    public String getPDFPath(Uri uri){

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
        private String encodeImage(Bitmap bm) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            imageLength =  b.length/1024;
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);

            return encImage;
        }

        public String convertFileToBase64String(File f) throws FileNotFoundException
    {
        InputStream inputStream = new FileInputStream(f.getAbsolutePath());
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {while ((bytesRead = inputStream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        bytes = baos.toByteArray();

       String encodedFile = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedFile;
    }

    // Converting File to Base64.encode String type using Method
    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile= "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile =  output.toString();
        }
        catch (FileNotFoundException e1 ) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    private String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes,Base64.NO_WRAP);
        return encoded;
    }
    private void Upload(String encImage) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("Object_Type", "Tenant");
            jsonObject.put("Object_Reference", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("File_Name", "photo");
            jsonObject.put("Attachment_Type", attachmentTypeName);
            jsonObject.put("File_Extension", ".pdf");
            jsonObject.put("Attachment", encImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new RequestService(getActivity(), MyDocumentFragment.this).postRequest("MyDocuments/Attachment/Create", jsonObject);


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


    public void onButtonShowPopupWindowClickDefault(View view) {
        applyDim(root, 0.5f);
        LinearLayout ll_camera, ll_removePhoto, ll_gallery,ll_pdf;
        View view_camera;
        TextView tv_cancel, tv_ok, tv_title;
        CircleImageView iv_remove_photo;
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.profilepic_dialog, null);
        ll_camera = (LinearLayout) popupView.findViewById(R.id.ll_camera);
        ll_removePhoto = (LinearLayout) popupView.findViewById(R.id.ll_removePhoto);
        tv_title = (TextView) popupView.findViewById(R.id.tv_title);
        tv_title.setText("Upload");
        ll_removePhoto.setVisibility(View.GONE);
        view_camera = (View) popupView.findViewById(R.id.view_camera);
        view_camera.setVisibility(View.GONE);
        ll_gallery = (LinearLayout) popupView.findViewById(R.id.ll_gallery);

        iv_remove_photo = (CircleImageView) popupView.findViewById(R.id.iv_remove_photo);
        // iv_remove_photo.setImageBitmap(bp);
        tv_cancel = (TextView) popupView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) popupView.findViewById(R.id.tv_ok);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, false);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                isPDF=0;

             /*   Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_GALLERY);
*/


                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_GALLERY);

              /*  Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
                bac_dim_layout.setVisibility(View.GONE);*/

            }
        });

        /*ll_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                isPDF=1;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, RESULT_PDF);



            }
        });*/
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

            }
        });
        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDim(root);
                isPDF=0;


                if (!checkPermission()) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    clearDim(root);
                    popupWindow.dismiss();

                }else {
                    popupWindow.dismiss();
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                ///
            }


        });

        ll_removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                clearDim(root);

                //  onRemovePopupDialog(v,bp);

            }
        });

        // dismiss the popup window when touched
       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });*/

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();

    }

    @Override
    public void downloadPresses(int position, String attachmentType, String hatype) {
        //  Toast.makeText(getActivity(),"yes",Toast.LENGTH_LONG ).show();


        volleyCall(attachmentType, hatype);
//downloadImage(attachmentType,hatype);

    }

    @Override
    public void uploadPresses(int position, String attachmnent_type, String haattachmnent_type) {



            if (position == 2) {
                attachmentTypeName = "Passport Copy of Tenant";
            }

            if (position == 3) {
                attachmentTypeName = "Emirates ID Copy";
            }
            if (position == 4) {
                attachmentTypeName = "Resident Visa Copy";
            }
            if (position == 5) {
                attachmentTypeName = "DEWA Registration Receipt";
            }
            if (position == 6) {
                attachmentTypeName = "Empower Registration Receipt";
            }
            if (position == 7) {
                attachmentTypeName = "Final DEWA Bill";
            }
            if (position == 8) {
                attachmentTypeName = "Final Empower Bill";
            }
            onButtonShowPopupWindowClickDefault(view);

    }


    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", IConstant.KEY);
            builder.addHeader("SessionId", applicationPreferences.getSessionId());
            builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

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
            progressDialog.dismiss();
            if (response != null) {
                if (!response.equalsIgnoreCase("")) {
                    if (response.contains("Message")) {
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("ProfileDetails", "" + response);
                        Gson gson = new Gson();
                        tenantDetails = gson.fromJson(response, TenantDetails.class);
                        //  mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, tenantDetails.getTenantDetails().get(0).getTenantMobile());
                        //  firstName.setText(tenantDetails.getTenantDetails().get(0).getTenantName());
                        VolleySingleton.getInstance().setTenantDetails(tenantDetails);
                        new RequestService(getActivity(), MyDocumentFragment.this).setArgumentsSecond("MyDocuments/Status?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&templateCodes=" + "all");
                    }
                } else {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getActivity(), "Server Error. Please try after some time.", Toast.LENGTH_LONG).show();

            }
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
            progressDialog.setMessage("Uploading document...");
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
                //    used++;

                  /*  String code = String.valueOf(response.code());
                    if (!code.equalsIgnoreCase("200")) {
                        Toast.makeText(getActivity(), "Upload Failed. Please try after sometime", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Document Uploaded Successfully.", Toast.LENGTH_LONG).show();
                    }*/
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = responseObject.getJSONArray("CreateAttachment");

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String createAttachment = jsonObject.getString("CreateAttachment");

                    if (createAttachment.equalsIgnoreCase("null")) {
                        Toast.makeText(getActivity(), "Upload Failed. Please try after sometime", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Document Uploaded Successfully.", Toast.LENGTH_LONG).show();
                        loadFragment(new MyDocumentFragment());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            }
            // Toast.makeText(getActivity(),"Picture Uploaded Successfully",Toast.LENGTH_LONG).show();



    }

    public void uploadImagenew(String encoded) {
        JSONObject jsonObject = new JSONObject();
        long ts = System.nanoTime();

        try {
            jsonObject.put("Object_Type", "Owner");
            jsonObject.put("Object_Reference", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("File_Name", "UploadDocument_"+ts);
            jsonObject.put("Attachment_Type", attachmentTypeName);

            jsonObject.put("File_Extension", ".jpg");
            jsonObject.put("Attachment", encoded);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL + "MyDocuments/Attachment/Create");
        builder = builder.post(body);
        builder = builder.addHeader("Content-Type", "application/json");

        builder = builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
        builder = builder.addHeader("SessionId", applicationPreferences.getSessionId());
        builder = builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new HttpAsyncTask().execute(request);
    }

    public void downloadImage(String attachType, String haAttach) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("Object_type", "Tenant");
            jsonObject.put("Object_reference", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Attachment_type", attachType);
            jsonObject.put("Attachment_hmy", haAttach);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        // Request request = new Request().Builder().url().post(body).build();
        REGISTER_URL = IConstant.BASE_URL;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REGISTER_URL + "MyDocuments/Attachment");
        builder = builder.post(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key", applicationPreferences.getLastStoredPassword());
        builder = builder.addHeader("SessionId", applicationPreferences.getSessionId());
        builder = builder.addHeader("ProfileId", mSavePreference.getString(IpreferenceKey.TCODE));

        Request request = builder.build();
        new HttpAsyncTask().execute(request);
    }

    public void volleyCall(String attachType, String haAttach) {
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();

        requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("Object_type", "Tenant");
            jsonObject.put("Object_reference", mSavePreference.getString(IpreferenceKey.TCODE));
            jsonObject.put("Attachment_type", attachType);
            jsonObject.put("Attachment_hmy", haAttach);
            mRequestBody = jsonObject.toString();


            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, IConstant.BASE_URL + "MyDocuments/Attachment",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("LOG_RESPONSE", response);


                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String base64 = jsonObject.getString("FileBase64String");


                                String filename = jsonObject.getString("File_Name");


                                String extension = filename.substring(filename.lastIndexOf("."));

                                if (extension.equalsIgnoreCase(".pdf")) {

                                    byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);
                                    final File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +  "Document" + ".pdf");
                                    byte[] pdfAsBytes1 = Base64.decode(base64, 0);
                                    FileOutputStream os;
                                    try {
                                        os = new FileOutputStream(dwldsPath, false);
                                        os.write(pdfAsBytes1);
                                        os.flush();
                                        os.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                   // String text = new String(pdfAsBytes, StandardCharsets.UTF_8);
                                    //createandDisplayPdf(text);
                                }
                                if (extension.equalsIgnoreCase(".jpeg")) {
                                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                    bitmap_new = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    showPopUp();
                                }

                                if (extension.equalsIgnoreCase(".jpg")) {
                                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                    bitmap_new = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    showPopUp();
                                }
                                if (extension.equalsIgnoreCase(".png")) {
                                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                    bitmap_new = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    showPopUp();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(),"Downloaded",Toast.LENGTH_LONG).show();

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Please try later. Something went wrong", Toast.LENGTH_LONG).show();
                    Log.e("LOG_RESPONSE", error.toString());
                }
            })

            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

               /* @Override
                protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return com.android.volley.Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }*/


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return param1;
                }


            };
            int socketTimeout = 40000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showPopUp() {
        applyDim(root, 0.5f);
        final Dialog nagDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.image_pop_up);
        ImageView btnClose = (ImageView) nagDialog.findViewById(R.id.iv_close);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.imageView_photo);
        ivPreview.setImageBitmap(bitmap_new);

        try {
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            Integer counter = 0;
            File file = new File(path, "DocumentPhoto" + counter + ".jpg");
            fOut = new FileOutputStream(file);
            bitmap_new.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    clearDim(root);
                    nagDialog.dismiss();
                }
            });
            nagDialog.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private boolean checkPermission() {
        clearDim(root);

        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }
    private Intent getFileChooserIntent() {
        String[] mimeTypes = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }
}
