package com.residents.dubaiassetmanagement;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.util.Scanner;

import de.greenrobot.event.EventBus;


/*
* Used to check version on PlayStore
* */

public class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

    private ProgressDialog progressDialog;
    String latestVersion, currentVersion;
    Activity mcontext;
    private String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.residents.dubaiassetmanagement";
    Dialog dialog;

    public GetLatestVersion(String currentVersion, Activity mainActivity) {
        this.currentVersion = currentVersion;
        mcontext = mainActivity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
//It retrieves the latest version by scraping the content of current version from play store at runtime
            Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
            System.out.println("dco"+doc.location().toString());
            System.out.println("dco1"+doc.body().toString());
            System.out.println("dco2  "+ doc.getElementsByClass("htlgb").get(6).text().toString());
          //  latestVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
            latestVersion = doc.getElementsByClass("htlgb").get(6).text().toString();


        } catch (Exception e) {
            e.printStackTrace();
            //EventBus.getDefault().post(new OpenLoginScreen());
        }

        return new JSONObject();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (latestVersion != null) {

int version=versionCompare(currentVersion,latestVersion);

if (String.valueOf(version).equalsIgnoreCase("1") || String.valueOf(version).equalsIgnoreCase("-1")){
    showUpdateDialog();
}else {

}
         /*   Float curntVersion = Float.valueOf(currentVersion);
            Float latstVersion = Float.valueOf(latestVersion);
            if (curntVersion < latstVersion) {//!currentVersion.equalsIgnoreCase(latestVersion)) {
              showUpdateDialog();
            }else {
               // EventBus.getDefault().post(new OpenLoginScreen());

            }*/
        }
    }


   /* private void showUpdateDialog() {
       *//* final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("A new version of CNAC is available.");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/apps/details?id=com.zoetis.hms")));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("May be later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EventBus.getDefault().post(new OpenLoginScreen());

                dialog.dismiss();
            }

        });


        builder.setCancelable(false);
        dialog = builder.show();*//*



    }*/


    private void showUpdateDialog() {
        final Dialog alert = new Dialog(mcontext);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.forceupdate_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alert.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        alert.getWindow().setAttributes(lp);
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView update = (TextView) alert.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/apps/details?id=com.residents.dubaiassetmanagement")));
                }
        });


        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();



    }
    public static int versionCompare(String str1, String str2) {
        try (Scanner s1 = new Scanner(str1);
             Scanner s2 = new Scanner(str2);) {
            s1.useDelimiter("\\.");
            s2.useDelimiter("\\.");

            while (s1.hasNextInt() && s2.hasNextInt()) {
                int v1 = s1.nextInt();
                int v2 = s2.nextInt();
                if (v1 < v2) {
                    return -1;
                } else if (v1 > v2) {
                    return 1;
                }
            }

            if (s1.hasNextInt() && s1.nextInt() != 0)
                return 1; //str1 has an additional lower-level version number
            if (s2.hasNextInt() && s2.nextInt() != 0)
                return -1; //str2 has an additional lower-level version

            return 0;
        } // end of try-with-resources
    }
}