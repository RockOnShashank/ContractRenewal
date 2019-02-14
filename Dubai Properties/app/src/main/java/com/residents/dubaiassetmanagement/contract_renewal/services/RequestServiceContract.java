package com.residents.dubaiassetmanagement.contract_renewal.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.residents.dubaiassetmanagement.ApplicationPreferences;
import com.residents.dubaiassetmanagement.IConstant;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.contract_renewal.interfaces.ContractRenewalCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestServiceContract {

    private String BASE_URL;
    private String REQUEST_URL;
    private Context ctx;
    private ContractRenewalCallback responseCallback;
    private ResponseCallbackRecaptcha responseCallbackRecaptcha;
    private String sessionid;
    private SavePreference mSavePreference;



    public RequestServiceContract(Context context, ContractRenewalCallback responseCallback) {
        this.ctx = context;
        this.responseCallback = responseCallback;
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
            builder.addHeader("Ocp-Apim-Subscription-Key", "3258a73a9b3a4449af9328d933af22fd");
            //

            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

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

    public class OkHttpHandlerGetStepper extends AsyncTask<String, Void, String> {

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
            builder.addHeader("Ocp-Apim-Subscription-Key", "3258a73a9b3a4449af9328d933af22fd");
            //

            builder.addHeader("SessionId", preferences.getSessionId());
            builder.addHeader("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));

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
                responseCallback.onSuccessGetStepper(response);
            }
        }
    }
    public void setArguments(String url){
      //  BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;

        BASE_URL= IConstant.BASE_URL;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(REQUEST_URL);
    }
    public void setArgumentsGetStepper(String url){
        //  BASE_URL = IConstant.BASE_URL;
        // REQUEST_URL = BASE_URL + url;

        BASE_URL = IConstant.BASE_URL;
        REQUEST_URL=BASE_URL+url;
        OkHttpHandlerGetStepper okHttpHandler = new OkHttpHandlerGetStepper();
        okHttpHandler.execute(REQUEST_URL);
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
                    responseCallback.onPostSuccess(responseObject.toString());
                    //   Toast.makeText(ctx, String.valueOf(responseObject), Toast.LENGTH_LONG).show();


                }catch (JSONException e) {

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
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
        Request request = builder.build();
        new PostRequest().execute(request);
    }
}
