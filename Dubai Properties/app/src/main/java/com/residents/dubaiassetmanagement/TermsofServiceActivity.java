package com.residents.dubaiassetmanagement;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsofServiceActivity extends AppCompatActivity {


    WebView wv_terms;

    private View view;
    private TextView fragmentTitle;
    private ImageView iv_backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsofservice_activity);
        //Set Fragment Title
       // fragmentTitle = (TextView) findViewById(R.id.title_app_bar);
      //  fragmentTitle.setText("TERMS AND CONDITIONS");
        wv_terms = (WebView) findViewById(R.id.wv_terms);
        iv_backButton = (ImageView) findViewById(R.id.iv_backButton);
        wv_terms.loadUrl("https://residents.dubaiam.ae/terms-and-conditions-mbl");

  //      wv_terms.loadUrl("https://deloittecustomerportalscal-333870-cd.azurewebsites.net/terms-and-conditions-mbl");
        WebSettings webSettings = wv_terms.getSettings();
        webSettings.setJavaScriptEnabled(true);
        iv_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
