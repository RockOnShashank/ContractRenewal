package com.residents.dubaiassetmanagement.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.Helper.UrlHelper;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.Profile.MeFragment;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;
import com.google.gson.JsonParser;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestService {
private String BASE_URL;
private String REQUEST_URL;
    private Context ctx;
    private ResponseCallback responseCallback;
    private ResponseCallbackRecaptcha responseCallbackRecaptcha;
    private String sessionid;
    private SavePreference mSavePreference;


    public RequestService(Context context, ResponseCallback responseCallback) {
        this.ctx = context;
        this.responseCallback = responseCallback;
    }

    public RequestService(Context context, ResponseCallback responseCallback, ResponseCallbackRecaptcha responseCallbackRecaptcha) {
        this.ctx = context;
        this.responseCallbackRecaptcha = responseCallbackRecaptcha;
    }

    ApplicationPreferences preferences =  ApplicationPreferences.getInstance(ApplicationController.getContext());

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
           //

            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();

                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccess(response);
            }
        }
    }

    public class OkHttpHandlerAttachment extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          //  progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            //

            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
           // progressDialog.dismiss();
            if (response != null) {
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccess(response);
            }
        }
    }


    public class OkHttpHandlerNoti extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallbackRecaptcha.onSuccessJWT(response);
            }
        }
    }


    public class OkHttpHandlerRecaptcha extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
          //

            //  builder.addHeader("SessionId", preferences.getSessionId());
         //   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();

                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallbackRecaptcha.onSuccessRecaptcha(response);
            }
        }
    }
    public class OkHttpHandlerNotification extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            //

            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccessNotificationCount(response);
            }
        }
    }
    public class OkHttpHandlerHome extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );
         //

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccessHome(response);
            }
        }
    }

    public class LogoutGetApi extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Logging out...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
           //

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccess(response);
            }
        }
    }
    public class OkHttpHandlerSecond extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build();

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mSavePreference = SavePreference.getInstance(ctx);

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            builder.addHeader("Ocp-Apim-Subscription-Key", preferences.getLastStoredPassword());
            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );
           //

            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                Log.i("CallbackResponse", "" + response);
                responseCallback.onSuccessSecond(response);
            }
        }
    }

    class PutRequestCall extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            mSavePreference = SavePreference.getInstance(ctx);

            try {

                response = client.newCall(requests[0]).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                    Log.i("Callbackresponse", "" + responseObject);
                   responseCallback.onPostSuccess(responseObject.toString(),"");
                 //   Toast.makeText(ctx, "Updated Successfully.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();

        }
    }

    class PutRequestCallSkip extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        @Override
        protected Response doInBackground(Request... requests) {
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            mSavePreference = SavePreference.getInstance(ctx);

            try {

                response = client.newCall(requests[0]).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
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
                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(response.body().string());
                    Log.i("Callbackresponse", "" + responseObject);
                //    responseCallback.onPostSuccess(responseObject.toString(),"");
                    //   Toast.makeText(ctx, "Updated Successfully.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class PostRequest extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;
            mSavePreference = SavePreference.getInstance(ctx);

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
                Log.i("CallbackResponse", "" + response);
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

    //getting response headers
                //
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("CallbackResponse", "" + responseObject);
                   responseCallback.onPostSuccess(responseObject.toString(),"");
                 //   Toast.makeText(ctx, String.valueOf(responseObject), Toast.LENGTH_LONG).show();


                }catch (JSONException e) {
                    responseCallback.onPostSuccess("true","");

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class PostRequestLogin extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;
            mSavePreference = SavePreference.getInstance(ctx);

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
                Log.i("CallbackResponse", "" + response);
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

                //getting response headers


                sessionid = response.header("SessionId");


                //
                try {
                    JSONObject responseObject = new JSONObject(response.body().string());
                    Log.i("CallbackResponse", "" + responseObject);
                    responseCallback.onPostSuccess(responseObject.toString(),sessionid);
                    //   Toast.makeText(ctx, String.valueOf(responseObject), Toast.LENGTH_LONG).show();


                }catch (JSONException e) {
                    responseCallback.onPostSuccess("true","");

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class PostRequestLoginJWT extends AsyncTask<Request, Void, Response> {
        private ProgressDialog progressDialog;
        OkHttpClient okHttpClient = new OkHttpClient();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;
            mSavePreference = SavePreference.getInstance(ctx);

            try {
                response = okHttpClient.newCall(requests[0]).execute();
                long tx = response.sentRequestAtMillis();
                long rx = response.receivedResponseAtMillis();
                System.out.println("ResponseTime : "+(rx - tx)+" ms");
                Log.i("CallbackResponse", "" + response);
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

               //


                //
                try {
                    JsonParser parser = new JsonParser();
                    String accessToken = parser.parse(response.body().string()).getAsString();
responseCallbackRecaptcha.onSuccessJWT(accessToken);

                } catch (IOException e) {
                    Log.i("CallbackResponse", "" + e.getMessage());

                    e.printStackTrace();
                }
            }

        }
    }
    public void setArguments(String url){
        BASE_URL = IConstant.BASE_URL;
       // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsAttachment(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerAttachment okHttpHandler = new OkHttpHandlerAttachment();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsNotification(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerNoti okHttpHandler = new OkHttpHandlerNoti();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsRecaptcha(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerRecaptcha okHttpHandler = new OkHttpHandlerRecaptcha();
        okHttpHandler.execute(REQUEST_URL);
    }

    public void setArgumentsNotificationCount(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerNotification okHttpHandler = new OkHttpHandlerNotification();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsforHome(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerHome okHttpHandler = new OkHttpHandlerHome();
        okHttpHandler.execute(REQUEST_URL);
    }

    public void setArgumentsLogout(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        LogoutGetApi okHttpHandler = new LogoutGetApi();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsSecond(String url){
        BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerSecond okHttpHandler = new OkHttpHandlerSecond();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void putRequest(String url,String jsonBody){
        mSavePreference = SavePreference.getInstance(ctx);

        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL=BASE_URL+url;
        JSONObject jsonObject = new JSONObject();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
      builder =  builder.addHeader("SessionId", preferences.getSessionId());
     builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
        //

        Request request = builder.build();
        new PutRequestCall().execute(request);
    }
    public void putRequestSkip(String url,String jsonBody){
        mSavePreference = SavePreference.getInstance(ctx);

        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL=BASE_URL+url;
        JSONObject jsonObject = new JSONObject();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
        //

        Request request = builder.build();
        new PutRequestCallSkip().execute(request);
    }
    public void putRequestForgetPassword(String url,String jsonBody, String tCode){
        mSavePreference = SavePreference.getInstance(ctx);

        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL=BASE_URL+url;
        JSONObject jsonObject = new JSONObject();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request.Builder builder = new Request.Builder();
        builder = builder.url(REQUEST_URL);
        builder = builder.put(body);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        builder =   builder.addHeader("ProfileId",tCode);
        //

        Request request = builder.build();
        new PutRequestCall().execute(request);
    }


    public void postRequest(String url, JSONObject object){
        mSavePreference = SavePreference.getInstance(ctx);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyString = RequestBody.create(JSON,object.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+url;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(bodyString);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );
        //

        Request request = builder.build();
        new PostRequest().execute(request);


    }


    public void postRequestFacebook(String url, JSONObject object){
        mSavePreference = SavePreference.getInstance(ctx);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyString = RequestBody.create(JSON,object.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+url;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(bodyString);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        //

        Request request = builder.build();
        new PostRequestLogin().execute(request);


    }

    public void postRequestLogin(String url, JSONObject object){
        mSavePreference = SavePreference.getInstance(ctx);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyString = RequestBody.create(JSON,object.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+url;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(bodyString);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        //

        Request request = builder.build();
        new PostRequestLogin().execute(request);


    }
    public void postRequestLoginJWT(String url, JSONObject object){
        mSavePreference = SavePreference.getInstance(ctx);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyString = RequestBody.create(JSON,object.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;
        BASE_URL = BASE_URL+url;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(bodyString);
        String ocp = preferences.getLastStoredPassword().replace("\n","");
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",ocp);

        Request request = builder.build();
        new PostRequestLoginJWT().execute(request);


    }

    public void postRequestFirebase(String url, JSONObject object){

        mSavePreference = SavePreference.getInstance(ctx);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody bodyString = RequestBody.create(JSON,object.toString());
        // Request request = new Request().Builder().url().post(body).build();
        BASE_URL = IConstant.BASE_URL;

        BASE_URL =BASE_URL+ url;
        Request.Builder builder = new Request.Builder();
        builder = builder.url(BASE_URL);
        builder = builder.post(bodyString);
        builder = builder.addHeader("Ocp-Apim-Subscription-Key",preferences.getLastStoredPassword());
        builder =  builder.addHeader("SessionId", preferences.getSessionId());
        builder =   builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE) );
        //

        Request request = builder.build();
        new PostRequest().execute(request);
    }
}
